package com.graham.apartmate.ui.libraries;

import java.io.IOException;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import com.graham.apartmate.ui.windows.mainwindow.MainSceneController;
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
	private Stage mainStage;
	private Stage popupStage;

	private MainSceneController mainSceneController;

	/**
	 * Creates and shows the login window
	 * */
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();
			//mainSceneController = loader.getController();

			mainStage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid file name passed... or sumtin' else I dunno...");
		}
	}

	public void debugWindow(FXMLLocation location) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();

			mainStage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid file name passed... or sumtin' else I dunno...");
		}
	}

	public void popupWindow() {
		//Learn how Dialogs work
	}

	/**
	 * Create additional window to add a new Table object related to one passed to it.
	 * Uses enumerated types from
	 * DBTables to specify specific location
	 */
	public void additionWindow(Table toAdd) {
		try {
			FXMLLocation location;

			switch (toAdd.getTableType()) {
				case BUILDINGS:
					location = FXMLLocation.BLDGADD;
					break;
				case TENANTS:
					location = FXMLLocation.TNANTADD;
					break;
				case CANDIDATES:
					location = FXMLLocation.CANDADD;
					break;
				case CONTRACTORS:
					location = FXMLLocation.CONTADD;
					break;
				case BILLS:
					location = FXMLLocation.BILLADD;
					break;
				default:
					if (Main.DEBUG)
						System.out.println("Invalid Table type");
					return;
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();

			SubWindowController controller = loader.getController();
			controller.setCurrentTable(toAdd);

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
	 * Create additional window to edit an existing Table object(Apartment, Candidate, Contractor, etc.) passed to it.
	 * Uses enumerated types from
	 * DBTables to specify specific location
	 */
	public void editWindow(Table toEdit) {
		try {
			FXMLLocation location;

			switch (toEdit.getTableType()) {
				case BUILDINGS:
					location = FXMLLocation.BLDGADD;
					break;
				case TENANTS:
					location = FXMLLocation.TNANTADD;
					break;
				case CANDIDATES:
					location = FXMLLocation.CANDADD;
					break;
				case CONTRACTORS:
					location = FXMLLocation.CONTADD;
					break;
				case BILLS:
					location = FXMLLocation.BILLADD;
					break;
				default:
					if (Main.DEBUG)
						System.out.println("Invalid Table type");
					return;
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();

			SubWindowController controller = loader.getController();
			controller.setCurrentTable(toEdit);

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


	public Stage getMainStage() {
		return mainStage;
	}

	public MainSceneController getMainScene() {
		return mainSceneController;
	}

	public void close() {
		mainStage.close();
	}

	public void closePopup() {
		popupStage.close();
	}
}
