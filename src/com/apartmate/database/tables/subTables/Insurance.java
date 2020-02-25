package com.apartmate.database.tables.subTables;

import java.util.Date;

import com.apartmate.database.tables.mainTables.Table;

/**
 * Insurance object
 * <p>
 * Records the insurance information of an apartment
 * 
 * @since Can we call this an alpha? (0.1)
 * @version MileStone 4 (0.4)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Insurance extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double payment;
	private double balance;
	private double totalPaid;
	private double totalDue;
	private Date paymentDate;
	private Date dueDate;

	public Insurance() {
		super();
		payment = 0;
		balance = 0;
		totalPaid = 0;
		paymentDate = new Date();
		dueDate = new Date();
	}

	// Mandatory field constructor
	public Insurance(int id, int fk, double payment, Date paymentDate, Date dueDate) {
		super(id, fk);
		this.payment = payment;
		this.balance = 0;
		this.totalPaid = 0;
		this.totalDue = 0;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}

	// Full Constructor
	public Insurance(int id, int fk, double payment, double totalPaid, double totalDue, Date paymentDate,
			Date dueDate) {
		super(id, fk);
		this.payment = payment;
		this.totalPaid = totalPaid;
		this.totalDue = totalDue;
		this.balance = totalDue - totalPaid;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public double getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(double totalDue) {
		this.totalDue = totalDue;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	// *************************************************************
}
