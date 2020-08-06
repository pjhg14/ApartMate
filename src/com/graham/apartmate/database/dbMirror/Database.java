package com.graham.apartmate.database.dbMirror;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.utilities.unordered.Heck;
import com.graham.apartmate.database.utilities.saving.LocalDBSaving;
import com.graham.apartmate.database.utilities.saving.SQLBridge;
import com.graham.apartmate.main.Main;

import javax.xml.stream.events.StartDocument;


/**
 * Contains Lists of all tables and subTables (through their respective table)
 * to locally store and manipulate information
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @see Apartment
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

	// Utility objects
	// Saving utilities
	/**
	 * Contains and manages server connections, querying, and loading
	 * */
	public final SQLBridge sqlBridge;

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
	 * List of all Apartments
	 * */
	private List<Apartment> apartments;

	/**
	 * List of all Tenants
	 * */
	private List<Tenant> tenants;

	/**
	 * List of all Candidates
	 * */
	private List<Candidate> candidates;

	/**
	 * List of all Contractors
	 * */
	private List<Contractor> contractors;

	// Table pointers
	/***/
	private Table currentTable;

	/***/
	private Table previousTable;

	/**
	 * Holds the currently selected Apartment
	 * */
	private static Apartment currApt;

	/**
	 * Holds the currently selected Tenant
	 * */
	private static Tenant currTnant;

	/**
	 * Holds the currently selected Candidate
	 * */
	private static Candidate currCand;

	/**
	 * Holds the currently selected Contractor
	 * */
	private static Contractor currCont;

	/**
	 * Holds the currently selected Insurance
	 * */
	private static Insurance currIns;

	/**
	 * Holds the currently selected Bill
	 * */
	private static Bill currBill;

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
		apartments = new ArrayList<>();
		tenants = new ArrayList<>();
		candidates = new ArrayList<>();
		contractors = new ArrayList<>();

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
		return "Apartments: " + apartments
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
		if (sqlBridge.connect()) {	// Attempt to connect to Database
			// populate tables using query
			onlineLoad();

			// move these to login button later
			connected = true;
			online = true;

			if (Main.DEBUG)
				debug();

			return true;
		} else if (localSave.createDir()) {	//Attempt to load from local files
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
		List<Apartment> cloneA = new ArrayList<>(apartments);
		List<Tenant> cloneT = new ArrayList<>(tenants);
		List<Candidate> cloneCa = new ArrayList<>(candidates);
		List<Contractor> cloneCo = new ArrayList<>(contractors);

		if (retry) {
			//Load
			offlineLoad();

			return true;
		} else {
			offlineLoad();
			if (apartments.isEmpty() || tenants.isEmpty() || candidates.isEmpty() || contractors.isEmpty()) {
				//If no data loaded, restore Lists
				apartments = cloneA;
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
				case APARTMENTS:
					id = apartments.get(apartments.size() - 1).getId();
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
				case INSURANCES:
					id = currApt.getInsurances().get(currApt.getInsurances().size() - 1).getId();
					return id;
				case BILLS:
					id = currApt.getBills().get(currApt.getBills().size() - 1).getId();
					return id;
				case INSPECTIONS:
					id = currTnant.getInspections().get(currTnant.getInspections().size() - 1).getId();
					return id;
				case ISSUES:
					id = currApt.getIssues().get(currApt.getIssues().size() - 1).getId();
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
	/***/
	public Apartment find(Apartment target) {
		for (Apartment apartment : apartments) {
			if (apartment.equals(target)) {
				return apartment;
			}
		}

		return null;
	}

	/***/
	public Tenant find(Tenant target) {
		for (Tenant tenant : tenants) {
			if (tenant.equals(target)) {
				return tenant;
			}
		}

		return null;
	}

	/***/
	public Candidate find(Candidate target) {
		for (Candidate candidate : candidates) {
			if (candidate.equals(target)) {
				return candidate;
			}
		}

		return null;
	}

	/***/
	public Contractor find(Contractor target) {
		for (Contractor contractor : contractors) {
			if (contractor.equals(target)) {
				return contractor;
			}
		}

		return null;
	}

	/***/
	public Apartment getRelatedApartment(Table table) {
		for (Apartment apartment : apartments) {
			if (table.getFk() == apartment.getId()) {
				return apartment;
			}
		}

		return null;
	}
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// add methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Adds an Apartment to the Database
	 * @param apartment Apartment to be added
	 * */
	public void add(Apartment apartment) {
		apartments.add(apartment);
		Collections.sort(apartments);

		if (online)
			sqlBridge.insert(apartment);
	}

	/**
	 * Adds a Tenant to the Database; fails if the Tenant has no related Apartment
	 * @param tenant Tenant to be added
	 * @return <code>true</code> if Tenant was added, <code>false</code> otherwise
	 * */
	public boolean add(Tenant tenant) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == tenant.getFk()) {
				Invoice initialDue = new Invoice();
				initialDue.setFk(tenant.getId());

				tenants.add(tenant);
				add(initialDue, DBTables.TENANTS);

				apartment.setNumTenants(apartment.getNumTenants() + 1);
				Collections.sort(tenants);

				if (online)
					sqlBridge.insert(tenant);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;

	}

	/**
	 * Adds a Candidate to the Database
	 * @param candidate Candidate to be added
	 * @return <code>true</code> if Candidate was added, <code>false</code> otherwise
	 * */
	public boolean add(Candidate candidate) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == candidate.getFk()) {
				candidates.add(candidate);
				Collections.sort(candidates);

				if (online)
					sqlBridge.insert(candidate);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	/**
	 * Adds a Contractor to the Database
	 * @param contractor Contractor to be added
	 * @return <code>true</code> if Contractor was added, <code>false</code> otherwise
	 * */
	public boolean add(Contractor contractor) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == contractor.getFk()) {
				Invoice initialDue = new Invoice();
				initialDue.setFk(contractor.getId());

				contractors.add(contractor);
				add(initialDue, DBTables.CONTRACTORS);

				Collections.sort(contractors);

				if (online)
					sqlBridge.insert(contractor);

				return true;
			}
			// Respective apartment not found; cycle
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
				for (Tenant tenant : tenants) {
					if (tenant.getId() == issInsp.getFk()) {
						tenant.getInspections().add(issInsp);
						Collections.sort(tenant.getInspections());

						if (online)
							sqlBridge.insert(issInsp,table);

						return true;
					}
					// Respective apartment not found; cycle
				}
				break;
			case ISSUES:
				for (Apartment apartment : apartments) {
					if (apartment.getId() == issInsp.getFk()) { // Find respective apartment
						apartment.getIssues().add(issInsp);
						Collections.sort(apartment.getIssues());

						if (online)
							sqlBridge.insert(issInsp,table);

						return true;
					}
				// Respective apartment not found; cycle
				}
				break;
			default:
				break;
		}
		return false;
	}

	/**
	 * Adds a Insurance to the Database
	 * @param insurance Insurance to be added
	 * @return <code>true</code> if Insurance was added, <code>false</code> otherwise
	 * */
	public boolean add(Insurance insurance) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == insurance.getFk()) { // Find respective apartment
				Invoice initialDue = new Invoice();
				initialDue.setFk(insurance.getId());

				apartment.getInsurances().add(insurance);
				add(initialDue, DBTables.INSURANCES);


				Collections.sort(apartment.getInsurances());

				if (online)
					sqlBridge.insert(insurance);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	/**
	 * Adds a Bill to the Database
	 * @param bill Bill to be added
	 * @return <code>true</code> if Bill was added, <code>false</code> otherwise
	 * */
	public boolean add(Bill bill) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == bill.getFk()) { // Find respective apartment
				Invoice initialDue = new Invoice();
				initialDue.setFk(bill.getId());

				apartment.getBills().add(bill);
				add(initialDue, DBTables.BILLS);

				Collections.sort(apartment.getInsurances());

				if (online)
					sqlBridge.insert(bill);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	/**
	 * Adds a Spouse to the Database; uses table constants to select whether the added
	 * spouse goes to a particular tenant or candidate
	 * @param spouse The Spouse to add to the Database
	 * @param table Table the Spouse is related to (Valid tables: Tenants & Candidates)
	 * @return <code>true</code> if the spouse was added, <code>false</code> otherwise
	 * */
	public boolean add(Spouse spouse, DBTables table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if ((tenant.getId() == spouse.getFk()) && tenant.getSpouse() == null) {
					tenant.setSpouse(spouse);

					if (online)
						sqlBridge.insert(spouse, DBTables.TENANTS);
					return true;
				}
			}
			return false;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if ((candidate.getId() == spouse.getFk2()) && candidate.getSpouse() == null) {
					candidate.setSpouse(spouse);

					if (online)
						sqlBridge.insert(spouse, DBTables.CANDIDATES);

					return true;
				}
			}
			return false;
		default:
			System.out.println("Invalid table operand");
			return false;
		}
	}

	/**
	 * Adds an Invoice to the Database; uses table constants to select whether the added
	 * Invoice goes to a particular Tenant or Candidate
	 * @param invoice The Invoice to add to the Database
	 * @param table Table the Spouse is related to (Valid tables: Tenants, Contractors, Insurances, & Bills)
	 * @return <code>true</code> if the spouse was added, <code>false</code> otherwise
	 * */
	public boolean add(Invoice invoice, DBTables table) {
		switch (table) {
			case TENANTS:
				for (Tenant tenant : tenants) {
					if (tenant.getId() == invoice.getFk()) {
						invoice.setDues(tenant.getRent());
						tenant.getInvoices().add(invoice);
						events.updateTotals(tenant.getInvoices());
						Collections.sort(tenant.getInvoices());

						if (online)
							sqlBridge.insert(invoice, table);

						return true;
					}
				}
				//Related Tenant not found; returning false
				return false;
			case CONTRACTORS:
				for (Contractor contractor : contractors) {
					if (contractor.getId() == invoice.getFk()) {
						invoice.setDues(contractor.getBill());
						contractor.getInvoices().add(invoice);
						events.updateTotals(contractor.getInvoices());
						Collections.sort(contractor.getInvoices());

						if (online)
							sqlBridge.insert(invoice, table);

						return true;
					}
				}
				//Related Contractor not found; returning false
				return false;
			case BILLS:
				for (Apartment apartment : apartments) {
					for (Bill bill : apartment.getBills()) {
						if (bill.getId() == invoice.getFk()) {
							invoice.setDues(bill.getBill());
							bill.getInvoices().add(invoice);
							events.updateTotals(bill.getInvoices());
							Collections.sort(bill.getInvoices());

							if (online)
								sqlBridge.insert(invoice, table);

							return true;
						}
					}
				}
				//Related Bill not found; returning false
				return false;
			case INSURANCES:
				for (Apartment apartment : apartments) {
					for (Insurance insurance : apartment.getInsurances()) {
						if (insurance.getId() == invoice.getFk()) {
							invoice.setDues(insurance.getBill());
							insurance.getInvoices().add(invoice);
							events.updateTotals(insurance.getInvoices());
							Collections.sort(insurance.getInvoices());

							if (online)
								sqlBridge.insert(invoice, table);

							return true;
						}
					}
				}
				//Related insurance not found; returning false
				return false;
			default:
				if (Main.DEBUG)
					System.out.println("Invalid Table operand");
				return false;
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// remove methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Removes an Apartment from the Database
	 * @param apartment Apartment to be removed
	 * @return <code>true</code> if the Apartment was removed <code>false</code> otherwise
	 * */
	public boolean remove(Apartment apartment) {
		try {
			if (apartments.remove(apartment)) {
				// Remove all main tables related to specific apartment
				tenants.removeIf(t -> (t.getFk() == apartment.getId()));
				candidates.removeIf(can -> (can.getFk() == apartment.getId()));
				contractors.removeIf(cont -> (cont.getFk() == apartment.getId()));

				if (online)
					sqlBridge.delete(apartment);

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
				System.out.println("Error removing Apartment");

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
				//TODO: Change when room data is added
				for (Apartment apartment : apartments) {
					if ((apartment.getId() == tenant.getFk()) && apartment.getNumTenants() != 0) {
						apartment.decrementNumTenants();

						if (online)
							sqlBridge.delete(tenant);

						return true;
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
			if (candidates.remove(candidate)) {
				if (online)
					sqlBridge.delete(candidate);

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
			if (contractors.remove(contractor)){
				if (online)
					sqlBridge.delete(contractor);

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
					for (Tenant tenant : tenants) {
						if (tenant.getInspections().remove(issInsp)) {

							if (online)
								sqlBridge.delete(issInsp,table);

							return true;
						}
					}
					break;
				case ISSUES:
					for (Apartment apartment : apartments) {
						if (apartment.getIssues().remove(issInsp)) {

							if (online)
								sqlBridge.delete(issInsp,table);

							return true;
						}
					}
					break;
				default:
					break;
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
	 * Removes an Insurance from the Database
	 * @param insurance Insurance to be removed
	 * @return <code>true</code> if the Insurance was removed <code>false</code> otherwise
	 * */
	public boolean remove(Insurance insurance) {
		try {
			for (Apartment apartment : apartments) {
				if (apartment.getInsurances().remove(insurance)) {
					if (online)
						sqlBridge.delete(insurance);

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
				System.out.println("Error removing Insurance");

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
			for (Apartment apartment : apartments) {
				if (apartment.getBills().remove(bill)) {
					if (online)
						sqlBridge.delete(bill);

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

	/**
	 * Removes an Spouse from the Database
	 * @param spouse Spouse to be removed
	 * @param table Table the Spouse is related to (Valid tables: Tenants & Candidates)
	 * @return <code>true</code> if the Issue or Inspection was removed <code>false</code> otherwise
	 * */
	public boolean remove(Spouse spouse, DBTables table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if (tenant.getId() == spouse.getFk()) {
					tenant.setSpouse(null);

					if (online)
						sqlBridge.delete(spouse, table);

					return true;
				}
			}
			return false;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if (candidate.getId() == spouse.getFk2()) {
					candidate.setSpouse(null);

					if (online)
						sqlBridge.delete(spouse, table);

					return true;
				}
			}
			return false;
		default:
			if (Main.DEBUG)
				System.out.println("Invalid table operand");

			return false;
		}
	}

	/**
	 * Removes an Invoice from the Database
	 * @param invoice Invoice to be removed
	 * @param table Table the Invoice is related to (Valid tables: Tenants, Contractors, Insurances, & Bills)
	 * @return <code>true</code> if the Invoice was removed <code>false</code> otherwise
	 * */
	public boolean remove(Invoice invoice, DBTables table) {
		try {
			switch (table) {
				case TENANTS:
					for (Tenant tenant : tenants) {
						if (tenant.getInvoices().remove(invoice)) {
							if (online)
								sqlBridge.delete(invoice, table);

							return true;
						}
					}
					return false;
				case CONTRACTORS:
					for (Contractor contractor : contractors) {
						if (contractor.getInvoices().remove(invoice)) {
							if (online)
								sqlBridge.delete(invoice, table);

							return true;
						}
					}
					return false;
				case BILLS:
					for (Apartment apartment : apartments) {
						for (Bill bill : apartment.getBills()) {
							if (bill.getInvoices().remove(invoice)) {
								if (online)
									sqlBridge.delete(invoice, table);

								return true;
							}
						}
					}
					return false;
				case INSURANCES:
					for (Apartment apartment : apartments) {
						for (Insurance insurance : apartment.getInsurances()) {
							if (insurance.getInvoices().remove(invoice)) {
								if (online)
									sqlBridge.delete(invoice, table);

								return true;
							}
						}
					}
					return false;
				default:
					if (Main.DEBUG)
						System.out.println("Invalid table operand");
					return false;
			}
		} catch (NullPointerException npe) {
			if (Main.DEBUG)
				System.out.println("Error in remove method construction");

			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			if (Main.DEBUG)
				System.out.println("Error removing Invoice");

			uoe.printStackTrace();
			return false;
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// edit methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Edits an Apartment that already exists in the Database
	 * @param apartment Apartment to edit
	 * @return <code>true</code> if Apartment exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Apartment apartment) {
		int index = apartments.indexOf(apartment);

		if (index > 0) {
			apartment.setEdited(true);
			apartments.set(index, apartment);

			events.upDateModified(apartment);

			if (online) {
				sqlBridge.update(apartment);
				apartment.setEdited(false);
			}

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

			if (online) {
				sqlBridge.update(tenant);
				tenant.setEdited(false);
			}

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

			if (online) {
				sqlBridge.update(candidate);
				candidate.setEdited(false);
			}

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

			if (online) {
				sqlBridge.update(contractor);
				contractor.setEdited(false);
			}

			return true;
		}

		return false;
	}

	/**
	 * Edits Spouse details that already exists in the Database
	 * @param spouse Spouse to edit
	 * @param table Selects the related Table (Valid Tables: Tenants & Candidates)
	 * @return <code>true</code> if Spouse exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Spouse spouse, DBTables table) {
		switch (table){
			case TENANTS:
				for (Tenant tnant : tenants) {
					if (tnant.getSpouse().equals(spouse)) {
						spouse.setEdited(true);
						tnant.setSpouse(spouse);

						if (online) {
							sqlBridge.update(spouse,table);
							spouse.setEdited(false);
						}

						return true;
					}
				}
				return false;
			case CANDIDATES:
				for (Candidate cand : candidates) {
					if (cand.getSpouse().equals(spouse)) {
						spouse.setEdited(true);
						cand.setSpouse(spouse);

						if (online) {
							sqlBridge.update(spouse,table);
							spouse.setEdited(false);
						}

						return true;
					}
				}
				return false;
			default:
				return false;
		}
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
				for (Tenant tenant : tenants) {
					int index = tenant.getInspections().indexOf(issInsp);

					if (index > 0) {
						issInsp.setEdited(true);
						tenant.getInspections().set(index,issInsp);

						events.upDateModified(issInsp);

						if (online) {
							sqlBridge.update(issInsp,table);
							issInsp.setEdited(false);
						}

						return true;
					}

				}
				break;
			case ISSUES:
				for (Apartment apartment : apartments) {
					int index = apartment.getIssues().indexOf(issInsp);

					if (index > 0) {
						issInsp.setEdited(true);
						apartment.getIssues().set(index,issInsp);

						events.upDateModified(issInsp);

						if (online) {
							sqlBridge.update(issInsp,table);
							issInsp.setEdited(false);
						}

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
	 * Edits Insurance details that already exists in the Database
	 * @param insurance Insurance to edit
	 * @return <code>true</code> if Insurance exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Insurance insurance) {
		for (Apartment apartment : apartments) {
			int index = apartment.getInsurances().indexOf(insurance);

			if (index > 0) {
				insurance.setEdited(true);
				apartment.getInsurances().set(index,insurance);

				events.upDateModified(insurance);

				if (online) {
					sqlBridge.update(insurance);
					insurance.setEdited(false);
				}

				return true;
			}

		}

		return false;
	}

	/**
	 * Edits Bill details that already exists in the Database
	 * @param bill Bill to edit
	 * @return <code>true</code> if Bill exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Bill bill) {
		for (Apartment apartment : apartments) {
			int index = apartment.getBills().indexOf(bill);

			if (index > 0) {
				bill.setEdited(true);
				apartment.getBills().set(index, bill);

				events.upDateModified(bill);

				if (online) {
					sqlBridge.update(bill);
					bill.setEdited(false);
				}

				return true;
			}
		}

		return false;
	}

	/**
	 * Edits Invoice details that already exists in the Database
	 * @param invoice Invoice to edit
	 * @param table Table the Invoice is related to (Valid tables: Tenants, Contractors, Insurances, & Bills)
	 * @return <code>true</code> if Invoice exists and changes are made, <code>false</code> if not
	 * */
	public boolean edit(Invoice invoice, DBTables table) {
		switch (table) {
			case TENANTS:
				for (Tenant tenant : tenants) {
					int index = tenant.getInvoices().indexOf(invoice);

					if (index > 0) {
						invoice.setEdited(true);
						tenant.getInvoices().set(index,invoice);

						updateInvoice(invoice, table);

						return true;
					}
				}

				return false;
			case CONTRACTORS:
				for (Contractor contractor : contractors) {
					int index = contractor.getInvoices().indexOf(invoice);

					if (index > 0) {
						invoice.setEdited(true);
						contractor.getInvoices().set(index,invoice);

						updateInvoice(invoice, table);

						return true;
					}
				}

				return false;
			case BILLS:
				for (Apartment apartment : apartments) {
					for (Bill bill : apartment.getBills()) {
						int index = bill.getInvoices().indexOf(invoice);

						if (index > 0) {
							invoice.setEdited(true);
							bill.getInvoices().set(index, invoice);

							updateInvoice(invoice, table);

							return true;
						}
					}
				}

				return false;
			case INSURANCES:
				for (Apartment apartment : apartments) {
					for (Insurance insurance : apartment.getInsurances()) {
						int index = insurance.getInvoices().indexOf(invoice);

						if (index > 0) {
							invoice.setEdited(true);
							insurance.getInvoices().set(index, invoice);

							updateInvoice(invoice, table);

							return true;
						}
					}
				}

				return false;
			default:
				if (Main.DEBUG)
					System.out.println("Invalid table operand");

				return false;
		}
	}

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
				orderInvByDueDate();
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
		apartments = sqlBridge.queryApartments();
		tenants = sqlBridge.queryTenants();
		candidates = sqlBridge.queryCandidates();
		contractors = sqlBridge.queryContractors();

		// Extraneous sort, to make sure that all lists are in order
		orderAscending();
	}

	/**
	 * Loads Database elements from local files
	 * */
	private void offlineLoad() {
		apartments = localSave.loadApartments();
		tenants = localSave.loadTenants();
		candidates = localSave.loadCandidates();
		contractors = localSave.loadContractors();

		// Extraneous sort, to make sure that all lists are in order
		orderAscending();
	}

	//TODO: Revise continuity check for online save
	/**
	 * Saves Database elements to the server
	 * */
	private void onlineSave() {
		sqlBridge.queryApartments().forEach(oldApt -> apartments.forEach(newApt -> {
			if (oldApt.equals(newApt)) {
				newApt.setEdited(true);
				sqlBridge.update(newApt);
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

		apartments.forEach(apt -> {
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
		localSave.saveApartments(apartments);
		localSave.saveTenants(tenants);
		localSave.saveCandidates(candidates);
		localSave.saveContractors(contractors);
	}

	/**
	 * Updates the passed Invoice
	 * */
	private void updateInvoice(Invoice invoice, DBTables table) {
		events.upDateModified(invoice);

		if (online) {
			sqlBridge.update(invoice, table);
			invoice.setEdited(false);
		}
	}

	/**
	 * Sorts all lists by ascending order
	 * */
	private void orderAscending() {
		Collections.sort(apartments);
		Collections.sort(tenants);
		Collections.sort(candidates);
		Collections.sort(contractors);

		apartments.forEach(a -> Collections.sort(a.getInsurances()));

		apartments.forEach(a -> Collections.sort(a.getIssues()));

		tenants.forEach(t -> Collections.sort(t.getInvoices()));

		tenants.forEach(t -> Collections.sort(t.getInspections()));

		contractors.forEach(c -> Collections.sort(c.getInvoices()));
	}

	/**
	 * Sorts all lists by descending order
	 * */
	private void orderDescending() {
		apartments.sort(Collections.reverseOrder());
		tenants.sort(Collections.reverseOrder());
		candidates.sort(Collections.reverseOrder());
		contractors.sort(Collections.reverseOrder());

		apartments.forEach(a -> a.getInsurances().sort(Collections.reverseOrder()));

		apartments.forEach(a -> a.getIssues().sort(Collections.reverseOrder()));

		tenants.forEach(t -> t.getInvoices().sort(Collections.reverseOrder()));

		tenants.forEach(t -> t.getInspections().sort(Collections.reverseOrder()));

		contractors.forEach(c -> c.getInvoices().sort(Collections.reverseOrder()));
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
		Comparator<Apartment> appByName = Comparator.comparing(Apartment::getAddress);
		Comparator<Tenant> tnantByName = Comparator.comparing(Tenant::getLastName);
		Comparator<Candidate> candByName = Comparator.comparing(Candidate::getLastName);
		Comparator<Contractor> contByName = Comparator.comparing(Contractor::getName);

		switch (currentOrder[0]) {
			case 0:
				apartments.sort(appByName);
				tenants.sort(tnantByName);
				candidates.sort(candByName);
				contractors.sort(contByName);
				break;
			case 1:
				apartments.sort(Collections.reverseOrder(appByName));
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

	/**
	 * Orders Invoice lists by Due Date
	 * */
	private void orderInvByDueDate() {
		apartments.forEach(a -> {
			a.getInsurances().forEach(inv -> inv.getInvoices().sort(Invoice.INVOICE_BY_DATE));
			a.getBills().forEach(bill -> bill.getInvoices().sort(Invoice.INVOICE_BY_DATE));
		});

		tenants.forEach(t -> t.getInvoices().sort(Invoice.INVOICE_BY_DATE));

		contractors.forEach(c -> c.getInvoices().sort(Invoice.INVOICE_BY_DATE));
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
	 * */
	public static void setOnline(boolean online) {
		Database.online = online;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets timed events and updates
	 * */
	public Heck getEvents() {
		return events;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Apartment list in the database
	 * */
	public List<Apartment> getApartments() {
		return Collections.unmodifiableList(apartments);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Apartments from an existing list
	 * */
	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Tenant list in the database
	 * */
	public List<Tenant> getTenants() {
		return Collections.unmodifiableList(tenants);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Tenants from an existing list
	 * */
	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Candidate list in the database
	 * */
	public List<Candidate> getCandidates() {
		return Collections.unmodifiableList(candidates);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Candidates from an existing list
	 * */
	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets a view of the Contractor list in the database
	 * */
	public List<Contractor> getContractors() {
		return Collections.unmodifiableList(contractors);
	}

	/**
	 * Setter:
	 * <p>
	 * Sets list of Contractors from an existing list
	 * */
	public void setContractors(List<Contractor> contractors) {
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

	/***/
	public void setPreviousTable(Table previousTable) {
		this.previousTable = previousTable;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Apartment
	 * */
	public Apartment getCurrApt() {
		return currApt;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Apartment
	 * */
	public void setCurrApt(Apartment currApt) {
		Database.currApt = currApt;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Tenant
	 * */
	public Tenant getCurrTnant() {
		return currTnant;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Tenant
	 * */
	public void setCurrTnant(Tenant currTant) {
		Database.currTnant = currTant;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Candidate
	 * */
	public Candidate getCurrCand() {
		return currCand;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Candidate
	 * */
	public void setCurrCand(Candidate currCand) {
		Database.currCand = currCand;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Contractor
	 * */
	public Contractor getCurrCont() {
		return currCont;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Contractor
	 * */
	public void setCurrCont(Contractor currCont) {
		Database.currCont = currCont;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Insurance
	 * */
	public Insurance getCurrIns() {
		return currIns;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Insurance
	 * */
	public void setCurrIns(Insurance currIns) {
		Database.currIns = currIns;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the currently selected Bill
	 * */
	public Bill getCurrBill() {
		return currBill;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the currently selected Bill
	 * */
	public void setCurrBill(Bill currBill) {
		Database.currBill = currBill;
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