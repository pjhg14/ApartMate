package com.graham.apartmate.database.dbMirror;

import java.sql.SQLException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.utilities.unordered.Heck;
import com.graham.apartmate.database.utilities.saving.LocalDBSaving;
import com.graham.apartmate.database.utilities.saving.SQLBridge;
import com.graham.apartmate.main.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains Lists of all tables and subTables (through their respective table)
 * to locally store and manipulate information
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @see Building
 * @see Tenant
 * @see Candidate
 * @see Contractor
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Finish modifying edit methods
//TODO: Find better way of writing orderById & orderByName
//TODO: Finish orderByDate
// change Lists to ObservableLists and migrate OLListeners from UI Controllers to here
public final class Database {

	/**
	 * Database Instance singleton: Acts as a mirror of saved data, whether that data
	 * Is stored online or locally
	 * */
	private static final Database INSTANCE = new Database();

	//------------------------------------------------------------------
	//Database status flags/////////////////////////////////////////////
	//------------------------------------------------------------------
	/**
	 * Contains whether or not a connection to previous data was made
	 * */
	private static boolean connected;

	/**
	 * Contains whether or not a connection to the server was successful
	 * */
	private static boolean online;

	/**
	 * Holds the current order:
	 * <p>
	 * <code>currentOrder[0]</code> holds ascending/descending order
	 * </p>
	 * <code>currentOrder[1]</code> holds order by id/name
	 * <p>
	 * Default: Ascending, By Id
	 * */
	private final int[] currentOrder = { 0, 2 };
	//------------------------------------------------------------------

	// Utility objects
	// Saving utilities
	/**
	 * Contains and manages server connections, querying, and loading
	 * */
	private final SQLBridge sqlBridge;

	/**
	 * Manages reading/writing to/of files locally stored on the computer
	 * */
	private final LocalDBSaving localSave;

	// Unordered utilities
	/**
	 * Manages all timed events an updates based on such
	 * */
	private final Heck events;

	// Main tables
	/**
	 * List of all Buildings
	 * */
	private ObservableList<Building> buildings;

	/**
	 * List of all Tenants
	 * */
	private ObservableList<Tenant> tenants;

	/**
	 * List of all Candidates
	 * */
	private ObservableList<Candidate> candidates;

	/**
	 * List of all Contractors
	 * */
	private ObservableList<Contractor> contractors;

	// Table pointers
	/***/
	private Table currentTable;

	/***/
	private Table previousTable;

	//Ledger
	/**
	 * Database general ledger
	 * <p>
	 * contains all modified Tables and their modifications (added, edited, deleted)
	 * </p>
	 * Utilized if auto-save is disabled (tbi) or if program odes not have access to online components
	 * */
	private Map<Table, String> ledger;

	/**
	 * Specifies the current Table type
	 * */
	private static DBTables currTable;

	/**
	 * Database constructor:
	 * <p>
	 * No other instances should exist
	 * */
	private Database() {
		buildings = FXCollections.observableArrayList();
		tenants = FXCollections.observableArrayList();
		candidates = FXCollections.observableArrayList();
		contractors = FXCollections.observableArrayList();

		sqlBridge = new SQLBridge();
		localSave = new LocalDBSaving();

		events = new Heck();
	}

	/**
	 * debug method:
	 * Not really sure what to do with this method...
	 * */
	private void debug() {
		if (online) {
			System.out.println("Database populated w/ existing sql data:\n" + toString());
		} else if (connected) {
			System.out.println("Database populated w/ existing local data:\n" + toString());
		} else {
			System.out.println("Existing data not found, database is empty");
		}
	}

	/**
	 * Overrided toString() method: prints values of all lists
	 * @return list values
	 * */
	@Override
	public String toString() {
		return "Buildings: " + buildings
				+ "\nTenants: " + tenants
				+ "\nCandidates: " + candidates
				+ "\nContractors: " + contractors;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Open/close methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Opens the connection to SQL through SQLBridge
	 * <p>
	 * If connection fails, attempt to load through local files
	 * <p>
	 * If no local files, initialize empty lists
	 * @return <code>true</code> if load was successful; <code>false</code> if not
	 * */
	public boolean open() {
		if (false) {	// Attempt to connect to Database; restore to sqlBridge.connect() later
			// populate tables using query
			onlineLoad();

			// move these to login button later
			connected = true;
			online = true;

			if (Main.DEBUG)
				debug();

			return true;
		} else if (false) {	//Attempt to load from local files; restore to localSave.createDir() later
			// populate tables using local save
			offlineLoad();


			// move these to login button later
			connected = true;
			online = false;

			if (Main.DEBUG)
				debug();

			return true;
		} else {	// If Offline and no local files exist, continue w/ no data
			System.out.println("Unable to populate tables: Database is empty");
			// move these to login button later
			connected = false;
			online = false;

			if (Main.DEBUG)
				debug();

			return false;
		}
	}

	//
	/**
	 * Method that Timer daemon will use to attempt to reconnect to the database
	 * @param username Username that will be used to attempt the connection
	 * @param pass Password of User
	 * */
	public void reConnect(String username, String pass) {
		if (!online) {
			sqlBridge.connect(username, pass);
		}
	}

	/**
	 * Saves the Database
	 * */
	public void save() {
		if (online)
			onlineSave();

		offlineSave();
	}

	/**
	 * Force load method:
	 * <p>
	 * Used when user wants to load from previous Database state
	 * <p>
	 * MUST be preceded with confirmation (possible massive loss of data)
	 * @param retry Whether this method has been attempted prior
	 * @return <code>true</code> if the load was successful, <code>false</code> otherwise
	 * */
	public boolean forceLoad(boolean retry) {
		//Clone current lists
		ObservableList<Building> cloneA = FXCollections.observableArrayList(buildings);
		ObservableList<Tenant> cloneT = FXCollections.observableArrayList(tenants);
		ObservableList<Candidate> cloneCa = FXCollections.observableArrayList(candidates);
		ObservableList<Contractor> cloneCo = FXCollections.observableArrayList(contractors);

		if (retry) {
			//Load
			offlineLoad();

			return true;
		} else {
			offlineLoad();
			if (buildings.isEmpty() || tenants.isEmpty() || candidates.isEmpty() || contractors.isEmpty()) {
				//If no data loaded, restore Lists
				buildings = cloneA;
				tenants = cloneT;
				candidates = cloneCa;
				contractors = cloneCo;
				return false;
			}

			return forceLoad(true);
		}
	}

	/**
	 * Saves lists and closes server connection
	 * */
	public void close() {
		save();

		if (online) {
			try {
				sqlBridge.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateOnlineDatabase() {
		for (Table table : ledger.keySet()) {
			switch (ledger.get(table)){
				case "Added":
					sqlBridge.insert(table);
					break;
				case "Deleted":
					sqlBridge.delete(table);
					break;
				case "Edited":
					sqlBridge.update(table);
					break;
				default:
					//Ummmmm???
			}
		}
	}

	//TODO: Move this method to a more appropriate place
	/**
	 * Gets the last Id of a Table list
	 * @param table the table in question
	 * @return Id of last indexed table
	 * */
	public int getLastID(DBTables table) {
		int id = 0;
		try {
			switch (table) {
				case BUILDINGS:
					id = buildings.get(buildings.size() - 1).getId();
					return id;
				case TENANTS:
					id = tenants.get(tenants.size() - 1).getId();
					return id;
				case CANDIDATES:
					id = candidates.get(candidates.size() - 1).getId();
					return id;
				case CONTRACTORS:
					id = contractors.get(contractors.size() - 1).getId();
					return id;
				case BILLS:
					for (Building building : buildings) {
						for (Bill bill : building.getBills()) {
							if (bill.getId() > id) {
								id = bill.getId();
							}
						}
					}
					return id;
				case ISSUES:
					for (Building building : buildings) {
						for (NoteLog issue : building.getIssues()) {
							if (issue.getId() > id) {
								id = issue.getId();
							}
						}
					}
					return id;
				case INSPECTIONS:
					for (Building building : buildings) {
						for (Apartment apartment : building.getApartments()) {
							for (NoteLog inspection : apartment.getInspections()) {
								if (inspection.getId() > id) {
									id = inspection.getId();
								}
							}
						}
					}
					return id;
				case ROOMMATE:
					for (Tenant tenant : tenants) {
						for (Occupant occupant : tenant.getOccupants()) {
							if (occupant.getId() > id) {
								id = occupant.getId();
							}
						}

					}

					for (Candidate candidate : candidates) {
						for (Occupant occupant : candidate.getOccupants()) {
							if (occupant.getId() > id) {
								id = occupant.getId();
							}
						}

					}
					return id;
				case ACCOUNT:
					for (Tenant tenant : tenants) {
						if (tenant.getAccount().getId() > id) {
							id = tenant.getAccount().getId();
						}
					}

					for (Contractor contractor : contractors) {
						if (contractor.getAccount().getId() > id) {
							id = contractor.getAccount().getId();
						}
					}

					for (Building building : buildings) {
						for (Bill bill : building.getBills()) {
							if (bill.getAccount().getId() > id) {
								id = bill.getAccount().getId();
							}
						}
					}
					return id;
				case PERSONALCONTACT:
					for (Tenant tenant : tenants) {
						if (tenant.getEContact1().getId() > id) {
							id = tenant.getEContact1().getId();
						}

						if (tenant.getEContact2().getId() > id) {
							id = tenant.getEContact2().getId();
						}
					}

					for (Candidate candidate : candidates) {
						if (candidate.getEContact1().getId() > id) {
							id = candidate.getEContact1().getId();
						}

						if (candidate.getEContact2().getId() > id) {
							id = candidate.getEContact2().getId();
						}
					}

					for (Contractor contractor : contractors) {
						if (contractor.getContact().getId() > id) {
							id = contractor.getContact().getId();
						}
					}
					return id;
				default:
					id = -1;
					throw new Exception("Invalid Table parameter");
			}
		} catch (Exception e) {
			if (Main.DEBUG)
				System.out.println(e.getMessage());

			return id;
		}
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// find methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	public String getResidency(Candidate candidate) {

		//iterate through all buildings
		for (Building building : buildings) {
			//iterate through building apartments
			for (Apartment apartment : building.getApartments()) {
				//check if apartment id = candidate/tenant fk
				if (apartment.getId() == candidate.getFk()) {
					//building found, return building, apartment
					return building.getAddress() + " " + building.getCity() + ", " + building.getState()
							+ "\n Apartment " + apartment.getSectionName();
				}
			}
		}

		return "ERROR: BUILDING NOT FOUND";
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// add methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Generic add method for main tables
	 * */
	public boolean add(Table instance) {
		switch (instance.getTableType()) {
			case BUILDINGS:
				return add((Building) instance);
			case TENANTS:
				return add((Tenant) instance);
			case CANDIDATES:
				return add((Candidate) instance);
			case CONTRACTORS:
				return add((Contractor) instance);
			default:
				return false;
		}
	}

	/**
	 * Adds a Building to the Database
	 * @param building Building to be added
	 * */
	public boolean add(Building building) {
		boolean added = buildings.add(building);
		Collections.sort(buildings);

		//Insert online method here

		return added;
	}

	/**
	 * Adds a Tenant to the Database; fails if the Tenant has no related Building
	 * @param tenant Tenant to be added
	 * @return <code>true</code> if Tenant was added, <code>false</code> otherwise
	 * */
	public boolean add(Tenant tenant) {
		for (Building building : buildings) {
			if (building.getId() == tenant.getFk()) {
				tenants.add(tenant);
				Collections.sort(tenants);

				//Insert online method here

				return true;
			}
			// Respective building not found; cycle
		}
		return false;

	}

	/**
	 * Adds a Candidate to the Database
	 * @param candidate Candidate to be added
	 * @return <code>true</code> if Candidate was added, <code>false</code> otherwise
	 * */
	public boolean add(Candidate candidate) {
		for (Building building : buildings) {
			if (building.getId() == candidate.getFk()) {
				candidates.add(candidate);
				Collections.sort(candidates);

				//Insert online method here

				return true;
			}
			// Respective building not found; cycle
		}
		return false;
	}

	/**
	 * Adds a Contractor to the Database
	 * @param contractor Contractor to be added
	 * @return <code>true</code> if Contractor was added, <code>false</code> otherwise
	 * */
	public boolean add(Contractor contractor) {
		for (Building building : buildings) {
			if (building.getId() == contractor.getFk()) {
				contractors.add(contractor);

				Collections.sort(contractors);

				//Insert online method here

				return true;
			}
			// Respective building not found; cycle
		}
		return false;

	}

	/**
	 * Adds an Issue or Inspection to the Database
	 * @param issInsp Issue or Inspection to be added
	 * @param table Selects the type of table (Valid Tables: Issue & Inspection)
	 * @return <code>true</code> if Candidate was added, <code>false</code> otherwise
	 * */
	public boolean add(NoteLog issInsp, DBTables table) {
		switch (table) {
			case INSPECTIONS:
				for (Building building : buildings) {
					for (Apartment apartment : building.getApartments()) {
						if (apartment.getId() == issInsp.getFk()) {
							apartment.getInspections().add(issInsp);
							Collections.sort(apartment.getInspections());

							//Insert online method here

							return true;
						}
					}
				}
				break;
			case ISSUES:
				for (Building building : buildings) {
					if (building.getId() == issInsp.getFk()) { // Find respective building
						building.getIssues().add(issInsp);
						Collections.sort(building.getIssues());

						//Insert online method here

						return true;
					}
				// Respective building not found; cycle
				}
				break;
			default:
				//Error catch here?
		}

		return false;
	}

	/**
	 * Adds a Bill to the Database
	 * @param bill Bill to be added
	 * @return <code>true</code> if Bill was added, <code>false</code> otherwise
	 * */
	public boolean add(Bill bill) {
		for (Building building : buildings) {
			if (building.getId() == bill.getFk()) { // Find respective Building
				building.getBills().add(bill);

				Collections.sort(building.getBills());

				//Insert online method here

				return true;
			}
			// Respective Building not found; cycle
		}
		return false;
	}

	//TODO: Add method for adding Account payment, dues, and credits

	//TODO: Add method for adding Personal Contact

	/**
	 * Adds a Spouse to the Database; uses table constants to select whether the added
	 * spouse goes to a particular tenant or candidate
	 * @param occupant The Spouse to add to the Database
	 * @param table Table the Spouse is related to (Valid tables: Tenants & Candidates)
	 * @return <code>true</code> if the spouse was added, <code>false</code> otherwise
	 * */
	public boolean add(Occupant occupant, DBTables table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if ((tenant.getId() == occupant.getFk()) && tenant.getOccupants() == null) {
					tenant.addRoomMate(occupant);

					//Insert online method here

					return true;
				}
			}
			break;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if ((candidate.getId() == occupant.getFk2()) && candidate.getOccupants() == null) {
					candidate.addRoomMate(occupant);

					//Insert online method here

					return true;
				}
			}
			break;
		default:
			System.out.println("Invalid table operand");
		}

		return false;
	}

	public boolean add(Apartment apartment) {
		for (Building building : buildings) {
			if (building.getId() == apartment.getFk()) {
				return building.addRoom(apartment);
			}
		}

		return false;
	}

	public boolean add(Lease lease) {
		for (Tenant tenant : tenants) {
			if (tenant.getId() == lease.getFk()) {
				tenant.getLease().renew(lease);

				return true;
			}
		}

		return false;
	}

	public boolean add(TransactionLog log, DBTables parent, boolean credit) {
		switch (parent) {
			case TENANTS:
				for (Tenant tenant : tenants) {
					if (tenant.getId() == log.getFk()) {
						if (credit) {
							return tenant.getAccount().addCreditFine(log);
						} else {
							return tenant.getAccount().addTransaction(log);
						}
					}
				}
				break;
			case CONTRACTORS:
				for (Contractor contractor : contractors) {
					if (contractor.getId() == log.getFk()) {
						if (credit) {
							return contractor.getAccount().addCreditFine(log);
						} else {
							return contractor.getAccount().addTransaction(log);
						}
					}
				}
				break;
			case BILLS:
				for (Building building : buildings) {
					for (Bill bill : building.getBills()) {
						if (bill.getId() == log.getFk()) {
							if (credit) {
								return bill.getAccount().addCreditFine(log);
							} else {
								return bill.getAccount().addTransaction(log);
							}
						}
					}
				}
				break;
			default:
				if (Main.DEBUG)
					System.out.println("invalid Table operand");
		}

		return false;
	}

	public boolean add(Contact contact, DBTables parent) {
		switch (parent) {
			case TENANTS:
				for (Tenant tenant : tenants) {
					if (tenant.getId() == contact.getFk()) {
						if (tenant.getEContact1() == null) {
							tenant.setEContact1(contact);
							return true;
						} else if (tenant.getEContact2() == null) {
							tenant.setEContact2(contact);
							return true;
						}
					}
				}
				break;
			case CANDIDATES:
				for (Candidate candidate : candidates) {
					if (candidate.getId() == contact.getFk()) {
						if (candidate.getEContact1() == null) {
							candidate.setEContact1(contact);
							return true;
						} else if (candidate.getEContact2() == null) {
							candidate.setEContact2(contact);
							return true;
						}
					}
				}
				break;
			case CONTRACTORS:
				for (Contractor contractor : contractors) {
					if (contractor.getId() == contact.getFk()) {
						if (contractor.getContact() == null) {
							contractor.setContact(contact);
							return true;
						}
					}
				}
				break;
			default:
				if (Main.DEBUG)
					System.out.println("invalid Table operand");
		}

		return false;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// remove methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Generic remove method for main tables
	 * */
	public boolean remove(Table instance) {
		switch (instance.getTableType()) {
			case BUILDINGS:
				return remove((Building) instance);
			case TENANTS:
				return remove((Tenant) instance);
			case CANDIDATES:
				return remove((Candidate) instance);
			case CONTRACTORS:
				return remove((Contractor) instance);
			default:
				return false;
		}
	}

	/**
	 * Removes a Building from the Database
	 * @param building Building to be removed
	 * @return <code>true</code> if the Building was removed <code>false</code> otherwise
	 * */
	public boolean remove(Building building) {
		try {
			if (buildings.remove(building)) {
				// Remove all main tables related to specific building
				tenants.removeIf(t -> (t.getFk() == building.getId()));
				candidates.removeIf(can -> (can.getFk() == building.getId()));
				contractors.removeIf(cont -> (cont.getFk() == building.getId()));

				//Insert online method here

				return true;
			}

			return false;
		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Building");

			uoe.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an Tenant from the Database
	 * @param tenant Tenant to be removed
	 * @return <code>true</code> if the Tenant was removed <code>false</code> otherwise
	 * */
	public boolean remove(Tenant tenant) {
		try {
			if (tenants.remove(tenant)) {
				for (Building building : buildings) {
					for (Apartment apartment : building.getApartments()) {
						if (apartment.getTenant().equals(tenant)) {
							//Insert online method here

							return apartment.removeTenant();
						}
					}
				}
			}

			return false;
		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Tenant");

			uoe.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an Candidate from the Database
	 * @param candidate Candidate to be removed
	 * @return <code>true</code> if the Candidate was removed <code>false</code> otherwise
	 * */
	public boolean remove(Candidate candidate) {
		try {
			//Insert online method here
			return candidates.remove(candidate);

		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Candidate");

			uoe.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an Contractor from the Database
	 * @param contractor Contractor to be removed
	 * @return <code>true</code> if the Contractor was removed <code>false</code> otherwise
	 * */
	public boolean remove(Contractor contractor) {
		try {
			//Insert online method here
			return contractors.remove(contractor);

		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Contractor");

			uoe.printStackTrace();
			return false;
		}

	}

	/**
	 * Removes an Issue or Inspection from the Database
	 * @param issInsp Issue or Inspection to be removed
	 * @param table Selects the type of table (Valid Tables: Issue & Inspection)
	 * @return <code>true</code> if the Issue or Inspection was removed <code>false</code> otherwise
	 * */
	public boolean remove(NoteLog issInsp, DBTables table) {
		try {
			switch (table) {
				case INSPECTIONS:
					for (Building building : buildings) {
						for (Apartment apartment : building.getApartments()) {
							if (apartment.getInspections().remove(issInsp)) {
								//Insert online method here

								return true;
							}
						}

					}
					break;
				case ISSUES:
					for (Building building : buildings) {
						if (building.getIssues().remove(issInsp)) {
							//Insert online method here

							return true;
						}
					}
					break;
				default:
					//sout here
			}

			return false;
		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing NoteLog");

			uoe.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an Bill from the Database
	 * @param bill Bill to be removed
	 * @return <code>true</code> if the Bill was removed <code>false</code> otherwise
	 * */
	public boolean remove(Bill bill){
		try {
			for (Building building : buildings) {
				if (building.getBills().remove(bill)) {
					//Insert online method here

					return true;
				}
			}
			return false;
		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Bill");

			uoe.printStackTrace();
			return false;
		}
	}

	//TODO: Add remove method for Account payments, dues, and credits

	//TODO: Add method for removing Personal Contact

	/**
	 * Removes an Spouse from the Database
	 * @param occupant Spouse to be removed
	 * @param table Table the Spouse is related to (Valid tables: Tenants & Candidates)
	 * @return <code>true</code> if the Issue or Inspection was removed <code>false</code> otherwise
	 * */
	public boolean remove(Occupant occupant, DBTables table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if (tenant.getId() == occupant.getFk()) {
					tenant.setSpouse(null);

					//Insert online method here

					return true;
				}
			}
			break;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if (candidate.getId() == occupant.getFk2()) {
					candidate.setSpouse(null);

					//Insert online method here

					return true;
				}
			}
			break;
		default:
			if (Main.DEBUG)
				System.out.println("Invalid table operand");

		}

		return false;
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// edit methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Generic edit method for main tables
	 * */
	public boolean edit(Table instance) {
		switch (instance.getTableType()) {
			case BUILDINGS:
				return edit((Building) instance);
			case TENANTS:
				return edit((Tenant) instance);
			case CANDIDATES:
				return edit((Candidate) instance);
			case CONTRACTORS:
				return edit((Contractor) instance);
			default:
				return false;
		}
	}

	/**
	 * Edits a Building that already exists in the Database
	 * @param building Building to edit
	 * @return <code>true</code> if Building exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Building building) {
		int index = buildings.indexOf(building);

		if (index > 0) {
			building.setEdited(true);
			buildings.set(index, building);

			events.upDateModified(building);

			//Insert online method here

			return true;
		}

		return false;

	}

	/**
	 * Edits Tenant details that already exists in the Database
	 * @param tenant Tenant to edit
	 * @return <code>true</code> if Tenant exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Tenant tenant) {
		int index = tenants.indexOf(tenant);

		if (index > 0) {
			tenant.setEdited(true);
			tenants.set(index, tenant);

			events.upDateModified(tenant);

			//Insert online method here

			return true;
		}

		return false;
	}

	/**
	 * Edits Candidate details that already exists in the Database
	 * @param candidate Candidate to edit
	 * @return <code>true</code> if Candidate exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Candidate candidate) {
		int index = candidates.indexOf(candidate);

		if (index > 0) {
			candidate.setEdited(true);
			candidates.set(index, candidate);

			events.upDateModified(candidate);

			//Insert online method here

			return true;
		}

		return false;
	}

	/**
	 * Edits Contractor details that already exists in the Database
	 * @param contractor Contractor to edit
	 * @return <code>true</code> if Contractor exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Contractor contractor) {
		int index = contractors.indexOf(contractor);

		if (index > 0) {
			contractor.setEdited(true);
			contractors.set(index, contractor);

			events.upDateModified(contractor);

			//Insert online method here

			return true;
		}

		return false;
	}

	/**
	 * Edits Spouse details that already exists in the Database
	 * @param occupant Spouse to edit
	 * @param table Selects the related Table (Valid Tables: Tenants & Candidates)
	 * @return <code>true</code> if Spouse exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Occupant occupant, DBTables table) {
		switch (table){
			case TENANTS:
				for (Tenant tnant : tenants) {
					if (tnant.getOccupants().equals(occupant)) {
						occupant.setEdited(true);
						tnant.removeRoomMate(occupant);

						//Insert online method here

						return true;
					}
				}
				break;
			case CANDIDATES:
				for (Candidate cand : candidates) {
					if (cand.getOccupants().equals(occupant)) {
						occupant.setEdited(true);
						cand.removeRoomMate(occupant);

						if (online) {
							sqlBridge.update(occupant,table);
							occupant.setEdited(false);
						}

						return true;
					}
				}
				break;
			default:
				//sout here
		}

		return false;
	}

	/**
	 * Edits Issue or Inspection details that already exist in the Database
	 * @param issInsp Issue or Inspection to edit
	 * @param table Selects the type of table (Valid Tables: Issue & Inspection)
	 * @return <code>true</code> if Issue or Inspection exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(NoteLog issInsp, DBTables table) {
		switch (table) {
			case INSPECTIONS:
				for (Building building : buildings) {
					for (Apartment apartment : building.getApartments()) {
						int index = apartment.getInspections().indexOf(issInsp);

						if (index > 0) {
							issInsp.setEdited(true);
							apartment.getInspections().set(index,issInsp);

							events.upDateModified(issInsp);

							//Insert online method here

							return true;
						}
					}


				}
				break;
			case ISSUES:
				for (Building building : buildings) {
					int index = building.getIssues().indexOf(issInsp);

					if (index > 0) {
						issInsp.setEdited(true);
						building.getIssues().set(index,issInsp);

						events.upDateModified(issInsp);

						//Insert online method here

					return true;
				}
			}
				break;
			default:
				break;
		}


		return false;
	}

	/**
	 * Edits Bill details that already exists in the Database
	 * @param bill Bill to edit
	 * @return <code>true</code> if Bill exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Bill bill) {
		for (Building building : buildings) {
			int index = building.getBills().indexOf(bill);

			if (index > 0) {
				bill.setEdited(true);
				building.getBills().set(index, bill);

				events.upDateModified(bill);

				//Insert online method here

				return true;
			}
		}

		return false;
	}

	//TODO: Add edit method for Account payment, dues, and credits

	//TODO: Add method for editing Personal Contact

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// List Ordering
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Sets ascending/descending order
	 * @param order Type of sorting
	 * */
	public void setSortingOrder(DBSorting order) {
		switch (order) {
			case ASCENDING:
				orderAscending();
				break;
			case DESCENDING:
				orderDescending();
				break;
			default:
				// Display error: invalid order
				System.out.println("Invalid order constant");
				return;
		}
		currentOrder[0] = order.orderNumber;
	}

	// Sets the order by the order_by constants
	/**
	 * Sets ordering by a specific Table identifying field
	 * @param orderBy Type to order by
	 * */
	public void setOrderBy(DBSorting orderBy) {
		switch (orderBy) {
			case ORDER_BY_ID: // Id (default)
				orderById();
				break;
			case ORDER_BY_NAME: // Name
				orderByName();
				break;
			case ORDER_BY_DATE:
				break;
			default:
				System.out.println("Invalid constant");
				return;
		}
		currentOrder[1] = orderBy.getOrderNumber();
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Private Utilities
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// List Listeners
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	/**
	 * Loads Database elements from server
	 * */
	private void onlineLoad() {
		buildings = FXCollections.observableArrayList(sqlBridge.queryBuildings());
		tenants = FXCollections.observableArrayList(sqlBridge.queryTenants());
		candidates = FXCollections.observableArrayList(sqlBridge.queryCandidates());
		contractors = FXCollections.observableArrayList(sqlBridge.queryContractors());

		// Extraneous sort, to make sure that all lists are in order
		orderAscending();
	}

	/**
	 * Loads Database elements from local files
	 * */
	private void offlineLoad() {
		buildings = FXCollections.observableArrayList(localSave.loadBuildings());
		tenants = FXCollections.observableArrayList(localSave.loadTenants());
		candidates = FXCollections.observableArrayList(localSave.loadCandidates());
		contractors = FXCollections.observableArrayList(localSave.loadContractors());

		// Extraneous sort, to make sure that all lists are in order
		orderAscending();
	}

	//TODO: Revise continuity check for online save
	/**
	 * Saves Database elements to the server
	 * */
	private void onlineSave() {
		sqlBridge.queryBuildings().forEach(olfBldg -> buildings.forEach(newBldg -> {
			if (olfBldg.equals(newBldg)) {
				newBldg.setEdited(true);
				sqlBridge.update(newBldg);
			}
		}));

		sqlBridge.queryTenants().forEach(oldTnant -> tenants.forEach(newTnant -> {
			if (oldTnant.equals(newTnant)) {
				newTnant.setEdited(true);
				sqlBridge.update(newTnant);
			}
		}));

		sqlBridge.queryCandidates().forEach(oldCand -> candidates.forEach(newCand -> {
			if (oldCand.equals(newCand)) {
				newCand.setEdited(true);
				sqlBridge.update(newCand);
			}
		}));

		sqlBridge.queryContractors().forEach(oldCont -> contractors.forEach(newCont -> {
			if (oldCont.equals(newCont)) {
				newCont.setEdited(true);
				sqlBridge.update(newCont);
			}
		}));

		buildings.forEach(apt -> {
			if (apt.isEdited()) {
				sqlBridge.insert(apt);
			}
		});

		tenants.forEach(tnant -> {
			if (tnant.isEdited()) {
				sqlBridge.insert(tnant);
			}
		});

		candidates.forEach(cand -> {
			if (cand.isEdited()) {
				sqlBridge.insert(cand);
			}
		});

		contractors.forEach(cont -> {
			if (cont.isEdited()) {
				sqlBridge.insert(cont);
			}
		});
	}

	/**
	 * Saves Database elements to local files
	 * */
	private void offlineSave() {
		localSave.saveBuildings(buildings);
		localSave.saveTenants(tenants);
		localSave.saveCandidates(candidates);
		localSave.saveContractors(contractors);
	}

	/**
	 * Sorts all lists by ascending order
	 * */
	private void orderAscending() {
		Collections.sort(buildings);
		Collections.sort(tenants);
		Collections.sort(candidates);
		Collections.sort(contractors);

		buildings.forEach(a -> Collections.sort(a.getApartments()));

		buildings.forEach(a -> Collections.sort(a.getIssues()));

		buildings.forEach(a -> a.getApartments().forEach(r -> Collections.sort(r.getInspections())));
	}

	/**
	 * Sorts all lists by descending order
	 * */
	private void orderDescending() {
		buildings.sort(Collections.reverseOrder());
		tenants.sort(Collections.reverseOrder());
		candidates.sort(Collections.reverseOrder());
		contractors.sort(Collections.reverseOrder());

		buildings.forEach(a -> a.getApartments().forEach(r -> r.getInspections().sort(Collections.reverseOrder())));

		buildings.forEach(a -> a.getIssues().sort(Collections.reverseOrder()));
	}

	/**
	 * Orders lists by Table Id (Default order)
	 * */
	private void orderById() {
		// sort lists
		switch (currentOrder[0]) {
			case 0:
				orderAscending();
				break;
			case 1:
				orderDescending();
				break;
			default:
				System.out.println("Invalid order state");
				break;

		}
	}

	/**
	 * Orders lists by name(Or equivalent)
	 * */
	private void orderByName() {
		// create comparators
		Comparator<Building> appByName = Comparator.comparing(Building::getAddress);
		Comparator<Tenant> tnantByName = Comparator.comparing(Tenant::getLastName);
		Comparator<Candidate> candByName = Comparator.comparing(Candidate::getLastName);
		Comparator<Contractor> contByName = Comparator.comparing(Contractor::getName);

		switch (currentOrder[0]) {
			case 0:
				buildings.sort(appByName);
				tenants.sort(tnantByName);
				candidates.sort(candByName);
				contractors.sort(contByName);
				break;
			case 1:
				buildings.sort(Collections.reverseOrder(appByName));
				tenants.sort(Collections.reverseOrder(tnantByName));
				candidates.sort(Collections.reverseOrder(candByName));
				contractors.sort(Collections.reverseOrder(contByName));
				break;
			default:
				//Should throw exception instead of a println
				System.out.println("Invalid order state");
				break;

		}
		// sort lists
	}

	/**
	 * Orders lists by Date Created
	 * */
	private void orderByDate() {
		//TODO: Order By Date Method
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Constant enums
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Sorting enumeration: holds sorting constants
	 * */
	enum DBSorting {
		/**
		 * Sorts by Ascending order
		 * */
		ASCENDING(0),

		/**
		 * Sorts by Descending order
		 * */
		DESCENDING(1),

		/**
		 * Orders tables by ID
		 * */
		ORDER_BY_ID(2),

		/**
		 * Orders tables by Name(or equivalent)
		 * */
		ORDER_BY_NAME(3),

		/**
		 * Orders tables by Date Created
		 * */
		ORDER_BY_DATE(4);

		/**
		 * DBSorting constructor; requires sorting number for saving reasons
		 * */
		DBSorting(int orderNumber) {
			this.orderNumber = orderNumber;
		}

		/**
		 * Sorting number
		 * */
		private final int orderNumber;

		/**
		 * Order number getter
		 * */
		public int getOrderNumber() {
			return orderNumber;
		}
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// General Getters & Setters
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gets the Database Instance
	 * @return Instance of Database
	 * */
	public static Database getInstance() {
		return INSTANCE;
	}

	/**
	 * Getter:
	 * <p>
	 * Returns if Database is connected to existing data
	 * @return <code>true</code> if connection is established, <code>false</code> if not
	 * */
	public static boolean isConnected() {
		return connected;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets connection variable
	 * */
	public static void setConnection(boolean connected) {
		Database.connected = connected;
	}

	/**
	 * Getter:
	 * <p>
	 * Returns if Database was able to connect to server
	 * @return <code>true</code> if Database is online, <code>false</code> if not
	 * */
	public static boolean isOnline() {
		return online;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets online variable
	 * @param online new online state
	 * */
	public static void setOnline(boolean online) {
		Database.online = online;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets timed events and updates
	 * @return database events
	 * */
	public Heck getEvents() {
		return events;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Building list in the database
	 * @return Building list
	 * */
	public ObservableList<Building> getBuildings() {
		return FXCollections.unmodifiableObservableList(buildings);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Building from an existing list
	 * @param buildings new list of Building
	 * */
	public void setBuildings(ObservableList<Building> buildings) {
		this.buildings = buildings;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Tenant list in the database
	 * @return Tenant list
	 * */
	public ObservableList<Tenant> getTenants() {
		return FXCollections.unmodifiableObservableList(tenants);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Tenants from an existing list
	 * */
	public void setTenants(ObservableList<Tenant> tenants) {
		this.tenants = tenants;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Candidate list in the database
	 * */
	public ObservableList<Candidate> getCandidates() {
		return FXCollections.unmodifiableObservableList(candidates);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Candidates from an existing list
	 * */
	public void setCandidates(ObservableList<Candidate> candidates) {
		this.candidates = candidates;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Contractor list in the database
	 * */
	public ObservableList<Contractor> getContractors() {
		return FXCollections.unmodifiableObservableList(contractors);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Contractors from an existing list
	 * */
	public void setContractors(ObservableList<Contractor> contractors) {
		this.contractors = contractors;
	}

	/***/
	public Table getCurrentTable() {
		return currentTable;
	}

	/***/
	public void setCurrentTable(Table currentTable) {
		this.currentTable = currentTable;
	}

	/***/
	public Table getPreviousTable() {
		return previousTable;
	}

	/**
	 * Setter:
	 * */
	public void setPreviousTable(Table previousTable) {
		this.previousTable = previousTable;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the database ledger
	 * @return ledger
	 * */
	public Map<Table, String> getLedger() {
		return ledger;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the Table in question
	 * */
	public static DBTables getCurrTable() {
		return currTable;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the Table in question
	 * */
	public static void setCurrTable(DBTables currTable) {
		Database.currTable = currTable;
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
}