package com.graham.apartmate.ui.windows.popupwindows.addwindows.tenant;

import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Building;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.main.Main;

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
	private ListView<Building> apartmentChoice;
	
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

	private Building relatedBuilding;
	
	private Tenant newTennant;
	
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	public void initialize() {
		relatedBuilding = new Building();
		newTennant = new Tenant();
		apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getBuildings()));
		apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		apartmentChoice.getSelectionModel().select(relatedBuilding);
	}
	
	@FXML
	private void useCurrApt() {
		apartmentChoice.setDisable(isUsingCurrApt.isSelected());
	}
	
	@FXML
	public void addSpouseToTenant() {
//		newTennant.setSpouse(new RoomMate());
//
//		Main.getLibrary().additionWindow(newTennant.getRoomMates());
//		try {
//			if (newTennant.getRoomMates().invalidName())
//				throw new NullPointerException("Spouse name is empty");
//
//			spNameLoadConf.setText(newTennant.getRoomMates().getFullName());
//		} catch(NullPointerException e) {
//			spNameLoadConf.setText("No Spouse Added");
//			newTennant.setSpouse(new RoomMate(Table.DUMMY_TABLE));
//
//			if (Main.DEBUG)
//				System.out.println(e.getMessage());
//		}
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
			LocalDate date;

			newTennant.setId(id);
			if(isUsingCurrApt.isSelected()) {
				newTennant.setFk(relatedBuilding.getId());
			}else {
				newTennant.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
			}

//			newTennant.setFirstName(firstNameTextField.getText());
//			newTennant.setLastName(lastNameTextField.getText());
//			newTennant.setPhone(phoneTextField.getText());
//			newTennant.setEmail(emailTextField.getText());
//			newTennant.setSsn(SSNTextField.getText());
//			newTennant.setRent(Double.parseDouble(rentTextField.getText()));
//			newTennant.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));

			date = movInDatePicker.getValue();
			//newTennant.setMovInDate(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));

			date = dateOfBirthDatePicker.getValue();
			//newTennant.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));

//			newTennant.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));

			Database.getInstance().add(newTennant);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		}
	}
}
