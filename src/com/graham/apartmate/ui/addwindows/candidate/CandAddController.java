package com.graham.apartmate.ui.addwindows.candidate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.subTables.Spouse;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.FXMLLocation;

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
public class CandAddController extends FXMLController {

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

	private Apartment relatedApartment;
	
	private Candidate newCandidate;
	
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
		relatedApartment = (Apartment) currentTable;
		newCandidate = new Candidate();

		apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getApartments()));
		apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		apartmentChoice.getSelectionModel().select(relatedApartment);
	}
	
	@FXML
	private void useCurrApt() {
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}
	
	@FXML
	public void addSpouseToCandidate() {
		newCandidate.setSpouse(new Spouse());

		Main.getLibrary().additionWindow(newCandidate.getSpouse(), FXMLLocation.SPOUSEADD);
		try {
			if (newCandidate.getSpouse().invalidName())
				throw new NullPointerException("Spouse name is empty");

			spNameLoadConf.setText(newCandidate.getSpouse().getFullName());
		} catch(NullPointerException e) {
			spNameLoadConf.setText("No Spouse Added");
			newCandidate.setSpouse(new Spouse(Table.DUMMY_TABLE));

			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	@FXML
	public void addToCandidates() {
		int id;

		int lastId = Database.getInstance().getLastID(DBTables.CANDIDATES);
		if (lastId > 0) {
			id = lastId;
		} else {
			id = 1;
		}

		try {
			LocalDate date;

			newCandidate.setId(id);
			if(isUsingCurrApt.isSelected()) {
				newCandidate.setFk(relatedApartment.getId());
			}else {
				newCandidate.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}
			newCandidate.setFirstName(firstNameTextField.getText());
			newCandidate.setLastName(lastNameTextField.getText());
			newCandidate.setPhone(phoneTextField.getText());
			newCandidate.setEmail(emailTextField.getText());
			newCandidate.setSSN(SSNTextField.getText());
			newCandidate.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));

			date = dateOfBirthDatePicker.getValue();
			newCandidate.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));

			newCandidate.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			Database.getInstance().add(newCandidate);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
