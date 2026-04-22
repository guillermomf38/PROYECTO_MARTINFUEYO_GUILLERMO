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
			return "/fxml/TutorEmpresa.fxml";
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
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
