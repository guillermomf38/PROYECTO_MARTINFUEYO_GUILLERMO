/**
 *Clase Empresa.java
 *
 *Entidad que representa una empresa colaboradora en la que los estudiantes
 *realizan su formación en empresa.
 *
 *Una empresa puede tener varios {@link TutorEmpresa} asignados y puede
 *acoger múltiples {@link FormacionEmpresa} a lo largo de distintos cursos 
 *académicos.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see TutorEmpresa
 *@see FormacionEmpresa
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Empresa")
public class Empresa {
	/**
     * Identificador único generado automáticamente
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEmpresa",unique=true, updatable = false, nullable = false)
	private Long idEmpresa;
	
	private String nombre;
	private String direccion;
	private String telefono;
	
	/** Correo electrónico de contacto. Debe ser único. */
	@Column(unique=true)
	private String email;
	
	/**
     * Lista de tutores de empresa que pertenecen a esta empresa.
     * Relación {@code @OneToMany} la FK reside en {@link TutorEmpresa}.
    */
	@OneToMany(mappedBy="empresa")
	private List<TutorEmpresa> tutores;
	
	/**
     * Lista de formaciones en empresa realizadas en esta empresa.
     * Relación {@code @OneToMany} la FK reside en {@link FormacionEmpresa}.
     */
	@OneToMany(mappedBy="empresa")
	private List<FormacionEmpresa> formaciones;
	
	public Empresa() {
		
	}

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