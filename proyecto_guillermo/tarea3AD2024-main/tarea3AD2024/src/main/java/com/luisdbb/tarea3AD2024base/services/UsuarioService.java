package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario save(Usuario entity) {
        return usuarioRepository.save(entity);
    }

    public Usuario update(Usuario entity) {
        return usuarioRepository.save(entity);
    }


    public void delete(Usuario entity) {
        usuarioRepository.delete(entity);
    }


    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

   
    public Usuario find(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

   
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

  
    public Usuario autenticacion(String usuario, String contrasena) {
        Usuario u = findByUsuario(usuario);
        if (u != null && contrasena.equals(u.getContrasena())) {
            return u;
        }
        return null;
    }


    
    public Usuario findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

  
    public void deleteInBatch(List<Usuario> usuarios) {
        usuarioRepository.deleteAll(usuarios);
    }
}