package com.empresa.tareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la API de tareas.
 */
@SpringBootApplication
public class TareasApplication {

	/**
	 * Inicia Spring Boot y el servidor web de la aplicación.
	 *
	 * @param args argumentos de línea de comandos que recibe Spring Boot
	 */
	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

}
