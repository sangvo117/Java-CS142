package model.items;

import model.entities.LivingEntity;
import model.world.Cell;

import static util.config.SimulationConstants.MEDKIT_HEAL_AMOUNT;
import static util.config.SimulationConstants.MEDKIT_STRING;

/**
 * Medkit — heals the user.
 */
public class Medkit extends Equipment {
    public Medkit(Cell cell) {
        this(cell, MEDKIT_STRING);
    }

    protected Medkit(Cell cell, String displayName) {
        super(cell, displayName);
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        target.takeDamage(MEDKIT_HEAL_AMOUNT);
    }

}