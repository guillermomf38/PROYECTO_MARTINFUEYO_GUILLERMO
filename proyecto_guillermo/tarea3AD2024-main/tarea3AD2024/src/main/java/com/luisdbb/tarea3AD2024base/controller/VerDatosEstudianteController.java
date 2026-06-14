/**
 *Clase VerDatosEstudianteController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see EstudianteService
 *@see SesionService
 *
 *Controller de la pantalla de consulta y modificación de datos propios del Estudiante.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.services.EstudianteService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class VerDatosEstudianteController implements Initializable {
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblCurso;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private PasswordField passNueva;
	@FXML
	private PasswordField passConfirmar;
	@FXML
	private Label lblMensaje;
	
	@FXML
	private Button btnVolver;

	@FXML
	private Button btnGuardar;

	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private EstudianteService estudianteService;
	@Autowired
	private SesionService sesionService;

	/** Estudiante logueado. Se obtiene de la sesión. */
	private Estudiante estudianteLogueado;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		 estudianteLogueado = sesionService.getEstudianteLogueado();
		    if (estudianteLogueado != null) {
		        lblUsuario.setText(estudianteLogueado.getUsuario());
		        lblCurso.setText(estudianteLogueado.getCurso());
		        txtNombre.setText(estudianteLogueado.getNombre());
		        txtApellidos.setText(estudianteLogueado.getApellidos());
		    }
		    openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	/**
	 * Guarda los cambios del estudiante logueado. La contraseña solo se cambia
	 * si los dos campos coinciden y no están vacíos. Usuario y curso no se
	 * pueden cambiar desde esta pantalla.
	 *
	 * @param event evento del botón Guardar cambios
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (estudianteLogueado == null) {
			error("No hay sesión activa.");
			return;
		}

		String nombre = txtNombre.getText().trim();
		String apellidos = txtApellidos.getText().trim();
		String nueva = passNueva.getText();
		String confirmar = passConfirmar.getText();

		if (nombre.isBlank() || apellidos.isBlank()) {
			error("Nombre y apellidos son obligatorios.");
			return;
		}
		if (!nueva.isBlank() && !nueva.equals(confirmar)) {
			error("Las contraseñas no coinciden.");
			return;
		}

		try {
			estudianteLogueado.setNombre(nombre);
			estudianteLogueado.setApellidos(apellidos);
			if (!nueva.isBlank())
				estudianteLogueado.setContrasena(nueva);
			estudianteService.update(estudianteLogueado);
			sesionService.setUsuarioLogueado(estudianteLogueado);
			passNueva.clear();
			passConfirmar.clear();
			ok("Datos actualizados correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Navega de vuelta al menú del estudiante.
	 *
	 * @param event evento del botón Volver
	 */
	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.ESTUDIANTE);
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto: " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error: " + msg);
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
	
}
