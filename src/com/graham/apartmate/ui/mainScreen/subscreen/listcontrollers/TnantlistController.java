package com.graham.apartmate.ui.mainScreen.subscreen.listcontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.ui.mainScreen.subscreen.ContentBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class TnantlistController {

    @FXML
    private FlowPane mainPane;

    @FXML
    public void initialize() {
        for (Tenant tenant : Database.getInstance().getTenants()) {
            int index = Database.getInstance().getTenants().indexOf(tenant);
            ContentBox.create(mainPane, index, tenant);
        }
    }
}
