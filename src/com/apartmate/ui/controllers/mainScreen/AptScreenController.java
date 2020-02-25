package com.apartmate.ui.controllers.mainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.utilities.TestingData;
import com.apartmate.main.Main;
import com.apartmate.ui.windows.FXMLLocation;

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
		apartments.subList((currPage - 1) * 8, (currPage * 8) - 1).forEach(apt -> {
			subList.add(apt);
		});;
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
			apartments.forEach(apt -> {
				subList.add(apt);
			});

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
			apartments.forEach(apt -> {
				subList.add(apt);
			});
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
		if (!subList.get(0).getAddress().equals("")) {
			// Container-Table join (container 1)
			cell1.setVisible(true);

			image1.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(0));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address1.setText(subList.get(0).getAddress());
			edit1.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(0));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete1.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(0).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(0));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell2() {
		if (!subList.get(1).getAddress().equals("")) {
			// Container-Table join (container 2)
			cell2.setVisible(true);
			image2.setOnMouseClicked(e -> {
				Database.currApt = subList.get(1);
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address2.setText(subList.get(1).getAddress());
			edit2.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(1));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete2.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(1).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(1));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell3() {
		if (!subList.get(2).getAddress().equals("")) {
			// Container-Table join (container 3)
			cell3.setVisible(true);
			image3.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(2));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address3.setText(subList.get(2).getAddress());
			edit3.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(2));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete3.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(2).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(2));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell4() {
		if (!subList.get(3).getAddress().equals("")) {
			// Container-Table join (container 4)
			cell4.setVisible(true);
			image4.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(3));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address4.setText(subList.get(3).getAddress());
			edit4.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(3));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete4.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(3).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(3));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell5() {
		if (!subList.get(4).getAddress().equals("")) {
			// Container-Table join (container 5)
			cell5.setVisible(true);
			image5.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(4));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address5.setText(subList.get(4).getAddress());
			edit5.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(4));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete5.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(4).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(4));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell6() {
		if (!subList.get(5).getAddress().equals("")) {
			// Container-Table join (container 6)
			cell6.setVisible(true);
			image6.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(5));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address6.setText(subList.get(5).getAddress());
			edit6.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(5));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete6.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(5).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(5));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell7() {
		if (!subList.get(6).getAddress().equals("")) {
			// Container-Table join (container 7)
			cell7.setVisible(true);
			image7.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(6));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address7.setText(subList.get(6).getAddress());
			edit7.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(6));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete7.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(6).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(6));
					setAptInfo(Database.getInstance().getApartments());
				}
			});
		}
	}

	private void setCell8() {
		if (!subList.get(7).getAddress().equals("")) {
			// Container-Table join (container 8)
			cell8.setVisible(true);
			image8.setOnMouseClicked(e -> {
				Database.getInstance().setCurrApt(subList.get(7));
				Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
			});
			address8.setText(subList.get(7).getAddress());
			edit8.setOnAction(e -> {
				Database.getInstance().setCurrApt(subList.get(7));
				Main.getLibrary().editWindow(FXMLLocation.APTEDIT);
			});
			delete8.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(7).getAddress());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					subList.remove(7);
					Database.getInstance().remove(subList.get(7));
					setAptInfo(Database.getInstance().getApartments());
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
