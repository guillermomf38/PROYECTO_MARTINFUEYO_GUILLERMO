/**
 * Clase AdministradorController.java
 * 
 * @author Guillermo Martin Fueyo
 * @version 1.0
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
public class AdministradorMenuController implements Initializable{

	@FXML
	private Label lblEleccionOpcion;
	
	@FXML
	private Label lblAsignarPerfiles;

	@FXML
	private Button btnIrAsignarPerfil;
	
	@FXML
	private Label lblGestionarTutores;
	
	@FXML
	private Button btnIrGestionarTutores;
	
	@FXML
	private Label lblGestionarCursos;
	
	@FXML
	private Button btnIrGestionarCursos;
	
	@FXML
	private Label lblGestionarEstudiantes;
	
	@FXML
	private Button btnIrGestionarEstudiantes;
	
	@FXML
	private Label lblAsociarCursosTutor;
	
	@FXML
	private Button btnAsociarCursosTutor;
	
	@FXML
	private Label lblPerfil;
	
	@FXML
	private Button btnCerrarSesion;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	 
	@FXML
	private void irAsignarPerfil(ActionEvent event) {
		
	}
	@FXML
	private void irGestionarTutores(ActionEvent event) {
		
	}
	@FXML
	private void irGestionarCursos(ActionEvent event) {
		
	}
	@FXML
	private void irGestionarEstudiantes(ActionEvent event) {
		
	}
	@FXML
	private void irAsociarcursosTutor(ActionEvent event) {
		
	}
	@FXML
	private void cerrarSesion(ActionEvent event) {
		stageManager.switchScene(com.luisdbb.tarea3AD2024base.view.FxmlView.LOGIN);
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
	}

}

