# TPI

Este proyecto es parte del Trabajo Práctico Integrador (TPI) realizado durante el año 2024, de las asignaturas Laboratorio de Computación IV, Programación IV y Metodología de Sistemas de la carrera "Tecnicatura Universitaria en Programación" de la "Universidad Tecnológica Nacional - Facultad Regional Córdoba".

## Tabla de Contenidos
- [Introducción](#introducción)
- [Configuración del Proyecto](#configuración-del-proyecto)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Documentación de la API](#documentación-de-la-api)
- [Endpoints](#endpoints)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Contribuir](#contribuir)

## Introducción
Este microservicio permite gestionar denuncias mediante la creación, actualización y obtención de información. Está diseñado para ser parte de una arquitectura más grande y proporciona funcionalidades para manejar archivos y estados de denuncias.

## Configuración del Proyecto
La configuración principal del proyecto se encuentra en el archivo `application.properties`. Asegúrate de ajustar las propiedades de la base de datos y el puerto según tus necesidades.

## Instalación y Ejecución
1. Clona este repositorio:
   ````bash
   git clone https://github.com/LCIV-2024/2W1-TPI-complaints.git
2. Accede al directorio del proyecto
   ````bash
   cd 2W1-TPI-Complaints
3. Configura las propiedades de la base de datos en `application.properties`.
4. Ejecuta el proyecto desde el IDE o editor de preferencia.
## Documentación de la API
La API está documentada con Swagger. Una vez que el servicio esté en funcionamiento, puedes acceder a la documentación en:

	http://localhost:8040/swagger-ui.html


## Endpoints
A continuación, se describen los endpoints disponibles:

-   **POST /**: Crear una nueva denuncia.
-   **GET /types**: Obtener los tipos de denuncias disponibles.
-   **GET /states**: Obtener los estados de las denuncias.
-   **GET /all**: Obtener una lista reducida de todas las denuncias.
-   **GET /state/{state}**: Obtener denuncias filtradas por estado.
-   **GET /{id}**: Obtener detalles de una denuncia por su ID.
-   **PUT /{id}**: Actualizar el estado de una denuncia.
-   **GET /dates**: Obtener denuncias en un rango de fechas. El formato de las fechas es `YYYY-MM-DDTHH:mm:ss`.
-   **POST /saveFile**: Guardar un archivo asociado a una denuncia.
-   **GET /getFile/{uuid}**: Obtener un archivo usando su UUID.
-   **GET /getFiles/{complaintId}**: Obtener archivos relacionados con una denuncia por su ID.
-   **GET /report/{id}**: Obtener denuncias asociadas a un informe.
-   **PUT /append**: Asociar denuncias a un informe.
-   **PUT /state/pending/{id}**: Actualizar el estado de una denuncia a "pendiente".
## Tecnologías utilizadas
-   **Java 17**
-   **Spring Boot 3.1.3**
-   **H2 Database** (para desarrollo)
- **MySQL**
-   **Swagger y Springdoc OpenAPI** para la documentación
-   **Lombok** para simplificar el código de los POJOs
-   **ModelMapper** para la conversión de objetos
