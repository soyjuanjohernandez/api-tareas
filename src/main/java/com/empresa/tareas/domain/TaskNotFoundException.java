package com.empresa.tareas.domain;

/**
 * Indica que no se encontró una tarea con el identificador solicitado.
 */
public class TaskNotFoundException extends RuntimeException {
    private final Long id;

    /**
     * Crea la excepción con un mensaje que identifica la tarea buscada.
     *
     * @param id identificador de la tarea que no se encontró
     */
    public TaskNotFoundException(Long id) {
        super("No existe la tarea con id " + id);
        this.id = id;
    }

    /**
     * Devuelve el identificador que se usó para buscar la tarea.
     *
     * @return identificador de la tarea que no se encontró
     */
    public Long getId() {
        return id;
    }
}
