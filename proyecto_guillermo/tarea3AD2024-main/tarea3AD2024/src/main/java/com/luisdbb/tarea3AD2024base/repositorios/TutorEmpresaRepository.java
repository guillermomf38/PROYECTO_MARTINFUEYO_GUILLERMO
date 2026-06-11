/**
 *Clase TutorEmpresaRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see TutorEmpresa
 *@see Empresa
 *
 *Repositorio JPA específico para la entidad {@link TutorEmpresa}.
 *
 * <p>Al igual que {@code ProfesoradoRepository}, tener un repositorio propio
 * para la subclase nos da listas de {@code TutorEmpresa} directamente tipadas.
 * Además añadimos las consultas específicas que necesitan los controllers:
 * buscar por email  y filtrar por empresa.</p>
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;

@Repository
public interface TutorEmpresaRepository extends JpaRepository<TutorEmpresa, Long> {
	 /**
     * Busca un tutor de empresa por su email.
     * Se usa en el service para validar unicidad antes de guardar.
     *
     * @param email email a buscar
     * @return {@code Optional} con el tutor si existe
     */
    Optional<TutorEmpresa> findByEmail(String email);

    /**
     * Devuelve todos los tutores de empresa pertenecientes a una empresa concreta.
     *
     * @param empresa empresa de la que se quieren ver los tutores
     * @return lista de tutores de esa empresa
     */
    List<TutorEmpresa> findByEmpresa(Empresa empresa);

    /**
     * Busca un tutor de empresa por su nombre de usuario.
     * Se usa para verificar que el usuario no existe ya al crear uno nuevo.
     *
     * @param usuario nombre de usuario a buscar
     * @return {@code Optional} con el tutor si existe
     */
    Optional<TutorEmpresa> findByUsuario(String usuario);
}
