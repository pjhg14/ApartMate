package com.apartmate.database.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.ContInvoice;
import com.apartmate.database.tables.subTables.TnantInvoice;

public class InvoiceUpdate {

	// Note: Nearly all modern operating systems assume that 1 day = 24 × 60 × 60 =
	// 86400 seconds in all cases
	/*
	 * Current needs
	 */

	// 28 days= 2,419,200; 2419200
	// 29 days= 2,505,600‬; 2505600
	// 30 days= 2,592,000; 2592000
	// 31 days= 2,678,400; 2678400

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

	public InvoiceUpdate() {

	}

	public void updateTenantInvoices() {
		for (Tenant tenant : Database.getInstance().getTenants()) {
			for (TnantInvoice invoice : tenant.getInvoices()) {
				if (invoice.getDueDate().getTime() > Calendar.getInstance().getTimeInMillis()) {
					if (checkMonth() != -1) {
						invoice.getDueDate().setTime(invoice.getDueDate().getTime() + checkMonth());
					} else {
						// return an invalid value so the program knows that something wrong happened
					}
				}
			}
		}
	}

	public void updateContractorInvoices() {
		for (Contractor contractor : Database.getInstance().getContractors()) {
			for (ContInvoice invoice : contractor.getInvoices()) {
				if (invoice.getDueDate().getTime() > Calendar.getInstance().getTimeInMillis()) {
					if (checkMonth() != -1) {
						invoice.getDueDate().setTime(invoice.getDueDate().getTime() + checkMonth());
					} else {
						// return an invalid value so the program knows that something wrong happened
					}
				}
			}
		}
	}

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
}
