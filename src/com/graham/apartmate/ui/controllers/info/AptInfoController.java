package com.graham.apartmate.ui.controllers.info;

import java.util.Optional;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.database.tables.subTables.Insurance;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.FXMLLocation;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
// Add TreeView Functionality
public class AptInfoController {

	@FXML
	private Button prevButton;

	@FXML
	private ImageView image;

	@FXML
	private Text address;
	@FXML
	private Text city;
	@FXML
	private Text state;
	@FXML
	private Text capacity;
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
	private ListView<Insurance> insListView;

	@FXML
	private Button newInsButton;

	@FXML
	private Button editInsButton;

	@FXML
	private Button delInsButton;

	@FXML
	private Button invoiceButton1;


	@FXML
	private ListView<Bill> billListView;

	@FXML
	private Button newBillButton;

	@FXML
	private Button editBillButton;

	@FXML
	private Button delBillButton;

	@FXML
	private Button invoiceButton2;

	// ---------------------------------------------------------

	@FXML
	private Button editButton;

	private ObservableList<Insurance> insList;

	private ObservableList<Bill> billList;

	private Apartment currApt;

	@FXML
	public void initialize() {
		currApt = Database.getInstance().getCurrApt();

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
		Database.setCurrTable(DBTables.ISSUES);
		Main.getLibrary().popWindow(FXMLLocation.ISSINSP);
	}

	@FXML
	public void viewInsInvoices() {
		Database.setCurrTable(DBTables.INSURANCES);
		Database.getInstance().setCurrIns(insListView.getSelectionModel().getSelectedItem());
		Main.getLibrary().popWindow(FXMLLocation.INVOICES);
	}

	@FXML
	public void addInsurance() {
		Database.getInstance().setCurrIns(insListView.getSelectionModel().getSelectedItem());
		Main.getLibrary().additionWindow(FXMLLocation.INSADD);
		Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
	}

	@FXML
	public void editInsurance() {
		Database.getInstance().setCurrIns(insListView.getSelectionModel().getSelectedItem());
		Main.getLibrary().editWindow(FXMLLocation.INSEDIT);
	}

	@FXML
	public void deleteInsurance() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setContentText("Are you sure you want to delete: " +
				insListView.getSelectionModel().getSelectedItem().getName());

		Optional<ButtonType> optional = alert.showAndWait();

		if (optional.isPresent() && optional.get() == ButtonType.OK) {
			invoiceButton1.setDisable(true);
			delInsButton.setDisable(true);
			editInsButton.setDisable(true);
			insList.remove(insListView.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	public void viewBillInvoices() {
		Database.setCurrTable(DBTables.BILLS);
		Main.getLibrary().popWindow(FXMLLocation.INVOICES);
	}

	@FXML
	public void addBill() {
		Database.getInstance().setCurrBill(billListView.getSelectionModel().getSelectedItem());
		Main.getLibrary().additionWindow(FXMLLocation.BILLADD);
	}

	@FXML
	public void editBill() {
		Database.getInstance().setCurrBill(billListView.getSelectionModel().getSelectedItem());
		Main.getLibrary().editWindow(FXMLLocation.BILLEDIT);
	}

	@FXML
	public void deleteBill() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setContentText("Are you sure you want to delete: " +
				billListView.getSelectionModel().getSelectedItem().getCompanyName());

		Optional<ButtonType> optional = alert.showAndWait();

		if (optional.isPresent() && optional.get() == ButtonType.OK) {
			invoiceButton2.setDisable(true);
			delBillButton.setDisable(true);
			editBillButton.setDisable(true);
			billList.remove(billListView.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	public void setButtons() {
		if (insListView.getSelectionModel().getSelectedItem() != null) {
			Database.getInstance().setCurrIns(insListView.getSelectionModel().getSelectedItem());
			invoiceButton1.setDisable(false);
			delInsButton.setDisable(false);
			editInsButton.setDisable(false);
		}

		if (billListView.getSelectionModel().getSelectedItem() != null) {
			Database.getInstance().setCurrBill(billListView.getSelectionModel().getSelectedItem());
			invoiceButton2.setDisable(false);
			delBillButton.setDisable(false);
			editBillButton.setDisable(false);
		}
	}

	@FXML
	public void backToApartments() {
		Database.getInstance().setCurrApt(null);
		Main.getLibrary().mainWindow(FXMLLocation.APARTMENT);
	}

	@FXML
	public void editApartment() {
		Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
	}

	private void setListViews() {
		insList = FXCollections.observableArrayList(Database.getInstance().getCurrApt().getInsurances());
		billList = FXCollections.observableArrayList(Database.getInstance().getCurrApt().getBills());

		insList.addListener((ListChangeListener<Insurance>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (Insurance insurance : c.getAddedSubList()) {
						Database.getInstance().sqlBridge.insert(insurance);
					}
				}

				if (c.wasRemoved()) {
					for (Insurance insurance : c.getRemoved()) {
						Database.getInstance().sqlBridge.delete(insurance);
					}
				}

				if (c.wasUpdated()) {
					for (Insurance insurance : c.getList()) {
						Database.getInstance().sqlBridge.update(insurance);
					}
				}
			}
		});

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

		insListView.setItems(insList);

		billListView.setItems(billList);
	}

	private void setText() {
		address.setText("Address: " + currApt.getAddress());
		city.setText("City: " + currApt.getCity());
		state.setText("State: " + currApt.getState());
		capacity.setText("Capacity: " + currApt.getCapacity());
		numTenants.setText("Occupied Rooms: " + currApt.getNumTenants());
	}

}
