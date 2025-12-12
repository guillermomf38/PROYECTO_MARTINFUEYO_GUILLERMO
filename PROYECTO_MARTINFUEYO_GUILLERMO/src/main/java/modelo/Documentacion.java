/**
 *Clase Documentacion.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package modelo;

public class Documentacion {
	private Long idDocumento;
	private String nombre;
	private String tipo;

	public Documentacion(Long idDocumento, String nombre, String tipo) {

		this.idDocumento = idDocumento;
		this.nombre = nombre;
		this.tipo = tipo;
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

	@Override
	public String toString() {
		return "Documentacion [idDocumento=" + idDocumento + ", nombre="
				+ nombre + ", tipo=" + tipo + "]";
	}

}
