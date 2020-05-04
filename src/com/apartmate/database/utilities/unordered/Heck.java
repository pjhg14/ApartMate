package com.apartmate.database.utilities.unordered;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
//import java.util.List;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.*;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.main.Main;


/**
 * Class that should handle all timed events including Java Timer daemons
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Milestone 3 (0.3)
 */
//TODO: Javadoc's for every method
//TODO: Move update calculations to Calculations class
public class Heck {

	/***/
	@SuppressWarnings("unused")
	private class Daemon extends TimerTask {

		private int permutations;

		private Daemon() {
			permutations = 0;
		}

		// Update

		/**
		 * Checks if the last invoice of a list is overdue and adds a new invoice if so
		 * @param invoices The list of invoices to check and potentially add to
		 * */
		public void autoAddInvoice(List<Invoice> invoices) {
			Invoice last = invoices.get(invoices.size() - 1);
			LocalDateTime tempTime = LocalDateTime.ofInstant(last.getDueDate().toInstant(), ZoneId.systemDefault());

			if (tempTime.isBefore(LocalDateTime.now())) {
				invoices.add(new Invoice(last.getId() + 1, last.getFk(), 0,last.getDues(),
						null, Date.from(tempTime.plusMonths(1).toInstant(ZoneOffset.UTC))));
			}
		}

		/***/
		@Override
		public void run() {
			Database.getInstance().getApartments().forEach(apt -> {
				apt.getInsurances().forEach(ins -> {
					//dunno if following is needed: sets dues field of invoice to the bill of insurance
					//ins.getInvoices().get(ins.getInvoices().size() -1).setDues(ins.getBill());
					autoAddInvoice(ins.getInvoices());
				});

				apt.getBills().forEach(bill -> autoAddInvoice(bill.getInvoices()));
			});

			Database.getInstance().getTenants().forEach(tnant -> autoAddInvoice(tnant.getInvoices()));

			Database.getInstance().getContractors().forEach(cont -> autoAddInvoice(cont.getInvoices()));

			permutations++;
		}

		/***/
		public int getPermutations() {
			return permutations;
		}
	}

	// Month long integer values
	private final long JANUARY = 2678400000L; // 31days
	private final long FEBUARY = 2419200000L; // 28days
	private final long LEAPFEB = 2505600000L; // 29days
	private final long MARCH = 2678400000L; // 31days
	private final long APRIL = 2592000000L; // 30days
	private final long MAY = 2678400000L; // 31days
	private final long JUNE = 2592000000L; // 30days
	private final long JULY = 2678400000L; // 31days
	private final long AUGUST = 2678400000L; // 31days
	private final long SEPTEMBER = 2592000000L; // 30days
	private final long OCTOBER = 2678400000L; // 31days
	private final long NOVEMBER = 2678400000L; // 31days
	private final long DECEMBER = 2678400000L; // 31days

	public static final Date MIN_DATE = parseDate("01-01-0001");

	/***/
	private Timer timer;
	/***/
	private TimerTask task;

	/***/
	public Heck() {
		timer = new Timer("Daemon", true);
		task = new Daemon();
	}

	/***/
	public void startDueDateAutomation() {
		timer.schedule(task, 60000);
	}

	/***/
	public void stopDueDateAutomation() {
		timer.cancel();
	}

	// --------------------------------------------------------
	// Update Table 'dateModified' methods/////////////////////
	// --------------------------------------------------------
	/***/
	public void upDateModified(Table table) {
		table.getDateModified().setTime(System.currentTimeMillis());
	}
	// --------------------------------------------------------
	// --------------------------------------------------------

	// --------------------------------------------------------
	// Update Invoice/Insurance total & balance fields////////
	// --------------------------------------------------------
	/**
	 * Updates the totals for a list of invoices <p>
	 * list must be initialized with one invoice
	 * */
	public void updateTotals(List<Invoice> invoices) {
		if (invoices.size() > 0 ) {
			double totalDue = 0, totalPaid = 0;
			//Iterate through list
			for (Invoice invoice : invoices) {
				totalDue += invoice.getDues();
				totalPaid += invoice.getPayment();
			}

			invoices.get(invoices.size() - 1).setTotalPaid(totalPaid);
			invoices.get(invoices.size() - 1).setTotalDue(totalDue);
			invoices.get(invoices.size() - 1).setBalance(totalDue - totalPaid);
		}
	}

	/**
	 * Updates the totals for a list of invoices up to a certain index of the list <p>
	 * list must be initialized with one invoice
	 * */
	public void updateTotals(List<Invoice> invoices, int index) {
		if (invoices.size() > 0) {
			double totalDue = 0, totalPaid = 0;
			//Iterate through list
			for (int i = 0; i <= index; i++) {
				totalDue += invoices.get(i).getDues();
				totalPaid += invoices.get(i).getPayment();
			}

			invoices.get(index).setTotalPaid(totalPaid);
			invoices.get(index).setTotalDue(totalDue);
			invoices.get(index).setBalance(totalDue - totalPaid);
		}
	}
	// --------------------------------------------------------
	// --------------------------------------------------------

	@SuppressWarnings("unused")
	private long checkMonth() {
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();

		switch (Calendar.getInstance().get(Calendar.MONTH)) {
		case 0:
			return JANUARY;
		case 1:
			if (cal.isLeapYear(Calendar.getInstance().get(Calendar.YEAR))) {
				return LEAPFEB;
			} else {
				return FEBUARY;
			}
		case 2:
			return MARCH;
		case 3:
			return APRIL;
		case 4:
			return MAY;
		case 5:
			return JUNE;
		case 6:
			return JULY;
		case 7:
			return AUGUST;
		case 8:
			return SEPTEMBER;
		case 9:
			return OCTOBER;
		case 10:
			return NOVEMBER;
		case 11:
			return DECEMBER;
		default:
			return -1;
		}
	}
	
	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("MM-dd-yyyy").parse(date);
		} catch (ParseException e) {
			System.out.println("Error Parsing string");
			return null;
		}
	}

	public static Date parseDateTime(String date) {
		try {
			return new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(date);
		} catch (ParseException e) {
			System.out.println("Error Parsing string");
			return null;
		}
	}

}
