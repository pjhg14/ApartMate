package com.graham.apartmate.database.tables.mainTables;

import java.util.Comparator;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.Bill;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.database.tables.subTables.Room;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Apartment object
 * <p></p>
 * Records information of owned Apartments as well as contain the Lists for
 * Insurance, Bill, and Issue
 *
 * @see Table
 * @see Bill
 * @see NoteLog
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Apartment extends Table {

	//--------------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of Apartment
	 * */
	private final SimpleStringProperty address;

	/**
	 * Name of City
	 * */
	private final SimpleStringProperty city;

	/**
	 * Name of State
	 * */
	private final SimpleStringProperty state;

	/**
	 * Zip-Code String
	 * */
	private final SimpleStringProperty zipCode;

	// Sub-tables
	/**
	 * List of Bills
	 * */
	private ObservableList<Bill> bills;

	/**
	 * List of issues
	 * */
	private ObservableList<NoteLog> issues;

	/**
	 * List of rooms
	 * */
	private ObservableList<Room> rooms;
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//Order Constants/////////////////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Sorts Apartment by address
	 * */
	public static final Comparator<Apartment> APT_BY_ADDRESS = Comparator.comparing(Apartment::getAddress);

	/**
	 * Sorts Apartment by city
	 * */
	public static final Comparator<Apartment> APT_BY_CITY = Comparator.comparing(Apartment::getCity);

	/**
	 * Sorts Apartment by state
	 * */
	public static final Comparator<Apartment> APT_BY_STATE = Comparator.comparing(Apartment::getState);

	/**
	 * Sorts Apartment by capacity (number of rooms)
	 * */
	public static final Comparator<Apartment> APT_BY_CAPACITY = Comparator.comparing(Apartment::getCapacity);
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//Constructors////////////////////////////////////////////////////////
	//--------------------------------------------------------------------
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
	 * @param initialCapacity Apartment's occupant capacity
	 * */
	public Apartment(int id, String address, String city, String state, String zipCode, int initialCapacity) {
		super(id);
		image = new Image("com/graham/apartmate/ui/res/Apartmentimg_small.png");
		this.address = new SimpleStringProperty(address);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zipCode = new SimpleStringProperty(zipCode);

		bills = FXCollections.observableArrayList();
		rooms = FXCollections.observableArrayList();
		for (int i = 0; i < initialCapacity; i++) {
			rooms.add(new Room());
		}
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//Overrided & Utility Methods/////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Gives all identifying information for the Apartment
	 * @return id & location data
	 * */
	@Override
	public String toString() {
		return String.format("Apartment %d %s %s %s %s", super.getId(), address, city, state, zipCode);
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 * @return Table's "generic" name
	 * */
	@Override
	public String getGenericName() {
		return getAddress();
	}

	/**
	 * Gets the type of Table in question
	 * @return table type
	 * */
	@Override
	public DBTables getTableType() {
		return DBTables.APARTMENTS;
	}

	/**
	 * Gives location data of the Apartment
	 * @return address, city, state, & zipcode
	 * */
	public String getLocation() {
		return getAddress() + " " + getCity() + "," + getState() + " " + getZipCode();
	}

	/**
	 * Gives the number of Tenants in the Apartment
	 * @return # of Tenants
	 * */
	public int getNumTenants() {
		int numTenants = 0;

		for (Room room : rooms) {
			if (room.hasOccupant()){
				numTenants++;
			}
		}

		return numTenants;
	}

	/**
	 * Gets the capacity (number of rooms) in an Apartment
	 * */
	public int getCapacity() {
		return rooms.size();
	}

	/***/
	public boolean addRoom(Room room) {
		return rooms.add(room);
	}

	/***/
	public boolean removeRoom(Room room) {
		return rooms.remove(room);
	}

	/**
	 * Adds a bill to the bill list
	 * @return whether or not the bill was successfully added to the list
	 * */
	public boolean addBill(Bill bill) {
		return bills.add(bill);
	}

	/**
	 * Removes a bill from the bill list
	 * @return whether or not the bill was successfully removed from the list
	 * */
	public boolean removeBill(Bill bill) {
		return bills.remove(bill);
	}

	/**
	 * Adds an Issue to the issue list
	 * @return whether or not the issue was successfully added to the list
	 * */
	public boolean addIssue(NoteLog issue) {
		return issues.add(issue);
	}

	/**
	 * Removes an Issue from the issue list
	 * @return whether or not the issue was successfully removed from the list
	 * */
	public boolean removeIssue(NoteLog issue) {
		return issues.remove(issue);
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//General Getters and setters/////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gives the address of the Apartment
	 * @return address
	 * */
	public String getAddress() {
		return address.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the address
	 * @param address New Address
	 * */
	public void setAddress(String address) {
		this.address.set(address);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets address field property
	 * @return address property
	 * */
	public SimpleStringProperty addressProperty() {
		return address;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the city the Apartment is located in
	 * @return city
	 * */
	public String getCity() {
		return city.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the city
	 * @param city New city
	 * */
	public void setCity(String city) {
		this.city.set(city);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets city field property
	 * @return city property
	 * */
	public SimpleStringProperty cityProperty() {
		return city;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the state the Apartment is located in
	 * @return state
	 * */
	public String getState() {
		return state.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the state
	 * @param state New State
	 * */
	public void setState(String state) {
		this.state.set(state);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets state field property
	 * @return state property
	 * */
	public SimpleStringProperty stateProperty() {
		return state;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the zip-code of the Apartment
	 * @return zip-code
	 * */
	public String getZipCode() {
		return zipCode.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the zip-code
	 * @param zipCode New zipCode
	 * */
	public void setZipCode(String zipCode) {
		this.zipCode.set(zipCode);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets zip code field property
	 * @return zip code property
	 * */
	public SimpleStringProperty zipCodeProperty() {
		return zipCode;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the list of Bills
	 * @return bill list
	 * */
	public ObservableList<Bill> getBills() {
		return FXCollections.unmodifiableObservableList(bills);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the list of Bills
	 * @param bills New Bill list
	 * */
	public void setBills(ObservableList<Bill> bills) {
		this.bills = bills;
	}

	/**
	 * Getter:
	 * <p>
	 * Gives the list of Issues related to the room of the Apartment
	 * @return Issue list
	 * */
	public ObservableList<NoteLog> getIssues() {
		return FXCollections.unmodifiableObservableList(issues);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Issue list
	 * @param issues New Issue list
	 * */
	public void setIssues(ObservableList<NoteLog> issues) {
		this.issues = issues;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the list of Rooms
	 * */
	public ObservableList<Room> getRooms() {
		return FXCollections.unmodifiableObservableList(rooms);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the list of rooms
	 * */
	public void setRooms(ObservableList<Room> rooms) {
		this.rooms = rooms;
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------
}
