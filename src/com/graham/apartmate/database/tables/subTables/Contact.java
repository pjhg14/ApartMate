package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.ui.res.classes.FXMLLocation;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;

/**
 * Contains the personal information of a Tenant, Candidate, or Contractor and/or their related acquaintances
 * */
public class Contact extends Table {

    //-------------------------------------------------------------
    //Fields///////////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Persons's first name
     * */
    private final SimpleStringProperty firstName;

    /**
     * Persons's last name
     * */
    private final SimpleStringProperty lastName;

    /**
     * Persons's phone number
     * */
    private final SimpleStringProperty phoneNumber;

    /**
     * Persons's email address
     * */
    private final SimpleStringProperty email;

    /**
     * Persons's ID number
     * */
    private final SimpleStringProperty ssn;

    /**
     * Persons's number of children
     * */
    private final SimpleIntegerProperty numChildren;

    // Optional fields
    /**
     * Persons's date of birth
     * */
    private final SimpleObjectProperty<LocalDate> dateOfBirth;

    /**
     * Candidate's annual Income
     * */
    private final SimpleIntegerProperty annualIncome;
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Constructors/////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Default constructor
     * Creates the baseline for a dummy Table
     */
    public Contact() {
        this(0,0,0,0,"","","","");
    }

    /**
     * Creates the baseline for a Table with two foreign keys
     *
     * @param id  Primary Key of the Table
     * @param fk  First foreign key of the Table
     * @param fk2 Second foreign key of the Table
     */
    public Contact(int id, int fk, int fk2, int fk3, String firstName, String lastName, String phoneNumber,
                   String email) {
        this(id,fk,fk2,fk3,firstName,lastName,phoneNumber,email,"",0,LocalDate.MIN, 0);
    }

    public Contact(int id, int fk, int fk2, int fk3, String firstName, String lastName, String phoneNumber,
                   String email, String ssn, int numChildren, LocalDate dateOfBirth, int annualIncome) {
        super(id, fk, fk2, fk3);
        image = new Image("com/graham/apartmate/ui/res/img/TenantImg_small.png");
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.ssn = new SimpleStringProperty(ssn);
        this.numChildren = new SimpleIntegerProperty(numChildren);
        this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
        this.annualIncome = new SimpleIntegerProperty(annualIncome);
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Overrided & Utility Methods//////////////////////////////////
    //-------------------------------------------------------------
    @Override
    public String toString() {
        return getLastName() + " " + getFirstName();
    }

    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "generic" name
     * */
    @Override
    public String getGenericName() {
        return getFullName();
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return DBTables.UNASSIGNED;
    }

    /***/
    @Override
    public String getInfoLocation() {
        return FXMLLocation.CONTACTINFO.getLocation();
    }

    /***/
    @Override
    public String getAddLocation() {
        return FXMLLocation.CONTACTADD.getLocation();
    }

    /***/
    @Override
    public String getEditLocation() {
        return FXMLLocation.CONTACTEDIT.getLocation();
    }

    /**
     * Gives the full name of the Contact
     * @return first name last name
     * */
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    /**
     * Gives the full name of the Contact; last first: first last
     * @return last name, first name
     * */
    public String getProperName() {
        return getLastName() + ", " + getFirstName();
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    //General Getters & Setters////////////////////////////////////////
    //-----------------------------------------------------------------
    /**
     * Getter:
     * <p>
     * Gets the Contact's first name
     * @return first name
     * */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Getter:
     * <p>
     * Gets the first name field property
     * @return first name property
     * */
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Setter:
     * <p>
     * Sets the Contact's First Name
     * @param firstName new first name
     * */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Getter:
     * <p>
     * Gets the Contact's last name
     * @return last name
     * */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Getter:
     * <p>
     * Gets the last name field property
     * @return last name property
     * */
    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Setter:
     * <p>
     * Sets the Contact's last name
     * @param lastName new last name
     * */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Getter:
     * <p>
     * Gets the Contact's phone number
     * @return phone number
     * */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Getter:
     * <p>
     * Gets the phone number field property
     * @return phone number property
     * */
    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Setter:
     * <p>
     * Sets Contact's phone number
     * @param phoneNumber new phone number
     * */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    /**
     * Getter:
     * <p>
     * Gets the Contact's email address
     * @return email address
     * */
    public String getEmail() {
        return email.get();
    }

    /**
     * Getter:
     * <p>
     * Gets the email address field property
     * @return email address property
     * */
    public SimpleStringProperty emailProperty() {
        return email;
    }

    /**
     * Setter:
     * <p>
     * Sets the Contact's email address
     * @param email new email address
     * */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /***/
    public String getSsn() {
        return ssn.get();
    }

    /***/
    public SimpleStringProperty ssnProperty() {
        return ssn;
    }

    /***/
    public void setSsn(String ssn) {
        this.ssn.set(ssn);
    }

    /***/
    public int getNumChildren() {
        return numChildren.get();
    }

    /***/
    public SimpleIntegerProperty numChildrenProperty() {
        return numChildren;
    }

    /***/
    public void setNumChildren(int numChildren) {
        this.numChildren.set(numChildren);
    }

    /***/
    public LocalDate getDateOfBirth() {
        return dateOfBirth.get();
    }

    /***/
    public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }

    /***/
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    /***/
    public int getAnnualIncome() {
        return annualIncome.get();
    }

    /***/
    public SimpleIntegerProperty annualIncomeProperty() {
        return annualIncome;
    }

    /***/
    public void setAnnualIncome(int annualIncome) {
        this.annualIncome.set(annualIncome);
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
}
