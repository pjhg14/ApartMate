package com.apartmate.ui.controllers.addition;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.main.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AptAddController {

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
	private TextField insBillTextField;

	@FXML
	private Text errorText;

	@FXML
	private Button addButton;

	private ObservableList<String> stateList;

	@FXML
	public void initialize() {
		List<String> temp = setStateList();
		
		stateList = FXCollections.observableArrayList(temp);

		stateComboBox.setItems(stateList);
	}

	@FXML
	public void addToApartments() {
		int id;

		if (Database.getInstance().getApartments().isEmpty()) {
			id = 1;
		} else {
			id = Database.getInstance().getApartments().get(Database.getInstance().getApartments().size() - 1).getId()
					+ 1;
		}

		try {
			Apartment temp = new Apartment();
			String parse;

			temp.setId(id);
			temp.setAddress(addressTextField.getText());
			temp.setCity(cityTextField.getText());

			if(stateComboBox.getValue() == "State" || stateComboBox.getValue() == "") {
				throw new NumberFormatException("Invalid Value in State box");
			}
			parse = stateComboBox.getValue()
					.substring(stateComboBox.getValue().indexOf('-') + 1).trim();
			temp.setState(parse);

			temp.setZipCode(zipTextField.getText());
			temp.setCapacity(Integer.parseInt(capacityTextField.getText()));
			if (insBillTextField.getText() == "") {
				throw new NumberFormatException("Invalid Value in Monthly insurance field");
			} else {
				parse = insBillTextField.getText();
			}
			temp.setInsBill(Double.parseDouble(parse));

			Database.getInstance().add(temp);

			Main.getLibrary().closePopup();
		} catch (NumberFormatException e) {
			errorText.setVisible(true);
			errorText.setText(e.getMessage());
		}
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
