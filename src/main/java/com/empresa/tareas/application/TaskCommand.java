package com.empresa.tareas.application;

import com.empresa.tareas.domain.TaskStatus;

import java.time.LocalDate;

/**
 * Agrupa los datos que recibe la aplicación al crear o actualizar una tarea.
 *
 * @param title título obligatorio de la tarea
 * @param description descripción opcional
 * @param dueDate fecha límite opcional
 * @param status estado solicitado para la actualización. La creación empieza en pendiente
 */
public record TaskCommand(String title, String description, LocalDate dueDate, TaskStatus status) {
}
