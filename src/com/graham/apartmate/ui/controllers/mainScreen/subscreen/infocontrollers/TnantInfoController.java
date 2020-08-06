package com.graham.apartmate.ui.controllers.mainScreen.subscreen.infocontrollers;

import java.util.Date;
import java.util.Objects;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.database.tables.subTables.Invoice;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.FXMLLocation;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
public class TnantInfoController extends FXMLController {

	@FXML
	private Button backButton;
	// ---------------------------------------------------------
	// TreeView fxml objects////////////////////////////////////
	// ---------------------------------------------------------
	@FXML
	private TreeView<String> quickTreeView;
	// ---------------------------------------------------------
	// ---------------------------------------------------------

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
	private TableView<Invoice> invTable;

	@FXML
	private TableColumn<Invoice, Number> payments;
	@FXML
	private TableColumn<Invoice, Number> dues;
	@FXML
	private TableColumn<Invoice, Number> balances;
	@FXML
	private TableColumn<Invoice, Number> totalPaid;
	@FXML
	private TableColumn<Invoice, Number> totalDues;
	@FXML
	private TableColumn<Invoice, Date> paymentDates;
	@FXML
	private TableColumn<Invoice, Date> dueDates;
	@FXML
	private TableColumn<Invoice, Date> dateCreated;
	@FXML
	private TableColumn<Invoice, Date> dateModified;

	// ---------------------------------------------------------

	@FXML
	private Button inspectionButton;

	@FXML
	private Button editButton;

	@FXML
	private Button editButton2;

	@FXML
	private Button tablePopup;
	
	@FXML
	private HBox spouseInfoBox;

	private ObservableList<Invoice> observIns;

	private Tenant selectedTnant;

	@FXML
	public void initialize() {
		//To make code more succinct
		selectedTnant = (Tenant) currentTable;

		setText();

		//TODO: Create single instance of Tenant's Invoice TableView for TnantInfo & InvoiceController

		// Initialize TableView
		observIns = FXCollections.observableArrayList(Database.getInstance().getCurrTnant().getInvoices());
		setListeners();

		// Column set-up
		setTableColumns();

		invTable.setItems(observIns);
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
	}

	@FXML
	public void viewInvoices() {
		Database.setCurrTable(DBTables.TENANTS);
		Main.getLibrary().invoiceWindow(selectedTnant, FXMLLocation.INVOICES);
	}

	@FXML
	public void backToTenants() {
		Database.getInstance().setCurrTnant(null);
		Main.getLibrary().mainWindow(FXMLLocation.TENANT);
	}

	@FXML
	public void showInspections() {
		Database.setCurrTable(DBTables.INSPECTIONS);
		Main.getLibrary().invoiceWindow(selectedTnant, FXMLLocation.ISSINSP);
	}

	@FXML
	public void editTenant() {
		Main.getLibrary().editWindow(selectedTnant, FXMLLocation.TNANTEDIT);
	}

	@FXML
	public void editSpouse() {
		Main.getLibrary().editWindow(selectedTnant.getSpouse(), FXMLLocation.SPOUSEEDIT);
	}

	private void setText() {
		// Set Tenant Text
		nameText.setText(selectedTnant.getFullName());
		addressText.setText(Objects.requireNonNull(Database.getInstance().getRelatedApartment(selectedTnant)).getAddress());
		phoneText.setText(selectedTnant.getPhone());
		emailText.setText(selectedTnant.getEmail());
		SSNText.setText(selectedTnant.getSSN());
		rentText.setText("" + selectedTnant.getRent());
		try {
			balanceText.setText(String.valueOf(selectedTnant.getInvoices().get(selectedTnant.getInvoices().size() - 1).getBalance()));
		} catch(IndexOutOfBoundsException e) {
			balanceText.setText("0");
		}

		try {
			// Set Spouse text
			if (selectedTnant.getSpouse().getFirstName().equals(""))
				throw new NullPointerException("Tenant has no Spouse");

			spNameText.setText(selectedTnant.getSpouse().getFirstName() + " " + selectedTnant.getSpouse().getLastName());
			spAddressText.setText(Objects.requireNonNull(Database.getInstance().getRelatedApartment(selectedTnant)).getAddress());
			spPhoneText.setText(selectedTnant.getSpouse().getPhone());
			spEmailText.setText(selectedTnant.getSpouse().getEmail());
			spSSNText.setText(selectedTnant.getSpouse().getSSN());
		} catch(NullPointerException npe) {
			spTitle.setVisible(false);
			spouseInfoBox.setVisible(false);
			editButton2.setVisible(false);

			if (Main.DEBUG)
				System.out.println(npe.getMessage());
		}
	}

	private void setListeners() {
		observIns.addListener((ListChangeListener<Invoice>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					//observIns.addAll(c.getAddedSubList());

					Database.getInstance().getEvents().updateTotals(observIns);
				}
				if (c.wasRemoved()) {
					//Database.getInstance().getCurrTnant().getInvoices().removeAll(c.getRemoved());
					Database.getInstance().getEvents().updateTotals(observIns);
				}
			}
		});
	}

	private void setTableColumns() {
		//Don't know if table should be editable in normal Tenant info view
		payments.setCellValueFactory(new PropertyValueFactory<>("payment"));
//		payments.setEditable(true);
//		payments.setOnEditCommit(event -> {
//					Invoice editedInvoice = event.getTableView().getItems().get(
//							event.getTablePosition().getRow());
//
//					int invoiceIndex = event.getTablePosition().getRow();
//
//					editedInvoice.setPayment(event.getNewValue().doubleValue());
//
//					//Update totals when updated
//					Database.getInstance().getEvents().updateTotals(
//							event.getTableView().getItems(), invoiceIndex);
//
//					//Update dateModified when updated
//					Database.getInstance().getEvents().upDateModified(
//							event.getTableView().getItems().get(invoiceIndex));
//				}
//		);

		dues.setCellValueFactory(new PropertyValueFactory<>("dues"));
//		dues.setEditable(true);
//		dues.setOnEditCommit(event -> {
//					Invoice editedInvoice = event.getTableView().getItems().get(
//							event.getTablePosition().getRow());
//
//					int invoiceIndex = event.getTablePosition().getRow();
//
//					editedInvoice.setDues(event.getNewValue().doubleValue());
//
//					//Update totals when updated
//					Database.getInstance().getEvents().updateTotals(
//							event.getTableView().getItems(), invoiceIndex);
//
//					//Update dateModified when updated
//					Database.getInstance().getEvents().upDateModified(
//							event.getTableView().getItems().get(invoiceIndex));
//				}
//		);

		balances.setCellValueFactory(new PropertyValueFactory<>("balance"));
		totalPaid.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));
		totalDues.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
		paymentDates.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
		dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		dateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
	}
}
