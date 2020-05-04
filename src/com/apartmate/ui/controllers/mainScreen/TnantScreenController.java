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

//TODO: Javadoc's for every method
// Add TreeView Functionality
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
	private Text name1;
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
	private Text name2;
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
	private Text name3;
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
	private Text name4;
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
	private Text name5;
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
	private Text name6;
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
	private Text name7;
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
	private Text name8;
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

	private List<Tenant> temp;

	@FXML
	public void initialize() {
		temp = new ArrayList<>();
		hideCells();

		trimList(Database.getInstance().getTenants());

		setTnantInfo(temp);
	}

	@FXML
	public void nextPage() {
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
	public void prevPage() {
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
		setCell(image1,name1,edit1,delete1,cell1,0);
		setCell(image2,name2,edit2,delete2,cell2,1);
		setCell(image3,name3,edit3,delete3,cell3,2);
		setCell(image4,name4,edit4,delete4,cell4,3);
		setCell(image5,name5,edit5,delete5,cell5,4);
		setCell(image6,name6,edit6,delete6,cell6,5);
		setCell(image7,name7,edit7,delete7,cell7,6);
		setCell(image8,name8,edit8,delete8,cell8,7);
	}

	private void setCell(ImageView image, Text name, Button edit, Button delete, VBox cell ,int index) {
		if (!subList.get(index).getFirstName().equals("") && index < 8) {
			// Container-Table join

			//Set information redirect (img box)
			image.setOnMouseClicked(e -> {
				Database.getInstance().setCurrTnant(subList.get(index));
				Main.getLibrary().mainWindow(FXMLLocation.TNANTINFO);
			});

			//Set name text
			name.setText(subList.get(index).getFullName());

			//Set information redirect (Edit button)
			edit.setOnAction(e -> {
				Database.getInstance().setCurrTnant(subList.get(index));
				Main.getLibrary().editWindow(FXMLLocation.TNANTEDIT);
			});
			delete.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(index).getFullName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.isPresent() && option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(index));
					subList.remove(index);
					setTnantInfo(Database.getInstance().getTenants());
				}
			});
			cell.setVisible(true);
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
