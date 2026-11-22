package com.empresa.tareas.infrastructure.adapter.in.rest;

import com.empresa.tareas.application.TaskCommand;
import com.empresa.tareas.application.port.in.TaskUseCase;
import com.empresa.tareas.domain.Task;
import com.empresa.tareas.domain.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Expone las operaciones de tareas por HTTP y las delega al puerto de entrada.
 */
@RestController
@RequestMapping(TaskController.TASKS_PATH)
public class TaskController {
    static final String TASKS_PATH = "/api/v1/tasks";

    private final TaskUseCase useCase;

    /**
     * Recibe el caso de uso que procesa las operaciones de tareas.
     *
     * @param useCase puerto de entrada de la aplicación
     */
    public TaskController(TaskUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     * Devuelve las tareas y permite filtrar el listado por estado.
     *
     * @param status estado opcional por el que se filtra
     * @return lista de tareas
     */
    @GetMapping
    public List<TaskResponse> list(@RequestParam(name = "status", required = false) TaskStatus status) {
        return useCase.list(status).stream().map(TaskResponse::from).toList();
    }

    /**
     * Devuelve la tarea que corresponde al identificador de la ruta.
     *
     * @param id identificador de la tarea
     * @return datos de la tarea encontrada
     */
    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable("id") Long id) {
        return TaskResponse.from(useCase.get(id));
    }

    /**
     * Valida la solicitud y crea una tarea en estado pendiente.
     *
     * @param request datos de la tarea nueva
     * @return respuesta 201 con la tarea creada y su ubicación
     */
    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        Task created = useCase.create(new TaskCommand(
                request.title(), request.description(), request.dueDate(), TaskStatus.PENDING));
        return ResponseEntity.created(URI.create(TASKS_PATH + "/" + created.id()))
                .body(TaskResponse.from(created));
    }

    /**
     * Valida los datos y actualiza la tarea indicada.
     *
     * @param id identificador de la tarea
     * @param request nuevos datos de la tarea
     * @return datos de la tarea actualizada
     */
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable("id") Long id, @Valid @RequestBody TaskRequest request) {
        Task updated = useCase.update(id, new TaskCommand(
                request.title(), request.description(), request.dueDate(), request.status()));
        return TaskResponse.from(updated);
    }

    /**
     * Elimina la tarea indicada y devuelve 204 sin cuerpo.
     *
     * @param id identificador de la tarea
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        useCase.delete(id);
    }
}
