/**
 *Clase SesionService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;

@Service
public class SesionService {
	 private Usuario usuarioLogueado;
	 
	    public Usuario getUsuarioLogueado() {
	        return usuarioLogueado;
	    }
	 
	    public void setUsuarioLogueado(Usuario usuario) {
	        this.usuarioLogueado = usuario;
	    }
	 
	    public void cerrarSesion() {
	        this.usuarioLogueado = null;
	    }
	 
	
	    public Profesorado getProfesradoLogueado() {
	        if (usuarioLogueado instanceof Profesorado) {
	            return (Profesorado) usuarioLogueado;
	        }
	        return null;
	    }
	 
	    public TutorEmpresa getTutorEmpresaLogueado() {
	        if (usuarioLogueado instanceof TutorEmpresa) {
	            return (TutorEmpresa) usuarioLogueado;
	        }
	        return null;
	    }
	 
	    public Estudiante getEstudianteLogueado() {
	        if (usuarioLogueado instanceof Estudiante) {
	            return (Estudiante) usuarioLogueado;
	        }
	        return null;
	    }
	 
	    public boolean estaLogueado() {
	        return usuarioLogueado != null;
	    }
}
