package com.graham.apartmate.database.tables.mainTables;

import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.Account;
import com.graham.apartmate.database.tables.subTables.Lease;
import com.graham.apartmate.database.tables.subTables.Contact;
import com.graham.apartmate.database.tables.subTables.Occupant;

import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.res.classes.FXMLLocation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Candidate object
 * <p>
 * Records the information of a candidate looking to rent a specific Apartment
 *
 * @see Table
 * @see Occupant
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Candidate extends Table {

	//------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	// Mandatory fields
	/***/
	private Contact personalInfo;

	/**
	 * Whether the Candidate is accepted or not (used to convert a candidate to a tenant)
	 * */
	private final SimpleBooleanProperty accepted;

	/***/
	private final SimpleObjectProperty<LocalDate> movInDate;

	// Candidate sub-tables
	/**
	 * Data of Candidate's Spouse (if any)
	 * */
	private ObservableList<Occupant> occupants;

	/**
	 * Candidate's first contact
	 * */
	private Contact eContact1;

	/**
	 * Candidate's second contact
	 * */
	private Contact eContact2;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Constructor/////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Default Constructor
	 * */
	public Candidate() {
		this(0,0,"","","","","",LocalDate.MIN,0,0,
				LocalDate.MIN, null, null);
	}

	/**
	 * Mandatory field constructor w/o birth date, annual income, or # of children
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					 Contact eContact1, Contact eContact2) {
		this(id, fk, firstName, lastName, phone, email, ssn, LocalDate.MIN, 0,0, LocalDate.MIN,
				eContact1, eContact2);
	}

	/**
	 * Full constructor
	 * @param id Candidate's id
	 * @param fk Id of Living Space Candidate applies for
	 * @param firstName Candidate's first name
	 * @param lastName Candidate's last name
	 * @param phone Candidate's phone number
	 * @param email Candidate's email address
	 * @param ssn Candidate's ssn
	 * @param dateOfBirth Candidate's date of birth
	 * @param annualIncome Candidate's Annual Income
	 * @param numChildren Number of children Candidate has
	 * @param eContact1 Candidate's first emergency contact
	 * @param eContact2 Candidate's second emergency contact
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					 LocalDate dateOfBirth, int annualIncome, int numChildren, LocalDate movInDate, Contact eContact1,
					 Contact eContact2) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/img/TenantImg_small.png");
		personalInfo = new Contact(0,0,0,0, firstName, lastName, phone, email, ssn, numChildren,
				dateOfBirth, annualIncome);
		this.movInDate = new SimpleObjectProperty<>(movInDate);
		accepted = new SimpleBooleanProperty(false);
		this.occupants = FXCollections.observableArrayList();
		this.eContact1 = eContact1;
		this.eContact2 = eContact2;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Overrides///////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Overrided toString() method
	 * <p>
	 * Returns Candidate's Id and name
	 * */
	@Override
	public String toString() {
		return String.format("Candidate: %s; %s, %s", super.getId() ,getLastName(), getFirstName());
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 * @return Table's "generic" name
	 * */
	@Override
	public String getGenericName() {
		return getFullName();
	}

	/**
	 * Gets the type of Table in question
	 * @return table type
	 * */
	@Override
	public DBTables getTableType() {
		return DBTables.CANDIDATES;
	}

	/***/
	@Override
	public String getInfoLocation() {
		return FXMLLocation.CANDINFO.getLocation();
	}

	/***/
	@Override
	public String getAddLocation() {
		return FXMLLocation.CANDADD.getLocation();
	}

	/***/
	@Override
	public String getEditLocation() {
		return FXMLLocation.CANDEDIT.getLocation();
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Utility Methods/////////////////////////////////
	//------------------------------------------------------------

	/**
	 * Gives the full name of the Candidate
	 * @return first name, last name
	 * */
	public String getFullName() {
		return personalInfo.getFullName();
	}

	/**
	 * Gives the full name of the Candidate: last first; first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return personalInfo.getProperName();
	}

	/**
	 * Tests whether the current candidate has an invalid name
	 * @return <code>false</code> if either first name or last name is empty, <code>true</code> if not
	 * */
	public boolean hasInvalidName() {
		return getFirstName().equals("") || getLastName().equals("");
	}

	/**
	 * Accepts the Candidate and converts it to a Tenant
	 * @return New Tenant from current Candidate
	 * */
	public Tenant accept(int aptId,Account account, Lease lease, LocalDate movInDate) {
		return new Tenant(this, aptId, movInDate, account, lease);
	}

	/**
	 * Returns whether or not a Candidate has a Spouse
	 * @return <code>true</code> if Candidate has a Spouse <code>false</code> if not
	 * */
	public boolean hasSpouse() {
		for (Occupant occupant : occupants) {
			if (occupant.isSpouse())
				return true;
		}

		return false;
	}

	/**
	 * Adds a roommate to the candidate
	 * */
	public void addRoomMate(Occupant occupant) {
		occupants.add(occupant);
	}

	/**
	 * Removes Candidate's Spouse
	 * */
	public void removeRoomMate(Occupant occupant) {
		occupants.remove(occupant);
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// General getters and setters////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gets first name of Candidate
	 * @return first name
	 * */
	public String getFirstName() {
		return personalInfo.getFirstName();
	}


	/**
	 * Getter:
	 * <p>
	 * Gets last name of Candidate
	 * @return last name
	 * */
	public String getLastName() {
		return personalInfo.getLastName();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return personalInfo.getPhoneNumber();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's email
	 * @return email
	 * */
	public String getEmail() {
		return personalInfo.getEmail();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the contact information of this table
	 * @return contact information
	 * */
	public Contact getPersonalInfo() {
		return personalInfo;
	}

	/**
	 * Setter
	 * <p>
	 * Sets the contact information of this table
	 * @param personalInfo new contact information
	 * */
	public void setPersonalInfo(Contact personalInfo) {
		this.personalInfo = personalInfo;
	}

	/**
	 * Getter:
	 * <p>
	 * Returns if a Candidate is accepted or not
	 * @return <code>true</code> if Candidate is accepted to live in a particular Apartment,
	 * <code>false</code> if not so
	 * */
	public boolean isAccepted() {
		return accepted.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets whether the Candidate has been accepted or not
	 * @param accepted New value
	 * */
	protected void setAccepted(boolean accepted) {
		this.accepted.set(accepted);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets accepted field property
	 * @return accepted property
	 * */
	public SimpleBooleanProperty acceptedProperty() {
		return accepted;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's move in date
	 * @return move in date
	 * */
	public LocalDate getMovInDate() {
		return movInDate.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's move in date
	 * @param movInDate New move in date
	 * */
	public void setMovInDate(LocalDate movInDate) {
		this.movInDate.set(movInDate);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets move in date field property
	 * @return move in date property
	 * */
	public SimpleObjectProperty<LocalDate> movInDateProperty() {
		return movInDate;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the Candidate's Spouse
	 * @return Spouse information
	 * */
	public ObservableList<Occupant> getOccupants() {
		return FXCollections.unmodifiableObservableList(occupants);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the Candidate's Spouse
	 * @param occupant New Spouse
	 * */
	public void setSpouse(ObservableList<Occupant> occupant) {
		this.occupants = occupant;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's first Emergency Contact
	 * */
	public Contact getEContact1() {
		return eContact1;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's first Emergency Contact
	 * */
	public void setEContact1(Contact eContact1) {
		this.eContact1 = eContact1;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's second Emergency Contact
	 * */
	public Contact getEContact2() {
		return eContact2;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's second Emergency Contact
	 * */
	public void setEContact2(Contact eContact2) {
		this.eContact2 = eContact2;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------
}
