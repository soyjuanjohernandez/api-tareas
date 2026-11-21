package com.empresa.tareas.application.port.out;

import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskStatus;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida que define las operaciones de persistencia que necesita la aplicación.
 */
public interface TaskRepositoryPort {
    /**
     * Guarda una tarea nueva o actualiza una existente.
     *
     * @param task tarea que se va a guardar
     * @return tarea guardada con su identificador
     */
    Task save(Task task);

    /**
     * Busca una tarea por su identificador.
     *
     * @param id identificador de la tarea
     * @return tarea encontrada o un valor vacío si no existe
     */
    Optional<Task> findById(Long id);

    /**
     * Recupera todas las tareas.
     *
     * @return lista de tareas guardadas
     */
    List<Task> findAll();

    /**
     * Recupera las tareas que tienen el estado indicado.
     *
     * @param status estado que se quiere consultar
     * @return tareas que tienen ese estado
     */
    List<Task> findByStatus(TaskStatus status);

    /**
     * Elimina una tarea por su identificador.
     *
     * @param id identificador de la tarea que se va a eliminar
     */
    void deleteById(Long id);
}
