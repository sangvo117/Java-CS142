package model.entities;

import model.enums.Faction;
import model.world.Cell;
import model.world.Simulation;

import static util.config.SimulationConstants.*;

/**
 * Base class for zombies with infection spread.
 */
public abstract class Undead extends LivingEntity {
    protected double infectionRate = INFECTION_RATE_DEFAULT;

    protected Undead(Cell cell, String displayName,
                     int maxHealth, int damage, int defense, int speed) {
        super(cell, UNDEAD_STRING_DEFAULT, UNDEAD_HEALTH_DEFAULT, UNDEAD_DAMAGE_DEFAULT, UNDEAD_DEFENSE_DEFAULT, UNDEAD_SPEED_DEFAULT, Faction.UNDEAD);
    }

    protected boolean infectNearby(Simulation grid) {
        boolean infectedSomeone = false;
        for (LivingEntity le : grid.getNearbyLiving(getCell(), 1)) {
            if (le instanceof Human && Math.random() < infectionRate) {
                ((Human) le).infect();
                infectedSomeone = true;
            }
        }
        return infectedSomeone;
    }

}