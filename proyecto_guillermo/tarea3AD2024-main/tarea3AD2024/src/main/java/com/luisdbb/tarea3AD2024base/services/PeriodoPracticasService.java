/**
 *Clase PeriodoPracticasService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.repositorios.PeriodoPracticasRepository;

@Service
public class PeriodoPracticasService {
	@Autowired
    private PeriodoPracticasRepository periodoRepository;

   
    public PeriodoPracticas save(PeriodoPracticas periodo) {
        return periodoRepository.save(periodo);
    }

    public PeriodoPracticas update(PeriodoPracticas periodo) {
        return periodoRepository.save(periodo);
    }

   
    public void delete(Long id) {
        periodoRepository.deleteById(id);
    }

  
    public PeriodoPracticas findById(Long id) {
        return periodoRepository.findById(id).orElse(null);
    }

  
    public List<PeriodoPracticas> findAll() {
        return periodoRepository.findAll();
    }

   
    public List<PeriodoPracticas> findByCursoAcademico(String cursoAcademico) {
        return periodoRepository.findByCursoAcademico(cursoAcademico);
    }

    
    public List<PeriodoPracticas> findByCoordinador(Profesorado coordinador) {
        return periodoRepository.findByCoordinador(coordinador);
    }
}
