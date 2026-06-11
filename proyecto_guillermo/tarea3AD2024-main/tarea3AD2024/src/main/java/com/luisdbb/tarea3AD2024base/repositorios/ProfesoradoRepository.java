/**
 *Clase ProfesoradoRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.luisdbb.tarea3AD2024base.modelo.Profesorado;

@Repository
public interface ProfesoradoRepository extends JpaRepository<Profesorado, Long>{
	@Query("SELECT p FROM Profesorado p WHERE p.email = :email")
    Optional<Profesorado> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Profesorado p WHERE p.usuario = :usuario")
    Optional<Profesorado> findByUsuario(@Param("usuario") String usuario);

    @Query("SELECT p FROM Profesorado p")
    List<Profesorado> findAll();

    List<Profesorado> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
            String nombre, String apellidos);
}
