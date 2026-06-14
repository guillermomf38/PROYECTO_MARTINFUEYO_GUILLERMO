# PROYECTO_MARTINFUEYO_GUILLERMO
Autor: Guillermo Martín Fueyo
Dentro de la carpeta del proyecto Spring Boot se encuentra la carpeta `instalador/` que contiene el fichero ejecutable generado con Inno Setup. Al ejecutarlo se instala la aplicación en el equipo con acceso directo en el escritorio y el menú Inicio.

## Requisitos para ejecutar la aplicación

- Java 17 o superior instalado en el equipo.
- XAMPP con MySQL activo y la base de datos `proyectoguillermo` creada.
- Credenciales de acceso al administrador configuradas en `application.properties`.

## Componente personalizado
Se ha desarrollado el componente `MensajeLabel` como proyecto Maven independiente. Extiende la clase Label de JavaFX y está integrado en las pantallas de Gestionar Tutores y Gestionar Empresas.

