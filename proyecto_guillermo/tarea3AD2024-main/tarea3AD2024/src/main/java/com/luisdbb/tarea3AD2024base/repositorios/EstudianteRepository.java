/**
 *Clase EstudianteRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Estudiante
 *@see Profesorado
 *
 * Repositorio JPA específico para la entidad {@link Estudiante}.
 *
 * <p>Al igual que {@code ProfesoradoRepository}, tener un repositorio
 * propio para la subclase nos permite obtener listas de {@code Estudiante}
 * ya tipadas sin necesidad de castings, y añadir consultas específicas
 * como filtrar por tutor de centro o por código de curso.</p>
 *
 */
 



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;


@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	@Query("SELECT e FROM Estudiante e")
    List<Estudiante> findAll();

    @Query("SELECT e FROM Estudiante e WHERE e.profesorado.idUsuario = :id")
    List<Estudiante> findByProfesoradoId(@Param("id") Long id);

    @Query("SELECT e FROM Estudiante e WHERE e.usuario = :usuario")
    Optional<Estudiante> findByUsuario(@Param("usuario") String usuario);

    List<Estudiante> findByCurso(String curso);

    List<Estudiante> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
            String nombre, String apellidos);
}
