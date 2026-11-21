package com.empresa.tareas.domain;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Representa una tarea inmutable. Cada cambio devuelve una nueva instancia.
 *
 * @param id identificador asignado al guardar la tarea
 * @param title título obligatorio de la tarea
 * @param description descripción opcional
 * @param status estado actual de la tarea
 * @param dueDate fecha límite opcional
 * @param createdAt instante en que se creó la tarea
 */
public record Task(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        Instant createdAt
) {
    /**
     * Valida el título y asigna un estado pendiente y el instante actual
     * cuando esos valores no se proporcionan.
     *
     * @throws IllegalArgumentException si el título es nulo o está en blanco
     */
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("el título es obligatorio");
        }
        status = status == null ? TaskStatus.PENDING : status;
        createdAt = createdAt == null ? Instant.now() : createdAt;
    }

    /**
     * Crea una tarea pendiente con el instante actual y sin identificador.
     *
     * @param title título obligatorio
     * @param description descripción opcional
     * @param dueDate fecha límite opcional
     * @return tarea nueva
     */
    public static Task create(String title, String description, LocalDate dueDate) {
        return new Task(null, title, description, TaskStatus.PENDING, dueDate, null);
    }

    /**
     * Copia la tarea con el identificador indicado.
     *
     * @param newId identificador que se asignará a la copia
     * @return tarea con el identificador indicado y los demás datos de la original
     */
    public Task withId(Long newId) {
        return new Task(newId, title, description, status, dueDate, createdAt);
    }

    /**
     * Copia la tarea con nuevos detalles y conserva su identificador, estado e instante de creación.
     *
     * @param newTitle nuevo título obligatorio
     * @param newDescription nueva descripción opcional
     * @param newDueDate nueva fecha límite opcional
     * @return tarea con los detalles actualizados
     */
    public Task withDetails(String newTitle, String newDescription, LocalDate newDueDate) {
        return new Task(id, newTitle, newDescription, status, newDueDate, createdAt);
    }

    /**
     * Copia la tarea con el estado indicado y conserva los demás datos.
     *
     * @param newStatus nuevo estado. Si es nulo, se usa {@link TaskStatus#PENDING}
     * @return tarea con el estado actualizado
     */
    public Task withStatus(TaskStatus newStatus) {
        return new Task(id, title, description, newStatus, dueDate, createdAt);
    }
}
