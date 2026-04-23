
/**
 *Clase Seguimiento.java
 *
 * Entidad que registra una entrada de seguimiento sobre una formación en empresa.
 * 
 *El {@link TutorEmpresa} genera registros de seguimiento para documentar
 *el progreso del estudiante: observaciones sobre su desempeño, valoración
 *obtenida y fecha del seguimiento. Una {@link FormacionEmpresa} puede tener
 *múltiples registros de seguimiento a lo largo del periodo.
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresa
 * @see TutorEmpresa
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
	/**
     * Identificador único generado automáticamente.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSeguimiento", unique = true, updatable = false, nullable = false)
	private Long idSeguimiento;

	private String observaciones;
	private String valoracion;
	private LocalDate fecha;
	
	/**
     * Formación en empresa a la que pertenece este seguimiento.
     * FK {@code idFormacion} en la tabla {@code Seguimiento}.
     */
	@ManyToOne
	@JoinColumn(name = "idFormacion")
	private FormacionEmpresa formacion;

	public Seguimiento() {

	}

	

	public Seguimiento(String observaciones,
			String valoracion, LocalDate fecha, FormacionEmpresa formacion) {
		super();
		this.observaciones = observaciones;
		this.valoracion = valoracion;
		this.fecha = fecha;
		this.formacion = formacion;
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



	public FormacionEmpresa getFormacion() {
		return formacion;
	}



	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}
	

}
