package model.entities;

import model.enums.Faction;
import model.items.Equipment;
import model.world.Cell;
import model.world.Simulation;

import static util.config.SimulationConstants.*;

/**
 * Base class for humans with infection and settlement behavior.
 */
public abstract class Human extends LivingEntity {
    protected boolean infected = false;
    protected int infectionTimer = 0;

    protected Human(Cell cell) {
        this(cell, HUMAN_STRING_DEFAULT,
                HUMAN_HEALTH_DEFAULT,
                HUMAN_DAMAGE_DEFAULT,
                HUMAN_DEFENSE_DEFAULT,
                HUMAN_SPEED_DEFAULT);
    }

    protected Human(Cell cell, String displayName,
                    int maxHealth, int damage, int defense, int speed) {
        super(cell, displayName, maxHealth, damage, defense, speed, Faction.HUMAN);
    }


    @Override
    public char getSymbol() {
        if (shouldBecomeZombie()) return COMMON_ZOMBIE_CHAR;
        if (infectionTimer > 0) return INFECTION_CHAR;
        return super.getSymbol();
    }

    public void infect() {
        if (!infected) {
            infected = true;
            infectionTimer = INFECTION_TURNS;
        }
    }

    protected void decrementInfectionTimer() {
        if (infected) infectionTimer = Math.max(0, infectionTimer - 1);
    }

    public boolean shouldBecomeZombie() {
        return infected && infectionTimer <= 0;
    }

    protected boolean isInSettlement(Simulation sim) {
        int nearbyHumans = 0;

        for (int dy = -SETTLEMENT_FORMATION_RADIUS; dy <= SETTLEMENT_FORMATION_RADIUS; dy++) {
            for (int dx = -SETTLEMENT_FORMATION_RADIUS; dx <= SETTLEMENT_FORMATION_RADIUS; dx++) {
                int newX = getX() + dx;
                int newY = getY() + dy;
                if (!sim.isValid(newX, newY)) continue;

                Entity entity = sim.get(new Cell(newX, newY));
                if (entity instanceof Human
                        && (Human) entity != this
                        && ((Human) entity).isPresent()) {
                    Human human = (Human) entity;
                    nearbyHumans++;
                }
            }
        }

        return nearbyHumans + 1 >= SETTLEMENT_MIN_SIZE;
    }

    protected void moveToFormSettlement(Simulation sim) {
        if (isInSettlement(sim)) {
            return;
        }

        Human nearest = sim.findNearest(getCell(), Human.class);
        if (nearest != null && getCell().distanceTo(nearest.getCell()) > 1) {
            sim.moveToward(nearest.getCell(), this);
        }
    }

    protected void tryPickup(Simulation sim) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int newX = getX() + dx, newY = getY() + dy;
                if (sim.isValid(newX, newY)) {
                    Entity e = sim.get(new Cell(newX, newY));
                    if (e instanceof Equipment) {
                        Equipment equipment = (Equipment) e;
                        equipment.useOn(this, sim);
                        return;
                    }
                }
            }
        }
    }
}
