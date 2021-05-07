package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;

import com.graham.apartmate.ui.res.classes.FXMLLocation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Account object:
 * <p>
 * Contains amount due, payments, and credits of monthly dues owed to Bills and Contractors
 * as well as owed by Tenants
 * <p>
 *
 * Account will be updated in the interim of each month
 * <p>
 * Payments will be added to a list which will be taken from the due(initialized from the dues column of
 *      the holding class) to give the balance.
 * <p>
 * The user can add credits/fines (positive/negative number for credit column) to an account at any time
 *      and will be added to the balance
 * <p>
 * At the end of each month the balance is checked; outstanding balance, whether positive or negative
 *      is added to the credit columns of a new Account instance. The existing one shall be added to a list
 *      called 'invoices' that hold all past transactions related to a Table
 * <p>
 * This class will replace all instances of the old 'Invoice' class
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Back Atcha' (0.8.5.1)
 * */
public class Account extends Table {

    /**
     * Current Balance of the Account
     * */
    private final SimpleDoubleProperty balance;

    /**
     * List of payments made
     * */
    private final ObservableList<TransactionLog> payments;

    /**
     * List of dues owed
     * */
    private final ObservableList<TransactionLog> dues;

    /**
     * List of credits/fines earned/penalized (positive amount/negative amount)
     * */
    private final ObservableList<TransactionLog> credits;

    /**
     * Date statement was invoiced
     * <p>
     * contains null value until statement invoicing
     * */
    private final SimpleObjectProperty<LocalDate> statementDate;

    /**
     * List of Statements from previous Accounts
     * */
    private ObservableList<Account> statements;

    /**
     * Default constructor
     * */
    public Account() {
        this(0,0,0,0,null);
    }

    public Account(TransactionLog initDue) {
        this(0,0,0,0, initDue);
    }

    /**
     * Account constructor w/ only initial due
     * */
    public Account(int id, int fk, int fk2, int fk3, TransactionLog initDue) {
        this(id, fk, fk2, fk3,initDue,null, null);
    }

    /**
     * Account constructor w/ initial due and initial payment
     * */
    public Account(int id, int fk, int fk2, int fk3, TransactionLog initDue, TransactionLog initPayment) {
        this(id, fk, fk2, fk3, initDue, initPayment,null);
    }

    /**
     * Account constructor w/ initial due, initial payment and initial credit
     * @param id id of this Account
     * @param fk Tenant related to this Account
     * @param fk2 Contractor related to this Account
     * @param fk3 Bill related to this Account
     * @param initDue initial dues from this Account
     * @param initPayment initial payment to this Account
     * @param initCredit initial credits to this Account
     * */
    public Account(int id, int fk, int fk2, int fk3, TransactionLog initDue, TransactionLog initPayment, TransactionLog initCredit) {
        super(id, fk, fk2, fk3);
        payments = FXCollections.observableArrayList();
        credits = FXCollections.observableArrayList();
        statements = FXCollections.observableArrayList();
        dues = FXCollections.observableArrayList();

        balance = new SimpleDoubleProperty(0);

        if (initDue != null) {
            dues.add(initDue);
            balance.set(-initDue.getAmount());
        }

        if (initPayment != null) {
            payments.add(initPayment);
            balance.set(balance.get() + initPayment.getAmount());
        }

        if (initCredit != null) {
            credits.add(initCredit);
            balance.set(balance.get() + initCredit.getAmount());
        }

        statementDate = new SimpleObjectProperty<>();
    }

    /**
     * Account Statement constructor
     * */
    private Account(int id, int fk, double balance, ObservableList<TransactionLog> payments,
                    ObservableList<TransactionLog> dues, ObservableList<TransactionLog> credits, LocalDate statementDate) {
        super(id, fk);
        this.balance = new SimpleDoubleProperty(balance);
        this.statementDate = new SimpleObjectProperty<>(statementDate);
        this.payments = payments;
        this.dues = dues;
        this.credits = credits;
    }

    /**
     * Gets the main identifying name of an instance of a Table
     * <p>
     * <i>Unused for this Table</i>
     * @return null
     * */
    @Override
    public String getGenericName() {
        return null;
    }

    /**
     * Gets the type of Table in question
     * <p>
     * @return type:ACCOUNT
     */
    @Override
    public DBTables getTableType() {
        return DBTables.ACCOUNT;
    }

    /***/
    @Override
    public String getInfoLocation() {
        return FXMLLocation.ACTINFO.getLocation();
    }

    /***/
    @Override
    public String getAddLocation() {
        return null;
    }

    /***/
    @Override
    public String getEditLocation() {
        return null;
    }

    /**
     * Adds a transaction to the appropriate list
     * <p>
     * Transaction value must be above/below 0 to add
     *
     * @return true if Transaction added successfully false if not
     * */
    public boolean addTransaction(TransactionLog transaction) {
        if (transaction.getAmount() > 0) {
            addPayment(transaction);
            return true;
        }else if (transaction.getAmount() < 0) {
            addDue(transaction);
            return true;
        }

        //Adding a transaction of 0 is redundant
        return false;
    }

    /**
     * Adds credit(positive amount)/ fine(negative amount) to the credits list
     * */
    public boolean addCreditFine(TransactionLog creditFine) {
        if (creditFine.getAmount() != 0){
            credits.add(creditFine);
            calculateBalance();
            return true;
        }

        //Adding a transaction of 0 is redundant
        return false;
    }

    public boolean removeTransaction(TransactionLog transaction) {
        if (transaction.getAmount() > 0) {
            removePayment(transaction);
            return true;
        }else if (transaction.getAmount() < 0) {
            removeDue(transaction);
            return true;
        }

        //There should be no transactions of 0
        return false;
    }

    public boolean removeCreditFine(TransactionLog creditFine) {
        if (creditFine.getAmount() != 0){
            credits.remove(creditFine);
            calculateBalance();
            return true;
        }

        //There should be no transactions of 0
        return false;
    }

    /**
     * */
    public ObservableList<TransactionLog> getTransactions() {
        //What's wrong here?
        //noinspection unchecked
        return FXCollections.concat(payments, dues, credits)
                .sorted(Comparator.comparing(TransactionLog::getTransactionDate));
    }

    /**
     * Default Statement creation method:
     * <p>
     *     invoices Account into statement list and then clears account for next month
     * </p>
     * Assumes no initial payment made
     * */
    public void createStatement(int newId) {
        int oldId = getId();
        int oldFk = getFk();

        //Add Account to archive
        statements.add(new Account(oldId, oldFk, getBalance(), payments, dues, credits, LocalDate.now()));

        //Clear Account
        setId(newId);
        payments.clear();
        credits.clear();
        if (getBalance() > 0) {
            credits.add(new TransactionLog(oldFk, getBalance(), LocalDate.now(), "Balance Rollover"));
        } else if (getBalance() < 0) {
            dues.add(new TransactionLog(oldFk, getBalance(), LocalDate.now(), "Balance Rollover"));
        }

        //Set balance according to existing transactions
        calculateBalance();
    }

    /**
     * Default Statement creation method:
     * <p>
     *     invoices Account into statement list and then clears account for next month
     * </p>
     * Statement creation w/ initial payment
     * */
    public void createStatement(int newId, TransactionLog initialPayment) {
        //Once Date has been changed to LocalDate; check if a month has passed since Date Created
        int oldId = getId();
        int oldFk = getFk();

        //Add Account to archive
        statements.add(new Account(oldId, oldFk, getBalance(), payments, dues, credits, LocalDate.now()));

        //Clear Account
        setId(newId);
        payments.clear();
        payments.add(initialPayment);
        credits.clear();
        if (getBalance() > 0) {
            credits.add(new TransactionLog(oldFk, getBalance(), LocalDate.now(), "Balance Rollover"));
        } else if (getBalance() < 0) {
            dues.add(new TransactionLog(oldFk, getBalance(), LocalDate.now(), "Balance Rollover"));
        }

        //Set balance according to existing transactions
        calculateBalance();
    }

    /**
     * Adds a payment to the payment list
     * */
    private void addPayment(TransactionLog payment) {
        payments.add(payment);
        calculateBalance();
    }

    /**
     * Adds an amount due to the dues list
     * */
    private void addDue(TransactionLog due) {
        dues.add(due);
        calculateBalance();
    }

    /**
     * Removes a payment from the payment list
     * */
    private void removePayment(TransactionLog payment) {
        payments.remove(payment);
        calculateBalance();
    }

    /**
     * Removes a due from the due list
     * */
    private void removeDue(TransactionLog due) {
        dues.remove(due);
        calculateBalance();
    }

    /**
     * calculates the balance
     * */
    private void calculateBalance() {
        balance.set(getTotalPayments() + getTotalCredits() - getTotalDues());
    }

    /**
     * Gets the total amount of payment made
     * @return total payments
     * */
    private double getTotalPayments() {
        return payments.stream().mapToDouble(TransactionLog::getAmount).sum();
    }

    /**
     * Gets the total amount of credits/fines
     * */
    private double getTotalCredits() {
        return credits.stream().mapToDouble(TransactionLog::getAmount).sum();
    }

    /**
     * Gets the total amount of dues owed
     * */
    private double getTotalDues() {
        return dues.stream().mapToDouble(TransactionLog::getAmount).sum();
    }
    //------------------------------------------------------------
    // General getters and setters////////////////////////////////
    //------------------------------------------------------------
    /**
     * Getter:
     * Gets the Account's current balance
     * @return balance amount
     * */
    public double getBalance() {
        return balance.get();
    }

    /**
     * Getter:
     * Gets the balance property
     * @return balance field property
     * */
    public SimpleDoubleProperty balanceProperty() {
        return balance;
    }

    /**
     * Gets the list of payment made
     * @return unmodifiable payment list
     * */
    public ObservableList<TransactionLog> getPayments() {
        return FXCollections.unmodifiableObservableList(payments);
    }

//    /**
//     * Setter:
//     * Sets the list of payments
//     * @param payments payment list
//     * */
//    public void setPayments(ObservableList<TransactionLog> payments) {
//        this.payments = payments;
//    }

    /**
     * Getter:
     * Gets the list of dues
     * @return unmodifiable due list
     * */
    public ObservableList<TransactionLog> getDues() {
        return FXCollections.unmodifiableObservableList(dues);
    }

//    /**
//     * Setter:
//     * Sets the list of dues
//     * @param dues due list
//     * */
//    public void setDues(ObservableList<TransactionLog> dues) {
//        this.dues = dues;
//    }

    /**
     * Getter:
     * Gets the list of credits
     * @return unmodifiable credit/fine list
     * */
    public ObservableList<TransactionLog> getCredits() {
        return FXCollections.unmodifiableObservableList(credits);
    }

//    /**
//     * Setter:
//     * Sets the list of credits/fines
//     * @param credits credit/fine list
//     * */
//    public void setCredits(ObservableList<TransactionLog> credits) {
//        this.credits = credits;
//    }

    /**
     * Getter:
     * Gets the statement date
     * @return statement invoice date
     * */
    public LocalDate getStatementDate() {
        return statementDate.get();
    }

    /**
     * Getter:
     * Gets the statement date property
     * @return statement date field property
     * */
    public SimpleObjectProperty<LocalDate> statementDateProperty() {
        return statementDate;
    }

    /**
     * Setter:
     * Sets the statement date
     * @param statementDate statement invoice date
     * */
    public void setStatementDate(LocalDate statementDate) {
        this.statementDate.set(statementDate);
    }

    /**
     * Getter:
     * Gets the list of invoiced statements
     * @return unmodifiable statement list
     * */
    public ObservableList<Account> getStatements() {
        return FXCollections.unmodifiableObservableList(statements);
    }

//    /**
//     * Setter:
//     * Sets the list of invoiced statements
//     * @param statements statement list
//     * */
//    public void setStatements(ObservableList<Account> statements) {
//        this.statements = statements;
//    }
    //------------------------------------------------------------
    //------------------------------------------------------------
}