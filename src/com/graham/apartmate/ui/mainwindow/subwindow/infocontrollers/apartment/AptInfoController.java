package com.graham.apartmate.ui.mainwindow.subwindow.infocontrollers.apartment;

import java.util.Optional;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.FXMLLocation;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
	private ObservableList<Bill> billList;

	private Apartment selectedApt;

	@FXML
	public void initialize() {
		selectedApt = (Apartment) currentTable;

		setText();
		setListViews();
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
	}

	@FXML
	public void showTenants() {
		Main.getLibrary().mainWindow(FXMLLocation.TENANT);
	}

	@FXML
	public void showCandidates() {
		Main.getLibrary().mainWindow(FXMLLocation.CANDIDATE);
	}

	@FXML
	public void showContractors() {
		Main.getLibrary().mainWindow(FXMLLocation.CONTRACTOR);
	}

	@FXML
	public void showIssues() {
		Main.getLibrary().noteLogWindow(selectedApt, FXMLLocation.ISSINSP);
	}


	@FXML
	public void viewInvoices() {
		Main.getLibrary().invoiceWindow(selectedApt, FXMLLocation.INVOICES);
	}

	@FXML
	public void addBill() {
		Main.getLibrary().additionWindow(billListView.getSelectionModel().getSelectedItem(), FXMLLocation.BILLADD);
	}

	@FXML
	public void editBill() {
		Main.getLibrary().editWindow(billListView.getSelectionModel().getSelectedItem(), FXMLLocation.BILLEDIT);
	}

	@FXML
	public void deleteBill() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setContentText("Are you sure you want to delete: " +
				billListView.getSelectionModel().getSelectedItem().getCompanyName());

		Optional<ButtonType> optional = alert.showAndWait();

		if (optional.isPresent() && optional.get() == ButtonType.OK) {
			invoiceButton.setDisable(true);
			delBillButton.setDisable(true);
			editBillButton.setDisable(true);
			billList.remove(billListView.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	public void setButtons() {
		if (billListView.getSelectionModel().getSelectedItem() != null) {
			Database.getInstance().setCurrBill(billListView.getSelectionModel().getSelectedItem());
			invoiceButton.setDisable(false);
			delBillButton.setDisable(false);
			editBillButton.setDisable(false);
		}
	}


	//TODO: Move to Apartment Class when List is converted to Observable List
	private void setListViews() {
		billList = FXCollections.observableArrayList(Database.getInstance().getCurrApt().getBills());

		billList.addListener((ListChangeListener<Bill>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach(bill -> Database.getInstance().sqlBridge.insert(bill));
				}

				if (c.wasRemoved()) {
					c.getRemoved().forEach(bill -> Database.getInstance().sqlBridge.delete(bill));
				}

				if (c.wasUpdated()) {
					c.getList().forEach(bill -> Database.getInstance().sqlBridge.update(bill));
				}
			}
		});

		billListView.setItems(billList);
	}

	private void setText() {
		addressText.setText("Address: " + selectedApt.getAddress());
		cityText.setText("City: " + selectedApt.getCity());
		stateText.setText("State: " + selectedApt.getState());
		capacityText.setText("Capacity: " + selectedApt.getCapacity());
		numTenants.setText("Occupied Rooms: " + selectedApt.getNumTenants());
	}


}
