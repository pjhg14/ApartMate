package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
//TODO: Finish javadocs
public class Account extends Table {

    /***/
    private double balance;

    /***/
    private List<TransactionLog> payments;

    /***/
    private TransactionLog due;

    /***/
    private List<TransactionLog> credits;

    /***/
    private List<Account> invoices;

    /***/
    public Account() {
        this(0,0,new TransactionLog(0,LocalDate.MIN),0,0);
    }

    /***/
    public Account(int id, int fk, double initPayment, LocalDate initPmtDate, double initDue, double initCredit) {
        this(id,fk, new TransactionLog(initPayment,initPmtDate),initDue,initCredit);
    }

    /***/
    public Account(int id, int fk, TransactionLog initPayment, double initDue, double initCredit) {
        super(id, fk);
        payments = new ArrayList<>();
        credits = new ArrayList<>();
        invoices = new ArrayList<>();
        due = new TransactionLog(initDue, LocalDate.now());

        payments.add(initPayment);
        credits.add(new TransactionLog(initCredit,LocalDate.MIN));

        balance = initPayment.getAmount() + initCredit - initDue;
    }

    /**
     * Invoice constructor
     * */
    public Account(int id, int fk, double balance, List<TransactionLog> payments, TransactionLog due,
                   List<TransactionLog> credits) {
        super(id, fk);
        this.balance = balance;
        this.payments = payments;
        this.due = due;
        this.credits = credits;
    }

    /***/
    @Override
    public String getGenericName() {
        return "UNUSED";
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return DBTables.NONE;
    }

    /**
     * Returns the image related to a particular instance of a Table
     *
     * @return Table image
     */
    @Override
    public Image getImage() {
        return new Image("");
    }

    /***/
    private double getTotalPayments() {
        double total = 0;
        for (TransactionLog payment : payments) {
            total += payment.getAmount();
        }
        return total;
    }

    /***/
    private double getTotalCredits() {
        double total = 0;
        for (TransactionLog credit : credits) {
            total += credit.getAmount();
        }
        return total;
    }

    /***/
    public void addPayment(TransactionLog payment) {
        payments.add(payment);
        calculateBalance();
    }

    /***/
    public void addPayment(double payment) {
        payments.add(new TransactionLog(payment,LocalDate.now()));
        calculateBalance();
    }

    /***/
    public void addPayment(double payment, LocalDate paymentDate) {
        payments.add(new TransactionLog(payment,paymentDate));
        calculateBalance();
    }

    /***/
    public void addSubAmountDue(double amount) {
        due.setAmount(due.getAmount() + amount);
        calculateBalance();
    }

    /***/
    public void addCreditFine(TransactionLog creditFine) {
        credits.add(creditFine);
        calculateBalance();
    }

    /***/
    public void addCreditFine(double creditFine) {
        credits.add(new TransactionLog(creditFine,LocalDate.now()));
        calculateBalance();
    }

    /***/
    public void addCreditFine(double creditFine, LocalDate insertDate) {
        credits.add(new TransactionLog(creditFine,insertDate));
        calculateBalance();
    }

    /***/
    private void calculateBalance() {
        balance = getTotalPayments() + getTotalCredits() - due.getAmount();
    }

    /**
     * Default Account invoicing method:
     * Assumes no initial payment made
     * */
    public void invoiceAccount() {
        //Once Date has been changed to LocalDate; check if a month has passed since Date Created
        int oldId = getId();
        int oldFk = getFk();

        //Add Account to archive
        invoices.add(new Account(oldId, oldFk, balance, payments, due, credits));

        //Clear Account
        setId(oldId + 1);
        payments.clear();
        credits.clear();
        if (balance != 0)
            credits.add(new TransactionLog(LocalDate.now(), balance, "Balance Rollover"));
        calculateBalance();
    }

    /**
     * Account invoicing method:
     * Invoices Account w/ initial payment
     * */
    public void invoiceAccount(TransactionLog initialPayment) {
        //Once Date has been changed to LocalDate; check if a month has passed since Date Created
        int oldId = getId();
        int oldFk = getFk();

        //Add Account to archive
        invoices.add(new Account(oldId, oldFk, balance, payments, due, credits));

        //Clear Account
        setId(oldId + 1);
        payments.clear();
        payments.add(initialPayment);
        credits.clear();
        if (balance != 0)
            credits.add(new TransactionLog(LocalDate.now(), balance, "Balance Rollover"));
        calculateBalance();
    }

    /**
     * Account invoicing method:
     * Invoices Account w/ initial payment
     * */
    public void invoiceAccount(double initialPayment, LocalDate initPmtDate) {
        invoiceAccount(new TransactionLog(initialPayment, initPmtDate));
    }

    //------------------------------------------------------------
    // General getters and setters////////////////////////////////
    //------------------------------------------------------------
    /***/
    public double getBalance() {
        return balance;
    }

    /***/
    public List<TransactionLog> getPayments() {
        return payments;
    }

    /***/
    public void setPayments(List<TransactionLog> payments) {
        this.payments = payments;
    }

    /***/
    public TransactionLog getDue() {
        return due;
    }

    /***/
    public void setDue(TransactionLog due) {
        this.due = due;
    }

    /***/
    public List<TransactionLog> getCredits() {
        return credits;
    }

    /***/
    public void setCredits(List<TransactionLog> credits) {
        this.credits = credits;
    }

    /***/
    public List<Account> getInvoices() {
        return invoices;
    }

    /***/
    public void setInvoices(List<Account> invoices) {
        this.invoices = invoices;
    }
    //------------------------------------------------------------
    //------------------------------------------------------------
}
