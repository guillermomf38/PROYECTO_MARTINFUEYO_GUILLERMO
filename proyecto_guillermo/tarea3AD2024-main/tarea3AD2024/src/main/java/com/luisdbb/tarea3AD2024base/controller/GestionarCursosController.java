/**
 *Clase GestionarCursosController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.services.PeriodoPracticasService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import javafx.util.StringConverter;

@Controller
public class GestionarCursosController implements Initializable {
	@FXML
	private TextField txtNombrePeriodo;
	@FXML
	private TextField txtCursoAcademico;
	@FXML
	private DatePicker dpFechaInicio;
	@FXML
	private DatePicker dpFechaFin;
	@FXML
	private Label lblMensaje;
	@FXML
	private Button btnVolver;
	
	@FXML
	private Button btnGuardar;
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnLimpiar;
	@FXML
	private TableView<PeriodoPracticas> tabla;
	@FXML
	private TableColumn<PeriodoPracticas, String> colNombre;
	@FXML
	private TableColumn<PeriodoPracticas, String> colInicio;
	@FXML
	private TableColumn<PeriodoPracticas, String> colFin;
	@FXML
	private TableColumn<PeriodoPracticas, String> colCurso;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private PeriodoPracticasService periodoService;

	private PeriodoPracticas seleccionado;

	private final ObservableList<PeriodoPracticas> datos = FXCollections
			.observableArrayList();

	private static final DateTimeFormatter FMT = DateTimeFormatter
			.ofPattern("dd/MM/yyyy");

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		configurarDatePickers();
		configurarTabla();
		cargarDatos();
		
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	private void configurarDatePickers() {
		StringConverter<LocalDate> conv = new StringConverter<>() {
			@Override
			public String toString(LocalDate fecha) {
				return fecha != null ? fecha.format(FMT) : "";
			}

			@Override
			public LocalDate fromString(String s) {
				return (s != null && !s.isBlank()) ? LocalDate.parse(s, FMT)
						: null;
			}
		};
		dpFechaInicio.setConverter(conv);
		dpFechaFin.setConverter(conv);
	}

	private void configurarTabla() {
		colNombre.setCellValueFactory(
				new PropertyValueFactory<>("nombrePeriodo"));

		colInicio.setCellValueFactory(
				data -> new javafx.beans.property.SimpleStringProperty(
						data.getValue().getFechaInicio() != null
								? data.getValue().getFechaInicio().format(FMT)
								: ""));

		colFin.setCellValueFactory(
				data -> new javafx.beans.property.SimpleStringProperty(
						data.getValue().getFechaFin() != null
								? data.getValue().getFechaFin().format(FMT)
								: ""));

		colCurso.setCellValueFactory(
				new PropertyValueFactory<>("cursoAcademico"));

		tabla.setItems(datos);

		tabla.getSelectionModel().selectedItemProperty()
				.addListener((obs, ant, nuevo) -> {
					if (nuevo != null) {
						seleccionado = nuevo;
						rellenarFormulario(nuevo);
					}
				});
	}

	private void cargarDatos() {
		datos.setAll(periodoService.findAll());
	}

	private void rellenarFormulario(PeriodoPracticas p) {
		txtNombrePeriodo.setText(p.getNombrePeriodo());
		txtCursoAcademico.setText(p.getCursoAcademico());
		dpFechaInicio.setValue(p.getFechaInicio());
		dpFechaFin.setValue(p.getFechaFin());
		limpiarMensaje();
	}

	@FXML
	private void guardar(ActionEvent e) {
		if (!validar())
			return;

		try {
			PeriodoPracticas nuevo = new PeriodoPracticas(
					txtNombrePeriodo.getText().trim(), dpFechaInicio.getValue(),
					dpFechaFin.getValue(), txtCursoAcademico.getText().trim());
			periodoService.save(nuevo);
			cargarDatos();
			limpiar(null);
			ok("Periodo guardado correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	@FXML
	private void modificar(ActionEvent e) {
		if (seleccionado == null) {
			error("Selecciona un periodo de la tabla para modificar.");
			return;
		}
		if (!validar())
			return;

		try {
			seleccionado.setNombrePeriodo(txtNombrePeriodo.getText().trim());
			seleccionado.setFechaInicio(dpFechaInicio.getValue());
			seleccionado.setFechaFin(dpFechaFin.getValue());
			seleccionado.setCursoAcademico(txtCursoAcademico.getText().trim());
			periodoService.update(seleccionado);
			cargarDatos();
			limpiar(null);
			ok("Periodo modificado correctamente.");
		} catch (Exception ex) {
			error("Error al modificar: " + ex.getMessage());
		}
	}

	@FXML
	private void eliminar(ActionEvent e) {
		if (seleccionado == null) {
			error("Selecciona un periodo de la tabla para eliminar.");
			return;
		}

		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Confirmar eliminación");
		confirm.setHeaderText(null);
		confirm.setContentText(
				"¿Eliminar el periodo '" + seleccionado.getNombrePeriodo()
						+ "'?\n" + "Esta acción no se puede deshacer.");

		confirm.showAndWait().ifPresent(resp -> {
			if (resp == ButtonType.OK) {
				try {
					periodoService.delete(seleccionado.getIdPeriodo());
					cargarDatos();
					limpiar(null);
					ok("Periodo eliminado correctamente.");
				} catch (Exception ex) {
					error("No se puede eliminar: el periodo tiene formaciones "
							+ "asociadas. Elimínalas primero.");
				}
			}
		});
	}

	@FXML
	private void limpiar(ActionEvent e) {
		txtNombrePeriodo.clear();
		txtCursoAcademico.clear();
		dpFechaInicio.setValue(null);
		dpFechaFin.setValue(null);
		tabla.getSelectionModel().clearSelection();
		seleccionado = null;
		limpiarMensaje();
	}

	@FXML
	private void volver(ActionEvent e) {
		stageManager.switchScene(FxmlView.ADMINISTRADOR);
	}

	private boolean validar() {
		if (txtNombrePeriodo.getText().isBlank()
				|| txtCursoAcademico.getText().isBlank()) {
			error("El nombre del periodo y el curso académico son obligatorios.");
			return false;
		}
		if (dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null) {
			error("Selecciona las fechas de inicio y fin del periodo.");
			return false;
		}

		if (!dpFechaFin.getValue().isAfter(dpFechaInicio.getValue())) {
			error("La fecha de fin debe ser posterior a la fecha de inicio.");
			return false;
		}
		return true;
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill: #27ae60;");
		lblMensaje.setText("Correcto: " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill: #c0392b;");
		lblMensaje.setText("Error: " + msg);
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
