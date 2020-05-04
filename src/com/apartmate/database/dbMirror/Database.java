package com.apartmate.database.dbMirror;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.apartmate.database.tables.mainTables.*;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.database.utilities.unordered.Heck;
//import com.apartmate.database.utilities.InvoiceUpdate;
import com.apartmate.database.utilities.saving.LocalDBSaving;
import com.apartmate.database.utilities.saving.SQLBridge;
import com.apartmate.main.Main;

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
//TODO: Javadoc's for every method
//TODO: Finish modifying edit methods
//TODO: Find better way of writing orderById & orderByName
//TODO: Finish orderByDate
public class Database {

	/***/
	private static final Database INSTANCE = new Database();

	// Constant enums
	/***/
	enum DBSorting {
		ASCENDING(0),
		DESCENDING(1),
		ORDER_BY_ID(2),
		ORDER_BY_NAME(3),
		ORDER_BY_DATE(4);

		DBSorting(int orderNumber) {
			this.orderNumber = orderNumber;
		}

		private final int orderNumber;

		public int getOrderNumber() {
			return orderNumber;
		}
	}

	/***/
	private static boolean connected;

	/***/
	private static boolean online;

	// currentOrder[0] holds ascending/descending order; currentOrder[1] holds order
	// by id/name
	/***/
	private final int[] currentOrder = { 0, 2 };

	// Utility objects
	// Saving utilities
	/***/
	public SQLBridge sqlBridge;

	/***/
	private LocalDBSaving localSave;

	// Unordered utilities
	/***/
	private Heck events;

	// Main tables
	/***/
	private List<Apartment> apartments;

	/***/
	private List<Tenant> tenants;

	/***/
	private List<Candidate> candidates;

	/***/
	private List<Contractor> contractors;

	// Table pointers
	/***/
	private static Apartment currApt; // Currently selected apartment

	/***/
	private static Tenant currTnant; // Currently selected tenant

	/***/
	private static Candidate currCand; // Currently selected candidate

	/***/
	private static Contractor currCont; // Currently selected contractor

	private static Insurance currIns;

	private static Bill currBill;

	private static DBTables currTable; //Currently selected table type

	/***/
	public Database() {
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

	/***/
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
	// TODO: Finish open method
	/***/
	public boolean open() {
		//Attempt to connect to the online database
		if (sqlBridge.connect()) { // Connect to Database
			// populate tables using query
			apartments = sqlBridge.queryApartments();
			tenants = sqlBridge.queryTenants();
			candidates = sqlBridge.queryCandidates();
			contractors = sqlBridge.queryContractors();

			// Extraneous sort, to make sure that all lists are in order
			orderAscending();

			// move these to login button later
			connected = true;
			online = true;

			if (Main.DEBUG)
				debug();

			return true;
		//Attempt to load from local files
		} else if (localSave.createDir()) {
			// populate tables using local save
			apartments = localSave.loadApartments();
			tenants = localSave.loadTenants();
			candidates = localSave.loadCandidates();
			contractors = localSave.loadContractors();

			// Extraneous sort, to make sure that all lists are in order
			orderAscending();

			// move these to login button later
			connected = true;
			online = false;

			if (Main.DEBUG)
				debug();

			return true;
		} else { // If Offline and no local files exist, continue w/ no data
			System.out.println("Unable to populate tables: Database is empty");
			// move these to login button later
			connected = false;
			online = false;

			if (Main.DEBUG)
				debug();

			return false;
		}
	}

	// Method that Timer daemon will use to attempt to reconnect to the database
	/***/
	public void reConnect(String username, String pass) {
		if (!online) {
			sqlBridge.connect();
		}
	}

	/***/
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
	 * */
	public boolean forceLoad(boolean retry) {
		List<Apartment> tempA = localSave.loadApartments();
		List<Tenant> tempT = localSave.loadTenants();
		List<Candidate> tempCa = localSave.loadCandidates();
		List<Contractor> tempCo = localSave.loadContractors();

		if (retry) {
			apartments = tempA;
			tenants = tempT;
			candidates = tempCa;
			contractors = tempCo;

			return true;
		} else {
			if (tempA.isEmpty() || tempT.isEmpty() || tempCa.isEmpty() || tempCo.isEmpty())
				return false;

			return forceLoad(true);
		}
	}

	//TODO: Revise continuity check for online save
	/***/
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

	/***/
	private void offlineSave() {
		localSave.saveApartments(apartments);
		localSave.saveTenants(tenants);
		localSave.saveCandidates(candidates);
		localSave.saveContractors(contractors);
	}

	/***/
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

	//TODO: Add sqlBridge insert if online to all add methods

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// add methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/***/
	public void add(Apartment apartment) {
		apartments.add(apartment);
		Collections.sort(apartments);

		if (online)
			sqlBridge.insert(apartment);
	}

	/***/
	public boolean add(Tenant tenant) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == tenant.getFk()) {
				Invoice initialDue = new Invoice();
				initialDue.setDues(tenant.getRent());
				tenant.getInvoices().add(initialDue);

				tenants.add(tenant);
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

	/***/
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

	/***/
	public boolean add(Contractor contractor) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == contractor.getFk()) {
				Invoice initialDue = new Invoice();
				initialDue.setDues(contractor.getBill());

				contractor.getInvoices().add(initialDue);
				contractors.add(contractor);
				Collections.sort(contractors);

				if (online)
					sqlBridge.insert(contractor);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;

	}

	/***/
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

	/***/
	public boolean add(Insurance insurance) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == insurance.getFk()) { // Find respective apartment
				Invoice initialDue = new Invoice();
				initialDue.setDues(insurance.getBill());
				insurance.getInvoices().add(initialDue);

				apartment.getInsurances().add(insurance);
				Collections.sort(apartment.getInsurances());

				if (online)
					sqlBridge.insert(insurance);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	/***/
	public boolean add(Bill bill) {
		for (Apartment apartment : apartments) {
			if (apartment.getId() == bill.getFk()) { // Find respective apartment
				Invoice initialDue = new Invoice();
				initialDue.setDues(bill.getBill());
				bill.getInvoices().add(initialDue);

				apartment.getBills().add(bill);
				Collections.sort(apartment.getInsurances());

				if (online)
					sqlBridge.insert(bill);

				return true;
			}
			// Respective apartment not found; cycle
		}
		return false;
	}

	// Uses table constants to select whether the added spouse goes to a particular
	// tenant or candidate
	/***/
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

	/***/
	public boolean add(Invoice invoice, DBTables table) {
		switch (table) {
			case TENANTS:
				for (Tenant tenant : tenants) {
					if (tenant.getId() == invoice.getFk()) {
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
	/***/
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
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing apartment");
			uoe.printStackTrace();
			return false;
		}
	}

	/***/
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
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
			return false;
		}
	}

	/***/
	public boolean remove(Candidate candidate) {
		try {
			if (candidates.remove(candidate)) {
				if (online)
					sqlBridge.delete(candidate);

				return true;
			}

			return false;
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
			return false;
		}
	}

	/***/
	public boolean remove(Contractor contractor) {
		try {
			if (contractors.remove(contractor)){
				if (online)
					sqlBridge.delete(contractor);

				return true;
			}

			return false;
		} catch (NullPointerException npe) {
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
			return false;
		}
	}

	/***/
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
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
			return false;
		}
	}

	/***/
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
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
			uoe.printStackTrace();
			return false;
		}
	}

//	/***/
//	public boolean remove(Issue issue) {
//		try {
//
//			return false;
//		} catch (NullPointerException npe) {
//			System.out.println("Error in remove method construction");
//			npe.printStackTrace();
//			return false;
//		} catch (UnsupportedOperationException uoe) {
//			System.out.println("Error removing tenant");
//			uoe.printStackTrace();
//			return false;
//		}
//	}

	/***/
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
			System.out.println("Invalid table operand");
			return false;
		}
	}

	/***/
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
			System.out.println("Error in remove method construction");
			npe.printStackTrace();
			return false;
		} catch (UnsupportedOperationException uoe) {
			System.out.println("Error removing tenant");
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
	 * @return true if Apartment exists and changes are made
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
	 * @return true if Tenant exists and changes are made
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

	/***/
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

	/***/
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

	/***/
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

	/***/
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

	/***/
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

	/***/
	public boolean edit(Bill bill) {
		for (Apartment apartment : apartments) {
			int index = apartment.getBills().indexOf(bill);

			if (index > 0) {
				bill.setEdited(true);
				apartment.getBills().set(index, bill);

				//events.updateModified(bill);

				if (online) {
					sqlBridge.update(bill);
					bill.setEdited(false);
				}

				return true;
			}
		}

		return false;
	}

//	/***/
//	public boolean edit(NoteLog issue) {
//
//
//		return false;
//	}

	/***/
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

	/***/
	private void updateInvoice(Invoice invoice, DBTables table) {
		events.upDateModified(invoice);

		if (online) {
			sqlBridge.update(invoice, table);
			invoice.setEdited(false);
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Other
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Sets ascending/descending order
	/***/
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

	/***/
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

	/***/
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

	// Sets the order by the order_by constants
	/***/
	public void setOrderBy(DBSorting orderBy) {
		switch (orderBy) {
			case ORDER_BY_ID: // Id (default)
				orderById();
				break;
			case ORDER_BY_NAME: // Name
				orderByName();
				break;
			case ORDER_BY_DATE:
				orderInvByDate();
				break;
			default:
				System.out.println("Invalid constant");
				return;
		}
		currentOrder[1] = orderBy.getOrderNumber();
	}

	/***/
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

	/***/
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

//	private void orderByDate() {
//
//	}

	/***/
	private void orderInvByDate() {
		apartments.forEach(a -> a.getInsurances().forEach(inv -> inv.getInvoices().sort(Invoice.INVOICE_BY_DATE)));

		tenants.forEach(t -> t.getInvoices().sort(Invoice.INVOICE_BY_DATE));

		contractors.forEach(c -> c.getInvoices().sort(Invoice.INVOICE_BY_DATE));
	}

	// --------------------------------------------------------------------------------------
	// General getters & setters
	/***/
	public static Database getInstance() {
		return INSTANCE;
	}

	/***/
	public static boolean isConnected() {
		return connected;
	}

	/***/
	public static void setConnection(boolean connected) {
		Database.connected = connected;
	}

	/***/
	@SuppressWarnings("unused")
	public static boolean isOnline() {
		return online;
	}

	/***/
	@SuppressWarnings("unused")
	public static void setOnline(boolean online) {
		Database.online = online;
	}

	/***/
	public Heck getEvents() {
		return events;
	}

	/***/
	public List<Apartment> getApartments() {
		return apartments;
	}

	/***/
	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}

	/***/
	public List<Tenant> getTenants() {
		return tenants;
	}

	/***/
	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	/***/
	public List<Candidate> getCandidates() {
		return candidates;
	}

	/***/
	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	/***/
	public List<Contractor> getContractors() {
		return contractors;
	}

	/***/
	public void setContractors(List<Contractor> contractors) {
		this.contractors = contractors;
	}

	/***/
	public Apartment getCurrApt() {
		return currApt;
	}

	/***/
	public void setCurrApt(Apartment currApt) {
		Database.currApt = currApt;
	}

	/***/
	public Tenant getCurrTnant() {
		return currTnant;
	}

	/***/
	public void setCurrTnant(Tenant currTant) {
		Database.currTnant = currTant;
	}

	/***/
	public Candidate getCurrCand() {
		return currCand;
	}

	/***/
	public void setCurrCand(Candidate currCand) {
		Database.currCand = currCand;
	}

	/***/
	public Contractor getCurrCont() {
		return currCont;
	}

	/***/
	public void setCurrCont(Contractor currCont) {
		Database.currCont = currCont;
	}

	/***/
	public Insurance getCurrIns() {
		return currIns;
	}

	/***/
	public void setCurrIns(Insurance currIns) {
		Database.currIns = currIns;
	}

	/***/
	public Bill getCurrBill() {
		return currBill;
	}

	/***/
	public void setCurrBill(Bill currBill) {
		Database.currBill = currBill;
	}

	/***/
	public static DBTables getCurrTable() {
		return currTable;
	}

	/***/
	public static void setCurrTable(DBTables currTable) {
		Database.currTable = currTable;
	}

	// --------------------------------------------------------------------------------------
}