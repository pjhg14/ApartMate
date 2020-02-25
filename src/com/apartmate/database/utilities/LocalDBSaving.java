package com.apartmate.database.utilities;

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

/**
 * Responsible for the saving of tables to a local file
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr <pjhg14@gmail.com>
 */
public class LocalDBSaving {

	private final File DIRECTORY = new File("data/db");

	public LocalDBSaving() {

	}

	public boolean createDir() {
		if (!DIRECTORY.exists()) {
			DIRECTORY.mkdir();
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Saving Methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	public boolean saveApartments() {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/apartments.dat")))) {
			for (Apartment apartment : Database.getInstance().getApartments()) {
				output.writeObject(apartment);
			}
		} catch (IOException io) {
			System.out.println("Exception occurred: " + io.getMessage());
			return false;
		}
		return true;
	}

	public boolean saveTenants() {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/tenants.dat")))) {
			for (Tenant tenant : Database.getInstance().getTenants()) {
				output.writeObject(tenant);
			}

			return true;
		} catch (IOException io) {
			System.out.println("Exception occurred: " + io.getMessage());
			return false;
		}
	}

	public boolean saveCandidates() {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/candidates.dat")))) {
			for (Candidate candidate : Database.getInstance().getCandidates()) {
				output.writeObject(candidate);
			}

			return true;
		} catch (IOException io) {
			System.out.println("Exception ocurred: " + io.getMessage());
			return false;
		}
	}

	public boolean saveContractors() {
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("data/db/contractors.dat")))) {
			for (Contractor contractor : Database.getInstance().getContractors()) {
				output.writeObject(contractor);
			}

			return true;
		} catch (IOException io) {
			System.out.println("Exception ocurred: " + io.getMessage());
			return false;
		}
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Loading Methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	public List<Apartment> loadApartments() {
		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("data/db/apartments.dat")))) {
			boolean endOfFile = false;
			List<Apartment> temp = new ArrayList<>();

			while (!endOfFile) {
				try {
					Apartment apartment = (Apartment) input.readObject();
					System.out.println("New Apartment loaded: " + apartment);

					temp.add(apartment);
				} catch (EOFException eof) {
					endOfFile = true;
				}
			}
			return temp;
		} catch (IOException | ClassNotFoundException io) {
			return null;
		}

	}

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
			System.out.println("" + io.getMessage());
			return null;
		}

	}

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
			return null;
		}
	}

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
			return null;
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
