package com.universidad.estudiantes.service;

import com.universidad.estudiantes.model.Estudiante;
import com.universidad.estudiantes.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio con lógica de negocio y manejo transaccional.
 * Actúa como capa intermedia entre el controlador y el repositorio.
 */
@Service
public class EstudianteService {

    private final EstudianteRepository repo;

    public EstudianteService(EstudianteRepository repo) {
        this.repo = repo;
    }

    /** Retorna todos los estudiantes registrados. */
    public List<Estudiante> listarTodos() {
        return repo.findAll();
    }

    /** Busca un estudiante por ID o lanza excepción si no existe. */
    public Estudiante buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + id));
    }

    /** Persiste un estudiante nuevo o actualiza uno existente. */
    @Transactional
    public Estudiante guardar(Estudiante estudiante) {
        return repo.save(estudiante);
    }

    /** Elimina un estudiante por su ID. */
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
