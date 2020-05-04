package com.apartmate.ui.controllers.addition;

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

//TODO: Javadoc's for every method
public class CandAddController {

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
	}
	
	@FXML
	private void useCurrApt() {
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}
	
	@FXML
	public void addSpouseToCandidate() {
		Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
		try {
			spNameLoadConf.setText(candSpouse.getFirstName() + " " + candSpouse.getLastName());
		} catch(NumberFormatException e) {
			spNameLoadConf.setText("No Spouse Added");
		}
	}

	@FXML
	public void addToTenants() {
		int id;

		if (Database.getInstance().getCandidates().isEmpty()) {
			id = 0;
		} else {
			id = Database.getInstance().getCandidates().get(Database.getInstance().getCandidates().size() - 1).getId() + 1;
		}

		try {
			Candidate temp = new Candidate();

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
			temp.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));
			date = dateOfBirthDatePicker.getValue();
			temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));
			if(candSpouse == null) {
				temp.setSpouse(candSpouse);
			}

			Database.getInstance().add(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
