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
	}

	@FXML
	public void showTenants() {
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showCandidates() {
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showContractors() {
		System.out.println("Doesn't work yet sorry");
	}

	@FXML
	public void showIssues() {
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
		System.out.println("Doesn't work yet sorry");
	}

	public void setButtons() {

	}

	private void setText() {

	}


}
