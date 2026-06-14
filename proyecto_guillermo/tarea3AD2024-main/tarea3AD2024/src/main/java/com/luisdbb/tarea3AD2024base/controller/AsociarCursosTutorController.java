/**
 *Clase AsociarCursosTutorController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see PeriodoPracticasService
 *@see ProfesradoService
 *
 *
 *Controller de la pantalla para asociar periodos de prácticas a tutores de centro.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.modelo.Profesorado;
import com.luisdbb.tarea3AD2024base.services.PeriodoPracticasService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Controller
public class AsociarCursosTutorController implements Initializable {
	@FXML
	private ComboBox<Profesorado> cboTutor;

	@FXML
	private TableView<PeriodoPracticas> tablaDisponibles;
	@FXML
	private TableColumn<PeriodoPracticas, String> colDispNombre;
	@FXML
	private TableColumn<PeriodoPracticas, String> colDispCurso;

	@FXML
	private TableView<PeriodoPracticas> tablaAsignados;
	@FXML
	private TableColumn<PeriodoPracticas, String> colAsigNombre;
	@FXML
	private TableColumn<PeriodoPracticas, String> colAsigCurso;
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnAsignar;
	@FXML
	private Button btnDesasignar;

	@FXML
	private Label lblMensaje;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private ProfesoradoService profesoradoService;
	@Autowired
	private PeriodoPracticasService periodoService;

	/**
	 * Periodos sin coordinador asignado o con un coordinador diferente al
	 * seleccionado.
	 */
	private final ObservableList<PeriodoPracticas> disponibles = FXCollections
			.observableArrayList();

	/** Periodos cuyo coordinador es el tutor actualmente seleccionado. */
	private final ObservableList<PeriodoPracticas> asignados = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		configurarCboTutor();
		configurarTablas();
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	/**
	 * Configura el ComboBox mostrando "Nombre Apellidos" de cada tutor.
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

	/**
	 * Configura las columnas de ambas tablas y las vincula a sus listas
	 * observables.
	 */
	private void configurarTablas() {
		colDispNombre.setCellValueFactory(
				new PropertyValueFactory<>("nombrePeriodo"));
		colDispCurso.setCellValueFactory(
				new PropertyValueFactory<>("cursoAcademico"));
		tablaDisponibles.setItems(disponibles);

		colAsigNombre.setCellValueFactory(
				new PropertyValueFactory<>("nombrePeriodo"));
		colAsigCurso.setCellValueFactory(
				new PropertyValueFactory<>("cursoAcademico"));
		tablaAsignados.setItems(asignados);
	}

	@FXML
	private void onTutorSeleccionado(ActionEvent event) {
		Profesorado tutor = cboTutor.getValue();
		if (tutor == null)
			return;
		List<PeriodoPracticas> todos = periodoService.findAll();
		Long idTutor = tutor.getIdUsuario();

		asignados.setAll(todos.stream()
				.filter(p -> p.getCoordinador() != null
						&& idTutor.equals(p.getCoordinador().getIdUsuario()))
				.collect(Collectors.toList()));

		disponibles.setAll(todos.stream()
				.filter(p -> p.getCoordinador() == null
						|| !idTutor.equals(p.getCoordinador().getIdUsuario()))
				.collect(Collectors.toList()));

		limpiarMensaje();
	}

	/**
	 * Asigna el periodo seleccionado en la tabla izquierda al tutor del
	 * ComboBox. Establece el campo {@code coordinador} del periodo y lo
	 * actualiza en BD.
	 *
	 * @param event evento del botón Asignar
	 */
	@FXML
	private void asignar(ActionEvent event) {
		Profesorado tutor = cboTutor.getValue();
		PeriodoPracticas p = tablaDisponibles.getSelectionModel()
				.getSelectedItem();

		if (tutor == null) {
			error("Selecciona un tutor primero.");
			return;
		}
		if (p == null) {
			error("Selecciona un periodo de la lista izquierda.");
			return;
		}

		try {
			p.setCoordinador(tutor);
			periodoService.update(p);

			disponibles.remove(p);
			asignados.add(p);
			ok("Periodo '" + p.getNombrePeriodo() + "' asignado a "
					+ tutor.getNombre() + " " + tutor.getApellidos() + ".");
		} catch (Exception ex) {
			error("Error al asignar: " + ex.getMessage());
		}
	}

	/**
	 * Quita el coordinador del periodo seleccionado en la tabla derecha. Pone
	 * {@code coordinador} a {@code null} y actualiza en BD.
	 *
	 * @param event evento del botón ← Quitar
	 */
	@FXML
	private void desasignar(ActionEvent event) {
		PeriodoPracticas p = tablaAsignados.getSelectionModel()
				.getSelectedItem();
		if (p == null) {
			error("Selecciona un periodo de la lista derecha.");
			return;
		}

		try {
			p.setCoordinador(null);
			periodoService.update(p);
			asignados.remove(p);
			disponibles.add(p);
			ok("Periodo '" + p.getNombrePeriodo() + "' desasignado.");
		} catch (Exception ex) {
			error("Error al desasignar: " + ex.getMessage());
		}
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

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto: " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error: " + msg);
	}

	private void limpiarMensaje() {
		lblMensaje.setText("");
	}

}
