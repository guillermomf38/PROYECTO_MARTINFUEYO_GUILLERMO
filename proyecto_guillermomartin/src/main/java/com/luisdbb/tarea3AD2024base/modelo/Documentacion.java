<<<<<<< HEAD
/**
 *Clase Documentacion.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Documentacion")
public class Documentacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDocumento",unique=true, updatable = false, nullable = false)
	
	private Long idDocumento;
	private String nombre;
	private String tipo;
	
	@Column(name = "idFormacion")
	private Long idFormacion;
	
	public Documentacion( String nombre, String tipo,
			Long idFormacion) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.idFormacion = idFormacion;
	}

	public Documentacion() {
		super();
	}

	public Long getIdDocumento() {
		return idDocumento;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Long idFormacion) {
		this.idFormacion = idFormacion;
	}

	

}
=======
/**
 *Clase Documentacion.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Documentacion")
public class Documentacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDocumento",unique=true, updatable = false, nullable = false)
	
	private Long idDocumento;
	private String nombre;
	private String tipo;
	
	@Column(name = "idFormacion")
	private Long idFormacion;
	
	public Documentacion( String nombre, String tipo,
			Long idFormacion) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.idFormacion = idFormacion;
	}

	public Documentacion() {
		super();
	}

	public Long getIdDocumento() {
		return idDocumento;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Long idFormacion) {
		this.idFormacion = idFormacion;
	}

	

}
>>>>>>> branch 'main' of https://github.com/guillermomf38/PROYECTO_MARTINFUEYO_GUILLERMO.git
