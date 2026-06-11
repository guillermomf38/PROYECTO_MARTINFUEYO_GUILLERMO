/**
 *Clase FormacionEmpresaRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresa
 *Repositorio JPA para la entidad {@link FormacionEmpresa}.
 */

package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;

@Repository
public interface FormacionEmpresaRepository
		extends JpaRepository<FormacionEmpresa, Long> {
	
	@Query("SELECT f FROM FormacionEmpresa f WHERE f.tutorEmpresa.idUsuario = :id AND f.activa = true")
	List<FormacionEmpresa> findActivasByTutorId(@Param("id") Long id);

	@Query("SELECT f FROM FormacionEmpresa f WHERE f.estudiante.idUsuario = :id AND f.activa = true")
	Optional<FormacionEmpresa> findActivaByEstudianteId(@Param("id") Long id);
	
	
	/**
	 * Devuelve todas las formaciones de un estudiante concreto.
	 *
	 * @param estudiante estudiante del que se quieren las formaciones
	 * @return lista de formaciones del estudiante
	 */
	List<FormacionEmpresa> findByEstudiante(Estudiante estudiante);

	/**
	 * Devuelve todas las formaciones supervisadas por un tutor de empresa.
	 *
	 * @param tutorEmpresa tutor de empresa del que se quieren las formaciones
	 * @return lista de formaciones del tutor
	 */
	List<FormacionEmpresa> findByTutorEmpresa(TutorEmpresa tutorEmpresa);

	/**
	 * Devuelve la formación activa de un estudiante concreto. Un estudiante
	 * solo puede tener una formación activa simultáneamente.
	 *
	 * @param estudiante estudiante del que se busca la formación activa
	 * @return {@code Optional} con la formación activa si existe
	 */
	Optional<FormacionEmpresa> findByEstudianteAndActivaTrue(
			Estudiante estudiante);

	/**
	 * Devuelve todas las formaciones activas del sistema.
	 * 
	 * @return lista de formaciones activas
	 */
	List<FormacionEmpresa> findByActivaTrue();

	/**
	 * Devuelve las formaciones activas supervisadas por un tutor de empresa.
	 *
	 * @param tutorEmpresa tutor de empresa del que se quieren las formaciones
	 *                     activas
	 * @return lista de formaciones activas del tutor
	 */
	List<FormacionEmpresa> findByTutorEmpresaAndActivaTrue(
			TutorEmpresa tutorEmpresa);
}
