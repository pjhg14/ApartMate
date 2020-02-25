package com.apartmate.ui.controllers.addition;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.Spouse;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TnantAddController {

	// ---------------------------------------------------------
	// fxml objects/////////////////////////////////////////////
	// ---------------------------------------------------------
	@FXML
	private CheckBox isUsingCurrApt;
	
	@FXML
	private ListView<Apartment> apartmentChoice;
	
	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private DatePicker dateOfBirthDatePicker;
	
	@FXML
	private TextField annualIncomeTextField;
	
	@FXML
	private TextField SSNTextField;

	@FXML
	private TextField rentTextField;

	@FXML
	private DatePicker movInDatePicker;
	
	@FXML
	private TextField numChildrenTextField;

	@FXML
	private Button spouseAddButton;

	@FXML
	private Text spNameLoadConf;

	@FXML
	private Text errorText;

	@FXML
	private Button addButton;
	
	public static Spouse tnantSpouse;
	
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
	public void addSpouseToTenant() {
		Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
		try {
			spNameLoadConf.setText(tnantSpouse.getFirstName() + " " + tnantSpouse.getLastName());
		} catch(NumberFormatException e) {
			spNameLoadConf.setText("No Spouse Added");
		}
	}

	@FXML
	public void addToTenants() {
		int id;

		if (Database.getInstance().getTenants().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getTenants().get(Database.getInstance().getTenants().size() - 1).getId() + 1;
		}

		try {
			Tenant temp = new Tenant();

			LocalDate date;

			temp.setId(id);
			if(isUsingCurrApt.isSelected()) {
				temp.setFk(Database.getInstance().getCurrApt().getId());
			}else {
				temp.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}
			temp.setFirstName(firstNameTextField.getText());
			temp.setLastName(lastNameTextField.getText());
			temp.setPhone(phoneTextField.getText());
			temp.setEmail(emailTextField.getText());
			temp.setSSN(SSNTextField.getText());
			temp.setRent(Double.parseDouble(rentTextField.getText()));
			temp.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));
			date = movInDatePicker.getValue();
			temp.setMovinDate(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
			date = dateOfBirthDatePicker.getValue();
			temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));
			if(tnantSpouse != null) {
				temp.setSpouse(tnantSpouse);
			}
			

			Database.getInstance().add(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}