package model.items;

import model.entities.LivingEntity;
import model.world.Cell;

import static util.config.SimulationConstants.*;

/**
 * Weapon — increases damage, slows movement.
 */
public class Weapon extends Equipment {
    public Weapon(Cell cell) {
        this(cell, WEAPON_STRING);
    }

    protected Weapon(Cell cell, String displayName) {
        super(cell, displayName);
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        target.addDamageBonus(WEAPON_DAMAGE_BONUS);
        target.addSpeedBonus(WEAPON_SPEED_PENALTY);
    }
}