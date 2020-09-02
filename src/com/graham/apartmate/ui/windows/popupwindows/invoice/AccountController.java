package com.graham.apartmate.ui.windows.popupwindows.invoice;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.database.tables.subTables.Bill;
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

public class AccountController {

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

    private Alert genericAlert;

    @FXML
    public void initialize() {
        //Set up alert box
        genericAlert = new Alert(Alert.AlertType.ERROR);

        // Initialize TableView using current table type
        switch (currentTable.getTableType()) {
            case TENANTS:

                break;
            case CONTRACTORS:

                break;
            case BILLS:

                break;
            default:
                genericAlert.setContentText("Unable to retrieve invoices for unknown Object");
                genericAlert.show();
                if (Main.DEBUG)
                    System.out.println("THIS SHOULD NOT HAPPEN!!!");
                return;
        }

    }


    public void setCurrTable(Table content) {
        currentTable = content;
    }
}

