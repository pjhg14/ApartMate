package com.graham.apartmate.ui.controllers.mainScreen.subscreen.infocontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.FXMLLocation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Objects;

//TODO: Javadoc's for every method
public class CandInfoController extends FXMLController {

	@FXML
	private Button backButton;

	// ---------------------------------------------------------
	// Tenant fxml objects//////////////////////////////////////
	// ---------------------------------------------------------
	@FXML
	private ImageView image;

	@FXML
	private Text nameText;
	@FXML
	private Text addressText;
	@FXML
	private Text phoneText;
	@FXML
	private Text emailText;
	@FXML
	private Text SSNText;
	@FXML
	private Text rentText;
	@FXML
	private Text balanceText;
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	// ---------------------------------------------------------
	// Spouse fxml objects//////////////////////////////////////
	// ---------------------------------------------------------
	@FXML
	private Text spTitle;

	@FXML
	private ImageView spImage;

	@FXML
	private Text spNameText;
	@FXML
	private Text spAddressText;
	@FXML
	private Text spPhoneText;
	@FXML
	private Text spEmailText;
	@FXML
	private Text spSSNText;
	// ---------------------------------------------------------
	// ---------------------------------------------------------

	@FXML
	private Button editButton;

	@FXML
	private Button editButton2;

	@FXML
	private HBox spouseInfoBox;

	private Candidate selectedCand;

	@FXML
	public void initialize() {
		selectedCand = (Candidate) currentTable;

		setText();
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.CANDINFO);
	}

	@FXML
	public void backToCandidates() {
		Main.getLibrary().mainWindow(FXMLLocation.CANDIDATE);
	}

	@FXML
	public void editCandidate() {
		Main.getLibrary().editWindow(selectedCand, FXMLLocation.CANDEDIT);
	}

	@FXML
	public void editSpouse() {
		Main.getLibrary().editWindow(selectedCand, FXMLLocation.SPOUSEEDIT);
	}

	private void setText() {
		nameText.setText("Name: " + selectedCand.getFullName());

		addressText.setText("Address: " + Objects.requireNonNull(
				Database.getInstance().getRelatedApartment(selectedCand)).getAddress());

		phoneText.setText("Phone: " + selectedCand.getPhone());
		emailText.setText("Email: " + selectedCand.getEmail());
		SSNText.setText("SSN: " + selectedCand.getSSN());

		try {
			if (selectedCand.getSpouse().getFirstName().equals(""))
				throw new NullPointerException("Candidate has no Spouse");

			spNameText.setText("Name: " + selectedCand.getSpouse().getFullName());

			spAddressText.setText("Address: " + Objects.requireNonNull(
					Database.getInstance().getRelatedApartment(selectedCand)).getAddress());

			spPhoneText.setText("Phone: " + selectedCand.getSpouse().getPhone());
			spEmailText.setText("Email: " + selectedCand.getSpouse().getEmail());
			spSSNText.setText("SSN: " + selectedCand.getSpouse().getSSN());
		} catch (NullPointerException e) {
			spTitle.setVisible(false);
			spouseInfoBox.setVisible(false);
			editButton2.setVisible(false);

			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}
}
