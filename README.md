# Cursos y Estudiantes — ManyToMany con JPA/Hibernate

Extensión del proyecto Post-Contenido 1 U8. Implementa la relación **ManyToMany bidireccional** entre `Curso` y `Estudiante` usando Spring Data JPA, con tabla de unión `curso_estudiante`, métodos helper para sincronización y consultas `JOIN FETCH` para evitar el problema N+1.

**Estudiante:** Mauricio Calderón  
**Universidad de Santander (UDES)**  
**Programación Web — Unidad 8 / Post-Contenido 2**

---

## Diagrama ER de la Relación ManyToMany

```
+-------------+         +------------------+         +--------------+
|  estudiantes|         |  curso_estudiante|         |    cursos    |
|-------------|         |------------------|         |--------------|
| id (PK)     |<------->| estudiante_id FK |         | id (PK)      |
| nombre      |         | curso_id      FK |<------->| nombre       |
| apellido    |         +------------------+         | creditos     |
| correo      |                                      +--------------+
| carrera     |
+-------------+
```

- Un estudiante puede estar en **muchos cursos**
- Un curso puede tener **muchos estudiantes**
- Hibernate genera automáticamente la tabla `curso_estudiante`

---

## Estructura del Proyecto

```
cursos-estudiantes/
├── src/main/java/com/universidad/estudiantes/
│   ├── EstudiantesApplication.java
│   ├── model/
│   │   ├── Estudiante.java       # Lado INVERSO @ManyToMany(mappedBy="estudiantes")
│   │   └── Curso.java            # Lado PROPIETARIO @JoinTable + helper methods
│   ├── repository/
│   │   ├── EstudianteRepository.java
│   │   └── CursoRepository.java  # JOIN FETCH para evitar N+1
│   ├── service/
│   │   ├── EstudianteService.java
│   │   └── CursoService.java     # inscribirEstudiante / desinscribirEstudiante
│   └── controller/
│       ├── EstudianteController.java
│       └── CursoController.java  # Rutas /cursos + inscripciones
└── src/main/resources/
    ├── application.properties
    └── templates/
        ├── estudiantes/ (lista, formulario, confirmar-eliminar)
        └── cursos/      (lista, formulario, inscribir)
```

---

## Configuración de MySQL

```sql
-- Reutiliza la BD del Post-Contenido 1
CREATE DATABASE IF NOT EXISTS estudiantesdb CHARACTER SET utf8mb4;
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'apppass';
GRANT ALL PRIVILEGES ON estudiantesdb.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
```

> Hibernate crea automáticamente las tablas `cursos` y `curso_estudiante` al arrancar.

---

## Cómo Ejecutar

```bash
git clone https://github.com/maocalderon/Calderon-post2-u8.git
cd Calderon-post2-u8
mvn spring-boot:run
```

- Estudiantes: http://localhost:8080/estudiantes
- Cursos: http://localhost:8080/cursos

---

## Endpoints Principales

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/cursos` | Lista cursos con estudiantes inscritos |
| GET | `/cursos/nuevo` | Formulario nuevo curso |
| POST | `/cursos/guardar` | Guarda el curso |
| GET | `/cursos/{id}/inscribir` | Vista para inscribir estudiante |
| POST | `/cursos/{cId}/inscribir/{eId}` | Inscribe estudiante en curso |
| POST | `/cursos/{cId}/desinscribir/{eId}` | Desinscribe estudiante |

---

## Verificar en MySQL

```sql
USE estudiantesdb;
SHOW TABLES;                    -- cursos, curso_estudiante, estudiantes
DESCRIBE curso_estudiante;      -- curso_id, estudiante_id
SELECT * FROM curso_estudiante; -- filas de inscripción
```

---

## Capturas de Pantalla
![alt text](image.png)
![alt text](image-1.png)
![alt text](image-2.png)

---

## Tecnologías

- Spring Boot 3.2.5 · Spring Data JPA · Hibernate · MySQL 8 · Thymeleaf · Java 17
