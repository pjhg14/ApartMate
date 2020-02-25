package com.apartmate.ui.controllers.addition;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;

import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.subTables.Spouse;
import com.apartmate.main.Main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ContAddController {
	
	@FXML
	private CheckBox isUsingCurrApt;
	
	@FXML
	private ListView<Apartment> apartmentChoice;
	
	@FXML
	private TextField nameTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private TextField emailTextField;
	
	@FXML
	private TextField billTextField;

	@FXML
	private Text errorText;

	@FXML
	private Button addButton;
	
	public static Spouse candSpouse;
	
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
		apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getApartments()));
		apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	@FXML
	private void useCurrApt() {
		if(isUsingCurrApt.isSelected()) {
			apartmentChoice.setDisable(true);
		}else {
			apartmentChoice.setDisable(false);
		}
	}
	
	

	@FXML
	public void addToTenants() {
		int id;

		if (Database.getInstance().getContractors().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getContractors().get(Database.getInstance().getContractors().size() - 1).getId() + 1;
		}

		try {
			Contractor temp = new Contractor();

			temp.setId(id);
			if(isUsingCurrApt.isSelected()) {
				temp.setFk(Database.getInstance().getCurrApt().getId());
			}else {
				temp.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}
			temp.setName(nameTextField.getText());
			temp.setPhone(phoneTextField.getText());
			temp.setEmail(emailTextField.getText());
			temp.setBill(Integer.parseInt(billTextField.getText()));

			Database.getInstance().add(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
