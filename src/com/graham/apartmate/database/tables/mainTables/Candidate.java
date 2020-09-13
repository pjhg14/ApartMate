package com.graham.apartmate.database.tables.mainTables;

import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.Account;
import com.graham.apartmate.database.tables.subTables.Lease;
import com.graham.apartmate.database.tables.subTables.PersonalContact;
import com.graham.apartmate.database.tables.subTables.RoomMate;

import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Candidate object
 * <p>
 * Records the information of a candidate looking to rent a specific Apartment
 *
 * @see Table
 * @see RoomMate
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
	/**
	 * First name of Candidate
	 * */
	private final SimpleStringProperty firstName;

	/**
	 * Last name of Candidate
	 * */
	private final SimpleStringProperty lastName;

	/**
	 * Candidate's phone number
	 * */
	private final SimpleStringProperty phone;

	/**
	 * Candidate's email
	 * */
	private final SimpleStringProperty email;

	/**
	 * Candidate's ID number
	 * */
	private final SimpleStringProperty ssn;

	/**
	 * Candidates number of children
	 * */
	private final SimpleIntegerProperty numChildren;

	// Optional fields
	/**
	 * Candidate's date of birth
	 * */
	private final SimpleObjectProperty<LocalDate> dateOfBirth;

	/**
	 * Candidate's annual Income
	 * */
	private final SimpleIntegerProperty annualIncome;

	/**
	 * Whether the Candidate is accepted or not (used to convert a candidate to a tenant)
	 * */
	private final SimpleBooleanProperty accepted;

	// Candidate sub-tables
	/**
	 * Data of Candidate's Spouse (if any)
	 * */
	private ObservableList<RoomMate> roomMates;

	/**
	 * Candidate's first contact
	 * */
	private PersonalContact contact1;

	/**
	 * Candidate's second contact
	 * */
	private PersonalContact contact2;
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
				null, null);
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
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					 PersonalContact contact1, PersonalContact contact2) {
		this(id, fk, firstName, lastName, phone, email, ssn, LocalDate.MIN, 0,0,
				contact1, contact2);
	}

	/**
	 * ... constructor
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, LocalDate dateOfBirth,
					 int annualIncome, String ssn, int numChildren, PersonalContact contact1, PersonalContact contact2) {
		this(id, fk, firstName, lastName, phone, email, ssn, dateOfBirth ,annualIncome, numChildren,
				contact1, contact2);
	}

	/**
	 * Full constructor
	 * */
	public Candidate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					 LocalDate dateOfBirth, int annualIncome, int numChildren, PersonalContact contact1,
					 PersonalContact contact2) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/TenantImg_small.png");
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
		this.annualIncome = new SimpleIntegerProperty(annualIncome);
		this.ssn = new SimpleStringProperty(ssn);
		this.numChildren = new SimpleIntegerProperty(numChildren);
		accepted = new SimpleBooleanProperty(false);
		this.roomMates = FXCollections.observableArrayList();
		this.contact1 = contact1;
		this.contact2 = contact2;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Overrided & Utility Methods/////////////////////////////////
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

	/**
	 * Gives the full name of the Candidate
	 * @return first name, last name
	 * */
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	/**
	 * Gives the full name of the Candidate: last first; first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return getLastName() + ", " + getFirstName();
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
	public Tenant accept(Account account, Lease lease, LocalDate movInDate) {
		return new Tenant(this, movInDate, account, lease);
	}

	/**
	 * Returns whether or not a Candidate has a Spouse
	 * @return <code>true</code> if Candidate has a Spouse <code>false</code> if not
	 * */
	public boolean hasSpouse() {
		for (RoomMate roomMate : roomMates) {
			if (roomMate.isSpouse())
				return true;
		}

		return false;
	}

	/**
	 * Adds a roommate to the candidate
	 * */
	public void addRoomMate(RoomMate roomMate) {
		roomMates.add(roomMate);
	}

	/**
	 * Removes Candidate's Spouse
	 * */
	public void removeRoomMate(RoomMate roomMate) {
		roomMates.remove(roomMate);
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
		return firstName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the first name of Candidate
	 * @param firstName New first name
	 * */
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets first name field property
	 * @return first name property
	 * */
	public SimpleStringProperty firstNameProperty() {
		return firstName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets last name of Candidate
	 * @return last name
	 * */
	public String getLastName() {
		return lastName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets last name of Candidate
	 * @param lastName New last name
	 * */
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets last name field property
	 * @return last name property
	 * */
	public SimpleStringProperty lastNameProperty() {
		return lastName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's phone number
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
	 * <p>
	 * Gets Candidate's email
	 * @return email
	 * */
	public String getEmail() {
		return email.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's email
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
	 * <p>
	 * Gets Candidate's Id number
	 * @return idn
	 * */
	public String getSsn() {
		return ssn.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's idn
	 * @param sSN New idn
	 * */
	public void setSsn(String sSN) {
		ssn.set(sSN);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets ssn field property
	 * @return ssn property
	 * */
	public SimpleStringProperty ssnProperty() {
		return ssn;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's date of birth
	 * @return date of birth
	 * */
	public LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's date of birth
	 * @param dateOfBirth New date of birth
	 * */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets date of birth field property
	 * @return date of birth property
	 * */
	public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
		return dateOfBirth;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's annual income
	 * @return annual income
	 * */
	public int getAnnualIncome() {
		return annualIncome.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's annual income
	 * @param annualIncome New annual income
	 * */
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome.set(annualIncome);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets annual income field property
	 * @return annual income property
	 * */
	public SimpleIntegerProperty annualIncomeProperty() {
		return annualIncome;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets number of Candidate's children
	 * @return # of children
	 * */
	public int getNumChildren() {
		return numChildren.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets number of Candidate's children
	 * @param numChildren New # of children
	 * */
	public void setNumChildren(int numChildren) {
		this.numChildren.set(numChildren);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets number of children field property
	 * @return number of children property
	 * */
	public SimpleIntegerProperty numChildrenProperty() {
		return numChildren;
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
	 * Gets the Candidate's Spouse
	 * @return Spouse information
	 * */
	public ObservableList<RoomMate> getRoomMates() {
		return FXCollections.unmodifiableObservableList(roomMates);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the Candidate's Spouse
	 * @param roomMate New Spouse
	 * */
	public void setSpouse(ObservableList<RoomMate> roomMate) {
		this.roomMates = roomMate;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's first Emergency Contact
	 * */
	public PersonalContact getContact1() {
		return contact1;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's first Emergency Contact
	 * */
	public void setContact1(PersonalContact contact1) {
		this.contact1 = contact1;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Candidate's second Emergency Contact
	 * */
	public PersonalContact getContact2() {
		return contact2;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Candidate's second Emergency Contact
	 * */
	public void setContact2(PersonalContact contact2) {
		this.contact2 = contact2;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------
}
