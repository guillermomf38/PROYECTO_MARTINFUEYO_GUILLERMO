/**
 *Clase UsuarioServiceTest.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;

@DataJpaTest
@Import(UsuarioService.class)
@TestPropertySource(properties = {
    "admin.usuario=admin",
    "admin.contrasena=admin123"
})
public class UsuarioServiceTest {
	  @Autowired
	    private UsuarioService usuarioService;

	    /** Permite insertar datos de prueba directamente sin pasar por el service. */
	    @Autowired
	    private TestEntityManager entityManager;

	 

	    /**
	     * TC-S01: admin con credenciales correctas → devuelve Administrador.
	     * El admin se verifica contra @TestPropertySource, no consulta la BD.
	     */
	    @Test
	    void autenticacion_adminCorrecto_devuelveAdministrador() {
	        Usuario resultado = usuarioService.autenticacion("admin", "admin123");

	        assertNotNull(resultado, "No debe ser null");
	        assertEquals(Perfil.ADMINISTRADOR, resultado.getPerfil(),
	                "El perfil debe ser ADMINISTRADOR");
	    }

	  

	    /**
	     * TC-S02: admin con contraseña incorrecta  devuelve null.
	     */
	    @Test
	    void autenticacion_adminContrasenaErronea_devuelveNull() {
	        Usuario resultado = usuarioService.autenticacion("admin", "claveErronea");

	        assertNull(resultado, "Con contraseña incorrecta debe devolver null");
	    }

	 

	    /**
	     * TC-S03: Profesorado con credenciales correctas devuelve Profesorado real.
	     * Primero insertamos un Profesorado con TestEntityManager y luego
	     * comprobamos que autenticacion() lo encuentra y devuelve tipado.
	     */
	    @Test
	    void autenticacion_profesoradoCorrecto_devuelveProfesrado() {
	        entityManager.persistAndFlush(
	                new Profesorado("tutor1", "pass123", "María", "García", "m@test.es"));

	        Usuario resultado = usuarioService.autenticacion("tutor1", "pass123");

	        assertNotNull(resultado);
	        assertInstanceOf(Profesorado.class, resultado,
	                "Debe devolver instancia de Profesorado, no un proxy genérico");
	        assertEquals(Perfil.PROFESORADO, resultado.getPerfil());
	    }

	    

	    /**
	     * TC-S04: usuario que no existe  devuelve null.
	     */
	    @Test
	    void autenticacion_usuarioNoExiste_devuelveNull() {
	        Usuario resultado = usuarioService.autenticacion("fantasma", "pass");

	        assertNull(resultado, "Usuario inexistente debe devolver null");
	    }
}
