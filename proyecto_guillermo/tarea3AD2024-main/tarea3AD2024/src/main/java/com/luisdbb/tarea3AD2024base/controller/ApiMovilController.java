/**
 *Clase ApiMovilController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
import com.luisdbb.tarea3AD2024base.services.SeguimientoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiMovilController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FormacionEmpresaService formacionService;
	@Autowired
	private SeguimientoService seguimientoService;

	@Value("${admin.usuario}")
	private String adminUsuario;
	@Value("${admin.contrasena}")
	private String adminContrasena;

	public record LoginRequest(String usuario, String contrasena) {
	}

	public record LoginResponse(Long id, String nombre, String apellidos,
			String usuario, String curso) {
	}

	public record FormacionResponse(Long idFormacion, String empresa,
			String direccionEmpresa, String telefonoEmpresa,
			String emailEmpresa, String tutorEmpresa, String emailTutor,
			String tutorCentro, String periodo, String fechaInicio,
			String fechaFin) {
	}

	public record SeguimientoResponse(Long id, String fecha, String valoracion,
			String observaciones) {
	}
	


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {

		if (req.usuario().equals(adminUsuario)) {
			return ResponseEntity.status(401).body("Acceso no permitido");
		}
		Usuario u = usuarioService.autenticacion(req.usuario(),
				req.contrasena());
		if (u == null || !(u instanceof Estudiante)) {
			return ResponseEntity.status(401).body("Credenciales incorrectas");
		}
		Estudiante est = (Estudiante) u;
		return ResponseEntity
				.ok(new LoginResponse(est.getIdUsuario(), est.getNombre(),
						est.getApellidos(), est.getUsuario(), est.getCurso()));
	}

	@GetMapping("/formacion/{idEstudiante}")
	public ResponseEntity<?> getFormacion(@PathVariable Long idEstudiante) {
		Optional<FormacionEmpresa> opt = formacionService
				.findActivaByEstudianteId(idEstudiante);
		if (opt.isEmpty()) {
			return ResponseEntity.status(404).body("Sin formación activa");
		}
		FormacionEmpresa f = opt.get();
		Empresa emp = f.getEmpresa();
		TutorEmpresa te = f.getTutorEmpresa();
		PeriodoPracticas p = f.getPeriodoPracticas();
		Profesorado pc = f.getEstudiante().getProfesorado();

		return ResponseEntity.ok(new FormacionResponse(f.getIdFormacion(),
				emp != null ? emp.getNombre() : "",
				emp != null ? emp.getDireccion() : "",
				emp != null ? emp.getTelefono() : "",
				emp != null ? emp.getEmail() : "",
				te != null ? te.getNombre() + " " + te.getApellidos() : "",
				te != null ? te.getEmail() : "",
				pc != null ? pc.getNombre() + " " + pc.getApellidos() : "",
				p != null ? p.getNombrePeriodo() : "",
				p != null && p.getFechaInicio() != null
						? p.getFechaInicio().toString()
						: "",
				p != null && p.getFechaFin() != null
						? p.getFechaFin().toString()
						: ""));
	}

	@GetMapping("/seguimientos/{idFormacion}")
	public ResponseEntity<?> getSeguimientos(@PathVariable Long idFormacion) {
		FormacionEmpresa f = formacionService.findById(idFormacion);
		if (f == null)
			return ResponseEntity.status(404).body("Formación no encontrada");

		List<SeguimientoResponse> lista = seguimientoService.findByFormacion(f)
				.stream()
				.map(s -> new SeguimientoResponse(s.getIdSeguimiento(),
						s.getFecha() != null ? s.getFecha().toString() : "",
						s.getValoracion(), s.getObservaciones()))
				.toList();
		return ResponseEntity.ok(lista);
	}
	

}
