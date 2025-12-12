/**
 *Clase Seguimiento.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package modelo;

import java.time.LocalDate;

public class Seguimiento {
	private Long idSeguimiento;
	private String observaciones;
	private String valoracion;
	private LocalDate fecha;

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

	public void setIdSeguimiento(Long idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
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

	@Override
	public String toString() {
		return "Seguimiento [idSeguimiento=" + idSeguimiento
				+ ", observaciones=" + observaciones + ", valoracion="
				+ valoracion + ", fecha=" + fecha + "]";
	}

}
