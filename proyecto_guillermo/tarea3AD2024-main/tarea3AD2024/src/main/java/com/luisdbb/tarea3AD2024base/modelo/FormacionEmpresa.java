/**
 *Clase FormacionEmpresa.java
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FormacionEmpresa")
public class FormacionEmpresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFormacion;

	@ManyToOne
	@JoinColumn(name = "idEstudiante")
	private Estudiante estudiante;

	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "idTutorEmpresa")
	private TutorEmpresa tutorEmpresa;

	@ManyToOne
	@JoinColumn(name = "idPeriodo")
	private PeriodoPracticas periodoPracticas;

	@Column(nullable = false)
	private boolean activa;

	protected FormacionEmpresa() {

	}

	public FormacionEmpresa(Estudiante estudiante, Empresa empresa,
			TutorEmpresa tutorEmpresa, PeriodoPracticas periodoPracticas) {
		super();
		this.estudiante = estudiante;
		this.empresa = empresa;
		this.tutorEmpresa = tutorEmpresa;
		this.periodoPracticas = periodoPracticas;
		this.activa = true;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TutorEmpresa getTutorEmpresa() {
		return tutorEmpresa;
	}

	public void setTutorEmpresa(TutorEmpresa tutorEmpresa) {
		this.tutorEmpresa = tutorEmpresa;
	}

	public PeriodoPracticas getPeriodoPracticas() {
		return periodoPracticas;
	}

	public void setPeriodoPracticas(PeriodoPracticas periodoPracticas) {
		this.periodoPracticas = periodoPracticas;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public Long getIdFormacion() {
		return idFormacion;
	}

}