package com.graham.apartmate.ui.controllers.mainScreen.subscreen.listcontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.ui.controllers.mainScreen.subscreen.ContentBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class ContlistController {

    @FXML
    private FlowPane mainPane;

    @FXML
    public void initialize() {
        for (Contractor contractor : Database.getInstance().getContractors()) {
            int index = Database.getInstance().getContractors().indexOf(contractor);
            ContentBox.create(mainPane, index, contractor);
        }
    }
}
