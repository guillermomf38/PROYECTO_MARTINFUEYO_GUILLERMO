/**
 *Clase ModificarDatosProfeController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see ProfesoradoService
 *@see SesionService
 *
 *
 *Controller de la pantalla de modificación de datos propios del Profesorado.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.services.ProfesoradoService;
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
public class ModificarDatosProfeController implements Initializable {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField passNuevaContrasena;
	@FXML
	private PasswordField passConfirmar;
	@FXML
	private Label lblMensaje;
	
	@FXML
	private Button btnGuardar;
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
	private ProfesoradoService profesoradoService;
	@Autowired
	private SesionService sesionService;

	/** Referencia al tutor logueado para actualizar sus datos. */
	private Profesorado tutorLogueado;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		  tutorLogueado = sesionService.getProfesradoLogueado();
		    if (tutorLogueado != null) {
		        txtNombre.setText(tutorLogueado.getNombre());
		        txtApellidos.setText(tutorLogueado.getApellidos());
		        txtEmail.setText(tutorLogueado.getEmail());
		    } else {
		        lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		        lblMensaje.setText("Error al cargar los datos. Vuelve a iniciar sesión.");
		    }
		    

			 openAyuda.setAccelerator(
				        new KeyCodeCombination(KeyCode.F1)
				    );
	}

	/**
	 * Guarda los cambios del tutor logueado.
	 *
	 * <p>
	 * Lógica de contraseña: si los campos de nueva contraseña están vacíos, no
	 * se modifica. Si se rellenan, deben coincidir.
	 * </p>
	 *
	 * <p>
	 * Valida email único: si el email cambió, comprueba que no lo use otro
	 * tutor antes de guardar.
	 * </p>
	 *
	 * @param event evento del botón Guardar cambios
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (tutorLogueado == null) {
			error("No hay sesión activa.");
			return;
		}

		String nombre = txtNombre.getText().trim();
		String apellidos = txtApellidos.getText().trim();
		String email = txtEmail.getText().trim();
		String nuevaPass = passNuevaContrasena.getText();
		String confirmar = passConfirmar.getText();

		if (nombre.isBlank() || apellidos.isBlank() || email.isBlank()) {
			error("Nombre, apellidos y email son obligatorios.");
			return;
		}

		if (!email.equals(tutorLogueado.getEmail())
				&& profesoradoService.existeEmail(email)) {
			error("Ese email ya lo usa otro tutor.");
			return;
		}

		if (!nuevaPass.isBlank() && !nuevaPass.equals(confirmar)) {
            error("Las contraseñas no coinciden."); return;
        }

		try {
			tutorLogueado.setNombre(nombre);
			tutorLogueado.setApellidos(apellidos);
			tutorLogueado.setEmail(email);
			  if (!nuevaPass.isBlank()) tutorLogueado.setContrasena(nuevaPass);
			profesoradoService.update(tutorLogueado);

			sesionService.setUsuarioLogueado(tutorLogueado);
			passNuevaContrasena.clear();
			passConfirmar.clear();
			ok("Datos actualizados correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Navega de vuelta al menú del profesorado.
	 *
	 * @param event evento del botón Volver
	 */
	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.PROFESORADO);
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error " + msg);
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
