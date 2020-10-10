package com.graham.apartmate.database.utilities.unordered;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.main.Main;
import javafx.collections.FXCollections;

/**
 * Testing data class; Holds all example data for demonstration
 *
 * @author Paul Graham Jr.
 * @version {@value Main#VERSION}
 * @since Milestone 3 (0.3)
 */
//TODO: Refactor to reflect recent changes
// Finalize bill samples
public class TestingData {

	private final List<Apartment> apartments;
	private final List<Tenant> tenants;
	private final List<Candidate> candidates;
	private final List<Contractor> contractors;

	/**
	 * Initializes sample data for debug use
	 * */
	public TestingData() {
		// Initialize lists
		apartments = new ArrayList<>();
		tenants = new ArrayList<>();
		candidates = new ArrayList<>();
		contractors = new ArrayList<>();


		List<String> usedAddresses = new ArrayList<>();

		//----------------------------------------------------------------------------------------------------
		//Apartment1
		//Apartment(int id, String address, String city, String state, String zipCode, int initialCapacity)
		//----------------------------------------------------------------------------------------------------
		List<String> address = getRandomAddress(usedAddresses);
		assert address != null;

		Apartment apartment = new Apartment(
				1,
				address.get(0).trim(),
				address.get(1).trim(),
				address.get(2).trim(),
				address.get(3).trim(),
				3
		);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Bills:
		//Bill(int id, int fk, String type, String companyName, String address, String phone, double bill, Account account)
		//----------------------------------------------------------------------------------------------------

		Bill bill = new Bill(
				1,
				apartment.getId(),
				"Mortgage",
				"Allen Bank",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						1,
						0,
						0,
						1,
						new TransactionLog(
								1,
								1,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						1,
						0,
						1,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				2,
				apartment.getId(),
				"Water Bill",
				"Synchrony Water Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						2,
						0,
						0,
						2,
						new TransactionLog(
								2,
								2,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						2,
						0,
						2,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				3,
				apartment.getId(),
				"Electric Bill",
				"Wall Electric",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						3,
						0,
						0,
						3,
						new TransactionLog(
								3,
								3,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						3,
						0,
						3,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				4,
				apartment.getId(),
				"Gas Bill",
				"Genesis Gas Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						4,
						0,
						0,
						4,
						new TransactionLog(
								4,
								4,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						4,
						0,
						4,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);

		apartments.add(apartment);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Apartment2
		//----------------------------------------------------------------------------------------------------
		address = getRandomAddress(usedAddresses);
		assert address != null;

		apartment = new Apartment(
				2,
				address.get(0).trim(),
				address.get(1).trim(),
				address.get(2).trim(),
				address.get(3).trim(),
				1
		);
		//----------------------------------------------------------------------------------------------------
		//Bills:
		//Bill(int id, int fk, String type, String companyName, String address, String phone, double bill, Account account)
		//----------------------------------------------------------------------------------------------------
		bill = new Bill(
				5,
				apartment.getId(),
				"Water Bill",
				"Montain Water Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						5,
						0,
						0,
						5,
						new TransactionLog(
								5,
								5,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						5,
						0,
						5,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				6,
				apartment.getId(),
				"Electric Bill",
				"Notta Electric Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						6,
						0,
						0,
						6,
						new TransactionLog(
								6,
								6,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						6,
						0,
						6,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				7,
				apartment.getId(),
				"Gas Bill",
				"Bilzard Gas Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						7,
						0,
						0,
						7,
						new TransactionLog(
								7,
								7,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						7,
						0,
						7,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		apartments.add(apartment);

		//----------------------------------------------------------------------------------------------------
		//Apartment3
		//----------------------------------------------------------------------------------------------------
		address = getRandomAddress(usedAddresses);
		assert address != null;


		apartment = new Apartment(
				3,
				address.get(0).trim(),
				address.get(1).trim(),
				address.get(2).trim(),
				address.get(3).trim(),
				5
		);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Bills:
		//Bill(int id, int fk, String type, String companyName, String address, String phone, double bill, Account account)
		//----------------------------------------------------------------------------------------------------
		bill = new Bill(
				8,
				apartment.getId(),
				"Mortgage",
				"Pursuit National Bank",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						8,
						0,
						0,
						8,
						new TransactionLog(
								8,
								8,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						8,
						0,
						8,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				9,
				apartment.getId(),
				"Water Bill",
				"Test Water Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						9,
						0,
						0,
						9,
						new TransactionLog(
								9,
								9,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						9,
						0,
						9,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				10,
				apartment.getId(),
				"Electric Bill",
				"Running out of names Electric",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						10,
						0,
						0,
						10,
						new TransactionLog(
								10,
								10,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						10,
						0,
						10,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		bill = new Bill(
				11,
				apartment.getId(),
				"Gas Bill",
				"I dunno Gas Company",
				listToString(Objects.requireNonNull(getRandomAddress(usedAddresses))),
				getRandomPhone(),
				new Account(
						11,
						0,
						0,
						11,
						new TransactionLog(
								11,
								11,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Contract(
						11,
						0,
						11,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		apartment.addBill(bill);
		apartments.add(apartment);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Add Tenants and related Tables
		//Apartment1 Tenants
		//Tenant(int id, int fk, String firstName, String lastName, String phone, String email, LocalDate dateOfBirth,
		//				  int annualIncome, String SSN, int numChildren, LocalDate movInDate,
		//				  PersonalContact contact1, PersonalContact contact2, Account initialAccount, Lease initialLease)
		//----------------------------------------------------------------------------------------------------
		String first = getRandomFirstName();
		String last = getRandomLastName();

		Tenant tenant = new Tenant(
				1,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),		//Create date generator
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						1,
						1,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						2,
						1,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						12,
						1,
						0,
						0,
						new TransactionLog(
								12,
								12,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						1,
						1,
						getRandomInt(4,12),
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);

		first = getRandomFirstName();
		last = getRandomLastName();
		tenant = new Tenant(
				2,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						3,
						2,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						4,
						2,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						13,
						2,
						0,
						0,
						new TransactionLog(
								13,
								13,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						2,
						2,
						getRandomInt(4,12),
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Apartment2 Tenants
		//----------------------------------------------------------------------------------------------------
		first = getRandomFirstName();
		last = getRandomLastName();
		tenant = new Tenant(
				3,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						5,
						3,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						6,
						3,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						14,
						3,
						0,
						0,
						new TransactionLog(
								14,
								14,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						3,
						3,
						getRandomInt(4,12),
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Apartment3 Tenants
		//----------------------------------------------------------------------------------------------------
		first = getRandomFirstName();
		last = getRandomLastName();
		tenant = new Tenant(
				4,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						7,
						4,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						8,
						4,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						15,
						4,
						0,
						0,
						new TransactionLog(
								15,
								15,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						4,
						4,
						getRandomInt(4,12),
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);

		first = getRandomFirstName();
		last = getRandomLastName();
		tenant = new Tenant(
				5,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						9,
						5,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						10,
						5,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						16,
						5,
						0,
						0,
						new TransactionLog(
								16,
								16,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						5,
						5,
						getRandomInt(4,12),
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);

		first = getRandomFirstName();
		last = getRandomLastName();
		tenant = new Tenant(
				6,
				apartments.get(0).getId(),
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomSsn(),
				getRandomInt(0,3),
				getRandomDate(3),
				new PersonalContact(
						11,
						6,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						12,
						6,
						0,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new Account(
						17,
						17,
						0,
						0,
						new TransactionLog(
								17,
								17,
								getRandomDouble(0,2000),
								LocalDate.now()
						)
				),
				new Lease(
						6,
						6,
						12,
						getRandomDouble(1000, 2000),
						0,
						getRandomDouble(500, 1000)
				)
		);
		tenants.add(tenant);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Add Candidates and related Tables
		//Apartment1
		//----------------------------------------------------------------------------------------------------
		first = getRandomFirstName();
		last = getRandomLastName();

		Candidate candidate = new Candidate(
				1,
				1,
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomSsn(),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomInt(0,3),
				new PersonalContact(
						13,
						0,
						1,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						14,
						0,
						1,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				)
		);
		candidates.add(candidate);

		first = getRandomFirstName();
		last = getRandomLastName();
		candidate = new Candidate(
				2,
				1,
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomSsn(),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomInt(0,3),
				new PersonalContact(
						15,
						0,
						2,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						16,
						0,
						2,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				)
		);
		candidates.add(candidate);


		//Apartment3
		first = getRandomFirstName();
		last = getRandomLastName();
		candidate = new Candidate(
				3,
				3,
				first,
				last,
				getRandomPhone(),
				getRandomEmail(first, last),
				getRandomSsn(),
				getRandomDate(1),
				getRandomInt(11000,90000),
				getRandomInt(0,3),
				new PersonalContact(
						17,
						0,
						3,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				),
				new PersonalContact(
						18,
						0,
						3,
						0,
						getRandomFirstName(),
						getRandomLastName(),
						getRandomPhone(),
						getRandomEmail(getRandomFirstName(),getRandomLastName())
				)
		);
		candidates.add(candidate);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------

		//----------------------------------------------------------------------------------------------------
		//Add Contractors and related Tables
		//Apartment1
		//----------------------------------------------------------------------------------------------------
		Contractor contractor = new Contractor(
				1,
				1,
				"Russet Contracting",
				getRandomPhone(),
				getRandomEmail("russet","Contracting"),
				new Contract(
						12,
						1,
						0,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		contractors.add(contractor);

		//Apartment3
		contractor = new Contractor(
				2,
				3,
				"Cesar Plumbing",
				getRandomPhone(),
				getRandomEmail("cesar","Plumbing"),
				new Contract(
						13,
						2,
						0,
						getRandomInt(3,12),
						getRandomDouble(1000,2000)
				)
		);
		contractors.add(contractor);
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------
	}

	private String getRandomFirstName() {
		try {
			List<String> nameList =
					Files.readAllLines(
							Paths.get("src" + File.separator+
									"com" +File.separator +
									"graham" + File.separator +
									"apartmate" + File.separator +
									"database" + File.separator +
									"utilities" + File.separator +
									"unordered" + File.separator +
									"res" + File.separator +
									"first_name_list.txt"));

			Random random = new Random();

			return nameList.get(random.nextInt(nameList.size() - 1));

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getRandomLastName() {
		try {
			List<String> nameList =
					Files.readAllLines(
							Paths.get("src" + File.separator+
									"com" +File.separator +
									"graham" + File.separator +
									"apartmate" + File.separator +
									"database" + File.separator +
									"utilities" + File.separator +
									"unordered" + File.separator +
									"res" + File.separator +
									"last_name_list.txt"));

			Random random = new Random();

			return nameList.get(random.nextInt(nameList.size() - 1));

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getRandomSsn() {
		return getRandomInt(100, 999) + "-" + getRandomInt(100, 999) + "-" + getRandomInt(1000, 9999);
	}

	private String getRandomEmail(String firstName, String lastName) {
		System.out.println(firstName);
		return firstName.charAt(1) + lastName + "@mail.com";
	}

	private List<String> getRandomAddress(List<String> used) {
		try {
			List<String> addressList =
					Files.readAllLines(
							Paths.get( "src" + File.separator+
										"com" +File.separator +
										"graham" + File.separator +
										"apartmate" + File.separator +
										"database" + File.separator +
										"utilities" + File.separator +
										"unordered" + File.separator +
										"res" + File.separator +
										"address_list.txt"));

			Random random = new Random();
			String output = addressList.get(random.nextInt(addressList.size() - 1));

			boolean usedBefore = !used.isEmpty();

			while (usedBefore) {
				for (String s : used) {
					if (output.equals(s)) {
						usedBefore = true;
						output = addressList.get(random.nextInt(addressList.size() - 1));
						break;
					} else {
						usedBefore = false;
					}
				}
			}

			return Arrays.asList(output.split(","));

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String listToString(List<String> list) {
		StringBuilder builder = new StringBuilder();

		for (String s : list) {
			builder.append(s);
		}

		return builder.toString();
	}

	private String getRandomBusinessName(int businessType) {
		String name = "";

		switch (businessType) {
			case 0:	//Mortgage
			case 1:	//Water company
			case 2:	//Electric company
			case 3:	//Gas company
			case 4:	//Contractor
			case 5:	//Plumber
			case 6:	//Electrician
		}

		return name;
	}

	private LocalDate getRandomDate(int type) {
		switch (type) {
			case 1:	//Birth Date
				return LocalDate.of(getRandomInt(1950,2000), getRandomInt(1,12), getRandomInt(1,28));
			case 2: //Recent Date
				return LocalDate.of(getRandomInt(2019,2020),getRandomInt(1,12), getRandomInt(1,28));
			case 3: //Past Recent Date
				LocalDate date;

				while(true) {
					date = LocalDate.of(getRandomInt(2018,2020),getRandomInt(1,12), getRandomInt(1,28));

					if (date.isBefore(LocalDate.now())) {
						return date;
					}
				}
			default:
				return null;
		}
	}

	private String getRandomPhone() {
		return  "(" + getRandomInt(100, 999) + ") " + getRandomInt(100, 999) + "-" + getRandomInt(1000, 9999);
	}

	private int getRandomInt(int min, int max) {
		Random random = new Random();

		return (int) (random.nextDouble() * ((max - min) + 1) + min);
	}

	private double getRandomDouble(int min, int max) {
		Random random = new Random();

		return (random.nextDouble() * ((max - min) + 1) + min);
	}

	/**
	 * Sets current lists to sample data
	 * <p>
	 * Warning! Deletes all current data!
	 * */
	public void useTestingData() {
		Database.getInstance().setApartments(FXCollections.observableArrayList(apartments));
		Database.getInstance().setTenants(FXCollections.observableArrayList(tenants));
		Database.getInstance().setCandidates(FXCollections.observableArrayList(candidates));
		Database.getInstance().setContractors(FXCollections.observableArrayList(contractors));
	}
}
