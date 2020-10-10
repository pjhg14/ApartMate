package com.graham.apartmate.ui.windows.popupwindows.editwindows.bill;

import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BillEditController {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField addressTextField;

    @FXML
    public TextField billTextField;

    @FXML
    public TextField phoneTextField;

    private Bill currBill;

    @FXML
    public void initialize() {
        //currBill = Database.getInstance().getCurrBill();
        setText();
    }

    @FXML
    public void editBill() {
        currBill.setCompanyName(nameTextField.getText());
        currBill.setAddress(addressTextField.getText());
        //currBill.setBill(Double.parseDouble(billTextField.getText()));
        currBill.setPhone(phoneTextField.getText());


        Main.getLibrary().closePopup();
    }

    @FXML
    public void closeWindow() {
        Main.getLibrary().closePopup();
    }

    private void setText() {
        nameTextField.setText(currBill.getCompanyName());
        addressTextField.setText(currBill.getAddress());
        //billTextField.setText("" + currBill.getBill());
        phoneTextField.setText(currBill.getPhone());

    }
}
