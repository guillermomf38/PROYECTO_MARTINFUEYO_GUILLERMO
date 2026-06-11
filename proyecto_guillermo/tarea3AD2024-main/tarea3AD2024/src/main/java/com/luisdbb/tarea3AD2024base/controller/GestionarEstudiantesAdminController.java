/**
 *Clase GestionarEstudiantesAdminController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see EstudianteService
 *@see ProfesradoService
 *Controller de la pantalla de gestión de estudiantes para el perfil Administrador.
 *
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.services.EstudianteService;
import com.luisdbb.tarea3AD2024base.services.ProfesoradoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.beans.property.SimpleStringProperty;

@Controller
public class GestionarEstudiantesAdminController implements Initializable {
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField passContrasena;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellidos;
	@FXML
	private ComboBox<String> cboCurso;
	@FXML
	private ComboBox<Profesorado> cboTutor;
	@FXML
	private Label lblMensaje;

	@FXML
	private TableView<Estudiante> tabla;
	@FXML
	private TableColumn<Estudiante, String> colUsuario;
	@FXML
	private TableColumn<Estudiante, String> colNombre;
	@FXML
	private TableColumn<Estudiante, String> colApellidos;
	@FXML
	private TableColumn<Estudiante, String> colCurso;
	@FXML
	private TableColumn<Estudiante, String> colTutor;
	
	@FXML
	private Button btnVolver;
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

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private EstudianteService estudianteService;
	@Autowired
	private ProfesoradoService profesoradoService;

	/** Estudiante actualmente seleccionado en la tabla. */
	private Estudiante seleccionado;

	private final ObservableList<Estudiante> datos = FXCollections
			.observableArrayList();

	/** Códigos de curso del departamento de Informática. */
	private static final List<String> CURSOS = List.of("1IFC303", "2IFC303",
			"1VIFC303", "2VIFC303", "1@IFC303", "2@IFC303", "1VIFC302",
			"2VIFC302");

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		cboCurso.setItems(FXCollections.observableArrayList(CURSOS));
		configurarCboTutor();
		configurarTabla();
		cargarDatos();
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	/**
	 * Configura el ComboBox de tutores con un StringConverter para mostrar
	 */
	private void configurarCboTutor() {
		cboTutor.setItems(
				FXCollections.observableArrayList(profesoradoService.findAll()));
		cboTutor.setConverter(new StringConverter<>() {
			@Override
			public String toString(Profesorado p) {
				return p == null ? "" : p.getNombre() + " " + p.getApellidos();
			}

			@Override
			public Profesorado fromString(String s) {
				return null;
			}
		});
	}

	private void configurarTabla() {
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colApellidos
				.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
		colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

		colTutor.setCellValueFactory(data -> {
			Profesorado p = data.getValue().getProfesorado();
			return new SimpleStringProperty(
					p != null ? p.getNombre() + " " + p.getApellidos()
							: "Sin asignar");
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

	/** Recarga todos los estudiantes desde la base de datos. */
	private void cargarDatos() {
		datos.setAll(estudianteService.findAll());
	}

	/**
	 * Rellena el formulario con los datos del estudiante seleccionado en la
	 * tabla.
	 *
	 * @param e estudiante seleccionado
	 */
	private void rellenarFormulario(Estudiante e) {
		txtUsuario.setText(e.getUsuario());
		passContrasena.clear();
		txtNombre.setText(e.getNombre());
		txtApellidos.setText(e.getApellidos());
		cboCurso.setValue(e.getCurso());
		cboTutor.setValue(e.getProfesorado());
		limpiarMensaje();
	}

	/**
	 * Crea un nuevo estudiante con los datos del formulario. Valida campos
	 * obligatorios y unicidad del nombre de usuario.
	 *
	 * @param event evento del botón Guardar
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (!validar(true))
			return;
		try {
			Estudiante nuevo = new Estudiante(txtUsuario.getText().trim(),
					passContrasena.getText(), txtNombre.getText().trim(),
					txtApellidos.getText().trim(),
					cboCurso.getValue() != null ? cboCurso.getValue() : "");
			nuevo.setProfesorado(cboTutor.getValue());
			estudianteService.save(nuevo);
			cargarDatos();
			limpiar(null);
			ok("Estudiante creado correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Actualiza los datos del estudiante seleccionado.
	 *
	 * @param event evento del botón Modificar
	 */
	@FXML
	private void modificar(ActionEvent event) {
		if (seleccionado == null) {
			error("Selecciona un estudiante para modificar.");
			return;
		}
		if (!validar(false))
			return;
		try {
			seleccionado.setNombre(txtNombre.getText().trim());
			seleccionado.setApellidos(txtApellidos.getText().trim());
			if (cboCurso.getValue() != null)
				seleccionado.setCurso(cboCurso.getValue());
			seleccionado.setProfesorado(cboTutor.getValue());
			if (!passContrasena.getText().isBlank())
				seleccionado.setContrasena(passContrasena.getText());
			estudianteService.update(seleccionado);
			cargarDatos();
			limpiar(null);
			ok("Estudiante modificado correctamente.");
		} catch (Exception ex) {
			error("Error al modificar: " + ex.getMessage());
		}
	}

	/**
	 * Elimina el estudiante seleccionado tras confirmación.
	 *
	 * @param event evento del botón Eliminar
	 */
	@FXML
	private void eliminar(ActionEvent event) {
		if (seleccionado == null) {
			error("Selecciona un estudiante para eliminar.");
			return;
		}
		Alert c = new Alert(Alert.AlertType.CONFIRMATION);
		c.setHeaderText(null);
		c.setContentText("¿Eliminar a " + seleccionado.getNombre() + " "
				+ seleccionado.getApellidos() + "?");
		c.showAndWait().ifPresent(r -> {
			if (r == ButtonType.OK) {
				try {
					estudianteService.delete(seleccionado.getIdUsuario());
					cargarDatos();
					limpiar(null);
					ok("Estudiante eliminado.");
				} catch (Exception ex) {
					error("No se puede eliminar: tiene formaciones asociadas.");
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
		cboCurso.setValue(null);
		cboTutor.setValue(null);
		tabla.getSelectionModel().clearSelection();
		seleccionado = null;
		limpiarMensaje();
	}

	/**
	 * Navega de vuelta al menú del administrador.
	 *
	 * @param event evento del botón Volver
	 */
	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.ADMINISTRADOR);
	}

	/**
	 * Valida los campos mínimos del formulario.
	 *
	 * @param esNuevo {@code true} si es una creación (exige contraseña y
	 *                unicidad de usuario)
	 * @return {@code true} si la validación pasa
	 */
	private boolean validar(boolean esNuevo) {
		if (txtUsuario.getText().isBlank() || txtNombre.getText().isBlank()
				|| txtApellidos.getText().isBlank()) {
			error("Usuario, nombre y apellidos son obligatorios.");
			return false;
		}
		if (esNuevo) {
			if (passContrasena.getText().isBlank()) {
				error("La contraseña es obligatoria al crear un estudiante.");
				return false;
			}

			boolean existe = estudianteService.findAll().stream().anyMatch(
					e -> e.getUsuario().equals(txtUsuario.getText().trim()));
			if (existe) {
				error("Ese nombre de usuario ya existe.");
				return false;
			}
		}
		return true;
	}

	public Profesorado getProfesorado() {
		return null;
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto" + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error" + msg);
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
