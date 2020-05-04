package com.apartmate.ui.windows;

import java.io.IOException;

import com.apartmate.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Responsible for the act of transitioning between windows
 * 
 * @since Can we call this an alpha? (0.1)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
//TODO: Javadoc's for every method
public class WindowLibrary {

	private Parent root;
	private static Stage mainStage;
	private Stage popupStage;

	/**
	 * Creates and shows the login window */
	public void loginWindow(Stage stage, FXMLLocation location) throws IOException {
		root = FXMLLoader.load(getClass().getResource(location.getLocation()));

		mainStage = stage;
		mainStage.setTitle("ApartMate");
		mainStage.setScene(new Scene(root, 500, 500));
		// mainStage.setOnCloseRequest(e -> {
		// Database.getInstance().close();
		// });

		if (Main.DEBUG)
			System.out.println("DEBUG is on");
		mainStage.show();
	}

	/**
	 * Changes window to one of the main views (to be used with apartments, tenants,
	 * and contractors) Uses enumerated types from the FXMLLoader class to specify
	 * window
	 */
	public void mainWindow(FXMLLocation location) {
		try {
			// stage.hide();
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));

			mainStage.setScene(new Scene(root, 1050, 550));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid file name passed... or sumtin' else I dunno...");
		}
	}

	/**
	 * Create additional window to add a new Table object (Apartment, Candidate, Contractor, etc.).
	 * Uses enumerated types from the
	 * FXMLLoader class to specify specific object
	 */
	public void additionWindow(FXMLLocation location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));

			popupStage = new Stage();
			popupStage.setTitle("Add");
			popupStage.setScene(new Scene(root));
			popupStage.setResizable(false);
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Addition whoops...");
		}
	}

	/**
	 * Create additional window to an new apartment. Uses enumerated types from the
	 * FXMLLoader class to specify window
	 */
	public void editWindow(FXMLLocation location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));

			popupStage = new Stage();
			popupStage.setTitle("Edit");
			popupStage.setScene(new Scene(root));
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Edit whoops");
		}
	}

	/**
	 * Creates an overall popup window */
	public void popWindow(FXMLLocation location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));

			popupStage = new Stage();

			popupStage.setTitle("Info");
			popupStage.setScene(new Scene(root));
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Whoops x2");
		}
	}

	public void close() {
		mainStage.close();
	}

	public void closePopup() {
		popupStage.close();
	}
}
