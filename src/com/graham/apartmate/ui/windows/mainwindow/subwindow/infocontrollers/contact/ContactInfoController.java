package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.contact;

import com.graham.apartmate.database.tables.subTables.Contact;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class ContactInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text nameText;

    @FXML
    private Text phoneText;

    @FXML
    private Text emailText;

    @FXML
    private Text numChildrenText;

    @FXML
    private Text dateOfBirthText;

    @FXML
    private Text annualIncomeText;

    @FXML
    private ImageView contactImg;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Contact selectedContact;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Initialize
    //----------------------------------------------------------
    @Override
    public void init() {
        selectedContact = (Contact) currentTable;

        nameText.setText(selectedContact.getFullName());
        phoneText.setText(selectedContact.getPhoneNumber());
        emailText.setText(selectedContact.getEmail());
        numChildrenText.setText(selectedContact.getNumChildren() + "");
        dateOfBirthText.setText(selectedContact.getDateOfBirth().toString());
        annualIncomeText.setText(selectedContact.getAnnualIncome() + "");
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

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

        selectedContact.setImage(new Image(img.toURI().toString()));
        contactImg.setImage(new Image(img.toURI().toString()));
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Methods
    //----------------------------------------------------------

    //----------------------------------------------------------
    //----------------------------------------------------------
}
