package com.graham.apartmate.ui.windows.popupwindows.editwindows.tenant;

import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.subTables.RoomMate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
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
		
		public static RoomMate tnantRoomMate;
		
		// ---------------------------------------------------------
		// ---------------------------------------------------------

		@FXML
		public void initialize() {
//			apartmentChoice.setItems(FXCollections.observableArrayList(Database.getInstance().getApartments()));
//			apartmentChoice.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//			apartmentChoice.getSelectionModel().select(Database.getInstance().getCurrApt());
//
//			//Populate Fields
//			Tenant editedTenant = Database.getInstance().getCurrTnant();
//
//			firstNameTextField.setText(editedTenant.getFirstName());
//			lastNameTextField.setText(editedTenant.getLastName());
//			phoneTextField.setText(editedTenant.getPhone());
//			emailTextField.setText(editedTenant.getEmail());
//			dateOfBirthDatePicker.setPromptText(
//					editedTenant.getDateOfBirth().toString());
//			annualIncomeTextField.setText(String.valueOf(editedTenant.getAnnualIncome()));
//			SSNTextField.setText(editedTenant.getSsn());
//			rentTextField.setText(String.valueOf(editedTenant.getRent()));
//			movInDatePicker.setPromptText(
//					editedTenant.getMovInDate().toString());
//			numChildrenTextField.setText(String.valueOf(editedTenant.getNumChildren()));
//
//			if (editedTenant.getRoomMates() != null) {
//				spNameLoadConf.setText(editedTenant.getRoomMates().getFirstName()
//						 + editedTenant.getRoomMates().getLastName());
//			}
		}
		
		@FXML
		public void addSpouseToTenant() {
//			if(Database.getInstance().getCurrTnant().getSpouse() != null) {
//				Main.getLibrary().additionWindow(FXMLLocation.SPOUSEADD);
//
//				Database.getInstance().getCurrTnant().setSpouse(tnantRoomMate);
//			}else {
//				errorText.setVisible(true);
//				errorText.setText("Tenant already has Spouse");
//			}
			
		}

		@FXML
		public void editTenant() {
//			try {
//				Tenant temp = new Tenant();
//
//				LocalDate date;
//
//				temp.setId(Database.getInstance().getCurrTnant().getId());
//				if(!apartmentChoice.getSelectionModel().getSelectedItem().equals(
//						Database.getInstance().getCurrApt())) {
//					temp.setFk(apartmentChoice.getSelectionModel().getSelectedItem().getId());
//				}
//				temp.setFirstName(firstNameTextField.getText());
//				temp.setLastName(lastNameTextField.getText());
//				temp.setPhone(phoneTextField.getText());
//				temp.setEmail(emailTextField.getText());
//				temp.setSsn(SSNTextField.getText());
//				temp.setRent(Double.parseDouble(rentTextField.getText()));
//				temp.setNumChildren(Integer.parseInt(numChildrenTextField.getText()));
//
//				if (movInDatePicker.getValue() != null) {
//					date = movInDatePicker.getValue();
//					temp.setMovInDate(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
//				} else {
//					temp.setMovInDate(Database.getInstance().getCurrTnant().getMovInDate());
//				}
//				if (dateOfBirthDatePicker.getValue() != null) {
//					date = dateOfBirthDatePicker.getValue();
//					temp.setDateOfBirth(Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault()))));
//				} else {
//					temp.setDateOfBirth(Database.getInstance().getCurrTnant().getDateOfBirth());
//				}
//				temp.setAnnualIncome(Integer.parseInt(annualIncomeTextField.getText()));
//
//				Database.getInstance().edit(temp);
//
//
//				//Main.getLibrary().closePopup();
//			} catch (NumberFormatException e) {
//				errorText.setVisible(true);
//				errorText.setText("Invalid data in fields");
//			}
		}
}
