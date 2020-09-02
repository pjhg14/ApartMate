package com.graham.apartmate.database.tables.mainTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.Account;
import com.graham.apartmate.database.tables.subTables.PersonalContact;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * Contractor object
 * <p>
 * Records information of contractors that have worked on a respective Apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Contractor extends Table {

	//------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Contractor's name
	 * */
	private final SimpleStringProperty name;

	/**
	 * Monthly payment
	 * */
	private final SimpleDoubleProperty bill;

	/**
	 * Contractor's phone number
	 * */
	private final SimpleStringProperty phone;

	/**
	 * Contractor's email
	 * */
	private final SimpleStringProperty email;

	/**
	 * Personal contact information of contractor
	 * */
	private PersonalContact contact;

	/**
	 * List of Contractor's Invoices
	 * */
	private final Account account;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Constructor/////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Default constructor
	 * */
	public Contractor() {
		this(0,0,"",0,"","");
	}

	/**
	 * Dummy Contractor Constructor
	 * */
	public Contractor(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Full constructor
	 * */
	public Contractor(int id, int fk, String name, double bill, String phone, String email) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/Tenantimg_small.png");
		this.name = new SimpleStringProperty(name);
		this.bill = new SimpleDoubleProperty(bill);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);

		account = new Account();
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Overrided & Utility Methods/////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Contractor Id and name
	 * */
	@Override
	public String toString() {
		return String.format("Contractor: id; %s, name; %s", super.getId() ,name);
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 * @return Table's "generic" name
	 * */
	@Override
	public String getGenericName() {
		return getName();
	}

	/**
	 * Gets the type of Table in question
	 * @return table type
	 * */
	@Override
	public DBTables getTableType() {
		return DBTables.CONTRACTORS;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//General getters and setters/////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Getter:
	 * Gets Contractor's business name
	 * @return name
	 * */
	public String getName() {
		return name.get();
	}

	/**
	 * Setter:
	 * Sets Contractor's business name
	 * @param name New name
	 * */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets name field property
	 * @return name property
	 * */
	public SimpleStringProperty nameProperty() {
		return name;
	}

	/**
	 * Getter:
	 * Gets monthly bill for Contractor
	 * @return bill
	 * */
	public double getBill() {
		return bill.get();
	}

	/**
	 * Setter:
	 * Sets monthly bill for Contractor
	 * @param bill New bill
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
	 * Gets Contractor's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone.get();
	}

	/**
	 * Setter:
	 * Sets Contractor's phone number
	 * @param phone New phone #
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
	 * Gets Contractor's email
	 * @return email
	 * */
	public String getEmail() {
		return email.get();
	}

	/**
	 * Setter:
	 * Sets Contrator's email
	 * @param email New email
	 * */
	public void setEmail(String email) {
		this.email.set(email);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets email field property
	 * @return email property
	 * */
	public SimpleStringProperty emailProperty() {
		return email;
	}

	/**
	 * Getter:
	 * Gets list of Invoices for Contractor
	 * @return list of Invoices
	 * */
	public Account getAccount() {
		return account;
	}

	/**
	 * Getter:
	 * Gets the personal contact information of the contractor
	 * */
	public PersonalContact getContact() {
		return contact;
	}

	/**
	 * Setter:
	 * Sets the personal contact information of the contractor
	 * */
	public void setContact(PersonalContact contact) {
		this.contact = contact;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------
}
