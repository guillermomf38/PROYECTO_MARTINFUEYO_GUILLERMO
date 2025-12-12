/**
 *Clase Estudiante.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package entidades;

public class Estudiante extends Usuario {
	private Long idEstudiante;
	private String nombre;
	private String apellidos;
	private String curso;

	public Estudiante() {

	}

	public Estudiante(Long idUsuario, String contraseña, Long idEstudiante,
			String nombre, String apellidos, String curso) {
		super(idUsuario, contraseña);
		this.idEstudiante = idEstudiante;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.curso = curso;
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
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

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Estudiante [idEstudiante=" + idEstudiante + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", curso=" + curso + "]";
	}

}
