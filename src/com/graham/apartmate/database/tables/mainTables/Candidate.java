package com.graham.apartmate.database.tables.mainTables;

import java.util.Date;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.EContact;
import com.graham.apartmate.database.tables.subTables.Spouse;

import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Candidate object
 * <p>
 * Records the information of a candidate looking to rent a specific Apartment
 *
 * @see Table
 * @see Spouse
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Candidate extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/***/
	private Image image;

	// Mandatory fields
	/**
	 * First name of Candidate
	 * */
	private String firstName;

	/**
	 * Last name of Candidate
	 * */
	private String lastName;

	/**
	 * Candidate's phone number
	 * */
	private String phone;

	/**
	 * Candidate's email
	 * */
	private String email;

	/**
	 * Candidate's ID number
	 * */
	private String SSN;

	// Optional fields
	/**
	 * Candidate's date of birth
	 * */
	private Date dateOfBirth;

	/**
	 * Candidate's annual Income
	 * */
	private int annualIncome;

	/**
	 * Candidates number of children
	 * */
	private int numChildren;

	/**
	 * Whether the Candidate is accepted or not (used to convert a candidate to a tenant)
	 * */
	private boolean accepted;

	// Candidate sub-tables
	/**
	 * Data of Candidate's Spouse (if any)
	 * */
	private Spouse spouse;

	/**
	 * Candidate's first contact
	 * */
	private EContact contact1;

	/**
	 * Candidate's second contact
	 * */
	private EContact contact2;

	/**
	 * Default Constructor
	 * */
	public Candidate() {
		this(0,0,"","","","","",new Date(),0,0,
				new Spouse(DUMMY_TABLE),new EContact(DUMMY_TABLE), new EContact(DUMMY_TABLE));
	}

	/**
	 * Dummy Candidate Constructor
	 * */
	public Candidate(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Mandatory field constructor w/o spouse
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String SSN,
					 EContact contact1, EContact contact2) {
		this(id, fk, firstName, lastName, phone, email, SSN, new Date(), 0,0,
				new Spouse(DUMMY_TABLE), contact1, contact2);
	}

	/**
	 * Mandatory field constructor w/ spouse
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String SSN,
			Spouse spouse, EContact contact1, EContact contact2) {
		this(id, fk, firstName, lastName, phone, email, SSN, new Date(),0,0,
				spouse, contact1,contact2);
	}

	/**
	 * Full constructor w/o spouse
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
			int annualIncome, String SSN, int numChildren, EContact contact1, EContact contact2) {
		this(id, fk, firstName, lastName, phone, email, SSN, dateOfBirth ,annualIncome, numChildren,
				new Spouse(DUMMY_TABLE),contact1, contact2);
	}

	/**
	 * Full constructor w/ spouse
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email,String SSN,
					 Date dateOfBirth, int annualIncome,  int numChildren, Spouse spouse, EContact contact1,
					 EContact contact2) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/Tenantimg_small.png");
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
		this.contact1 = contact1;
		this.contact2 = contact2;
	}

	/**
	 * Overrided toString() method
	 * <p>
	 * Returns Candidate's Id and name
	 * */
	@Override
	public String toString() {
		return String.format("Candidate: %s; %s, %s", super.getId() ,lastName, firstName);
	}

	/***/
	@Override
	public String getGenericName() {
		return getFullName();
	}

	/***/
	@Override
	public DBTables getTableType() {
		return DBTables.CANDIDATES;
	}

	/***/
	@Override
	public Image getImage() {
		return image;
	}

	/**
	 * Gives the full name of the Candidate
	 * @return first name, last name
	 * */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Gives the full name of the Candidate: last first; first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return lastName + ", " + firstName;
	}

	/**
	 * Returns whether or not a Candidate has a Spouse
	 * @return <code>true</code> if Candidate has a Spouse <code>false</code> if not
	 * */
	public boolean hasSpouse() {
		return !spouse.isDummy();
	}

	/**
	 * Removes Candidate's Spouse
	 * */
	public void removeSpouse() {
		spouse = new Spouse(DUMMY_TABLE);
	}
	//------------------------------------------------------------
	// General getters and setters////////////////////////////////
	//------------------------------------------------------------
	/***/
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets first name of Candidate
	 * @return first name*/
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the first name of Candidate
	 * @param firstName New first name
	 * */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets last name of Candidate
	 * @return last name
	 * */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets last name of Candidate
	 * @param lastName New last name
	 * */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's phone number
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's email
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's email
	 * @param email New email
	 * */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's Id number
	 * @return idn
	 * */
	public String getSSN() {
		return SSN;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's idn
	 * @param sSN New idn
	 * */
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's date of birth
	 * @return date of birth
	 * */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's date of birth
	 * @param dateOfBirth New date of birth
	 * */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's annual income
	 * @return annual income
	 * */
	public int getAnnualIncome() {
		return annualIncome;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's annual income
	 * @param annualIncome New annual income
	 * */
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets number of Candidate's children
	 * @return # of children
	 * */
	public int getNumChildren() {
		return numChildren;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets number of Candidate's children
	 * @param numChildren New # of children
	 * */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * Getter:
	 * <p>
	 * Returns if a Candidate is accepted or not
	 * @return <code>true</code> if Candidate is accepted to live in a particular Apartment,
	 * <code>false</code> if not so
	 * */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets whether the Candidate has been accepted or not
	 * @param accepted New value
	 * */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the Candidate's Spouse
	 * @return Spouse information
	 * */
	public Spouse getSpouse() {
		return spouse;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the Candidate's Spouse
	 * @param spouse New Spouse
	 * */
	public void setSpouse(Spouse spouse) {
		this.spouse = spouse;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's first Emergency Contact
	 * */
	public EContact getContact1() {
		return contact1;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's first Emergency Contact
	 * */
	public void setContact1(EContact contact1) {
		this.contact1 = contact1;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's second Emergency Contact
	 * */
	public EContact getContact2() {
		return contact2;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's second Emergency Contact
	 * */
	public void setContact2(EContact contact2) {
		this.contact2 = contact2;
	}

	//------------------------------------------------------------
}
