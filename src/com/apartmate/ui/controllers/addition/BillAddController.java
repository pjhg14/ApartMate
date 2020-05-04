package com.apartmate.ui.controllers.addition;

import com.apartmate.database.dbMirror.DBTables;
import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.subTables.Bill;
import com.apartmate.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BillAddController {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField addressTextField;

    @FXML
    public TextField billTextField;

    @FXML
    public TextField phoneTextField;

    //Not sure if needed
    @FXML
    public void initialize() {

    }

    @FXML
    public void submitNewBill() {
        int lastId = Database.getInstance().getLastID(DBTables.BILLS);

        if (lastId <= 0)
            lastId = 0;

        Bill newBill = new Bill();

        newBill.setId(lastId + 1);
        newBill.setFk(Database.getInstance().getCurrApt().getId());
        newBill.setBillName(nameTextField.getText());
        newBill.setAddress(addressTextField.getText());
        newBill.setBill(Double.parseDouble(billTextField.getText()));
        newBill.setPhone(phoneTextField.getText());


        Database.getInstance().add(newBill);

        Main.getLibrary().closePopup();
    }

    @FXML
    public void closeWindow() {
        Main.getLibrary().closePopup();
    }
}
