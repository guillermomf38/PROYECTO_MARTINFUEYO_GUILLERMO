/**
 *Clase Empresa.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package modelo;

public class Empresa {
	private Long idEmpresa;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;

	public Empresa(Long idEmpresa, String nombre, String direccion,
			String telefono, String email) {
		this.idEmpresa = idEmpresa;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", telefono=" + telefono
				+ ", email=" + email + "]";
	}

}
