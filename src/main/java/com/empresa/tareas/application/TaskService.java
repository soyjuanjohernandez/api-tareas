package com.empresa.tareas.application;

import com.empresa.tareas.application.port.in.TaskUseCase;
import com.empresa.tareas.application.port.out.TaskRepositoryPort;
import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskNotFoundException;
import com.empresa.tareas.domain.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ejecuta las operaciones de tareas y delega su almacenamiento al puerto de salida.
 */
@Service
public class TaskService implements TaskUseCase {
    private final TaskRepositoryPort repository;

    /**
     * Recibe el repositorio que usará el servicio para consultar y guardar tareas.
     *
     * @param repository implementación del puerto de persistencia
     */
    public TaskService(TaskRepositoryPort repository) {
        this.repository = repository;
    }

    /**
     * Consulta todas las tareas o aplica el filtro de estado cuando se indica.
     */
    @Override
    public List<Task> list(TaskStatus status) {
        return status == null ? repository.findAll() : repository.findByStatus(status);
    }

    /**
     * Busca la tarea y lanza la excepción de dominio si el repositorio no la encuentra.
     */
    @Override
    public Task get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * Usa el modelo de dominio para crear y guardar una tarea pendiente.
     */
    @Override
    public Task create(TaskCommand command) {
        Task task = Task.create(command.title(), command.description(), command.dueDate());
        return repository.save(task);
    }

    /**
     * Carga la tarea, aplica los cambios a una copia y guarda el resultado.
     */
    @Override
    public Task update(Long id, TaskCommand command) {
        Task updated = get(id)
                .withDetails(command.title(), command.description(), command.dueDate())
                .withStatus(command.status());
        return repository.save(updated);
    }

    /**
     * Comprueba que la tarea existe antes de solicitar su eliminación.
     */
    @Override
    public void delete(Long id) {
        get(id);
        repository.deleteById(id);
    }
}
