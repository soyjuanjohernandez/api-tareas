package com.empresa.tareas.domain;

/**
 * Estados que puede tener una tarea.
 */
public enum TaskStatus {
    /** La tarea está por iniciar. */
    PENDING,
    /** La tarea está en curso. */
    IN_PROGRESS,
    /** La tarea está terminada. */
    DONE
}
