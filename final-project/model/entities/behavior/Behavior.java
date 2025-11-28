package model.entities.behavior;

import model.entities.LivingEntity;
import model.enums.Action;
import model.world.Simulation;

/**
 * Functional interface for entity behaviors.
 */
@FunctionalInterface
public interface Behavior {
    /**
     * @return the type of action performed
     */
    Action execute(LivingEntity entity, Simulation simulation);

    /**
     * Optional debug description. Default = empty.
     * Override in concrete behaviors for rich logging.
     */
    default String getDebugInfo(LivingEntity entity) {
        return "";
    }
}