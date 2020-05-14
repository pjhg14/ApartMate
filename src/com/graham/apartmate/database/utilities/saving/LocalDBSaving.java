package com.graham.apartmate.database.utilities.saving;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.graham.apartmate.database.tables.mainTables.Apartment;
import com.graham.apartmate.database.tables.mainTables.Candidate;
import com.graham.apartmate.database.tables.mainTables.Contractor;
import com.graham.apartmate.database.tables.mainTables.Tenant;
import com.graham.apartmate.main.Main;

/**
 * Responsible for the saving of tables to a local file
 *
 * @author Paul Graham Jr <pjhg14@gmail.com>
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Finalize Class (After toolbar finalization)
public class LocalDBSaving {

	/**
	 * File constant
	 * */
	private static final File DIRECTORY = new File("data/db");

	/**
	 * Checks if the directory 'com/apartmate/data/db' exists and creates it
	 * @return <code>true</code> if directory exists
	 * (whether it existed before invocation or was recently made),
	 * <code>false</code> if not so
	 * */
	public boolean createDir() {
		if (!DIRECTORY.exists()) {
			return !DIRECTORY.mkdir();
		}
		return true;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Saving Methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Iterates through all passed Apartments in a list and saves them to apartments.dat
	 * @param apartments List of Apartments to save
	 * */
	public void saveApartments(List<Apartment> apartments) {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/apartments.dat")))) {
			for (Apartment apartment : apartments) {
				output.writeObject(apartment);
			}
		} catch (IOException io) {
			System.out.println("Exception occurred while saving Apartment list: " + io.getMessage());
		}
	}

	/**
	 * Iterates through all passed Tenants in a list and saves them to tenants.dat
	 * @param tenants List of Tenants to save
	 * */
	public void saveTenants(List<Tenant> tenants) {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/tenants.dat")))) {
			for (Tenant tenant : tenants) {
				output.writeObject(tenant);
			}

		} catch (IOException io) {
			System.out.println("Exception occurred while saving Tenant list: " + io.getMessage());
		}
	}

	/**
	 * Iterates through all passed Candidates in a list and saves them to candidates.dat
	 * @param candidates List of Candidates to save
	 * */
	public void saveCandidates(List<Candidate> candidates) {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/candidates.dat")))) {
			for (Candidate candidate : candidates) {
				output.writeObject(candidate);
			}

		} catch (IOException io) {
			System.out.println("Exception occurred while saving Candidate list: " + io.getMessage());
		}
	}

	/**
	 * Iterates through all passed Contractors in a list and saves them to contractors.dat
	 * @param contractors List of Contractors to save
	 * */
	public void saveContractors(List<Contractor> contractors) {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/contractors.dat")))) {
			for (Contractor contractor : contractors) {
				output.writeObject(contractor);
			}

		} catch (IOException io) {
			System.out.println("Exception occurred while saving Contractor list: " + io.getMessage());
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Loading Methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/**
	 * Loads a list of Apartments from apartments.dat
	 * @return List of loaded Apartments
	 * */
	public List<Apartment> loadApartments() {
		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("data/db/apartments.dat")))) {
			boolean endOfFile = false;
			List<Apartment> temp = new ArrayList<>();

			while (!endOfFile) {
				try {
					Apartment apartment = (Apartment) input.readObject();

					if (Main.DEBUG)
						System.out.println("New Apartment loaded: " + apartment);

					temp.add(apartment);
				} catch (EOFException eof) {
					endOfFile = true;
				}
			}
			return temp;
		} catch (IOException | ClassNotFoundException io) {
			System.out.println("Error occurred while loading Apartments: Apartment list empty" + io.getMessage());
			return new ArrayList<>();
		}

	}

	/**
	 * Loads a list of Tenants from tenants.dat
	 * @return List of loaded Tenants
	 * */
	public List<Tenant> loadTenants() {
		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("data/db/tenants.dat")))) {
			boolean endOfFile = false;
			List<Tenant> temp = new ArrayList<>();

			while (!endOfFile) {
				try {
					Tenant tenant = (Tenant) input.readObject();
					System.out.println();

					temp.add(tenant);
				} catch (EOFException eof) {
					endOfFile = true;
				}
			}
			return temp;
		} catch (IOException | ClassNotFoundException io) {
			System.out.println("Error occurred while loading Tenants: Tenant list empty" + io.getMessage());
			return new ArrayList<>();
		}

	}

	/**
	 * Loads a list of Candidates from candidates.dat
	 * @return List of loaded Candidates
	 * */
	public List<Candidate> loadCandidates() {
		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("data/db/contractors.dat")))) {
			boolean endOfFile = false;
			List<Candidate> temp = new ArrayList<>();

			while (!endOfFile) {
				try {
					Candidate candidate = (Candidate) input.readObject();

					temp.add(candidate);
				} catch (EOFException eof) {
					endOfFile = true;
				}
			}
			return temp;
		} catch (IOException | ClassNotFoundException io) {
			System.out.println("Error occurred while loading Candidates: Candidate list empty" + io.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Loads a list of Contractors from contractors.dat
	 * @return List of loaded Contractors
	 * */
	public List<Contractor> loadContractors() {
		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("data/db/contractors.dat")))) {
			boolean endOfFile = false;
			List<Contractor> temp = new ArrayList<>();

			while (!endOfFile) {
				try {
					Contractor contractor = (Contractor) input.readObject();
					System.out.println();

					temp.add(contractor);
				} catch (EOFException eof) {
					endOfFile = true;
				}
			}
			return temp;
		} catch (IOException | ClassNotFoundException io) {
			System.out.println("Error occurred while loading Contractors: Contractor list empty" + io.getMessage());
			return new ArrayList<>();
		}
	}


//	public boolean loadUsers() {
//		try(ObjectInputStream input = new ObjectInputStream(new
//				BufferedInputStream(new FileInputStream("data/db/users.dat")))) { boolean
//	 		endOfFile = false;
//
//	 	while(!endOfFile) {
//
//	 	} } catch(IOException e) {
//
//	 	}
//
//	 return true;
//	}

}
