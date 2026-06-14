/**
 *Clase GestionarTutoresController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.services.ProfesoradoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import componente.MensajeLabel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class GestionarTutoresController implements Initializable {
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField passContrasena;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtEmail;
	
	
	@FXML 
	private TextField txtBuscar;
	@FXML
	private MensajeLabel lblMensaje;
	
	
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	
	@FXML
	private Button btnGuardar;
	
	@FXML
	private Button btnModificar;
	
	@FXML
	private Button btnEliminar;
	
	@FXML 
	private Button btnLimpiar;

	@FXML
	private TableView<Profesorado> tabla;
	@FXML
	private TableColumn<Profesorado, String> colUsuario;
	@FXML
	private TableColumn<Profesorado, String> colNombre;
	@FXML
	private TableColumn<Profesorado, String> colApellidos;
	@FXML
	private TableColumn<Profesorado, String> colEmail;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ProfesoradoService profesoradoService;

	private Profesorado seleccionado;
	
	private List<Profesorado> todosLosTutores = new ArrayList<>();

	private final ObservableList<Profesorado> datos = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		configurarTabla();
		cargarDatos();
		configurarBuscador();
		
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}
	
	private void configurarBuscador() {
	    txtBuscar.textProperty().addListener((observable, anterior, nuevo) -> {
	        if (nuevo == null || nuevo.isBlank()) {
	            
	            datos.setAll(todosLosTutores);
	        } else {
	            String filtro = nuevo.toLowerCase().trim();
	            datos.setAll(todosLosTutores.stream()
	                    .filter(p ->
	                            p.getNombre().toLowerCase().contains(filtro)
	                            || p.getApellidos().toLowerCase().contains(filtro)
	                            || p.getUsuario().toLowerCase().contains(filtro))
	                    .toList());
	        }
	    });
	}

	private void configurarTabla() {
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		tabla.setItems(datos);

		tabla.getSelectionModel().selectedItemProperty()
				.addListener((obs, anterior, nuevo) -> {
					if (nuevo != null) {
						seleccionado = nuevo;
						rellenarFormulario(nuevo);
					}
				});
	}

	private void cargarDatos() {
		 todosLosTutores = profesoradoService.findAll();
		    String filtroActual = txtBuscar != null ? txtBuscar.getText() : "";
		    if (filtroActual.isBlank()) {
		        datos.setAll(todosLosTutores);
		    } else {
		        String filtro = filtroActual.toLowerCase().trim();
		        datos.setAll(todosLosTutores.stream()
		                .filter(p ->
		                        p.getNombre().toLowerCase().contains(filtro)
		                        || p.getApellidos().toLowerCase().contains(filtro)
		                        || p.getUsuario().toLowerCase().contains(filtro))
		                .toList());
		    }
	}

	private void rellenarFormulario(Profesorado p) {
		txtUsuario.setText(p.getUsuario());
		passContrasena.clear();
		txtNombre.setText(p.getNombre());
		txtApellidos.setText(p.getApellidos());
		txtEmail.setText(p.getEmail());
		lblMensaje.limpiar();
	}
	@FXML
	private void limpiarBusqueda(ActionEvent event) {
	    txtBuscar.clear();
	    datos.setAll(todosLosTutores);
	}
	@FXML
	private void guardar(ActionEvent e) {
		if (!validarCamposObligatorios())
			return;

		String usuario = txtUsuario.getText().trim();
		String contrasena = passContrasena.getText();
		String nombre = txtNombre.getText().trim();
		String apellidos = txtApellidos.getText().trim();
		String email = txtEmail.getText().trim();

		if (contrasena.isBlank()) {
		
			lblMensaje.showError(" La contraseña es obligatoria al crear un tutor.");
			return;
		}
		if (profesoradoService.existeUsuario(usuario)) {
			
			lblMensaje.showError(" El nombre de usuario '" + usuario + "' ya esta en uso.");
			return;
		}
		if (profesoradoService.existeEmail(email)) {
			
			lblMensaje.showError(" El email '" + email + "' ya está registrado.");
			return;
		}

		try {
			Profesorado nuevo = new Profesorado(usuario, contrasena, nombre,
					apellidos, email);
			profesoradoService.save(nuevo);
			cargarDatos();
			limpiar(null);
			
			lblMensaje.showOk(" Tutor '" + nombre + " " + apellidos
					+ "' creado correctamente.");
		} catch (Exception ex) {
		
			lblMensaje.showError(" Error al guardar: " + ex.getMessage());
		}
	}

	@FXML
	private void modificar(ActionEvent e) {
		if (seleccionado == null) {
			
			lblMensaje.showError(" Selecciona un tutor de la tabla para modificar.");
			
			return;
		}
		if (!validarCamposObligatorios())
			return;

		String nuevaContra = passContrasena.getText();
		String email = txtEmail.getText().trim();

		if (!email.equals(seleccionado.getEmail())
				&& profesoradoService.existeEmail(email)) {
		
			
			lblMensaje.showError(" El email '" + email
					+ "' ya está registrado por otro tutor.");
			return;
		}

		try {
			seleccionado.setNombre(txtNombre.getText().trim());
			seleccionado.setApellidos(txtApellidos.getText().trim());
			seleccionado.setEmail(email);
			if (!nuevaContra.isBlank()) {
				seleccionado.setContrasena(nuevaContra);
			}
			profesoradoService.update(seleccionado);
			cargarDatos();
			limpiar(null);
			
			lblMensaje.showOk(" Tutor modificado correctamente.");
		} catch (Exception ex) {
			
			lblMensaje.showError(" Error al modificar: " + ex.getMessage());
		}
	}

	@FXML
	private void eliminar(ActionEvent e) {
		if (seleccionado == null) {
			
			lblMensaje.showError(" Selecciona un tutor de la tabla para eliminar.");
			return;
		}

		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Confirmar eliminación");
		confirm.setHeaderText(null);
		confirm.setContentText("¿Eliminar al tutor '" + seleccionado.getNombre()
				+ " " + seleccionado.getApellidos()
				+ "'?\nEsta acción no se puede deshacer.");

		confirm.showAndWait().ifPresent(respuesta -> {
			if (respuesta == ButtonType.OK) {
				try {
					profesoradoService.delete(seleccionado.getIdUsuario());
					cargarDatos();
					limpiar(null);
					lblMensaje.showOk(" Tutor eliminado"
							+ " correctamente.");

				} catch (Exception ex) {
					
					
					lblMensaje.showError(" No se puede eliminar: el tutor tiene estudiantes "
							+ "o periodos asignados. Reasígnalos primero.");
				}
			}
		});
	}

	@FXML
	private void limpiar(ActionEvent e) {
		txtUsuario.clear();
		passContrasena.clear();
		txtNombre.clear();
		txtApellidos.clear();
		txtEmail.clear();
		tabla.getSelectionModel().clearSelection();
		seleccionado = null;
		lblMensaje.limpiar();
	}

	@FXML
	private void volver(ActionEvent e) {
		stageManager.switchScene(FxmlView.ADMINISTRADOR);
	}

	private boolean validarCamposObligatorios() {
		if (txtUsuario.getText().isBlank() || txtNombre.getText().isBlank()
				|| txtApellidos.getText().isBlank()
				|| txtEmail.getText().isBlank()) {
			lblMensaje.showError(" Usuario, nombre, apellidos y email son obligatorios.");
			return false;
		}
		return true;
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
