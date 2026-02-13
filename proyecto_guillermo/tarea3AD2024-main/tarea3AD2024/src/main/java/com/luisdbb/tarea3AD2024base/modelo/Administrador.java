/**
 *Clase Administrador.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */


package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administrador")
public class Administrador extends Usuario {

	protected Administrador() {

	}

	public Administrador(String usuario, String contrasena) {
		super(usuario, contrasena, Perfil.ADMINISTRADOR);
	}

}
