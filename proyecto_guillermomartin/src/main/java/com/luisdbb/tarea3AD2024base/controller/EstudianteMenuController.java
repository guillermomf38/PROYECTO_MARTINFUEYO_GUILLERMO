/**
 * Clase EstudianteMenuController.java
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
public class EstudianteMenuController implements Initializable {

	@FXML
	private Label lblPerfil;
	
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Label lblOpcion;
	
	@FXML
	private Label lblVerDatosEstudiante;
	
	@FXML
	private Button btnVerDatosEstudiante;
	
	@FXML
	private Label lblDatosEmpresaTutor;
	
	@FXML
	private Button btnDatosEmpresaTutor;
	
	@FXML
	private Label lblJustificante;
	
	@FXML
	private Button btnJustificante;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private void verDatosEstudiante(ActionEvent event) {
		
	}
	@FXML
	private void datosEmpresaTutor(ActionEvent event) {
		
	}
	@FXML
	private void adjuntarJustificante(ActionEvent event) {
		
	}
	@FXML
	private void cerrarSesion(ActionEvent event) {
		stageManager.switchScene(com.luisdbb.tarea3AD2024base.view.FxmlView.LOGIN);
    }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

