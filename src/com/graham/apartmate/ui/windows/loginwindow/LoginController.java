package com.graham.apartmate.ui.windows.loginwindow;

import java.io.IOException;
import java.io.InvalidObjectException;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.libraries.FXMLLocation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
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
	public void submitCredentials() throws IOException {
		try {
			//check usernameField and passwordField for text
			if (validateFields())
				throw new IOException("Put error constant here (make error constants in Validation class)");
			//check list of users to see if text input is valid

			//if so, pass user to Database.open to try to load database

			if (Database.getInstance().open()) {
				System.out.println("Saved data should not be loaded!!!");
			} else {
				System.out.println("currently expected result (this should throw a popup later)");
			}

			Main.getLibrary().mainWindow(FXMLLocation.MAIN);
		} catch (InvalidObjectException ioe) {
			errorText.setText("Error Opening Database, this is not good at all");
			if (!errorText.isVisible())
				errorText.setVisible(true);
			ioe.printStackTrace();
		} catch (IOException e) {
			errorText.setText("Fields are required");
			if (!errorText.isVisible())
				errorText.setVisible(true);
			e.printStackTrace();
		}
	}

	private boolean validateFields() {
		return usernameField.getText().equals("") && usernameField.getText().equals("");
	}

}
