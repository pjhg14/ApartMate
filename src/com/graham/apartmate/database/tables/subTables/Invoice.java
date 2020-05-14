package com.graham.apartmate.database.tables.subTables;

import java.util.Comparator;
import java.util.Date;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.utilities.unordered.Heck;
import com.graham.apartmate.main.Main;

/**
 * Overall Invoice object
 * <p>
 * Records the invoice information of various Tables
 * <p>
 * Replaces individual Invoices (TnantInvoice, ContInvoce, )
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Capstone (0.8)
 */
public class Invoice extends Table {

	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 1L;

	/**
	 * Current payment
	 * */
	private double payment;

	/**
	 * Current monthly due
	 * */
	private double dues;

	/**
	 * Current balance
	 * */
	private double balance;

	/**
	 * Total amount paid
	 * */
	private double totalPaid;

	/**
	 * Total amount due
	 * */
	private double totalDue;

	/**
	 * Date payment was made
	 * */
	private Date paymentDate;

	/**
	 * Date payment is due
	 * */
	private Date dueDate;

	/**
	 * Invoice sorting constant
	 * */
	public static final Comparator<Invoice> INVOICE_BY_DATE = Comparator.comparing(Invoice::getDueDate);

	/**
	 * Default constructor
	 * */
	public Invoice() {
		this(0,0,0,0,0,0,0, Heck.MIN_DATE,Heck.MIN_DATE);
	}

	/**
	 * Initial payment constructor
	 * */
	public Invoice(int id, int fk, double paymentAmount, double dues, Date paymentDate, Date dueDate) {
		this(id,fk,paymentAmount,dues,0,0,0,paymentDate,dueDate);
	}

	/**
	 * Full constructor
	 * */
	public Invoice(int id, int fk, double paymentAmount, double dues, double balance, double totalPaid, double totalDue,
				   Date paymentDate, Date dueDate) {
		super(id, fk);
		this.payment = paymentAmount;
		this.dues = dues;
		this.balance = balance;
		this.totalPaid = totalPaid;
		this.totalDue = totalDue;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
	}

	/**
	 * Adds an amount to the current payment
	 * @return New payment amount
	 * */
	public double addPayment(double amount) {
		return payment += amount;
	}

	/**
	 * Subtract an amount from the current payment
	 * @return New payment amount
	 * */
	public double subtractPayment(double amount) {
		return payment -= amount;
	}

	// *************************************************************
	// General getters and setters
	/**
	 * Getter:
	 * <p>
	 * Gets current payment
	 * @return payment
	 * */
	public double getPayment() {
		return payment;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets current payment
	 * @param paymentAmount New payment amount
	 * */
	public void setPayment(double paymentAmount) {
		this.payment = paymentAmount;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets current amount due each month
	 * @return monthly due
	 * */
	public double getDues() {
		return dues;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets amount due each month
	 * @param dues New monthly dues
	 * */
	public void setDues(double dues) {
		this.dues = dues;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets current balance
	 * @return balance
	 * */
	public double getBalance() {
		return balance;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets current balance
	 * @param balance New current balance
	 * */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets total paid
	 * @return total paid
	 * */
	public double getTotalPaid() {
		return totalPaid;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets total paid
	 * @param totalPaid New total paid
	 * */
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets total due
	 * @return total due
	 * */
	public double getTotalDue() {
		return totalDue;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets total due
	 * @param totalDue New total due
	 * */
	public void setTotalDue(double totalDue) {
		this.totalDue = totalDue;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets payment date
	 * @return payment date
	 * */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets payment date
	 * @param paymentDate New payment date
	 * */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Getter:
	 * v
	 * Gets payment due date
	 * @return due date
	 * */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets payment due date
	 * @param dueDate New due date
	 * */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	// *************************************************************
}
