package com.github.nenadjakic.eav.entity.security

/**
 * Enum class representing actions that can be performed.
 *
 * Each action has a description explaining what it allows or represents.
 *
 * @property description A description of the action.
 */
enum class Action(private val description: String) {

    /**
     * Represents no action.
     */
    NONE(""),

    /**
     * Allows reading action.
     */
    READ("Read action is allowed."),

    /**
     * Allows creating action.
     */
    CREATE("Create action is allowed."),

    /**
     * Allows modifying action.
     */
    UPDATE("Modify action is allowed."),

    /**
     * Allows deleting action.
     */
    DELETE("Delete action is allowed.")
}