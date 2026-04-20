package com.universidad.estudiantes.repository;

import com.universidad.estudiantes.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA de Curso.
 * Usa JOIN FETCH en las consultas para cargar estudiantes en una sola query
 * y evitar el problema N+1 (múltiples consultas SELECT).
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // JOIN FETCH carga todos los cursos con sus estudiantes en una sola consulta
    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.estudiantes")
    List<Curso> findAllConEstudiantes();

    // Carga un curso específico con sus estudiantes inscritos
    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.estudiantes WHERE c.id = :id")
    Optional<Curso> findByIdConEstudiantes(Long id);
}
