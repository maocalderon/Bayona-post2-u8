package com.universidad.estudiantes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA Curso.
 * Lado PROPIETARIO de la relación ManyToMany.
 * Define la tabla de unión "curso_estudiante" con @JoinTable.
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "creditos")
    private int creditos;

    // Lado PROPIETARIO — define la tabla de unión curso_estudiante
    @ManyToMany
    @JoinTable(
        name = "curso_estudiante",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private Set<Estudiante> estudiantes = new HashSet<>();

    // Constructor vacío requerido por JPA
    public Curso() {}

    public Curso(String nombre, int creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
    }

    /**
     * Helper method: agrega estudiante y sincroniza ambos lados de la relación.
     */
    public void agregarEstudiante(Estudiante e) {
        this.estudiantes.add(e);
        e.getCursos().add(this);
    }

    /**
     * Helper method: quita estudiante y sincroniza ambos lados de la relación.
     */
    public void quitarEstudiante(Estudiante e) {
        this.estudiantes.remove(e);
        e.getCursos().remove(this);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public Set<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Set<Estudiante> estudiantes) { this.estudiantes = estudiantes; }
}
