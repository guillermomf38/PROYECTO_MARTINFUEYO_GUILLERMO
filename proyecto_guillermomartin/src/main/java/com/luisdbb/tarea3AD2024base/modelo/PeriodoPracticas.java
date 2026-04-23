
/**
 *Clase PeriodoPracticas.java
 * 
 *Entidad que representa un periodo de formación en empresa dentro de un
 *curso académico.
 *
 *Un {@link Profesorado} actúa
 *como coordinador del periodo. Cada {@link FormacionEmpresa} queda registrada
 *dentro de un periodo concreto.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresa
 *@see Profesorado
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PeriodoPracticas")
public class PeriodoPracticas {
	/**
     * Identificador único generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPeriodo",unique=true, updatable = false, nullable = false)
	private Long idPeriodo;
	
	private String nombrePeriodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String cursoAcademico;
	
	 /**
     * Tutor de centro que actúa como coordinador de este periodo.
     * FK {@code idProfesorado} en la tabla {@code PeriodoPracticas}.
     */
	@ManyToOne
	@JoinColumn(name = "idProfesorado")
	private Profesorado coordinador;
	
	/**
     * Lista de formaciones en empresa registradas en este periodo.
     * Relación {@code @OneToMany} gestionada desde {@link FormacionEmpresa}.
     */
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
