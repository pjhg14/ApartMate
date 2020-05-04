package com.apartmate.ui.controllers.addition;

import com.apartmate.database.dbMirror.DBTables;
import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.subTables.Insurance;
import com.apartmate.main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InsAddController {
    @FXML
    public TextField nameTextField;

    @FXML
    public TextField billTextField;

    @FXML
    public TextField phoneTextField;

    @FXML
    public TextField emailTextField;

    //Not sure if needed
    @FXML
    public void initialize() {

    }

    @FXML
    public void submitNewInsurance() {
        int lastId = Database.getInstance().getLastID(DBTables.INSURANCES);

        if (lastId <= 0)
            lastId = 0;

        Insurance newInsurance = new Insurance();

        newInsurance.setId(lastId + 1);
        newInsurance.setFk(Database.getInstance().getCurrApt().getId());
        newInsurance.setName(nameTextField.getText());
        newInsurance.setBill(Double.parseDouble(billTextField.getText()));
        newInsurance.setPhone(phoneTextField.getText());
        newInsurance.setEmail(emailTextField.getText());

        Database.getInstance().add(newInsurance);

        Main.getLibrary().closePopup();
    }

    @FXML
    public void closeWindow() {
        Main.getLibrary().closePopup();
    }
}
