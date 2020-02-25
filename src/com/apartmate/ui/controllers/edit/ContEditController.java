package com.apartmate.ui.controllers.edit;

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

public class ContEditController {

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
		apartmentChoice.getSelectionModel().select(Database.getInstance().getCurrApt());
		
		nameTextField.setText(Database.getInstance().getCurrCont().getName());
		phoneTextField.setText(Database.getInstance().getCurrCont().getPhone());
		emailTextField.setText(Database.getInstance().getCurrCont().getEmail());
		billTextField.setText(String.valueOf(Database.getInstance().getCurrCont().getBill()));
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

			Database.getInstance().edit(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
