package com.empresa.tareas.infrastructure.adapter.out.persistence;

import com.empresa.tareas.application.port.out.TaskRepositoryPort;
import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementa el puerto de salida con Spring Data JPA y devuelve tareas del dominio.
 */
@Repository
public class TaskPersistenceAdapter implements TaskRepositoryPort {
    private final TaskJpaRepository jpa;

    /**
     * Recibe el repositorio de Spring Data que ejecuta las operaciones en la base de datos.
     *
     * @param jpa repositorio asociado a la entidad de tareas
     */
    public TaskPersistenceAdapter(TaskJpaRepository jpa) {
        this.jpa = jpa;
    }

    /**
     * Convierte la tarea a una entidad, la guarda y devuelve el resultado como tarea de dominio.
     */
    @Override
    public Task save(Task task) {
        return jpa.save(TaskJpaEntity.from(task)).toDomain();
    }

    /**
     * Busca la entidad por identificador y la convierte si existe.
     */
    @Override
    public Optional<Task> findById(Long id) {
        return jpa.findById(id).map(TaskJpaEntity::toDomain);
    }

    /**
     * Recupera todas las entidades y las convierte a tareas de dominio.
     */
    @Override
    public List<Task> findAll() {
        return jpa.findAll().stream().map(TaskJpaEntity::toDomain).toList();
    }

    /**
     * Consulta por estado en la base de datos y convierte las entidades encontradas.
     */
    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return jpa.findByStatus(status).stream().map(TaskJpaEntity::toDomain).toList();
    }

    /**
     * Solicita a Spring Data JPA que elimine la entidad con ese identificador.
     */
    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
