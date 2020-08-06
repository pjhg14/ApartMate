package com.graham.apartmate.ui.popupwindows.invoice;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.database.tables.subTables.Invoice;
import com.graham.apartmate.main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class InvoiceController {

    @FXML
    private TableView<Invoice> insTable;

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

    private Table currentTable;

    private ObservableList<Invoice> observIns;

    private Alert genericAlert;

    @FXML
    public void initialize() {
        //Set up alert box
        genericAlert = new Alert(Alert.AlertType.ERROR);

        // Initialize TableView using current table type
        switch (currentTable.getTableType()) {
            case TENANTS:
                observIns = FXCollections.observableList(((Tenant) currentTable).getInvoices());
                break;
            case CONTRACTORS:
                observIns = FXCollections.observableList(((Contractor) currentTable).getInvoices());
                break;
            case BILLS:
                observIns = FXCollections.observableList(((Bill) currentTable).getInvoices());
                break;
            default:
                genericAlert.setContentText("Unable to retrieve invoices for unknown Object");
                genericAlert.show();
                if (Main.DEBUG)
                    System.out.println("THIS SHOULD NOT HAPPEN!!!");
                return;
        }

        setTableColumns();

        addListeners();

        insTable.setItems(observIns);

        setTableColumnDetails();
    }

    @FXML
    public void addInvoice() {
        Invoice newInvoice = new Invoice();
        int lastIndex;
        if (observIns.size() != 0) {
            lastIndex = observIns.size() - 1;
        } else {
            lastIndex = 0;
        }

        //Attempt to add Invoice
        try {
            //If list is empty, set ID to 1
            if (lastIndex == 0) {
                newInvoice.setId(1);
            } else {
                newInvoice.setId(observIns.get(lastIndex).getId() + 1);
            }

            //Set foreign key
            newInvoice.setFk(currentTable.getId());

            //Check payment's text field to see if any text was entered
            String buffer = paymentTextField.getText();
            if (buffer == null) {
                buffer = "0";
            }
            newInvoice.setPayment(Double.parseDouble(buffer));

            //set dues from last invoice (change)


            //Set date from DatePickers
            //Payment Date
            newInvoice.setPaymentDate(
                    Date.from(Instant.from(paymentDueDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));
            //Due Date
            newInvoice.setDueDate(Date.from(Instant.from(dueDateDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));

            //Add new Invoice

            observIns.add(newInvoice);

            if (Main.DEBUG)
                System.out.println("New Invoice added: " + newInvoice);

        } catch (NumberFormatException e) {
            genericAlert.setContentText("Number field cannot have non numeric characters");
            genericAlert.show();

            if (Main.DEBUG)
                System.out.println("Number mismatch error in addInvoice method");
        } catch (Exception e) {
            if (Main.DEBUG)
                e.getMessage();

            e.printStackTrace();
        }
    }

    //Re instate when able to initialize w/ fxml
//	@FXML
//	public void editInvoice() {
//
//	}

    @FXML
    public void deleteInvoice() {
        observIns.remove(insTable.getSelectionModel().getSelectedItem());
    }

    private void setTableColumns() {
        // Column set-up

        //Payment & Dues columns are editable so...
        payments.setCellValueFactory(new PropertyValueFactory<>("payment"));
        payments.setEditable(true);
        payments.setOnEditCommit(event -> {
                    Invoice editedInvoice = event.getTableView().getItems().get(
                            event.getTablePosition().getRow());

                    int invoiceIndex = event.getTablePosition().getRow();

                    editedInvoice.setPayment(event.getNewValue().doubleValue());

                    //Update totals when updated
                    Database.getInstance().getEvents().updateTotals(
                            event.getTableView().getItems(), invoiceIndex);

                    //Update dateModified when updated
                    Database.getInstance().getEvents().upDateModified(
                            event.getTableView().getItems().get(invoiceIndex));
                }
        );

        dues.setCellValueFactory(new PropertyValueFactory<>("dues"));
        dues.setEditable(true);
        dues.setOnEditCommit(event -> {
                    Invoice editedInvoice = event.getTableView().getItems().get(
                            event.getTablePosition().getRow());

                    int invoiceIndex = event.getTablePosition().getRow();

                    editedInvoice.setDues(event.getNewValue().doubleValue());

                    //Update totals when updated
                    Database.getInstance().getEvents().updateTotals(
                            event.getTableView().getItems(), invoiceIndex);

                    //Update dateModified when updated
                    Database.getInstance().getEvents().upDateModified(
                            event.getTableView().getItems().get(invoiceIndex));
                }
        );

        balances.setCellValueFactory(new PropertyValueFactory<>("balance"));
        totalPaid.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));
        totalDues.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
        paymentDates.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        dateModified.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
    }

    private void addListeners() {
        //List addition/removal listener
        observIns.addListener((ListChangeListener<Invoice>) c -> {

            while (c.next()) {
                if (c.wasAdded()) {
                    Database.getInstance().getEvents().updateTotals(observIns);

                    c.getAddedSubList().forEach(invoice ->
                            Database.getInstance().sqlBridge.insert(invoice, Database.getCurrTable()));
                } else if (c.wasRemoved()) {
                    Database.getInstance().getEvents().updateTotals(observIns);

                    c.getRemoved().forEach(invoice ->
                            Database.getInstance().sqlBridge.delete(invoice, Database.getCurrTable()));
                } else if (c.wasUpdated()) {
                    Database.getInstance().getEvents().updateTotals(observIns);

                    c.getList().forEach(invoice ->
                            Database.getInstance().sqlBridge.update(invoice, Database.getCurrTable()));
                }
            }
        });
    }

    private void setTableColumnDetails() {
        insTable.setEditable(true);
    }

    public void setCurrTable(Table content) {
        currentTable = content;
    }
}

