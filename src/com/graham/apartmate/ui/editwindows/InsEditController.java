package com.graham.apartmate.ui.editwindows;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.subTables.Insurance;
import com.graham.apartmate.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InsEditController {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField billTextField;

    @FXML
    public TextField phoneTextField;

    @FXML
    public TextField emailTextField;

    private Insurance currInsurance;

    @FXML
    public void initialize() {
        currInsurance = Database.getInstance().getCurrIns();
        setText();
    }

    @FXML
    public void editInsurance() {
        currInsurance.setName(nameTextField.getText());
        currInsurance.setBill(Double.parseDouble(billTextField.getText()));
        currInsurance.setPhone(phoneTextField.getText());
        currInsurance.setEmail(emailTextField.getText());

        Main.getLibrary().closePopup();
    }

    @FXML
    public void closeWindow() {
        Main.getLibrary().closePopup();
    }

    private void setText() {
        nameTextField.setText(currInsurance.getName());
        billTextField.setText("" + currInsurance.getBill());
        phoneTextField.setText(currInsurance.getPhone());
        emailTextField.setText(currInsurance.getEmail());
    }
}
