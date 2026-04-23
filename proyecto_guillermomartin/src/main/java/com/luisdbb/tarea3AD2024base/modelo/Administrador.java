/**
 *Clase Administrador.java
 * 
 *Entidad que representa al administrador general del sistema.
 *Extiende {@link Usuario} heredando las credenciales de acceso.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Usuario
 *@see Perfil
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
     * El perfil se establece automáticamente como {@link Perfil#ADMINISTRADOR}.
     *
     * @param usuario   nombre de usuario único para el acceso al sistema
     * @param contrasena contraseña de acceso
     */
	public Administrador(String usuario, String contrasena) {
		super(usuario, contrasena, Perfil.ADMINISTRADOR);
	}

}

