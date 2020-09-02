package com.graham.apartmate.ui.windows.addwindows.contractor;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;

import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.main.Main;

import com.graham.apartmate.ui.misc.FXMLController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
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

	private Apartment relatedApartment;
	
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
		relatedApartment =  new Apartment();
		apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getApartments()));
		apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		apartmentChoice.getSelectionModel().select(relatedApartment);
	}
	
	@FXML
	private void useCurrApt() {
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}
	
	

	@FXML
	public void addToTenants() {
		int id;

		int lastId = Database.getInstance().getLastID(DBTables.CONTRACTORS);
		if (lastId > 0) {
			id = lastId;
		} else {
			id = 1;
		}

		try {
			Contractor newContractor = new Contractor();

			newContractor.setId(id);
			if(isUsingCurrApt.isSelected()) {
				newContractor.setFk(relatedApartment.getId());
			}else {
				newContractor.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}
			newContractor.setName(nameTextField.getText());
			newContractor.setPhone(phoneTextField.getText());
			newContractor.setEmail(emailTextField.getText());
			newContractor.setBill(Integer.parseInt(billTextField.getText()));

			Database.getInstance().add(newContractor);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
