package com.graham.apartmate.database.utilities.unordered;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.graham.apartmate.database.dbMirror.Database;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.database.tables.mainTables.Table;


/**
 * Class that should handle all timed events including Java Timer daemons
 *
 * @author Paul Graham Jr.
 * @version {@value Main#VERSION}
 * @since Milestone 3 (0.3)
 */
//TODO: Move update calculations to Calculations class
public class Heck {

	/**
	 * Holds the minimum date for date fields
	 * <p>
	 * Effectively similar to LocalDate.MIN and will be replaced by such
	 * upon refactor to Java.time from Java.util.Date
	 * */
	public static final Date MIN_DATE = parseDate("01-01-0001");

	/**
	 * Timer that schedules Daemon's tasks
	 * */
	private Timer timer;

	/**
	 * Essentially Daemon
	 * @see Daemon
	 * */
	private TimerTask task;

	/**
	 * Sets up the Daemon and the timer it operates on
	 * */
	public Heck() {
		timer = new Timer("Daemon", true);
		task = new Daemon();
	}

	/**
	 * Starts automation timer
	 * */
	public void startDueDateAutomation() {
		timer.schedule(task, 60000);
	}

	/**
	 * Stops automation timer
	 * */
	public void stopDueDateAutomation() {
		timer.cancel();
	}

	// --------------------------------------------------------
	// Update Table 'dateModified' methods/////////////////////
	// --------------------------------------------------------
	/**
	 * Updates the date modified field of any Table class
	 * */
	public void upDateModified(Table table) {
		table.setDateModified(LocalDateTime.now());
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
	public void updateTotals() {

	}
	// --------------------------------------------------------
	// --------------------------------------------------------
	
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

	/**
	 * Timer Daemon responsible for automatic timed events
	 * */
	private class Daemon extends TimerTask {

		/**
		 * Number of times the daemon has run
		 * */
		private int permutations;

		/**
		 * Sets permutations to 0
		 * */
		private Daemon() {
			permutations = 0;
		}

		/**
		 * Checks if the last invoice of a list is overdue and adds a new invoice if so
		 * */
		public void autoAddInvoice() {
		}

		/**
		 * Starts the Daemon
		 * */
		@Override
		public void run() {

			permutations++;
		}

		/**
		 * Gets the amount of times the Daemon's task has run
		 * */
		public int getPermutations() {
			return permutations;
		}
	}
}
