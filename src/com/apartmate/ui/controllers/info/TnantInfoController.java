package com.apartmate.ui.controllers.info;

import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.TnantInvoice;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TnantInfoController {

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

	// ---------------------------------------------------------
	// TableView Variables
	@FXML
	private TableView<TnantInvoice> invTable;

	@FXML
	private TableColumn<TnantInvoice, Number> payments;
	@FXML
	private TableColumn<TnantInvoice, Number> balances;
	@FXML
	private TableColumn<TnantInvoice, Number> totalDues;
	@FXML
	private TableColumn<TnantInvoice, Date> paymentDates;
	@FXML
	private TableColumn<TnantInvoice, Date> dueDates;
	@FXML
	private TableColumn<TnantInvoice, Date> dateCreated;
	@FXML
	private TableColumn<TnantInvoice, Date> dateModified;
	// ---------------------------------------------------------

	@FXML
	private Button editButton;

	@FXML
	private Button editButton2;

	@FXML
	private Button tablePopup;
	
	@FXML
	private HBox spouseInfoBox;

	private ObservableList<TnantInvoice> observIns;

	private Tenant currTnant;

	@FXML
	public void initialize() {
		currTnant = Database.getInstance().getCurrTnant();

		// Set Tenant Text
		nameText.setText("Name: " + currTnant.getFirstName() + " " + currTnant.getLastName());
		addressText.setText("Address: " + Database.getInstance().getCurrApt().getAddress());
		phoneText.setText("Phone: " + currTnant.getPhone());
		emailText.setText("Email: " + currTnant.getEmail());
		SSNText.setText("SSN: " + currTnant.getSSN());
		rentText.setText("Rent: " + currTnant.getRent());
		try {
			balanceText.setText(String.valueOf(currTnant.getInvoices().get(currTnant.getInvoices().size() - 1).getBalance()));
		} catch(IndexOutOfBoundsException e) {
			balanceText.setText("0");
		}
		// Initialize TableView
		observIns = FXCollections.observableArrayList(Database.getInstance().getCurrTnant().getInvoices());
		observIns.addListener((ListChangeListener<TnantInvoice>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					Database.getInstance().getCurrTnant().getInvoices().addAll(c.getAddedSubList());

					c.getAddedSubList().forEach(inv -> {
						Database.getInstance().getEvents().updateTotals(inv);
					});
				}
				if (c.wasRemoved()) {
					Database.getInstance().getCurrTnant().getInvoices().removeAll(c.getRemoved());
				}
			}
		});

		// Column set-up
		balances.setCellValueFactory(new PropertyValueFactory<>("balance"));
		payments.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
		totalDues.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
		paymentDates.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
		dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		dateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));

		invTable.setItems(observIns);

		try {
		// Set Spouse text
		spNameText.setText(currTnant.getSpouse().getFirstName() + " " + currTnant.getSpouse().getLastName());
		spAddressText.setText(Database.getInstance().getCurrApt().getAddress());
		spPhoneText.setText(currTnant.getSpouse().getPhone());
		spEmailText.setText(currTnant.getSpouse().getEmail());
		spSSNText.setText(currTnant.getSpouse().getSSN());
		} catch(NullPointerException npe) {
			spTitle.setVisible(false);
			spouseInfoBox.setVisible(false);
			editButton2.setVisible(false);
		}
	}

	@FXML
	public void openTable() {
		Main.getLibrary().popWindow(FXMLLocation.TNANTINVOICES);
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
	}

	@FXML
	public void backToTenants() {
		Database.getInstance().setCurrTnant(null);
		Main.getLibrary().mainWindow(FXMLLocation.TENANT);
	}

	@FXML
	public void editTenant() {
		Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
	}

	@FXML
	public void editSpouse() {
		Main.getLibrary().editWindow(FXMLLocation.SPOUSEEDIT);
	}
}
