/**
 *Clase Seguimiento.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Seguimiento")
public class Seguimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSeguimiento", unique = true, updatable = false, nullable = false)
	private Long idSeguimiento;

	private String observaciones;
	private String valoracion;
	private LocalDate fecha;

	@ManyToOne
	@JoinColumn(name = "idFormacion")
	private FormacionEmpresa formacion;

	public Seguimiento() {

	}

	public Seguimiento(Long idSeguimiento, String observaciones,
			String valoracion, LocalDate fecha) {
		this.idSeguimiento = idSeguimiento;
		this.observaciones = observaciones;
		this.valoracion = valoracion;
		this.fecha = fecha;
	}

	public Long getIdSeguimiento() {
		return idSeguimiento;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getValoracion() {
		return valoracion;
	}

	public void setValoracion(String valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
