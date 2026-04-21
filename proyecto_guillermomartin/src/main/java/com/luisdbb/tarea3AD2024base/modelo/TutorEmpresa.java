/**
 *Clase TutorEmpresa.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TutorEmpresa")
public class TutorEmpresa extends Usuario {

	private String nombre;
	private String apellidos;

	@Column(unique = true)
	private String email;

	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	@OneToMany(mappedBy = "tutorEmpresa") 
	private List<FormacionEmpresa> formaciones = new ArrayList<>();

	public TutorEmpresa() {
	}

	public TutorEmpresa(String usuario, String contrasena, String nombre,
			String apellidos, String email) {
		super(usuario, contrasena, Perfil.TUTOREMPRESA);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}

	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}
}