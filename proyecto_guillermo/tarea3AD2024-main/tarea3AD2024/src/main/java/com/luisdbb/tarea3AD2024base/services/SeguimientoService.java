/**
 *Clase SeguimientoService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Seguimiento
 *@see SeguimientoRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link Seguimiento}.
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Seguimiento;
import com.luisdbb.tarea3AD2024base.repositorios.SeguimientoRepository;

@Service
public class SeguimientoService {
	 @Autowired
	    private SeguimientoRepository seguimientoRepository;

	    /**
	     * Guarda un nuevo seguimiento en la base de datos.
	     *
	     * @param seguimiento entidad a persistir
	     * @return entidad guardada con el id generado
	     */
	    public Seguimiento save(Seguimiento seguimiento) {
	        return seguimientoRepository.save(seguimiento);
	    }

	    /**
	     * Actualiza un seguimiento existente.
	     *
	     * @param seguimiento entidad con los nuevos datos
	     * @return entidad actualizada
	     */
	    public Seguimiento update(Seguimiento seguimiento) {
	        return seguimientoRepository.save(seguimiento);
	    }

	    /**
	     * Elimina un seguimiento por su id.
	     *
	     * @param id identificador del seguimiento a eliminar
	     */
	    public void delete(Long id) {
	        seguimientoRepository.deleteById(id);
	    }

	    /**
	     * Devuelve todos los seguimientos de una formación ordenados
	     * del más reciente al más antiguo.
	     *
	     * @param formacion formación de la que se quiere el historial
	     * @return lista de seguimientos ordenados por fecha descendente
	     */
	    public List<Seguimiento> findByFormacion(FormacionEmpresa formacion) {
	        return seguimientoRepository.findByFormacionOrderByFechaDesc(formacion);
	    }

	    /**
	     * Devuelve todos los seguimientos del sistema.
	     *
	     * @return lista de todos los seguimientos
	     */
	    public List<Seguimiento> findAll() {
	        return seguimientoRepository.findAll();
	    }
}
