package com.apartmate.main;

import com.apartmate.ui.windows.FXMLLocation;
import com.apartmate.ui.windows.WindowLibrary;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class; Starting point for ApartMate
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Main extends Application {
	/**
	 * DEBUG variable: Should allow debug mode with JVM options
	 * <p>
	 * Changing in Eclipse: >Run::Run Configurations...::Arguments tab::VM Arguments
	 * <p>
	 * Changing through CMD line: java -Ddebug=true -jar ApartMate.jar
	 */
	public final static boolean DEBUG = Boolean.valueOf(System.getProperty("debug", "false"));

	public static final String VERSION = "0.8 Capstone";

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
