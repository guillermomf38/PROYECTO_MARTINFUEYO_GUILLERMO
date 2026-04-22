/**
 *Clase Estudiante.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estudiante")
public class Estudiante extends Usuario {

	private String nombre;
	private String apellidos;
	private String curso;
    
	@ManyToOne
	@JoinColumn(name="idProfesorado")
	private Profesorado profesorado;
	
	@OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<FormacionEmpresa> formaciones;
	
	public Estudiante() {

	}

	public Estudiante(String usuario, String contrasena, String nombre,
			String apellidos, String curso) {
		super(usuario, contrasena, Perfil.ESTUDIANTE);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.curso = curso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
