package com.apartmate.ui.controllers.info;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
// Add TreeView Functionality
public class CandInfoController {

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

	private Candidate currCand;

	@FXML
	public void initialize() {
		currCand = Database.getInstance().getCurrCand();

		// Set Text
		setText();
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.CANDINFO);
	}

	@FXML
	public void backToCandidates() {
		Database.getInstance().setCurrCand(null);
		Main.getLibrary().mainWindow(FXMLLocation.CANDIDATE);
	}

	@FXML
	public void editCandidate() {
		Main.getLibrary().editWindow(FXMLLocation.CANDEDIT);
	}

	@FXML
	public void editSpouse() {
		Main.getLibrary().editWindow(FXMLLocation.SPOUSEEDIT);
	}

	private void setText() {
		nameText.setText("Name: " + currCand.getFirstName() + " " + currCand.getLastName());
		addressText.setText("Address: " + Database.getInstance().getCurrApt().getAddress());
		phoneText.setText("Phone: " + currCand.getPhone());
		emailText.setText("Email: " + currCand.getEmail());
		SSNText.setText("SSN: " + currCand.getSSN());

		try {
			if (currCand.getSpouse().getFirstName().equals(""))
				throw new NullPointerException("Candidate has no Spouse");
			spNameText
					.setText("Name: " + currCand.getSpouse().getFirstName() + " " + currCand.getSpouse().getLastName());
			spAddressText.setText("Address: " + Database.getInstance().getCurrApt().getAddress());
			spPhoneText.setText("Phone: " + currCand.getSpouse().getPhone());
			spEmailText.setText("Email: " + currCand.getSpouse().getEmail());
			spSSNText.setText("SSN: " + currCand.getSpouse().getSSN());
		} catch (NullPointerException e) {
			spTitle.setVisible(false);
			spouseInfoBox.setVisible(false);
			editButton2.setVisible(false);

			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}
}
