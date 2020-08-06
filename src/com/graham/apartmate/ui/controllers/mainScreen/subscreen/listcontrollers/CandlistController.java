package com.graham.apartmate.ui.controllers.mainScreen.subscreen.listcontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.ui.controllers.mainScreen.subscreen.ContentBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class CandlistController {

    @FXML
    private FlowPane mainPane;

    @FXML
    public void initialize() {
        for (Candidate candidate : Database.getInstance().getCandidates()) {
            int index = Database.getInstance().getCandidates().indexOf(candidate);
            ContentBox.create(mainPane, index, candidate);
        }
    }
}
