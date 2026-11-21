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
