package com.graham.apartmate.ui.libraries;

import java.io.IOException;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.misc.FXMLController;
import com.graham.apartmate.ui.windows.popupwindows.invoice.AccountController;
import com.graham.apartmate.ui.windows.popupwindows.notes.NoteListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
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
	//private Stage popupStage2;

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
	public FXMLLoader mainWindow(FXMLLocation location) {
		try {
			root = FXMLLoader.load(getClass().getResource(location.getLocation()));;

			mainStage.setScene(new Scene(root));

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid file name passed... or sumtin' else I dunno...");
		}

		return null;
	}

	/**
	 * Create additional window to create and add a new Table Object based on table type
	 * Uses enumerated types from the
	 * FXMLLocation class to specify specific location
	 */
	public void additionWindow(DBTables tableType) {
		try {
			FXMLLocation location;

			switch (tableType) {
				case APARTMENTS:
					location = FXMLLocation.APTADD;
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
	 * Create additional window to add a new Table object related to one passed to it.
	 * Uses enumerated types from
	 * DBTables to specify specific location
	 */
	public void additionWindow(Table toAdd) {
		try {
			FXMLLocation location;

			switch (toAdd.getTableType()) {
				case APARTMENTS:
					location = FXMLLocation.APTADD;
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

			FXMLController controller = loader.getController();
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
				case APARTMENTS:
					location = FXMLLocation.APTADD;
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

			FXMLController controller = loader.getController();
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

	/**
	 * Creates a popup window managing a list of invoices from a specific table instance
	 * */
	public void invoiceWindow(Table content, FXMLLocation location) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();

			popupStage = new Stage();

			popupStage.setTitle("Info");
			popupStage.setScene(new Scene(root));
			popupStage.initModality(Modality.APPLICATION_MODAL);

			AccountController controller = loader.getController();
			controller.setCurrTable(content);

			popupStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Whoops x2");
		}
	}

	/**
	 * Creates a popup window managing a list of Issues/Inspections from a specific table instance
	 * */
	public void noteLogWindow(Table content, FXMLLocation location) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(location.getLocation()));
			root = loader.load();

			popupStage = new Stage();

			popupStage.setTitle("Info");
			popupStage.setScene(new Scene(root));
			popupStage.initModality(Modality.APPLICATION_MODAL);

			NoteListController controller = loader.getController();
			controller.setCurrTable(content);

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
