/**
 *Clase LoginController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */



package controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	@FXML
	private Label lblLogin;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button btnSignIn;
	
	@FXML
	private void login(ActionEvent event) {
        System.out.println("Login correcto");
    }

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
