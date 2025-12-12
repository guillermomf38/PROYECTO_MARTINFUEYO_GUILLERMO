/**
 *Clase PeriodoPracticas.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package entidades;

import java.time.LocalDate;

public class PeriodoPracticas {
	private Long idPeriodo;
	private String nombrePeriodo;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String cursoAcademico;

	public PeriodoPracticas(Long idPeriodo, String nombrePeriodo,
			LocalDate fechaInicio, LocalDate fechaFin, String cursoAcademico) {
		this.idPeriodo = idPeriodo;
		this.nombrePeriodo = nombrePeriodo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.cursoAcademico = cursoAcademico;
	}

	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
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

	@Override
	public String toString() {
		return "PeriodoPracticas [idPeriodo=" + idPeriodo + ", nombrePeriodo="
				+ nombrePeriodo + ", fechaInicio=" + fechaInicio + ", fechaFin="
				+ fechaFin + ", cursoAcademico=" + cursoAcademico + "]";
	}

}
