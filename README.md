# API de tareas

Proyecto del Reto Práctico 1. Usa Java 21, Spring Boot 4.0.10, Gradle y una base de datos H2 en memoria.

## Requisitos

- Un JDK 21. Configura `JAVA_HOME` con la ruta al JDK y agrega su carpeta `bin` al `PATH` de tu terminal.
- Conexión a Internet en la primera ejecución para que el Gradle Wrapper descargue Gradle y las dependencias.

## Arranque en Windows

Ejecuta estos comandos en PowerShell desde la raíz del proyecto.

```powershell
java -version
.\gradlew.bat bootRun
```

Si tienes varios JDK, puedes seleccionar el 21 para la sesión actual antes de ejecutar los comandos anteriores.

```powershell
$env:JAVA_HOME = 'C:\ruta\al\jdk-21'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

La aplicación escucha en `http://localhost:8080`. Para detenerla, pulsa `Ctrl+C`.

En Linux o macOS, ejecuta `./gradlew bootRun`.

H2 se inicia con la aplicación. Las tareas se guardan en memoria y se pierden al detener el proceso.

## Operaciones REST

| Método | Ruta | Respuesta correcta |
| --- | --- | --- |
| `GET` | `/api/v1/tasks` | `200`, con filtro opcional `?status=PENDING` |
| `GET` | `/api/v1/tasks/{id}` | `200` |
| `POST` | `/api/v1/tasks` | `201` con el encabezado `Location` |
| `PUT` | `/api/v1/tasks/{id}` | `200` |
| `DELETE` | `/api/v1/tasks/{id}` | `204` sin cuerpo |

El título es obligatorio y admite hasta 120 caracteres. La descripción es opcional y admite hasta 500. Usa el formato `AAAA-MM-DD` para `dueDate`. Las tareas se crean en estado `PENDING`. Al actualizar, puedes indicar `PENDING`, `IN_PROGRESS` o `DONE`. Si omites el estado en el `PUT`, se usa `PENDING`.

### Ejemplo en PowerShell

Con la aplicación en ejecución, abre otra terminal. El ejemplo crea su propia tarea y usa el identificador que devuelve la API.

```powershell
$api = 'http://localhost:8080/api/v1/tasks'
$task = Invoke-RestMethod -Method Post -Uri $api -ContentType 'application/json' `
    -Body (@{ title = 'Preparar informe'; dueDate = '2026-12-01' } | ConvertTo-Json)
$taskUrl = "$api/$($task.id)"

Invoke-RestMethod -Uri "${api}?status=PENDING"
Invoke-RestMethod -Uri $taskUrl
Invoke-RestMethod -Method Put -Uri $taskUrl -ContentType 'application/json' `
    -Body (@{ title = 'Revisar informe'; status = 'DONE' } | ConvertTo-Json)
Invoke-RestMethod -Method Delete -Uri $taskUrl
```

Los datos inválidos devuelven `400` y una tarea inexistente devuelve `404`. Ambos errores usan `application/problem+json`.

## Compilación y pruebas

```powershell
.\gradlew.bat test
.\gradlew.bat build
```

En Linux o macOS, usa `./gradlew test` y `./gradlew build`. Gradle genera el informe de pruebas en `build/reports/tests/test/index.html`. Las dos pruebas usan JUnit Jupiter 6, gestionado por Spring Boot y requerido por la extensión de Spring Framework 7 que usa `@WebMvcTest`.
