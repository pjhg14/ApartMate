package com.apartmate.ui.controllers.edit;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
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

public class CandEditController {

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
	private TextField numChildrenTextField;

	@FXML
	private Button spouseAddButton;

	@FXML
	private Text spNameLoadConf;

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
		
		firstNameTextField.setText(Database.getInstance().getCurrTnant().getFirstName());
		lastNameTextField.setText(Database.getInstance().getCurrTnant().getLastName());
		phoneTextField.setText(Database.getInstance().getCurrTnant().getPhone());
		emailTextField.setText(Database.getInstance().getCurrTnant().getEmail());
		dateOfBirthDatePicker.setValue(
				Database.getInstance().getCurrTnant().getDateOfBirth()
				.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getAnnualIncome()));
		SSNTextField.setText(Database.getInstance().getCurrTnant().getSSN());
		numChildrenTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getNumChildren()));
		
		if (Database.getInstance().getCurrTnant().getSpouse() != null) {
			spNameLoadConf.setText(Database.getInstance().getCurrTnant().getSpouse().getFirstName()
					 + Database.getInstance().getCurrTnant().getSpouse().getLastName());
		}
	}

	@FXML
	private void useCurrApt() {
		if (isUsingCurrApt.isSelected()) {
			apartmentChoice.setDisable(true);
		} else {
			apartmentChoice.setDisable(false);
		}
	}

	@FXML
	public void addSpouseToCandidate() {
		if(Database.getInstance().getCurrTnant().getSpouse() != null) {
			Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
			
			Database.getInstance().getCurrTnant().setSpouse(candSpouse);
		}else {
			errorText.setVisible(true);
			errorText.setText("Candidate already has Spouse");
		}
	}

	@FXML
	public void addToTenants() {
		int id;

		if (Database.getInstance().getCandidates().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getCandidates().get(Database.getInstance().getCandidates().size() - 1).getId()
					+ 1;
		}

		try {
			Candidate temp = new Candidate();

			LocalDate date;

			temp.setId(id);
			if (isUsingCurrApt.isSelected()) {
				temp.setFk(Database.getInstance().getCurrApt().getId());
			} else {
				temp.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}
			temp.setFirstName(firstNameTextField.getText());
			temp.setLastName(lastNameTextField.getText());
			temp.setPhone(phoneTextField.getText());
			temp.setEmail(emailTextField.getText());
			temp.setSSN(SSNTextField.getText());
			temp.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));
			date = dateOfBirthDatePicker.getValue();
			temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			Database.getInstance().getCandidates().add(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
