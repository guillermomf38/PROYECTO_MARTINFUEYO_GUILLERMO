/**
 *Clase CalificarEstudianteController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see SeguimientoService
 *@see FormacionEmpresaService
 *Controller de la pantalla de calificación de estudiantes.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Seguimiento;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
import com.luisdbb.tarea3AD2024base.services.SeguimientoService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Controller
public class CalificarEstudianteController implements Initializable {
	@FXML
	private ComboBox<Estudiante> cboEstudiante;
	@FXML
	private ComboBox<String> cboValoracion;
	@FXML
	private DatePicker dpFecha;
	@FXML
	private TextArea txtObservaciones;
	@FXML
	private Label lblMensaje;

	@FXML
	private TableView<Seguimiento> tablaValoraciones;
	@FXML
	private TableColumn<Seguimiento, String> colFecha;
	@FXML
	private TableColumn<Seguimiento, String> colValoracion;
	@FXML
	private TableColumn<Seguimiento, String> colObservacion;
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnLimpiar;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private FormacionEmpresaService formacionService;
	@Autowired
	private SeguimientoService seguimientoService;

	/** Formación activa del estudiante seleccionado. */
	private FormacionEmpresa formacionSeleccionada;

	/** Lista observable vinculada a la tabla de valoraciones. */
	private final ObservableList<Seguimiento> datosValoraciones = FXCollections
			.observableArrayList();

	private static final DateTimeFormatter FMT = DateTimeFormatter
			.ofPattern("dd/MM/yyyy");

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		cboValoracion.setItems(FXCollections.observableArrayList("APTO",
				"NO APTO", "PENDIENTE"));
		dpFecha.setValue(LocalDate.now());
		cargarComboEstudiantes();
		configurarTabla();
		
		openAyuda.setAccelerator(
		        new KeyCodeCombination(KeyCode.F1)
		    );
	}

	/**
	 * Carga en el combo solo los estudiantes con formación activa supervisada
	 * por el tutor de empresa logueado.
	 */
	private void cargarComboEstudiantes() {
		 TutorEmpresa tutor = sesionService.getTutorEmpresaLogueado();
		    if (tutor == null) return;

		    List<Estudiante> estudiantes = formacionService
		            .findActivasByTutorId(tutor.getIdUsuario())
		            .stream()
		            .map(FormacionEmpresa::getEstudiante)
		            .toList();

		    cboEstudiante.setItems(FXCollections.observableArrayList(estudiantes));
		    cboEstudiante.setConverter(new StringConverter<>() {
		        @Override public String toString(Estudiante e) {
		            return e == null ? "" : e.getNombre() + " " + e.getApellidos();
		        }
		        @Override public Estudiante fromString(String s) { return null; }
		    });
	}

	/**
	 * Configura las columnas de la tabla de valoraciones. Fecha usa lambda para
	 * formatear como dd/MM/yyyy.
	 */
	private void configurarTabla() {
		colFecha.setCellValueFactory(
				d -> new SimpleStringProperty(d.getValue().getFecha() != null
						? d.getValue().getFecha().format(FMT)
						: ""));
		colValoracion
				.setCellValueFactory(new PropertyValueFactory<>("valoracion"));
		colObservacion.setCellValueFactory(
				new PropertyValueFactory<>("observaciones"));
		tablaValoraciones.setItems(datosValoraciones);
	}

	/**
	 * Al seleccionar un estudiante carga su historial de calificaciones en la
	 * tabla, filtrando solo las entradas de tipo valoración.
	 *
	 * @param event evento del ComboBox
	 */
	@FXML
	private void onEstudianteSeleccionado(ActionEvent event) {
		Estudiante est = cboEstudiante.getValue();
		if (est == null)
			return;
		formacionService.findActivaByEstudianteId(est.getIdUsuario())
        .ifPresent(f -> {
			formacionSeleccionada = f;
			datosValoraciones.setAll(seguimientoService.findByFormacion(f)
					.stream()
					.filter(s -> s.getValoracion() != null
							&& (s.getValoracion().equals("APTO")
									|| s.getValoracion().equals("NO APTO")
									|| s.getValoracion().equals("PENDIENTE")))
					.toList());
		});
	}

	/**
	 * Guarda una nueva valoración del estudiante seleccionado.
	 *
	 * @param event evento del botón Guardar valoración
	 */
	@FXML
	private void guardar(ActionEvent event) {
		if (cboEstudiante.getValue() == null) {
			error("Selecciona un estudiante.");
			return;
		}
		if (formacionSeleccionada == null) {
			error("El estudiante no tiene formación activa.");
			return;
		}
		if (cboValoracion.getValue() == null) {
			error("Selecciona una valoración.");
			return;
		}
		if (dpFecha.getValue() == null) {
			error("Selecciona la fecha.");
			return;
		}

		try {
			Seguimiento val = new Seguimiento(txtObservaciones.getText().trim(),
					cboValoracion.getValue(), dpFecha.getValue(),
					formacionSeleccionada);
			seguimientoService.save(val);
			onEstudianteSeleccionado(null);
			limpiar(null);
			ok("Valoración guardada correctamente.");
		} catch (Exception ex) {
			error("Error al guardar: " + ex.getMessage());
		}
	}

	/**
	 * Limpia los campos del formulario manteniendo el estudiante seleccionado.
	 *
	 * @param event evento del botón Limpiar
	 */
	@FXML
	private void limpiar(ActionEvent event) {
		cboValoracion.setValue(null);
		dpFecha.setValue(LocalDate.now());
		txtObservaciones.clear();
		limpiarMensaje();
	}

	/**
	 * Navega de vuelta al menú del tutor de empresa.
	 *
	 * @param event evento del botón Volver
	 */
	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.TUTOREMPRESA);
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
		lblMensaje.setText("Correcto:  " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error: " + msg);
	}

	private void limpiarMensaje() {
		lblMensaje.setText("");
	}

}
