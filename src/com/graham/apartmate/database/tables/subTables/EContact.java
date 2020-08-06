package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Emergency Contact object
 * <p>
 * Records the information of an Emergency Contact for a specific Tenant or candidate
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class EContact extends Table {

    /**
     * First name of Contact
     * */
    private String firstName;

    /**
     * Last name of Contact
     * */
    private String lastName;

    /**
     * Contact's address
     * */
    private String address;

    /**
     * Contact's phone number
     * */
    private String phone;

    /**
     * Contact's email address
     * */
    private String email;

    /**
     * Default constructor
     * */
    public EContact() {
        this(0,0,"","","","","");
    }

    /**
     * Dummy constructor
     * */
    public EContact(String dummy) {
        this();
        if (dummy.equals(DUMMY_TABLE)) {
            super.setDummy(true);
        }
    }

    /**
     * Full constructor
     * */
    public EContact(int id, int fk, String firstName, String lastName, String address, String phone, String email) {
        super(id, fk);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

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

    //------------------------------------------------------------
    // General getters and setters////////////////////////////////
    //------------------------------------------------------------
    /**
     * Getter:
     * <p>
     * Gets first name of Contact
     * @return first name
     * */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter:
     * <p>
     * Sets Contact's first name
     * @param firstName New first name
     * */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter:
     * <p>
     * Gets last name of Contact
     * @return last name
     * */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter:
     * <p>
     * Sets last name of Contact
     * @param lastName New last name
     * */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter:
     * <p>
     * Gets Contact's address
     * @return address
     * */
    public String getAddress() {
        return address;
    }

    /**
     * Setter:
     * <p>
     * Sets Contact's address
     * @param address New address
     * */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter:
     * <p>
     * Gets Contact's phone #
     * @return phone #
     * */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter:
     * <p>
     * Sets Contact's phone #
     * @param phone New phone #
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter:
     * <p>
     * Gets Contact's Email
     * @return email
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Setter:
     * <p>
     * Sets Contact's email
     * @param email New email
     * */
    public void setEmail(String email) {
        this.email = email;
    }
    //------------------------------------------------------------
    //------------------------------------------------------------
}
