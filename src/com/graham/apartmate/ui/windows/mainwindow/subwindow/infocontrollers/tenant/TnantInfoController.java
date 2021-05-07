package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.tenant;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.database.tables.subTables.Occupant;
import com.graham.apartmate.database.tables.subTables.TransactionLog;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class TnantInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text nameText;

    @FXML
    private Text emailText;

    @FXML
    private Text residenceContext;

    @FXML
    private Text residenceText;

    @FXML
    private Text balanceText;

    @FXML
    private ImageView tnantImg;

    @FXML
    private FlowPane contentList;

    @FXML
    private ListView<TransactionLog> transactionListView;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Tenant selectedTnant;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Initialize
    //----------------------------------------------------------
    @Override
    public void init() {
        selectedTnant = (Tenant) currentTable;

        //Set text
        nameText.setText(selectedTnant.getFullName());

        emailText.setText(selectedTnant.getEmail());

        if (selectedTnant.isEvicted()) {
            residenceContext.setVisible(false);
            residenceText.setText("Evicted");
        } else {
            residenceText.setText(Database.getInstance().getResidency(selectedTnant));
        }
        balanceText.setText(selectedTnant.getBalance() + "");

        //Set imageView
        tnantImg.setImage(selectedTnant.getImage());

        //Set listView
        transactionListView.setCellFactory(callBack -> new ListCell<TransactionLog>(){

            @Override
            protected void updateItem(TransactionLog item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || item.getAmount() == 0 || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText("$"
                            + item.getAmount()
                            + ((item.getAmount() > 0) ? " received" : " billed")
                            + " on "
                            + item.getTransactionDate());
                    setTextFill((item.getAmount() > 0) ? Color.GREEN : Color.RED);
                }
            }
        });
        transactionListView.setItems(selectedTnant.getAccount().getTransactions());

        listOccupants(selectedTnant.getOccupants());
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

        selectedTnant.setImage(new Image(img.toURI().toString()));
        tnantImg.setImage(new Image(img.toURI().toString()));
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
        selectedTnant.getAccount().removeTransaction(transactionListView.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void viewTenantContact() {
        subWindowSubmit.accept(selectedTnant.getPersonalInfo(), false);
    }

    @FXML
    public void viewLease() {
        subWindowSubmit.accept(selectedTnant.getLease(), false);
    }

    @FXML
    public void viewAccount() {
        subWindowSubmit.accept(selectedTnant.getAccount(), false);
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
        deleteButton.setOnAction(event -> Database.getInstance().remove(occupant,selectedTnant.getTableType()));

        container.getChildren().add(deleteButton);

        return container;
    }
    //----------------------------------------------------------
    //----------------------------------------------------------
}
