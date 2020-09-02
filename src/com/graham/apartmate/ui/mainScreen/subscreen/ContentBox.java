package com.graham.apartmate.ui.mainScreen.subscreen;

import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ContentBox {

    public static VBox create(Pane parent, int index, Table content) {
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

        ImageView icon = new ImageView();
        container.getChildren().add(icon);

        Text name = new Text(content.getGenericName());
        container.getChildren().add(name);

        HBox buttonbar = new HBox();
        buttonbar.setSpacing(5);

        Button editButton = new Button();
        buttonbar.getChildren().add(editButton);

        Button deleteButton = new Button();
        buttonbar.getChildren().add(deleteButton);

        container.getChildren().add(buttonbar);

        parent.getChildren().add(container);
        return container;
    }
}
