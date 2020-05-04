package com.apartmate.database.tables.subTables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.apartmate.database.tables.mainTables.Table;
import com.apartmate.main.Main;

/**
 * Insurance object
 * <p>
 * Records the insurance information of an apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
public class Insurance extends Table {

	/***/
	private static final long serialVersionUID = 1L;

	/***/
	private String name;

	/***/
	private double bill;

	/***/
	private String phone;

	/***/
	private String email;

	/***/
	private ArrayList<Invoice> invoices;

	/***/
	public static final Comparator<Insurance> INS_BY_NAME = Comparator.comparing(Insurance::getName);

	/***/
	public static final Comparator<Insurance> INS_BY_EMAIL = Comparator.comparing(Insurance::getEmail);

	/***/
	public Insurance() {
		this(0,0,"",0,"","");
	}

	/***/
	public Insurance(int id, int fk, String name, double bill, String phone, String email) {
		super(id, fk);
		this.name = name;
		this.bill = bill;
		this.phone = phone;
		this.email = email;

		invoices = new ArrayList<>();
	}

	@Override
	public String toString() {
		return name + ": " + email;
	}

	// *************************************************************
	// General getters and setters
	/***/
	public String getName() {
		return name;
	}

	/***/
	public void setName(String name) {
		this.name = name;
	}

	/***/
	public double getBill() {
		return bill;
	}

	/***/
	public void setBill(double bill) {
		this.bill = bill;
	}

	/***/
	public String getPhone() {
		return phone;
	}

	/***/
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/***/
	public String getEmail() {
		return email;
	}

	/***/
	public void setEmail(String email) {
		this.email = email;
	}

	/***/
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}

	/***/
	public void setInvoices(ArrayList<Invoice> invoices) {
		this.invoices = invoices;
	}
	// *************************************************************

}
