package com.graham.apartmate.ui.mainwindow;


import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.mainwindow.subwindow.listcontrollers.ContentBoxController;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.FXMLLocation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.function.Consumer;

import static com.graham.apartmate.database.dbMirror.DBTables.*;

public class MainSceneController extends FXMLController {

    //FXML Fields
    @FXML
    private TreeView<String> quickView;

    @FXML
    private Text conText;

    //--------------------------------------------------------
    /**
     * The reference for the center pane of the screen
     * */
    @FXML
    private TabPane subPane;

    /**
     * */
    @FXML
    private Tab overviewTab;

    @FXML
    private Tab aptTab;

    @FXML
    private Pane aptPane;

//    @FXML
//    private Tab tnantTab;
//
//    @FXML
//    private Tab candTab;
//
//    @FXML
//    private Tab contTab;
    //--------------------------------------------------------

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button addEditButton;

    /*
    * This is the Main Scene for ApartMate upon logging in
    *
    * TreeView:
    *
    * Button:
    * backButton:
    *
    *
    * ConText:
    * changes in response to the currently hovered object
    *
    * SubPane:
    * upon loading this scene defaults to the overview Tab
    * The cells representing these Apartments contain the image of said apartment (or default image if none exists),
    *   an Edit and delete button
    * The edit and delete button are self explanatory, upon clicking on the image, the user is taken to the Info of the
    *   selected Apartment
    * The Apartment infoView can lead to the Tenant, Candidate, and Contractor boxListView mimicking the boxListView
    *   (i.e. cells) functionality
    *
    * Here, upon request for a specific Table the subPane will load the boxList of the requested Table, from there they
    *   can request the infoView of a specific instance of that Table wnd successfully return
    * */

    //Sub-window consumers
    private final Consumer<Table> aptDataBus = table -> Main.getLibrary().infoSubWindow(aptTab, table);

    //Button even constants
    private final EventHandler<ActionEvent> addTable = event -> Main.getLibrary().additionWindow(currentTable.getTableType());

    private final EventHandler<ActionEvent> editTable = event -> Main.getLibrary().editWindow(currentTable);

    @FXML
    public void initialize() {
        //Set up TreeView

        //Set up Buttons
        addEditButton.setOnAction(addTable);

        //Initialize Tabs
        Main.getLibrary().overviewSubWindow(overviewTab, FXMLLocation.OVERVIEW);
        Main.getLibrary().contentBoxSubWindow(aptTab, FXMLLocation.APARTMENT);

        //Start at Overview Tab
        subPane.getSelectionModel().selectFirst();

    }

    //--------------------------------------------------------------------
    //Button Methods//////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    @FXML
    private void back() {

    }

    @FXML
    private void forward() {

    }

    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Menu Bar Methods////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    @FXML
    private void saveDatabase() {
        //force save
        Database.getInstance().save();
    }

    @FXML
    private void close() {

    }
    //--------------------------------------------------------------------


    //Hides the button every time the
    private void hideAddButton() {
        addEditButton.setVisible(false);
    }

    private void toAddButton() {
        addEditButton.setText("Add");
        addEditButton.setOnAction(addTable);
    }

    private void toEditButton() {
        addEditButton.setText("Edit");
        addEditButton.setOnAction(editTable);
    }

    private void setUpQuickView() {

    }


}
