/**
 *Clase FormacionEmpresa.java
 *
 *Entidad central del sistema. Representa la asignación de un
 *estudiante a una empresa durante un periodo de prácticas.
 *Si la empresa devuelve al estudiante, activa pasa a false
 *y se crea un nuevo registro con la nueva asignación.
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
	/** true mientras la formación está en curso. */
	@Column(nullable = false)
	private boolean activa;

	protected FormacionEmpresa() {

	}
	/**
	 * Crea una formación activa. activa se inicializa a true.
	 * @param estudiante       alumno que realiza la formación
	 * @param empresa          empresa donde se realiza
	 * @param tutorEmpresa     tutor asignado en la empresa
	 * @param periodoPracticas periodo de FCT correspondiente
	 */
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