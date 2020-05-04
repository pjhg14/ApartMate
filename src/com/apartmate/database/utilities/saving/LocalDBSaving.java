package com.apartmate.database.utilities.saving;

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

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.main.Main;

/**
 * Responsible for the saving of tables to a local file
 *
 * @author Paul Graham Jr <pjhg14@gmail.com>
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
//TODO: Finalize Class
public class LocalDBSaving {

	private static final File DIRECTORY = new File("data/db");

	/**
	 * Checks if the directory 'com/apartmate/data/db' exists and creates it if not so
	 * @return true/false depending on whether the directory exists or not
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

	/*
	 * public boolean loadUsers() throws ClassNotFoundException{
	 * try(ObjectInputStream input = new ObjectInputStream(new
	 * BufferedInputStream(new FileInputStream("data/db/users.dat")))) { boolean
	 * endOfFile = false;
	 * 
	 * while(!endOfFile) {
	 * 
	 * } } catch(IOException e) {
	 * 
	 * }
	 * 
	 * return true; }
	 */
}
