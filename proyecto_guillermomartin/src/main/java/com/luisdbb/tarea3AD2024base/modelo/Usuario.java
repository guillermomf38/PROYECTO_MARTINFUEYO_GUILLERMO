
/**
 *Clase Usuario
 *
 *Entidad base que representa a cualquier usuario del sistema.
 *Cada subclase ({@link Administrador},
 *{@link Profesorado}, {@link TutorEmpresa}, {@link Estudiante}) tiene
 *su propia tabla que se une a esta mediante la clave primaria {@code idUsuario}.
 *
 *Almacena las credenciales de acceso comunes a todos los perfiles.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Perfil
 *@see Administrador
 *@see Profesorado
 *@see TutorEmpresa
 *@see Estudiante
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
	/**
     * Identificador único generado automáticamente por la base de datos.
     * Accesible desde las subclases al estar declarado {@code protected}.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long idUsuario;

	@Column(unique = true, nullable = false)
	private String usuario;

	@Column(nullable = false)
	protected String contrasena;
	/**
     * Perfil que determina el rol y los permisos del usuario en el sistema.
     */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Perfil perfil;

	public Usuario() {

	}

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