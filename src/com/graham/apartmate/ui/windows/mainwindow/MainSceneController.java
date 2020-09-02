package com.graham.apartmate.ui.windows.mainwindow;


import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.mainwindow.subwindow.listcontrollers.ContentBoxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.function.Consumer;

import static com.graham.apartmate.ui.libraries.FXMLLocation.*;

public class MainSceneController {

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
    /**
     * Sub-window's info view transition Consumer
     * */
    private final Consumer<Table> tableInfoSubmit = table -> {
        try {
            FXMLLoader loader;
            FXMLController controller;

            switch (table.getTableType()) {
                case APARTMENTS:
                    loader = new FXMLLoader(getClass().getResource(APTINFO.getLocation()));

                    controller = loader.getController();
                    controller.setCurrentTable(table);;

                    controller.init();
                    aptTab.setContent(loader.load());
                    break;
                case TENANTS:
                    loader = new FXMLLoader(getClass().getResource(TNANTINFO.getLocation()));
                    controller = loader.getController();
                    controller.setCurrentTable(table);

                    controller.init();
                    //aptTab.setContent(loader.load());
                    break;
                case CANDIDATES:
                     loader = new FXMLLoader(getClass().getResource(CANDINFO.getLocation()));

                    controller = loader.getController();
                    controller.setCurrentTable(table);

                    controller.init();
                    //aptTab.setContent(loader.load());
                    break;
                case CONTRACTORS:
                    loader = new FXMLLoader(getClass().getResource(CONTINFO.getLocation()));

                    controller = loader.getController();
                    controller.setCurrentTable(table);

                    controller.init();
                    //aptTab.setContent(loader.load());
                    break;
                default:
                    //Ummm...
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    @FXML
    public void initialize() {
        //Set up TreeView

        //Set up Buttons

        //Initialize Tabs
        initSubScreen();

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
        //Database.getInstance().save();
        System.out.println("Saving has been disabled for now, sorry");
    }

    @FXML
    private void close() {

    }
    //--------------------------------------------------------------------
    private void initSubScreen() {
        try {
            //Set overview tab
            FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW.getLocation()));
            overviewTab.setContent(loader.load());

            //set apartment tab
            loader = new FXMLLoader(getClass().getResource(LISTS.getLocation()));
            ContentBoxController controller = loader.getController();
            aptTab.setContent(loader.load());

            //set tenant tab

            //set candidate tab

            //set contractor tab
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpQuickView() {

    }


}