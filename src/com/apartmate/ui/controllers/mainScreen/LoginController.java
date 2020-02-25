package com.apartmate.ui.controllers.mainScreen;

import java.io.IOException;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

	@FXML
	private Button loginButton;

	@FXML
	private Text errorText;

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	public void onButtonPressed() throws IOException {
		try {
			if (Database.getInstance().open()) {
				Main.getLibrary().mainWindow(FXMLLocation.APARTMENT);
			}
		} catch (Exception e) {
			errorText.setText("Fields are required");
			if (!errorText.isVisible())
				errorText.setVisible(true);
			e.printStackTrace();
		}
	}

}
