package com.graham.apartmate.database.tables.subTables;


import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

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
public class RoomMate extends Table {

	//-----------------------------------------------------------------
	//Fields///////////////////////////////////////////////////////////
	//-----------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	// Mandatory fields
	/**
	 * Spouse's first name
	 * */
	private final SimpleStringProperty firstName;

	/**
	 * Spouse's last name
	 * */
	private final SimpleStringProperty lastName;

	/**
	 * Spouse's phone number
	 * */
	private final SimpleStringProperty phone;

	/**
	 * Spouse's email address
	 * */
	private final SimpleStringProperty email;

	/**
	 * Spouse's idn
	 * */
	private final SimpleStringProperty ssn;

	// Optional fields
	/**
	 * Spouse's date of birth
	 * */
	private final SimpleObjectProperty<LocalDate> dateOfBirth;

	/**
	 * Spouse's annual income
	 * */
	private final SimpleIntegerProperty annualIncome;

	/***/
	private final SimpleBooleanProperty isSpouse;
	//-----------------------------------------------------------------
	//-----------------------------------------------------------------

	//-----------------------------------------------------------------
	//Constructors/////////////////////////////////////////////////////
	//-----------------------------------------------------------------
	/**
	 * Default constructor
	 * */
	public RoomMate() {
		this(0,0,"","","","","",0, LocalDate.MIN);
	}

	/**
	 * Dummy Spouse Constructor
	 * */
	public RoomMate(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Tenant Spouse constructor
	 * */
	public RoomMate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/TenantImg_small.png");
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.ssn = new SimpleStringProperty(ssn);
		this.annualIncome = new SimpleIntegerProperty(annualIncome);
		this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
		this.isSpouse = new SimpleBooleanProperty(false);
	}

	/**
	 * Candidate Spouse constructor
	 * */
	public RoomMate(int id, int fk, int fk2, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth) {
		super(id, 0, fk2);
		image = new Image("com/graham/apartmate/ui/res/TenantImg_small.png");
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.ssn = new SimpleStringProperty(ssn);
		this.annualIncome = new SimpleIntegerProperty(annualIncome);
		this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
		this.isSpouse = new SimpleBooleanProperty(false);
	}

	/**
	 * Tenant Spouse constructor
	 * */
	public RoomMate(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth, boolean isSpouse) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/TenantImg_small.png");
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.ssn = new SimpleStringProperty(ssn);
		this.annualIncome = new SimpleIntegerProperty(annualIncome);
		this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
		this.isSpouse = new SimpleBooleanProperty(isSpouse);
	}

	/**
	 * Candidate Spouse constructor
	 * */
	public RoomMate(int id, int fk, int fk2, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth, boolean isSpouse) {
		super(id, 0, fk2);
		image = new Image("com/graham/apartmate/ui/res/TenantImg_small.png");
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.email = new SimpleStringProperty(email);
		this.ssn = new SimpleStringProperty(ssn);
		this.annualIncome = new SimpleIntegerProperty(annualIncome);
		this.dateOfBirth = new SimpleObjectProperty<>(dateOfBirth);
		this.isSpouse = new SimpleBooleanProperty(isSpouse);
	}
	//-----------------------------------------------------------------
	//-----------------------------------------------------------------

	//-----------------------------------------------------------------
	//Overrided & Utility Methods//////////////////////////////////////
	//-----------------------------------------------------------------
	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Spouse's Id and name
	 * @return id, last name, first name
	 * */
	@Override
	public String toString() {
		return String.format("Spouse %d: %s, %s", super.getId() ,getLastName(), getFirstName());
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 *
	 * @return Table's "unique" name
	 */
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
		return DBTables.ROOMMATE;
	}

	/**
	 * Gives the full name of the Spouse
	 * @return first name last name
	 * */
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	/**
	 * Gives the full name of the Spouse; last first: first last
	 * @return last name, first name
	 * */
	public String getProperName() {
		return getLastName() + ", " + getFirstName();
	}

	/**
	 * Checks whether or not either the first name or last name is empty
	 * @return <code>true</code> if so, <code>false</code> if not
	 * */
	public boolean invalidName() {
		return getFirstName().equals("") || getLastName().equals("");
	}
	//-----------------------------------------------------------------
	//-----------------------------------------------------------------

	//-----------------------------------------------------------------
	//General getters and setters//////////////////////////////////////
	//-----------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's first name
	 * @return first name
	 * */
	public String getFirstName() {
		return firstName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's first name
	 * @param firstName Spouse's new first name
	 * */
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the first name field property
	 * @return first name property
	 * */
	public SimpleStringProperty firstNameProperty() {
		return firstName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's last name
	 * @return last name
	 * */
	public String getLastName() {
		return lastName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's last name
	 * @param lastName Spouse's new last name
	 * */
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the last name field property
	 * @return last name property
	 * */
	public SimpleStringProperty lastNameProperty() {
		return lastName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's phone number
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the phone field property
	 * @return phone property
	 * */
	public SimpleStringProperty phoneProperty() {
		return phone;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's email address
	 * @return email
	 * */
	public String getEmail() {
		return email.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's email
	 * @param email New email address
	 * */
	public void setEmail(String email) {
		this.email.set(email);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the email address field property
	 * @return email address property
	 * */
	public SimpleStringProperty emailProperty() {
		return email;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's idn
	 * @return idn
	 * */
	public String getSsn() {
		return ssn.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's idn
	 * @param sSN New idn
	 * */
	public void setSsn(String sSN) {
		ssn.set(sSN);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the ssn field property
	 * @return ssn property
	 * */
	public SimpleStringProperty ssnProperty() {
		return ssn;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's date of birth
	 * @return date of birth
	 * */
	public LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's date o birth
	 * @param dateOfBirth New date of birth
	 * */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the date of birth field property
	 * @return date of birth property
	 * */
	public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
		return dateOfBirth;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's annual income
	 * @return Annual income
	 * */
	public int getAnnualIncome() {
		return annualIncome.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Spouse's annual income
	 * @param annualIncome New annual income
	 * */
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome.set(annualIncome);
	}

	/**
	 * Getter
	 * <p>
	 * Gets the annual income field property
	 * @return annual income property
	 * */
	public SimpleIntegerProperty annualIncomeProperty() {
		return annualIncome;
	}

	public boolean isSpouse() {
		return isSpouse.get();
	}

	public SimpleBooleanProperty isSpouseProperty() {
		return isSpouse;
	}

	public void setIsSpouse(boolean isSpouse) {
		this.isSpouse.set(isSpouse);
	}

	//-----------------------------------------------------------------
	//-----------------------------------------------------------------
}
