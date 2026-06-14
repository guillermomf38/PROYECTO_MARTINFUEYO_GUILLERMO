/**
 *Clase Empresa.java
 *
 *Entidad que representa una empresa colaboradora en la que
 *los estudiantes realizan su formación en empresa.
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see TutorEmpresa
 *@see FormacionEmpresa
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Empresa")
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmpresa",unique=true, updatable = false, nullable = false)
	private Long idEmpresa;
	
	private String nombre;
	private String direccion;
	private String telefono;
	
	@Column(unique=true)
	private String email;
	/** Tutores de empresa pertenecientes a esta empresa. */
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
	private List<TutorEmpresa> tutores = new ArrayList<>();
	
	@OneToMany(mappedBy="empresa")
	private List<FormacionEmpresa> formaciones;
	
	public Empresa() {
		
	}
	/**
	 * Crea una empresa con sus datos de contacto.
	 * @param nombre    nombre comercial
	 * @param direccion dirección física
	 * @param telefono  teléfono de contacto
	 * @param email     email único
	 */
	public Empresa(String nombre, String direccion,
			String telefono, String email) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
