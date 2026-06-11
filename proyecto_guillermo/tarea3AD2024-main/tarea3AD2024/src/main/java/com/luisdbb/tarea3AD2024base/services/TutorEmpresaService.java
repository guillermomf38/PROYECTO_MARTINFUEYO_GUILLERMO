/**
 *Clase TutorEmpresaService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see TutorEmpresa
 *@see TutorEmpresaRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link TutorEmpresa}.
 *
 * <p>Lo usan varios controllers:
 * <ul>
 *   <li>{@code GestionarTutorEmpresaController}</li>
 *   <li>{@code AsignacionController} obtiene la lista de tutores
 *       de una empresa concreta para el ComboBox de asignación.</li>
 * </ul>
 * </p>
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.repositorios.TutorEmpresaRepository;

@Service
public class TutorEmpresaService {
	 @Autowired
	    private TutorEmpresaRepository tutorEmpresaRepository;

	    /**
	     * Guarda un nuevo tutor de empresa.
	     *
	     * @param tutorEmpresa entidad a persistir
	     * @return entidad guardada con el id generado
	     */
	    public TutorEmpresa save(TutorEmpresa tutorEmpresa) {
	        return tutorEmpresaRepository.save(tutorEmpresa);
	    }

	    /**
	     * Actualiza los datos de un tutor de empresa existente.
	     *
	     * @param tutorEmpresa entidad con los nuevos datos
	     * @return entidad actualizada
	     */
	    public TutorEmpresa update(TutorEmpresa tutorEmpresa) {
	        return tutorEmpresaRepository.save(tutorEmpresa);
	    }

	    /**
	     * Elimina un tutor de empresa por su id.
	     *
	     * @param id identificador del tutor a eliminar
	     */
	    public void delete(Long id) {
	        tutorEmpresaRepository.deleteById(id);
	    }

	    /**
	     * Busca un tutor de empresa por su id.
	     *
	     * @param id identificador a buscar
	     * @return el tutor o {@code null} si no existe
	     */
	    public TutorEmpresa findById(Long id) {
	        return tutorEmpresaRepository.findById(id).orElse(null);
	    }

	    /**
	     * Devuelve todos los tutores de empresa del sistema.
	     *
	     * @return lista de todos los tutores de empresa
	     */
	    public List<TutorEmpresa> findAll() {
	        return tutorEmpresaRepository.findAll();
	    }

	    /**
	     * Devuelve los tutores de empresa de una empresa concreta.
	     *
	     * @param empresa empresa de la que se quieren los tutores
	     * @return lista de tutores de esa empresa
	     */
	    public List<TutorEmpresa> findByEmpresa(Empresa empresa) {
	        return tutorEmpresaRepository.findByEmpresa(empresa);
	    }

	    /**
	     * Comprueba si ya existe un tutor de empresa con el email indicado.
	     *
	     * @param email email a comprobar
	     * @return {@code true} si el email ya está en uso
	     */
	    public boolean existeEmail(String email) {
	        return tutorEmpresaRepository.findByEmail(email).isPresent();
	    }

	    /**
	     * Comprueba si ya existe un usuario con el nombre de usuario indicado.
	     *
	     * @param usuario nombre de usuario a comprobar
	     * @return {@code true} si el nombre de usuario ya está en uso
	     */
	    public boolean existeUsuario(String usuario) {
	        return tutorEmpresaRepository.findByUsuario(usuario).isPresent();
	    }
}
