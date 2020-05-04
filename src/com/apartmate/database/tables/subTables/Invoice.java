package com.apartmate.database.tables.subTables;

import java.util.Comparator;
import java.util.Date;

import com.apartmate.database.tables.mainTables.Table;
import com.apartmate.database.utilities.unordered.Heck;
import com.apartmate.main.Main;

/**
 * Overall Invoice object
 * <p>
 * Records the invoice information of a tenant
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
public class Invoice extends Table {

	/***/
	private static final long serialVersionUID = 1L;

	/***/
	private double payment;

	/***/
	private double dues;

	/***/
	private double balance;

	/***/
	private double totalPaid;

	/***/
	private double totalDue;

	/***/
	private Date paymentDate;

	/***/
	private Date dueDate;

	/***/
	public static final Comparator<Invoice> INVOICE_BY_DATE = Comparator.comparing(Invoice::getDueDate);

	/***/
	public Invoice() {
		this(0,0,0,0,0,0,0, Heck.MIN_DATE,Heck.MIN_DATE);
	}

	/***/
	public Invoice(int id, int fk, double paymentAmount, double dues, Date paymentDate, Date dueDate) {
		this(id,fk,paymentAmount,dues,0,0,0,paymentDate,dueDate);
	}

	/***/
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

	// *************************************************************
	// General getters and setters
	/***/
	public double getPayment() {
		return payment;
	}

	/***/
	public void setPayment(double paymentAmount) {
		this.payment = paymentAmount;
	}

	/**
	 * @return dues
	 */
	public double getDues() {
		return dues;
	}

	/**
	 * @param dues ...
	 */
	public void setDues(double dues) {
		this.dues = dues;
	}

	/***/
	public double getBalance() {
		return balance;
	}

	/***/
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/***/
	public double getTotalPaid() {
		return totalPaid;
	}

	/***/
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	/***/
	public double getTotalDue() {
		return totalDue;
	}

	/***/
	public void setTotalDue(double totalDue) {
		this.totalDue = totalDue;
	}

	/***/
	public Date getPaymentDate() {
		return paymentDate;
	}

	/***/
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/***/
	public Date getDueDate() {
		return dueDate;
	}

	/***/
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	// *************************************************************
}
