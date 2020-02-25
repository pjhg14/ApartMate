package com.apartmate.ui.controllers.edit;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AptEditController {
	@FXML
	private TextField addressTextField;

	@FXML
	private TextField cityTextField;

	@FXML
	private TextField stateTextField;

	@FXML
	private TextField zipTextField;

	@FXML
	private TextField capacityTextField;

	@FXML
	private TextField insBillTextField;

	@FXML
	private Text errorText;

	@FXML
	private Button addButton;

	@FXML
	public void initialize() {
		addressTextField.setText(Database.getInstance().getCurrApt().getAddress());
		cityTextField.setText(Database.getInstance().getCurrApt().getCity());
		stateTextField.setText(Database.getInstance().getCurrApt().getState());
		zipTextField.setText(Database.getInstance().getCurrApt().getZipCode());
		capacityTextField.setText(Integer.toString(Database.getInstance().getCurrApt().getCapacity()));
		insBillTextField.setText(Double.toString(Database.getInstance().getCurrApt().getInsBill()));
	}

	@FXML
	public void addToApartments() {
		int id;

		if (Database.getInstance().getApartments().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getApartments().get(Database.getInstance().getApartments().size() - 1).getId()
					+ 1;
		}

		try {
			Apartment temp = new Apartment();
			String parse;

			temp.setId(id);
			temp.setAddress(addressTextField.getText());
			temp.setCity(cityTextField.getText());
			temp.setState(stateTextField.getText());
			temp.setZipCode(zipTextField.getText());
			temp.setCapacity(Integer.parseInt(capacityTextField.getText()));
			if (insBillTextField.getText() != "") {
				parse = "0";
			} else {
				parse = insBillTextField.getText();
			}
			temp.setInsBill(Double.parseDouble(parse));

			Database.getInstance().edit(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
