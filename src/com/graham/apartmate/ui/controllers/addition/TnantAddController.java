package com.graham.apartmate.ui.controllers.addition;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.database.tables.subTables.Spouse;
import com.graham.apartmate.main.Main;
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
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}
	
	@FXML
	public void addSpouseToTenant() {
		Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
		try {
			if (tnantSpouse.getFirstName().equals("") || tnantSpouse.getLastName().equals(""))
				throw new NullPointerException("Spouse name is empty");

			spNameLoadConf.setText(tnantSpouse.getFullName());
		} catch(NullPointerException e) {
			spNameLoadConf.setText("No Spouse Added");

			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	@FXML
	public void addToTenants() {
		int id;

		int lastId = Database.getInstance().getLastID(DBTables.TENANTS);
		if (lastId > 0) {
			id = lastId;
		} else {
			id = 1;
		}

		try {
			Tenant tenantToAdd = new Tenant();

			LocalDate date;

			tenantToAdd.setId(id);
			if(isUsingCurrApt.isSelected()) {
				tenantToAdd.setFk(Database.getInstance().getCurrApt().getId());
			}else {
				tenantToAdd.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}

			tenantToAdd.setFirstName(firstNameTextField.getText());
			tenantToAdd.setLastName(lastNameTextField.getText());
			tenantToAdd.setPhone(phoneTextField.getText());
			tenantToAdd.setEmail(emailTextField.getText());
			tenantToAdd.setSSN(SSNTextField.getText());
			tenantToAdd.setRent(Double.parseDouble(rentTextField.getText()));
			tenantToAdd.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));

			date = movInDatePicker.getValue();
			tenantToAdd.setMovInDate(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));

			date = dateOfBirthDatePicker.getValue();
			tenantToAdd.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));

			tenantToAdd.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			if (tnantSpouse != null && !(tnantSpouse.getFirstName().equals("") || tnantSpouse.getLastName().equals(""))) {
				tenantToAdd.setSpouse(tnantSpouse);
			}

			Database.getInstance().add(tenantToAdd);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
