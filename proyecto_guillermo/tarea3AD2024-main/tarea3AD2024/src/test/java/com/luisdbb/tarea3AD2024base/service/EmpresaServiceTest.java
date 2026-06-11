/**
 *Clase EmpresaServiceTest.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.services.EmpresaService;

@DataJpaTest
@Import(EmpresaService.class)
public class EmpresaServiceTest {
	@Autowired
    private EmpresaService empresaService;

    @Autowired
    private TestEntityManager entityManager;

    
    /**
     * TC-E01: guardar empresa  se le asigna id y los datos son correctos.
     */
    @Test
    void save_empresaValida_persisteConId() {
        Empresa nueva = new Empresa("TechSolutions", "C/ Mayor 1", "985000000", "info@tech.es");

        Empresa guardada = empresaService.save(nueva);

        assertNotNull(guardada.getIdEmpresa(), "Debe generarse un id automático");
        assertEquals("TechSolutions", guardada.getNombre());
        assertEquals("info@tech.es",  guardada.getEmail());
    }


    /**
     * TC-E02: email ya registrado  existeEmail devuelve true.
     */
    @Test
    void existeEmail_emailRegistrado_devuelveTrue() {
        entityManager.persistAndFlush(
                new Empresa("Softdev", "Avda. 5", "986000000", "soft@dev.es"));

        assertTrue(empresaService.existeEmail("soft@dev.es"),
                "Debe devolver true para un email ya registrado");
    }

   

    /**
     * TC-E03: email no registrado  existeEmail devuelve false.
     */
    @Test
    void existeEmail_emailNoRegistrado_devuelveFalse() {
        assertFalse(empresaService.existeEmail("nuevo@empresa.es"),
                "Debe devolver false para un email no registrado");
    }

   

    /**
     * TC-E04: findAll con dos empresas guardadas  devuelve lista con 2 elementos.
     */
    @Test
    void findAll_dosEmpresasGuardadas_devuelveDos() {
        entityManager.persistAndFlush(
                new Empresa("EmpresaA", "C/1", "111111111", "a@test.es"));
        entityManager.persistAndFlush(
                new Empresa("EmpresaB", "C/2", "222222222", "b@test.es"));

        List<Empresa> resultado = empresaService.findAll();

        assertEquals(2, resultado.size(),
                "findAll debe devolver las 2 empresas insertadas");
    }
}
