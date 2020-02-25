package com.apartmate.ui.controllers.edit;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TnantEditController {

	// ---------------------------------------------------------
		// fxml objects/////////////////////////////////////////////
		// ---------------------------------------------------------
		
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
			apartmentChoice.getSelectionModel().select(Database.getInstance().getCurrApt());
			
			//Populate Fields
			firstNameTextField.setText(Database.getInstance().getCurrTnant().getFirstName());
			lastNameTextField.setText(Database.getInstance().getCurrTnant().getLastName());
			phoneTextField.setText(Database.getInstance().getCurrTnant().getPhone());
			emailTextField.setText(Database.getInstance().getCurrTnant().getEmail());
			dateOfBirthDatePicker.setValue(
					Database.getInstance().getCurrTnant().getDateOfBirth()
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getAnnualIncome()));
			SSNTextField.setText(Database.getInstance().getCurrTnant().getSSN());
			rentTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getRent()));
			movInDatePicker.setValue(
					Database.getInstance().getCurrTnant().getMovinDate()
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			numChildrenTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getNumChildren()));
			
			if (Database.getInstance().getCurrTnant().getSpouse() != null) {
				spNameLoadConf.setText(Database.getInstance().getCurrTnant().getSpouse().getFirstName()
						 + Database.getInstance().getCurrTnant().getSpouse().getLastName());
			}
		}
		
		@FXML
		public void addSpouseToTenant() {
			if(Database.getInstance().getCurrTnant().getSpouse() != null) {
				Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
				
				Database.getInstance().getCurrTnant().setSpouse(tnantSpouse);
			}else {
				errorText.setVisible(true);
				errorText.setText("Tenant already has Spouse");
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
				if(!apartmentChoice.getSelectionModel().getSelectedItem().equals(
						Database.getInstance().getCurrApt())) {
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

				Database.getInstance().edit(temp);

				
				//Main.getLibrary().closePopup();
			} catch (NumberFormatException e) {
				errorText.setVisible(true);
				errorText.setText("Invalid data in fields");
			}
		}
}
