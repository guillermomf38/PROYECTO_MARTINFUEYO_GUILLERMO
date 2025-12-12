/**
 *Clase Profesorado.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package modelo;

public class Profesorado extends Usuario {
	private Long idProfesor;
	private String nombre;
	private String apellidos;
	private String email;

	public Profesorado() {

	}

	public Profesorado(Long idUsuario, String contraseña, Long idProfesor,
			String nombre, String apellidos, String email) {
		super(idUsuario, contraseña);
		this.idProfesor = idProfesor;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public Long getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Long idProfesor) {
		this.idProfesor = idProfesor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Profesorado [idProfesor=" + idProfesor + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", email=" + email + "]";
	}

}
