package com.graham.apartmate.ui.windows.mainwindow.subwindow.listcontrollers;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.tables.subTables.Apartment;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Optional;

public class ContentBoxController extends SubWindowController {

    @FXML
    private FlowPane mainPane;

    public void init() {
        //Do stuff
        display();
    }
    
    private void display() {
        switch (currentTable.getTableType()) {
            case BUILDINGS:
                for (Building building : Database.getInstance().getBuildings()) {
                    mainPane.getChildren().add(contentBox(building));
                }
                break;
            case TENANTS:
                for (Tenant tenant : Database.getInstance().getTenants()) {
                    mainPane.getChildren().add(contentBox(tenant));
                }
                break;
            case CANDIDATES:
                for (Candidate candidate : Database.getInstance().getCandidates()) {
                    mainPane.getChildren().add(contentBox(candidate));
                }
                break;
            case CONTRACTORS:
                for (Contractor contractor : Database.getInstance().getContractors()) {
                    mainPane.getChildren().add(contentBox(contractor));
                }
                break;
            case LIVING_SPACE:
                for (Candidate candidate : ((Apartment) currentTable).getExpectantCandidates()) {
                    mainPane.getChildren().add(contentBox(candidate));
                }
                break;
            default:
                break;
        }
    }

    private VBox contentBox(Table content) {

        VBox container = new VBox();
        //Set up VBox properties
        container.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView();
        //Set up ImageView properties
        Image image = content.getImage();
        icon.setImage(image);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setOnMouseClicked(event -> subWindowSubmit.accept(content, false));
        container.getChildren().add(icon);

        Text name = new Text(content.getGenericName());
        container.getChildren().add(name);

        HBox buttonbar = new HBox();

        //Set up HBox properties
        buttonbar.setSpacing(5);
        buttonbar.setAlignment(Pos.CENTER);

        Button editButton = new Button("Edit");

        //Set up Button1 properties
        editButton.setOnAction(event -> {
            System.out.println("Enter edit window here");
        });
        buttonbar.getChildren().add(editButton);

        Button deleteButton = new Button("Delete");

        //Set up Button2 properties
        deleteButton.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

            confirmation.setContentText("Are you sure you want to delete " + content.getGenericName() + "?");

            Optional<ButtonType> optional = confirmation.showAndWait();

            if (optional.isPresent() && optional.get() == ButtonType.OK){
                Database.getInstance().remove(content);
            }
        });
        buttonbar.getChildren().add(deleteButton);

        container.getChildren().add(buttonbar);

        return container;
    }

    @FXML
    public void addTable () {
        switch (currentTable.getTableType()) {
            case BUILDINGS:
                System.out.println("This would've let you add an Apartment");
                break;
            case TENANTS:
                System.out.println("This would've let you add a Tenant");
                break;
            case CANDIDATES:
            case LIVING_SPACE:
                System.out.println("This would've let you add a Candidate");
                break;
            case CONTRACTORS:
                System.out.println("This would've let you add a Contractor");
                break;
            default:
                break;
        }

        System.out.println("Add Table Button does not work yet! sorry!");
    }
}
