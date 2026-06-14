/**
 *Clase AsignarPerfilController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class AsignarPerfilController implements Initializable {

	@FXML
	private RadioButton rdProfesorado;
	@FXML
	private RadioButton rdTutorEmpresa;
	@FXML
	private RadioButton rdEstudiante;

	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField passContrasena;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtEmail;

	@FXML
	private Button btnCrearUsuario;
	@FXML
	private Button btnLimpiar;
	@FXML
	private Button btnVolver;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UsuarioService usuarioService;

	private final ToggleGroup grupoPerfil = new ToggleGroup();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		openAyuda.setAccelerator(
		        new KeyCodeCombination(KeyCode.F1)
		    );

		rdProfesorado.setToggleGroup(grupoPerfil);
		rdTutorEmpresa.setToggleGroup(grupoPerfil);
		rdEstudiante.setToggleGroup(grupoPerfil);
	}

	@FXML
	private void crearUsuario(ActionEvent event) {

		Perfil perfilSeleccionado = getPerfilSeleccionado();
		if (perfilSeleccionado == null) {
			mostrarError(
					"Selecciona un perfil");
			return;
		}

		String usuario = txtUsuario.getText().trim();
		String contrasena = passContrasena.getText();
		String nombre = txtNombre.getText().trim();
		String apellidos = txtApellidos.getText().trim();
		String email = txtEmail.getText().trim();

		if (usuario.isBlank() || contrasena.isBlank()) {
			mostrarError("Usuario y contrasena son obligatorios.");
			return;
		}
		if (nombre.isBlank() || apellidos.isBlank()) {
			mostrarError("Nombre y apellidos son obligatorios.");
			return;
		}

		if (usuarioService.findByUsuario(usuario) != null) {
			mostrarError("El usuario ya existe");
			return;
		}

		try {
			switch (perfilSeleccionado) {

			case PROFESORADO -> {
				if (email.isBlank()) {
					mostrarError("El email es obligatorio para Profesorado.");
					return;
				}
				Profesorado p = new Profesorado(usuario, contrasena, nombre,
						apellidos, email);
				usuarioService.save(p);
			}

			case TUTOREMPRESA -> {
				if (email.isBlank()) {
					mostrarError(
							"El email es obligatorio para Tutor de Empresa.");
					return;
				}

				TutorEmpresa t = new TutorEmpresa(usuario, contrasena, nombre,
						apellidos, email);
				usuarioService.save(t);
			}

			case ESTUDIANTE -> {

				Estudiante est = new Estudiante(usuario, contrasena, nombre,
						apellidos, "");
				usuarioService.save(est);
			}
			}

			mostrarCorrecto("Usuario creado correctamente");
			limpiar(null);

		} catch (Exception ex) {
			mostrarError("Error ");
		}
	}

	@FXML
	private void limpiar(ActionEvent event) {
		grupoPerfil.selectToggle(null);
		txtUsuario.clear();
		passContrasena.clear();
		txtNombre.clear();
		txtApellidos.clear();
		txtEmail.clear();
	}

	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMINISTRADOR);
	}

	private Perfil getPerfilSeleccionado() {
		if (rdProfesorado.isSelected())
			return Perfil.PROFESORADO;
		if (rdTutorEmpresa.isSelected())
			return Perfil.TUTOREMPRESA;
		if (rdEstudiante.isSelected())
			return Perfil.ESTUDIANTE;
		return null;
	}
	
	 @FXML
		private void handleAutoria(ActionEvent event) {
			try {
				WebView webView=new WebView();
				
				String url=getClass().getResource("/autoria/autoria.html").toExternalForm();
				webView.getEngine().load(url);
				
				Stage helpStage=new Stage();
				helpStage.setTitle("Créditos");
				
				Scene helpScene=new Scene(webView, 600,400);
				
				helpStage.setScene(helpScene);
				
				helpStage.initModality(Modality.APPLICATION_MODAL);
				helpStage.setResizable(true);
				
				helpStage.show();
				
			}catch(NullPointerException e) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Archivo no encontrado");
				alert.setContentText("Por favor, verifica que el archivo 'autoria.html' este en la ruta '/autoria/autoria.html'");
				alert.showAndWait();
			}
		}
	  
	  @FXML
		private void handleAyuda(ActionEvent event) {
			try {
				WebView webView=new WebView();
				
				String url=getClass().getResource("/ayuda/ayuda.html").toExternalForm();
				webView.getEngine().load(url);
				
				Stage helpStage=new Stage();
				helpStage.setTitle("Ayuda");
				
				Scene helpScene=new Scene(webView, 600,400);
				
				helpStage.setScene(helpScene);
				
				helpStage.initModality(Modality.APPLICATION_MODAL);
				helpStage.setResizable(true);
				
				helpStage.show();
				
			}catch(NullPointerException e) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Archivo no encontrado");
				alert.setContentText("Por favor, verifica que el archivo 'ayuda.html' este en la ruta '/ayuda/ayuda.html'");
				alert.showAndWait();
			}
		}

	private void mostrarError(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error: ");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	private void mostrarCorrecto(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Registrado Correctamente: ");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

}