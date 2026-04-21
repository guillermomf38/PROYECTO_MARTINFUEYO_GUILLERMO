/**
 *Clase Usuario
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long idUsuario;

	@Column(unique = true, nullable = false)
	private String usuario;

	@Column(nullable = false)
	protected String contrasena;

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
