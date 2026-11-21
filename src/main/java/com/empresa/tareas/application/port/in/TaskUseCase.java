package com.empresa.tareas.application.port.in;

import com.empresa.tareas.application.TaskCommand;
import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskNotFoundException;
import com.empresa.tareas.domain.TaskStatus;

import java.util.List;

/**
 * Puerto de entrada que define las operaciones de tareas disponibles para los adaptadores.
 */
public interface TaskUseCase {
    /**
     * Lista las tareas y permite filtrarlas por estado.
     *
     * @param status estado buscado. Si es nulo, se consultan todas las tareas
     * @return tareas que coinciden con el filtro
     */
    List<Task> list(TaskStatus status);

    /**
     * Consulta una tarea por su identificador.
     *
     * @param id identificador de la tarea
     * @return tarea encontrada
     * @throws TaskNotFoundException si la tarea no existe
     */
    Task get(Long id);

    /**
     * Crea una tarea en estado pendiente.
     *
     * @param command datos de la tarea nueva
     * @return tarea guardada con su identificador asignado
     */
    Task create(TaskCommand command);

    /**
     * Actualiza los detalles y el estado de una tarea existente.
     *
     * @param id identificador de la tarea
     * @param command datos actualizados
     * @return tarea guardada con los cambios
     * @throws TaskNotFoundException si la tarea no existe
     */
    Task update(Long id, TaskCommand command);

    /**
     * Elimina una tarea existente.
     *
     * @param id identificador de la tarea
     * @throws TaskNotFoundException si la tarea no existe
     */
    void delete(Long id);
}
