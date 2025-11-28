package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

import static util.config.SimulationConstants.*;

/**
 * Soldier that defends human settlements.
 */
public class Soldier extends Human {
    public Soldier(Cell cell) {
        super(cell, SOLDIER_STRING, SOLDIER_HEALTH, SOLDIER_DAMAGE, SOLDIER_DEFENSE, SOLDIER_SPEED);
    }

    public Soldier(Cell cell, String name, int maxHeath, int damage, int defense, int speed) {
        super(cell, name, maxHeath, damage, defense, speed);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::pickup,
                this::defendSettlement,
                this::attackUndead,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, Simulation sim) {
        tryPickup(sim);
        return Action.PICKUP;
    }

    private Action defendSettlement(LivingEntity me, Simulation sim) {
        Civilian nearest = sim.findNearest(me.getCell(), Civilian.class);
        if (nearest != null && nearest.isInSettlement(sim)) {
            int distance = me.getCell().distanceTo(nearest.getCell());
            if (distance > 4) {
                sim.moveToward(nearest.getCell(), me);
            }
        }
        return Action.GROUP;
    }

    private Action attackUndead(LivingEntity me, Simulation sim) {
        Undead nearest = sim.findNearest(me.getCell(), Undead.class);
        if (nearest != null && me.getCell().distanceTo(nearest.getCell()) <= 3) {
            me.attack(nearest);
        }
        return Action.ATTACK;
    }

    private Action infectionCheck(LivingEntity me, Simulation sim) {
        if (me instanceof Human) {
            Human human = (Human) me;
            human.decrementInfectionTimer();
        }
        return Action.IDLE;
    }
}
