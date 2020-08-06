package com.graham.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.database.tables.subTables.Insurance;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

/**
 * Apartment object
 * <p></p>
 * Records information of owned Apartments as well as contain the Lists for
 * Insurance, Bill, and Issue
 *
 * @see Table
 * @see Insurance
 * @see Bill
 * @see NoteLog
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Apartment extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/***/
	private Image image;

	/**
	 * Name of Apartment
	 * */
	private String address;

	/**
	 * Name of City
	 * */
	private String city;

	/**
	 * Name of State
	 * */
	private String state;

	/**
	 * Zip-Code String
	 * */
	private String zipCode;

	/**
	 * # of occupants the Apartment can hold
	 * */
	private int capacity;

	/**
	 * # of Tenants currently within the Apartment
	 * */
	private int numTenants;

	// Sub-tables
	/**
	 * List of Insurances
	 * (TO BE REMOVED)
	 * */
	private List<Insurance> insurances;

	/**
	 * List of Issues
	 * */
	private List<NoteLog> issues;

	/**
	 * List of Bills
	 * */
	private List<Bill> bills;

//	 /***/
//	 private List<Room> rooms;

	/**
	 * */
	public static final Comparator<Apartment> APT_BY_ADDRESS = Comparator.comparing(Apartment::getAddress);

	/**
	 * Default constructor:
	 * */
	public Apartment() {
		this(0,"","","","",0);
	}

	/**
	 * Dummy Apartment Constructor
	 * */
	public Apartment(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	/**
	 * Full constructor:
	 * Creates Apartment with existing capacity
	 * @param id Primary Key of the Apartment
	 * @param address address of the Apartment
	 * @param city city the Apartment is located in
	 * @param state state the Apartment is located in
	 * @param zipCode zip-code of the Apartment
	 * @param capacity Apartment's occupant capacity
	 * */
	public Apartment(int id, String address, String city, String state, String zipCode, int capacity) {
		super(id);
		image = new Image("com/graham/apartmate/ui/res/Apartmentimg_small.png");
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.capacity = capacity;
		numTenants = 0;

		insurances = new ArrayList<>();
		issues = new ArrayList<>();
		bills = new ArrayList<>();
		// rooms = new ArrayList<>();
	}

	/**
	 * Gives all identifying information for the Apartment
	 * @return id & location data
	 * */
	@Override
	public String toString() {
		return String.format("Apartment %d %s %s %s %s", super.getId(),
				address, city, state, zipCode);
	}

	/***/
	@Override
	public String getGenericName() {
		return address;
	}

	/***/
	@Override
	public DBTables getTableType() {
		return DBTables.APARTMENTS;
	}

	/***/
	@Override
	public Image getImage() {
		return image;
	}

	/**
	 * Gives location data of the Apartment
	 * @return address, city, state, & zipcode
	 * */
	public String getLocation() {
		return address + " " + city + "," + state + " " + zipCode;
	}

	/**
	 * Increments the number of Tenants within the Apartment and returns such
	 * @return numTenants + 1
	 * */
	public int incrementNumTenants() {
		return ++numTenants;
	}

	/**
	 * Decrements the number of Tenants within the Apartment and returns such
	 * @return numTenants - 1
	 * */
	public int decrementNumTenants() {
		if (numTenants > 0) {
			return --numTenants;
		}

		return -1;
	}

//	/***/
//	public boolean addBill(Bill bill) {
//		return bills.add(bill);
//	}
//
//	/***/
//	public boolean removeBill(Bill bill) {
//		return bills.remove(bill);
//	}

	// *************************************************************
	// General getters and setters
	/***/
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the address of the Apartment
	 * @return address
	 * */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the address
	 * @param address New Address
	 * */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the city the Apartment is located in
	 * @return city
	 * */
	public String getCity() {
		return city;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the city
	 * @param city New city
	 * */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the state the Apartment is located in
	 * @return state
	 * */
	public String getState() {
		return state;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the state
	 * @param state New State
	 * */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the zip-code of the Apartment
	 * @return zip-code
	 * */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the zip-code
	 * @param zipCode New zipCode
	 * */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the capacity of the Apartment
	 * @return capacity
	 * */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the capacity
	 * @param capacity New capacity
	 * */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the number of Tenants in the Apartment
	 * @return # of Tenants
	 * */
	public int getNumTenants() {
		return numTenants;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the # of Tenants
	 * @param numTenants New # of Tenants
	 * */
	public void setNumTenants(int numTenants) {
		this.numTenants = numTenants;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the list of Insurances related to the Apartment
	 * @return Insurance list
	 * */
	public List<Insurance> getInsurances() {
		return insurances;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Insurance list
	 * @param insurances New Insurance list
	 * */
	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the list of Issues related to the Apartment
	 * @return Issue list
	 * */
	public List<NoteLog> getIssues() {
		return issues;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Issue list
	 * @param issues New Issue list
	 * */
	public void setIssues(List<NoteLog> issues) {
		this.issues = issues;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the list of Bills
	 * @return bill list
	 * */
	public List<Bill> getBills() {
		return bills;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the list of Bills
	 * @param bills New Bill list
	 * */
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

//	public List<Room> getRooms() { return rooms; }
//
//	public void setRooms(List<Room> rooms) { this.rooms = rooms; }

	// *************************************************************
}
