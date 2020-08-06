package com.graham.apartmate.ui.mainwindow.subwindow.listcontrollers;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ContentBoxController {

    @FXML
    public void initialize() {
        //Do Stuff
    }

    private VBox contentBox(Pane parent, int index, Table content) {
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
        });
        container.getChildren().add(icon);

        Text name = new Text(content.getGenericName());
        container.getChildren().add(name);

        HBox buttonbar = new HBox();
        //Set up HBox properties
        buttonbar.setSpacing(5);

        Button editButton = new Button();
        //Set up Button1 properties
        buttonbar.getChildren().add(editButton);

        Button deleteButton = new Button();
        //Set up Button2 properties
        buttonbar.getChildren().add(deleteButton);

        container.getChildren().add(buttonbar);

        parent.getChildren().add(container);
        return container;
    }
}
