package com.apartmate.database.tables.subTables;

import com.apartmate.database.tables.mainTables.Table;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Bill Class
 * <p></p>
 * Contains company, bill, and invoice info
 */
//TODO: Javadoc's for every method
public class Bill extends Table {

    /***/
    private static final long serialVersionID = 1L;

    /***/
    private String billName;

    /***/
    private String address;

    /***/
    private String phone;

    /***/
    private double bill;

    /***/
    private ArrayList<Invoice> invoices;

    /***/
    public static final Comparator<Bill> BILL_BY_ADDRESS = Comparator.comparing(Bill::getAddress);

    /***/
    public Bill() {
        this(0,0,"","","",0);
    }

    /***/
    public Bill(int id, int fk, String billName, String address, String phone, double bill) {
        super(id, fk);
        this.billName = billName;
        this.address = address;
        this.phone = phone;
        this.bill = bill;

        invoices = new ArrayList<>();
    }

    /***/
    @Override
    public String toString() {
        return billName + ": " + address;
    }

    // *************************************************************
    // General getters and setters
    /***/
    public String getBillName() {
        return billName;
    }

    /***/
    public void setBillName(String billName) {
        this.billName = billName;
    }

    /***/
    public String getAddress() {
        return address;
    }

    /***/
    public void setAddress(String address) {
        this.address = address;
    }

    /***/
    public String getPhone() {
        return phone;
    }

    /***/
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /***/
    public double getBill() {
        return bill;
    }

    /***/
    public void setBill(double bill) {
        this.bill = bill;
    }

    /***/
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    /***/
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    // *************************************************************
}
