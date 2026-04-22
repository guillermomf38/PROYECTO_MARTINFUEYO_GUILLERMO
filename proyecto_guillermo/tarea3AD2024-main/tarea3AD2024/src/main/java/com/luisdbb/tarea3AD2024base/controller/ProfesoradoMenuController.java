/**
 * Clase ProfesoradoMenuController.java
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
public class ProfesoradoMenuController implements Initializable{
  
	
	@FXML
	private Label lblPerfil;
	
	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Label lblOpcion;
	
	@FXML
	private Label lblGestionarEstudiantes;
	
	@FXML
	private Button btnGestionarEstudiantes;
	
	@FXML
	private Label lblGestionarEmpresas;
	
	@FXML
	private Button btnGestionarEmpresas;
	
	@FXML
	private Label lblGestionarTutorEmpresa;
	
	@FXML
	private Button bntGestionarTutorEmpresa;
	
	@FXML
	private Label lblAsignacion;
	
	@FXML
	private Button btnAsignacion;
	
	@FXML
	private Label lblModificarDatos;
	
	@FXML
	private Button btnModificarDatos;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@FXML
	private void gestionarEstudiantes(ActionEvent event) {
		
	}
	@FXML
	private void gestionarEmpresas(ActionEvent event) {
		
	}
	@FXML
	private void gestionarTutorEmpresa(ActionEvent event) {
		
	}
	@FXML
	private void asignacion(ActionEvent event) {
		
	}
	@FXML
	private void modificarDatos(ActionEvent event) {
		
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

