package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.libraries.FXMLLocation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Living Space Object:
 * <p>
 * Records the information of a Living Space of an Apartment
 *
 * @since Capstone (0.8)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr
 */
public class LivingSpace extends Table {
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
	private final SimpleStringProperty sectionName;

	/***/
	private Room room;

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
	public LivingSpace() {
		this(0,0,0,"");
	}

	/**
	 * Constructor w/o room name nor initial occupant
	 * */
	public LivingSpace(int id, int fk) {
		this(id, fk, 0, "");
	}

	/**
	 * Constructor w/o room name
	 * */
	public LivingSpace(int id, int fk, int fk2) {
		this(id, fk, fk2,"");
	}

	/**
	 * Constructor w/o initial occupant (no fk2)
	 * */
	public LivingSpace(int id, int fk, String sectionName) {
		this(id, fk,0, sectionName);
	}

	/**
	 * Full Constructor
	 *
	 * @param id id of Living Space
	 * @param fk Id of Apartment the Living Space is a part of
	 * @param fk2 Id of Tenant residing (0 for no Tenant)
	 * @param sectionName name of Living Space
	 * */
	public LivingSpace(int id, int fk, int fk2, String sectionName) {
		super(id, fk, fk2);
		image = new Image("com/graham/apartmate/ui/res/ApartmentImg_small.png");
		this.sectionName = new SimpleStringProperty(sectionName);

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
	 * <p>
	 * In this case, does the same as getting this living space's section name
	 * @return Table's "unique" name
	 */
	@Override
	public String getGenericName() {
		return getSectionName();
	}

	/**
	 * Gets the type of Table in question
	 *
	 * @return table type
	 */
	@Override
	public DBTables getTableType() {
		return DBTables.LIVING_SPACE;
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
	public String getSectionName() {
		return sectionName.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the room's name
	 * <p>
	 * To be formatted as: 2A, 1C, etc.
	 * */
	public void setSectionName(String sectionName) {
		this.sectionName.set(sectionName);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the room name field property
	 * @return room name property
	 * */
	public SimpleStringProperty sectionNameProperty() {
		return sectionName;
	}

	public Room getRoomType() {
		return room;
	}

	public void setRoomType(Room room) {
		this.room = room;
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
