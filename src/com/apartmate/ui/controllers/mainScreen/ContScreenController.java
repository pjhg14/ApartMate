package com.apartmate.ui.controllers.mainScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Contractor;
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

public class ContScreenController {
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

	// Placeholder for the total number of pages
	private int numPages = 1;

	// Current page number
	private int currPage = 1;

	// SubList of apartments in the database (should NEVER contain more than 8
	// candidates)
	private List<Contractor> subList;

	// List of all tenants that belong to the current apartment
	private List<Contractor> temp = new ArrayList<>();

	@FXML
	public void initialize() {
		hideCells();

		trimList(Database.getInstance().getContractors());

		setContInfo(temp);
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
		setContInfo(temp);
	}

	@FXML
	public void prevPage(ActionEvent action) {
		// Go to the next set of 8 candidates in the database
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

		// redraw cells
		setContInfo(temp);
	}

	@FXML
	public void addNewContractor() {
		Main.getLibrary().additionWindow(FXMLLocation.CONTADD);
	}

	@FXML
	public void backToApartment() {
		Database.getInstance().setCurrCand(null);
		Main.getLibrary().mainWindow(FXMLLocation.APTINFO);
	}

	@FXML
	public void refresh() {
		setContInfo(Database.getInstance().getContractors());
	}

	private void trimList(List<Contractor> contractors) {
		// Add all candidates that have the same fk as currApt's id
		for (Contractor contractor : contractors) {
			if (contractor.getFk() == Database.getInstance().getCurrApt().getId()) {
				temp.add(contractor);
			}
		}
	}

	private void splitList(List<Contractor> contractors) {
		numPages = contractors.size() % 8;
		// Using page number,
		subList = contractors.subList((currPage - 1) * 8, (currPage * 8) - 1);
	}

	private void setContInfo(List<Contractor> contractors) {
		// Validate list
		// If current List/sublist is less than 8
		if (contractors.size() < 8) {
			// Hide page buttons
			nextButton.setVisible(false);
			prevButton.setVisible(false);

			// Fill out the the rest w/ dummy apartments until the list has 8 elements
			int x = contractors.size();
			subList = contractors;

			while (subList.size() < 8) {
				subList.add(x, new Contractor());
			}
			// If current List/sublist is greater than 8
		} else if (contractors.size() > 8) {
			// Show page buttons
			nextButton.setVisible(true);
			prevButton.setVisible(true);

			// SplitList, get total pages needed
			splitList(contractors);
		} else {
			// Set subList to the 8 existing apartments
			subList = contractors;
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
		if (!subList.get(0).getName().equals("")) {
			// Container-Table join (container 1)
			cell1.setVisible(true);

			image1.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(0));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address1.setText(subList.get(0).getName());
			edit1.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(0));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete1.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(0).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(0));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell2() {
		if (!subList.get(1).getName().equals("")) {
			// Container-Table join (container 2)
			cell2.setVisible(true);
			image2.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(1));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address2.setText(subList.get(1).getName());
			edit2.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(1));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete2.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(1).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(1));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell3() {
		if (!subList.get(2).getName().equals("")) {
			// Container-Table join (container 3)
			cell3.setVisible(true);
			image3.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(2));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address3.setText(subList.get(2).getName());
			edit3.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(2));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete3.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(2).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(2));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell4() {
		if (!subList.get(3).getName().equals("")) {
			// Container-Table join (container 4)
			cell4.setVisible(true);
			image4.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(3));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address4.setText(subList.get(3).getName());
			edit4.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(3));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete4.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(3).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(3));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell5() {
		if (!subList.get(4).getName().equals("")) {
			// Container-Table join (container 5)
			cell5.setVisible(true);
			image5.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(4));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address5.setText(subList.get(4).getName());
			edit5.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(4));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete5.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(4).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(4));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell6() {
		if (!subList.get(5).getName().equals("")) {
			// Container-Table join (container 6)
			cell6.setVisible(true);
			image6.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(5));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address6.setText(subList.get(5).getName());
			edit6.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(5));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete6.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(5).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(5));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell7() {
		if (!subList.get(6).getName().equals("")) {
			// Container-Table join (container 7)
			cell7.setVisible(true);
			image7.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(6));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address7.setText(subList.get(6).getName());
			edit7.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(6));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete7.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(6).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					Database.getInstance().remove(subList.get(6));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
				}
			});
		}
	}

	private void setCell8() {
		if (!subList.get(7).getName().equals("")) {
			// Container-Table join (container 8)
			cell8.setVisible(true);
			image8.setOnMouseClicked(e -> {
				Database.getInstance().setCurrCont(subList.get(7));
				Main.getLibrary().mainWindow(FXMLLocation.CONTINFO);
			});
			address8.setText(subList.get(7).getName());
			edit8.setOnAction(e -> {
				Database.getInstance().setCurrCont(subList.get(7));
				Main.getLibrary().editWindow(FXMLLocation.CONTEDIT);
			});
			delete8.setOnAction(e -> {
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Are you sure you want to delete " + subList.get(7).getName());

				Optional<ButtonType> option = a.showAndWait();

				if (option.get() == ButtonType.OK) {
					subList.remove(7);
					Database.getInstance().remove(subList.get(7));
					trimList(Database.getInstance().getContractors());
					setContInfo(temp);
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