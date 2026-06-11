/**
 *Clase DocumentacionRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Documentacion
 *
 *Repositorio JPA para la entidad {@link Documentacion}.
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Documentacion;

@Repository
public interface DocumentacionRepository extends JpaRepository<Documentacion, Long>{
	
	/**
     * Devuelve todos los documentos asociados a una formación concreta.
     * Se usa para mostrar el historial de justificantes del estudiante.
     *
     * @param idFormacion id de la formación cuyos documentos se quieren
     * @return lista de documentos de esa formación
     */
    List<Documentacion> findByIdFormacion(Long idFormacion);

}
