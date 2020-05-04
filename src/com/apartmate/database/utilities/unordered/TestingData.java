package com.apartmate.database.utilities.unordered;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.database.utilities.unordered.Heck;
import com.apartmate.main.Main;

/**
 * Testing data class; Holds all example data for demonstration
 * 
 * @since 0.1
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
//TODO: Javadoc's for every method
//TODO: Refactor to reflect recent changes
public class TestingData {

	private List<Apartment> apartments;
	private List<Tenant> tenants;
	private List<Candidate> candidates;
	private List<Contractor> contractors;

	//private List<Insurance> insurances;
	//private List<Issue> issues;
	//private List<Inspection> inspections;

	//private List<TnantInvoice> tnantInvoices;
	//private List<ContInvoice> contInvoices;


	public TestingData() {
		// Initialize lists
		apartments = new ArrayList<>();
		tenants = new ArrayList<>();
		candidates = new ArrayList<>();
		contractors = new ArrayList<>();

		//insurances = new ArrayList<>();
		//issues = new ArrayList<>();
		//inspections = new ArrayList<>();
		//tnantInvoices = new ArrayList<>();
		//contInvoices = new ArrayList<>();

		// Insert empty Table objects for index 0
		// apartments.add(new Apartment());
		//tenants.add(new Tenant());
		//candidates.add(new Candidate());
		//contractors.add(new Contractor());

		// Add sample data
		apartments.add(new Apartment(1, "1 S.Winston place", "Lincroft", "NJ", "08893", 5));
		apartments.add(new Apartment(2, "1194 Allison Way", "Edison", "NJ", "08812", 4));
		apartments.add(new Apartment(3, "8367 Long Street", "Wyndmoor", "PA", "18893", 5));
		apartments.add(new Apartment(4, "64 Walters Avenue", "Brooklyn", "NY", "07723", 3));
		apartments.add(new Apartment(5, "21 Emile Drive", "Matawan", "NJ", "08867", 1));
		//apartments.add(new Apartment(6, "Address", "", "NJ", "", 1, 1000));
		//apartments.add(new Apartment(7, "Address", "", "", "", 3, 1000));
		//apartments.add(new Apartment(8, "Address", "", "", "", 2, 1000));

		//insurances.add(new Insurance());
		//insurances.add(new Insurance());
		//insurances.add(new Insurance());
		//insurances.add(new Insurance());
		//insurances.add(new Insurance());

		//issues.add(new Issue());
		//issues.add(new Issue());
		//issues.add(new Issue());
		//issues.add(new Issue());
		//issues.add(new Issue());

		//inspections.add(new Inspection());
		//inspections.add(new Inspection());
		//inspections.add(new Inspection());
		//inspections.add(new Inspection());
		//inspections.add(new Inspection());

		//apartments.forEach(a -> {
			//insurances.forEach(insu -> {
			//	if (insu.getFk() == a.getId()) {
			//		a.getInsurances().add(insu);
			//	}
			//});

			//issues.forEach(iss -> {
			//	if (iss.getFk() == a.getId()) {
			//		a.getIssues().add(iss);
			//	}
			//});

			//inspections.forEach(insp -> {
			//	if (insp.getFk() == a.getId()) {
			//		a.getInspections().add(insp);
			//	}
			//});
		//});

		tenants.add(new Tenant(1, 1, "John", "Doe", "(000) 000-0000", "jhnDoe@email.com",
				Heck.parseDate("12-01-1996"), 65000, "000-00-0000", 1000, 1, Heck.parseDate("11-12-2013"),
				new Spouse(1, 1, "Jane", "Doe", "(000) 000-0000",
						"jneDoe@gmail.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		tenants.add(new Tenant(2, 2, "Marcus", "Aktus", "(000) 000-0000", "mAktus@email.com",
				Heck.parseDate("09-15-2001"), 75000, "000-00-0000", 1000, 0, Heck.parseDate("02-11-1999")));
		tenants.add(new Tenant(3, 3, "Phyllis", "Lorez", "(000) 000-0000", "pLorez@email.com",
				Heck.parseDate("04-30-1980"), 95000, "000-00-0000", 1000, 0, Heck.parseDate("04-15-2000"),
				new Spouse(2, 3, "Stephen", "George", "(000) 000-0000",
						"sGeorge@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		tenants.add(new Tenant(4, 4, "Marie", "Wilton", "(000) 000-0000", "mWilton@email.com",
				Heck.parseDate("07-12-1999"), 70000, "000-00-0000", 1000, 2, Heck.parseDate("09-09-2019")));
		tenants.add(new Tenant(5, 4, "Darius", "Cortez", "(000) 000-0000", "dCortez@email.com",
				Heck.parseDate("10-22-1995"), 60000, "000-00-0000", 1000, 3, Heck.parseDate("01-01-2015"),
				new Spouse(3, 4, "Carl", "Wilton", "(000) 000-0000",
						"cWilton@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));

		//tenant1
		//tnantInvoices.add(new TnantInvoice());
		//tnantInvoices.add(new TnantInvoice());
		//tnantInvoices.add(new TnantInvoice());

		//tenant2
		//tnantInvoices.add(new TnantInvoice());

		//tenant3
		//tnantInvoices.add(new TnantInvoice());
		//tnantInvoices.add(new TnantInvoice());

		//tenant4
		//tnantInvoices.add(new TnantInvoice());

		//tenant5
		//tnantInvoices.add(new TnantInvoice());
		//tnantInvoices.add(new TnantInvoice());

		//tenants.forEach(t -> {
		//	tnantInvoices.forEach(inv -> {
		//		if (inv.getFk() == t.getId()) {
		//			t.getInvoices().add(inv);
		//		}
		//	});
		//});

		candidates.add(new Candidate(1, 2, "Wallace", "Alice", "(000) 000-0000", "wAlice@email.com", "000-00-0000",
				new Spouse(1, 0, 1, "Molly", "Alice", "(000) 000-0000",
						"mAlice@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		candidates.add(new Candidate(2, 2, "Cadence", "Melodois", "(000) 000-0000", "cMelodois@email.com", "000-00-0000",
				new Spouse(2, 0, 2, "Alex", "Melodois", "(000) 000-0000",
						"aMelodois@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		candidates.add(new Candidate(3, 4, "Walter", "Evans", "(000) 000-0000", "wEvans@email.com", "000-00-0000",
				new Spouse(3, 0, 3, "Samantha", "Evans", "(000) 000-0000",
						"sEvans@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		candidates.add(new Candidate(4, 4, "Terence", "Carpenter", "(000) 000-0000", "tCarpenter@email.com", "000-00-0000",
				new Spouse(4, 0, 4, "Hope", "Carpenter", "(000) 000-0000",
						"hCarpenter@email.com", "000-00-0000",Heck.parseDate("12-12-1999"))));
		candidates.add(new Candidate(5, 4, "Joy", "Baggins", "(000) 000-0000", "jBaggins@email.com", "000-00-0000"));

		//contractor 1
		contractors.add(new Contractor(1, 1, "Pete's Plumbing", 1000,"(000) 000-0000","test@testing.test"));
		contractors.add(new Contractor(2, 3, "Own Company", 1000,"(000) 000-0000","test@testing.test"));
		contractors.add(new Contractor(3, 4, "Own Company", 1000,"(000) 000-0000","test@testing.test"));
		contractors.add(new Contractor(4, 5, "Own Company", 1000,"(000) 000-0000","test@testing.test"));
		contractors.add(new Contractor(5, 5, "Miguel Construction", 1000,"(000) 000-0000","test@testing.test"));

		//contractor 2
		//contInvoices.add(new ContInvoice());
		//contInvoices.add(new ContInvoice());

		//contractor 3
		//contInvoices.add(new ContInvoice());
		//contInvoices.add(new ContInvoice());
		//contInvoices.add(new ContInvoice());

		//contractor 4
		//contInvoices.add(new ContInvoice());

		//contractor 5
		//contInvoices.add(new ContInvoice());

		//contractors.forEach(c -> {
		//	contInvoices.forEach(inv -> {
		//		if (inv.getFk() == c.getId()) {
		//			c.getInvoices().add(inv);
		//		}
		//	});
		//});
	}

	public void useTestingData() {
		Database.getInstance().setApartments(apartments);
		Database.getInstance().setTenants(tenants);
		Database.getInstance().setCandidates(candidates);
		Database.getInstance().setContractors(contractors);
	}
}
