package com.graham.apartmate.database.tables.subTables;

import java.time.LocalDate;

//Technically a table but does not store individual ID's; instead Primary Key is transaction date
//(Storing individual transactions [payments, dues, credits] would very quickly get out of hand if done by number
// [MAX_INT or even MAX_LONG would happen on the Java side eventually])
public class TransactionLog {

    private LocalDate transactionDate;
    private double amount;
    private String note;

    public TransactionLog(double amount, LocalDate transactionDate) {
        this(transactionDate, amount, "");
    }

    public TransactionLog(LocalDate transactionDate, double amount, String note) {
        this.note = note;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }




}
