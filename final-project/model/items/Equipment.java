package model.items;

import model.entities.LivingEntity;
import model.entities.NonLivingEntity;
import model.world.Cell;
import model.world.Simulation;

/**
 * Abstract base class for all usable equipment.
 * When used, applies effect and removes itself from the world.
 */
public abstract class Equipment extends NonLivingEntity {

    protected Equipment(Cell cell, String displayName) {
        super(cell, displayName);
    }

    /**
     * Uses this equipment on a target entity.
     * Applies the effect and permanently removes the item from the grid.
     */
    public void useOn(LivingEntity target, Simulation grid) {
        applyEffect(target);
        grid.remove(this); // Item disappears after use — perfect
    }

    /** Applies the equipment's effect to the target */
    protected abstract void applyEffect(LivingEntity target);
}