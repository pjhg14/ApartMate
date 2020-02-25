package com.apartmate.ui.controllers.info;

import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.subTables.Insurance;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class AptInfoController {

	@FXML
	private Button backButton;

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
	private Text insuranceDue;

	@FXML
	private Button showTenants;
	@FXML
	private Button showCandidates;
	@FXML
	private Button showContractors;

	@FXML
	private Button showIssues;
	@FXML
	private Button showInspections;

	// ---------------------------------------------------------
	// TableView Variables
	@FXML
	private TableView<Insurance> insTable;

	@FXML
	private TableColumn<Insurance, Number> payments;
	@FXML
	private TableColumn<Insurance, Number> balances;
	@FXML
	private TableColumn<Insurance, Number> totalDues;
	@FXML
	private TableColumn<Insurance, Date> paymentDates;
	@FXML
	private TableColumn<Insurance, Date> dueDates;
	@FXML
	private TableColumn<Insurance, Date> dateCreated;
	@FXML
	private TableColumn<Insurance, Date> dateModified;
	// ---------------------------------------------------------

	@FXML
	private Button editButton;

	@FXML
	private Button tablePopup;

	private ObservableList<Insurance> observIns;

	private Apartment currApt;

	@FXML
	public void initialize() {
		currApt = Database.currApt;

		// Set Text
		address.setText("Address: " + currApt.getAddress());
		city.setText("City: " + currApt.getCity());
		state.setText("State: " + currApt.getState());
		capacity.setText("Capacity: " + currApt.getCapacity());
		numTenants.setText("Occupied Rooms: " + currApt.getNumTenants());
		if (currApt.getInsurances().size() != 0) {
			insuranceDue.setText(
					"Insurance Due: " + currApt.getInsurances().get(currApt.getInsurances().size() - 1).getBalance());
		}
		insuranceDue.setText("Insurance Due: 0");

		// Initialize TableView
		observIns = FXCollections.observableArrayList(Database.getInstance().getCurrApt().getInsurances());
		observIns.addListener((ListChangeListener<Insurance>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					Database.getInstance().getCurrApt().getInsurances().addAll(c.getAddedSubList());

					c.getAddedSubList().forEach(ins -> {
						Database.getInstance().getEvents().updateTotals(ins);
					});
				}
				if (c.wasRemoved()) {
					Database.getInstance().getCurrApt().getInsurances().removeAll(c.getRemoved());
				}
			}
		});

		// Column set-up
		balances.setCellValueFactory(new PropertyValueFactory<>("balance"));
		payments.setCellValueFactory(new PropertyValueFactory<>("payment"));
		totalDues.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
		paymentDates.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
		dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		dateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));

		insTable.setItems(observIns);
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
		Database.getInstance().setIssInsSwitch(true);
		Main.getLibrary().mainWindow(FXMLLocation.ISSINSP);
	}

	@FXML
	public void showInspections() {
		Database.getInstance().setIssInsSwitch(false);
		Main.getLibrary().mainWindow(FXMLLocation.ISSINSP);
	}

	@FXML
	public void openTable() {
		Main.getLibrary().popWindow(FXMLLocation.INSURANCES);
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

}
