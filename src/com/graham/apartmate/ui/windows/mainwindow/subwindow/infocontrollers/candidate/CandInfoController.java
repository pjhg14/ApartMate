package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.candidate;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.subTables.Occupant;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class CandInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text nameText;

    @FXML
    private Text emailText;

    @FXML
    private Text residenceText;

    @FXML
    private Text movInPrompt;

    @FXML
    private Text movInDateText;

    @FXML
    private ImageView candImg;

    @FXML
    private FlowPane contentList;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Candidate selectedCand;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Initialize
    //----------------------------------------------------------
    @Override
    public void init() {
        selectedCand = (Candidate) currentTable;

        nameText.setText(selectedCand.getFullName());
        emailText.setText(selectedCand.getEmail());
        residenceText.setText(Database.getInstance().getResidency(selectedCand));

        if (selectedCand.isAccepted() && selectedCand.getMovInDate() != null) {
            movInDateText.setText(selectedCand.getMovInDate().toString());
        } else {
            movInPrompt.setVisible(false);
            movInDateText.setVisible(false);
        }

        listOccupants(selectedCand.getOccupants());
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //FXML Methods
    //----------------------------------------------------------
    @FXML
    public void viewCandidateContact() {
        subWindowSubmit.accept(selectedCand.getPersonalInfo(), false);
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

        selectedCand.setImage(new Image(img.toURI().toString()));
        candImg.setImage(new Image(img.toURI().toString()));
    }

    @FXML
    public void addOccupant() {
        //TODO: Occupant addition stub
        System.out.println("No outer window yet sorry");
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Methods
    //----------------------------------------------------------
    private void listOccupants(List<Occupant> occupants) {
        for (Occupant occupant : occupants) {
            contentList.getChildren().add(contentBox(occupant));
        }
    }

    private VBox contentBox(Occupant occupant) {
        VBox container = new VBox();

        ImageView icon = new ImageView(occupant.getImage());
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setOnMouseClicked(event -> subWindowSubmit.accept(occupant.getPersonalInfo(), false));

        container.getChildren().add(icon);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> Database.getInstance().remove(occupant,selectedCand.getTableType()));

        container.getChildren().add(deleteButton);

        return container;
    }
    //----------------------------------------------------------
    //----------------------------------------------------------
}
