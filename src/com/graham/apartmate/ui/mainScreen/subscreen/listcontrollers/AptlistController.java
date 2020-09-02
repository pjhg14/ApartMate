package com.graham.apartmate.ui.mainScreen.subscreen.listcontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.ui.mainScreen.subscreen.ContentBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class AptlistController {

    @FXML
    private FlowPane mainPane;

    @FXML
    public void initialize() {
        for (Apartment apartment : Database.getInstance().getApartments()) {
            int index = Database.getInstance().getApartments().indexOf(apartment);
            ContentBox.create(mainPane, index, apartment);
        }
    }
}
