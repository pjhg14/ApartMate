package com.apartmate.ui.controllers.info;

import java.util.Date;

import com.apartmate.database.dbMirror.DBTables;
import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.subTables.Invoice;
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

//TODO: Javadoc's for every method
// Add TreeView Functionality
public class ContInfoController {

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
	private Text billText;
	@FXML
	private Text balanceText;
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
	private Button editButton;

	@FXML
	private Button editButton2;

	@FXML
	private Button tablePopup;

	private ObservableList<Invoice> observIns;

	private Contractor contractor;

	@FXML
	public void initialize() {
		contractor = Database.getInstance().getCurrCont();

		// Set Tenant Text
		setText();

		// Initialize TableView
		observIns = FXCollections.observableArrayList(Database.getInstance().getCurrCont().getInvoices());
		observIns.addListener((ListChangeListener<Invoice>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					Database.getInstance().getEvents().updateTotals(observIns);
				}
				if (c.wasRemoved()) {
					Database.getInstance().getCurrCont().getInvoices().removeAll(c.getRemoved());
				}
			}
		});

		// Column set-up
		setTableColumns();

		invTable.setItems(observIns);
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
	}

	@FXML
	public void viewInvoices() {
		Database.setCurrTable(DBTables.CONTRACTORS);
		Main.getLibrary().popWindow(FXMLLocation.INVOICES);
	}

	@FXML
	public void backToContractors() {
		Database.getInstance().setCurrCont(null);
		Main.getLibrary().mainWindow(FXMLLocation.CONTRACTOR);
	}

	@FXML
	public void editContractor() {
		Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
	}

	private void setText() {
		nameText.setText("Name: " + contractor.getName());
		addressText.setText("Address: " + Database.getInstance().getCurrApt().getAddress());
		phoneText.setText("Phone: " + contractor.getPhone());
		emailText.setText("Email: " + contractor.getEmail());
		billText.setText("Monthly Payment: : " + contractor.getBill());
		balanceText.setText("Balance: " + contractor.getInvoices().get(contractor.getInvoices().size() - 1).getBalance());
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
