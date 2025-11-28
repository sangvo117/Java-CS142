package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

import static util.config.SimulationConstants.*;

/**
 * Follows its Elite leader. Repels rival hordes. Never merges.
 */
public class CommonZombie extends Undead {
    public CommonZombie(Cell cell) {
        super(cell, COMMON_ZOMBIE_STRING, COMMON_ZOMBIE_HEALTH, COMMON_ZOMBIE_DAMAGE, UNDEAD_DEFENSE_DEFAULT, COMMON_ZOMBIE_SPEED);
    }

    public CommonZombie(Cell cell, String displayName, int health, int damage, int defense, int speed) {
        super(cell, displayName, health, damage, defense, speed);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::repelRivals,
                this::followLeader,
                this::hunt,
                this::infect
        );
    }

    private Action repelRivals(LivingEntity me, Simulation sim) {
        EliteZombie myLeader = sim.findNearest(me.getCell(), EliteZombie.class);
        EliteZombie rival = sim.findNearest(me.getCell(), EliteZombie.class);
        if (rival != null && rival != myLeader && getCell().distanceTo(rival.getCell()) <= RIVAL_REPEL_RANGE) {
            sim.moveAwayFrom(me, rival);
            return Action.MOVE;
        }
        return Action.IDLE;
    }

    private Action followLeader(LivingEntity me, Simulation sim) {
        EliteZombie leader = sim.findNearest(me.getCell(), EliteZombie.class);
        if (leader != null && getCell().distanceTo(leader.getCell()) > HORDE_FOLLOW_RANGE) {
            sim.moveToward(leader.getCell(), me);
        }
        return Action.MOVE;
    }

    private Action hunt(LivingEntity me, Simulation sim) {
        sim.moveTowardNearest(me, Human.class);
        return Action.MOVE;
    }

    private Action infect(LivingEntity me, Simulation sim) {
        infectNearby(sim);
        return Action.INFECT;
    }

    private Action attackOrInfect(LivingEntity me, Simulation sim) {
        Human target = sim.findNearest(me.getCell(), Human.class);
        if (target != null) {
            int originalHealth = target.getHealth();
            me.attack(target);
            if (target.getHealth() < originalHealth) {
                boolean infected = infectNearby(sim);
                if (infected) return Action.INFECT;
            }
            return Action.ATTACK;
        }
        return Action.IDLE;
    }
}
