package com.graham.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.EContact;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.database.tables.subTables.Spouse;
import com.graham.apartmate.database.tables.subTables.Invoice;
import com.graham.apartmate.database.utilities.unordered.Heck;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Tenant object
 * <p>
 * Records the information of a tenant of a specific Apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Replace all mentions of depreciated Date classes with java.time.LocalDate
// Add boolean indicating whether the security deposit was sent
// Possibly add address Tenant moved to for deposit mailing
// Add interface for account logging?
// Add loaded image constructor
public class Tenant extends Table {

	/**
	 * Serializable Long
	 * */
	private static final long serialVersionUID = 1L;

	/***/
	private Image image;

	//------------------------------------------------------------
	// Mandatory fields///////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * First name of Tenant
	 * */
	private String firstName;

	/**
	 * Last name of Tenant
	 * */
	private String lastName;

	/**
	 * Phone # of Tenant
	 * */
	private String phone;

	/**
	 * Email address of Tenant
	 * */
	private String email;

	/**
	 * Tenant's ID number
	 * */
	private String SSN;

	/**
	 * Amount Tenant owes every month
	 * */
	private double rent; // amount tenant owes every month

	/**
	 * # of children the Tenant has
	 * */
	private int numChildren;

	/**
	 * Tenant's move-in date
	 * */
	private Date movInDate; // tenant's move in date
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// Optional/uninitialized fields//////////////////////////////
	//------------------------------------------------------------
	/**
	 * Tenant's date of birth
	 * */
	private Date dateOfBirth;

	/**
	 * Tenant's annual income
	 * */
	private int annualIncome; // tenants's annual income(optional)

	/**
	 * True if the Tenant is slated for eviction/evicted
	 * */
	private boolean slatedForEviction;

	/**
	 * Eviction reason
	 * */
	private String evictReason;

	/**
	 * Date a Tenant has moved out of the Apartment
	 * */
	private Date movOutDate;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// Tenant sub-tables//////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Data of Tenant's Spouse (if any)
	 * */
	private Spouse spouse;

	/**
	 * Tenant's first contact
	 * */
	private EContact contact1;

	/**
	 * Tenant's second contact
	 * */
	private EContact contact2;

	/**
	 * List of the Tenant's invoices
	 * */
	private List<Invoice> invoices;

	/**
	 * List of the Tenant's inspections
	 * */
	private List<NoteLog> inspections;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Constructor/////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Default constructor:
	 * */
	public Tenant() {
		this(0,0,"","","","",new Date(),-1,"",-1,0,
				new Date(),new Spouse(DUMMY_TABLE), new EContact(DUMMY_TABLE), new EContact(DUMMY_TABLE));
	}

	/**
	 * Dummy Tenant Constructor
	 * */
	public Tenant(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Candidate conversion constructor
	 * @param id Id of tenant to be
	 * @param candidate Candidate to convert
	 * @param rent New Tenant's rent
	 * @param movInDate New Tenant's move in date
	 * */
	public Tenant(int id, Candidate candidate, double rent, Date movInDate) {
		this(id, candidate.getFk(), candidate.getFirstName(), candidate.getLastName(), candidate.getPhone(),
				candidate.getEmail(),candidate.getDateOfBirth(),candidate.getAnnualIncome(),candidate.getSSN(),
				rent,candidate.getNumChildren(), movInDate, candidate.getSpouse(), candidate.getContact1(),
				candidate.getContact2());
	}

	// Constructor w/o Spouse
	/**
	 * Creates a Tenant object w/o a spouse
	 * @param id Primary Key of Tenant
	 * @param fk Foreign Key to Apartment
	 * @param firstName Tenant's first name
	 * @param lastName Tenant's last name
	 * @param phone Tenant's phone #
	 * @param email Tenant's email
	 * @param dateOfBirth Tenant's date of birth
	 * @param annualIncome Tenant's annual income
	 * @param SSN Tenants's ID #
	 * @param rent Tenants's monthly rent
	 * @param numChildren Tenant's # of children
	 * @param movInDate Tenant's move in date
	 * */
	public Tenant(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
			int annualIncome, String SSN, double rent, int numChildren, Date movInDate, EContact contact1,
				  EContact contact2) {
		this(id,fk,firstName,lastName,phone,email,dateOfBirth,annualIncome,SSN,rent,numChildren,movInDate,
				new Spouse(DUMMY_TABLE), contact1, contact2);
	}

	// Constructor w/ Spouse
	/**
	 * Creates a Tenant object w/o a spouse
	 * @param id Primary Key of Tenant
	 * @param fk Foreign Key to Apartment
	 * @param firstName Tenant's first name
	 * @param lastName Tenant's last name
	 * @param phone Tenant's phone #
	 * @param email Tenant's email
	 * @param dateOfBirth Tenant's date of birth
	 * @param annualIncome Tenant's annnual income
	 * @param SSN Tenants's ID #
	 * @param rent Tenants's monthly rent
	 * @param numChildren Tenant's # of children
	 * @param movInDate Tenant's move in date
	 * @param spouse Tenant's Spouse
	 * */
	public Tenant(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
				  int annualIncome, String SSN, double rent, int numChildren, Date movInDate, Spouse spouse,
				  EContact contact1, EContact contact2) {
		super(id, fk);
		image = new Image("com/graham/apartmate/ui/res/Tenantimg_small.png");
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.annualIncome = annualIncome;
		this.SSN = SSN;
		this.rent = rent;
		this.numChildren = numChildren;
		this.movInDate = movInDate;
		movOutDate = Heck.MIN_DATE;
		slatedForEviction = false;
		evictReason = "";

		this.spouse = spouse;
		this.contact1 = contact1;
		this.contact2 = contact2;
		invoices = new ArrayList<>();
		inspections = new ArrayList<>();
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	// ToString method; prints full name (last then first)
	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Tenant's Id and name
	 * @return id, last name, first name
	 * */
	@Override
	public String toString() {
		return String.format("Tenant %d: %s, %s", super.getId(), lastName, firstName);
	}

	/***/
	@Override
	public String getGenericName() {
		return getFullName();
	}

	/***/
	@Override
	public DBTables getTableType() {
		return DBTables.TENANTS;
	}

	/***/
	@Override
	public Image getImage() {
		return image;
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

	/***/
	public boolean invalidName() {
		return firstName.equals("") || lastName.equals("");
	}

	/**
	 * Returns whether or not a Tenant has a Spouse
	 * @return <code>true</code> if Tenant has a Spouse <code>false</code> if not
	 * */
	public boolean hasSpouse() {
		return !spouse.isDummy();
	}

	/**
	 * Removes Tenant's Spouse
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
	 * Gets first name of Tenant
	 * @return first name
	 * */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's first name
	 * @param firstName New first name
	 * */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets last name of Tenant
	 * @return last name
	 * */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets last name of Tenant
	 * @param lastName New last name
	 * */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's phone #
	 * @return phone #
	 * */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's phone #
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's Email
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's email
	 * @param email New email
	 * */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's date of birth
	 * @return Date of birth
	 * */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's date of birth
	 * @param dateOfBirth New date of birth
	 * */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's annual income
	 * @return annual income
	 * */
	public int getAnnualIncome() {
		return annualIncome;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's annual income
	 * @param annualIncome New annual income
	 * */
	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's ID #
	 * @return SSN
	 * */
	public String getSSN() {
		return SSN;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's ID #
	 * @param sSN New idn
	 * */
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's monthly rent
	 * @return rent
	 * */
	public double getRent() {
		return rent;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's monthly rent payment
	 * @param rent New rent amount
	 * */
	public void setRent(double rent) {
		this.rent = rent;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's # of children
	 * @return # of children
	 * */
	public int getNumChildren() {
		return numChildren;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's # of children
	 * @param numChildren New # of children
	 * */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's move in date
	 * @return move in date
	 * */
	public Date getMovInDate() {
		return movInDate;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's move in date
	 * @param movInDate New move in date
	 * */
	public void setMovInDate(Date movInDate) {
		this.movInDate = movInDate;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's move out date
	 * @return move out date
	 * */
	public Date getMovOutDate() {
		return movOutDate;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's move out date
	 * @param movOutDate New move out date
	 * */
	public void setMovOutDate(Date movOutDate) {
		this.movOutDate = movOutDate;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets whether the Tenant is evicted
	 * @return annual income
	 * */
	public boolean isEvicted() {
		return slatedForEviction;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets whether the Tenant was evicted or not
	 * @param isEvicted New value
	 * */
	public void setEvicted(boolean isEvicted) {
		this.slatedForEviction = isEvicted;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the reason for the Tenant's eviction
	 * @return eviction reason
	 * */
	public String getEvictReason() {
		return evictReason;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the reason for the Tenant's eviction
	 * @param evictReason New text
	 * */
	public void setEvictReason(String evictReason) {
		this.evictReason = evictReason;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's Spouse (if any)
	 * @return spouse
	 * */
	public Spouse getSpouse() {
		return spouse;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's Spouse object
	 * @param spouse New Spouse
	 * */
	public void setSpouse(Spouse spouse) {
		this.spouse = spouse;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's first Emergency Contact
	 * */
	public EContact getContact1() {
		return contact1;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's first Emergency Contact
	 * */
	public void setContact1(EContact contact1) {
		this.contact1 = contact1;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's second Emergency Contact
	 * */
	public EContact getContact2() {
		return contact2;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's second Emergency Contact
	 * */
	public void setContact2(EContact contact2) {
		this.contact2 = contact2;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's Invoice list
	 * @return invoice list
	 * */
	public List<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's Invoice list
	 * @param invoices New Invoice list
	 * */
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets list of Inspections related to a Tenant
	 * @return inspections
	 * */
	public List<NoteLog> getInspections() {
		return inspections;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's Inspection list
	 * @param inspections New Inspection list
	 * */
	public void setInspections(List<NoteLog> inspections) {
		this.inspections = inspections;
	}

	//------------------------------------------------------------
	//------------------------------------------------------------
}
