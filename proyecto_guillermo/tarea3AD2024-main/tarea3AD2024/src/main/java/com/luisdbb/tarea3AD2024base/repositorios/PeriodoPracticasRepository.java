/**
 *Clase PeriodoPracticasRepository.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;

@Repository
public interface PeriodoPracticasRepository extends JpaRepository<PeriodoPracticas, Long>{
	List<PeriodoPracticas> findByCursoAcademico(String cursoAcademico);

 
    List<PeriodoPracticas> findByCoordinador(Profesorado coordinador);

    List<PeriodoPracticas> findByNombrePeriodoContainingIgnoreCase(String nombre);
}
