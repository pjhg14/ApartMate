package com.graham.apartmate.ui.windows.editwindows.candidate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.subTables.RoomMate;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.libraries.FXMLLocation;

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

	public static RoomMate candRoomMate;

	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
//		apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getApartments()));
//		apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//		apartmentChoice.getSelectionModel().select(Database.getInstance().getCurrApt());
//
//		firstNameTextField.setText(Database.getInstance().getCurrTnant().getFirstName());
//		lastNameTextField.setText(Database.getInstance().getCurrTnant().getLastName());
//		phoneTextField.setText(Database.getInstance().getCurrTnant().getPhone());
//		emailTextField.setText(Database.getInstance().getCurrTnant().getEmail());
//		dateOfBirthDatePicker.setPromptText(
//				Database.getInstance().getCurrTnant().getDateOfBirth().toString());
//		annualIncomeTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getAnnualIncome()));
//		SSNTextField.setText(Database.getInstance().getCurrTnant().getSSN());
//		numChildrenTextField.setText(String.valueOf(Database.getInstance().getCurrTnant().getNumChildren()));
//
//		if (Database.getInstance().getCurrTnant().getSpouse() != null) {
//			spNameLoadConf.setText(Database.getInstance().getCurrTnant().getSpouse().getFirstName()
//					 + Database.getInstance().getCurrTnant().getSpouse().getLastName());
//		}
	}

	@FXML
	private void useCurrApt() {
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}

	@FXML
	public void addSpouseToCandidate() {
//		if(Database.getInstance().getCurrTnant().getSpouse() != null) {
//			//Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
//
//			Database.getInstance().getCurrTnant().setSpouse(candRoomMate);
//		}else {
//			errorText.setVisible(true);
//			errorText.setText("Candidate already has Spouse");
//		}
	}

	@FXML
	public void editCandidate() {
//		try {
//			Candidate temp = new Candidate();
//
//			LocalDate date;
//
//			temp.setId(Database.getInstance().getCurrCand().getId());
//			if (isUsingCurrApt.isSelected()) {
//				temp.setFk(Database.getInstance().getCurrApt().getId());
//			} else {
//				temp.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
//			}
//			temp.setFirstName(firstNameTextField.getText());
//			temp.setLastName(lastNameTextField.getText());
//			temp.setPhone(phoneTextField.getText());
//			temp.setEmail(emailTextField.getText());
//			temp.setSsn(SSNTextField.getText());
//			temp.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));
//
//			if (dateOfBirthDatePicker.getValue() != null) {
//				date = dateOfBirthDatePicker.getValue();
//				temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
//			} else {
//				temp.setDateOfBirth(Database.getInstance().getCurrCand().getDateOfBirth());
//			}
//
//			temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));
//
//			Database.getInstance().add(temp);
//
//			Main.getLibrary().closePopup();
//		} catch (NumberFormatException e) {
//			errorText.setVisible(true);
//			errorText.setText("Invalid data in fields");
//		}
	}
}
