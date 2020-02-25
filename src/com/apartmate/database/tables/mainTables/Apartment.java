package com.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.tables.subTables.Inspection;
import com.apartmate.database.tables.subTables.Insurance;
import com.apartmate.database.tables.subTables.Issue;
//import com.apartmate.database.tables.subTables.Room;

/**
 * Apartment object
 * <p>
 * Records information of owned Apartments as well as contain the Lists for
 * Insurance, Issue, and Inspection
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Apartment extends Table {

	private static final long serialVersionUID = 1L;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private int capacity;
	private int numTenants;
	private double insBill;

	// Sub-tales 1
	// private List<Tenant> tenants;
	// private List<Contractor> contractors;

	// Sub-tables 2

	private List<Insurance> insurances;
	private List<Issue> issues;
	private List<Inspection> inspections;
	// private List<Room> rooms;

	// Default constructor; creates invalid apartment

	public Apartment() {
		super();
		address = "";
		city = "";
		state = "";
		zipCode = "";
		capacity = 0;
		numTenants = 0;
		insBill = 0;

		insurances = new ArrayList<>();
		issues = new ArrayList<>();
		inspections = new ArrayList<>();
		// rooms = new ArrayList<>();
	}

	public Apartment(int id, String address, String city, String state, String zipCode, int capacity) {
		super(id);
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.capacity = capacity;
		numTenants = 0;
		insBill = 0;

		insurances = new ArrayList<>();
		issues = new ArrayList<>();
		inspections = new ArrayList<>();
		// rooms = new ArrayList<>();
	}

	public Apartment(int id, String address, String city, String state, String zipCode, int capacity, double insBill) {
		super(id);
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.capacity = capacity;
		numTenants = 0;
		this.insBill = insBill;

		insurances = new ArrayList<>();
		issues = new ArrayList<>();
		inspections = new ArrayList<>();
		// rooms = new ArrayList<>();
	}

	public Apartment(int id, String address, String city, String state, String zipCode, int capacity, int numTenants,
			double insBill) {
		super(id);
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.capacity = capacity;
		this.numTenants = numTenants;
		this.insBill = insBill;

		insurances = new ArrayList<>();
		issues = new ArrayList<>();
		inspections = new ArrayList<>();
		// rooms = new ArrayList<>();
	}

	// ToString method
	@Override
	public String toString() {
		return "Apartment " + super.getId() + " " + address + " " + city + " " + state + " " + zipCode + " " + capacity
				+ " " + numTenants + " " + insBill;
	}

	public String getLocation() {
		return address + " " + city + "," + state + " " + zipCode;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNumTenants() {
		return numTenants;
	}

	public void setNumTenants(int numTenants) {
		this.numTenants = numTenants;
	}

	public double getInsBill() {
		return insBill;
	}

	public void setInsBill(double insurance) {
		this.insBill = insurance;
	}

	/*
	 * public List<Tenant> getTenants(){ return tenants; }
	 * 
	 * public void setTenants(List<Tenant> tenants) { this.tenants = tenants; }
	 * 
	 * public List<Contractor> getContractors(){ return contractors; }
	 * 
	 * public void setContractors(List<Contractor> contractors) { this.contractors =
	 * contractors; }
	 */
	public List<Insurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Inspection> getInspections() {
		return inspections;
	}

	public void setInspections(List<Inspection> inspections) {
		this.inspections = inspections;
	}

	/*
	 * public List<Room> getRooms() { return rooms; }
	 * 
	 * public void setRooms(List<Room> rooms) { this.rooms = rooms; }
	 */
	// *************************************************************
}
