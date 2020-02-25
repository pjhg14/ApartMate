package com.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apartmate.database.tables.mainTables.Table;
import com.apartmate.database.tables.subTables.Spouse;
import com.apartmate.database.tables.subTables.TnantInvoice;

/**
 * Tenant object
 * <p>
 * Records the information of a tenant of a specific Apartment
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Tenant extends Table {

	// Serializabe ID
	private static final long serialVersionUID = 1L;

	// Mandatory fields
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String SSN;
	private double rent; // amount tenant owes every month
	private int numChildren;
	private Date movinDate; // tenant's move in date

	// Optional/uninitialized fields
	private Date dateOfBirth;
	private int annualIncome; // tenants's annual income(optional)
	private boolean slatedForEviction;
	private String evictReason;
	private Date movoutDate; // date when tenant has been evicted, will prompt deletion reminder if desired

	// Tenant sub-tables
	private Spouse spouse; // data of tenant's spouse
	private List<TnantInvoice> tnantInvoices;

	// Default constructor; used for import from SQL
	public Tenant() {
		super();
		firstName = "";
		lastName = "";
		phone = "";
		email = "";
		dateOfBirth = new Date();
		annualIncome = -1;
		SSN = "";
		rent = -1;
		numChildren = 0;
		movinDate = new Date();
		movoutDate = new Date();
		slatedForEviction = false;
		evictReason = "";

		spouse = null;
		tnantInvoices = new ArrayList<>();
	}

	// Constructor w/o Spouse
	public Tenant(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
			int annualIncome, String SSN, double rent, int numChildren, Date movinDate) {
		super(id, fk);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.annualIncome = annualIncome;
		this.SSN = SSN;
		this.rent = rent;
		this.numChildren = numChildren;
		this.movinDate = movinDate;
		movoutDate = new Date();
		slatedForEviction = false;
		evictReason = "";

		spouse = null;
		tnantInvoices = new ArrayList<>();
	}

	// Constructor w/ Spouse
	public Tenant(int id, int fk, String firstName, String lastName, String phone, String email, Date dateOfBirth,
			int annualIncome, String SSN, double rent, int numChildren, Date movinDate, Spouse spouse) {
		super(id, fk);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.annualIncome = annualIncome;
		this.SSN = SSN;
		this.rent = rent;
		this.numChildren = numChildren;
		this.movinDate = movinDate;
		movoutDate = new Date();
		slatedForEviction = false;
		evictReason = "";

		this.spouse = spouse;
		tnantInvoices = new ArrayList<>();
	}

	// ToString method; prints full name (last then first)
	@Override
	public String toString() {
		return "Tenant " + super.getId() + " " + lastName + ", " + firstName;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(int annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	public Date getMovinDate() {
		return movinDate;
	}

	public void setMovinDate(Date movinDate) {
		this.movinDate = movinDate;
	}

	public Date getMovoutDate() {
		return movoutDate;
	}

	public void setMovoutDate(Date movoutDate) {
		this.movoutDate = movoutDate;
	}

	public boolean isEvicted() {
		return slatedForEviction;
	}

	public void setEvicted(boolean isEvicted) {
		this.slatedForEviction = isEvicted;
	}

	public String getEvictReason() {
		return evictReason;
	}

	public void setEvictReason(String evictReason) {
		this.evictReason = evictReason;
	}

	public Spouse getSpouse() {
		return spouse;
	}

	public void setSpouse(Spouse spouse) {
		this.spouse = spouse;
	}

	public List<TnantInvoice> getInvoices() {
		return tnantInvoices;
	}

	public void setInvoices(List<TnantInvoice> tnantInvoices) {
		this.tnantInvoices = tnantInvoices;
	}
	// *************************************************************
}
