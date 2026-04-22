/**
 *Clase PeriodoPracticas.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PeriodoPracticas")
public class PeriodoPracticas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPeriodo",unique=true, updatable = false, nullable = false)
	private Long idPeriodo;
	
	private String nombrePeriodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String cursoAcademico;
	
	@OneToMany(mappedBy="periodoPracticas")
	private List<FormacionEmpresa> formaciones =new ArrayList<>();;

	public PeriodoPracticas() {
		
	}
	public PeriodoPracticas(String nombrePeriodo,
			LocalDate fechaInicio, LocalDate fechaFin, String cursoAcademico) {
		
		this.nombrePeriodo = nombrePeriodo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cursoAcademico = cursoAcademico;
	}

	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCursoAcademico() {
		return cursoAcademico;
	}

	public void setCursoAcademico(String cursoAcademico) {
		this.cursoAcademico = cursoAcademico;
	}
	public List<FormacionEmpresa> getFormaciones() {
		return formaciones;
	}
	public void setFormaciones(List<FormacionEmpresa> formaciones) {
		this.formaciones = formaciones;
	}

}
