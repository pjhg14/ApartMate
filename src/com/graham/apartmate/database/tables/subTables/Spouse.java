package com.graham.apartmate.database.tables.subTables;

import java.util.Date;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;

/**
 * Spouse object
 * <p>
 * Records the information of the spouse (husband,wife,boy/girlfriend, etc.) of
 * a Tenant or Candidate
 * </p>
 *
 * @since Can we call this an alpha? (0.1)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
//TODO: Javadoc's for every method
public class Spouse extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	// Mandatory fields
	/**
	 * Spouse's first name
	 * */
	private String firstName;

	/**
	 * Spouse's last name
	 * */
	private String lastName;

	/**
	 * Spouse's phone number
	 * */
	private String phone;

	/**
	 * Spouse's email address
	 * */
	private String email;

	/**
	 * Spouse's idn
	 * */
	private String SSN;

	// Optional fields
	/**
	 * Spouse's date of birth
	 * */
	private Date dateOfBirth;

	/**
	 * Spouse's annual income
	 * */
	private int annualIncome;


	/**
	 * Default constructor
	 * */
	public Spouse() {
		this(0,0,"","","","","",new Date());
	}

	/**
	 * Tenant Spouse constructor
	 * */
	public Spouse(int id, int fk, String firstName, String lastName, String phone, String email, String SSN, Date dateOfBirth) {
		super(id, fk);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.SSN = SSN;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Candidate Spouse constructor
	 * */
	public Spouse(int id, int fk, int fk2, String firstName, String lastName, String phone, String email, String SSN, Date dateOfBirth) {
		super(id, 0, fk2);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.SSN = SSN;
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Spouse's Id and name
	 * @return id, last name, first name
	 * */
	@Override
	public String toString() {
		return String.format("Spouse %d: %s, %s", super.getId() ,lastName, firstName);
	}

	/**
	 * Gives the full name of the Spouse
	 * @return first name last name
	 * */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Gives the full name of the Spouse; last first: first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return lastName + ", " + firstName;
	}

	// *************************************************************
	// General getters and setters
	/**
	 * Getter:
	 * Gets Spouse's first name
	 * @return first name
	 * */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter:
	 * Sets Spouse's first name
	 * @param firstName Spouse's new first name
	 * */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter:
	 * Gets Spouse's last name
	 * @return last name
	 * */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter:
	 * Sets Spouse's last name
	 * @param lastName Spouse's new last name
	 * */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter:
	 * Gets Spouse's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter:
	 * Sets Spouse's phone number
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter:
	 * Gets Spouse's email address
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter:
	 * Sets Spouse's email
	 * @param email New email address
	 * */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter:
	 * Gets Spouse's idn
	 * @return idn
	 * */
	public String getSSN() {
		return SSN;
	}

	/**
	 * Setter:
	 * Sets Spouse's idn
	 * @param sSN New idn
	 * */
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/**
	 * Getter:
	 * Gets Spouse's date of birth
	 * @return date of birth
	 * */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Setter:
	 * Sets Spouse's date o birth
	 * @param dateOfBirth New date of birth
	 * */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Getter:
	 * Gets Spouse's annual income
	 * @return Annual income
	 * */
	public int getAnnualIncome() {
		return annualIncome;
	}

	/**
	 * Setter:
	 * Sets Spouse's annual income
	 * @param annualIncome New annual income
	 * */
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}
}
