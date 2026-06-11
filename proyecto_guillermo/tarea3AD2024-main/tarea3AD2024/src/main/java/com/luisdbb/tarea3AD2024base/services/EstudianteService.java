/**
 *Clase EstudianteService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Estudiante
 *@see EstudianteRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link Estudiante}.
 *<p>Se comparte entre dos controllers distintos:
 * <ul>
 *   <li>{@code GestionarEstudiantesAdminController} — usa {@link #findAll()}
 *       para ver y gestionar todos los estudiantes del sistema.</li>
 *   <li>{@code GestionarEstudiantesProfeController} — usa
 *       {@link #findByProfesorado(Profesorado)} para obtener solo
 *       los estudiantes del tutor de centro logueado.</li>
 * </ul>
 * </p>
 *
 * <p>Al guardar un {@code Estudiante} (que hereda de {@code Usuario}),
 * JPA con la estrategia {@code JOINED} inserta automáticamente una fila
 * en la tabla {@code Usuario} y otra en la tabla {@code Estudiante}.</p>
 *
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;

import com.luisdbb.tarea3AD2024base.repositorios.EstudianteRepository;

@Service
public class EstudianteService {
	 @Autowired
	    private EstudianteRepository estudianteRepository;

	    /**
	     * Guarda un nuevo estudiante en la base de datos.
	     *
	     * @param estudiante entidad a persistir
	     * @return entidad guardada con el id generado
	     */
	    public Estudiante save(Estudiante estudiante) {
	        return estudianteRepository.save(estudiante);
	    }

	    /**
	     * Actualiza los datos de un estudiante existente.
	     *
	     * @param estudiante entidad con los nuevos datos
	     * @return entidad actualizada
	     */
	    public Estudiante update(Estudiante estudiante) {
	        return estudianteRepository.save(estudiante);
	    }

	    /**
	     * Elimina un estudiante por su id..
	     *
	     * @param id identificador del estudiante a eliminar
	     */
	    public void delete(Long id) {
	        estudianteRepository.deleteById(id);
	    }

	    /**
	     * Busca un estudiante por su id.
	     *
	     * @param id identificador a buscar
	     * @return el estudiante o {@code null} si no existe
	     */
	    public Estudiante findById(Long id) {
	        return estudianteRepository.findById(id).orElse(null);
	    }

	    /**
	     * Devuelve todos los estudiantes del sistema.
	     *
	     * @return lista de todos los estudiantes
	     */
	    public List<Estudiante> findAll() {
	        return estudianteRepository.findAll();
	    }

	   
	    public List<Estudiante> findByProfesoradoId(Long idProfesorado) {
	        return estudianteRepository.findByProfesoradoId(idProfesorado);
	    }

	    /**
	     * Devuelve los estudiantes de un curso concreto.
	     *
	     * @param curso código del curso (ej: "2VIFC302")
	     * @return lista de estudiantes de ese curso
	     */
	    public List<Estudiante> findByCurso(String curso) {
	        return estudianteRepository.findByCurso(curso);
	    }
}
