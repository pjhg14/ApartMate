package com.apartmate.ui.controllers.edit;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.subTables.Spouse;
import com.apartmate.main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SpouseEditController {
	// ---------------------------------------------------------
	// fxml objects/////////////////////////////////////////////
	// ---------------------------------------------------------
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
	private Text errorText;

	@FXML
	private Button addButton;

	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
		if(Database.getInstance().getCurrTnant() != null) {
			firstNameTextField.setText(Database.getInstance().getCurrTnant().getSpouse().getFirstName());
			lastNameTextField.setText(Database.getInstance().getCurrTnant().getSpouse().getLastName());
			phoneTextField.setText(Database.getInstance().getCurrTnant().getSpouse().getPhone());
			emailTextField.setText(Database.getInstance().getCurrTnant().getSpouse().getEmail());
			if(Database.getInstance().getCurrTnant().getSpouse().getDateOfBirth() != null){
				dateOfBirthDatePicker.setValue(
						Database.getInstance().getCurrTnant().getSpouse().getDateOfBirth()
								.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getSpouse().getAnnualIncome()));
				SSNTextField.setText(Database.getInstance().getCurrTnant().getSpouse().getSSN());
			}
		}else if(Database.getInstance().getCurrCand() != null) {
			firstNameTextField.setText(Database.getInstance().getCurrCand().getSpouse().getFirstName());
			lastNameTextField.setText(Database.getInstance().getCurrCand().getSpouse().getLastName());
			phoneTextField.setText(Database.getInstance().getCurrCand().getSpouse().getPhone());
			emailTextField.setText(Database.getInstance().getCurrCand().getSpouse().getEmail());
			dateOfBirthDatePicker.setValue(
					Database.getInstance().getCurrCand().getSpouse().getDateOfBirth()
					.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrCand().getSpouse().getAnnualIncome()));
			SSNTextField.setText(Database.getInstance().getCurrCand().getSpouse().getSSN());
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
			Spouse temp = new Spouse();

			LocalDate date;

			temp.setId(id);
			if (Database.getInstance().getCurrTnant() != null) {
				temp.setFk(Database.getInstance().getCurrTnant().getId());
			} else if (Database.getInstance().getCurrCand() != null) {
				temp.setFk(Database.getInstance().getCurrCand().getId());
			}

			temp.setFirstName(firstNameTextField.getText());
			temp.setLastName(lastNameTextField.getText());
			temp.setPhone(phoneTextField.getText());
			temp.setEmail(emailTextField.getText());
			temp.setSSN(SSNTextField.getText());
			date = dateOfBirthDatePicker.getValue();
			temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			if(Database.getInstance().getCurrTnant() != null) {
				Database.getInstance().getCurrTnant().setSpouse(temp);
			} else if(Database.getInstance().getCurrCand() != null) {
				Database.getInstance().getCurrCand().setSpouse(temp);
			}

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
