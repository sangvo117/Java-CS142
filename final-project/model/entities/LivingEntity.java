package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.enums.Faction;
import model.world.Cell;
import model.world.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Base class for all living entities (humans, zombies).
 * Contains health, stats, faction, initiative, and behavior system.
 */
public abstract class LivingEntity extends Entity {

    private static final Random RANDOM = new Random();

    protected int maxHealth;
    protected int health;
    protected int baseDamage;
    protected int baseDefense;
    protected int baseSpeed;

    private int damageBonus = 0;
    private int defenseBonus = 0;
    private int speedBonus = 0;

    private final int tieBreaker;

    private final Faction faction;

    protected LivingEntity(Cell cell, String displayName, int maxHealth, int damage, int defense, int speed, Faction faction) {
        super(cell, displayName);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseDamage = damage;
        this.baseDefense = defense;
        this.baseSpeed = speed;
        this.faction = Objects.requireNonNull(faction, "Faction cannot be null");
        this.tieBreaker = RANDOM.nextInt(100);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return baseDamage + damageBonus;
    }

    public int getDefense() {
        return baseDefense + defenseBonus;
    }

    public int getSpeed() {
        return Math.max(1, baseSpeed + speedBonus);
    }

    public void addDamageBonus(int bonus) {
        this.damageBonus += bonus;
    }

    public void addDefenseBonus(int bonus) {
        this.defenseBonus += bonus;
    }

    public void addSpeedBonus(int bonus) {
        this.speedBonus += bonus;
    }

    public int getInitiative() {
        return getSpeed() * 100 + tieBreaker;  // Perfect: no ties, speed dominates
    }

    public int getTieBreaker() {
        return tieBreaker;
    }

    public void takeDamage(int rawDamage) {
        if (rawDamage <= 0) {
            health = Math.min(maxHealth, health - rawDamage);
            return;
        }
        int damageTaken = Math.max(0, rawDamage - getDefense());
        health = Math.max(0, health - damageTaken);
    }

    public void attack(LivingEntity target) {
        if (target != null && target.isPresent()) {
            target.takeDamage(getDamage());
        }
    }

    @Override
    public final boolean isPresent() {
        return health > 0;
    }

    public final boolean isDead() {
        return health <= 0;
    }

    public final void kill() {
        health = 0;
    }

    public Faction getFaction() {
        return faction;
    }

    public boolean isEnemyOf(LivingEntity other) {
        Objects.requireNonNull(other, "Cannot check enmity with null entity");

        if (this.faction == Faction.NEUTRAL || other.faction == Faction.NEUTRAL) {
            return false;
        }
        return this.faction != other.faction;
    }

    public boolean isAllyOf(LivingEntity other) {
        Objects.requireNonNull(other, "Cannot check enmity with null entity");

        if (this.faction == Faction.NEUTRAL || other.faction == Faction.NEUTRAL) {
            return false;
        }
        return this.faction == other.faction;
    }

    public final void act(Simulation simulation) {
        if (!isPresent()) return;

        for (Behavior behavior : getBehaviors()) {
            Action result = behavior.execute(this, simulation);
            if (result == Action.DEAD || result == Action.TRANSFORM) {
                break;
            }
        }
    }

    protected List<Behavior> getBehaviors() {
        return new ArrayList<>();
    }
}