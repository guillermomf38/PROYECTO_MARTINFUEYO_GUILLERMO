/**
 *Clase EmpresaService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Empresa
 *@see EmpresaRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link Empresa}.
 *
 * <p>Lo usan dos controllers distintos:
 * <ul>
 *   <li>{@code GestionarEmpresasController}</li>
 *   <li>{@code GestionarTutorEmpresaController} para obtener
 *       la lista de empresas disponibles al crear un tutor de empresa.</li>
 * </ul>
 * </p>
 *
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.repositorios.EmpresaRepository;

@Service
public class EmpresaService {
	@Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Guarda una nueva empresa en la base de datos.
     *
     * @param empresa entidad a persistir
     * @return entidad guardada con el id generado
     */
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * Actualiza los datos de una empresa existente.
     *
     * @param empresa entidad con los nuevos datos
     * @return entidad actualizada
     */
    public Empresa update(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * Elimina una empresa por su id.
     *
     * @param id identificador de la empresa a eliminar
     */
    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }

    /**
     * Busca una empresa por su id.
     *
     * @param id identificador a buscar
     * @return la empresa o {@code null} si no existe
     */
    public Empresa findById(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    /**
     * Devuelve todas las empresas del sistema.
     *
     * @return lista de todas las empresas
     */
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    /**
     * Comprueba si ya existe una empresa con el email indicado.
     *
     * @param email email a comprobar
     * @return {@code true} si el email ya está en uso
     */
    public boolean existeEmail(String email) {
        return empresaRepository.findByEmail(email).isPresent();
    }
}
