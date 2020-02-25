package com.apartmate.ui.controllers.mainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TnantScreenController {
	// ----------------------------------------------------------------
	// Utility Buttons/////////////////////////////////////////////////
	// ----------------------------------------------------------------
	@FXML
	private Button sampleDataButton;

	@FXML
	private Button backButton;

	@FXML
	private Button fowardButton;

	@FXML
	private Button addButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button prevButton;
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	// ----------------------------------------------------------------
	// Containers//////////////////////////////////////////////////////
	// ----------------------------------------------------------------

	// Container 1
	@FXML
	private VBox cell1;
	@FXML
	private ImageView image1;
	@FXML
	private Text address1;
	@FXML
	private Button edit1;
	@FXML
	private Button delete1;

	// Container 2
	@FXML
	private VBox cell2;
	@FXML
	private ImageView image2;
	@FXML
	private Text address2;
	@FXML
	private Button edit2;
	@FXML
	private Button delete2;

	// Container 3
	@FXML
	private VBox cell3;
	@FXML
	private ImageView image3;
	@FXML
	private Text address3;
	@FXML
	private Button edit3;
	@FXML
	private Button delete3;

	// Container 4
	@FXML
	private VBox cell4;
	@FXML
	private ImageView image4;
	@FXML
	private Text address4;
	@FXML
	private Button edit4;
	@FXML
	private Button delete4;

	// Container 5
	@FXML
	private VBox cell5;
	@FXML
	private ImageView image5;
	@FXML
	private Text address5;
	@FXML
	private Button edit5;
	@FXML
	private Button delete5;

	// Container 6
	@FXML
	private VBox cell6;
	@FXML
	private ImageView image6;
	@FXML
	private Text address6;
	@FXML
	private Button edit6;
	@FXML
	private Button delete6;

	// Container 7
	@FXML
	private VBox cell7;
	@FXML
	private ImageView image7;
	@FXML
	private Text address7;
	@FXML
	private Button edit7;
	@FXML
	private Button delete7;

	// Container 8
	@FXML
	private VBox cell8;
	@FXML
	private ImageView image8;
	@FXML
	private Text address8;
	@FXML
	private Button edit8;
	@FXML
	private Button delete8;
	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	// Placeholder for the total number of pages (for Apartments)
	private int numPages = 1;

	// Current page number
	private int currPage = 1;

	// List of all tenants that belong to the

	// SubList of apartments in the database (should NEVER contain more than 8
	// tenants)
	private List<Tenant> subList;

	private List<Tenant> temp = new ArrayList<>();

	@FXML
	public void initialize() {
		hideCells();

		trimList(Database.getInstance().getTenants());

		setTnantInfo(temp);
	}

	@FXML
	public void nextPage(ActionEvent action) {
		// Go to the next set of 8 apartments in the database
		// Set currPage to next set of 8
		if (currPage < numPages) {
			currPage++;
			// Disable Buttons if needed
			if (currPage == numPages) {
				if (!nextButton.isDisabled())
					nextButton.setDisable(true);
			} else {
				if (nextButton.isDisabled())
					prevButton.setDisable(false);
			}
		}

		// redraw apartments
		setTnantInfo(temp);
	}

	@FXML
	public void prevPage(ActionEvent action) {
		// Go to the next set of 8 apartments in the database
		// Set currPage
		if (currPage > 1) {
			currPage--;
			// Disable Buttons if needed
			if (currPage == 1) {
				if (!prevButton.isDisabled())
					prevButton.setDisable(true);
			} else {
				if (prevButton.isDisabled())
					prevButton.setDisable(false);
			}
		}

		// redraw apartments
		setTnantInfo(temp);
	}

	@FXML
	public void addNewTenant() {
		Main.getLibrary().additionWindow(FXMLLocation.TNANTADD);
	}

	@FXML
	public void backToApartment() {
		Database.getInstance().setCurrTnant(null);
		Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
	}

	@FXML
	public void refresh() {
		setTnantInfo(Database.getInstance().getTenants());
	}

	private void trimList(List<Tenant> tenants) {
		// Add all tenants that have the same fk as currApt's id
		for (Tenant tenant : tenants) {
			if (tenant.getFk() == Database.getInstance().getCurrApt().getId()) {
				temp.add(tenant);
			}
		}
	}

	private void splitList(List<Tenant> tenants) {
		numPages = tenants.size() % 8;
		// Using page number,
		subList = tenants.subList((currPage - 1) * 8, (currPage * 8) - 1);
	}

	private void setTnantInfo(List<Tenant> tenants) {
		// Validate list
		// If current List/sublist is less than 8
		if (tenants.size() < 8) {
			// Hide page buttons
			nextButton.setVisible(false);
			prevButton.setVisible(false);

			// Fill out the the rest w/ dummy apartments until the list has 8 elements
			int x = tenants.size();
			subList = tenants;

			while (subList.size() < 8) {
				subList.add(x, new Tenant());
			}
			// If current List/sublist is greater than 8
		} else if (tenants.size() > 8) {
			// Show page buttons
			nextButton.setVisible(true);
			prevButton.setVisible(true);

			// SplitList, get total pages needed
			splitList(tenants);
		} else {
			// Set subList to the 8 existing apartments
			subList = tenants;
		}

		// Set Buttons (should be 8 elements in)
		hideCells();

		setCells();
	}

	private void setCells() {
		setCell1();
		setCell2();
		setCell3();
		setCell4();
		setCell5();
		setCell6();
		setCell7();
		setCell8();
	}

	private void setCell1() {
		if (!subList.get(0).getFirstName().equals("")) {
			// Container-Table join (container 1)
			cell1.setVisible(true);

			image1.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(0));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address1.setText(subList.get(0).getFirstName() + " " + subList.get(0).getLastName());
			edit1.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(0));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete1.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(0).getFirstName() + " "
						+ subList.get(0).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(0));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell2() {
		if (!subList.get(1).getFirstName().equals("")) {
			// Container-Table join (container 2)
			cell2.setVisible(true);
			image2.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(1));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address2.setText(subList.get(1).getFirstName() + " " + subList.get(1).getLastName());
			edit2.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(1));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete2.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(1).getFirstName() + " "
						+ subList.get(1).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(1));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell3() {
		if (!subList.get(2).getFirstName().equals("")) {
			// Container-Table join (container 3)
			cell3.setVisible(true);
			image3.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(2));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address3.setText(subList.get(2).getFirstName() + " " + subList.get(2).getLastName());
			edit3.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(2));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete3.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(2).getFirstName() + " "
						+ subList.get(2).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(2));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell4() {
		if (!subList.get(3).getFirstName().equals("")) {
			// Container-Table join (container 4)
			cell4.setVisible(true);
			image4.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(3));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address4.setText(subList.get(3).getFirstName() + " " + subList.get(3).getLastName());
			edit4.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(3));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete4.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(3).getFirstName() + " "
						+ subList.get(3).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(3));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell5() {
		if (!subList.get(4).getFirstName().equals("")) {
			// Container-Table join (container 5)
			cell5.setVisible(true);
			image5.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(4));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address5.setText(subList.get(4).getFirstName() + " " + subList.get(4).getLastName());
			edit5.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(4));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete5.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(4).getFirstName() + " "
						+ subList.get(4).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(4));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell6() {
		if (!subList.get(5).getFirstName().equals("")) {
			// Container-Table join (container 6)
			cell6.setVisible(true);
			image6.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(5));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address6.setText(subList.get(5).getFirstName() + " " + subList.get(5).getLastName());
			edit6.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(5));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete6.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(5).getFirstName() + " "
						+ subList.get(5).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(5));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell7() {
		if (!subList.get(6).getFirstName().equals("")) {
			// Container-Table join (container 7)
			cell7.setVisible(true);
			image7.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(6));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address7.setText(subList.get(6).getFirstName() + " " + subList.get(6).getLastName());
			edit7.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(6));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete7.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(6).getFirstName() + " "
						+ subList.get(6).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(6));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void setCell8() {
		if (!subList.get(7).getFirstName().equals("")) {
			// Container-Table join (container 8)
			cell8.setVisible(true);
			image8.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(7));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});
			address8.setText(subList.get(7).getFirstName() + " " + subList.get(7).getLastName());
			edit8.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(7));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete8.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(7).getFirstName() + " "
						+ subList.get(7).getLastName() + "'s Profile?");

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					subList.remove(7);
					Database.getInstance().remove(subList.get(7));
					trimList(Database.getInstance().getTenants());
					setTnantInfo(temp);
				}
			});
		}
	}

	private void hideCells() {
		if (prevButton.isVisible())
			prevButton.setVisible(false);
		if (nextButton.isVisible())
			nextButton.setVisible(false);

		if (cell1.isVisible())
			cell1.setVisible(false);
		if (cell2.isVisible())
			cell2.setVisible(false);
		if (cell3.isVisible())
			cell3.setVisible(false);
		if (cell4.isVisible())
			cell4.setVisible(false);
		if (cell5.isVisible())
			cell5.setVisible(false);
		if (cell6.isVisible())
			cell6.setVisible(false);
		if (cell7.isVisible())
			cell7.setVisible(false);
		if (cell8.isVisible())
			cell8.setVisible(false);
	}
}
