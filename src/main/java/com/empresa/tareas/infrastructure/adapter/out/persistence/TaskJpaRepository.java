package com.empresa.tareas.infrastructure.adapter.out.persistence;

import com.empresa.tareas.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de Spring Data JPA para las entidades de la tabla tasks.
 */
public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, Long> {
    /**
     * Recupera las entidades que tienen el estado indicado.
     *
     * @param status estado que se va a consultar
     * @return entidades con ese estado
     */
    List<TaskJpaEntity> findByStatus(TaskStatus status);
}
