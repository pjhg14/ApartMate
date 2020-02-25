package com.apartmate.ui.controllers.info;

import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.subTables.ContInvoice;
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
	private TableView<ContInvoice> invTable;

	@FXML
	private TableColumn<ContInvoice, Number> payments;
	@FXML
	private TableColumn<ContInvoice, Number> balances;
	@FXML
	private TableColumn<ContInvoice, Number> totalDues;
	@FXML
	private TableColumn<ContInvoice, Date> paymentDates;
	@FXML
	private TableColumn<ContInvoice, Date> dueDates;
	@FXML
	private TableColumn<ContInvoice, Date> dateCreated;
	@FXML
	private TableColumn<ContInvoice, Date> dateModified;
	// ---------------------------------------------------------

	@FXML
	private Button editButton;

	@FXML
	private Button editButton2;

	@FXML
	private Button tablePopup;

	private ObservableList<ContInvoice> observIns;

	private Contractor currCont;

	@FXML
	public void initialize() {
		currCont = Database.getInstance().getCurrCont();

		// Set Tenant Text
		nameText.setText("Name: " + currCont.getName());
		addressText.setText("Address: " + Database.getInstance().getCurrApt().getAddress());
		phoneText.setText("Phone: " + currCont.getPhone());
		emailText.setText("Email: " + currCont.getEmail());
		billText.setText("Monthly Payment: : " + currCont.getBill());
		balanceText.setText("Balance: " + currCont.getInvoices().get(currCont.getInvoices().size() - 1).getBalance());

		// Initialize TableView
		observIns = FXCollections.observableArrayList(Database.getInstance().getCurrCont().getInvoices());
		observIns.addListener((ListChangeListener<ContInvoice>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					Database.getInstance().getCurrCont().getInvoices().addAll(c.getAddedSubList());

					c.getAddedSubList().forEach(inv -> {
						Database.getInstance().getEvents().updateTotals(inv);
					});
				}
				if (c.wasRemoved()) {
					Database.getInstance().getCurrCont().getInvoices().removeAll(c.getRemoved());
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
	}

	@FXML
	public void refresh() {
		Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
	}

	@FXML
	public void openTable() {
		Main.getLibrary().popWindow(FXMLLocation.CONTINVOICES);
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
}
