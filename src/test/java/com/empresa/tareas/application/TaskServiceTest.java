package com.empresa.tareas.application;

import com.empresa.tareas.application.port.out.TaskRepositoryPort;
import com.empresa.tareas.domain.TaskNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Prueba el caso de uso con un repositorio simulado.
 */
class TaskServiceTest {
    private final TaskRepositoryPort repository = mock(TaskRepositoryPort.class);
    private final TaskService service = new TaskService(repository);

    /**
     * Comprueba que la excepción conserva el identificador de la tarea inexistente.
     */
    @Test
    void buscarUnaTareaInexistenteLanzaUnaExcepcion() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(id))
                .isInstanceOfSatisfying(TaskNotFoundException.class,
                        exception -> assertThat(exception.getId()).isEqualTo(id));
    }
}
