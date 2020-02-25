package com.apartmate.ui.controllers.invoice;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.subTables.ContInvoice;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContInvoiceController {

	@FXML
	private TableView<ContInvoice> insTable;

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

	@FXML
	private TextField paymentTextField;

	@FXML
	private DatePicker paymentDueDatePicker;

	@FXML
	private DatePicker dueDateDatePicker;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	private ObservableList<ContInvoice> observIns;

	@FXML
	public void initialize() {
		// Initialize TableView
		observIns = FXCollections.observableList(Database.getInstance().getCurrCont().getInvoices());
		// Column set-up
		balances.setCellValueFactory(new PropertyValueFactory<>("balance"));
		payments.setCellValueFactory(new PropertyValueFactory<>("payment"));
		totalDues.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
		paymentDates.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
		dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		dateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
		dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));

		//
		observIns.addListener((ListChangeListener<ContInvoice>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					c.getAddedSubList().forEach(inv -> {
						Database.getInstance().getEvents().updateTotals(inv);
					});
				}
				if (c.wasRemoved()) {
					if(Database.getInstance().getCurrCont().getInvoices() != null && 
							Database.getInstance().getCurrCont().getInvoices().size() > 1) {
						Database.getInstance().getEvents().updateTotals(Database.getInstance().getCurrCont().getInvoices()
							.get(Database.getInstance().getCurrCont().getInvoices().size() - 1));
					}
					
					// Database.getInstance().getCurrApt().getInsurances().removeAll(c.getRemoved());
				}
				if (c.wasUpdated()) {

				}
			}
		});

		insTable.setItems(observIns);
	}

	@FXML
	public void addInvoice() {
		ContInvoice temp = new ContInvoice();
		int lastIndex;
		if (Database.getInstance().getCurrCont().getInvoices().size() != 0) {
			lastIndex = Database.getInstance().getCurrCont().getInvoices().size() - 1;
		} else {
			lastIndex = 0;
		}

		try {
			if (lastIndex == 0) {
				temp.setId(0);
			} else {
				temp.setId(Database.getInstance().getCurrCont().getInvoices().get(lastIndex).getId() + 1);
			}
			temp.setFk(Database.getInstance().getCurrCont().getId());
			String buffer = paymentTextField.getText();
			if (buffer == null) {
				buffer = "0";
			}
			temp.setPayment(Double.parseDouble(buffer));
			temp.setPaymentDate(
					Date.from(Instant.from(paymentDueDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));
			temp.setDueDate(Date.from(Instant.from(dueDateDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));

			observIns.add(temp);
			// Database.getInstance().getCurrApt().getInsurances().add(temp);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteInvoice() {
		ObservableList<ContInvoice> selected, all;
		all = insTable.getItems();
		selected = insTable.getSelectionModel().getSelectedItems();

		selected.forEach(all::remove);
	}
}
