package com.graham.apartmate.main;

import com.graham.apartmate.ui.libraries.FXMLLocation;
import com.graham.apartmate.ui.libraries.WindowLibrary;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class; Starting point for ApartMate
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Main extends Application {

    /**
     * DEBUG variable: Should allow debug mode with JVM options
     * <p>
     * Changing in Eclipse: >Run::Run Configurations...::Arguments tab::VM Arguments: Type in: -Ddebug=true
     * </p>
     * Changing in IntelliJ: >Run::Edit Configurations...::VM Options: Type in: -Ddebug=true
     * <p>
     * Changing through CMD line: java -Ddebug=true -jar ApartMate.jar
     * </p>
     */
    public final static boolean DEBUG = Boolean.parseBoolean(System.getProperty("debug", "false"));

    //New Version: <Unnamed Version> 0.8.6
    //Current Patch: Back Atcha' 0.8.5.2
    /**
     * Version constant; holds the current version of the program
     * */
    public static final String VERSION = "Back Atcha' (0.8.5.1)";

    /**
     * Manages the display and switching of window scenes
     * */
    private static WindowLibrary library;

    /**
     * Start point of the program
     * */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the JavaFX application
     * */
    @Override
    public void start(Stage stage) throws Exception {
        library = new WindowLibrary();
        library.loginWindow(stage, FXMLLocation.LOGIN);
    }

    /**
     * Getter:
     * <p>
     * Gets the WindowLibrary
     * */
    public static WindowLibrary getLibrary() {
        return library;
    }
}