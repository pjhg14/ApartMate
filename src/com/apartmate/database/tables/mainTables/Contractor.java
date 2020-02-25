package com.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.tables.subTables.ContInvoice;

/**
 * Contractor object
 * <p>
 * Records information of contractors that have worked on a respective Apartment
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Contractor extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private double bill;
	private String phone;
	private String email;

	private List<ContInvoice> contInvoices;

	public Contractor() {
		super();
		name = "";
		bill = 0;
		email = "";
		phone = "";

		contInvoices = new ArrayList<>();
	}

	public Contractor(int id, int fk, String name) {
		super(id, fk);
		this.name = name;

		contInvoices = new ArrayList<>();
	}

	public Contractor(int id, int fk, String name, double bill, String phone, String email) {
		super(id, fk);
		this.name = name;
		this.bill = bill;
		this.phone = phone;
		this.email = email;

		contInvoices = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Contractor name: " + name + "Bill = " + bill;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
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

	public List<ContInvoice> getInvoices() {
		return contInvoices;
	}

	public void setInvoices(List<ContInvoice> contInvoices) {
		this.contInvoices = contInvoices;
	}

	// *************************************************************
}
