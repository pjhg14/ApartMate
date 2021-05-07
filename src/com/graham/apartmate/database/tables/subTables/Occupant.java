package com.graham.apartmate.database.tables.subTables;


import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleBooleanProperty;
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
public class Occupant extends Table {

	//-----------------------------------------------------------------
	//Fields///////////////////////////////////////////////////////////
	//-----------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/***/
	private final Contact personalInfo;

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
	public Occupant() {
		this(0,0,"","","","","",0, LocalDate.MIN, false);
	}

	/**
	 * Tenant Roommate constructor
	 * */
	public Occupant(int id, int fk, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth, boolean isSpouse) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/img/TenantImg_small.png");
		personalInfo = new Contact(0,0,0,0,firstName,lastName,phone,email, ssn, 0,
				dateOfBirth, annualIncome);
		this.isSpouse = new SimpleBooleanProperty(isSpouse);
	}

	/**
	 * Candidate Roommate constructor
	 * */
	public Occupant(int id, int fk, int fk2, String firstName, String lastName, String phone, String email, String ssn,
					int annualIncome, LocalDate dateOfBirth, boolean isSpouse) {
		super(id, 0, fk2);
		image = new Image("com/graham/apartmate/ui/res/img/TenantImg_small.png");
		personalInfo = new Contact(0,0,0,0,firstName,lastName,phone,email, ssn, 0,
				dateOfBirth, annualIncome);
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

	/***/
	@Override
	public String getInfoLocation() {
		return null;
	}

	/***/
	@Override
	public String getAddLocation() {
		return null;
	}

	/***/
	@Override
	public String getEditLocation() {
		return null;
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
		return personalInfo.getFirstName();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's last name
	 * @return last name
	 * */
	public String getLastName() {
		return personalInfo.getLastName();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return personalInfo.getPhoneNumber();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Spouse's email address
	 * @return email
	 * */
	public String getEmail() {
		return personalInfo.getEmail();
	}

	/***/
	public Contact getPersonalInfo() {
		return personalInfo;
	}

	/***/
	public boolean isSpouse() {
		return isSpouse.get();
	}

	/***/
	public SimpleBooleanProperty isSpouseProperty() {
		return isSpouse;
	}

	/***/
	public void setIsSpouse(boolean isSpouse) {
		this.isSpouse.set(isSpouse);
	}
	//-----------------------------------------------------------------
	//-----------------------------------------------------------------
}
