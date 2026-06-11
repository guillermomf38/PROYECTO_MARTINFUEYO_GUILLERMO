/**
 *Clase GestionarEmpresasController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see EmpresaService
 *@see Empresa
 *
 *Controller de la pantalla de gestión de empresas colaboradoras.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.services.EmpresaService;
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
public class GestionarEmpresasController implements Initializable {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtEmail;
	
	@FXML private MensajeLabel lblMensaje;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnLimpiar;

	@FXML
	private TableView<Empresa> tabla;
	@FXML
	private TableColumn<Empresa, String> colNombre;
	@FXML
	private TableColumn<Empresa, String> colDireccion;
	@FXML
	private TableColumn<Empresa, String> colTelefono;
	@FXML
	private TableColumn<Empresa, String> colEmail;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private EmpresaService empresaService;

	/** Empresa actualmente seleccionada en la tabla. */
	private Empresa seleccionada;

	/** Lista observable vinculada a la tabla. */
	private final ObservableList<Empresa> datos = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		configurarTabla();
		cargarDatos();
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	/**
	 * Enlaza columnas con los getters de {@link Empresa} mediante
	 * {@code PropertyValueFactory} y añade el listener de selección.
	 */
	private void configurarTabla() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colDireccion
				.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tabla.setItems(datos);
		tabla.getSelectionModel().selectedItemProperty()
				.addListener((obs, ant, nuevo) -> {
					if (nuevo != null) {
						seleccionada = nuevo;
						rellenarFormulario(nuevo);
					}
				});
	}

	/** Recarga todas las empresas desde la base de datos. */
	private void cargarDatos() {
		datos.setAll(empresaService.findAll());
	}

	/**
	 * Rellena el formulario con los datos de la empresa seleccionada en la
	 * tabla.
	 *
	 * @param e empresa seleccionada
	 */
	private void rellenarFormulario(Empresa e) {
		txtNombre.setText(e.getNombre());
		txtDireccion.setText(e.getDireccion());
		txtTelefono.setText(e.getTelefono());
		txtEmail.setText(e.getEmail());
		lblMensaje.limpiar();
	}

	/**
	 * Crea una nueva empresa con los datos del formulario. Valida que nombre y
	 * email no estén vacíos y que el email sea único.
	 *
	 * @param event evento del botón Guardar
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (!validar())
			return;
		String email = txtEmail.getText().trim();
		if (empresaService.existeEmail(email)) {
		
			lblMensaje.showError("El email '" + email
					+ "' ya está registrado por otra empresa.");
			return;
		}
		try {
			Empresa nueva = new Empresa(txtNombre.getText().trim(),
					txtDireccion.getText().trim(), txtTelefono.getText().trim(),
					email);
			empresaService.save(nueva);
			cargarDatos();
			limpiar(null);
			
			lblMensaje.showOk("Empresa guardada correctamente.");
			
		} catch (Exception ex) {
		
			lblMensaje.showError("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Actualiza los datos de la empresa seleccionada.
	 *
	 * @param event evento del botón Modificar
	 */
	@FXML
	private void modificar(ActionEvent event) {
		if (seleccionada == null) {
		
			lblMensaje.showError("Selecciona una empresa para modificar.");
			return;
		}
		if (!validar())
			return;
		String email = txtEmail.getText().trim();
		if (!email.equals(seleccionada.getEmail())
				&& empresaService.existeEmail(email)) {
			
			lblMensaje.showError("El email '" + email + "' ya está en uso por otra empresa.");
			return;
		}
		try {
			seleccionada.setNombre(txtNombre.getText().trim());
			seleccionada.setDireccion(txtDireccion.getText().trim());
			seleccionada.setTelefono(txtTelefono.getText().trim());
			seleccionada.setEmail(email);
			empresaService.update(seleccionada);
			cargarDatos();
			limpiar(null);
			
			lblMensaje.showOk("Empresa modificada correctamente.");
		} catch (Exception ex) {
			
			lblMensaje.showError("Error al modificar: " + ex.getMessage());
		}
	}

	/**
	 * Elimina la empresa seleccionada tras confirmación.
	 *
	 * @param event evento del botón Eliminar
	 */
	@FXML
	private void eliminar(ActionEvent event) {
		if (seleccionada == null) {
		
			lblMensaje.showError("Selecciona una empresa para eliminar.");
			return;
		}
		Alert c = new Alert(Alert.AlertType.CONFIRMATION);
		c.setHeaderText(null);
		c.setContentText(
				"¿Eliminar la empresa '" + seleccionada.getNombre() + "'?");
		c.showAndWait().ifPresent(r -> {
			if (r == ButtonType.OK) {
				try {
					empresaService.delete(seleccionada.getIdEmpresa());
					cargarDatos();
					limpiar(null);
				
					lblMensaje.showOk("Empresa eliminada.");
				} catch (Exception ex) {
				
					lblMensaje.showError("No se puede eliminar: la empresa tiene tutores o formaciones asociadas.");
				}
			}
		});
	}

	/**
	 * Limpia todos los campos del formulario y deselecciona la tabla.
	 *
	 * @param event evento del botón Limpiar
	 */
	@FXML
	private void limpiar(ActionEvent event) {
		txtNombre.clear();
		txtDireccion.clear();
		txtTelefono.clear();
		txtEmail.clear();
		tabla.getSelectionModel().clearSelection();
		seleccionada = null;
		lblMensaje.limpiar();
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

	/**
	 * Valida que nombre y email del formulario no estén vacíos.
	 *
	 * @return {@code true} si la validación pasa
	 */
	private boolean validar() {
		if (txtNombre.getText().isBlank() || txtEmail.getText().isBlank()) {
		
			lblMensaje.showError("Nombre y email son obligatorios.");
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
