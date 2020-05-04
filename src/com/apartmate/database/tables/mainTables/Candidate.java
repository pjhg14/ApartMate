package com.apartmate.database.tables.mainTables;

import java.util.Date;

import com.apartmate.database.tables.subTables.Spouse;

import com.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Candidate object
 * <p>
 * Records the information of a candidate looking to rent a specific Apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
public class Candidate extends Table {

	/***/
	private static final long serialVersionUID = 1L;

	/***/
	private Image image;

	// Mandatory fields
	/***/
	private String firstName;

	/***/
	private String lastName;

	/***/
	private String phone;

	/***/
	private String email;

	/***/
	private String SSN;

	// Optional fields
	/***/
	private Date dateOfBirth;

	/***/
	private int annualIncome; // candidate's annual income(optional)

	/***/
	private int numChildren;

	/***/
	private boolean accepted;

	// Candidate sub-tables
	/***/
	private Spouse spouse;

	// Dummy Candidate
	/***/
	public Candidate() {
		this(0,0,"","","","","",new Date(),0,0,new Spouse());
	}

	// Mandatory field constructor
	/***/
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String SSN) {
		this(id, fk, firstName, lastName, phone, email, SSN, new Date(), 0,0,new Spouse());
	}

	// Mandatory field constructor w/ spouse
	/***/
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String SSN,
			Spouse spouse) {
		this(id, fk, firstName, lastName, phone, email, SSN, new Date(),0,0,spouse);
	}

	// Full constructor w/o spouse
	/***/
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
			int annualIncome, String SSN, int numChildren) {
		this(id, fk, firstName, lastName, phone, email, SSN, dateOfBirth ,annualIncome, numChildren, new Spouse());
	}

	// Full constructor w/ spouse
	/***/
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email,String SSN, Date dateOfBirth,
			int annualIncome,  int numChildren, Spouse spouse) {
		super(id, fk);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.annualIncome = annualIncome;
		this.SSN = SSN;
		this.numChildren = numChildren;
		accepted = false;
		this.spouse = spouse;
	}

	/***/
	@Override
	public String toString() {
		return "Candidate name:" + " " + lastName + "," + firstName + "; Email: " + email;
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
	/***/
	public Image getImage() {
		return image;
	}

	/***/
	public void setImage(Image image) {
		this.image = image;
	}

	/***/
	public String getFirstName() {
		return firstName;
	}

	/***/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/***/
	public String getLastName() {
		return lastName;
	}

	/***/
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getEmail() {
		return email;
	}

	/***/
	public void setEmail(String email) {
		this.email = email;
	}

	/***/
	public String getSSN() {
		return SSN;
	}

	/***/
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/***/
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/***/
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/***/
	public int getAnnualIncome() {
		return annualIncome;
	}

	/***/
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	/***/
	public int getNumChildren() {
		return numChildren;
	}

	/***/
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	/***/
	public boolean isAccepted() {
		return accepted;
	}

	/***/
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/***/
	public Spouse getSpouse() {
		return spouse;
	}

	/***/
	public void setSpouse(Spouse spouce) {
		this.spouse = spouce;
	}
	// *************************************************************
}
