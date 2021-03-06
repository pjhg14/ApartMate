package com.graham.apartmate.ui.windows.popupwindows.addwindows.roommate;

import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.subTables.Occupant;
import com.graham.apartmate.main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
public class SpouseAddController {

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

	}

	@FXML
	public void addSpouse() {
		int id;

		if (Database.getInstance().getCandidates().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getCandidates().get(Database.getInstance().getCandidates().size() - 1).getId()
					+ 1;
		}

		try {
			Occupant temp = new Occupant();

			LocalDate date;

			temp.setId(id);
//			if (Database.getInstance().getCurrTnant() != null) {
//				temp.setFk(Database.getInstance().getCurrTnant().getId());
//			} else if (Database.getInstance().getCurrCand() != null) {
//				temp.setFk(Database.getInstance().getCurrCand().getId());
//			}

//			temp.setFirstName(firstNameTextField.getText());
//			temp.setLastName(lastNameTextField.getText());
//			temp.setPhone(phoneTextField.getText());
//			temp.setEmail(emailTextField.getText());
//			temp.setSsn(SSNTextField.getText());
			date = dateOfBirthDatePicker.getValue();
			//temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
//			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			//TnantAddController.tnantSpouse = temp;
			//CandAddController.candSpouse = temp;

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
