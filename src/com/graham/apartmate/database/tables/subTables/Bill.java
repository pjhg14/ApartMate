package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.main.Main;
import com.graham.apartmate.database.tables.mainTables.Table;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Bill Class
 * <p>
 * Contains company, bill, and invoice info
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Capstone (0.8)
 */
public class Bill extends Table {

    /**
     * Serialization long
     * */
    private static final long serialVersionID = 1L;

    /**
     * Name of company holding the bill
     * */
    private String companyName;

    /**
     * Company address
     * */
    private String address;

    /**
     * Company phone number
     * */
    private String phone;

    /**
     * Monthly payment
     * */
    private double bill;

    /**
     * List of Invoices
     * */
    private ArrayList<Invoice> invoices;

    /**
     * Bill sorting constant
     * */
    public static final Comparator<Bill> BILL_BY_NAME = Comparator.comparing(Bill::getCompanyName);

    /**
     * Default constructor
     * */
    public Bill() {
        this(0,0,"","","",0);
    }

    /**
     * Full constructor
     * */
    public Bill(int id, int fk, String companyName, String address, String phone, double bill) {
        super(id, fk);
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.bill = bill;

        invoices = new ArrayList<>();
    }

    /**
     * Gives all identifying information for the Bill
     * @return id, company name, & address
     * */
    @Override
    public String toString() {
        return String.format("Bill: %d; %s, %s", super.getId(), companyName, address);
    }

    // *************************************************************
    // General getters and setters
    /**
     * Getter:
     * <p>
     * Gets name of company related to the Bill
     * @return Company's name
     * */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter:
     * <p>
     * Sets name of company related to the Bill
     * @param companyName New name of company holding the Bill
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter:
     * <p>
     * Gets address of company related to the Bill
     * @return Company's address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Setter:
     * <p>
     * Sets address of company related to the Bill
     * @param address New address of company
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter:
     * <p>
     * Gets phone number of company related to the Bill
     * @return Company's phone #
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter:
     * <p>
     * Sets phone number of company related to the Bill
     * @param phone New phone # of company
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter:
     * <p>
     * Gets monthly payment for the Bill
     * @return monthly payment
     * */
    public double getBill() {
        return bill;
    }

    /**
     * Setter:
     * <p>
     * Sets monthly payment for the Bill
     * @param bill New monthly payment
     * */
    public void setBill(double bill) {
        this.bill = bill;
    }

    /**
     * Getter:
     * <p>
     * Gets list of Bill's Invoices
     * @return Invoice list
     * */
    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    /**
     * Setter:
     * <p>
     * Sets list of Bill's Invoices
     * @param invoices New list of Invoices
     * */
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    // *************************************************************
}
