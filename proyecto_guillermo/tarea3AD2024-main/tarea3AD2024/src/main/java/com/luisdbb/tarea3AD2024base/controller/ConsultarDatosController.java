/**
 *Clase ConsultarDatosController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see FormacionEmpresaService
 *@see SesionService
 *
 *Controller de la pantalla de consulta de datos de estudiantes.
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
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.modelo.TutorEmpresa;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Controller
public class ConsultarDatosController implements Initializable {

	@FXML
	private ComboBox<Estudiante> cboEstudiante;
	@FXML
	private Label lblNombre;
	@FXML
	private Label lblApellidos;
	@FXML
	private Label lblCurso;
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblEmpresa;
	@FXML
	private Label lblPeriodo;
	
	@FXML
	private Button btnVolver;
	
	@FXML
	private MenuItem openAutoria;
	
	@FXML
	private MenuItem openAyuda;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private FormacionEmpresaService formacionService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		cargarComboEstudiantes();

		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
	}

	/**
	 * Carga en el combo solo los estudiantes con formación activa supervisada
	 * por el tutor de empresa que está logueado.
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
	 * Al seleccionar un estudiante en el combo, rellena todos los labels con
	 * sus datos personales y los de su formación activa. Si no tiene formación
	 * activa, los campos de empresa y periodo quedan vacíos.
	 *
	 * @param event evento del ComboBox
	 */
	@FXML
	private void onEstudianteSeleccionado(ActionEvent event) {
		Estudiante est = cboEstudiante.getValue();
		if (est == null) {
			limpiarLabels();
			return;
		}

		lblNombre.setText(est.getNombre());
		lblApellidos.setText(est.getApellidos());
		lblCurso.setText(est.getCurso());
		lblUsuario.setText(est.getUsuario());

		formacionService.findActivaByEstudianteId(est.getIdUsuario()).ifPresentOrElse(f -> {
			lblEmpresa.setText(
					f.getEmpresa() != null ? f.getEmpresa().getNombre() : "—");
			lblPeriodo.setText(f.getPeriodoPracticas() != null
					? f.getPeriodoPracticas().getNombrePeriodo()
					: "—");
		}, () -> {
			lblEmpresa.setText("Sin formación activa");
			lblPeriodo.setText("—");
		});
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

	/** Vacía todos los labels de datos. */
	private void limpiarLabels() {
		lblNombre.setText("");
		lblApellidos.setText("");
		lblCurso.setText("");
		lblUsuario.setText("");
		lblEmpresa.setText("");
		lblPeriodo.setText("");
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
