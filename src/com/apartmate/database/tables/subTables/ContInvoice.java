package com.apartmate.database.tables.subTables;

import java.util.Date;

import com.apartmate.database.tables.mainTables.Table;

/**
 * Contractor Invoice object
 * <p>
 * Records the invoice information of a contractor
 * 
 * @since Can we call this an alpha? (0.1)
 * @version MileStone 4 (0.4)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class ContInvoice extends Table {

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

	public ContInvoice() {
		super();
		payment = -1;
		balance = -1;
		paymentDate = new Date();
		dueDate = new Date();
	}

	public ContInvoice(int id, int fk, double paymentAmount, Date paymentDate, Date dueDate) {
		super(id, fk);
		this.payment = paymentAmount;
		this.balance = 0;
		this.totalPaid = 0;
		this.totalDue = 0;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}

	public ContInvoice(int id, int fk, double paymentAmount, double balance, double totalPaid, double totalDue,
			Date paymentDate, Date dueDate) {
		super(id, fk);
		this.payment = paymentAmount;
		this.balance = balance;
		this.totalPaid = totalPaid;
		this.totalDue = totalDue;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double paymentAmount) {
		this.payment = paymentAmount;
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
