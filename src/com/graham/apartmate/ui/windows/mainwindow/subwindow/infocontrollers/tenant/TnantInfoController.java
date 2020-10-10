package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.tenant;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class TnantInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text nameText;

    @FXML
    private Text residenceContext;

    @FXML
    private Text residenceText;

    @FXML
    private Text balanceText;

    @FXML
    private ImageView tnantImg;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Tenant selectedTnant;
    //----------------------------------------------------------
    //----------------------------------------------------------


    @Override
    public void init() {
        selectedTnant = (Tenant) currentTable;

        //Set text
        nameText.setText(selectedTnant.getFullName());

        if (selectedTnant.isEvicted()) {
            residenceContext.setVisible(false);
            residenceText.setText("Evicted");
        } else {
            residenceText.setText(Database.getInstance().getTenantResidency(selectedTnant));
        }
        balanceText.setText(selectedTnant.getBalance() + "");

        //Set imageView
        tnantImg.setImage(selectedTnant.getImage());
    }

    //----------------------------------------------------------
    //FXML Methods
    //----------------------------------------------------------
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

        selectedTnant.setImage(new Image(img.toURI().toString()));
        tnantImg.setImage(new Image(img.toURI().toString()));
    }

    @FXML
    public void viewLease() {
        subWindowSubmit.accept(selectedTnant.getLease(), false);
    }

    @FXML
    public void viewStatements() {
        subWindowSubmit.accept(selectedTnant.getAccount(), false);
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Methods
    //----------------------------------------------------------

    //----------------------------------------------------------
    //----------------------------------------------------------
}
