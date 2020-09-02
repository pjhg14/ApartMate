package com.graham.apartmate.database.utilities.unordered;

import java.util.ArrayList;
import java.util.List;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.main.Main;

/**
 * Testing data class; Holds all example data for demonstration
 *
 * @author Paul Graham Jr.
 * @version {@value Main#VERSION}
 * @since Milestone 3 (0.3)
 */
//TODO: Refactor to reflect recent changes
public class TestingData {

	private List<Apartment> apartments;
	private List<Tenant> tenants;
	private List<Candidate> candidates;
	private List<Contractor> contractors;

	private List<Bill> bills;

	private List<NoteLog> issues;
	private List<NoteLog> inspections;


	/**
	 * Initializes sample data for debug use
	 * */
	public TestingData() {
		// Initialize lists
		apartments = new ArrayList<>();
		tenants = new ArrayList<>();
		candidates = new ArrayList<>();
		contractors = new ArrayList<>();

		//Add apartments and related Tables

		//Add Tenants and related Tables

		//Add Candidates and related Tables

		//Add Contractors and related Tables
	}

	/**
	 * Sets current lists to sample data
	 * <p>
	 * Warning! Deletes all current data!
	 * */
	public void useTestingData() {
//		Database.getInstance().setApartments(apartments);
//		Database.getInstance().setTenants(tenants);
//		Database.getInstance().setCandidates(candidates);
//		Database.getInstance().setContractors(contractors);
	}
}
