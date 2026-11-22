package com.empresa.tareas.infrastructure.adapter.in.rest;

import com.empresa.tareas.domain.Task;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Datos que la API devuelve al consultar, crear o actualizar una tarea.
 *
 * @param id identificador de la tarea
 * @param title título de la tarea
 * @param description descripción de la tarea
 * @param status nombre del estado actual
 * @param dueDate fecha límite de la tarea
 * @param createdAt instante de creación
 */
public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        LocalDate dueDate,
        Instant createdAt
) {
    /**
     * Convierte una tarea de dominio en la representación que devuelve la API.
     *
     * @param task tarea de dominio
     * @return datos de la tarea para la respuesta HTTP
     */
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.id(), task.title(), task.description(), task.status().name(),
                task.dueDate(), task.createdAt());
    }
}
