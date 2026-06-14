/**
 *Clase Documentacion.java
 *
 *Almacena metadatos de ficheros adjuntos por el estudiante
 *como justificantes de faltas de asistencia.
 *Vinculada a una FormacionEmpresa mediante idFormacion.
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresa
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
	/** Nombre descriptivo del documento. */
	private String nombre;
	private String tipo;
	private String ruta;
	/** FK de la FormacionEmpresa a la que pertenece. */
	@Column(name = "idFormacion")
	private Long idFormacion;
	
	
	public Documentacion() {
		
	}

	public Documentacion(String nombre, String tipo, Long idFormacion) {
	    this.nombre = nombre;
	    this.tipo = tipo;
	    this.idFormacion = idFormacion;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
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

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(Long idFormacion) {
		this.idFormacion = idFormacion;
	}


}
