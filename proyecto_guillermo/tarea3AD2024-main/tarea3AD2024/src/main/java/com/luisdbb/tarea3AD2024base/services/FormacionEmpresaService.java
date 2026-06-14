/**
 *Clase FormacionEmpresaService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresa
 *@see FormacionEmpresaRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link FormacionEmpresa}.
 */

package com.luisdbb.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.FormacionEmpresaRepository;

@Service
public class FormacionEmpresaService {
	@Autowired
	private FormacionEmpresaRepository formacionRepository;

	/**
	 * Guarda una nueva formación en empresa.
	 *
	 * @param formacion entidad a persistir
	 * @return entidad guardada con el id generado
	 */
	public FormacionEmpresa save(FormacionEmpresa formacion) {
		return formacionRepository.save(formacion);
	}

	/**
	 * Actualiza una formación existente.
	 *
	 * @param formacion entidad con los nuevos datos
	 * @return entidad actualizada
	 */
	public FormacionEmpresa update(FormacionEmpresa formacion) {
		return formacionRepository.save(formacion);
	}

	/**
	 * Busca una formación por su id.
	 *
	 * @param id identificador a buscar
	 * @return la formación o {@code null} si no existe
	 */
	public FormacionEmpresa findById(Long id) {
		return formacionRepository.findById(id).orElse(null);
	}

	/**
	 * Devuelve todas las formaciones del sistema.
	 *
	 * @return lista de todas las formaciones
	 */
	public List<FormacionEmpresa> findAll() {
		return formacionRepository.findAll();
	}

	/**
	 * Devuelve todas las formaciones de un estudiante.
	 *
	 * @param estudiante estudiante del que se quiere el historial
	 * @return lista de formaciones del estudiante
	 */
	public List<FormacionEmpresa> findByEstudiante(Estudiante estudiante) {
		return formacionRepository.findByEstudiante(estudiante);
	}

	/**
	 * Devuelve las formaciones activas supervisadas por un tutor de empresa.
	 * Filtra las inactivas para que el tutor solo vea sus estudiantes actuales.
	 *
	 * @param tutorEmpresa tutor de empresa logueado
	 * @return lista de formaciones activas del tutor
	 */
	public List<FormacionEmpresa> findActivasByTutor(
			TutorEmpresa tutorEmpresa) {
		return formacionRepository
				.findByTutorEmpresaAndActivaTrue(tutorEmpresa);
	}

	/**
	 * Devuelve la formación activa de un estudiante concreto. Un estudiante
	 * solo puede tener una formación activa simultáneamente.
	 *
	 * @param estudiante estudiante del que se busca la formación activa
	 * @return {@code Optional} con la formación activa si existe
	 */
	public Optional<FormacionEmpresa> findActivaByEstudiante(
			Estudiante estudiante) {
		return formacionRepository.findByEstudianteAndActivaTrue(estudiante);
	}

	/**
	 * Desactiva la formación activa de un estudiante. Pone el campo
	 * {@code activa} a {@code false} y guarda el cambio.
	 *
	 * @param estudiante estudiante cuya formación se quiere desactivar
	 */
	public void desactivarFormacion(Estudiante estudiante) {
		formacionRepository.findByEstudianteAndActivaTrue(estudiante)
				.ifPresent(f -> {
					f.setActiva(false);
					formacionRepository.save(f);
				});
	}
	
	
	public List<FormacionEmpresa> findActivasByTutorId(Long idTutor) {
	    return formacionRepository.findActivasByTutorId(idTutor);
	}

	public Optional<FormacionEmpresa> findActivaByEstudianteId(Long idEstudiante) {
	    return formacionRepository.findActivaByEstudianteId(idEstudiante);
	}
	
	public List<FormacionEmpresa> findByActivaTrue() {
	    return formacionRepository.findByActivaTrue();
	}
}
