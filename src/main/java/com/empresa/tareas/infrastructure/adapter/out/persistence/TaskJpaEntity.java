package com.empresa.tareas.infrastructure.adapter.out.persistence;

import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Representa una fila de la tabla tasks y convierte entre la entidad JPA y el modelo de dominio.
 */
@Entity
@Table(name = "tasks")
public class TaskJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status;

    private LocalDate dueDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Permite que JPA cree la entidad al leer una fila de la base de datos.
     */
    protected TaskJpaEntity() {
    }

    /**
     * Copia los datos del dominio a una entidad que JPA puede guardar.
     *
     * @param task tarea de dominio
     * @return entidad con los datos de la tarea
     */
    static TaskJpaEntity from(Task task) {
        TaskJpaEntity entity = new TaskJpaEntity();
        entity.id = task.id();
        entity.title = task.title();
        entity.description = task.description();
        entity.status = task.status();
        entity.dueDate = task.dueDate();
        entity.createdAt = task.createdAt();
        return entity;
    }

    /**
     * Reconstruye la tarea de dominio con los datos de la entidad.
     *
     * @return tarea de dominio
     */
    Task toDomain() {
        return new Task(id, title, description, status, dueDate, createdAt);
    }
}
