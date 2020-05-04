package com.apartmate.database.tables.subTables;

import java.util.Date;

import com.apartmate.database.tables.mainTables.Table;
import com.apartmate.main.Main;

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

	private static final long serialVersionUID = 1L;

	// Mandatory fields
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String SSN;

	// Optional fields
	private Date dateOfBirth;
	private int annualIncome; // spouse's annual income(optional)

	public Spouse() {
		this(0,0,"","","","","",new Date());
	}

	// Tenant Spouse constructor
	public Spouse(int id, int fk, String firstName, String lastName, String phone, String email, String SSN, Date dateOfBirth) {
		super(id, fk);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.SSN = SSN;
		this.dateOfBirth = dateOfBirth;
	}

	// Candidate Spouse constructor
	public Spouse(int id, int fk, int fk2, String firstName, String lastName, String phone, String email, String SSN, Date dateOfBirth) {
		super(id, 0, fk2);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.SSN = SSN;
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Spouse name:" + " " + lastName + "," + firstName + "; Email: " + email;
	}

	/**
	 * Gives the full name of the Tenant
	 * @return first name last name
	 * */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Gives the full name of the Tenant; last first: first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return lastName + ", " + firstName;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	} // Just Here to help with getter/setter auto-generation

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}
}
