/**
 *Clase DatosEmpresaTutorController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresaService
 *@see SesionService
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class DatosEmpresaTutorController implements Initializable {
	@FXML
	private Label lblEmpresaNombre;
	@FXML
	private Label lblEmpresaDireccion;
	@FXML
	private Label lblEmpresaTelefono;
	@FXML
	private Label lblEmpresaEmail;
	@FXML
	private Label lblTutorNombre;
	@FXML
	private Label lblTutorApellidos;
	@FXML
	private Label lblTutorEmail;
	@FXML
	private Label lblSinFormacion;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@FXML
	private Button btnVolver;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private FormacionEmpresaService formacionService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		 Estudiante est = sesionService.getEstudianteLogueado();
		    if (est == null) return;

		    formacionService.findActivaByEstudianteId(est.getIdUsuario())
		            .ifPresentOrElse(
		                this::rellenarDatos,
		                this::mostrarSinFormacion
		            );
		    
			 openAyuda.setAccelerator(
				        new KeyCodeCombination(KeyCode.F1)
				    );
	}

	/**
	 * Rellena todos los labels con los datos de la empresa y del tutor
	 * extraídos de la formación activa.
	 *
	 * @param f formación activa del estudiante logueado
	 */
	private void rellenarDatos(FormacionEmpresa f) {
		lblSinFormacion.setVisible(false);

		Empresa empresa = f.getEmpresa();
		if (empresa != null) {
			lblEmpresaNombre.setText(empresa.getNombre());
			lblEmpresaDireccion.setText(empresa.getDireccion());
			lblEmpresaTelefono.setText(empresa.getTelefono());
			lblEmpresaEmail.setText(empresa.getEmail());
		}

		TutorEmpresa tutor = f.getTutorEmpresa();
		if (tutor != null) {
			lblTutorNombre.setText(tutor.getNombre());
			lblTutorApellidos.setText(tutor.getApellidos());
			lblTutorEmail.setText(tutor.getEmail());
		}
	}

	/**
	 * Muestra el mensaje de que el estudiante no tiene formación activa y
	 * limpia todos los labels de datos.
	 */
	private void mostrarSinFormacion() {
		lblSinFormacion.setVisible(true);
		lblEmpresaNombre.setText("—");
		lblEmpresaDireccion.setText("—");
		lblEmpresaTelefono.setText("—");
		lblEmpresaEmail.setText("—");
		lblTutorNombre.setText("—");
		lblTutorApellidos.setText("—");
		lblTutorEmail.setText("—");
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
