package com.empresa.tareas.infrastructure.adapter.in.rest;

import com.empresa.tareas.domain.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Convierte errores de la API de tareas en respuestas HTTP con formato ProblemDetail.
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Devuelve 404 e incluye el identificador de la tarea que no se encontró.
     *
     * @param exception excepción generada por el caso de uso
     * @return problema con el identificador buscado
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail notFound(TaskNotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("Tarea no encontrada");
        problem.setProperty("id", exception.getId());
        return problem;
    }

    /**
     * Devuelve 400 con los mensajes de validación agrupados por campo.
     *
     * @param exception excepción con los campos que no pasaron la validación
     * @return problema con los errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail invalid(MethodArgumentNotValidException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Datos inválidos");
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        problem.setProperty("errores", errors);
        return problem;
    }

    /**
     * Devuelve 400 si el cuerpo está vacío o no se puede leer como una solicitud de tarea.
     *
     * @return problema que explica el formato esperado
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail unreadableBody() {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "El cuerpo debe contener un JSON válido con los tipos de datos esperados.");
        problem.setTitle("Cuerpo de solicitud inválido");
        return problem;
    }

    /**
     * Devuelve 400 si un parámetro de la ruta o de la consulta tiene un valor inválido.
     *
     * @param exception excepción con el nombre del parámetro
     * @return problema que identifica el parámetro inválido
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail invalidParameter(MethodArgumentTypeMismatchException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Parámetro inválido");
        problem.setProperty("errores", Map.of(exception.getName(), "valor inválido"));
        return problem;
    }
}
