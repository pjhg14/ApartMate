package com.apartmate.database.dbMirror;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.apartmate.database.tables.mainTables.*;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.database.utilities.Heck;
//import com.apartmate.database.utilities.InvoiceUpdate;
import com.apartmate.database.utilities.LocalDBSaving;
import com.apartmate.database.utilities.SQLBridge;

/**
 * Contains Lists of all tables and subTables (through their respective table)
 * to locally store and manipulate information
 * 
 * @see tables
 * @see sub_tables
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Database {

	private static Database instance = new Database();

	// Method constants
	public static final int ASCENDING = 0;
	public static final int DESCENDING = 1;

	public static final int ORDER_BY_ID = 2; // Functionally orders by newest added(disregarding updates)
	public static final int ORDER_BY_NAME = 3;
	public static final int ORDER_BY_DATE = 4;

	public static final int APARTMENTS = 5;
	public static final int TENANTS = 6;
	public static final int CANDIDATES = 7;
	public static final int CONTRACTORS = 8;

	private static boolean connected;
	private static boolean online;

	// currentOrder[0] holds ascending/descending order; currentOrder[1] holds order
	// by id/name
	private int[] currentOrder = { 0, 2 };

	// Utility objects
	// Saving utilities
	private SQLBridge sqlBridge;
	private LocalDBSaving localSave;

	// Other utilities
	// private InvoiceUpdate invUpdate;
	private Heck events;

	// Main tables
	private List<Apartment> apartments;
	private List<Tenant> tenants;
	private List<Candidate> candidates;
	private List<Contractor> contractors;

	// Table pointers
	public static Apartment currApt; // Currently selected apartment
	private static Tenant currTnant; // Currently selected tenant
	private static Candidate currCand; // Currently selected candidate
	private static Contractor currCont; // Currently selected contractor

	// Issue/inspection switch true = issues; false = inspections
	private boolean issInsSwitch;

	public Database() {
		tenants = new ArrayList<Tenant>();
		apartments = new ArrayList<Apartment>();
		candidates = new ArrayList<Candidate>();
		contractors = new ArrayList<Contractor>();

		sqlBridge = new SQLBridge();
		localSave = new LocalDBSaving();

		events = new Heck();

		debug();
	}

	private void debug() {
		System.out.println("New database created");
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Open/close methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// TODO: populating local database w/ SQL/file
	public boolean open() {
		if (sqlBridge.connect()) { // Connect to Database
			// populate tables using query
			apartments = sqlBridge.queryApartments();
			tenants = sqlBridge.queryTenants();
			candidates = sqlBridge.queryCandidates();
			contractors = sqlBridge.queryContractors();

			// Extraneous sort, to make sure that all lists are in order
			Collections.sort(apartments);

			// debug();
			// move these to login button later
			connected = true;
			online = true;
			return true;
		} else if (localSave.createDir()) { // If offline, check for existing local file
			// populate tables using file handler
			apartments = localSave.loadApartments();
			tenants = localSave.loadTenants();
			candidates = localSave.loadCandidates();
			contractors = localSave.loadContractors();

			// debug();
			// move these to login button later
			connected = true;
			online = false;
			return true;
		} else { // If Offline and no local files exist, continue w/ no data
			System.out.println("Unable to populate tables: Database is empty"); // debug line; comment out later
			// move these to login button later
			connected = false;
			online = false;
			return false;
		}
	}

	// Method that Timer daemon will use to attempt to reconnect to the database
	public void reConnect(String username, String pass) {
		if (!online) {
			sqlBridge.connect();
		} else {
			// But the database is already online...
		}
	}

	public void save() {
		// Save All
		sqlBridge.queryApartments().forEach(apt -> {
			apartments.forEach(apt2 -> {
				if (apt.equals(apt2)) {
					apt2.setEdited(true);
					sqlBridge.update(apt2);
				}
			});
		});

		sqlBridge.queryTenants().forEach(tnant -> {
			tenants.forEach(tnant2 -> {
				if (tnant.equals(tnant2)) {
					tnant2.setEdited(true);
					sqlBridge.update(tnant2);
				}

			});
		});

		sqlBridge.queryCandidates().forEach(cand -> {
			candidates.forEach(cand2 -> {
				if (cand.equals(cand2)) {
					cand2.setEdited(true);
					sqlBridge.update(cand2);
				}

			});
		});

		sqlBridge.queryContractors().forEach(cont -> {
			contractors.forEach(cont2 -> {
				if (cont.equals(cont2)) {
					cont2.setEdited(true);
					sqlBridge.update(cont2);
				}

			});
		});

		apartments.forEach(apt -> {
			if (!apt.isEdited()) {
				sqlBridge.insert(apt);
			}
		});

		tenants.forEach(tnant -> {
			if (!tnant.isEdited()) {
				sqlBridge.insert(tnant);
			}
		});

		candidates.forEach(cand -> {
			if (!cand.isEdited()) {
				sqlBridge.insert(cand);
			}
		});

		contractors.forEach(cont -> {
			if (!cont.isEdited()) {
				sqlBridge.insert(cont);
			}
		});
	}

	public void close() {
		// setSortingOrder(ASCENDING);
		// setOrderBy(ORDER_BY_ID);
		save();
		 
		try {
			sqlBridge.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// save to .dat files
			localSave.saveApartments();
			localSave.saveTenants();
			localSave.saveCandidates();
			localSave.saveContractors();
		}
	}
	
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// add methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public void add(Apartment apartment) {
		apartments.add(apartment);
		Collections.sort(apartments);
		sqlBridge.insert(apartment);
	}

	public boolean add(Tenant tenant) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == tenant.getFk()) {
				tenants.add(tenant);
				apartment.setNumTenants(apartment.getNumTenants() + 1);
				Collections.sort(tenants);
				sqlBridge.insert(tenant);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;

	}

	public boolean add(Candidate candidate) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == candidate.getFk()) {
				candidates.add(candidate);
				Collections.sort(candidates);
				sqlBridge.insert(candidate);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	public boolean add(Contractor contractor) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == contractor.getFk()) {
				contractors.add(contractor);
				Collections.sort(contractors);
				sqlBridge.insert(contractor);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;

	}

	public boolean add(Inspection inspection) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == inspection.getFk()) {
				apartment.getInspections().add(inspection);
				sqlBridge.insert(inspection);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	public boolean add(Insurance insurance) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == insurance.getFk()) { // Find respective apartment
				apartment.getInsurances().add(insurance);
				events.updateTotals(insurance);
				sqlBridge.insert(insurance);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	public boolean add(Issue issue) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == issue.getFk()) { // Find respective apartment
				apartment.getIssues().add(issue);
				sqlBridge.insert(issue);
				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	// Uses table constants to select whether the added spouse goes to a particular
	// tenant or candidate
	public boolean add(Spouse spouse, int table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if ((tenant.getId() == spouse.getFk()) && tenant.getSpouse() == null) {
					tenant.setSpouse(spouse);
					sqlBridge.insert(spouse, TENANTS);
					return true;
				}
			}
			return false;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if ((candidate.getId() == spouse.getFk2()) && candidate.getSpouse() == null) {
					candidate.setSpouse(spouse);
					sqlBridge.insert(spouse, CANDIDATES);
					return true;
				}
			}
			return false;
		default:
			System.out.println("Invalid table operand");
			return false;
		}
	}

	public boolean add(TnantInvoice invoice) {
		for (Tenant tenant : tenants) {
			if (tenant.getId() == invoice.getFk()) { // Find respective tenant
				tenant.getInvoices().add(invoice);
				events.updateTotals(invoice);
				Collections.sort(currTnant.getInvoices());
				return true;
			}
			// Respective tenant not found; cycle
		}
		return false;

	}

	public boolean add(ContInvoice invoice) {
		for (Contractor contractor : contractors) {
			if (contractor.getId() == invoice.getFk()) { // Find respective contractor
				contractor.getInvoices().add(invoice);
				events.updateTotals(invoice);
				Collections.sort(currCont.getInvoices());
				return true;
			}
			// Respective contractor not found; cycle
		}
		return false;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// remove methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	public void remove(Apartment apartment) {
		try {
			// Remove all main tables related to specific apartment
			tenants.removeIf(t -> (t.getFk() == apartment.getId()));
			candidates.removeIf(can -> (can.getFk() == apartment.getId()));
			contractors.removeIf(cont -> (cont.getFk() == apartment.getId()));

			// Remove apartment
			apartments.removeIf(a -> (a.getId() == apartment.getId()));
			sqlBridge.delete(apartment);
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing apartment");
			uoe.printStackTrace();
		}
	}

	public void remove(Tenant tenant) {
		try {
			tenants.removeIf(t -> (t.getId() == tenant.getId()));

			for (Apartment apartment : apartments) {
				if ((apartment.getId() == tenant.getFk()) && apartment.getNumTenants() != 0) {
					apartment.setNumTenants(apartment.getNumTenants() - 1);
					sqlBridge.delete(tenant);
				}
			}
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(Candidate candidate) {
		try {
			candidates.removeIf(can -> (can.getId() == candidate.getId()));
			sqlBridge.delete(candidate);
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(Contractor contractor) {
		try {
			contractors.removeIf(cont -> (cont.getId() == contractor.getId()));
			sqlBridge.delete(contractor);
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(Inspection inspection) {
		try {
			for (Apartment apartment : apartments) {
				apartment.getInspections().removeIf(insp -> (insp.getId() == inspection.getId()));
			}
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(Insurance insurance) {
		try {
			for (Apartment apartment : apartments) {
				apartment.getInsurances().removeIf(insu -> (insu.getId() == insurance.getId()));
			}
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(Issue issue) {
		try {
			for (Apartment apartment : apartments) {
				apartment.getIssues().removeIf(iss -> (iss.getId() == issue.getId()));
			}
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public boolean remove(Spouse spouse, int table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if (tenant.getId() == spouse.getFk()) {
					tenant.setSpouse(null);
					sqlBridge.delete(spouse, table);
					return true;
				}
			}
			return false;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if (candidate.getId() == spouse.getFk2()) {
					candidate.setSpouse(null);
					sqlBridge.delete(spouse, table);
				}
			}
			return false;
		default:
			System.out.println("Invalid table operand");
			return false;
		}
	}

	public void remove(TnantInvoice invoice) {
		try {
			tenants.forEach(t -> {
				t.getInvoices().removeIf(inv -> (inv.getId() == invoice.getId()));
			});
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	public void remove(ContInvoice invoice) {
		try {
			contractors.forEach(c -> {
				c.getInvoices().removeIf(inv -> (inv.getId() == invoice.getId()));
			});
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// edit methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	public void edit(Apartment apartment) {
		remove(apartment);
		events.updateModified(apartment);
		add(apartment);
		
		sqlBridge.update(apartment);
	}

	public void edit(Tenant tenant) {
		remove(tenant);
		events.updateModified(tenant);
		add(tenant);
		
		sqlBridge.update(tenant);
	}

	public void edit(Candidate candidate) {
		remove(candidate);
		events.updateModified(candidate);
		add(candidate);
		
		sqlBridge.update(candidate);
	}

	public void edit(Contractor contractor) {
		remove(contractor);
		events.updateModified(contractor);
		add(contractor);
		
		sqlBridge.update(contractor);
	}

	public void edit(Spouse spouse, int table) {
		switch (table) {
		case TENANTS:
			for (Tenant tenant : tenants) {
				if (spouse.getFk() == tenant.getId()) {
					tenant.setSpouse(spouse);
					events.updateModified(spouse, table);
					
					sqlBridge.update(spouse, table);
				}
			}
			break;
		case CANDIDATES:
			for (Candidate candidate : candidates) {
				if (spouse.getFk2() == candidate.getId()) {
					candidate.setSpouse(spouse);
					events.updateModified(spouse, table);
					
					sqlBridge.update(spouse, table);
				}
			}
		}
	}

	public void edit(Inspection inspection) {
		remove(inspection);
		events.updateModified(inspection);
		add(inspection);
		
		sqlBridge.update(inspection);
	}

	public void edit(Insurance insurance) {
		remove(insurance);
		events.updateModified(insurance);
		add(insurance);
		
		sqlBridge.update(insurance);
	}

	public void edit(Issue issue) {
		remove(issue);
		events.updateModified(issue);
		add(issue);
		
		sqlBridge.update(issue);
	}

	public void edit(TnantInvoice invoice) {
		remove(invoice);
		events.updateModified(invoice);
		add(invoice);
		
		sqlBridge.update(invoice);
	}

	public void edit(ContInvoice invoice) {
		remove(invoice);
		events.updateTotals(invoice);
		add(invoice);
		
		sqlBridge.update(invoice);
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Other
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	@Override
	public String toString() {
		return apartments.toString() + "\n" + tenants.toString() + "\n" + candidates.toString() + "\n"
				+ contractors.toString();
	}

	// Sets ascending/descending order
	public void setSortingOrder(int order) {
		switch (order) {
		case ASCENDING: // Ascending
			orderAscending();
			order = ASCENDING;
			break;
		case DESCENDING: // Descending
			orderDescending();
			order = DESCENDING;
			break;
		default:
			// Display error: invalid order
			System.out.println("Invalid order constant");
			return;
		}
		currentOrder[0] = order;
	}

	private void orderAscending() {
		Collections.sort(apartments);
		Collections.sort(tenants);
		Collections.sort(candidates);
		Collections.sort(contractors);

		apartments.forEach(a -> {
			Collections.sort(a.getInspections());
		});
		apartments.forEach(a -> {
			Collections.sort(a.getInsurances());
		});
		apartments.forEach(a -> {
			Collections.sort(a.getIssues());
		});

		tenants.forEach(t -> {
			Collections.sort(t.getInvoices());
		});

		contractors.forEach(c -> {
			Collections.sort(c.getInvoices());
		});
	}

	private void orderDescending() {
		apartments.sort(Collections.reverseOrder());
		tenants.sort(Collections.reverseOrder());
		candidates.sort(Collections.reverseOrder());
		contractors.sort(Collections.reverseOrder());

		apartments.forEach(a -> {
			a.getInspections().sort(Collections.reverseOrder());
		});
		apartments.forEach(a -> {
			a.getInsurances().sort(Collections.reverseOrder());
		});
		apartments.forEach(a -> {
			a.getIssues().sort(Collections.reverseOrder());
		});

		tenants.forEach(t -> {
			t.getInvoices().sort(Collections.reverseOrder());
			;
		});

		contractors.forEach(c -> {
			c.getInvoices().sort(Collections.reverseOrder());
			;
		});
	}

	// Sets the order by the order_by constants
	public void setOrderBy(int orderBy) {
		switch (orderBy) {
		case ORDER_BY_ID: // Id (default)
			orderById();
			orderBy = ORDER_BY_ID;
			break;
		case ORDER_BY_NAME: // Name
			orderByName();
			orderBy = ORDER_BY_NAME;
			break;
		case ORDER_BY_DATE:
			orderByDate();
			orderBy = ORDER_BY_DATE;
			break;
		default:
			System.out.println("Invalid constant");
			return;
		}
		currentOrder[1] = orderBy;
	}

	private void orderById() {
		// sort lists
		switch (currentOrder[0]) {
		case ASCENDING:
			orderAscending();
			break;
		case DESCENDING:
			orderDescending();
			break;
		default:
			System.out.println("Invalid order state");
			break;

		}
	}

	private void orderByName() {
		// create comparators
		Comparator<Apartment> appByName = Comparator.comparing(Apartment::getAddress);
		Comparator<Tenant> tnantByName = Comparator.comparing(Tenant::getLastName);
		Comparator<Candidate> candByName = Comparator.comparing(Candidate::getLastName);
		Comparator<Contractor> contByName = Comparator.comparing(Contractor::getName);

		switch (currentOrder[0]) {
		case ASCENDING:
			Collections.sort(apartments, appByName);
			Collections.sort(tenants, tnantByName);
			Collections.sort(candidates, candByName);
			Collections.sort(contractors, contByName);
			break;
		case DESCENDING:
			apartments.sort(Collections.reverseOrder(appByName));
			tenants.sort(Collections.reverseOrder(tnantByName));
			candidates.sort(Collections.reverseOrder(candByName));
			contractors.sort(Collections.reverseOrder(contByName));
			break;
		default:
			System.out.println("Invalid order state");
			break;

		}
		// sort lists
	}

	private void orderByDate() {
		Comparator<Insurance> insuByDate = Comparator.comparing(Insurance::getDueDate);
		Comparator<TnantInvoice> tinvByDate = Comparator.comparing(TnantInvoice::getDueDate);
		Comparator<ContInvoice> cinvByDate = Comparator.comparing(ContInvoice::getDueDate);

		apartments.forEach(a -> {
			Collections.sort(a.getInsurances(), insuByDate);
		});
		tenants.forEach(t -> {
			Collections.sort(t.getInvoices(), tinvByDate);
		});
		contractors.forEach(c -> {
			Collections.sort(c.getInvoices(), cinvByDate);
		});
	}

	@SuppressWarnings("unused")
	private void validate(List<Table> list1, List<Table> list2) {

	}

	// --------------------------------------------------------------------------------------
	// General getters & setters
	public void VVVVVV() {
	}

	public static Database getInstance() {
		return instance;
	}

	public static boolean isConnected() {
		return connected;
	}

	public static void setConnection(boolean connected) {
		Database.connected = connected;
	}

	public static boolean isOnline() {
		return online;
	}

	public static void setOnline(boolean online) {
		Database.online = online;
	}

	public Heck getEvents() {
		return events;
	}

	public List<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public List<Contractor> getContractors() {
		return contractors;
	}

	public void setContractors(List<Contractor> contractors) {
		this.contractors = contractors;
	}

	public Apartment getCurrApt() {
		return currApt;
	}

	public void setCurrApt(Apartment currApt) {
		Database.currApt = currApt;
	}

	public Tenant getCurrTnant() {
		return currTnant;
	}

	public void setCurrTnant(Tenant currTant) {
		Database.currTnant = currTant;
	}

	public Candidate getCurrCand() {
		return currCand;
	}

	public void setCurrCand(Candidate currCand) {
		Database.currCand = currCand;
	}

	public Contractor getCurrCont() {
		return currCont;
	}

	public void setCurrCont(Contractor currCont) {
		Database.currCont = currCont;
	}

	public boolean isIssInsSwitch() {
		return issInsSwitch;
	}

	public void setIssInsSwitch(boolean issInsSwitch) {
		this.issInsSwitch = issInsSwitch;
	}

	// --------------------------------------------------------------------------------------
}