
/**
 *Clase Estudiante.java
 *
 *Entidad que representa a un estudiante
 *
 *Cada estudiante está asociado a un {@link Profesorado}
 *y puede tener varias {@link FormacionEmpresa}
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Profesorado
 *@see FormacionEmpresa
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
	
	/**
     * Tutor de centro responsable de este estudiante.
     * Relación {@code @ManyToOne} la FK {@code idProfesorado} reside en esta tabla.
     */
	@ManyToOne
	@JoinColumn(name="idProfesorado")
	private Profesorado profesorado;
	
	 /**
     * Historial de formaciones en empresa del estudiante.
     * La eliminación en cascada garantiza que al borrar el estudiante
     * se eliminan también sus registros de formación.
     */
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

	public void setProfesorado(Profesorado profesorado) {
		this.profesorado = profesorado;
	}

}
