package com.graham.apartmate.database.tables.subTables;

import java.util.ArrayList;
import java.util.Comparator;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Insurance object
 * <p>
 * Records the insurance information of an apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
// Might be redundant; Bill class serves the exact same purpose. Delete?
// If culled, add email field to Bill class
public class Insurance extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of Insurance company
	 * */
	private String name;

	/**
	 * Monthly payment
	 * */
	private double bill;

	/**
	 * Company's phone number
	 * */
	private String phone;

	/**
	 * Company's email
	 * */
	private String email;

	/**
	 * List of Invoices
	 * */
	private ArrayList<Invoice> invoices;

	/**
	 * Insurance sorting constant
	 * */
	public static final Comparator<Insurance> INS_BY_NAME = Comparator.comparing(Insurance::getName);

	/**
	 * Default constructor
	 * */
	public Insurance() {
		this(0,0,"",0,"","");
	}

	/**
	 * Dummy Insurance Constructor
	 * */
	public Insurance(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Full constructor
	 * */
	public Insurance(int id, int fk, String name, double bill, String phone, String email) {
		super(id, fk);
		this.name = name;
		this.bill = bill;
		this.phone = phone;
		this.email = email;

		invoices = new ArrayList<>();
	}

	/**
	 * Gives all identifying information for the Insurance
	 * @return id, name, email, phone#
	 * */
	@Override
	public String toString() {
		return String.format("Insurance: %d; %s, %s, %s ", super.getId(), name, email, phone);
	}

	@Override
	public String getGenericName() {
		return name;
	}

	/**
	 * Gets the type of Table in question
	 *
	 * @return table type
	 */
	@Override
	public DBTables getTableType() {
		return DBTables.INSURANCES;
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

	// *************************************************************
	// General getters and setters
	/**
	 * Getter:
	 * <p>
	 * Gets name of Insurance company
	 * @return Company's name
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets name of Insurance company
	 * @param name New name for Insurance company
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets monthly payment
	 * @return Monthly payment
	 * */
	public double getBill() {
		return bill;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets monthly payment
	 * @param bill New amount for monthly payment
	 * */
	public void setBill(double bill) {
		this.bill = bill;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Insurance company's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Insurance company's phone number
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Insurance company's email
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Insurance company's email
	 * @param email New email address
	 * */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets list of Invoices
	 * @return Invoice list
	 * */
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Invoices
	 * @param invoices New Invoice list
	 * */
	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
	// *************************************************************

}
