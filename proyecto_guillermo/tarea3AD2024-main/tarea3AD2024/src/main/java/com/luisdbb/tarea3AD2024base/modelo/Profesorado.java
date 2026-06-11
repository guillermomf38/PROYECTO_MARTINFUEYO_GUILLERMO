/**
 *Clase Profesorado.java
 *
 *Entidad que representa al tutor de centro del departamento.
 *Extiende Usuario con perfil PROFESORADO.
 *Gestiona estudiantes, empresas y asignaciones.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Estudiante
 *@see PeriodoPracticas
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Profesorado")
public class Profesorado extends Usuario {

	private String nombre;
	private String apellidos;

	@Column(unique = true)
	private String email;
	/** Lista de estudiantes asignados a este tutor. */
	@OneToMany(mappedBy="profesorado")
	private List<Estudiante>estudiantes;

	public Profesorado() {

	}

/**
 * Crea un tutor de centro. Perfil fijado a PROFESORADO.
 * @param usuario    nombre de acceso único
 * @param contrasena contraseña
 * @param nombre     nombre de pila
 * @param apellidos  apellidos completos
 * @param email      correo institucional único
 */

	public Profesorado(String usuario, String contrasena, String nombre,
			String apellidos, String email) {
		super(usuario, contrasena, Perfil.PROFESORADO);
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

}
