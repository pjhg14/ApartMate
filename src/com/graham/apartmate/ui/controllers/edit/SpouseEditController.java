package com.graham.apartmate.ui.controllers.edit;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.subTables.Spouse;
import com.graham.apartmate.main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
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
		switch (Database.getCurrTable()) {
			case TENANTS:
				break;
			case CANDIDATES:
				break;
			default:
				break;
		}
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
			dateOfBirthDatePicker.setPromptText(
					Database.getInstance().getCurrCand().getSpouse().getDateOfBirth().toString());
			annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrCand().getSpouse().getAnnualIncome()));
			SSNTextField.setText(Database.getInstance().getCurrCand().getSpouse().getSSN());
		}
	}

	@FXML
	public void editSpouse() {
		try {
			Spouse temp = new Spouse();
			LocalDate date;

			//Establish fields w/ differences via parent table
			switch (Database.getCurrTable()) {
				case TENANTS:
					temp.setId(Database.getInstance().getCurrTnant().getId());

					temp.setFk(Database.getInstance().getCurrTnant().getId());

					if (dateOfBirthDatePicker.getValue() != null) {
						date = dateOfBirthDatePicker.getValue();
						temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
					} else {
						temp.setDateOfBirth(Database.getInstance().getCurrTnant().getDateOfBirth());
					}
					break;
				case CANDIDATES:
					temp.setId(Database.getInstance().getCurrCand().getId());

					temp.setFk(Database.getInstance().getCurrCand().getId());

					if (dateOfBirthDatePicker.getValue() != null) {
						date = dateOfBirthDatePicker.getValue();
						temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
					} else {
						temp.setDateOfBirth(Database.getInstance().getCurrCand().getDateOfBirth());
					}
					break;
				default:
					return;
			}

			temp.setFirstName(firstNameTextField.getText());
			temp.setLastName(lastNameTextField.getText());
			temp.setPhone(phoneTextField.getText());
			temp.setEmail(emailTextField.getText());
			temp.setSSN(SSNTextField.getText());

			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));


			switch (Database.getCurrTable()) {
				case TENANTS:
					Database.getInstance().getCurrTnant().setSpouse(temp);
					break;
				case CANDIDATES:
					Database.getInstance().getCurrCand().setSpouse(temp);
					break;
			}

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
