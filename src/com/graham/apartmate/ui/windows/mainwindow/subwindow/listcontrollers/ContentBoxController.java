package com.graham.apartmate.ui.windows.mainwindow.subwindow.listcontrollers;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
        icon.setOnMouseClicked(event -> {
            //Event Here
            infoWindowSubmit.accept(content);
        });
        container.getChildren().add(icon);

        Text name = new Text(content.getGenericName());
        container.getChildren().add(name);

        HBox buttonbar = new HBox();
        //Set up HBox properties
        buttonbar.setSpacing(5);

        Button editButton = new Button();
        //Set up Button1 properties
        editButton.setOnAction(event -> Database.getInstance().edit(content));
        buttonbar.getChildren().add(editButton);

        Button deleteButton = new Button();
        deleteButton.setOnAction(event -> Database.getInstance().remove(content));
        //Set up Button2 properties
        buttonbar.getChildren().add(deleteButton);

        container.getChildren().add(buttonbar);

        //mainPane.getChildren().add(container);
        return container;
    }

    @FXML
    public void addTable () {
        switch (displayedTables) {
            case APARTMENTS:
                break;
            case TENANTS:
                break;
            case CANDIDATES:
                break;
            case CONTRACTORS:
                break;
            default:
                break;
        }
    }

    public void setDisplayedTables(DBTables tableType) {
        displayedTables = tableType;
    }

    public void setInfoWindowSubmit(Consumer<Table> infoWindowSubmit) {
        this.infoWindowSubmit = infoWindowSubmit;
    }
}
