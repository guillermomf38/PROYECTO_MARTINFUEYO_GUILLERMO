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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private void registrarFaltas(ActionEvent event) {
		
	}
	@FXML
	private void consultarDatos(ActionEvent event) {
		
	}
	@FXML
	private void calificarEstudiante(ActionEvent event) {
		
	}
	@FXML
	private void cerrarSesion(ActionEvent event) {
		stageManager.switchScene(com.luisdbb.tarea3AD2024base.view.FxmlView.LOGIN);
   }
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
