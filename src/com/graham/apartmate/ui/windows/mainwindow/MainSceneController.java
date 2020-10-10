package com.graham.apartmate.ui.windows.mainwindow;


import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BiConsumer;

import static com.graham.apartmate.ui.libraries.FXMLLocation.*;

public class MainSceneController {

    //--------------------------------------------------------
    //FXML Fields
    //--------------------------------------------------------
    @FXML
    private TreeView<String> quickView;

    @FXML
    private Text conText;

    /**
     * The reference for the center pane of the screen
     * */
    @FXML
    private TabPane subPane;

    /***/
    @FXML
    private Tab overviewTab;

    /***/
    @FXML
    private Tab aptTab;

    /***/
    @FXML
    private Tab tnantTab;

    /***/
    @FXML
    private Tab candTab;

    /***/
    @FXML
    private Tab contTab;

    /***/
    @FXML
    private Button backButton;

    /***/
    @FXML
    private Button nextButton;
    //--------------------------------------------------------
    //--------------------------------------------------------

    //--------------------------------------------------------
    //Utility Fields
    //--------------------------------------------------------

    /*
    * When the sub-window changes, push the last */
    /**
     * Utility field for the back button
     * */
    private List<Deque<FXMLLoader>> subWindowHistory;

    /**
     * Utility field for the next button
     * */
    private List<Deque<FXMLLoader>> reverseHistory;
    //--------------------------------------------------------
    //--------------------------------------------------------

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
     * Sub-window's transition Consumer
     * */
    private final BiConsumer<Table,Boolean> subWindowSubmit = (table, isList) -> {
        if (isList) {
            createListWindow(table);
        } else {
            createInfoWindow(table);
        }
    };

    /***/
    @FXML
    protected void initialize() {
        //Set up TreeView

        //Set up Buttons

        //Initialize utilities
        subWindowHistory = new ArrayList<>();
        reverseHistory = new ArrayList<>();

        //For each tab, add a deque to act as that tab's history
        for (Tab ignored : subPane.getTabs()) {
            subWindowHistory.add(new ArrayDeque<>());
            reverseHistory.add(new ArrayDeque<>());
        }

        //Initialize Tabs
        initSubScreen();

        subPane.setOnMouseClicked(event -> {
            System.out.println(subPane.getSelectionModel().getSelectedIndex());
            System.out.println(subPane.getSelectionModel().getSelectedItem());
            System.out.println(subWindowHistory.get(subPane.getSelectionModel().getSelectedIndex()));
        });

        //Start at Overview Tab
        subPane.getSelectionModel().selectFirst();
    }

    //--------------------------------------------------------------------
    //Button Methods//////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    /***/
    @FXML
    private void back() {
        int index = subPane.getSelectionModel().getSelectedIndex();

        if (subWindowHistory.get(index).size() >= 2) {
            /*
             * Get the last Node from the tab's respective history and remove it from that history
             * */
            subWindowHistory.get(index).pop();
            FXMLLoader last = subWindowHistory.get(index).peek();

            /*
             * Set the content of the currently selected tab to the last Node
             * */
            assert last != null;
            subPane.getSelectionModel().getSelectedItem().setContent(last.getRoot());
            reverseHistory.get(index).push(last);
        } else {
            if (Main.DEBUG)
                System.out.println("Back button should be disabled");

            //backButton.setDisable(true);
        }
    }

    /***/
    @FXML
    private void forward() {
        int index = subPane.getSelectionModel().getSelectedIndex();

        if (reverseHistory.get(index).isEmpty()) {
            /**/
            reverseHistory.get(index).pop();
            FXMLLoader next = reverseHistory.get(index).peek();

            /**/
            assert next != null;
            subPane.getSelectionModel().getSelectedItem().setContent(next.getRoot());
            subWindowHistory.get(index).push(next);
        } else {
            if (Main.DEBUG)
                System.out.println("Next button should be disabled");

            //nextButton.setDisable(true);
        }
    }
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Menu Bar Methods////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    /***/
    @FXML
    private void saveDatabase() {
        //force save
        //Database.getInstance().save();
        System.out.println("Saving has been disabled for now, sorry");
    }

    /***/
    @FXML
    private void close() {

    }
    //--------------------------------------------------------------------
    /***/
    private void initSubScreen() {
        try {
            //Set overview tab
            FXMLLoader loader = new FXMLLoader(getClass().getResource(OVERVIEW.getLocation()));
            overviewTab.setContent(loader.load());

            createListWindow(aptTab, new Building());

            createListWindow(tnantTab, new Tenant());

            createListWindow(candTab, new Candidate());

            createListWindow(contTab, new Contractor());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a list window on the sub screen using default values from the database
     * (main tables)
     * */
    private void createListWindow(Tab tab, Table table) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LISTS.getLocation()));
            tab.setContent(loader.load());

            SubWindowController controller = loader.getController();

            controller.setSubWindowSubmit(subWindowSubmit);
            controller.setCurrentTable(table);

            controller.init();

            //There's DEFINITELY a better way of doing this, but I can't think of it at the moment
            //Gets the passed tab from the TabPane and pushes the loader into
            int index = 0;
            for (Tab subPaneTab : subPane.getTabs()) {

                if (subPaneTab.equals(tab)) {
                    subWindowHistory.get(index).push(loader);
                    break;
                }

                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a list window on the sub screen using a sub table list from a parent table
     * */
    private void createListWindow(Table table) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(LISTS.getLocation()));
            subPane.getSelectionModel().getSelectedItem().setContent(loader.load());

            SubWindowController controller = loader.getController();

            controller.setSubWindowSubmit(subWindowSubmit);
            controller.setCurrentTable(table);

            controller.init();

            subWindowHistory.get(subPane.getSelectionModel().getSelectedIndex()).push(loader);
            reverseHistory.get(subPane.getSelectionModel().getSelectedIndex()).clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an info window on the sub screen of a particular table
     * */
    public void createInfoWindow(Table table) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(table.getInfoLocation()));
            subPane.getSelectionModel().getSelectedItem().setContent(loader.load());

            SubWindowController controller = loader.getController();

            controller.setSubWindowSubmit(subWindowSubmit);
            controller.setCurrentTable(table);

            controller.init();

            subWindowHistory.get(subPane.getSelectionModel().getSelectedIndex()).push(loader);
            reverseHistory.get(subPane.getSelectionModel().getSelectedIndex()).clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
