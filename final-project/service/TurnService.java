package service;

import model.entities.CommonZombie;
import model.entities.Human;
import model.entities.LivingEntity;
import model.world.Cell;
import model.world.Simulation;
import model.world.WorldGrid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages turn order, initiative, actions, infection, and cleanup.
 * The heart of the simulation loop.
 */
public class TurnService {

    private final WorldGrid grid;
    private final Simulation simulation;

    public TurnService(WorldGrid grid, Simulation simulation) {
        this.grid = grid;
        this.simulation = simulation;
    }

    /**
     * Executes one complete game turn.
     */
    public void processTurn() {
        List<LivingEntity> leList = collectActiveEntities();
        sortByInitiative(leList);
        executeActions(leList);
        transformInfectedHumans();
        cleanupDeadEntities();
    }

    /**
     * Collects all living, active entities from the grid.
     */
    private List<LivingEntity> collectActiveEntities() {
        List<LivingEntity> leList = new ArrayList<>();

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                LivingEntity le = grid.getLivingAt(x, y);
                if (le != null && le.isPresent()) {
                    leList.add(le);
                }
            }
        }
        return leList;
    }

    /**
     * Sorts entities by initiative (speed × 100 + tieBreaker), descending.
     */
    private void sortByInitiative(List<LivingEntity> leList) {
        leList.sort(Comparator.comparingInt(LivingEntity::getInitiative).reversed());
    }

    /**
     * Lets every living entity perform its behavior chain.
     */
    private void executeActions(List<LivingEntity> leList) {
        for (LivingEntity actor : leList) {
            if (actor.isPresent()) {
                actor.act(simulation);
            }
        }
    }

    /**
     * Transforms humans who have been infected long enough into zombies.
     */
    private void transformInfectedHumans() {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Human human = grid.getAs(x, y, Human.class);
                if (human != null && human.shouldBecomeZombie()) {
                    Cell cell = new Cell(x, y);
                    human.kill();
                    grid.set(cell, new CommonZombie(cell));
                }
            }
        }
    }

    /**
     * Removes dead entities from the grid.
     * Override in subclasses or configure behavior later (e.g. leave corpse).
     */
    private void cleanupDeadEntities() {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                LivingEntity entity = grid.getLivingAt(x, y);
                if (entity != null && entity.isDead()) {
                    grid.set(new Cell(x, y), null);
                }
            }
        }
    }
}