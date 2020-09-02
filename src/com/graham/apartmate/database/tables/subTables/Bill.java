package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

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

    //-----------------------------------------------------------------
    //Fields///////////////////////////////////////////////////////////
    //-----------------------------------------------------------------
    /**
     * Serialization long
     * */
    private static final long serialVersionID = 1L;

    /**
     * Type of bill (Insurance, Water, Electric, Mortgage)
     * */
    private final SimpleStringProperty type;

    /**
     * Name of company holding the bill
     * */
    private final SimpleStringProperty companyName;

    /**
     * Company address (if any)
     * */
    private final SimpleStringProperty address;

    /**
     * Company phone number
     * */
    private final SimpleStringProperty phone;

    /**
     * Monthly payment
     * */
    private final SimpleDoubleProperty bill;

    /**
     * Bill account
     * */
    private final Account account;
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //Order Constants//////////////////////////////////////////////////
    //-----------------------------------------------------------------
    /**
     * Bill sorting constant
     * */
    public static final Comparator<Bill> BILL_BY_NAME = Comparator.comparing(Bill::getCompanyName);
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //Constructors/////////////////////////////////////////////////////
    //-----------------------------------------------------------------
    /**
     * Default constructor
     * */
    public Bill() {
        this(0,0,"","","","",0, new Account());
    }

    /**
     * Dummy Bill Constructor
     * */
    public Bill(String dummy) {
        this();
        if (dummy.equals(DUMMY_TABLE)) {
            super.setDummy(true);
        }
    }

    /**
     * Full constructor
     * */
    public Bill(int id, int fk, String type, String companyName, String address, String phone, double bill, Account account) {
        super(id, fk);
        this.type = new SimpleStringProperty(type);
        this.companyName = new SimpleStringProperty(companyName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.bill = new SimpleDoubleProperty(bill);

        this.account = account;
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //Overrided & Utility Methods//////////////////////////////////////
    //-----------------------------------------------------------------
    /**
     * Gives all identifying information for the Bill
     * @return id, company name, & address
     * */
    @Override
    public String toString() {
        return String.format("Bill: %d; %s, %s", super.getId(), getCompanyName(), getAddress());
    }

    @Override
    public String getGenericName() {
        return getCompanyName();
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return DBTables.BILLS;
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
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //General getters and setters//////////////////////////////////////
    //-----------------------------------------------------------------

    /**
     * Getter:
     * <p>
     * Gets the type of the Bill
     * @return Bill's type
     * */
    public String getType() {
        return type.get();
    }

    /**
     * Setter:
     * <p>
     * Sets type of the Bill
     * @param type New name of the Bill
     * */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Getter:
     * <p>
     * Gets type field property
     * @return type property
     * */
    public SimpleStringProperty typeProperty() {
        return type;
    }

    /**
     * Getter:
     * <p>
     * Gets name of company related to the Bill
     * @return Company's name
     * */
    public String getCompanyName() {
        return companyName.get();
    }

    /**
     * Setter:
     * <p>
     * Sets name of company related to the Bill
     * @param companyName New name of company holding the Bill
     * */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    /**
     * Getter:
     * <p>
     * Gets company name field property
     * @return company name property
     * */
    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    /**
     * Getter:
     * <p>
     * Gets address of company related to the Bill
     * @return Company's address
     * */
    public String getAddress() {
        return address.get();
    }

    /**
     * Setter:
     * <p>
     * Sets address of company related to the Bill
     * @param address New address of company
     * */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Getter:
     * <p>
     * Gets address field property
     * @return address property
     * */
    public SimpleStringProperty addressProperty() {
        return address;
    }

    /**
     * Getter:
     * <p>
     * Gets phone number of company related to the Bill
     * @return Company's phone #
     * */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Setter:
     * <p>
     * Sets phone number of company related to the Bill
     * @param phone New phone # of company
     * */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * Getter:
     * <p>
     * Gets phone field property
     * @return phone property
     * */
    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    /**
     * Getter:
     * <p>
     * Gets monthly payment for the Bill
     * @return monthly payment
     * */
    public double getBill() {
        return bill.get();
    }

    /**
     * Setter:
     * <p>
     * Sets monthly payment for the Bill
     * @param bill New monthly payment
     * */
    public void setBill(double bill) {
        this.bill.set(bill);
    }

    /**
     * Getter:
     * <p>
     * Gets bill field property
     * @return bill property
     * */
    public SimpleDoubleProperty billProperty() {
        return bill;
    }

    /**
     * Getter:
     * <p>
     * Gets list of Bill's Invoices
     * @return Invoice list
     * */
    public Account getAccount() {
        return account;
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
}
