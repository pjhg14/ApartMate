package com.graham.apartmate.database.tables.mainTables;

import java.util.ArrayList;
import java.util.List;

import com.graham.apartmate.database.tables.subTables.Invoice;
import com.graham.apartmate.main.Main;

/**
 * Contractor object
 * <p>
 * Records information of contractors that have worked on a respective Apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Contractor extends Table {

	/**
	 * Serialization long*/
	private static final long serialVersionUID = 1L;

	/**
	 * Contractor's name
	 * */
	private String name;

	/**
	 * Monthly payment
	 * */
	private double bill;

	/**
	 * Contractor's phone number
	 * */
	private String phone;

	/**
	 * Contractor's email
	 * */
	private String email;

	/**
	 * List of Contractor's Invoices
	 * */
	private List<Invoice> invoices;

	/**
	 * Default constructor
	 * */
	public Contractor() {
		this(0,0,"",0,"","");
	}

	/**
	 * Full constructor
	 * */
	public Contractor(int id, int fk, String name, double bill, String phone, String email) {
		super(id, fk);
		this.name = name;
		this.bill = bill;
		this.phone = phone;
		this.email = email;

		invoices = new ArrayList<>();
	}

	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Contractor Id and name
	 * */
	@Override
	public String toString() {
		return String.format("Contractor: id; %s, name; %s", super.getId() ,name);
	}

	// *************************************************************
	// General getters and setters
	/**
	 * Getter:
	 * Gets Contractor's business name
	 * @return name
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Setter:
	 * Sets Contractor's business name
	 * @param name New name
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter:
	 * Gets monthly bill for Contractor
	 * @return bill
	 * */
	public double getBill() {
		return bill;
	}

	/**
	 * Setter:
	 * Sets monthly bill for Contractor
	 * @param bill New bill
	 * */
	public void setBill(double bill) {
		this.bill = bill;
	}

	/**
	 * Getter:
	 * Gets Contractor's phone number
	 * @return phone #
	 * */
	public String getPhone() {
		return phone;
	}

	/**
	 * Setter:
	 * Sets Contractor's phone number
	 * @param phone New phone #
	 * */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter:
	 * Gets Contractor's email
	 * @return email
	 * */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter:
	 * Sets Contrator's email
	 * @param email New email
	 * */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter:
	 * Gets list of Invoices for Contractor
	 * @return list of Invoices
	 * */
	public List<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * Setter:
	 * Sets list of Invoices for Contractor
	 * @param invoices New list of Invoices
	 * */
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	// *************************************************************
}
