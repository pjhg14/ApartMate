package com.graham.apartmate.ui.misc;


import com.graham.apartmate.database.utilities.unordered.TestingData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DebugWindowController {

    @FXML
    public void loadSampleData(ActionEvent action) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setContentText("Are you sure you want to load sample data?\n" +
                "All currently held data will be lost!");

        Optional<ButtonType> optional = confirmation.showAndWait();

        if (optional.isPresent() && optional.get() == ButtonType.OK){
            TestingData sampleData = new TestingData();
            sampleData.useTestingData();
        }
    }
}
