package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.building;


import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Building;
import com.graham.apartmate.database.tables.subTables.Bill;

import com.graham.apartmate.database.tables.subTables.Apartment;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class BldgInfoController extends SubWindowController {

	//----------------------------------------------------------
	//FXML Fields
	//----------------------------------------------------------

	/***/
	@FXML
	private Text addressText;

	/***/
	@FXML
	private Text addressDetails;

	/***/
	@FXML
	private ImageView bldgImage;

	/***/
	@FXML
	private FlowPane livingSpaceList;

	/***/
	@FXML
	private ListView<Bill> billListView;

	/***/
	@FXML
	private ListView<NoteLog> issueListView;
	//----------------------------------------------------------
	//----------------------------------------------------------

	//----------------------------------------------------------
	//Other Fields
	//----------------------------------------------------------
	/***/
	private Building selectedBldg;
	//----------------------------------------------------------
	//----------------------------------------------------------

	//----------------------------------------------------------
	//Initialize
	//----------------------------------------------------------
	/***/
	@Override
	public void init() {
		selectedBldg = (Building) currentTable;

		for (Apartment apartment : selectedBldg.getApartments()) {
			System.out.println("Apartment: " + apartment.getId() +
					apartment.hasTenant() +
					apartment.getExpectantCandidates().isEmpty());
		}

		addressText.setText(selectedBldg.getAddress());
		addressDetails.setText(
				String.format("%s %s, %s", selectedBldg.getCity(), selectedBldg.getState(), selectedBldg.getZipCode()));

		bldgImage.setImage(selectedBldg.getImage());

		billListView.setCellFactory(callBack -> new ListCell<Bill>(){

			@Override
			protected void updateItem(Bill item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					//TODO: Revise list views to show:
					// bill_type: Held by bill_name, balance balance_amount
					setText(item.getCompanyName() + " $" + item.getAccount().getBalance());
				}
			}
		});

		issueListView.setCellFactory(callBack -> new ListCell<NoteLog>(){

			@Override
			protected void updateItem(NoteLog item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					setText("");
				}
			}
		});


		billListView.setItems(selectedBldg.getBills());
		issueListView.setItems(selectedBldg.getIssues());

		setFlowPaneItems(selectedBldg.getApartments());
	}
	//----------------------------------------------------------
	//----------------------------------------------------------

	//----------------------------------------------------------
	//FXML Methods
	//----------------------------------------------------------
	/***/
	@FXML
	public void editImage() {
		//Open file explorer so user can choose choose new image
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image");

		FileChooser.ExtensionFilter imgFilter =
				new FileChooser.ExtensionFilter("PNG images (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(imgFilter);

		imgFilter =
				new FileChooser.ExtensionFilter("JPEG images (*.jpg)","*.jpg");
		fileChooser.getExtensionFilters().add(imgFilter);

		imgFilter =
				new FileChooser.ExtensionFilter("BMP images (*.bmp)","*.bmp");
		fileChooser.getExtensionFilters().add(imgFilter);

		imgFilter =
				new FileChooser.ExtensionFilter("GIF images (*.gif)","*.gif");
		fileChooser.getExtensionFilters().add(imgFilter);

		File img = fileChooser.showOpenDialog(Main.getLibrary().getMainStage());

		selectedBldg.setImage(new Image(img.toURI().toString()));
		bldgImage.setImage(new Image(img.toURI().toString()));
	}

	/***/
	@FXML
	public void toBillInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				//Do stuff
				System.out.println("Make me actually do stuff plz");
			}
		}
	}

	/***/
	@FXML
	public void toIssueInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				//Do stuff
				System.out.println("Make me actually do stuff plz");
			}
		}
	}

	/***/
	@FXML
	public void addBill() {
		System.out.println("Finish Custom popup window");
	}

	/***/
	@FXML
	public void editBill() {
		System.out.println("Finish Custom popup window");
	}

	/***/
	@FXML
	public void deleteBill() {
		selectedBldg.removeBill(billListView.getSelectionModel().getSelectedItem());
	}

	/***/
	@FXML
	public void addIssue() {
		System.out.println("Finish Custom popup window");
	}

	/***/
	@FXML
	public void editIssue() {
		System.out.println("Finish Custom popup window");
	}

	/***/
	@FXML
	public void deleteIssue() {
		selectedBldg.removeIssue(issueListView.getSelectionModel().getSelectedItem());
	}
	//----------------------------------------------------------
	//----------------------------------------------------------

	//----------------------------------------------------------
	//Other Methods
	//----------------------------------------------------------
	/***/
	public void setFlowPaneItems(ObservableList<Apartment> apartments) {
		for (Apartment apartment : apartments) {
			livingSpaceList.getChildren().add(contentBox(apartment));
		}
	}

	/***/
	private VBox contentBox(Apartment apartment) {
		/*
		* VBox construction:
		* 	ImageView
		* 	livingSpace Name Text
		*
		* 	Inner VBox:
		* 		HBox:
		* 			Tenant Button
		* 			Candidates Button
		* 		Delete Button
		* */

		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.setPrefWidth(170);
		container.setPrefHeight(175);
		container.setPadding(new Insets(5,5,5,5));

		ImageView icon = new ImageView(apartment.getImage());
		//IV properties
		icon.setPreserveRatio(true);
		icon.setSmooth(true);
		icon.setOnMouseClicked(
				event -> subWindowSubmit.accept(apartment, false));
		container.getChildren().add(icon);

		//Name Text
		container.getChildren().add(new Text(apartment.getSectionName()));

		HBox occupantBar = new HBox(5);
		occupantBar.setAlignment(Pos.CENTER);

		Button tenantButton = new Button("Tenant");

		tenantButton.setStyle("-fx-font-size:13");
		tenantButton.setPrefWidth(71);
		tenantButton.setDisable(!apartment.hasTenant());
		tenantButton.setOnAction(
				event -> subWindowSubmit.accept(apartment.getTenant(), false));
		occupantBar.getChildren().add(tenantButton);

		Button candidatesButton = new Button("Candidates");

		candidatesButton.setStyle("-fx-font-size:13");
		candidatesButton.setPrefWidth(82);
		candidatesButton.setDisable(apartment.getExpectantCandidates().isEmpty());
		candidatesButton.setOnAction(
				event -> subWindowSubmit.accept(apartment, true));
		occupantBar.getChildren().add(candidatesButton);

		container.getChildren().add(occupantBar);

		Button deleteButton = new Button("Delete");

		deleteButton.setStyle("-fx-font-size:13");
		deleteButton.setOnAction(event -> Database.getInstance().remove(apartment));

		container.getChildren().add(deleteButton);

		return container;
	}
	//----------------------------------------------------------
	//----------------------------------------------------------
}
