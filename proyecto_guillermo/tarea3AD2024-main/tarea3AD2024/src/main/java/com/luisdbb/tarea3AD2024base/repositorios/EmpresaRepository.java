/**
 *Clase EmpresaRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Empresa
 *
 *Repositorio JPA para la entidad {@link Empresa}.
 *
 * <p>{@code Empresa} no hereda de {@code Usuario}, por lo que este
 * repositorio es directo.</p>
 *
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	 /**
     * Busca una empresa por su email.
     * Se usa en el service para validar que no haya emails duplicados
     * antes de guardar o modificar una empresa.
     *
     * @param email email de la empresa
     * @return {@code Optional} con la empresa si existe
     */
    Optional<Empresa> findByEmail(String email);
}
