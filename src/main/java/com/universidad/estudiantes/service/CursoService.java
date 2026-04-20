package com.universidad.estudiantes.service;

import com.universidad.estudiantes.model.Curso;
import com.universidad.estudiantes.model.Estudiante;
import com.universidad.estudiantes.repository.CursoRepository;
import com.universidad.estudiantes.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Curso con gestión de relación bidireccional ManyToMany.
 * Usa helper methods de la entidad para sincronizar ambos lados.
 */
@Service
public class CursoService {

    private final CursoRepository cursoRepo;
    private final EstudianteRepository estudianteRepo;

    public CursoService(CursoRepository cursoRepo, EstudianteRepository estudianteRepo) {
        this.cursoRepo = cursoRepo;
        this.estudianteRepo = estudianteRepo;
    }

    /** Retorna todos los cursos con sus estudiantes cargados (JOIN FETCH). */
    public List<Curso> listarTodos() {
        return cursoRepo.findAllConEstudiantes();
    }

    /** Busca curso por ID con estudiantes cargados, lanza excepción si no existe. */
    public Curso buscarPorId(Long id) {
        return cursoRepo.findByIdConEstudiantes(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + id));
    }

    /** Crea o actualiza un curso. */
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepo.save(curso);
    }

    /** Elimina un curso por ID. */
    @Transactional
    public void eliminar(Long id) {
        cursoRepo.deleteById(id);
    }

    /**
     * Inscribe un estudiante en un curso.
     * Usa el helper method agregarEstudiante() para sincronizar ambos lados.
     */
    @Transactional
    public void inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = buscarPorId(cursoId);
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + estudianteId));
        curso.agregarEstudiante(estudiante); // sincroniza ambos lados
        cursoRepo.save(curso);
    }

    /**
     * Desinscribe un estudiante de un curso.
     * Usa el helper method quitarEstudiante() para sincronizar ambos lados.
     */
    @Transactional
    public void desinscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = buscarPorId(cursoId);
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + estudianteId));
        curso.quitarEstudiante(estudiante); // sincroniza ambos lados
        cursoRepo.save(curso);
    }
}
