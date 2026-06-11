/**
 *Clase GestionarTutorEmpresaController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see TutorEmpresaService
 *@see EmpresaService
 *
 *Controller de la pantalla de gestión de tutores de empresa.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.services.EmpresaService;
import com.luisdbb.tarea3AD2024base.services.TutorEmpresaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import javafx.util.StringConverter;

@Controller
public class GestionarTutorEmpresaController implements Initializable {
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
	private ComboBox<Empresa> cboEmpresa;
	@FXML
	private Label lblMensaje;
	@FXML
	private Button btnGuardar;

	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnLimpiar;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;

	@FXML
	private TableView<TutorEmpresa> tabla;
	@FXML
	private TableColumn<TutorEmpresa, String> colUsuario;
	@FXML
	private TableColumn<TutorEmpresa, String> colNombre;
	@FXML
	private TableColumn<TutorEmpresa, String> colApellidos;
	@FXML
	private TableColumn<TutorEmpresa, String> colEmail;
	@FXML
	private TableColumn<TutorEmpresa, String> colEmpresa;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private TutorEmpresaService tutorEmpresaService;
	@Autowired
	private EmpresaService empresaService;

	/** Tutor de empresa seleccionado en la tabla. */
	private TutorEmpresa seleccionado;

	/** Lista observable vinculada a la tabla. */
	private final ObservableList<TutorEmpresa> datos = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		configurarCboEmpresa();
		configurarTabla();
		cargarDatos();

		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	private void configurarCboEmpresa() {
		cboEmpresa.setItems(
				FXCollections.observableArrayList(empresaService.findAll()));
		cboEmpresa.setConverter(new StringConverter<>() {
			@Override
			public String toString(Empresa e) {
				return e == null ? "" : e.getNombre();
			}

			@Override
			public Empresa fromString(String s) {
				return null;
			}
		});
	}

	private void configurarTabla() {
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

		colEmpresa.setCellValueFactory(data -> {
			Empresa e = data.getValue().getEmpresa();
			return new SimpleStringProperty(
					e != null ? e.getNombre() : "Sin empresa");
		});

		tabla.setItems(datos);
		tabla.getSelectionModel().selectedItemProperty()
				.addListener((obs, ant, nuevo) -> {
					if (nuevo != null) {
						seleccionado = nuevo;
						rellenarFormulario(nuevo);
					}
				});
	}

	/** Recarga todos los tutores de empresa desde la base de datos. */
	private void cargarDatos() {
		datos.setAll(tutorEmpresaService.findAll());
	}

	/**
	 * Rellena el formulario con los datos del tutor seleccionado en la tabla.
	 *
	 * @param t tutor de empresa seleccionado
	 */
	private void rellenarFormulario(TutorEmpresa t) {
		txtUsuario.setText(t.getUsuario());
		passContrasena.clear();
		txtNombre.setText(t.getNombre());
		txtApellidos.setText(t.getApellidos());
		txtEmail.setText(t.getEmail());
		cboEmpresa.setValue(t.getEmpresa());
		limpiarMensaje();
	}

	/**
	 * Crea un nuevo tutor de empresa.
	 *
	 * @param event evento del botón Guardar
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (!validarObligatorios())
			return;
		if (passContrasena.getText().isBlank()) {
			error("La contraseña es obligatoria al crear.");
			return;
		}
		if (cboEmpresa.getValue() == null) {
			error("Selecciona la empresa del tutor.");
			return;
		}
		if (tutorEmpresaService.existeUsuario(txtUsuario.getText().trim())) {
			error("Ese nombre de usuario ya existe.");
			return;
		}
		if (tutorEmpresaService.existeEmail(txtEmail.getText().trim())) {
			error("Ese email ya está registrado.");
			return;
		}
		try {
			TutorEmpresa nuevo = new TutorEmpresa(txtUsuario.getText().trim(),
					passContrasena.getText(), txtNombre.getText().trim(),
					txtApellidos.getText().trim(), txtEmail.getText().trim());
			nuevo.setEmpresa(cboEmpresa.getValue());
			tutorEmpresaService.save(nuevo);
			cargarDatos();
			limpiar(null);
			ok("Tutor de empresa creado correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Actualiza los datos del tutor de empresa seleccionado. Contraseña y
	 * empresa pueden mantenerse si se dejan sin cambiar.
	 *
	 * @param event evento del botón Modificar
	 */
	@FXML
	private void modificar(ActionEvent event) {
		if (seleccionado == null) {
			error("Selecciona un tutor para modificar.");
			return;
		}
		if (!validarObligatorios())
			return;
		String email = txtEmail.getText().trim();
		if (!email.equals(seleccionado.getEmail())
				&& tutorEmpresaService.existeEmail(email)) {
			error("Ese email ya está en uso por otro tutor.");
			return;
		}
		try {
			seleccionado.setNombre(txtNombre.getText().trim());
			seleccionado.setApellidos(txtApellidos.getText().trim());
			seleccionado.setEmail(email);
			if (cboEmpresa.getValue() != null)
				seleccionado.setEmpresa(cboEmpresa.getValue());
			if (!passContrasena.getText().isBlank())
				seleccionado.setContrasena(passContrasena.getText());
			tutorEmpresaService.update(seleccionado);
			cargarDatos();
			limpiar(null);
			ok("Tutor de empresa modificado correctamente.");
		} catch (Exception ex) {
			error("Error al modificar: " + ex.getMessage());
		}
	}

	/**
	 * Elimina el tutor de empresa seleccionado tras confirmación.
	 *
	 * @param event evento del botón Eliminar
	 */
	@FXML
	private void eliminar(ActionEvent event) {
		if (seleccionado == null) {
			error("Selecciona un tutor para eliminar.");
			return;
		}
		Alert c = new Alert(Alert.AlertType.CONFIRMATION);
		c.setHeaderText(null);
		c.setContentText("¿Eliminar al tutor '" + seleccionado.getNombre() + " "
				+ seleccionado.getApellidos() + "'?");
		c.showAndWait().ifPresent(r -> {
			if (r == ButtonType.OK) {
				try {
					tutorEmpresaService.delete(seleccionado.getIdUsuario());
					cargarDatos();
					limpiar(null);
					ok("Tutor de empresa eliminado.");
				} catch (Exception ex) {
					error("No se puede eliminar: tiene formaciones activas asociadas.");
				}
			}
		});
	}

	/**
	 * Limpia el formulario y deselecciona la tabla.
	 *
	 * @param event evento del botón Limpiar
	 */
	@FXML
	private void limpiar(ActionEvent event) {
		txtUsuario.clear();
		passContrasena.clear();
		txtNombre.clear();
		txtApellidos.clear();
		txtEmail.clear();
		cboEmpresa.setValue(null);
		tabla.getSelectionModel().clearSelection();
		seleccionado = null;
		limpiarMensaje();
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
	 * Valida que usuario, nombre, apellidos y email no estén vacíos.
	 *
	 * @return {@code true} si la validación pasa
	 */
	private boolean validarObligatorios() {
		if (txtUsuario.getText().isBlank() || txtNombre.getText().isBlank()
				|| txtApellidos.getText().isBlank()
				|| txtEmail.getText().isBlank()) {
			error("Usuario, nombre, apellidos y email son obligatorios.");
			return false;
		}
		return true;
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Eliminar " + msg);
	}

	private void limpiarMensaje() {
		lblMensaje.setText("");
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
