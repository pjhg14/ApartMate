package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.contractor;

import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.database.tables.subTables.TransactionLog;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;


public class ContInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text nameText;

    //TODO: change to more appropriate name
    @FXML
    private Text residenceText;

    @FXML
    private Text balanceContext;

    @FXML
    private Text balanceText;

    @FXML
    private Text emailText;

    @FXML
    private ImageView contImg;

    @FXML
    private Button contractorContactButton;

    @FXML
    private ListView<TransactionLog> paymentListView;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Contractor selectedCont;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Initialize
    //----------------------------------------------------------
    @Override
    public void init() {
        selectedCont = (Contractor) currentTable;

        nameText.setText(selectedCont.getName());
        residenceText.setText("Dunno what to do yet, sorry");

        balanceText.setText("$" + selectedCont.getAccount().getBalance());
        emailText.setText(selectedCont.getEmail());
        contImg.setImage(selectedCont.getImage());
        contractorContactButton.setText(selectedCont.getName()
                + "'s Contact: \n"
                + selectedCont.getContact().getFullName());

        paymentListView.setCellFactory(callBack -> new ListCell<TransactionLog>() {

            @Override
            protected void updateItem(TransactionLog item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || item.getAmount() == 0 || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText("$"
                            + item.getAmount()
                            + ((item.getAmount() > 0) ? " paid" : " owed")
                            + " on "
                            + item.getTransactionDate());
                    setTextFill((item.getAmount() > 0) ? Color.GREEN : Color.RED);
                }
            }
        });
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

        selectedCont.setImage(new Image(img.toURI().toString()));
        contImg.setImage(new Image(img.toURI().toString()));
    }

    @FXML
    public void addTransaction() {
        //TODO: Create addition windows and link from here
    }

    @FXML
    public void editTransaction() {
        //TODO: Create addition windows and link from here
    }

    @FXML
    public void deleteTransaction() {

    }

    @FXML
    public void viewAccount() {
        subWindowSubmit.accept(selectedCont.getAccount(), false);
    }

    @FXML
    public void viewContract() {
        subWindowSubmit.accept(selectedCont.getContract(), false);
    }

    @FXML
    public void viewContractorContact() {
        subWindowSubmit.accept(selectedCont.getContact(), false);
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Methods
    //----------------------------------------------------------

    //None yet...

    //----------------------------------------------------------
    //----------------------------------------------------------
}
