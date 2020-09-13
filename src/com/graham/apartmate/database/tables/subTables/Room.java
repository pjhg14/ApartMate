package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Room Object:
 * <p>
 * Records the information of a Room of an Apartment
 *
 * @since Capstone (0.8)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr
 */
public class Room extends Table {

	//--------------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Serializable element
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The room's name, i.e: 2A, 1C, etc.
	 * */
	private final SimpleStringProperty roomName;

	/**
	 * Holds the occupying tenant, if any
	 */
	private Tenant occupant;

	/**
	 * List of expectant candidates for the room
	 * */
	private final ObservableList<Candidate> expectantCandidates;

	/**
	 * List of the Tenant's inspections
	 * */
	private final ObservableList<NoteLog> inspections;
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//Constructors////////////////////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Default Constructor
	 * */
	public Room() {
		this(0,0);
	}

	/**
	 * Constructor w/o room name nor initial occupant
	 * */
	public Room(int id, int fk) {
		this(id, fk, 0, "");
	}

	/**
	 * Constructor w/o room name
	 * */
	public Room(int id, int fk, int fk2) {
		this(id, fk, fk2,"");
	}

	/**
	 * Constructor w/o initial occupant (no fk)
	 * */
	public Room(int id, int fk, String roomName) {
		this(id, fk,0, roomName);
	}

	/**
	 * Full Constructor
	 * */
	public Room(int id, int fk, int fk2, String roomName) {
		super(id, fk, fk2);
		this.roomName = new SimpleStringProperty(roomName);

		expectantCandidates = FXCollections.observableArrayList();
		inspections = FXCollections.observableArrayList();
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//Overrided & Utility Methods/////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Gets the main identifying name of an instance of a Table
	 *
	 * @return Table's "unique" name
	 */
	@Override
	public String getGenericName() {
		return getRoomName();
	}

	/**
	 * Gets the type of Table in question
	 *
	 * @return table type
	 */
	@Override
	public DBTables getTableType() {
		return DBTables.ROOM;
	}

	/**
	 * Tells whether or not this room has an occupant
	 * */
	public boolean hasOccupant() {
		return getFk2() != 0 || occupant != null;
	}

	/**
	 * Adds an occupant to the Room
	 * */
	public boolean addOccupant(Tenant tenant) {
		if (occupant != null) {
			return false;
		}

		occupant = tenant;
		return true;
	}

	/**
	 * Removes the occupant from the Room
	 * */
	public boolean removeOccupant() {
		if (occupant == null) {
			return false;
		}

		occupant = null;
		return true;
	}

	/**
	 * Adds an expectant candidate to the Room
	 * */
	public boolean addCandidate(Candidate candidate) {
		return expectantCandidates.add(candidate);
	}

	/**
	 * Removes an expectant candidate from the Room
	 * */
	public boolean removeCandidate(Candidate candidate) {
		return expectantCandidates.remove(candidate);
	}

	/**
	 * Adds an inspection to the Room
	 * */
	public boolean addInspection(NoteLog noteLog) {
		return inspections.add(noteLog);
	}

	/**
	 * Removes the inspection from the Room
	 * */
	public boolean removeInspection(NoteLog noteLog) {
		return inspections.remove(noteLog);
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------

	//--------------------------------------------------------------------
	//General Getters and setters/////////////////////////////////////////
	//--------------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gets the room's name
	 * @return the room's name
	 * */
	public String getRoomName() {
		return roomName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the room's name
	 * <p>
	 * To be formatted as: 2A, 1C, etc.
	 * */
	public void setRoomName(String roomName) {
		this.roomName.set(roomName);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the room name field property
	 * @return room name property
	 * */
	public SimpleStringProperty roomNameProperty() {
		return roomName;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the Tenant occupying this Room
	 * @return current occupant
	 * */
	public Tenant getOccupant() {
		return occupant;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the list of expectant Candidates
	 * @return expectant Candidate list
	 * */
	public ObservableList<Candidate> getExpectantCandidates() {
		return expectantCandidates;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets list of Inspections
	 * @return inspections
	 * */
	public ObservableList<NoteLog> getInspections() {
		return FXCollections.unmodifiableObservableList(inspections);
	}
	//--------------------------------------------------------------------
	//--------------------------------------------------------------------
}
