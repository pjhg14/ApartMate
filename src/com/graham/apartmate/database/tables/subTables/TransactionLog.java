package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;

import com.graham.apartmate.main.Main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Transaction Object
 * <p>
 * Records information of a transaction amount, date, and related notes
 *
 * @see com.graham.apartmate.database.tables.mainTables.Tenant
 * @see com.graham.apartmate.database.tables.mainTables.Contractor
 * @see com.graham.apartmate.database.tables.subTables.Bill
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since ??? (???)
 * */
public class TransactionLog extends Table {

    /**
     * Date transaction was made
     * */
    private final SimpleObjectProperty<LocalDate> transactionDate;

    /**
     * Transaction's amount
     * */
    private final SimpleDoubleProperty amount;

    /**
     * Type of medium the payment was made on
     * */
    private final SimpleStringProperty paymentType;

    /**
     * Note related to Transaction
     * */
    private final SimpleStringProperty note;

    /**
     * Whether or not the id field has been set
     * */
    private boolean idSet;

    /**
     * Transaction constructor:
     * Full constructor w/o initial note
     * */
    public TransactionLog(int id, int fk, double amount, LocalDate transactionDate) {
        this(id, fk, amount, transactionDate, "","other");
    }

    /**
     * Transaction constructor:
     * Full constructor
     * */
    public TransactionLog(int id, int fk, double amount, LocalDate transactionDate, String note, String paymentType) {
        super(id, fk);
        this.note = new SimpleStringProperty(note);
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionDate = new SimpleObjectProperty<>(transactionDate);
        this.paymentType = new SimpleStringProperty();
        idSet = true;
    }

    /**
     * Transaction constructor:
     * Id assignment postponed w/o initial note
     * */
    public TransactionLog(int fk, double amount, LocalDate transactionDate) {
        this(fk, amount, transactionDate, "","other");
    }

    /**
     * Transaction constructor:
     * Id assignment postponed w/ initial note
     * */
    public TransactionLog(int fk, double amount, LocalDate transactionDate, String note) {
        super();
        this.note = new SimpleStringProperty(note);
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionDate = new SimpleObjectProperty<>(transactionDate);
        this.paymentType = new SimpleStringProperty();
        idSet = false;  //Need to update TransactionLog w/id later
        setDateCreated(LocalDateTime.now());
        setDateModified(LocalDateTime.now());
    }

    /**
     * Transaction constructor:
     * Id assignment postponed w/ initial note
     * */
    public TransactionLog(int fk, double amount, LocalDate transactionDate, String note, String paymentType) {
        super();
        this.note = new SimpleStringProperty(note);
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionDate = new SimpleObjectProperty<>(transactionDate);
        this.paymentType = new SimpleStringProperty(paymentType);
        idSet = false;  //Need to update TransactionLog w/id later
        setDateCreated(LocalDateTime.now());
        setDateModified(LocalDateTime.now());
    }

    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "generic" name
     */
    @Override
    public String getGenericName() {
        return null;
    }

    /**
     * Gets the type of Table in question
     * <p>
     * Unused for this Table
     * @return type:INVALID
     */
    @Override
    public DBTables getTableType() {
        return DBTables.INVALID;
    }

    /**
     * Returns the image related to a particular Transaction be it a Venmo receipt, a photograph of physical money, or
     * a photo of a check
     * @return Transaction image
     */
    @Override
    public Image getImage() {
        return new Image("");
    }

    /**
     * Returns amount paid/owed and the date processed
     * */
    @Override
    public String toString() {
        return "Amount of $" +
                amount +
                (getAmount() >= 0 ? "paid ":" owed ") +
                "on" + transactionDate;
    }
    //------------------------------------------------------------------------
    //General Getters & Setters///////////////////////////////////////////////
    //------------------------------------------------------------------------

    /**
     * Getter:
     * <p>
     * Gets the transaction date
     * @return transaction date
     * */
    public LocalDate getTransactionDate() {
        return transactionDate.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the transaction date
     * @param transactionDate new transaction date
     * */
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate.set(transactionDate);
    }

    /**
     * Getter:
     * <p>
     * Gets the transaction date property
     * @return transaction date property
     * */
    public SimpleObjectProperty<LocalDate> transactionDateProperty() {
        return transactionDate;
    }

    /**
     * Getter:
     * <p>
     * Gets the amount paid/due/credited
     * @return amount
     * */
    public double getAmount() {
        return amount.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the amount paid/due
     * @param amount new amount due/paid/credited
     * */
    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    /**
     * Getter:
     * <p>
     * Gets the amount property
     * @return amount property
     * */
    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    /**
     * Getter:
     * <p>
     * Gets the medium the payment was made on
     * @return payment medium
     * */
    public String getPaymentType() {
        return paymentType.get();
    }

    /**
     * Getter:
     * <p>
     * Gets the payment medium type property
     * @return payment type property
     * */
    public SimpleStringProperty paymentTypeProperty() {
        return paymentType;
    }

    /**
     * Setter:
     * <p>
     * Sets the medium the payment was made on
     * @param paymentType new payment type
     * */
    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }

    /**
     * Getter:
     * <p>
     * Gets transaction note
     * @return note
     * */
    public String getNote() {
        return note.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the transaction note
     * @param note new transaction note
     * */
    public void setNote(String note) {
        this.note.set(note);
    }

    /**
     * Getter:
     * <p>
     * Gets the note field property
     * @return note property
     * */
    public SimpleStringProperty noteProperty() {
        return note;
    }

    /**
     * Getter:
     * <p>
     * Gets whether the id has been set
     * @return true if id has been set, false if not so
     * */
    public boolean isIdSet() {
        return idSet;
    }

    /**
     * Setter:
     * <p>
     * Sets whether the id was been set
     * @param idSet new set boolean
     * */
    public void setIdSet(boolean idSet) {
        this.idSet = idSet;
    }
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
}
