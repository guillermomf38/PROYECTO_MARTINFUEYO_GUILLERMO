/**
 *Clase AdjuntarJustificanteController.java
 * 
 *@author Guillermo Martin Fueyo
 *@version 1.0
 *@see DocumentacionService
 *@see FormacionEmpresaService
 *
 *Controller de la pantalla de adjuntar justificantes de faltas de asistencia.
 */

package com.luisdbb.tarea3AD2024base.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Documentacion;
import com.luisdbb.tarea3AD2024base.modelo.Estudiante;
import com.luisdbb.tarea3AD2024base.modelo.FormacionEmpresa;
import com.luisdbb.tarea3AD2024base.services.DocumentacionService;
import com.luisdbb.tarea3AD2024base.services.FormacionEmpresaService;
import com.luisdbb.tarea3AD2024base.services.SesionService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class AdjuntarJustificanteController implements Initializable {
	@FXML
	private TextField txtNombre;
	@FXML
	private ComboBox<String> cboTipo;
	@FXML
	private Label lblRuta;
	@FXML
	private Label lblMensaje;

	@FXML
	private TableView<Documentacion> tabla;
	@FXML
	private TableColumn<Documentacion, String> colNombre;
	@FXML
	private TableColumn<Documentacion, String> colTipo;
	@FXML
	private TableColumn<Documentacion, String> colRuta;
	
	@FXML
	private Button btnVolver;
	@FXML
	private Button btnSeleccionar;
	@FXML
	private Button btnAdjuntar;
	@FXML
	private Button btnEliminar;
	
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
	@Autowired
	private DocumentacionService documentacionService;

	/** Ruta absoluta del fichero seleccionado con el FileChooser. */
	private String rutaFicheroSeleccionado;

	/** Formación activa del estudiante logueado. */
	private FormacionEmpresa formacionActiva;

	/** Lista observable vinculada a la tabla de documentos. */
	private final ObservableList<Documentacion> datos = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		 openAyuda.setAccelerator(
			        new KeyCodeCombination(KeyCode.F1)
			    );
		  cboTipo.setItems(FXCollections.observableArrayList(
		            "Justificante médico", "Justificante oficial",
		            "Otro justificante"));
		    configurarTabla();

		    Estudiante est = sesionService.getEstudianteLogueado();
		    if (est != null) {
		        formacionService.findActivaByEstudianteId(est.getIdUsuario())
		                .ifPresent(f -> {
		                    formacionActiva = f;
		                    cargarDatos();
		                });
		    }

		    if (formacionActiva == null) {
		        error("No tienes ninguna formación activa. No puedes adjuntar documentos.");
		    }
	}

	/**
	 * Configura las columnas de la tabla de documentos adjuntos. La columna
	 * ruta muestra el nombre del campo {@code nombre} que en este caso almacena
	 * la ruta si no se añadió el campo específico al modelo.
	 */
	private void configurarTabla() {
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		new PropertyValueFactory<>("ruta");

		colRuta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tabla.setItems(datos);
	}

	/** Recarga los documentos de la formación activa desde la base de datos. */
	private void cargarDatos() {
		if (formacionActiva != null) {
			datos.setAll(documentacionService
					.findByFormacion(formacionActiva.getIdFormacion()));
		}
	}

	/**
	 * Abre el explorador de archivos del sistema operativo para que el
	 * estudiante seleccione el fichero justificante. Admite PDF, imágenes y
	 * documentos de texto. La ruta del fichero seleccionado se muestra en el
	 * label {@code lblRuta}.
	 *
	 * @param event evento del botón Seleccionar fichero
	 */
	@FXML
	private void seleccionarFichero(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Selecciona el justificante");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Documentos PDF", "*.pdf"),
				new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg",
						"*.jpeg"),
				new FileChooser.ExtensionFilter("Todos los ficheros", "*.*"));

		File fichero = fc.showOpenDialog(lblRuta.getScene().getWindow());
		if (fichero != null) {
			rutaFicheroSeleccionado = fichero.getAbsolutePath();
			lblRuta.setText(fichero.getName());
			lblRuta.setStyle("-fx-text-fill: #2c3e50;");

			if (txtNombre.getText().isBlank()) {
				txtNombre.setText(fichero.getName());
			}
		}
	}

	/**
	 * Guarda los metadatos del fichero seleccionado como un nuevo
	 * {@link Documentacion} vinculado a la formación activa del estudiante.
	 *
	 * @param event evento del botón Adjuntar
	 */
	@FXML
	private void adjuntar(ActionEvent event) {
		if (formacionActiva == null) {
			error("No tienes formación activa.");
			return;
		}
		if (txtNombre.getText().isBlank()) {
			error("Introduce un nombre para el documento.");
			return;
		}
		if (cboTipo.getValue() == null) {
			error("Selecciona el tipo de documento.");
			return;
		}
		if (rutaFicheroSeleccionado == null) {
			error("Selecciona primero un fichero.");
			return;
		}

		try {
			Documentacion doc = new Documentacion(txtNombre.getText().trim(),
					cboTipo.getValue(), formacionActiva.getIdFormacion());

			doc.setRuta(rutaFicheroSeleccionado);
			documentacionService.save(doc);
			cargarDatos();
			limpiarFormulario();
			ok("Justificante adjuntado correctamente.");
		} catch (Exception ex) {
			error("Error al adjuntar: " + ex.getMessage());
		}
	}

	/**
	 * Elimina el documento seleccionado en la tabla tras confirmación.
	 *
	 * @param event evento del botón Eliminar seleccionado
	 */
	@FXML
	private void eliminar(ActionEvent event) {
		Documentacion sel = tabla.getSelectionModel().getSelectedItem();
		if (sel == null) {
			error("Selecciona un documento de la tabla para eliminar.");
			return;
		}

		Alert c = new Alert(Alert.AlertType.CONFIRMATION);
		c.setHeaderText(null);
		c.setContentText("¿Eliminar el documento '" + sel.getNombre() + "'?");
		c.showAndWait().ifPresent(r -> {
			if (r == ButtonType.OK) {
				documentacionService.delete(sel.getIdDocumento());
				cargarDatos();
				ok("Documento eliminado.");
			}
		});
	}

	/**
	 * Navega de vuelta al menú del estudiante.
	 *
	 * @param event evento del botón Volver
	 */
	@FXML
	private void volver(ActionEvent event) {
		stageManager.switchScene(FxmlView.ESTUDIANTE);
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

	/** Limpia los campos del formulario tras adjuntar un documento. */
	private void limpiarFormulario() {
		txtNombre.clear();
		cboTipo.setValue(null);
		lblRuta.setText("Ningún fichero seleccionado");
		lblRuta.setStyle("-fx-text-fill:#888;");
		rutaFicheroSeleccionado = null;
		limpiarMensaje();
	}

	private void ok(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#27ae60;");
		lblMensaje.setText("Correcto " + msg);
	}

	private void error(String msg) {
		lblMensaje.setStyle("-fx-text-fill:#c0392b;");
		lblMensaje.setText("Error " + msg);
	}

	private void limpiarMensaje() {
		lblMensaje.setText("");
	}

}
