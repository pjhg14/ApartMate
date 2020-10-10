package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * Contains the personal information of a Tenant, Candidate, or Contractor
 * */
public class PersonalContact extends Table {

    //-------------------------------------------------------------
    //Fields///////////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * The Contact's first name
     * */
    private final SimpleStringProperty firstName;

    /**
     * The Contact's last name
     * */
    private final SimpleStringProperty lastName;

    /**
     * The Contact's phone number
     * */
    private final SimpleStringProperty phoneNumber;

    /**
     * The Contact's email address
     * */
    private final SimpleStringProperty email;
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Constructors/////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Default constructor
     * Creates the baseline for a dummy Table
     */
    public PersonalContact() {
        this(0,0,0,0,"","","","");
    }

    /**
     * Default constructor
     * Creates the baseline for a dummy Table
     */
    public PersonalContact(String dummy) {
        this();
        if (dummy.equals(DUMMY_TABLE)) {
            super.setDummy(true);
        }
    }

    /**
     * Creates the baseline for a Table with two foreign keys
     *
     * @param id  Primary Key of the Table
     * @param fk  First foreign key of the Table
     * @param fk2 Second foreign key of the Table
     */
    public PersonalContact(int id, int fk, int fk2, int fk3, String firstName, String lastName, String phoneNumber,
                           String email) {
        super(id, fk, fk2, fk3);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email =  new SimpleStringProperty(email);

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
        return DBTables.INVALID;
    }

    /***/
    @Override
    public String getInfoLocation() {
        return null;
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
     * Returns the image related to a particular instance of a Table
     *
     * @return Table image
     */
    @Override
    public Image getImage() {
        return null;
    }

    /**
     * Gives the full name of the Contact
     * @return first name last name
     * */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Gives the full name of the Contact; last first: first last
     * @return last name, first name
     * */
    public String getProperName() {
        return lastName + ", " + firstName;
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
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
}
