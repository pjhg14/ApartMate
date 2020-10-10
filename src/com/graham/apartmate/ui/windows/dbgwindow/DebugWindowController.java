package com.graham.apartmate.ui.windows.dbgwindow;


import com.graham.apartmate.database.utilities.unordered.TestingData;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.libraries.FXMLLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.util.Optional;

public class DebugWindowController {

    @FXML
    private Text exeText;

    @FXML
    public void loadSampleData() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setContentText("Are you sure you want to load sample data?");

        Optional<ButtonType> optional = confirmation.showAndWait();

        if (optional.isPresent() && optional.get() == ButtonType.OK){
            TestingData sampleData = new TestingData();
            sampleData.useTestingData();

            exeText.setVisible(true);
            exeText.setText("Sample Data Loaded");
        }
    }

    @FXML
    public void done() {
        Main.getLibrary().mainWindow(FXMLLocation.MAIN);
    }
}
