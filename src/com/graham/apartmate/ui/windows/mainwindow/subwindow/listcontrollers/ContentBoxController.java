package com.graham.apartmate.ui.windows.mainwindow.subwindow.listcontrollers;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.*;
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
import java.util.function.Consumer;

public class ContentBoxController {

    @FXML
    private FlowPane mainPane;

    private DBTables displayedTables;

    private Consumer<Table> infoWindowSubmit;

    public void init() {
        //Do stuff
        display();
    }
    
    private void display() {
        switch (displayedTables) {
            case APARTMENTS:
                for (Apartment apartment : Database.getInstance().getApartments()) {
                    mainPane.getChildren().add(contentBox(apartment));
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
            default:
                break;
        }
    }

    private VBox contentBox(Table content) {
        /*
         * VBox construction:
         *   ImageView
         *   Generic Name Text
         *
         *   HBox:
         *       Edit Button
         *       Delete Button
         * */

        VBox container = new VBox();
        //Set up VBox properties
        container.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView();
        //Set up ImageView properties
        Image image = content.getImage();
        icon.setImage(image);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setOnMouseClicked(event -> infoWindowSubmit.accept(content));
        container.getChildren().add(icon);

        Text name = new Text(content.getGenericName());
        container.getChildren().add(name);

        HBox buttonbar = new HBox();
        //Set up HBox properties
        buttonbar.setSpacing(5);

        Button editButton = new Button();
        //Set up Button1 properties
        editButton.setOnAction(event -> {
            //open edit window
        });
        buttonbar.getChildren().add(editButton);

        Button deleteButton = new Button();
        deleteButton.setOnAction(event -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);

            confirmation.setContentText("Are you sure you want to delete " + content.getGenericName() + "?");

            Optional<ButtonType> optional = confirmation.showAndWait();

            if (optional.isPresent() && optional.get() == ButtonType.OK){
                Database.getInstance().remove(content);
            }
        });
        //Set up Button2 properties
        buttonbar.getChildren().add(deleteButton);

        container.getChildren().add(buttonbar);

        return container;
    }

    @FXML
    public void addTable () {
        switch (displayedTables) {
            case APARTMENTS:
                System.out.println("This would've let you add an Apartment");
                break;
            case TENANTS:
                System.out.println("This would've let you add a Tenant");
                break;
            case CANDIDATES:
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

    public void setDisplayedTables(DBTables tableType) {
        this.displayedTables = tableType;
    }

    public void setInfoWindowSubmit(Consumer<Table> infoWindowSubmit) {
        this.infoWindowSubmit = infoWindowSubmit;
    }
}
