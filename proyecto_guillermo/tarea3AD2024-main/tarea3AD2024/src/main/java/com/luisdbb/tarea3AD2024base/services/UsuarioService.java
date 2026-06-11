package com.luisdbb.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Administrador;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.EstudianteRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ProfesoradoRepository;
import com.luisdbb.tarea3AD2024base.repositorios.TutorEmpresaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepository;

    
    @Autowired
    private ProfesoradoRepository profesoradoRepository;

    @Autowired
    private TutorEmpresaRepository tutorEmpresaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Value("${admin.usuario}")
    private String adminUsuario;

    @Value("${admin.contrasena}")
    private String adminContrasena;

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

    public Usuario findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public void deleteInBatch(List<Usuario> usuarios) {
        usuarioRepository.deleteAll(usuarios);
    }

    
    public Usuario autenticacion(String usuario, String contrasena) {

       
        if (usuario.equals(adminUsuario) && contrasena.equals(adminContrasena)) {
            Administrador admin = new Administrador(adminUsuario, adminContrasena);
            return admin;
        }

       
        Optional<Profesorado> profe = profesoradoRepository.findByUsuario(usuario);
        if (profe.isPresent() && contrasena.equals(profe.get().getContrasena())) {
            return profe.get();
        }

        
        Optional<TutorEmpresa> tutor = tutorEmpresaRepository.findByUsuario(usuario);
        if (tutor.isPresent() && contrasena.equals(tutor.get().getContrasena())) {
            return tutor.get();
        }

        
        Optional<Estudiante> est = estudianteRepository.findByUsuario(usuario);
        if (est.isPresent() && contrasena.equals(est.get().getContrasena())) {
            return est.get();
        }

        return null;
    }
}