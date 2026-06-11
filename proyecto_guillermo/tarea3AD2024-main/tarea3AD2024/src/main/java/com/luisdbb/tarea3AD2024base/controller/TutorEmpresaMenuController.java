/**
 *Clase TutorEmpresaController.java
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
public class TutorEmpresaMenuController implements Initializable {

	@FXML
	private Label lblPerfil;
	
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Label lblOpcion;

	@FXML
	private Label lblRegistrarFaltas;
	
	@FXML
	private Button btnRegistrarFaltas;
	
	@FXML
	private Label lblConsultarDatos;
	
	@FXML
	private Button btnConsultarDatos;
	
	@FXML
	private Label lblCalificarEstudiante;
	
	@FXML
	private Button btnCalificarEstudiante;
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@Lazy
	@Autowired
	private StageManager stageManager;

	
	
	@FXML
	private void registrarFaltas(ActionEvent event) {
		 stageManager.switchScene(FxmlView.REGISTRAR_FALTAS);
	}
	@FXML
	private void consultarDatos(ActionEvent event) {
		 stageManager.switchScene(FxmlView.CONSULTAR_DATOS);
	}
	@FXML
	private void calificarEstudiante(ActionEvent event) {
		  stageManager.switchScene(FxmlView.CALIFICAR_ESTUDIANTE);
	}
	@FXML
	private void cerrarSesion(ActionEvent event) {
		stageManager.switchScene(com.luisdbb.tarea3AD2024base.view.FxmlView.LOGIN);
   }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
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
