package com.graham.apartmate.ui.windows.mainwindow.subwindow.infocontrollers.account;

import com.graham.apartmate.database.tables.subTables.Account;
import com.graham.apartmate.database.tables.subTables.TransactionLog;
import com.graham.apartmate.ui.windows.utility.SubWindowController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class ActInfoController extends SubWindowController {

    //----------------------------------------------------------
    //FXML Fields
    //----------------------------------------------------------
    @FXML
    private Text holderNameText;

    @FXML
    private Text balanceText;

    @FXML
    private Text termText;

    @FXML
    private Text dueDateText;

    @FXML
    private ListView<TransactionLog> transactionListView;

    @FXML
    private ListView<Account> statementListView;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Fields
    //----------------------------------------------------------
    private Account selectedAccount;
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Initialize
    //----------------------------------------------------------
    @Override
    public void init() {
        selectedAccount = (Account) currentTable;

        holderNameText.setText(""); //Enter name of holding Tenant here

        balanceText.setText(selectedAccount.getBalance() + "");

        termText.setText(""); //Enter term
    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //FXML Methods
    //----------------------------------------------------------
    @FXML
    public void addTransaction() {

    }

    @FXML
    public void editTransaction() {

    }

    @FXML
    public void deleteTransaction() {

    }

    @FXML
    public void forceStatement() {

    }

    @FXML
    public void editStatement() {

    }

    @FXML
    public void deleteStatement() {

    }
    //----------------------------------------------------------
    //----------------------------------------------------------

    //----------------------------------------------------------
    //Other Methods
    //----------------------------------------------------------

    //----------------------------------------------------------
    //----------------------------------------------------------
}
