package com.graham.apartmate.ui.controllers.mainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.utilities.unordered.TestingData;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.FXMLLocation;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

//TODO: Javadoc's for every method
// Add TreeView Functionality
public class AptScreenController {

	@FXML
	private HBox window;

	// ----------------------------------------------------------------
	// Utility Buttons/////////////////////////////////////////////////
	// ----------------------------------------------------------------
	@FXML
	private Button sampleDataButton;

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
	
	// ----------------------------------------------------------------
	@FXML
	private Text pageText;
	// ----------------------------------------------------------------

	// Placeholder for the total number of pages (for Apartments)
	private int numPages = 1;

	// Current page number
	private int currPage = 1;

	// private int currPageTnant;
	// private int currPageCand;
	// private int currPageCont;

	// SubList of apartments in the database (should NEVER contain more than 8
	// apartments)
	private List<Apartment> subList = new ArrayList<>();
	// private List<Tenant> tenantSublist;
	// private List<Candidate> candidateSublist;
	// private List<Contractor> contractorSublist;

	@FXML
	public void initialize() {
		pageText.setText(String.valueOf(numPages));
		hideCells();
		if (Database.isConnected()) {
			setAptInfo(Database.getInstance().getApartments());
		}
		Database.setConnection(true);
	}

	@FXML
	public void loadSampleData(ActionEvent action) {
		TestingData sampleData = new TestingData();
		sampleData.useTestingData();
		setAptInfo(Database.getInstance().getApartments());
		sampleDataButton.setVisible(false);
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
		pageText.setText(String.valueOf(numPages));
		setAptInfo(Database.getInstance().getApartments());
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
		setAptInfo(Database.getInstance().getApartments());
	}

	@FXML
	public void addNewApartment() {
		Main.getLibrary().additionWindow(FXMLLocation.APTADD);
	}

	@FXML
	public void refresh() {
		if (Main.DEBUG)
			System.out.println(Database.getInstance().getApartments());
		setAptInfo(Database.getInstance().getApartments());
	}

	@FXML
	public void saveDatabase() {
		Database.getInstance().save();
	}

	@FXML
	public void close() {
		Database.getInstance().close();
		Main.getLibrary().close();
	}

	private void splitList(List<Apartment> apartments) {
		numPages = apartments.size() % 8;
		// Using page number,
		//subList = 
		subList.addAll(apartments.subList((currPage - 1) * 8, (currPage * 8) - 1));
	}

	private void setAptInfo(List<Apartment> apartments) {
		// Validate list
		subList.clear();
		// If current List/sublist is less than 8
		if (apartments.size() < 8) {
			// Hide page buttons
			nextButton.setVisible(false);
			prevButton.setVisible(false);

			// Fill out the the rest w/ dummy apartments until the list has 8 elements
			int x = apartments.size();
			subList.addAll(apartments);

			while (subList.size() < 8) {
				subList.add(x, new Apartment());
			}
			// If current List/sublist is greater than 8
		} else if (apartments.size() > 8) {
			// Show page buttons
			nextButton.setVisible(true);
			prevButton.setVisible(true);

			// SplitList, get total pages needed
			splitList(apartments);
		} else {
			// Set subList to the 8 existing apartments
			subList.addAll(apartments);
		}

		// Set Buttons (should be 8 elements in)
		hideCells();

		setCells();
	}

	private void setCells() {
		setCell(image1,address1,edit1,delete1,cell1,0);
		setCell(image2,address2,edit2,delete2,cell2,1);
		setCell(image3,address3,edit3,delete3,cell3,2);
		setCell(image4,address4,edit4,delete4,cell4,3);
		setCell(image5,address5,edit5,delete5,cell5,4);
		setCell(image6,address6,edit6,delete6,cell6,5);
		setCell(image7,address7,edit7,delete7,cell7,6);
		setCell(image8,address8,edit8,delete8,cell8,7);

	}


	private void setCell(ImageView image, Text address, Button edit, Button delete, VBox cell ,int index) {
		if (!subList.get(index).getAddress().equals("") && index < 8) {
			// Container-Table join

			//Set information redirect (img box)
			image.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(index));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});

			//Set address text
			address.setText(subList.get(index).getAddress());

			//Set information redirect (Edit button)
			edit.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(index));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(index).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.isPresent() && option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(index));
					subList.remove(index);
					setAptInfo(Database.getInstance().getApartments());
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
