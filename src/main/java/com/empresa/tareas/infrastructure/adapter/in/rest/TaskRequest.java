package com.empresa.tareas.infrastructure.adapter.in.rest;

import com.empresa.tareas.domain.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Datos JSON que acepta la API al crear o actualizar una tarea.
 *
 * @param title título obligatorio de hasta 120 caracteres
 * @param description descripción opcional de hasta 500 caracteres
 * @param dueDate fecha límite opcional
 * @param status estado solicitado para actualizar la tarea
 */
public record TaskRequest(
        @NotBlank(message = "obligatorio") @Size(max = 120) String title,
        @Size(max = 500) String description,
        LocalDate dueDate,
        TaskStatus status
) {
}
