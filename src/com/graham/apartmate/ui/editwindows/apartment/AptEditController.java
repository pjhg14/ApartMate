package com.graham.apartmate.ui.editwindows.apartment;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.main.Main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

//TODO: Finish edit window (edit button does not work)
// Javadoc's for every method
public class AptEditController {
	@FXML
	private TextField addressTextField;

	@FXML
	private TextField cityTextField;

	@FXML
	private ComboBox<String> stateComboBox;

	@FXML
	private TextField zipTextField;

	@FXML
	private TextField capacityTextField;

	@FXML
	private Text errorText;

	@FXML
	private Button editButton;

	@FXML
	public void initialize() {
		setText();
	}

	@FXML
	public void editApartment() {
		try {
			Apartment editedApartment = new Apartment();

			editedApartment.setId(Database.getInstance().getCurrApt().getId());
			editedApartment.setAddress(addressTextField.getText());
			editedApartment.setCity(cityTextField.getText());

			if (stateComboBox.getValue().equals("State") || stateComboBox.getValue().equals("")) {
				throw new NumberFormatException("Invalid Value in State box");
			}
			String shortState = stateComboBox.getValue()
					.substring(stateComboBox.getValue().indexOf('-') + 1).trim();
			editedApartment.setState(shortState);

			editedApartment.setZipCode(zipTextField.getText());
			editedApartment.setCapacity(Integer.parseInt(capacityTextField.getText()));

			Database.getInstance().edit(editedApartment);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText("Invalid data in fields");
		} catch (IndexOutOfBoundsException e) {
			if (Main.DEBUG)
				System.out.println("Error occurred: " + e.getMessage());
		}
	}

	private void setText() {
		addressTextField.setText(Database.getInstance().getCurrApt().getAddress());
		cityTextField.setText(Database.getInstance().getCurrApt().getCity());

		List<String> stateList = setStateList();
		stateComboBox.setItems(FXCollections.observableArrayList(stateList));
		for (String state : stateList) {
			if (state.substring(state.indexOf('-') + 1).trim().equals(Database.getInstance().getCurrApt().getState())) {
				//stateComboBox.setPromptText(state);
				stateComboBox.getSelectionModel().select(state);
			}
		}

		zipTextField.setText(Database.getInstance().getCurrApt().getZipCode());
		capacityTextField.setText(Integer.toString(Database.getInstance().getCurrApt().getCapacity()));
	}

	private List<String> setStateList() {
		List<String> temp = new ArrayList<>();

		temp.add("Alabama - AL");
		temp.add("American Samoa - AS");
		temp.add("Alaska - AK");
		temp.add("Arizona - AZ");
		temp.add("Arkansas - AR");
		temp.add("California - CA");
		temp.add("Colorado - CO");
		temp.add("Connecticut - CT");
		temp.add("District of Columbia - DC");
		temp.add("Delaware - DE");
		temp.add("Federated States of Micronesia - FM");
		temp.add("Florida - FL");
		temp.add("Georgia - GA");
		temp.add("Guam - GU");
		temp.add("Hawaii - HI");
		temp.add("Idaho - ID");
		temp.add("Illinois - IL");
		temp.add("Indiana - IN");
		temp.add("Iowa - IA");
		temp.add("Kansas - KS");
		temp.add("Kentucky - KY");
		temp.add("Louisiana - LA");
		temp.add("Maine - ME");
		temp.add("Maryland - MD");
		temp.add("Marshall Islands - MH");
		temp.add("Massachusetts - MA");
		temp.add("Michigan - MI");
		temp.add("Minnesota - MN");
		temp.add("Mississippi - MS");
		temp.add("Missouri - MO");
		temp.add("Montana - MT");
		temp.add("Nebraska - NE");
		temp.add("Nevada - NV");
		temp.add("New Hampshire - NH");
		temp.add("New Jersey - NJ");
		temp.add("New Mexico - NM");
		temp.add("New York - NY");
		temp.add("North Carolina - NC");
		temp.add("North Dakota - ND");
		temp.add("Northern Mariana Islands - MP");
		temp.add("Ohio - OH");
		temp.add("Oklahoma - OK");
		temp.add("Oregon - OR");
		temp.add("Palau - PW");
		temp.add("Pennsylvania - PA");
		temp.add("Rhode Island - RI");
		temp.add("Puerto Rico - PR");
		temp.add("South Carolina - SC");
		temp.add("South Dakota - SD");
		temp.add("Tennessee - TN");
		temp.add("Texas - TX");
		temp.add("Utah - UT");
		temp.add("Vermont - VT");
		temp.add("Virgin Islands - VI");
		temp.add("Virginia - VA");
		temp.add("Washington - WA");
		temp.add("West Virginia - WV");
		temp.add("Wisconsin - WI");
		temp.add("Wyoming - WY");

		return temp;
	}
}
