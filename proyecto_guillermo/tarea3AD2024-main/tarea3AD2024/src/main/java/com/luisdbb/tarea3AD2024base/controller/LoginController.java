      /**
 *Clase LoginController.java
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
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
@Controller
public class LoginController implements Initializable {
	
	@FXML
	private Label lblTitulo;
	@FXML
	private Label lblInicioSesion;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField passContrasena;
	@FXML
	private Button btnIniciarSesion;
	@FXML
	private Label lblLogin;
	
	@Lazy
	@Autowired
    private StageManager stageManager;
    
    @Autowired
    private UsuarioService usuarioService;

	public void setStageManager(StageManager stageManager) {
	    this.stageManager = stageManager;
	}
	
	  @FXML
	    private void iniciarSesion(ActionEvent event) {
	        String user = txtUsuario.getText();
	        String contra = passContrasena.getText();

	        Usuario u = usuarioService.autenticacion(user, contra);

	        if (u != null) {
	          
	            switch (u.getPerfil()) {
	                case ADMINISTRADOR:
	                    stageManager.switchScene(FxmlView.ADMINISTRADOR);
	                    break;
	                case ESTUDIANTE:
	                    stageManager.switchScene(FxmlView.ESTUDIANTE);
	                    break;
	                case PROFESORADO:
	                    stageManager.switchScene(FxmlView.PROFESORADO);
	                    break;
	                case TUTOREMPRESA:
	                    stageManager.switchScene(FxmlView.TUTOREMPRESA);
	                    break;
	                default:
	                    lblLogin.setText("Perfil no reconocido");
	            }
	        } else {
	            lblLogin.setText("Usuario o contraseña incorrectos");
	        }
	    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
