package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	ADMINISTRADOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("administrador.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AdministradorMenu.fxml";
		}
	},
	ESTUDIANTE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("estudiante.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EstudianteMenu.fxml";
		}
	},
	PROFESORADO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("profesorado.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ProfesoradoMenu.fxml";
		}
	},
	TUTOREMPRESA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("tutorempresa.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/TutorEmpresaMenu.fxml";
		}
	},	
	ASIGNARPERFIL{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("asignarperfil.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AsignarPerfil.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	GESTIONAR_TUTORES {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionartutores.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarTutores.fxml";
		}
	},


	GESTIONAR_CURSOS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionarcursos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarCursos.fxml";
		}
	},
	GESTIONAR_ESTUDIANTES_ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionarestudiantes.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarEstudiantesAdmin.fxml";
		}
	},
	
	ASOCIAR_CURSOS_TUTOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("asociarcursostutor.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AsociarCursosTutor.fxml";
		}
	},

	
	GESTIONAR_EMPRESAS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionarempresas.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarEmpresas.fxml";
		}
	},
	
	GESTIONAR_TUTOR_EMPRESA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionartutorempresa.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarTutorEmpresa.fxml";
		}
	},
	
	GESTIONAR_ESTUDIANTES_PROFE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("gestionarestudiantes.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/GestionarEstudiantesProfe.fxml";
		}
	},
	
	ASIGNACION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("asignacion.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Asignacion.fxml";
		}
	},
	
	MODIFICAR_DATOS_PROFE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("modificardatos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ModificarDatosProfe.fxml";
		}
	},

	
	REGISTRAR_FALTAS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registrarfaltas.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegistrarFaltas.fxml";
		}
	},
	
	CONSULTAR_DATOS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("consultardatos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ConsultarDatos.fxml";
		}
	},
	
	CALIFICAR_ESTUDIANTE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("calificarest.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CalificarEstudiante.fxml";
		}
	},

	
	VER_DATOS_ESTUDIANTE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("verdatosest.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/VerDatosEstudiante.fxml";
		}
	},

	DATOS_EMPRESA_TUTOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("datosempresatutor.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/DatosEmpresaTutor.fxml";
		}
	},
	
	ADJUNTAR_JUSTIFICANTE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("adjuntarjustificante.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/AdjuntarJustificante.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
