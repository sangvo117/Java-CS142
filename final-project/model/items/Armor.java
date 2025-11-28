package model.items;

import model.entities.LivingEntity;
import model.world.Cell;

import static util.config.SimulationConstants.ARMOR_DEFENSE_BONUS;
import static util.config.SimulationConstants.ARMOR_STRING;

/**
 * Armor — increases defense.
 */
public class Armor extends Equipment {
    public Armor(Cell cell) {
        this(cell,  ARMOR_STRING);
    }

    public Armor(Cell cell, String displayName) {
        super(cell, displayName);
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        target.addDefenseBonus(ARMOR_DEFENSE_BONUS);
    }

}