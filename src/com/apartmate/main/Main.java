package com.apartmate.main;

import com.apartmate.ui.windows.FXMLLocation;
import com.apartmate.ui.windows.WindowLibrary;

import javafx.application.Application;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

/**
 * Main class; Starting point for ApartMate
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
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

    //New Version:
    public static final String VERSION = "Back Atcha' (0.8.5)";

    private static WindowLibrary library;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        library = new WindowLibrary();
        library.loginWindow(stage, FXMLLocation.LOGIN);
    }

    public static WindowLibrary getLibrary() {
        return library;
    }
}