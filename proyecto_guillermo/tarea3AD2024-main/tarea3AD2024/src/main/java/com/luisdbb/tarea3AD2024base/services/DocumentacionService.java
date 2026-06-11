/**
 *Clase DocumentacionService.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see Documentacion
 *@see DocumentacionRepository
 *
 *Servicio que centraliza la lógica de negocio sobre {@link Documentacion}.
 */



package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Documentacion;
import com.luisdbb.tarea3AD2024base.repositorios.DocumentacionRepository;

@Service
public class DocumentacionService {
	 @Autowired
	    private DocumentacionRepository documentacionRepository;

	    /**
	     * Guarda un nuevo documento en la base de datos.
	     *
	     * @param documentacion entidad a persistir
	     * @return entidad guardada con el id generado
	     */
	    public Documentacion save(Documentacion documentacion) {
	        return documentacionRepository.save(documentacion);
	    }

	    /**
	     * Elimina un documento por su id.
	     *
	     * @param id identificador del documento a eliminar
	     */
	    public void delete(Long id) {
	        documentacionRepository.deleteById(id);
	    }

	    /**
	     * Devuelve todos los documentos asociados a una formación concreta.
	     *
	     * @param idFormacion id de la formación cuyos documentos se quieren
	     * @return lista de documentos de esa formación
	     */
	    public List<Documentacion> findByFormacion(Long idFormacion) {
	        return documentacionRepository.findByIdFormacion(idFormacion);
	    }

	    /**
	     * Devuelve todos los documentos del sistema.
	     *
	     * @return lista de todos los documentos
	     */
	    public List<Documentacion> findAll() {
	        return documentacionRepository.findAll();
	    }
}
