package com.apartmate.database.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.apartmate.database.dbMirror.Database;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.*;

/**
 * Class that should handle all timed events including Java Timer daemons
 * 
 * @since Milestone 3 (0.3)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Heck {

	@SuppressWarnings("unused")
	private class Daemon extends TimerTask {

		private int permutations;

		private Daemon() {
			permutations = 0;
		}

		// Update
		public void updateInsuranceDueDates() {
			Database.getInstance().getApartments().forEach(apt -> {
				Insurance ins = apt.getInsurances().get(apt.getInsurances().size() - 1);
				LocalDateTime tempTime = LocalDateTime.ofInstant(ins.getDueDate().toInstant(), ZoneId.systemDefault());
				if (tempTime.isBefore(LocalDateTime.now())) {
					// new Date();
					apt.getInsurances().add(new Insurance(ins.getId() + 1, ins.getFk(), 0, null,
							Date.from(tempTime.plusMonths(1).toInstant(ZoneOffset.UTC))));
				}
			});
		}

		public void updateTnantInvoiceDueDates() {
			Database.getInstance().getTenants().forEach(tnant -> {
				TnantInvoice inv = tnant.getInvoices().get(tnant.getInvoices().size() - 1);
				LocalDateTime tempTime = LocalDateTime.ofInstant(inv.getDueDate().toInstant(), ZoneId.systemDefault());
				if (tempTime.isBefore(LocalDateTime.now())) {
					// new Date();
					tnant.getInvoices().add(new TnantInvoice(inv.getId() + 1, inv.getFk(), 0, null,
							Date.from(tempTime.plusMonths(1).toInstant(ZoneOffset.UTC))));
				}
			});
		}

		public void updateContInvoiceDueDates() {
			Database.getInstance().getContractors().forEach(cont -> {
				ContInvoice inv = cont.getInvoices().get(cont.getInvoices().size() - 1);
				LocalDateTime tempTime = LocalDateTime.ofInstant(inv.getDueDate().toInstant(), ZoneId.systemDefault());
				if (tempTime.isBefore(LocalDateTime.now())) {
					// new Date();
					cont.getInvoices().add(new ContInvoice(inv.getId() + 1, inv.getFk(), 0, null,
							Date.from(tempTime.plusMonths(1).toInstant(ZoneOffset.UTC))));
				}
			});
		}

		@Override
		public void run() {
			updateInsuranceDueDates();
			updateTnantInvoiceDueDates();
			updateContInvoiceDueDates();

		}

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

	private Timer timer;
	private TimerTask task;

	public Heck() {
		timer = new Timer("Daemon", true);
		task = new Daemon();
	}

	public void startDueDateAutomation() {
		timer.schedule(task, 60000);
	}

	public void stopDueDateAutomation() {
		timer.cancel();
	}

	// --------------------------------------------------------
	// Update Table 'dateModified' methods/////////////////////
	// --------------------------------------------------------
	public void updateModified(Apartment apartment) {
		int location = Database.getInstance().getApartments().indexOf(apartment);
		Database.getInstance().getApartments().get(location).getDateModified().setTime(System.currentTimeMillis());
	}

	public void updateModified(Tenant tenant) {
		int location = Database.getInstance().getTenants().indexOf(tenant);
		Database.getInstance().getTenants().get(location).getDateModified().setTime(System.currentTimeMillis());
	}

	public void updateModified(Candidate candidate) {
		int location = Database.getInstance().getCandidates().indexOf(candidate);
		Database.getInstance().getCandidates().get(location).getDateModified().setTime(System.currentTimeMillis());
	}

	public void updateModified(Contractor contractor) {
		int location = Database.getInstance().getContractors().indexOf(contractor);
		Database.getInstance().getContractors().get(location).getDateModified().setTime(System.currentTimeMillis());
	}

	public void updateModified(Inspection inspection) {
		Database.getInstance().getApartments().forEach(apt -> {
			if (inspection.getFk() == apt.getId()) {
				apt.getInspections().forEach(ins -> {
					if (inspection.getId() == ins.getId()) {
						int location = apt.getInspections().indexOf(ins);
						apt.getInspections().get(location).getDateModified().setTime(System.currentTimeMillis());
					}

				});
			}
		});
	}

	public void updateModified(Insurance insurance) {
		Database.getInstance().getApartments().forEach(apt -> {
			if (insurance.getFk() == apt.getId()) {
				apt.getInsurances().forEach(ins -> {
					if (insurance.getId() == ins.getId()) {
						int location = apt.getInsurances().indexOf(ins);
						apt.getInsurances().get(location).getDateModified().setTime(System.currentTimeMillis());
					}

				});
			}
		});
	}

	public void updateModified(Issue issue) {
		Database.getInstance().getApartments().forEach(apt -> {
			if (issue.getFk() == apt.getId()) {
				apt.getIssues().forEach(iss -> {
					if (issue.getId() == iss.getId()) {
						int location = apt.getIssues().indexOf(iss);
						apt.getIssues().get(location).getDateModified().setTime(System.currentTimeMillis());
					}

				});
			}
		});
	}

	public void updateModified(Spouse spouse, int table) {
		switch (table) {
		case Database.TENANTS:
			Database.getInstance().getTenants().forEach(tnant -> {
				if (spouse.getFk() == tnant.getId()) {
					tnant.getSpouse().getDateModified().setTime(System.currentTimeMillis());
				}
			});
			break;
		case Database.CANDIDATES:
			Database.getInstance().getCandidates().forEach(cand -> {
				if (spouse.getFk2() == cand.getId()) {
					cand.getSpouse().getDateModified().setTime(System.currentTimeMillis());
				}
			});
			break;
		default:
			break;
		}
	}

	public void updateModified(TnantInvoice invoice) {
		Database.getInstance().getTenants().forEach(tnant -> {
			if (invoice.getFk() == tnant.getId()) {
				tnant.getInvoices().forEach(inv -> {
					if (invoice.getId() == inv.getId()) {
						int location = tnant.getInvoices().indexOf(inv);
						tnant.getInvoices().get(location).getDateModified().setTime(System.currentTimeMillis());
					}

				});
			}
		});
	}

	public void updateModified(ContInvoice invoice) {
		Database.getInstance().getContractors().forEach(cont -> {
			if (invoice.getFk() == cont.getId()) {
				cont.getInvoices().forEach(inv -> {
					if (invoice.getId() == inv.getId()) {
						int location = cont.getInvoices().indexOf(inv);
						cont.getInvoices().get(location).getDateModified().setTime(System.currentTimeMillis());
					}

				});
			}
		});
	}
	// --------------------------------------------------------
	// --------------------------------------------------------

	// --------------------------------------------------------
	// Update Invoice/Insurance total & balance fields////////
	// --------------------------------------------------------

	public void updateTotals(Insurance insurance) {
		Database.getInstance().getApartments().forEach(apt -> {
			if (insurance.getFk() == apt.getId()) {
				int index = apt.getInsurances().indexOf(insurance);
				double totalDue = 0, totalPaid = 0;
				for (int x = index; x >= 0; x--) {
					totalDue += apt.getInsBill();
					totalPaid += apt.getInsurances().get(x).getPayment();
				}
				apt.getInsurances().get(index).setTotalPaid(totalPaid);
				apt.getInsurances().get(index).setTotalDue(totalDue);
				apt.getInsurances().get(index).setBalance(totalPaid - totalDue);
			}
		});
	}

	public void updateTotals(TnantInvoice invoice) {
		Database.getInstance().getTenants().forEach(tnant -> {
			if (invoice.getFk() == tnant.getId()) {
				// List<TnantInvoice> subList = tnant.getInvoices().subList(0,
				// tnant.getInvoices().indexOf(invoice));
				int index = tnant.getInvoices().indexOf(invoice);
				double totalDue = 0, totalPaid = 0;
				for (int x = index; x >= 0; x--) {
					totalDue += tnant.getRent();
					totalPaid += tnant.getInvoices().get(x).getPayment();
				}
				tnant.getInvoices().get(index).setTotalPaid(totalPaid);
				tnant.getInvoices().get(index).setTotalDue(totalDue);
				tnant.getInvoices().get(index).setBalance(totalDue - totalPaid);
			}
		});
	}

	public void updateTotals(ContInvoice invoice) {
		Database.getInstance().getContractors().forEach(cont -> {
			if (invoice.getFk() == cont.getId()) {
				// List<ContInvoice> subList = cont.getInvoices().subList(0,
				// cont.getInvoices().indexOf(invoice));
				int index = cont.getInvoices().indexOf(invoice);
				double totalDue = 0, totalPaid = 0;
				for (int x = index; x >= 0; x--) {
					totalDue += cont.getBill();
					totalPaid += cont.getInvoices().get(x).getPayment();
				}
				cont.getInvoices().get(index).setTotalPaid(totalPaid);
				cont.getInvoices().get(index).setTotalDue(totalDue);
				cont.getInvoices().get(index).setBalance(totalDue - totalPaid);
			}
		});
	}
	// --------------------------------------------------------
	// --------------------------------------------------------

	public Date nextMonth(Date date) {
		LocalDateTime newDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return Date.from(newDate.plusMonths(1).toInstant(ZoneOffset.UTC));
	}

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
