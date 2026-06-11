/**
 *Clase SeguimientoRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Seguimiento
 *@see FormacionEmpresa
 *
 *Repositorio JPA para la entidad {@link Seguimiento}.
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Seguimiento;

@Repository
public interface SeguimientoRepository  extends JpaRepository<Seguimiento, Long> {
	/**
     * Devuelve todos los seguimientos de una formación ordenados por fecha
     * descendente .
     *
     * @param formacion formación de la que se quieren los seguimientos
     * @return lista de seguimientos ordenados de más reciente a más antiguo
     */
    List<Seguimiento> findByFormacionOrderByFechaDesc(FormacionEmpresa formacion);

    /**
     * Devuelve los seguimientos de una formación cuya valoración
     * contiene el texto indicado.
     * Permite filtrar solo faltas ("JUSTIFICADA","INJUSTIFICADA")
     * o solo calificaciones ("APTO","NO APTO","PENDIENTE").
     *
     * @param formacion  formación de la que se quieren los seguimientos
     * @param valoracion texto a buscar en el campo valoracion
     * @return lista de seguimientos filtrados
     */
    List<Seguimiento> findByFormacionAndValoracionContainingIgnoreCase(
            FormacionEmpresa formacion, String valoracion);
}
