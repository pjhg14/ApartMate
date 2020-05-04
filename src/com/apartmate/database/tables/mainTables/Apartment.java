package com.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.tables.subTables.*;
import com.apartmate.main.Main;
//import com.apartmate.database.tables.subTables.Room;

/**
 * Apartment object
 * <p></p>
 * Records information of owned Apartments as well as contain the Lists for
 * Insurance, Issue, and Inspection
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Finish Javadoc's of setters
public class Apartment extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

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

	// private List<Room> rooms;

	/**
	 * Default constructor:
	 * Creates a dummy Apartment object
	 * */
	public Apartment() {
		this(0,"","","","",0);
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
	 * @return id, location data, capacity, and # of tenants
	 * */
	@Override
	public String toString() {
		return "Apartment " + super.getId() + " " + address + " " + city + " " + state + " " + zipCode + " " + capacity
				+ " " + numTenants;
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

	// *************************************************************
	// General getters and setters
	/**
	 * Getter:
	 * Gives the address of the Apartment
	 * @return address
	 * */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter:
	 * Sets the address
	 * @param address ...
	 * */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter:
	 * Gives the city the Apartment is located in
	 * @return city
	 * */
	public String getCity() {
		return city;
	}

	/**
	 * Setter:
	 * Sets the city
	 * @param city ...
	 * */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Getter:
	 * Gives the state the Apartment is located in
	 * @return state
	 * */
	public String getState() {
		return state;
	}

	/**
	 * Setter:
	 * Sets the state
	 * @param state ...
	 * */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Getter:
	 * Gives the zip-code of the Apartment
	 * @return zip-code
	 * */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Setter:
	 * Sets the zip-code
	 * @param zipCode ...
	 * */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Getter:
	 * Gives the capacity of the Apartment
	 * @return capacity
	 * */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Setter:
	 * Sets the capacity
	 * @param capacity ...
	 * */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Getter:
	 * Gives the number of Tenants in the Apartment
	 * @return # of Tenants
	 * */
	public int getNumTenants() {
		return numTenants;
	}

	/**
	 * Setter:
	 * Sets the # of Tenants
	 * @param numTenants ...
	 * */
	public void setNumTenants(int numTenants) {
		this.numTenants = numTenants;
	}

	/**
	 * Getter:
	 * Gives the list of Insurances related to the Apartment
	 * @return Insurance list*/
	public List<Insurance> getInsurances() {
		return insurances;
	}

	/**
	 * Setter:
	 * Sets Insurance list
	 * @param insurances ...
	 * */
	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	/**
	 * Getter:
	 * Gives the list of Issues related to the Apartment
	 * @return Issue list
	 * */
	public List<NoteLog> getIssues() {
		return issues;
	}

	/**
	 * Setter:
	 * Sets Issue list
	 * @param issues ...
	 * */
	public void setIssues(List<NoteLog> issues) {
		this.issues = issues;
	}

	/**
	 * Getter:
	 * Gets the list of Bills
	 * @return bill list
	 * */
	public List<Bill> getBills() {
		return bills;
	}

	/**
	 * Setter:
	 * Sets the list of Bills
	 * @param bills ...
	 * */
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

//	public List<Room> getRooms() { return rooms; }
//
//	public void setRooms(List<Room> rooms) { this.rooms = rooms; }

	// *************************************************************
}
