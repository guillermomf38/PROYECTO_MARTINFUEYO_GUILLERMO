/**
 *Clase TutorEmpresa.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package modelo;

public class TutorEmpresa extends Usuario {
	private Long idTutor;
	private String nombre;
	private String apellidos;
	private String email;

	public TutorEmpresa() {

	}

	public TutorEmpresa(Long idUsuario, String contraseña, Long idTutor,
			String nombre, String apellidos, String email) {
		super(idUsuario, contraseña);
		this.idTutor = idTutor;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public Long getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
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
		return "TutorEmpresa [idTutor=" + idTutor + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", email=" + email + "]";
	}

}
