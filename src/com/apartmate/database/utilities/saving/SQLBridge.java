package com.apartmate.database.utilities.saving;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.dbMirror.DBTables;
import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.main.Main;


/**
 * SQL Class; establishes connection and facilitates the saving and loading of
 * table
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
@SuppressWarnings("FieldCanBeLocal")
public class SQLBridge {

	// Connection variables
	/***/
	private Connection connection = null;

	/***/
	private Statement statement = null;

	/***/
	private ResultSet rs = null;

	/***/
	private final String url;

	/***/
	private final String defaultUserName;

	/***/
	private final String defaultPass;

	/***/
	private boolean preparedStatementsSet = false;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Table constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Tables
	/***/
	private final String TABLE_APARTMENTS = "apartments";

	/***/
	private final String TABLE_TENANTS = "tenants";

	/***/
	private final String TABLE_CANDIDATES = "candidates";

	/***/
	private final String TABLE_CONTRACTORS = "contractors";

	// Sub-tables
	/***/
	private final String SUB_TABLE_INSURANCES = "insurances";

	/***/
	private final String SUB_TABLE_INS_INVOICES = "ins_invoices";

	/***/
	private final String SUB_TABLE_BILLS = "bills";

	/***/
	private final String SUB_TABLE_BILL_INVOICES = "bill_invoices";

	/***/
	private final String SUB_TABLE_ISSUES = "issues";

	/***/
	private final String SUB_TABLE_INSPECTIONS = "inspections";

	/***/
	private final String SUB_TABLE_TNANT_SPOUSES = "tnant_spouses";

	/***/
	private final String SUB_TABLE_CAND_SPOUSES = "cand_spouses";

	/***/
	private final String SUB_TABLE_TNANT_INVOICES = "tnant_invoices";

	/***/
	private final String SUB_TABLE_CONT_INVOICES = "cont_invoices";

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Column constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Apartment table columns
	/***/
	private final String COLUMN_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_APT_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_APT_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_APT_ADDRESS = "address";

	/***/
	private final String COLUMN_APT_CITY = "city";

	/***/
	private final String COLUMN_APT_STATE = "state";

	/***/
	private final String COLUMN_APT_ZIP = "zip";

	/***/
	private final String COLUMN_APT_CAPACITY = "capacity";

	/***/
	private final String COLUMN_APT_NUM_TENANTS = "num_tenants";

	// Tenant table columns
	/***/
	private final String COLUMN_TNANT_ID = "tenant_id";

	/***/
	private final String COLUMN_TNANT_APT = "apartment_id";

	/***/
	private final String COLUMN_TNANT_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_TNANT_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_TNANT_FNAME = "first_name";

	/***/
	private final String COLUMN_TNANT_LNAME = "last_name";

	/***/
	private final String COLUMN_TNANT_PHONE = "phone_number";

	/***/
	private final String COLUMN_TNANT_EMAIL = "email";

	/***/
	private final String COLUMN_TNANT_IDN = "ssn";

	/***/
	private final String COLUMN_TNANT_RENT = "rent";

	/***/
	private final String COLUMN_TNANT_NUM_CHILDREN = "num_children";

	/***/
	private final String COLUMN_TNANT_MOVE_IN_DATE = "move_in_date";

	/***/
	private final String COLUMN_TNANT_DOB = "date_of_birth";

	/***/
	private final String COLUMN_TNANT_ANNUAL_INCOME = "annual_income";

	/***/
	private final String COLUMN_TNANT_SLATED_FOR_EVICTION = "slated_for_eviction";

	/***/
	private final String COLUMN_TNANT_EVICT_REASON = "evict_reason";

	/***/
	private final String COLUMN_TNANT_MOV_OUT_DATE = "move_out_date";

	// Spouse table columns
	/***/
	private final String COLUMN_SPOUSE_ID = "spouse_id";

	/***/
	private final String COLUMN_SPOUSE_TNANT_ID = "tenant_id";

	/***/
	private final String COLUMN_SPOUSE_CAND_ID = "candidate_id";

	/***/
	private final String COLUMN_SPOUSE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_SPOUSE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_SPOUSE_FNAME = "first_name";

	/***/
	private final String COLUMN_SPOUSE_LNAME = "last_name";

	/***/
	private final String COLUMN_SPOUSE_PHONE = "phone_number";

	/***/
	private final String COLUMN_SPOUSE_EMAIL = "email";

	/***/
	private final String COLUMN_SPOUSE_IDN = "ssn";

	/***/
	private final String COLUMN_SPOUSE_DOB = "date_of_birth";

	/***/
	private final String COLUMN_SPOUSE_ANNUAL_INCOME = "annual_income";

	// Tenant invoice table columns
	/***/
	private final String COLUMN_TNANT_INVOICE_ID = "invoice_id";

	/***/
	private final String COLUMN_TNANT_INVOICE_TNANT_ID = "tenant_id";

	/***/
	private final String COLUMN_TNANT_INVOICE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_TNANT_INVOICE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_TNANT_INVOICE_PMT_AMOUNT = "payment_amount";

	/***/
	private final String COLUMN_TNANT_INVOICE_BALANCE = "balance";

	/***/
	private final String COLUMN_TNANT_INVOICE_TOTAL_PAID = "total_paid";

	/***/
	private final String COLUMN_TNANT_INVOICE_TOTAL_DUE = "total_due";

	/***/
	private final String COLUMN_TNANT_INVOICE_PAYMENT_DATE = "payment_date";

	/***/
	private final String COLUMN_TNANT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// Inspection table columns
	/***/
	private final String COLUMN_INSPECTION_ID = "inspection_id";

	/***/
	private final String COLUMN_INSPECTION_TNANT_ID = "tenant_id";

	/***/
	private final String COLUMN_INSPECTION_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_INSPECTION_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_INSPECTION_DESCRIPTION = "inspection_desc";

	/***/
	private final String COLUMN_INSPECTION_DATE = "inspection_date";

	// Candidate table columns
	/***/
	private final String COLUMN_CAND_ID = "candidate_id";

	/***/
	private final String COLUMN_CAND_APT = "apartment_id";

	/***/
	private final String COLUMN_CAND_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_CAND_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_CAND_FNAME = "first_name";

	/***/
	private final String COLUMN_CAND_LNAME = "last_name";

	/***/
	private final String COLUMN_CAND_PHONE = "phone_number";

	/***/
	private final String COLUMN_CAND_EMAIL = "email";

	/***/
	private final String COLUMN_CAND_DOB = "date_of_birth";

	/***/
	private final String COLUMN_CAND_ANNUAL_INCOME = "annual_income";

	/***/
	private final String COLUMN_CAND_IDN = "ssn";

	/***/
	private final String COLUMN_CAND_NUM_CHILDREN = "num_children";

	/***/
	private final String COLUMN_CAND_ACCEPTED = "accepted";

	// Contractor table columns
	/***/
	private final String COLUMN_CONT_ID = "contractor_id";

	/***/
	private final String COLUMN_CONT_APT = "apartment_id";

	/***/
	private final String COLUMN_CONT_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_CONT_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_CONT_NAME = "company_name";

	/***/
	private final String COLUMN_CONT_BILL = "bill";

	/***/
	private final String COLUMN_CONT_PHONE = "phone_number";

	/***/
	private final String COLUMN_CONT_EMAIL = "email";

	// Contractor Invoice table columns
	/***/
	private final String COLUMN_CONT_INVOICE_ID = "invoice_id";

	/***/
	private final String COLUMN_CONT_INVOICE_TNANT_ID = "contractor_id";

	/***/
	private final String COLUMN_CONT_INVOICE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_CONT_INVOICE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_CONT_INVOICE_PMT_AMOUNT = "payment_amount";

	/***/
	private final String COLUMN_CONT_INVOICE_BALANCE = "balance";

	/***/
	private final String COLUMN_CONT_INVOICE_TOTAL_PAID = "total_paid";

	/***/
	private final String COLUMN_CONT_INVOICE_TOTAL_DUE = "total_due";

	/***/
	private final String COLUMN_CONT_INVOICE_PAYMENT_DATE = "payment_date";

	/***/
	private final String COLUMN_CONT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// Insurance table columns
	/***/
	private final String COLUMN_INSURANCE_ID = "insurance_id";

	/***/
	private final String COLUMN_INSURANCE_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_INSURANCE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_INSURANCE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_INSURANCE_NAME = "ins_name";

	/***/
	private final String COLUMN_INSURANCE_BILL = "bill";

	/***/
	private final String COLUMN_INSURANCE_PHONE = "phone_number";

	/***/
	private final String COLUMN_INSURANCE_EMAIL = "email";

	// Insurance Invoice columns
	/***/
	private final String COLUMN_INS_INVOICE_ID = "invoice_id";

	/***/
	private final String COLUMN_INS_INVOICE_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_INS_INVOICE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_INS_INVOICE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_INS_INVOICE_PAYMENT = "payment_amount";

	/***/
	private final String COLUMN_INS_INVOICE_BALANCE = "balance";

	/***/
	private final String COLUMN_INS_INVOICE_TOTAL_PAID = "total_paid";

	/***/
	private final String COLUMN_INS_INVOICE_TOTAL_DUE = "total_due";

	/***/
	private final String COLUMN_INS_INVOICE_PMT_DATE = "payment_date";

	/***/
	private final String COLUMN_INS_INVOICE_DUE_DATE = "payment_due_date";

	// Bill table columns
	/***/
	private final String COLUMN_BILL_ID = "bill_id";

	/***/
	private final String COLUMN_BILL_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_BILL_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_BILL_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_BILL_NAME = "bill_name";

	/***/
	private final String COLUMN_BILL_ADDRESS = "address";

	/***/
	private final String COLUMN_BILL_PHONE = "phone";

	/***/
	private final String COLUMN_BILL_BILL = "bill";

	// Bill Invoice columns
	/***/
	private final String COLUMN_BILL_INVOICE_ID = "invoice_id";

	/***/
	private final String COLUMN_BILL_INVOICE_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_BILL_INVOICE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_BILL_INVOICE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_BILL_INVOICE_PAYMENT = "payment_amount";

	/***/
	private final String COLUMN_BILL_INVOICE_BALANCE = "balance";

	/***/
	private final String COLUMN_BILL_INVOICE_TOTAL_PAID = "total_paid";

	/***/
	private final String COLUMN_BILL_INVOICE_TOTAL_DUE = "total_due";

	/***/
	private final String COLUMN_BILL_INVOICE_PMT_DATE = "payment_date";

	/***/
	private final String COLUMN_BILL_INVOICE_DUE_DATE = "payment_due_date";

	// Issue table columns
	/***/
	private final String COLUMN_ISSUE_ID = "issue_id";

	/***/
	private final String COLUMN_ISSUE_APT_ID = "apartment_id";

	/***/
	private final String COLUMN_ISSUE_DATE_CREATED = "date_created";

	/***/
	private final String COLUMN_ISSUE_DATE_MODIFIED = "date_modified";

	/***/
	private final String COLUMN_ISSUE_DESCRIPTION = "issue_desc";

	/***/
	private final String COLUMN_ISSUE_DATE = "issue_date";

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Query constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Query Strings
	/***/
	private final String QUERY_APARTMENTS = "SELECT * FROM " + TABLE_APARTMENTS;

	/***/
	private final String QUERY_TENANTS = "SELECT * FROM " + TABLE_TENANTS;

	/***/
	private final String QUERY_TNANT_SPOUSES = "SELECT * FROM " + SUB_TABLE_TNANT_SPOUSES;

	/***/
	private final String QUERY_TNANT_INVOICES = "SELECT * FROM " + SUB_TABLE_TNANT_INVOICES;

	/***/
	private final String QUERY_CANDIDATES = "SELECT * FROM " + TABLE_CANDIDATES;

	/***/
	private final String QUERY_CAND_SPOUSES = "SELECT * FROM " + SUB_TABLE_CAND_SPOUSES;

	/***/
	private final String QUERY_CONTRACTORS = "SELECT * FROM " + TABLE_CONTRACTORS;

	/***/
	private final String QUERY_CONT_INVOICES = "SELECT * FROM " + SUB_TABLE_CONT_INVOICES;

	/***/
	private final String QUERY_INSURANCES = "SELECT * FROM " + SUB_TABLE_INSURANCES;

	/***/
	private final String QUERY_INS_INVOICES = "SELECT * FROM " + SUB_TABLE_INS_INVOICES;

	/***/
	private final String QUERY_BILLS = "SELECT * FROM " + SUB_TABLE_BILLS;

	/***/
	private final String QUERY_BILL_INVOICES = "SELECT * FROM " + SUB_TABLE_BILL_INVOICES;

	/***/
	private final String QUERY_ISSUES = "SELECT * FROM " + SUB_TABLE_ISSUES;

	/***/
	private final String QUERY_INSPECTIONS = "SELECT * FROM " + SUB_TABLE_INSPECTIONS;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Insert constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	//Column counts

	/*
	* Apartments: 9
	* Tenants: 17
	* Spouses (Tenant, Candidate): 11
	* Invoices (Tenant, Contractor, Insurances, Bills): 11
	* NoteLogs (Inspection, Issue): 5
	* Candidates: 13
	* Contractors: 8
	* Insurances: 8
	* Bills: 8
	* */

	/***/
	private final String SPOUSE_VALUES = "(?,?,?,?,?,?,?,?,?,?,?)";

	/***/
	private final String INVOICE_VALUES = "(?,?,?,?,?,?,?,?,?,?,?)";

	/***/
	private final String NOTE_LOG_VALUES = "(?,?,?,?,?,?)";

	// Insert statements
	/***/
	private final String INSERT_INTO_APARTMENTS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?)", TABLE_APARTMENTS);

	/***/
	private final String INSERT_INTO_TENANTS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_TENANTS);

	/***/
	private final String INSERT_INTO_TNANT_SPOUSES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_TNANT_SPOUSES, SPOUSE_VALUES);

	/***/
	private final String INSERT_INTO_TNANT_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_TNANT_INVOICES, INVOICE_VALUES);

	/***/
	private final String INSERT_INTO_INSPECTIONS =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_INSPECTIONS, NOTE_LOG_VALUES);

	/***/
	private final String INSERT_INTO_CANDIDATES =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_CANDIDATES);

	/***/
	private final String INSERT_INTO_CAND_SPOUSES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_CAND_SPOUSES, SPOUSE_VALUES);

	/***/
	private final String INSERT_INTO_CONTRACTORS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", TABLE_CONTRACTORS);

	/***/
	private final String INSERT_INTO_CONT_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_CONT_INVOICES, INVOICE_VALUES);

	/***/
	private final String INSERT_INTO_INSURANCES =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", SUB_TABLE_INSURANCES);

	/***/
	private final String INSERT_INTO_INS_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_INS_INVOICES, INVOICE_VALUES);

	/***/
	private final String INSERT_INTO_BILLS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", SUB_TABLE_BILLS);

	/***/
	private final String INSERT_INTO_BILL_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_BILL_INVOICES, INVOICE_VALUES);

	/***/
	private final String INSERT_INTO_ISSUES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_ISSUES, NOTE_LOG_VALUES);



	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Prepared Statement table constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	//TODO: Finish Update Constants
	/*
	* Update Template:
	* UPDATE table_name SET field1 = new-value1, field2 = new-value2...
	* [WHERE Clause]
	* */

	//Apartment update
	private final String UPDATE_APARTMENT =
			String.format("UPDATE %s " +
					"SET %s = ?, " +		//date_modified
					"SET %s = ?, " +		//address
					"SET %s = ?, " +		//city
					"SET %s = ?, " +		//state
					"SET %s = ?, " +		//zip
					"SET %s = ?, " +		//capacity
					"SET %s = ?, " +		//num_tenants
					" WHERE %s = ?",
					TABLE_APARTMENTS,
					COLUMN_APT_DATE_MODIFIED, COLUMN_APT_ADDRESS,COLUMN_APT_CITY,COLUMN_APT_STATE,
					COLUMN_APT_ZIP,COLUMN_APT_CAPACITY,COLUMN_APT_NUM_TENANTS,
					COLUMN_APT_ID
			);


	//Tenant update
	private final String UPDATE_TENANTS =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date_modified
					"SET %s = ? " +		//first_name
					"SET %s = ? " +		//last_name
					"SET %s = ? " +		//phone_number
					"SET %s = ? " +		//email
					"SET %s = ? " +		//idn
					"SET %s = ? " +		//rent
					"SET %s = ? " +		//num_children
					"SET %s = ? " +		//move_in_date
					"SET %s = ? " +		//date_of_birth
					"SET %s = ? " +		//annual_income
					"SET %s = ? " +		//slated_for_eviction
					"SET %s = ? " +		//move_out_date
					"WHERE %s = ?",
					TABLE_TENANTS,
					COLUMN_TNANT_DATE_MODIFIED, COLUMN_TNANT_FNAME, COLUMN_TNANT_LNAME, COLUMN_TNANT_PHONE,
					COLUMN_TNANT_EMAIL, COLUMN_TNANT_IDN, COLUMN_TNANT_RENT, COLUMN_TNANT_NUM_CHILDREN,
					COLUMN_TNANT_MOVE_IN_DATE, COLUMN_TNANT_DOB, COLUMN_TNANT_ANNUAL_INCOME,
					COLUMN_TNANT_SLATED_FOR_EVICTION, COLUMN_TNANT_MOV_OUT_DATE,
					COLUMN_TNANT_ID
			);

//	//Spouse;
//	private final String UPDATE_SPOUSES_SPOUSE_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", TABLE_CONTRACTORS);
//
//	//TnantInvoice;
//	private final String UPDATE_TNANT_INVOICES_INVOICE_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", TABLE_CONTRACTORS);
//
//	//Inspection;
//	private final String UPDATE_INSPECTIONS_INSPECTION_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", TABLE_CONTRACTORS);
//
//	//Candidate update
//	private final String UPDATE_CANDIDATES_CANDIDATE_ID =
//	"UPDATE cont_invoices SET ? = ? WHERE id = ?";
//
//
//	//Contractor update
//	private final String UPDATE_CONTRACTORS_CONTRACTOR_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", TABLE_CONTRACTORS);
//
//	//ContInvoice;
//	private final String UPDATE_CONT_INVOICES_INVOICE_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", TABLE_CONTRACTORS);
//
//	//Insurance;
//	private final String UPDATE_INSURANCES_INSURANCE_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", SUB_TABLE_INSURANCES);
//
//	//Insurance Invoice
//
//	//Bill
//
//	//Bill Invoice
//
//	//Issue;
//	private final String UPDATE_ISSUES_ISSUE_ID =
//			String.format("UPDATE %s SET ? = ? WHERE id = ?", SUB_TABLE_ISSUES);

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Delete Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Delete methods
	/***/
	private final String DELETE_FROM_APARTMENTS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_APARTMENTS, COLUMN_APT_ID);

	/***/
	private final String DELETE_FROM_TENANTS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_TENANTS, COLUMN_TNANT_ID);

	/***/
	private final String DELETE_FROM_TNANT_SPOUSES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_TNANT_SPOUSES, COLUMN_SPOUSE_ID);

	/***/
	private final String DELETE_FROM_TNANT_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_TNANT_INVOICES, COLUMN_TNANT_INVOICE_ID);

	/***/
	private final String DELETE_FROM_INSPECTIONS =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_INSPECTIONS, COLUMN_INSPECTION_ID);

	/***/
	private final String DELETE_FROM_CANDIDATES =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_CANDIDATES, COLUMN_CAND_ID);

	/***/
	private final String DELETE_FROM_CAND_SPOUSES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_CAND_SPOUSES, COLUMN_SPOUSE_ID);

	/***/
	private final String DELETE_FROM_CONTRACTORS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_CONTRACTORS, COLUMN_CONT_ID);

	/***/
	private final String DELETE_FROM_CONT_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_CONT_INVOICES, COLUMN_CONT_INVOICE_ID);

	/***/
	private final String DELETE_FROM_INSURANCES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_INSURANCES, COLUMN_INSURANCE_ID);

	/***/
	private final String DELETE_FROM_INS_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?",SUB_TABLE_INS_INVOICES,COLUMN_INS_INVOICE_ID);

	/***/
	private final String DELETE_FROM_BILLS =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_BILLS,COLUMN_BILL_ID);

	/***/
	private final String DELETE_FROM_BILL_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_BILL_INVOICES, COLUMN_BILL_INVOICE_ID);

	/***/
	private final String DELETE_FROM_ISSUES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_ISSUES, COLUMN_ISSUE_ID);

	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Prepared Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Query Prepared Statements (Might not be needed; no (known) risk of SQL injection (requires no user input))
//	private PreparedStatement queryApartments;
//	private PreparedStatement queryTenants;
//	private PreparedStatement queryContractors;
//	private	PreparedStatement queryInsurance;
//	private PreparedStatement queryIssue;
//	private PreparedStatement queryInspection;
//	private PreparedStatement queryTnantInvoice;
//	private PreparedStatement queryContInvoice;


	// Insert Prepared Statements
	/***/
	private PreparedStatement insertApartment;

	/***/
	private PreparedStatement insertTenant;

	/***/
	private PreparedStatement insertTnantSpouse;

	/***/
	private PreparedStatement insertTnantInvoice;

	/***/
	private PreparedStatement insertInspection;

	/***/
	private PreparedStatement insertCandidate;

	/***/
	private PreparedStatement insertCandSpouse;

	/***/
	private PreparedStatement insertContractor;

	/***/
	private PreparedStatement insertContInvoice;

	/***/
	private PreparedStatement insertInsurance;

	/***/
	private PreparedStatement insertInsInvoice;

	/***/
	private PreparedStatement insertBill;

	/***/
	private PreparedStatement insertBillInvoice;

	/***/
	private PreparedStatement insertIssue;



	//Update Prepared Statements
	private PreparedStatement updateApartment;
	private PreparedStatement updateTenant;
//	private PreparedStatement updateCandidate;
//	private PreparedStatement updateContractor;
//	private	PreparedStatement updateInsurance;
//	private PreparedStatement updateIssue;
//	private PreparedStatement updateInspection;
//	private PreparedStatement updateSpouse;
//	private PreparedStatement updateTnantInvoice;
//	private	PreparedStatement updateContInvoice;


	// Delete Prepared statements
	/***/
	private PreparedStatement deleteApartment;

	/***/
	private PreparedStatement deleteTenant;

	/***/
	private PreparedStatement deleteTnantSpouse;

	/***/
	private PreparedStatement deleteTnantInvoice;

	/***/
	private PreparedStatement deleteInspection;

	/***/
	private PreparedStatement deleteCandidate;

	/***/
	private PreparedStatement deleteCandSpouse;

	/***/
	private PreparedStatement deleteContractor;

	/***/
	private PreparedStatement deleteContInvoice;

	/***/
	private PreparedStatement deleteInsurance;

	/***/
	private PreparedStatement deleteInsInvoice;

	/***/
	private PreparedStatement deleteBill;

	/***/
	private PreparedStatement deleteBillInvoice;

	/***/
	private PreparedStatement deleteIssue;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// SQLBridge Constructors
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Constructor
	/***/
	public SQLBridge() {
		url = "jdbc:mysql://localhost/am";
		defaultUserName = "testing";
		defaultPass = "Java#1sQl#2TestingUser#99";
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// SQL Connection methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Testing connect function
	/***/
	public boolean connect() {
		try {
			connection = DriverManager.getConnection(url, defaultUserName, defaultPass);
			statement = connection.createStatement();
			// prepared statement setup
			prepareStatements();

			// queryApartments = connection.prepareStatement(QUERY_APARTMENTS);

			return true;
		} catch (SQLException e) {
			System.out.println("Exception occurred: " + e.getMessage());
			return false;
		}

	}

	// Final connect method; requires username entry
	/***/
	public boolean connect(String username, String pass) {
		try {
			connection = DriverManager.getConnection(url, username, pass);
			statement = connection.createStatement();
			// prepared statement setup
			prepareStatements();
			// queryApartments = connection.prepareStatement(QUERY_APARTMENTS);

			return true;
		} catch (SQLException e) {
			System.out.println("Exception occurred: " + e.getMessage());
			return false;
		}

	}

	//TODO: Finish save method
	/***/
	public boolean save() {
		//Database.getInstance();

		// add/delete new/removed apartments

		// add/delete new/removed tenants

		// add/delete new/removed candidates

		// add/delete new/removed contractors
		return true;
	}

	/***/
	public void close() throws SQLException {
		// Save all edited Tables
		// Close resources
		if (preparedStatementsSet) {
			closeStatements();
		}

		if (rs != null) {
			rs.close();
			// System.out.println("Result set successfully closed...");
		}

		if (statement != null) {
			statement.close();
			// System.out.println("Statement object successfully closed...");
		}

		if (connection != null) {
			connection.close();
			// System.out.println("Connection successfully closed...");
		}

	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	/***/
	public List<Apartment> queryApartments() {
		List<Apartment> queriedApartments = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_APARTMENTS);

			while (rs.next()) {
				Apartment app = new Apartment();

				// Set fields
				app.setId(rs.getInt(COLUMN_APT_ID));
				app.setDateCreated(rs.getTimestamp(COLUMN_APT_DATE_CREATED));
				app.setDateModified(rs.getTimestamp(COLUMN_APT_DATE_MODIFIED));
				app.setAddress(rs.getString(COLUMN_APT_ADDRESS));
				app.setCity(rs.getString(COLUMN_APT_CITY));
				app.setState(rs.getString(COLUMN_APT_STATE));
				app.setZipCode(rs.getString(COLUMN_APT_ZIP));
				app.setCapacity(rs.getInt(COLUMN_APT_CAPACITY));
				app.setNumTenants(rs.getInt(COLUMN_APT_NUM_TENANTS));

				queriedApartments.add(app);
			}

			// Set foreign tables:
			// Insurances
			rs = statement.executeQuery(QUERY_INSURANCES);

			while (rs.next()) {
				Insurance insurance = new Insurance();

				insurance.setId(rs.getInt(COLUMN_INSURANCE_ID));
				insurance.setFk(rs.getInt(COLUMN_INSURANCE_APT_ID));
				insurance.setDateCreated(rs.getTimestamp(COLUMN_INSURANCE_DATE_CREATED));
				insurance.setDateModified(rs.getTimestamp(COLUMN_INSURANCE_DATE_MODIFIED));
				insurance.setName(rs.getString(COLUMN_INSURANCE_NAME));
				insurance.setBill(rs.getDouble(COLUMN_INSURANCE_BILL));
				insurance.setPhone(rs.getString(COLUMN_INSURANCE_PHONE));
				insurance.setEmail(rs.getString(COLUMN_INSURANCE_EMAIL));

				queriedApartments.forEach(apt -> {
					if (insurance.getFk() == apt.getId()) {
						apt.getInsurances().add(insurance);
					}
				});
			}

			// Insurance Invoices
			rs = statement.executeQuery(QUERY_INS_INVOICES);

			while (rs.next()) {
				Invoice insInvoice = new Invoice();

				insInvoice.setId(rs.getInt(COLUMN_INS_INVOICE_ID));
				insInvoice.setFk(rs.getInt(COLUMN_INS_INVOICE_APT_ID));
				insInvoice.setDateCreated(rs.getTimestamp(COLUMN_INS_INVOICE_DATE_CREATED));
				insInvoice.setDateModified(rs.getTimestamp(COLUMN_INS_INVOICE_DATE_MODIFIED));
				insInvoice.setPayment(rs.getDouble(COLUMN_INS_INVOICE_PAYMENT));
				insInvoice.setBalance(rs.getDouble(COLUMN_INS_INVOICE_BALANCE));
				insInvoice.setTotalPaid(rs.getDouble(COLUMN_INS_INVOICE_TOTAL_PAID));
				insInvoice.setTotalDue(rs.getDouble(COLUMN_INS_INVOICE_TOTAL_DUE));
				insInvoice.setPaymentDate(rs.getTimestamp(COLUMN_INS_INVOICE_PMT_DATE));
				insInvoice.setDueDate(rs.getTimestamp(COLUMN_INS_INVOICE_DUE_DATE));

				queriedApartments.forEach(apt -> apt.getInsurances().forEach(ins -> {
					if (insInvoice.getFk() == ins.getId()) {
						ins.getInvoices().add(insInvoice);
					}
				}));
			}

			// Bills
			rs = statement.executeQuery(QUERY_BILLS);

			while (rs.next()) {
				Bill bill = new Bill();

				bill.setId(rs.getInt(COLUMN_BILL_ID));
				bill.setFk(rs.getInt(COLUMN_BILL_APT_ID));
				bill.setDateCreated(rs.getTimestamp(COLUMN_BILL_DATE_CREATED));
				bill.setDateModified(rs.getTimestamp(COLUMN_BILL_DATE_MODIFIED));
				bill.setBillName(rs.getString(COLUMN_BILL_NAME));
				bill.setAddress(rs.getString(COLUMN_BILL_ADDRESS));
				bill.setPhone(rs.getString(COLUMN_BILL_PHONE));
				bill.setBill(rs.getDouble(COLUMN_BILL_BILL));

				queriedApartments.forEach(apt -> {
					if (bill.getFk() == apt.getId()) {
						apt.getBills().add(bill);
					}
				});
			}

			// Bill Invoices
			rs = statement.executeQuery(QUERY_BILL_INVOICES);

			while (rs.next()) {
				Invoice billInvoice = new Invoice();

				billInvoice.setId(rs.getInt(COLUMN_BILL_INVOICE_ID));
				billInvoice.setFk(rs.getInt(COLUMN_BILL_INVOICE_APT_ID));
				billInvoice.setDateCreated(rs.getTimestamp(COLUMN_BILL_INVOICE_DATE_CREATED));
				billInvoice.setDateModified(rs.getTimestamp(COLUMN_BILL_INVOICE_DATE_MODIFIED));
				billInvoice.setPayment(rs.getDouble(COLUMN_BILL_INVOICE_PAYMENT));
				billInvoice.setBalance(rs.getDouble(COLUMN_BILL_INVOICE_BALANCE));
				billInvoice.setTotalPaid(rs.getDouble(COLUMN_BILL_INVOICE_TOTAL_PAID));
				billInvoice.setTotalDue(rs.getDouble(COLUMN_BILL_INVOICE_TOTAL_DUE));
				billInvoice.setPaymentDate(rs.getTimestamp(COLUMN_BILL_INVOICE_PMT_DATE));
				billInvoice.setDueDate(rs.getTimestamp(COLUMN_BILL_INVOICE_DUE_DATE));

				queriedApartments.forEach(apt -> apt.getBills().forEach(bill -> {
					if (billInvoice.getFk() == bill.getId()) {
						bill.getInvoices().add(billInvoice);
					}
				}));
			}

			// Issues
			rs = statement.executeQuery(QUERY_ISSUES);

			while (rs.next()) {
				NoteLog issue = new NoteLog();

				issue.setId(rs.getInt(COLUMN_ISSUE_ID));
				issue.setFk(rs.getInt(COLUMN_ISSUE_APT_ID));
				issue.setDateCreated(rs.getTimestamp(COLUMN_ISSUE_DATE_CREATED));
				issue.setDateModified(rs.getTimestamp(COLUMN_ISSUE_DATE_MODIFIED));
				issue.setLog(rs.getString(COLUMN_ISSUE_DESCRIPTION));
				issue.setLogDate(rs.getDate(COLUMN_ISSUE_DATE));

				queriedApartments.forEach(apt -> {
					if (issue.getFk() == apt.getId()) {
						apt.getIssues().add(issue);
					}
				});

			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying apartments");
			return null;
		}

		return queriedApartments;
	}

	/***/
	public List<Tenant> queryTenants() {
		List<Tenant> queriedTenants = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_TENANTS);

			// Set fields
			while (rs.next()) {
				Tenant tenant = new Tenant();

				tenant.setId(rs.getInt(COLUMN_TNANT_ID));
				tenant.setFk(rs.getInt(COLUMN_TNANT_APT));
				tenant.setDateCreated(rs.getTimestamp(COLUMN_TNANT_DATE_CREATED));
				tenant.setDateModified(rs.getTimestamp(COLUMN_TNANT_DATE_MODIFIED));
				tenant.setFirstName(rs.getString(COLUMN_TNANT_FNAME));
				tenant.setLastName(rs.getString(COLUMN_TNANT_LNAME));
				tenant.setPhone(rs.getString(COLUMN_TNANT_PHONE));
				tenant.setEmail(rs.getString(COLUMN_TNANT_EMAIL));
				tenant.setSSN(rs.getString(COLUMN_TNANT_IDN));
				tenant.setRent(rs.getDouble(COLUMN_TNANT_RENT));
				tenant.setNumChildren(rs.getInt(COLUMN_TNANT_NUM_CHILDREN));
				tenant.setMovInDate(rs.getDate(COLUMN_TNANT_MOVE_IN_DATE));
				tenant.setDateOfBirth(rs.getDate(COLUMN_TNANT_DOB));
				tenant.setAnnualIncome(rs.getInt(COLUMN_TNANT_ANNUAL_INCOME));
				tenant.setEvicted(rs.getBoolean(COLUMN_TNANT_SLATED_FOR_EVICTION));
				tenant.setEvictReason(rs.getString(COLUMN_TNANT_EVICT_REASON));
				tenant.setMovOutDate(rs.getDate(COLUMN_TNANT_MOV_OUT_DATE));

				queriedTenants.add(tenant);
			}
			// Set foreign tables
			// Spouse
			rs = statement.executeQuery(QUERY_TNANT_SPOUSES);

			while (rs.next()) {
				Spouse tnantSpouse = new Spouse();

				tnantSpouse.setId(rs.getInt(COLUMN_SPOUSE_ID));
				tnantSpouse.setFk(rs.getInt(COLUMN_SPOUSE_TNANT_ID));
				tnantSpouse.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED));
				tnantSpouse.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED));
				tnantSpouse.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				tnantSpouse.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				tnantSpouse.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				tnantSpouse.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				tnantSpouse.setSSN(rs.getString(COLUMN_SPOUSE_IDN));
				tnantSpouse.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB));
				tnantSpouse.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				queriedTenants.forEach(tnant -> {
					if (tnantSpouse.getFk() == tnant.getId()) {
						tnant.setSpouse(tnantSpouse);
					}
				});
			}

			// Invoices
			rs = statement.executeQuery(QUERY_TNANT_INVOICES);
			while (rs.next()) {
				Invoice tnantInvoice = new Invoice();

				tnantInvoice.setId(rs.getInt(COLUMN_TNANT_INVOICE_ID));
				tnantInvoice.setFk(rs.getInt(COLUMN_TNANT_INVOICE_TNANT_ID));
				tnantInvoice.setDateCreated(rs.getTimestamp(COLUMN_TNANT_INVOICE_DATE_CREATED));
				tnantInvoice.setDateModified(rs.getTimestamp(COLUMN_TNANT_INVOICE_DATE_MODIFIED));
				tnantInvoice.setPayment(rs.getDouble(COLUMN_TNANT_INVOICE_PMT_AMOUNT));
				tnantInvoice.setBalance(rs.getDouble(COLUMN_TNANT_INVOICE_BALANCE));
				tnantInvoice.setTotalPaid(rs.getDouble(COLUMN_TNANT_INVOICE_TOTAL_PAID));
				tnantInvoice.setTotalDue(rs.getDouble(COLUMN_TNANT_INVOICE_TOTAL_DUE));
				tnantInvoice.setPaymentDate(rs.getTimestamp(COLUMN_TNANT_INVOICE_PAYMENT_DATE));
				tnantInvoice.setDueDate(rs.getTimestamp(COLUMN_TNANT_INVOICE_PMT_DUE_DATE));

				queriedTenants.forEach(tnant -> {
					if (tnantInvoice.getFk() == tnant.getId()) {
						tnant.getInvoices().add(tnantInvoice);
					}
				});
			}

			// Inspection
			rs = statement.executeQuery(QUERY_INSPECTIONS);

			while (rs.next()) {
				NoteLog inspection = new NoteLog();

				inspection.setId(rs.getInt(COLUMN_INSPECTION_ID));
				inspection.setFk(rs.getInt(COLUMN_INSPECTION_TNANT_ID));
				inspection.setDateCreated(rs.getTimestamp(COLUMN_INSPECTION_DATE_CREATED));
				inspection.setDateModified(rs.getTimestamp(COLUMN_INSPECTION_DATE_MODIFIED));
				inspection.setLog(rs.getString(COLUMN_INSPECTION_DESCRIPTION));
				inspection.setLogDate(rs.getDate(COLUMN_INSPECTION_DATE));

				queriedTenants.forEach(tnant -> {
					if (inspection.getFk() == tnant.getId()) {
						tnant.getInspections().add(inspection);
					}
				});
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying tenants");
			return null;
		}

		return queriedTenants;
	}

	/***/
	public List<Candidate> queryCandidates() {
		List<Candidate> queriedCandidates = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_CANDIDATES);

			// Set Fields
			while (rs.next()) {
				Candidate candidate = new Candidate();

				candidate.setId(rs.getInt(COLUMN_CAND_ID));
				candidate.setFk(rs.getInt(COLUMN_CAND_APT));
				candidate.setDateCreated(rs.getTimestamp(COLUMN_CAND_DATE_CREATED));
				candidate.setDateModified(rs.getTimestamp(COLUMN_CAND_DATE_MODIFIED));
				candidate.setFirstName(rs.getString(COLUMN_CAND_FNAME));
				candidate.setLastName(COLUMN_CAND_LNAME);
				candidate.setPhone(rs.getString(COLUMN_CAND_PHONE));
				candidate.setEmail(rs.getString(COLUMN_CAND_EMAIL));
				candidate.setDateOfBirth(rs.getDate(COLUMN_CAND_DOB));
				candidate.setSSN(rs.getString(COLUMN_CAND_IDN));
				candidate.setNumChildren(rs.getInt(COLUMN_CAND_NUM_CHILDREN));
				candidate.setAnnualIncome(rs.getInt(COLUMN_CAND_ANNUAL_INCOME));
				candidate.setAccepted(rs.getBoolean(COLUMN_CAND_ACCEPTED));

				queriedCandidates.add(candidate);
			}
			// Set foreign tables
			rs = statement.executeQuery(QUERY_CAND_SPOUSES);

			// Should only run once
			while (rs.next()) {
				Spouse candSpouse = new Spouse();

				candSpouse.setId(rs.getInt(COLUMN_SPOUSE_ID));
				candSpouse.setFk2(rs.getInt(COLUMN_SPOUSE_CAND_ID));
				candSpouse.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED));
				candSpouse.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED));
				candSpouse.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				candSpouse.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				candSpouse.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				candSpouse.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				candSpouse.setSSN(rs.getString(COLUMN_SPOUSE_IDN));
				candSpouse.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB));
				candSpouse.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				queriedCandidates.forEach(cand -> {
					if (candSpouse.getFk2() == cand.getId()) {
						cand.setSpouse(candSpouse);
					}
				});
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying candidates");
			return null;
		}

		return queriedCandidates;
	}

	/***/
	public List<Contractor> queryContractors() {
		List<Contractor> queriedContractors = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_CONTRACTORS);

			while (rs.next()) {
				Contractor contractor = new Contractor();

				contractor.setId(rs.getInt(COLUMN_CONT_ID));
				contractor.setFk(rs.getInt(COLUMN_CONT_APT));
				contractor.setDateCreated(rs.getTimestamp(COLUMN_CONT_DATE_CREATED));
				contractor.setDateModified(rs.getTimestamp(COLUMN_CONT_DATE_MODIFIED));
				contractor.setName(rs.getString(COLUMN_CONT_NAME));
				contractor.setBill(rs.getDouble(COLUMN_CONT_BILL));
				contractor.setPhone(rs.getString(COLUMN_CONT_PHONE));
				contractor.setEmail(rs.getString(COLUMN_CONT_EMAIL));

				queriedContractors.add(contractor);
			}
			// Set foreign tables
			// Invoices
			rs = statement.executeQuery(QUERY_CONT_INVOICES);
			while (rs.next()) {
				Invoice contInvoice = new Invoice();

				contInvoice.setId(rs.getInt(COLUMN_CONT_INVOICE_ID));
				contInvoice.setFk(rs.getInt(COLUMN_CONT_INVOICE_TNANT_ID));
				contInvoice.setDateCreated(rs.getTimestamp(COLUMN_CONT_INVOICE_DATE_CREATED));
				contInvoice.setDateModified(rs.getDate(COLUMN_CONT_INVOICE_DATE_MODIFIED));
				contInvoice.setPayment(rs.getDouble(COLUMN_CONT_INVOICE_PMT_AMOUNT));
				contInvoice.setBalance(rs.getDouble(COLUMN_CONT_INVOICE_BALANCE));
				contInvoice.setTotalPaid(rs.getDouble(COLUMN_CONT_INVOICE_TOTAL_PAID));
				contInvoice.setTotalDue(rs.getDouble(COLUMN_CONT_INVOICE_TOTAL_DUE));
				contInvoice.setPaymentDate(rs.getTimestamp(COLUMN_CONT_INVOICE_PAYMENT_DATE));
				contInvoice.setDueDate(rs.getTimestamp(COLUMN_CONT_INVOICE_PMT_DUE_DATE));

				queriedContractors.forEach(cont -> {
					if (contInvoice.getFk() == cont.getId()) {
						cont.getInvoices().add(contInvoice);
					}
				});
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying contractors");
			return null;
		}

		return queriedContractors;
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Insert Methods
	/***/
	public void insert(Apartment apartment) {
		try {
			// Save Apartment
			System.out.println("Saving Apartment");
			insertApartment.setInt(1, apartment.getId());
			insertApartment.setTimestamp(2, new Timestamp(apartment.getDateCreated().getTime()));
			insertApartment.setTimestamp(3, new Timestamp(apartment.getDateModified().getTime()));
			insertApartment.setString(4, apartment.getAddress());
			insertApartment.setString(5, apartment.getCity());
			insertApartment.setString(6, apartment.getState());
			insertApartment.setString(7, apartment.getZipCode());
			insertApartment.setInt(8, apartment.getCapacity());
			insertApartment.setInt(9, apartment.getNumTenants());

			insertApartment.execute();
			insertApartment.clearParameters();

			// Save sub-tables
			// Insurance
			for (Insurance insurance : apartment.getInsurances()) {
				insert(insurance);
			}

			//Bill
			for (Bill bill : apartment.getBills()) {
				insert(bill);
			}

			// Issue
			for (NoteLog issue : apartment.getIssues()) {
				insert(issue, DBTables.ISSUES);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	/***/
	public void insert(Tenant tenant) {
		try {
			// Save Tenant
			System.out.println("Saving Tenant");
			insertTenant.setInt(1, tenant.getId());
			insertTenant.setInt(2, tenant.getFk());
			insertTenant.setTimestamp(3, new Timestamp(tenant.getDateCreated().getTime()));
			insertTenant.setTimestamp(4, new Timestamp(tenant.getDateModified().getTime()));
			insertTenant.setString(5, tenant.getFirstName());
			insertTenant.setString(6, tenant.getLastName());
			insertTenant.setString(7, tenant.getPhone());
			insertTenant.setString(8, tenant.getEmail());
			insertTenant.setString(9, tenant.getSSN());
			insertTenant.setDouble(10, tenant.getRent());
			insertTenant.setInt(11, tenant.getNumChildren());
			insertTenant.setDate(12, new Date(tenant.getMovInDate().getTime()));
			insertTenant.setDate(13, new Date(tenant.getDateOfBirth().getTime()));
			insertTenant.setInt(14, tenant.getAnnualIncome());
			insertTenant.setBoolean(15, tenant.isEvicted());
			insertTenant.setString(16, tenant.getEvictReason());
			insertTenant.setDate(17, new Date(tenant.getMovOutDate().getTime()));

			insertTenant.execute();
			insertTenant.clearParameters();

			// Spouse
			if (!tenant.getSpouse().getFirstName().equals("") && !tenant.getSpouse().getLastName().equals(""))
				insert(tenant.getSpouse(), DBTables.TENANTS);

			// Tnant_Invoice
			for (Invoice invoice : tenant.getInvoices()) {
				insert(invoice, DBTables.TENANTS);
			}

			// Inspection
			for (NoteLog inspection : tenant.getInspections()) {
				insert(inspection, DBTables.INSPECTIONS);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(Candidate candidate) {
		try {
			// Save Candidate
			System.out.println("Saving Candidate");
			insertCandidate.setInt(1, candidate.getId());
			insertCandidate.setInt(2, candidate.getFk());
			insertCandidate.setTimestamp(3, new Timestamp(candidate.getDateCreated().getTime()));
			insertCandidate.setTimestamp(4, new Timestamp(candidate.getDateModified().getTime()));
			insertCandidate.setString(5, candidate.getFirstName());
			insertCandidate.setString(6, candidate.getLastName());
			insertCandidate.setString(7, candidate.getPhone());
			insertCandidate.setString(8, candidate.getEmail());
			insertCandidate.setDate(9, new Date(candidate.getDateOfBirth().getTime()));
			insertCandidate.setInt(10, candidate.getAnnualIncome());
			insertCandidate.setString(11, candidate.getSSN());
			insertCandidate.setInt(12, candidate.getNumChildren());
			insertCandidate.setBoolean(13, candidate.isAccepted());

			insertCandidate.execute();
			insertCandidate.clearParameters();

			// Spouse
			if (!candidate.getSpouse().getFirstName().equals("") && !candidate.getSpouse().getLastName().equals(""))
				insert(candidate.getSpouse(), DBTables.CANDIDATES);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(Contractor contractor) {
		try {
			// Save Contractor
			System.out.println("Saving Contractor");
			insertContractor.setInt(1, contractor.getId());
			insertContractor.setInt(2, contractor.getFk());
			insertContractor.setTimestamp(3, new Timestamp(contractor.getDateCreated().getTime()));
			insertContractor.setTimestamp(4, new Timestamp(contractor.getDateModified().getTime()));
			insertContractor.setString(5, contractor.getName());
			insertContractor.setDouble(6, contractor.getBill());
			insertContractor.setString(7, contractor.getPhone());
			insertContractor.setString(8, contractor.getEmail());

			// Invoice
			for (Invoice invoice : contractor.getInvoices()) {
				insert(invoice, DBTables.CONTRACTORS);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(Insurance insurance) {
		try {
			System.out.println("Saving Insurance");
			insertInsurance.setInt(1, insurance.getId());
			insertInsurance.setInt(2, insurance.getFk());
			insertInsurance.setTimestamp(3, new Timestamp(insurance.getDateCreated().getTime()));
			insertInsurance.setTimestamp(4, new Timestamp(insurance.getDateModified().getTime()));
			insertInsurance.setString(5, insurance.getName());
			insertInsurance.setDouble(6, insurance.getBill());
			insertInsurance.setString(7, insurance.getPhone());
			insertInsurance.setString(8, insurance.getEmail());

			insertInsurance.execute();
			insertInsurance.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(NoteLog issInsp, DBTables table) {
		try {
			switch (table) {
				case INSPECTIONS:
					System.out.println("Saving Inspection");
					insertInspection.setInt(1, issInsp.getId());
					insertInspection.setInt(2, issInsp.getFk());
					insertInspection.setTimestamp(3, new Timestamp(issInsp.getDateCreated().getTime()));
					insertInspection.setTimestamp(4, new Timestamp(issInsp.getDateModified().getTime()));
					insertInspection.setString(5, issInsp.getLog());
					insertInspection.setDate(6, new Date(issInsp.getLogDate().getTime()));

					insertInspection.execute();
					insertInspection.clearParameters();
					break;
				case ISSUES:
					System.out.println("Saving Issue");
					insertIssue.setInt(1, issInsp.getId());
					insertIssue.setInt(2, issInsp.getFk());
					insertIssue.setTimestamp(3, new Timestamp(issInsp.getDateCreated().getTime()));
					insertIssue.setTimestamp(4, new Timestamp(issInsp.getDateModified().getTime()));
					insertIssue.setString(5, issInsp.getLog());
					insertIssue.setDate(6, new Date(issInsp.getLogDate().getTime()));

					insertIssue.execute();
					insertIssue.clearParameters();
					break;
				default:
					break;
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(Bill bill) {
		try {
			System.out.println("Saving Bill");
			insertBill.setInt(1, bill.getId());
			insertBill.setInt(2, bill.getFk());
			insertBill.setTimestamp(3, new Timestamp(bill.getDateCreated().getTime()));
			insertBill.setTimestamp(4, new Timestamp(bill.getDateModified().getTime()));
			insertBill.setString(5, bill.getBillName());
			insertBill.setString(6, bill.getAddress());
			insertBill.setString(7, bill.getPhone());
			insertBill.setDouble(8, bill.getBill());

			insertBill.execute();
			insertBill.clearParameters();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***/
	public void insert(Spouse spouse, DBTables table) {
		try {
			switch(table) {
				case TENANTS:
					System.out.println("Saving Spouse(tenant)");
					insertTnantSpouse.setInt(1, spouse.getId());
					insertTnantSpouse.setInt(2, spouse.getFk());
					insertTnantSpouse.setTimestamp(3, new Timestamp(spouse.getDateCreated().getTime()));
					insertTnantSpouse.setTimestamp(4, new Timestamp(spouse.getDateModified().getTime()));
					insertTnantSpouse.setString(5, spouse.getFirstName());
					insertTnantSpouse.setString(6, spouse.getLastName());
					insertTnantSpouse.setString(7, spouse.getPhone());
					insertTnantSpouse.setString(8, spouse.getEmail());
					insertTnantSpouse.setString(9, spouse.getSSN());
					insertTnantSpouse.setDate(10, new Date(spouse.getDateOfBirth().getTime()));
					insertTnantSpouse.setInt(11, spouse.getAnnualIncome());

					insertTnantSpouse.execute();
					insertTnantSpouse.clearParameters();
					break;
				case CANDIDATES:
					System.out.println("Saving Spouse(candidate)");
					insertCandSpouse.setInt(1, spouse.getId());
					insertCandSpouse.setInt(2, spouse.getFk());
					insertCandSpouse.setTimestamp(3, new Timestamp(spouse.getDateCreated().getTime()));
					insertCandSpouse.setTimestamp(4, new Timestamp(spouse.getDateModified().getTime()));
					insertCandSpouse.setString(5, spouse.getFirstName());
					insertCandSpouse.setString(6, spouse.getLastName());
					insertCandSpouse.setString(7, spouse.getPhone());
					insertCandSpouse.setString(8, spouse.getEmail());
					insertCandSpouse.setString(9, spouse.getSSN());
					insertCandSpouse.setDate(10, new Date(spouse.getDateOfBirth().getTime()));
					insertCandSpouse.setInt(11, spouse.getAnnualIncome());

					insertCandSpouse.execute();
					insertCandSpouse.clearParameters();
					break;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void insert(Invoice invoice, DBTables table) {
		try {
			switch (table) {
				case TENANTS:
					System.out.println("Saving Invoice(tenant)");
					insertTnantInvoice.setInt(1, invoice.getId());
					insertTnantInvoice.setInt(2, invoice.getFk());
					insertTnantInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
					insertTnantInvoice.setTimestamp(4, new Timestamp(invoice.getDateModified().getTime()));
					insertTnantInvoice.setDouble(5, invoice.getPayment());
					insertTnantInvoice.setDouble(6,invoice.getDues());
					insertTnantInvoice.setDouble(7, invoice.getBalance());
					insertTnantInvoice.setDouble(8, invoice.getTotalPaid());
					insertTnantInvoice.setDouble(9, invoice.getTotalDue());
					insertTnantInvoice.setTimestamp(10, new Timestamp(invoice.getPaymentDate().getTime()));
					insertTnantInvoice.setTimestamp(11, new Timestamp(invoice.getDueDate().getTime()));

					insertTnantInvoice.execute();
					insertTnantInvoice.clearParameters();
					break;
				case CONTRACTORS:
					System.out.println("Saving Invoice (Contractor)");
					insertContInvoice.setInt(1, invoice.getId());
					insertContInvoice.setInt(2, invoice.getFk());
					insertContInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
					insertContInvoice.setTimestamp(4, new Timestamp(invoice.getDateModified().getTime()));
					insertContInvoice.setDouble(5, invoice.getPayment());
					insertContInvoice.setDouble(6,invoice.getDues());
					insertContInvoice.setDouble(7, invoice.getBalance());
					insertContInvoice.setDouble(8, invoice.getTotalPaid());
					insertContInvoice.setDouble(9, invoice.getTotalDue());
					insertContInvoice.setTimestamp(10, new Timestamp(invoice.getPaymentDate().getTime()));
					insertContInvoice.setTimestamp(11, new Timestamp(invoice.getDueDate().getTime()));

					insertContInvoice.execute();
					insertContInvoice.clearParameters();
					break;
				case INSURANCES:
					System.out.println("Saving Invoice (Insurance)");
					insertInsInvoice.setInt(1, invoice.getId());
					insertInsInvoice.setInt(2, invoice.getFk());
					insertInsInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
					insertInsInvoice.setTimestamp(4, new Timestamp(invoice.getDateModified().getTime()));
					insertInsInvoice.setDouble(5, invoice.getPayment());
					insertInsInvoice.setDouble(6,invoice.getDues());
					insertInsInvoice.setDouble(7, invoice.getBalance());
					insertInsInvoice.setDouble(8, invoice.getTotalPaid());
					insertInsInvoice.setDouble(9, invoice.getTotalDue());
					insertInsInvoice.setTimestamp(10, new Timestamp(invoice.getPaymentDate().getTime()));
					insertInsInvoice.setTimestamp(11, new Timestamp(invoice.getDueDate().getTime()));

					insertInsInvoice.execute();
					insertInsInvoice.clearParameters();
					break;
				case BILLS:
					System.out.println("Saving Invoice (Bill)");
					insertBillInvoice.setInt(1, invoice.getId());
					insertBillInvoice.setInt(2, invoice.getFk());
					insertBillInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
					insertBillInvoice.setTimestamp(4, new Timestamp(invoice.getDateModified().getTime()));
					insertBillInvoice.setDouble(5, invoice.getPayment());
					insertBillInvoice.setDouble(6,invoice.getDues());
					insertBillInvoice.setDouble(7, invoice.getBalance());
					insertBillInvoice.setDouble(8, invoice.getTotalPaid());
					insertBillInvoice.setDouble(9, invoice.getTotalDue());
					insertBillInvoice.setTimestamp(10, new Timestamp(invoice.getPaymentDate().getTime()));
					insertBillInvoice.setTimestamp(11, new Timestamp(invoice.getDueDate().getTime()));

					insertBillInvoice.execute();
					insertBillInvoice.clearParameters();
					break;
				default:
					//stuff
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Update methods
	/***/
	public void update(Apartment apartment) {
		try {
			connection.setAutoCommit(false);
			delete(apartment);
			insert(apartment);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error updating apartment");
		}
	}

	/***/
	public void update(Tenant tenant) {
		try {
			connection.setAutoCommit(false);
			delete(tenant);
			insert(tenant);

			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error updating tenant");
		}
	}

	/***/
	public void update(Candidate candidate) {
		try {
			connection.setAutoCommit(false);
			delete(candidate);
			insert(candidate);

			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error updating candidate");
		}
	}

	/***/
	public void update(Contractor contractor) {
		try {
			connection.setAutoCommit(false);
			delete(contractor);
			insert(contractor);

			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error updating contractor");
		}
	}

	/***/
	public void update(Insurance insurance) {
		try {
			connection.setAutoCommit(false);
			delete(insurance);
			insert(insurance);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void update(Bill bill) {
		try {
			connection.setAutoCommit(false);
			delete(bill);
			insert(bill);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/***/
	public void update(NoteLog issInsp, DBTables table) {
		try {
			connection.setAutoCommit(false);
			delete(issInsp, table);
			insert(issInsp, table);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void update(Spouse spouse, DBTables table) {
		try {
			connection.setAutoCommit(false);
			delete(spouse,table);
			insert(spouse,table);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void update(Invoice invoice, DBTables table) {
		try {
			connection.setAutoCommit(false);
			delete(invoice, table);
			insert(invoice, table);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Delete methods
	/***/
	public void delete(Apartment apartment) {
		try {
			deleteApartment.setInt(1, apartment.getId());
			deleteApartment.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting apartment");
		}
	}

	/***/
	public void delete(Tenant tenant) {
		try {
			deleteTenant.setInt(1, tenant.getId());
			deleteTenant.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting tenant");
		}
	}

	/***/
	public void delete(Candidate candidate) {
		try {
			deleteCandidate.setInt(1, candidate.getId());
			deleteCandidate.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting candidate");
		}
	}

	/***/
	public void delete(Contractor contractor) {
		try {
			deleteContractor.setInt(1, contractor.getId());
			deleteContractor.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting contractor");
		}
	}

	/***/
	public void delete(Insurance insurance) {
		try {
			deleteInsurance.setInt(1, insurance.getId());
			deleteInsurance.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/***/
	public void delete(NoteLog issInsp, DBTables table) {
		try {
			switch (table) {
				case INSPECTIONS:
					deleteInspection.setInt(1, issInsp.getId());
					deleteInspection.execute();
					break;
				case ISSUES:
					deleteIssue.setInt(1, issInsp.getId());
					deleteIssue.execute();
					break;
				default:
					throw new SQLException("Invalid Table Operand");
			}

		} catch (SQLException e) {
			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	/***/
	public void delete(Bill bill) {
		try {
			deleteBill.setInt(1, bill.getId());
			deleteBill.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***/
	public void delete(Spouse spouse, DBTables table) {
		try {
			switch(table){
				case TENANTS:
					deleteTnantSpouse.setInt(1, spouse.getId());
					deleteInspection.execute();
					break;
				case CANDIDATES:
					deleteCandSpouse.setInt(1, spouse.getId());
					deleteInspection.execute();
					break;
				default:
					throw new SQLException("Invalid Table Operand");
			}
		} catch (SQLException e) {
			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	/***/
	public void delete(Invoice invoice, DBTables table) {
		try {
			switch (table) {
				case TENANTS:
					deleteTnantInvoice.setInt(1, invoice.getId());
					deleteTnantInvoice.execute();
					break;
				case CONTRACTORS:
					deleteContInvoice.setInt(1, invoice.getId());
					deleteContInvoice.execute();
					break;
				case INSURANCES:
					deleteInsInvoice.setInt(1, invoice.getId());
					deleteInsInvoice.execute();
					break;
				case BILLS:
					deleteBillInvoice.setInt(1, invoice.getId());
					deleteInsInvoice.execute();
					break;
				default:
					throw new SQLException("Invalid Table Operand");
			}

		} catch (SQLException e) {
			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	// ---------------------------------------------------------------------------------
	// Other
	/**
	 * Sets preparedStatements from constants
	 * */
	private void prepareStatements() {
		try {
			// Inserts
			// Insert Prepared Statements
			insertApartment = connection.prepareStatement(INSERT_INTO_APARTMENTS);
			insertTenant = connection.prepareStatement(INSERT_INTO_TENANTS);
			insertTnantSpouse = connection.prepareStatement(INSERT_INTO_TNANT_SPOUSES);
			insertTnantInvoice = connection.prepareStatement(INSERT_INTO_TNANT_INVOICES);
			insertInspection = connection.prepareStatement(INSERT_INTO_INSPECTIONS);
			insertCandidate = connection.prepareStatement(INSERT_INTO_CANDIDATES);
			insertCandSpouse = connection.prepareStatement(INSERT_INTO_CAND_SPOUSES);
			insertContractor = connection.prepareStatement(INSERT_INTO_CONTRACTORS);
			insertContInvoice = connection.prepareStatement(INSERT_INTO_CONT_INVOICES);
			insertInsurance = connection.prepareStatement(INSERT_INTO_INSURANCES);
			insertInsInvoice = connection.prepareStatement(INSERT_INTO_INS_INVOICES);
			insertBill = connection.prepareStatement(INSERT_INTO_BILLS);
			insertBillInvoice = connection.prepareStatement(INSERT_INTO_BILL_INVOICES);
			insertIssue = connection.prepareStatement(INSERT_INTO_ISSUES);

			//Updates
			updateApartment = connection.prepareStatement(UPDATE_APARTMENT);
			updateTenant = connection.prepareStatement(UPDATE_TENANTS);
//			updateTnantSpouse = connection.prepareStatement(UPDATE_TNANT_SPOUSE);
// 			updateTnantInvoice = connection.prepareStatement(UPDATE_TNANT_INVOICE;
//			updateInspection = connection.prepareStatement(UPDATE_INSPECTION);
//			updateCandidate = connection.prepareStatement(UPDATE_CANDIDATE);
// 			updateCandSpouse = connection.prepareStatement(UPDATE_CAND_SPOUSE);
//			updateContractor = connection.prepareStatement(UPDATE_CONTRACTOR);
//			updateContInvoice = connection.prepareStatement(UPDATE_CONT_INVOICE);
//			updateInsurance = connection.prepareStatement(UPDATE_INSURANCE);
//			updateInsInvoice = connection.prepareStatement(UPDATE_INS_INVOICE);
//			updateBill = connection.prepareStatement(UPDATE_BILL);
// 			updateBillInvoice = connection.prepareStatement(UPDATE_BILL_INVOICE);
//			updateIssue = connection.prepareStatement(UPDATE_ISSUE);

			// Deletes
			deleteApartment = connection.prepareStatement(DELETE_FROM_APARTMENTS);
			deleteTenant = connection.prepareStatement(DELETE_FROM_TENANTS);
			deleteTnantSpouse = connection.prepareStatement(DELETE_FROM_TNANT_SPOUSES);
			deleteTnantInvoice = connection.prepareStatement(DELETE_FROM_TNANT_INVOICES);
			deleteInspection = connection.prepareStatement(DELETE_FROM_INSPECTIONS);
			deleteCandidate = connection.prepareStatement(DELETE_FROM_CANDIDATES);
			deleteCandSpouse = connection.prepareStatement(DELETE_FROM_CAND_SPOUSES);
			deleteContractor = connection.prepareStatement(DELETE_FROM_CONTRACTORS);
			deleteContInvoice = connection.prepareStatement(DELETE_FROM_CONT_INVOICES);
			deleteInsurance = connection.prepareStatement(DELETE_FROM_INSURANCES);
			deleteInsInvoice = connection.prepareStatement(DELETE_FROM_INS_INVOICES);
			deleteBill = connection.prepareStatement(DELETE_FROM_BILLS);
			deleteBillInvoice = connection.prepareStatement(DELETE_FROM_BILL_INVOICES);
			deleteIssue = connection.prepareStatement(DELETE_FROM_ISSUES);

			preparedStatementsSet = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Something went wrong with preparing statements");
			preparedStatementsSet = false;

		}
	}

	/**
	 * Closes preparedStatements
	 * */
	private void closeStatements() {
		try {
			if (preparedStatementsSet) {
				insertApartment.close();
				insertTenant.close();
				insertTnantSpouse.close();
				insertTnantInvoice.close();
				insertInspection.close();
				insertCandidate.close();
				insertCandSpouse.close();
				insertContractor.close();
				insertContInvoice.close();
				insertInsurance.close();
				insertInsInvoice.close();
				insertBill.close();
				insertBillInvoice.close();
				insertIssue.close();

				//Update Prepared Statements
//				updateApartment.close();
//				updateTenant.close();
//				updateCandidate.close();
//				updateContractor.close();
//				updateInsurance.close();
//				updateIssue.close();
//				updateInspection.close();
//				updateSpouse.close();
//				updateTnantInvoice.close();
//				updateContInvoice.close();


				// Delete Prepared statements
				deleteApartment.close();
				deleteTenant.close();
				deleteTnantSpouse.close();
				deleteTnantInvoice.close();
				deleteInspection.close();
				deleteCandidate.close();
				deleteCandSpouse.close();
				deleteContractor.close();
				deleteContInvoice.close();
				deleteInsurance.close();
				deleteInsInvoice.close();
				deleteBill.close();
				deleteBillInvoice.close();
				deleteIssue.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
