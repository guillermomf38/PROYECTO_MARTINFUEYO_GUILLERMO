/**
 *Clase Usuario.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package entidades;

public abstract class Usuario {
	
	protected Long idUsuario;
	protected String contraseña;
	
	public Usuario() {
		
	}

	public Usuario(Long idUsuario, String contraseña) {
		super();
		this.idUsuario = idUsuario;
		this.contraseña = contraseña;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", contraseña=" + contraseña+ "]";
	}

}
