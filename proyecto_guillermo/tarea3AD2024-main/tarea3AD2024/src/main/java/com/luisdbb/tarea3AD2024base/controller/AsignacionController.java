/**
 *Clase AsignacionController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresaService
 *@see SesionService
 *
 *Controller de la pantalla de asignación de estudiantes a tutores de empresa.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Empresa;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.PeriodoPracticas;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.EmpresaService;
import com.luisdbb.tarea3AD2024base.services.EstudianteService;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
import com.luisdbb.tarea3AD2024base.services.PeriodoPracticasService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.services.TutorEmpresaService;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Controller
public class AsignacionController implements Initializable {

	@FXML
	private ComboBox<Estudiante> cboEstudiante;
	@FXML
	private ComboBox<PeriodoPracticas> cboPeriodo;
	@FXML
	private ComboBox<Empresa> cboEmpresa;
	@FXML
	private ComboBox<TutorEmpresa> cboTutor;
	@FXML
	private Label lblMensaje;
	
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnAsignar;
	@FXML
	private Button btnDesactivar;
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;

	@FXML
	private TableView<FormacionEmpresa> tabla;
	@FXML
	private TableColumn<FormacionEmpresa, String> colEstudiante;
	@FXML
	private TableColumn<FormacionEmpresa, String> colEmpresa;
	@FXML
	private TableColumn<FormacionEmpresa, String> colTutor;
	@FXML
	private TableColumn<FormacionEmpresa, String> colPeriodo;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private FormacionEmpresaService formacionService;
	@Autowired
	private EstudianteService estudianteService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private TutorEmpresaService tutorEmpresaService;
	@Autowired
	private PeriodoPracticasService periodoService;

	/** Lista observable vinculada a la tabla de formaciones activas. */
	private final ObservableList<FormacionEmpresa> datos = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		configurarCombos();
		configurarTabla();
		cargarDatos();
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	private void configurarCombos() {
		Usuario u = sesionService.getUsuarioLogueado();
	    List<Estudiante> estudiantes = (u != null)
	            ? estudianteService.findByProfesoradoId(u.getIdUsuario())
	            : estudianteService.findAll();

	    cboEstudiante.setItems(FXCollections.observableArrayList(estudiantes));
	    cboEstudiante.setConverter(strConv(e -> e.getNombre() + " " + e.getApellidos()));

	    cboPeriodo.setItems(FXCollections.observableArrayList(periodoService.findAll()));
	    cboPeriodo.setConverter(strConv(p -> p.getNombrePeriodo()
	            + " (" + p.getCursoAcademico() + ")"));

	    cboEmpresa.setItems(FXCollections.observableArrayList(empresaService.findAll()));
	    cboEmpresa.setConverter(strConv(Empresa::getNombre));

	    cboTutor.setConverter(strConv(t -> t.getNombre() + " " + t.getApellidos()));
	}

	private void configurarTabla() {
		colEstudiante
				.setCellValueFactory(d -> new SimpleStringProperty(
						d.getValue().getEstudiante() != null
								? d.getValue().getEstudiante().getNombre() + " "
										+ d.getValue().getEstudiante()
												.getApellidos()
								: ""));
		colEmpresa.setCellValueFactory(
				d -> new SimpleStringProperty(d.getValue().getEmpresa() != null
						? d.getValue().getEmpresa().getNombre()
						: ""));
		colTutor.setCellValueFactory(
				d -> new SimpleStringProperty(
						d.getValue().getTutorEmpresa() != null
								? d.getValue().getTutorEmpresa().getNombre()
										+ " "
										+ d.getValue().getTutorEmpresa()
												.getApellidos()
								: ""));
		colPeriodo.setCellValueFactory(d -> new SimpleStringProperty(
				d.getValue().getPeriodoPracticas() != null
						? d.getValue().getPeriodoPracticas().getNombrePeriodo()
						: ""));
		tabla.setItems(datos);
	}

	/** Recarga las formaciones activas en la tabla. */
	private void cargarDatos() {
	    try {
	        datos.setAll(formacionService.findByActivaTrue());
	    } catch (Exception e) {
	      
	        datos.clear();
	    }
	}
	/**
	 * Al seleccionar una empresa, recarga el combo de tutores filtrando solo
	 * los que pertenecen a esa empresa. Esto garantiza coherencia en la
	 * asignación.
	 *
	 * @param event evento del ComboBox de empresas
	 */
	@FXML
	private void onEmpresaSeleccionada(ActionEvent event) {
		Empresa empresa = cboEmpresa.getValue();
		if (empresa != null) {
			cboTutor.setItems(FXCollections.observableArrayList(
					tutorEmpresaService.findByEmpresa(empresa)));
			cboTutor.setValue(null);
		}
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

	/**
	 * Crea una nueva {@link FormacionEmpresa} con los datos seleccionados.
	 * Valida que todos los combos estén rellenos y que el estudiante no tenga
	 * ya una formación activa.
	 *
	 * @param event evento del botón Asignar
	 */

	/**
	 * Desactiva la formación del estudiante seleccionado en el combo. Se usa
	 * cuando la empresa devuelve al estudiante antes de que termine el periodo.
	 *
	 * @param event evento del botón Desactivar formación
	 */
	@FXML
	private void desactivar(ActionEvent event) {
		Estudiante est = cboEstudiante.getValue();
	    if (est == null) { error("Selecciona un estudiante."); return; }

	    formacionService.findActivaByEstudianteId(est.getIdUsuario())
	        .ifPresentOrElse(
	            f -> {
	                Alert c = new Alert(Alert.AlertType.CONFIRMATION);
	                c.setHeaderText(null);
	                c.setContentText("¿Desactivar la formación de "
	                        + est.getNombre() + " " + est.getApellidos() + "?");
	                c.showAndWait().ifPresent(r -> {
	                    if (r == ButtonType.OK) {
	                        f.setActiva(false);
	                        formacionService.update(f);
	                        cargarDatos();
	                        ok("Formación desactivada.");
	                    }
	                });
	            },
	            () -> error("El estudiante no tiene formación activa.")
	        );
	}
	
	@FXML
	private void asignar(ActionEvent event) {
	    Estudiante       est     = cboEstudiante.getValue();
	    PeriodoPracticas periodo = cboPeriodo.getValue();
	    Empresa          empresa = cboEmpresa.getValue();
	    TutorEmpresa     tutor   = cboTutor.getValue();

	    if (est == null || periodo == null || empresa == null || tutor == null) {
	        error(" Selecciona estudiante, periodo, empresa y tutor.");
	        return;
	    }

	    if (formacionService.findActivaByEstudianteId(est.getIdUsuario()).isPresent()) {
	        error(" El estudiante ya tiene una formación activa. Desactívala primero.");
	        return;
	    }

	    try {
	        FormacionEmpresa nueva = new FormacionEmpresa(est, empresa, tutor, periodo);
	        formacionService.save(nueva);
	        cargarDatos();
	        limpiarCombos();
	        ok("Formación creada correctamente.");
	    } catch (Exception ex) {
	        error("Error al asignar: " + ex.getMessage());
	    }
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

	/** Limpia todos los combos. */
	private void limpiarCombos() {
		cboEstudiante.setValue(null);
		cboPeriodo.setValue(null);
		cboEmpresa.setValue(null);
		cboTutor.setValue(null);
		limpiarMensaje();
	}

	/**
	 * Crea un StringConverter genérico para cualquier tipo T usando una lambda.
	 * Evita repetir código al configurar múltiples combos.
	 *
	 * @param fn función que extrae el String a mostrar del objeto T
	 * @return StringConverter configurado
	 */
	private <T> StringConverter<T> strConv(
			java.util.function.Function<T, String> fn) {
		return new StringConverter<>() {
			@Override
			public String toString(T o) {
				return o == null ? "" : fn.apply(o);
			}

			@Override
			public T fromString(String s) {
				return null;
			}
		};
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
