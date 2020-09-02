package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.apartment;

import java.util.Optional;

import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.libraries.FXMLLocation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AptInfoController extends FXMLController {

	@FXML
	private ImageView image;

	@FXML
	private Text addressText;
	@FXML
	private Text cityText;
	@FXML
	private Text stateText;
	@FXML
	private Text capacityText;
	@FXML
	private Text numTenants;

	@FXML
	private Button showTenants;

	@FXML
	private Button showCandidates;

	@FXML
	private Button showContractors;

	@FXML
	private Button showIssues;

	// ---------------------------------------------------------
	// ListView Variables
	@FXML
	private ListView<Bill> billListView;

	@FXML
	private Button editBillButton;

	@FXML
	private Button delBillButton;

	@FXML
	private Button invoiceButton;
	// ---------------------------------------------------------

	private Apartment selectedApt;

	@Override
	public void init() {
		selectedApt = (Apartment) currentTable;

		setText();
		setListViews();
	}

	@FXML
	public void refresh() {
		//Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showTenants() {
		//Convert sub-window to tenant list
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showCandidates() {
		//Convert sub-window to candidate list
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showContractors() {
		//Convert sub-window to contractor list
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showIssues() {
		//Main.getLibrary().noteLogWindow(selectedApt, FXMLLocation.ISSINSP);
		System.out.println("Doesn't work yet sorry");
	}


	@FXML
	public void addBill() {
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void editBill() {
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void deleteBill() {
//		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//
//		alert.setContentText("Are you sure you want to delete: " +
//				billListView.getSelectionModel().getSelectedItem().getCompanyName());
//
//		Optional<ButtonType> optional = alert.showAndWait();
//
//		if (optional.isPresent() && optional.get() == ButtonType.OK) {
//			invoiceButton.setDisable(true);
//			delBillButton.setDisable(true);
//			editBillButton.setDisable(true);
//		}
		System.out.println("Doesn't work yet sorry");
	}

	public void setButtons() {

	}

	//TODO: Move to Apartment Class when List is converted to Observable List
	private void setListViews() {

	}

	private void setText() {
		addressText.setText("Address: " + selectedApt.getAddress());
		cityText.setText("City: " + selectedApt.getCity());
		stateText.setText("State: " + selectedApt.getState());
		capacityText.setText("Capacity: " + selectedApt.getCapacity());
		numTenants.setText("Occupied Rooms: " + selectedApt.getNumTenants());
	}


}
