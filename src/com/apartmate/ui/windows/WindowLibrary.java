package com.apartmate.ui.windows;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Responsible for the act of transitioning between windows
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class WindowLibrary {

	private Parent root;
	private static Stage mainStage;
	private Stage popupStage;

	public WindowLibrary() {

	}

	public void loginWindow(Stage stage, FXMLLocation location) throws IOException {
		root = FXMLLoader.load(getClass().getResource(location.getLocation()));

		WindowLibrary.mainStage = stage;
		WindowLibrary.mainStage.setTitle("ApartMate");
		WindowLibrary.mainStage.setScene(new Scene(root, 500, 500));
		// mainStage.setOnCloseRequest(e -> {
		// Database.getInstance().close();
		// });
		WindowLibrary.mainStage.show();
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
	 * Create additional window to an new apartment Uses enumerated types from the
	 * FXMLLoader class to specify window
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
			System.out.println("Whoops x2");
		}
	}

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
			System.out.println("Whoops x2");
		}
	}

	public void popWindow(FXMLLocation location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));

			popupStage = new Stage();

			popupStage.setTitle("Edit");
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
