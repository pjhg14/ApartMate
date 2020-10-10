package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.apartment;


import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.subTables.Bill;

import com.graham.apartmate.database.tables.subTables.LivingSpace;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class AptInfoController extends SubWindowController {

	//----------------------------------------------------------
	//Fields
	//----------------------------------------------------------

	@FXML
	private Text addressText;

	@FXML
	private Text addressDetails;

	@FXML
	private ImageView aptImage;

	@FXML
	private FlowPane livingSpaceList;

	@FXML
	private ListView<Bill> billListView;

	@FXML
	private ListView<NoteLog> issueListView;
	//----------------------------------------------------------
	//----------------------------------------------------------

	private Apartment selectedApt;

	@Override
	public void init() {
		selectedApt = (Apartment) currentTable;

		addressText.setText(selectedApt.getAddress());
		addressDetails.setText(
				String.format("%s %s, %s", selectedApt.getCity(), selectedApt.getState(),selectedApt.getZipCode()));

		aptImage.setImage(selectedApt.getImage());

		billListView.setItems(selectedApt.getBills());
		issueListView.setItems(selectedApt.getIssues());

		setFlowPaneItems(selectedApt.getLivingSpaces());
	}

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

		selectedApt.setImage(new Image(img.toURI().toString()));
		aptImage.setImage(new Image(img.toURI().toString()));
	}

	public void setFlowPaneItems(ObservableList<LivingSpace> livingSpaces) {
		for (LivingSpace livingSpace : livingSpaces) {
			livingSpaceList.getChildren().add(livingSpaceBox(livingSpace));
		}
	}

	private VBox livingSpaceBox(LivingSpace livingSpace) {
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

		ImageView icon = new ImageView();
		//IV properties
		icon.setImage(livingSpace.getImage());
		icon.setPreserveRatio(true);
		icon.setSmooth(true);
		icon.setOnMouseClicked(
				event -> Main.getLibrary().getMainScene().createInfoWindow(livingSpace));
		container.getChildren().add(icon);

		//Name Text
		container.getChildren().add(new Text(livingSpace.getSectionName()));

		HBox occupantBar = new HBox();
		occupantBar.setSpacing(5);
		occupantBar.setAlignment(Pos.CENTER);

		Button tenantButton = new Button("Tenant");
		tenantButton.setOnAction(
				event -> subWindowSubmit.accept(livingSpace.getOccupant(), false));
		occupantBar.getChildren().add(tenantButton);

		Button candidatesButton = new Button("Candidates");
		candidatesButton.setOnAction(
				event -> subWindowSubmit.accept(livingSpace, true));
		occupantBar.getChildren().add(candidatesButton);

		container.getChildren().add(occupantBar);

		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> Database.getInstance().remove(livingSpace));

		container.getChildren().add(deleteButton);

		return container;
	}

	@FXML
	public void toBillInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				//Do stuff
				System.out.println("Make me actually do stuff plz");
			}
		}
	}

	@FXML
	public void toIssueInfo(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				//Do stuff
				System.out.println("Make me actually do stuff plz");
			}
		}
	}

	@FXML
	public void addBill() {
		System.out.println("Finish Custom popup window");
	}

	@FXML
	public void editBill() {
		System.out.println("Finish Custom popup window");
	}

	@FXML
	public void deleteBill() {
		selectedApt.removeBill(billListView.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void addIssue() {
		System.out.println("Finish Custom popup window");
	}

	@FXML
	public void editIssue() {
		System.out.println("Finish Custom popup window");
	}

	@FXML
	public void deleteIssue() {
		selectedApt.removeIssue(issueListView.getSelectionModel().getSelectedItem());
	}

}
