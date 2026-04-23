
/**
 *Clase FormacionEmpresa.java
 * 
 *Entidad que representa la asignación de un estudiante a una empresa
 *durante un periodo de prácticas concreto. 
 *
 *relaciona un {@link Estudiante},una {@link Empresa},
 *un {@link TutorEmpresa} y un {@link PeriodoPracticas}.
 *
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Estudiante
 *@see Empresa
 *@see TutorEmpresa
 *@see PeriodoPracticas
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
	/**
     * Identificador único generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFormacion;
	
	/**
     * Estudiante que realiza esta formación.
     * FK {@code idEstudiante} en la tabla {@code FormacionEmpresa}.
    */
	@ManyToOne
	@JoinColumn(name = "idEstudiante")
	private Estudiante estudiante;
	
	/**
     * Empresa en la que se realiza la formación.
     * FK {@code idEmpresa} en la tabla {@code FormacionEmpresa}.
     */
	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;
	
	/**
     * Tutor de empresa responsable de supervisar al estudiante.
     * FK {@code idTutorEmpresa} en la tabla {@code FormacionEmpresa}.
     */
	@ManyToOne
	@JoinColumn(name = "idTutorEmpresa")
	private TutorEmpresa tutorEmpresa;
	
	/**
     * Periodo de prácticas en el que se realiza esta formación.
     * FK {@code idPeriodo} en la tabla {@code FormacionEmpresa}.
     */
	@ManyToOne
	@JoinColumn(name = "idPeriodo")
	private PeriodoPracticas periodoPracticas;
	
	 /**Indica si la formación está actualmente activa*/
	
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