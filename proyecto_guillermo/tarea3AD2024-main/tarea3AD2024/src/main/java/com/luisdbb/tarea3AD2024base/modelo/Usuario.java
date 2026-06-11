/**
 *Clase Usuario
 * 
 *Entidad base que representa a cualquier usuario del sistema.
 *Superclase de la jerarquía JPA con estrategia JOINED.
 *Cada subclase tiene su propia tabla unida mediante la PK.

 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Perfil
 */

package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuario")
public class Usuario {
	/** Nombre de usuario único para el acceso al sistema. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long idUsuario;
	/** Contraseña de acceso. Protected para herencia. */
	@Column(unique = true, nullable = false)
	private String usuario;

	@Column(nullable = false)
	protected String contrasena;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Perfil perfil;
	
	

	public Usuario() {

	}
	/**
	 * Crea un usuario con credenciales y perfil.
	 * @param usuario    nombre de usuario único
	 * @param contrasena contraseña de acceso
	 * @param perfil     rol del usuario en el sistema
	 */
	public Usuario(String usuario, String contrasena, Perfil perfil) {

		this.usuario = usuario;
		this.contrasena = contrasena;
		this.perfil = perfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

}
