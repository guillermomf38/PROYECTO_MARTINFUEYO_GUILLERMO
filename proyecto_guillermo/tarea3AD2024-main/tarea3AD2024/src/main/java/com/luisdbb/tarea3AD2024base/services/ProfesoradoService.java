/**
 *Clase ProfesoradoService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.repositorios.ProfesoradoRepository;

@Service
public class ProfesoradoService {
	@Autowired
    private ProfesoradoRepository profesoradoRepository;

  
    public Profesorado save(Profesorado profesorado) {
        return profesoradoRepository.save(profesorado);
    }

    
    public Profesorado update(Profesorado profesorado) {
        return profesoradoRepository.save(profesorado);
    }

    
    public void delete(Long id) {
        profesoradoRepository.deleteById(id);
    }

   
    public Profesorado findById(Long id) {
        return profesoradoRepository.findById(id).orElse(null);
    }

    
    public List<Profesorado> findAll() {
        return profesoradoRepository.findAll();
    }

   
    public boolean existeEmail(String email) {
        return profesoradoRepository.findByEmail(email).isPresent();
    }

    public Profesorado findByUsuario(String usuario) {
        return profesoradoRepository.findByUsuario(usuario).orElse(null);
    }
    public boolean existeUsuario(String usuario) {
        return profesoradoRepository.findByUsuario(usuario).isPresent();
    }
}
