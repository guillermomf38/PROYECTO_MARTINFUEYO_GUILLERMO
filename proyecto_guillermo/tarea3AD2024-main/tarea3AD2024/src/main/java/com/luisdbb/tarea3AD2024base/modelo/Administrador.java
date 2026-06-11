/**
 *Clase Administrador.java
 *
 *Entidad que representa al administrador general del sistema.
 *Extiende Usuario con perfil fijado a ADMINISTRADOR.
 *Sus credenciales se configuran en application.properties.
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Usuario
 */


package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administrador")
public class Administrador extends Usuario {

	protected Administrador() {

	}
	/**
	 * Crea un administrador con las credenciales indicadas.
	 * @param usuario    nombre de usuario único
	 * @param contrasena contraseña de acceso
	 */

	public Administrador(String usuario, String contrasena) {
		super(usuario, contrasena, Perfil.ADMINISTRADOR);
	}

}
