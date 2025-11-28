package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

import static util.config.SimulationConstants.*;

public class Civilian extends Human {
    public Civilian(Cell cell) {
        super(cell, CIVILIAN_STRING, CIVILIAN_HEALTH, CIVILIAN_DAMAGE, HUMAN_DEFENSE_DEFAULT, CIVILIAN_SPEED);
    }

    public Civilian(Cell cell, String name, int maxHeath, int damage, int defense, int speed) {
        super(cell, name, maxHeath, damage, defense, speed);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::pickup,
                this::formSettlement,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, Simulation g) {
        if (tryPickup(g)) return Action.PICKUP;
        return Action.IDLE;
    }

    private Action formSettlement(LivingEntity me, Simulation g) {
        moveToFormSettlement(g);
        return Action.GROUP;
    }

    private Action infectionCheck(LivingEntity me, Simulation g) {
        decrementInfectionTimer();
        return Action.IDLE;
    }
}
