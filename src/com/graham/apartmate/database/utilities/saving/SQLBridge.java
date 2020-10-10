package com.graham.apartmate.database.utilities.saving;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.*;
import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.main.Main;


/**
 * SQL Class; establishes connection and facilitates the saving and loading of
 * table
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Javadoc's for every method
// finish update constants
@SuppressWarnings("FieldCanBeLocal")
public class SQLBridge {

	// Connection variables
	/**
	 * SQL connection instance
	 * */
	private Connection connection;

	/**
	 * SQL statement
	 * */
	private Statement statement;

	/**
	 * Result Set from statement
	 * */
	private ResultSet rs;

	/**
	 * Server url
	 * */
	private final String url;

	/**
	 * Default username for connection
	 * */
	private final String defaultUserName;

	/**
	 * default password for connection
	 * */
	private final String defaultPass;

	/**
	 * Prepared statement flag; <code>true</code> if set correctly
	 * <code>false</code> if not
	 * */
	private boolean preparedStatementsSet = false;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Table constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Tables
	/**
	 * Aparment table constant
	 * */
	private final String TABLE_APARTMENTS = "apartments";

	/**
	 * Tenant table constant
	 * */
	private final String TABLE_TENANTS = "tenants";

	/**
	 * Candidate table constant
	 * */
	private final String TABLE_CANDIDATES = "candidates";

	/**
	 * Contractor table constant
	 * */
	private final String TABLE_CONTRACTORS = "contractors";

	// Sub-tables
	/**
	 * Insurance table constant
	 * */
	private final String SUB_TABLE_INSURANCES = "insurances";

	/**
	 * Insurance Invoice table constant
	 * */
	private final String SUB_TABLE_INS_INVOICES = "ins_invoices";

	/**
	 * Bill table constant
	 * */
	private final String SUB_TABLE_BILLS = "bills";

	/**
	 * Bill Invoice table constant
	 * */
	private final String SUB_TABLE_BILL_INVOICES = "bill_invoices";

	/**
	 * Issue table constant
	 * */
	private final String SUB_TABLE_ISSUES = "issues";

	/**
	 * Inspection table constant
	 * */
	private final String SUB_TABLE_INSPECTIONS = "inspections";

	/**
	 * Tenant Spouse table constant
	 * */
	private final String SUB_TABLE_TNANT_SPOUSES = "tnant_spouses";

	/**
	 * Candidate Spouse table constant
	 * */
	private final String SUB_TABLE_CAND_SPOUSES = "cand_spouses";

	/**
	 * Tenant Invoice table constant
	 * */
	private final String SUB_TABLE_TNANT_INVOICES = "tnant_invoices";

	/**
	 * Contractor Invoice table constant
	 * */
	private final String SUB_TABLE_CONT_INVOICES = "cont_invoices";

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Column constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Apartment table columns
	/**
	 * Apartment Id column
	 * */
	private final String COLUMN_APT_ID = "apartment_id";

	/**
	 * Apartment date created column
	 * */
	private final String COLUMN_APT_DATE_CREATED = "date_created";

	/**
	 * Apartment date modified column
	 * */
	private final String COLUMN_APT_DATE_MODIFIED = "date_modified";

	/**
	 * Apartment address column
	 * */
	private final String COLUMN_APT_ADDRESS = "address";

	/**
	 * Apartment city column
	 * */
	private final String COLUMN_APT_CITY = "city";

	/**
	 * Apartment state column
	 * */
	private final String COLUMN_APT_STATE = "state";

	/**
	 * Apartment zip code column
	 * */
	private final String COLUMN_APT_ZIP = "zip";

	/**
	 * Apartment capacity column
	 * */
	private final String COLUMN_APT_CAPACITY = "capacity";

	/**
	 * Apartment number of tenants column
	 * */
	private final String COLUMN_APT_NUM_TENANTS = "num_tenants";

	// Tenant table columns
	/**
	 * Tenant Id column
	 * */
	private final String COLUMN_TNANT_ID = "tenant_id";

	/**
	 * Tenant-Apartment foreign key column
	 * */
	private final String COLUMN_TNANT_APT = "apartment_id";

	/**
	 * Tenant date created column
	 * */
	private final String COLUMN_TNANT_DATE_CREATED = "date_created";

	/**
	 * Tenant date modified column
	 * */
	private final String COLUMN_TNANT_DATE_MODIFIED = "date_modified";

	/**
	 * Tenant first name column
	 * */
	private final String COLUMN_TNANT_FNAME = "first_name";

	/**
	 * Tenant last name column
	 * */
	private final String COLUMN_TNANT_LNAME = "last_name";

	/**
	 * Tenant phone number column
	 * */
	private final String COLUMN_TNANT_PHONE = "phone_number";

	/**
	 * Tenant email address column
	 * */
	private final String COLUMN_TNANT_EMAIL = "email";

	/**
	 * Tenant Id number column
	 * */
	private final String COLUMN_TNANT_IDN = "ssn";

	/**
	 * Tenant rent column
	 * */
	private final String COLUMN_TNANT_RENT = "rent";

	/**
	 * Tenant number of children column
	 * */
	private final String COLUMN_TNANT_NUM_CHILDREN = "num_children";

	/**
	 * Tenant move in date column
	 * */
	private final String COLUMN_TNANT_MOVE_IN_DATE = "move_in_date";

	/**
	 * Tenant date of birth column
	 * */
	private final String COLUMN_TNANT_DOB = "date_of_birth";

	/**
	 * Tenant annual income column
	 * */
	private final String COLUMN_TNANT_ANNUAL_INCOME = "annual_income";

	/**
	 * Tenant evicted? column
	 * */
	private final String COLUMN_TNANT_SLATED_FOR_EVICTION = "slated_for_eviction";

	/**
	 * Tenant eviction reason column
	 * */
	private final String COLUMN_TNANT_EVICT_REASON = "evict_reason";

	/**
	 * Tenant move out date column
	 * */
	private final String COLUMN_TNANT_MOV_OUT_DATE = "move_out_date";

	// Spouse table columns
	/**
	 * Spouse Id column
	 * */
	private final String COLUMN_SPOUSE_ID = "spouse_id";

	/**
	 * Spouse-Tenant foreign key column
	 * */
	private final String COLUMN_SPOUSE_TNANT_ID = "tenant_id";

	/**
	 * Spouse-Candidate foreign key column
	 * */
	private final String COLUMN_SPOUSE_CAND_ID = "candidate_id";

	/**
	 * Spouse date created column
	 * */
	private final String COLUMN_SPOUSE_DATE_CREATED = "date_created";

	/**
	 * Spouse date modified column
	 * */
	private final String COLUMN_SPOUSE_DATE_MODIFIED = "date_modified";

	/**
	 * Spouse first name column
	 * */
	private final String COLUMN_SPOUSE_FNAME = "first_name";

	/**
	 * Spouse last name column
	 * */
	private final String COLUMN_SPOUSE_LNAME = "last_name";

	/**
	 * Spouse phone number column
	 * */
	private final String COLUMN_SPOUSE_PHONE = "phone_number";

	/**
	 * Spouse email address column
	 * */
	private final String COLUMN_SPOUSE_EMAIL = "email";

	/**
	 * Spouse Id number column
	 * */
	private final String COLUMN_SPOUSE_IDN = "ssn";

	/**
	 * Spouse date of birth column
	 * */
	private final String COLUMN_SPOUSE_DOB = "date_of_birth";

	/**
	 * Spouse annual income column
	 * */
	private final String COLUMN_SPOUSE_ANNUAL_INCOME = "annual_income";

	// Tenant invoice table columns
	/**
	 * Invoice Id column
	 * */
	private final String COLUMN_TNANT_INVOICE_ID = "invoice_id";

	/**
	 * Invoice-Tenant foreign key column
	 * */
	private final String COLUMN_TNANT_INVOICE_TNANT_ID = "tenant_id";

	/**
	 * Invoice date created column
	 * */
	private final String COLUMN_TNANT_INVOICE_DATE_CREATED = "date_created";

	/**
	 * Invoice date modified column
	 * */
	private final String COLUMN_TNANT_INVOICE_DATE_MODIFIED = "date_modified";

	/**
	 * Invoice payment amount column
	 * */
	private final String COLUMN_TNANT_INVOICE_PMT_AMOUNT = "payment_amount";

	/**
	 * Invoice dues column
	 * */
	private final String COLUMN_TNANT_INVOICE_DUES = "dues";

	/**
	 * Invoice balance column
	 * */
	private final String COLUMN_TNANT_INVOICE_BALANCE = "balance";

	/**
	 * Invoice total paid column
	 * */
	private final String COLUMN_TNANT_INVOICE_TOTAL_PAID = "total_paid";

	/**
	 * Invoice total due column
	 * */
	private final String COLUMN_TNANT_INVOICE_TOTAL_DUE = "total_due";

	/**
	 * Invoice payment date column
	 * */
	private final String COLUMN_TNANT_INVOICE_PAYMENT_DATE = "payment_date";

	/**
	 * Invoice payment due date column
	 * */
	private final String COLUMN_TNANT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// Inspection table columns
	/**
	 * Inspection Id column
	 * */
	private final String COLUMN_INSPECTION_ID = "inspection_id";

	/**
	 * Inspection-Tenant foreign key column
	 * */
	private final String COLUMN_INSPECTION_TNANT_ID = "tenant_id";

	/**
	 * Inspection date created column
	 * */
	private final String COLUMN_INSPECTION_DATE_CREATED = "date_created";

	/**
	 * Inspection date modified column
	 * */
	private final String COLUMN_INSPECTION_DATE_MODIFIED = "date_modified";

	/**
	 * Inspection description column
	 * */
	private final String COLUMN_INSPECTION_DESCRIPTION = "inspection_desc";

	/**
	 * Inspection date column
	 * */
	private final String COLUMN_INSPECTION_DATE = "inspection_date";

	// Candidate table columns
	/**
	 * Candidate Id column
	 * */
	private final String COLUMN_CAND_ID = "candidate_id";

	/**
	 * Candidate-Apartment foreign key column
	 * */
	private final String COLUMN_CAND_APT = "apartment_id";

	/**
	 * Candidate date created column
	 * */
	private final String COLUMN_CAND_DATE_CREATED = "date_created";

	/**
	 * Candidate date modified column
	 * */
	private final String COLUMN_CAND_DATE_MODIFIED = "date_modified";

	/**
	 * Candidate first name column
	 * */
	private final String COLUMN_CAND_FNAME = "first_name";

	/**
	 * Candidate last name column
	 * */
	private final String COLUMN_CAND_LNAME = "last_name";

	/**
	 * Candidate phone number column
	 * */
	private final String COLUMN_CAND_PHONE = "phone_number";

	/**
	 * Candidate email column
	 * */
	private final String COLUMN_CAND_EMAIL = "email";

	/**
	 * Candidate date od birth column
	 * */
	private final String COLUMN_CAND_DOB = "date_of_birth";

	/**
	 * Candidate annual income column
	 * */
	private final String COLUMN_CAND_ANNUAL_INCOME = "annual_income";

	/**
	 * Candidate Id number column
	 * */
	private final String COLUMN_CAND_IDN = "ssn";

	/**
	 * Candidate number of children column
	 * */
	private final String COLUMN_CAND_NUM_CHILDREN = "num_children";

	/**
	 * Candidate accepted? column
	 * */
	private final String COLUMN_CAND_ACCEPTED = "accepted";

	// Contractor table columns
	/**
	 * Contractor Id column
	 * */
	private final String COLUMN_CONT_ID = "contractor_id";

	/**
	 * Contractor-Apartment foreign key column
	 * */
	private final String COLUMN_CONT_APT = "apartment_id";

	/**
	 * Contractor date created column
	 * */
	private final String COLUMN_CONT_DATE_CREATED = "date_created";

	/**
	 * Contractor date modified column
	 * */
	private final String COLUMN_CONT_DATE_MODIFIED = "date_modified";

	/**
	 * Contractor company name column
	 * */
	private final String COLUMN_CONT_NAME = "company_name";

	/**
	 * Contractor bill column
	 * */
	private final String COLUMN_CONT_BILL = "bill";

	/**
	 * Contractor phone number column
	 * */
	private final String COLUMN_CONT_PHONE = "phone_number";

	/**
	 * Contractor email address column
	 * */
	private final String COLUMN_CONT_EMAIL = "email";

	// Contractor Invoice table columns
	/**
	 * Invoice Id column
	 * */
	private final String COLUMN_CONT_INVOICE_ID = "invoice_id";

	/**
	 * Invoice-Contractor foreign key column
	 * */
	private final String COLUMN_CONT_INVOICE_CONT_ID = "contractor_id";

	/**
	 * Invoice date created column
	 * */
	private final String COLUMN_CONT_INVOICE_DATE_CREATED = "date_created";

	/**
	 * Invoice date modified column
	 * */
	private final String COLUMN_CONT_INVOICE_DATE_MODIFIED = "date_modified";

	/**
	 * Invoice payment amount column
	 * */
	private final String COLUMN_CONT_INVOICE_PMT_AMOUNT = "payment_amount";

	/**
	 * Invoice dues column
	 * */
	private final String COLUMN_CONT_INVOICE_DUES = "dues";

	/**
	 * Invoice balance column
	 * */
	private final String COLUMN_CONT_INVOICE_BALANCE = "balance";

	/**
	 * Invoice total paid column
	 * */
	private final String COLUMN_CONT_INVOICE_TOTAL_PAID = "total_paid";

	/**
	 * Invoice total due column
	 * */
	private final String COLUMN_CONT_INVOICE_TOTAL_DUE = "total_due";

	/**
	 * Invoice payment date column
	 * */
	private final String COLUMN_CONT_INVOICE_PAYMENT_DATE = "payment_date";

	/**
	 * Invoice payment due date column
	 * */
	private final String COLUMN_CONT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// Insurance table columns
	/**
	 * Insurance Id column
	 * */
	private final String COLUMN_INSURANCE_ID = "insurance_id";

	/**
	 * Insurance-Apartment foreign key column
	 * */
	private final String COLUMN_INSURANCE_APT_ID = "apartment_id";

	/**
	 * Insurance date created column
	 * */
	private final String COLUMN_INSURANCE_DATE_CREATED = "date_created";

	/**
	 * Insurance date modified column
	 * */
	private final String COLUMN_INSURANCE_DATE_MODIFIED = "date_modified";

	/**
	 * Insurance company name column
	 * */
	private final String COLUMN_INSURANCE_NAME = "ins_name";

	/**
	 * Insurance bill column
	 * */
	private final String COLUMN_INSURANCE_BILL = "bill";

	/**
	 * Insurance phone number column
	 * */
	private final String COLUMN_INSURANCE_PHONE = "phone_number";

	/**
	 * Insurance email address column
	 * */
	private final String COLUMN_INSURANCE_EMAIL = "email";

	// Insurance Invoice columns
	/**
	 * Invoice Id column
	 * */
	private final String COLUMN_INS_INVOICE_ID = "invoice_id";

	/**
	 * Invoice-Insurance foreign key column
	 * */
	private final String COLUMN_INS_INVOICE_APT_ID = "apartment_id";

	/**
	 * Invoice date created column
	 * */
	private final String COLUMN_INS_INVOICE_DATE_CREATED = "date_created";

	/**
	 * Invoice date modified column
	 * */
	private final String COLUMN_INS_INVOICE_DATE_MODIFIED = "date_modified";

	/**
	 * Invoice payment amount column
	 * */
	private final String COLUMN_INS_INVOICE_PAYMENT = "payment_amount";

	/**
	 * Invoice dues column
	 * */
	private final String COLUMN_INS_INVOICE_DUES = "dues";

	/**
	 * Invoice balance column
	 * */
	private final String COLUMN_INS_INVOICE_BALANCE = "balance";

	/**
	 * Invoice total paid column
	 * */
	private final String COLUMN_INS_INVOICE_TOTAL_PAID = "total_paid";

	/**
	 * Invoice total due column
	 * */
	private final String COLUMN_INS_INVOICE_TOTAL_DUE = "total_due";

	/**
	 * Invoice payment date column
	 * */
	private final String COLUMN_INS_INVOICE_PMT_DATE = "payment_date";

	/**
	 * Invoice payment due date column
	 * */
	private final String COLUMN_INS_INVOICE_DUE_DATE = "payment_due_date";

	// Bill table columns
	/**
	 * Bill Id column
	 * */
	private final String COLUMN_BILL_ID = "bill_id";

	/**
	 * Bill-Apartment foreign key column
	 * */
	private final String COLUMN_BILL_APT_ID = "apartment_id";

	/**
	 * Bill date created column
	 * */
	private final String COLUMN_BILL_DATE_CREATED = "date_created";

	/**
	 * Bill date modified column
	 * */
	private final String COLUMN_BILL_DATE_MODIFIED = "date_modified";

	/**
	 * Bill name column
	 * */
	private final String COLUMN_BILL_NAME = "bill_name";

	/**
	 * Bill address column
	 * */
	private final String COLUMN_BILL_ADDRESS = "address";

	/**
	 * Bill phone number column
	 * */
	private final String COLUMN_BILL_PHONE = "phone";

	/**
	 * Bill bill column
	 * */
	private final String COLUMN_BILL_BILL = "bill";

	// Bill Invoice columns
	/**
	 * Invoice Id column
	 * */
	private final String COLUMN_BILL_INVOICE_ID = "invoice_id";

	/**
	 * Invoice-Bill foreign key column
	 * */
	private final String COLUMN_BILL_INVOICE_APT_ID = "apartment_id";

	/**
	 * Invoice date created column
	 * */
	private final String COLUMN_BILL_INVOICE_DATE_CREATED = "date_created";

	/**
	 * Invoice date modified column
	 * */
	private final String COLUMN_BILL_INVOICE_DATE_MODIFIED = "date_modified";

	/**
	 * Invoice payment amount column
	 * */
	private final String COLUMN_BILL_INVOICE_PAYMENT = "payment_amount";

	/**
	 * Invoice dues column
	 * */
	private final String COLUMN_BILL_INVOICE_DUES = "dues";

	/**
	 * Invoice balance column
	 * */
	private final String COLUMN_BILL_INVOICE_BALANCE = "balance";

	/**
	 * Invoice total paid column
	 * */
	private final String COLUMN_BILL_INVOICE_TOTAL_PAID = "total_paid";

	/**
	 * Invoice total due column
	 * */
	private final String COLUMN_BILL_INVOICE_TOTAL_DUE = "total_due";

	/**
	 * Invoice payment date column
	 * */
	private final String COLUMN_BILL_INVOICE_PMT_DATE = "payment_date";

	/**
	 * Invoice payment due date column
	 * */
	private final String COLUMN_BILL_INVOICE_DUE_DATE = "payment_due_date";

	// Issue table columns
	/**
	 * Issue Id column
	 * */
	private final String COLUMN_ISSUE_ID = "issue_id";

	/**
	 * Issue-Apartment column
	 * */
	private final String COLUMN_ISSUE_APT_ID = "apartment_id";

	/**
	 * Issue date created column
	 * */
	private final String COLUMN_ISSUE_DATE_CREATED = "date_created";

	/**
	 * Issue date modified column
	 * */
	private final String COLUMN_ISSUE_DATE_MODIFIED = "date_modified";

	/**
	 * Issue description column
	 * */
	private final String COLUMN_ISSUE_DESCRIPTION = "issue_desc";

	/**
	 * Issue date column
	 * */
	private final String COLUMN_ISSUE_DATE = "issue_date";

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Query constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Query Strings
	/**
	 * Apartment query constant
	 * */
	private final String QUERY_APARTMENTS = "SELECT * FROM " + TABLE_APARTMENTS;

	/**
	 * Tenant query constant
	 * */
	private final String QUERY_TENANTS = "SELECT * FROM " + TABLE_TENANTS;

	/**
	 * Spouse(Tenant) query constant
	 * */
	private final String QUERY_TNANT_SPOUSES = "SELECT * FROM " + SUB_TABLE_TNANT_SPOUSES;

	/**
	 * Invoice(Tenant) query constant
	 * */
	private final String QUERY_TNANT_INVOICES = "SELECT * FROM " + SUB_TABLE_TNANT_INVOICES;

	/**
	 * Inspection query constant
	 * */
	private final String QUERY_INSPECTIONS = "SELECT * FROM " + SUB_TABLE_INSPECTIONS;

	/**
	 * Candidate query constant
	 * */
	private final String QUERY_CANDIDATES = "SELECT * FROM " + TABLE_CANDIDATES;

	/**
	 * Spouse(Candidate) query constant
	 * */
	private final String QUERY_CAND_SPOUSES = "SELECT * FROM " + SUB_TABLE_CAND_SPOUSES;

	/**
	 * Contractor query constant
	 * */
	private final String QUERY_CONTRACTORS = "SELECT * FROM " + TABLE_CONTRACTORS;

	/**
	 * Invoice(Contractor) query constant
	 * */
	private final String QUERY_CONT_INVOICES = "SELECT * FROM " + SUB_TABLE_CONT_INVOICES;

	/**
	 * Insurance query constant
	 * */
	private final String QUERY_INSURANCES = "SELECT * FROM " + SUB_TABLE_INSURANCES;

	/**
	 * Invoice(Insurance) query constant
	 * */
	private final String QUERY_INS_INVOICES = "SELECT * FROM " + SUB_TABLE_INS_INVOICES;

	/**
	 * Bill query constant
	 * */
	private final String QUERY_BILLS = "SELECT * FROM " + SUB_TABLE_BILLS;

	/**
	 * Invoice(Bill) query constant
	 * */
	private final String QUERY_BILL_INVOICES = "SELECT * FROM " + SUB_TABLE_BILL_INVOICES;

	/**
	 * Issue query constant
	 * */
	private final String QUERY_ISSUES = "SELECT * FROM " + SUB_TABLE_ISSUES;

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

	/**
	 * Insertion values for all Spouse tables
	 * */
	private final String SPOUSE_VALUES = "(?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * Insertion values for all Invoice tables
	 * */
	private final String INVOICE_VALUES = "(?,?,?,?,?,?,?,?,?,?,?)";

	/**
	 * Insertion values for all Note/Log values
	 * */
	private final String NOTE_LOG_VALUES = "(?,?,?,?,?,?)";

	// Insert statements
	/**
	 * Apartment insertion constant
	 * */
	private final String INSERT_INTO_APARTMENTS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?)", TABLE_APARTMENTS);

	/**
	 * Tenant insertion constant
	 * */
	private final String INSERT_INTO_TENANTS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_TENANTS);

	/**
	 * Spouse(Tenant) insertion constant
	 * */
	private final String INSERT_INTO_TNANT_SPOUSES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_TNANT_SPOUSES, SPOUSE_VALUES);

	/**
	 * Invoice(Tenant) insertion constant
	 * */
	private final String INSERT_INTO_TNANT_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_TNANT_INVOICES, INVOICE_VALUES);

	/**
	 * Inspection insertion constant
	 * */
	private final String INSERT_INTO_INSPECTIONS =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_INSPECTIONS, NOTE_LOG_VALUES);

	/**
	 * Candidate insertion constant
	 * */
	private final String INSERT_INTO_CANDIDATES =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_CANDIDATES);

	/**
	 * Spouse(Candidate) insertion constant
	 * */
	private final String INSERT_INTO_CAND_SPOUSES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_CAND_SPOUSES, SPOUSE_VALUES);

	/**
	 * Contractor insertion constant
	 * */
	private final String INSERT_INTO_CONTRACTORS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", TABLE_CONTRACTORS);

	/**
	 * Invoice(Contractor) insertion constant
	 * */
	private final String INSERT_INTO_CONT_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_CONT_INVOICES, INVOICE_VALUES);

	/**
	 * Insurance insertion constant
	 * */
	private final String INSERT_INTO_INSURANCES =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", SUB_TABLE_INSURANCES);

	/**
	 * Invoice(Insurance) insertion constant
	 * */
	private final String INSERT_INTO_INS_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_INS_INVOICES, INVOICE_VALUES);

	/**
	 * Bill insertion constant
	 * */
	private final String INSERT_INTO_BILLS =
			String.format("INSERT INTO %s VALUES (?,?,?,?,?,?,?,?)", SUB_TABLE_BILLS);

	/**
	 * Invoice(Bill) insertion constant
	 * */
	private final String INSERT_INTO_BILL_INVOICES =
			String.format("INSERT INTO %s VALUES %s", SUB_TABLE_BILL_INVOICES, INVOICE_VALUES);

	/**
	 * Issue insertion constant
	 * */
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
	/**
	 * Apartment update constant
	 * */
	private final String UPDATE_APARTMENTS =
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
	/**
	 * Tenant update constant
	 * */
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

	/**
	 * Spouse(Tenant) update constant
	 * */
	private final String UPDATE_TNANT_SPOUSES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//first name
					"SET %s = ? " +		//last name
					"SET %s = ? " +		//phone
					"SET %s = ? " +		//email
					"SET %s = ? " +		//idn
					"SET %s = ? " +		//dob
					"SET %s = ? " +		//annual income
					"WHERE %s = ?",
					SUB_TABLE_TNANT_SPOUSES,
					COLUMN_SPOUSE_DATE_MODIFIED, COLUMN_SPOUSE_FNAME, COLUMN_SPOUSE_LNAME, COLUMN_SPOUSE_PHONE,
					COLUMN_SPOUSE_EMAIL, COLUMN_SPOUSE_IDN, COLUMN_SPOUSE_DOB, COLUMN_SPOUSE_ANNUAL_INCOME,
					COLUMN_SPOUSE_ID
			);

	/**
	 * Invoice(Tenant) update constant
	 * */
	private final String UPDATE_TNANT_INVOICES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//payment amount
					"SET %s = ? " +		//dues
					"SET %s = ? " +		//balance
					"SET %s = ? " +		//total paid
					"SET %s = ? " +		//total due
					"SET %s = ? " +		//payment date
					"SET %s = ? " +		//due date
					"WHERE %s = ?",
					SUB_TABLE_TNANT_INVOICES,
					COLUMN_TNANT_INVOICE_DATE_MODIFIED, COLUMN_TNANT_INVOICE_PMT_AMOUNT, COLUMN_TNANT_INVOICE_DUES,
					COLUMN_TNANT_INVOICE_BALANCE, COLUMN_TNANT_INVOICE_TOTAL_PAID, COLUMN_TNANT_INVOICE_TOTAL_DUE,
					COLUMN_TNANT_INVOICE_PAYMENT_DATE, COLUMN_TNANT_INVOICE_PMT_DUE_DATE,
					COLUMN_TNANT_INVOICE_ID
			);

	/**
	 * Inspection update constant
	 * */
	private final String UPDATE_INSPECTIONS =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//description
					"SET %s = ? " +		//inspection date
					"WHERE %s = ?",
					SUB_TABLE_INSPECTIONS,
					COLUMN_INSPECTION_DATE_MODIFIED, COLUMN_INSPECTION_DESCRIPTION, COLUMN_INSPECTION_DATE,
					COLUMN_INSPECTION_ID);

	/**
	 * Candidate update constant
	 * */
	private final String UPDATE_CANDIDATES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//first name
					"SET %s = ? " +		//last name
					"SET %s = ? " +		//phone number
					"SET %s = ? " +		//email
					"SET %s = ? " +		//dob
					"SET %s = ? " +		//annual income
					"SET %s = ? " +		//ssn
					"SET %s = ? " +		//number of children
					"SET %s = ? " +		//accepted
					"WHERE %s = ?",
					TABLE_CANDIDATES,
					COLUMN_CAND_DATE_MODIFIED, COLUMN_CAND_FNAME, COLUMN_CAND_LNAME, COLUMN_CAND_PHONE,
					COLUMN_CAND_EMAIL, COLUMN_CAND_DOB,	COLUMN_CAND_ANNUAL_INCOME, COLUMN_CAND_IDN,
					COLUMN_CAND_NUM_CHILDREN, COLUMN_CAND_ACCEPTED,
					COLUMN_CAND_ID);

	/**
	 * Spouse(Candidate) update constant
	 * */
	private final String UPDATE_CAND_SPOUSES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//first name
					"SET %s = ? " +		//last name
					"SET %s = ? " +		//phone
					"SET %s = ? " +		//email
					"SET %s = ? " +		//idn
					"SET %s = ? " +		//dob
					"SET %s = ? " +		//annual income
					"WHERE %s = ?",
					SUB_TABLE_CAND_SPOUSES,
					COLUMN_SPOUSE_DATE_MODIFIED, COLUMN_SPOUSE_FNAME, COLUMN_SPOUSE_LNAME, COLUMN_SPOUSE_PHONE,
					COLUMN_SPOUSE_EMAIL, COLUMN_SPOUSE_IDN, COLUMN_SPOUSE_DOB, COLUMN_SPOUSE_ANNUAL_INCOME,
					COLUMN_SPOUSE_ID
			);

	/**
	 * Contractor update constant
	 * */
	private final String UPDATE_CONTRACTORS =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//company name
					"SET %s = ? " +		//bill
					"SET %s = ? " +		//phone number
					"SET %s = ? " +		//email
					"WHERE %s = ?",
					TABLE_CONTRACTORS,
					COLUMN_CONT_DATE_MODIFIED, COLUMN_CONT_NAME, COLUMN_CONT_BILL, COLUMN_CONT_PHONE, COLUMN_CONT_EMAIL,
					COLUMN_CONT_ID
			);

	/**
	 * Invoice(Contractor) update constant
	 * */
	private final String UPDATE_CONT_INVOICES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//payment amount
					"SET %s = ? " +		//dues
					"SET %s = ? " +		//balance
					"SET %s = ? " +		//total paid
					"SET %s = ? " +		//total due
					"SET %s = ? " +		//payment date
					"SET %s = ? " +		//due date
					"WHERE %s = ?",
					SUB_TABLE_CONT_INVOICES,
					COLUMN_CONT_INVOICE_DATE_MODIFIED, COLUMN_CONT_INVOICE_PMT_AMOUNT, COLUMN_CONT_INVOICE_DUES,
					COLUMN_CONT_INVOICE_BALANCE, COLUMN_CONT_INVOICE_TOTAL_PAID, COLUMN_CONT_INVOICE_TOTAL_DUE,
					COLUMN_CONT_INVOICE_PAYMENT_DATE, COLUMN_CONT_INVOICE_PMT_DUE_DATE,
					COLUMN_CONT_INVOICE_ID
			);

	/**
	 * Insurance update constant
	 * */
	private final String UPDATE_INSURANCES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//name
					"SET %s = ? " +		//bill
					"SET %s = ? " +		//phone
					"SET %s = ? " +		//email
					"WHERE %s = ?",
					SUB_TABLE_INSURANCES,
					COLUMN_INSURANCE_DATE_MODIFIED, COLUMN_INSURANCE_NAME, COLUMN_INSURANCE_BILL,
					COLUMN_INSURANCE_PHONE, COLUMN_INSURANCE_EMAIL,
					COLUMN_INSURANCE_ID
			);

	/**
	 * Invoice(Insurance) update constant
	 * */
	private final String UPDATE_INS_INVOICES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//payment amount
					"SET %s = ? " +		//dues
					"SET %s = ? " +		//balance
					"SET %s = ? " +		//total paid
					"SET %s = ? " +		//total due
					"SET %s = ? " +		//payment date
					"SET %s = ? " +		//due date
					"WHERE %s = ?",
					SUB_TABLE_INS_INVOICES,
					COLUMN_INS_INVOICE_DATE_MODIFIED, COLUMN_INS_INVOICE_PAYMENT, COLUMN_INS_INVOICE_DUES,
					COLUMN_INS_INVOICE_BALANCE, COLUMN_INS_INVOICE_TOTAL_PAID, COLUMN_INS_INVOICE_TOTAL_DUE,
					COLUMN_INS_INVOICE_PMT_DATE, COLUMN_INS_INVOICE_DUE_DATE,
					COLUMN_INS_INVOICE_ID
			);

	/**
	 * Bill update constant
	 * */
	private final String UPDATE_BILLS =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//name
					"SET %s = ? " +		//address
					"SET %s = ? " +		//phone
					"SET %s = ? " +		//bill
					"WHERE %s = ?",
					SUB_TABLE_BILLS,
					COLUMN_BILL_DATE_MODIFIED, COLUMN_BILL_NAME, COLUMN_BILL_ADDRESS, COLUMN_BILL_PHONE,
					COLUMN_BILL_BILL,
					COLUMN_BILL_ID
			);

	/**
	 * Invoice(Bill) update constant
	 * */
	private final String UPDATE_BILL_INVOICES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//payment amount
					"SET %s = ? " +		//dues
					"SET %s = ? " +		//balance
					"SET %s = ? " +		//total paid
					"SET %s = ? " +		//total due
					"SET %s = ? " +		//payment date
					"SET %s = ? " +		//due date
					"WHERE %s = ?",
					SUB_TABLE_BILL_INVOICES,
					COLUMN_BILL_INVOICE_DATE_MODIFIED, COLUMN_BILL_INVOICE_PAYMENT, COLUMN_BILL_INVOICE_DUES,
					COLUMN_BILL_INVOICE_BALANCE, COLUMN_BILL_INVOICE_TOTAL_PAID, COLUMN_BILL_INVOICE_TOTAL_DUE,
					COLUMN_BILL_INVOICE_PMT_DATE, COLUMN_BILL_INVOICE_DUE_DATE,
					COLUMN_BILL_INVOICE_ID
			);

	/**
	 * Issue update constant
	 * */
	private final String UPDATE_ISSUES =
			String.format("UPDATE %s " +
					"SET %s = ? " +		//date modified
					"SET %s = ? " +		//description
					"SET %s = ? " +		//date
					"WHERE %s = ?",
					SUB_TABLE_ISSUES,
					COLUMN_ISSUE_DATE_MODIFIED, COLUMN_ISSUE_DESCRIPTION, COLUMN_ISSUE_DATE,
					COLUMN_ISSUE_ID);

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Delete Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Delete methods
	/**
	 * Apartment delete constant
	 * */
	private final String DELETE_FROM_APARTMENTS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_APARTMENTS, COLUMN_APT_ID);

	/**
	 * Tenant delete constant
	 * */
	private final String DELETE_FROM_TENANTS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_TENANTS, COLUMN_TNANT_ID);

	/**
	 * Spouse(Tenant) delete constant
	 * */
	private final String DELETE_FROM_TNANT_SPOUSES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_TNANT_SPOUSES, COLUMN_SPOUSE_ID);

	/**
	 * Invoice(Tenant) delete constant
	 * */
	private final String DELETE_FROM_TNANT_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_TNANT_INVOICES, COLUMN_TNANT_INVOICE_ID);

	/**
	 * Inspection delete constant
	 * */
	private final String DELETE_FROM_INSPECTIONS =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_INSPECTIONS, COLUMN_INSPECTION_ID);

	/**
	 * Candidate delete constant
	 * */
	private final String DELETE_FROM_CANDIDATES =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_CANDIDATES, COLUMN_CAND_ID);

	/**
	 * Spouse(Candidate) delete constant
	 * */
	private final String DELETE_FROM_CAND_SPOUSES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_CAND_SPOUSES, COLUMN_SPOUSE_ID);

	/**
	 * Contractor delete constant
	 * */
	private final String DELETE_FROM_CONTRACTORS =
			String.format("DELETE FROM %s WHERE %s = ?", TABLE_CONTRACTORS, COLUMN_CONT_ID);

	/**
	 * Invoice(Contractor) delete constant
	 * */
	private final String DELETE_FROM_CONT_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_CONT_INVOICES, COLUMN_CONT_INVOICE_ID);

	/**
	 * Insurance delete constant
	 * */
	private final String DELETE_FROM_INSURANCES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_INSURANCES, COLUMN_INSURANCE_ID);

	/**
	 * Invoice(Insurance) delete constant
	 * */
	private final String DELETE_FROM_INS_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?",SUB_TABLE_INS_INVOICES,COLUMN_INS_INVOICE_ID);

	/**
	 * Bill delete constant
	 * */
	private final String DELETE_FROM_BILLS =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_BILLS,COLUMN_BILL_ID);

	/**
	 * Invoice(Bill) delete constant
	 * */
	private final String DELETE_FROM_BILL_INVOICES =
			String.format("DELETE FROM %s WHERE %s = ?", SUB_TABLE_BILL_INVOICES, COLUMN_BILL_INVOICE_ID);

	/**
	 * Issue delete constant
	 * */
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
	/**
	 * Apartment insert prepared statement
	 * */
	private PreparedStatement insertApartment;

	/**
	 * Tenant insert prepared statement
	 * */
	private PreparedStatement insertTenant;

	/**
	 * Spouse(Tenant) insert prepared statement
	 * */
	private PreparedStatement insertTnantSpouse;

	/**
	 * Invoice(Tenant) insert prepared statement
	 * */
	private PreparedStatement insertTnantAccount;

	/**
	 * Inspection insert prepared statement
	 * */
	private PreparedStatement insertInspection;

	/**
	 * Candidate insert prepared statement
	 * */
	private PreparedStatement insertCandidate;

	/**
	 * Spouse(Candidate) insert prepared statement
	 * */
	private PreparedStatement insertCandSpouse;

	/**
	 * Contractor insert prepared statement
	 * */
	private PreparedStatement insertContractor;

	/**
	 * Invoice(Contractor) insert prepared statement
	 * */
	private PreparedStatement insertContAccount;

	/**
	 * Insurance insert prepared statement
	 * */
	private PreparedStatement insertInsurance;

	/**
	 * Invoice(Insurance) insert prepared statement
	 * */
	private PreparedStatement insertInsInvoice;

	/**
	 * Bill insert prepared statement
	 * */
	private PreparedStatement insertBill;

	/**
	 * Invoice(Bill) insert prepared statement
	 * */
	private PreparedStatement insertBillAccount;

	/**
	 * Issue insert prepared statement
	 * */
	private PreparedStatement insertIssue;



	//Update Prepared Statements
	/***/
	private PreparedStatement updateApartment;

	/***/
	private PreparedStatement updateTenant;

	/***/
	private PreparedStatement updateTnantSpouse;

	/***/
	private PreparedStatement updateTnantInvoice;

	/***/
	private PreparedStatement updateCandidate;

	/***/
	private PreparedStatement updateCandSpouse;

	/***/
	private PreparedStatement updateContractor;

	/***/
	private	PreparedStatement updateContInvoice;

	/***/
	private	PreparedStatement updateInsurance;

	/***/
	private	PreparedStatement updateInsInvoice;

	/***/
	private PreparedStatement updateBill;

	/***/
	private PreparedStatement updateBillInvoice;

	/***/
	private PreparedStatement updateIssue;

	/***/
	private PreparedStatement updateInspection;






	// Delete Prepared statements
	/**
	 * Apartment delete prepared statement
	 * */
	private PreparedStatement deleteApartment;

	/**
	 * Tenant delete prepared statement
	 * */
	private PreparedStatement deleteTenant;

	/**
	 * Spouse(Tenant) delete prepared statement
	 * */
	private PreparedStatement deleteTnantSpouse;

	/**
	 * Invoice(Tenant) delete prepared statement
	 * */
	private PreparedStatement deleteTnantInvoice;

	/**
	 * Inspection delete prepared statement
	 * */
	private PreparedStatement deleteInspection;

	/**
	 * Candidate delete prepared statement
	 * */
	private PreparedStatement deleteCandidate;

	/**
	 * Spouse(Candidate) delete prepared statement
	 * */
	private PreparedStatement deleteCandSpouse;

	/**
	 * Contractor delete prepared statement
	 * */
	private PreparedStatement deleteContractor;

	/**
	 * Invoice(Contractor) delete prepared statement
	 * */
	private PreparedStatement deleteContInvoice;

	/**
	 * Insurance delete prepared statement
	 * */
	private PreparedStatement deleteInsurance;

	/**
	 * Invoice(Insurance) delete prepared statement
	 * */
	private PreparedStatement deleteInsInvoice;

	/**
	 * Bill delete prepared statement
	 * */
	private PreparedStatement deleteBill;

	/**
	 * Invoice(Bill) delete prepared statement
	 * */
	private PreparedStatement deleteBillInvoice;

	/**
	 * Issue delete prepared statement
	 * */
	private PreparedStatement deleteIssue;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// SQLBridge Constructors
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Constructor
	/**
	 * SQLBridge constructor:
	 * <p>
	 * Establishes url and default credentials before instantiating an instance of SQLBridge
	 * */
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
	/**
	 * Attempts (default) connection to database
	 * @return <code>true</code> if connection is successful <code>false</code> if not
	 * */
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
	/**
	 * Attempts connection to database with user credentials
	 * @return <code>true</code> if connection is successful <code>false</code> if not
	 * */
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
	// Might not be needed; save is already in database
	/**
	 * Saves Database items to server
	 * */
	public boolean save() {
		//Database.getInstance();

		// add/delete new/removed apartments

		// add/delete new/removed tenants

		// add/delete new/removed candidates

		// add/delete new/removed contractors
		return true;
	}

	/**
	 * Closes all resources for SQL connection
	 * */
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
	/**
	 * Query's and returns the list of queried Apartments and related tables (Insurances, Bills, associated Invoices,
	 * and Issues) from the server
	 * @return Queried Apartment list
	 * */
	public List<Building> queryBuildings() {
		List<Building> queriedBuildings = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_APARTMENTS);

			while (rs.next()) {
				Building app = new Building();

				// Set fields
				app.setId(rs.getInt(COLUMN_APT_ID));
				app.setDateCreated(rs.getTimestamp(COLUMN_APT_DATE_CREATED).toLocalDateTime());
				app.setDateModified(rs.getTimestamp(COLUMN_APT_DATE_MODIFIED).toLocalDateTime());
				app.setAddress(rs.getString(COLUMN_APT_ADDRESS));
				app.setCity(rs.getString(COLUMN_APT_CITY));
				app.setState(rs.getString(COLUMN_APT_STATE));
				app.setZipCode(rs.getString(COLUMN_APT_ZIP));


				queriedBuildings.add(app);
			}

			// Set foreign tables:
			// Bills
			rs = statement.executeQuery(QUERY_BILLS);

			while (rs.next()) {
				Bill bill = new Bill();

				bill.setId(rs.getInt(COLUMN_BILL_ID));
				bill.setFk(rs.getInt(COLUMN_BILL_APT_ID));
				bill.setDateCreated(rs.getTimestamp(COLUMN_BILL_DATE_CREATED).toLocalDateTime());
				bill.setDateModified(rs.getTimestamp(COLUMN_BILL_DATE_MODIFIED).toLocalDateTime());
				bill.setCompanyName(rs.getString(COLUMN_BILL_NAME));
				bill.setAddress(rs.getString(COLUMN_BILL_ADDRESS));
				bill.setPhone(rs.getString(COLUMN_BILL_PHONE));
				//bill.setBill(rs.getDouble(COLUMN_BILL_BILL));

				queriedBuildings.forEach(apt -> {
					if (bill.getFk() == apt.getId()) {
						apt.getBills().add(bill);
					}
				});
			}

			//Rooms

			// Inspection
			rs = statement.executeQuery(QUERY_INSPECTIONS);

			while (rs.next()) {
				NoteLog inspection = new NoteLog(false);

				inspection.setId(rs.getInt(COLUMN_INSPECTION_ID));
				inspection.setFk(rs.getInt(COLUMN_INSPECTION_TNANT_ID));
				inspection.setDateCreated(rs.getTimestamp(COLUMN_INSPECTION_DATE_CREATED).toLocalDateTime());
				inspection.setDateModified(rs.getTimestamp(COLUMN_INSPECTION_DATE_MODIFIED).toLocalDateTime());
				inspection.setLog(rs.getString(COLUMN_INSPECTION_DESCRIPTION));
				inspection.setLogDate(rs.getDate(COLUMN_INSPECTION_DATE).toLocalDate());

				queriedBuildings.forEach(apt -> apt.getLivingSpaces().forEach(livingSpace -> {
					if (inspection.getFk() == livingSpace.getId()) {
						livingSpace.getInspections().add(inspection);
					}
				}));
			}

			// Bill Invoices
			//TODO: refactor to bill account
			rs = statement.executeQuery(QUERY_BILL_INVOICES);

			while (rs.next()) {
				Account billAccount = new Account();
			}

			// Issues
			rs = statement.executeQuery(QUERY_ISSUES);

			while (rs.next()) {
				NoteLog issue = new NoteLog(true);

				issue.setId(rs.getInt(COLUMN_ISSUE_ID));
				issue.setFk(rs.getInt(COLUMN_ISSUE_APT_ID));
				issue.setDateCreated(rs.getTimestamp(COLUMN_ISSUE_DATE_CREATED).toLocalDateTime());
				issue.setDateModified(rs.getTimestamp(COLUMN_ISSUE_DATE_MODIFIED).toLocalDateTime());
				issue.setLog(rs.getString(COLUMN_ISSUE_DESCRIPTION));
				issue.setLogDate(rs.getDate(COLUMN_ISSUE_DATE).toLocalDate());

				queriedBuildings.forEach(apt -> {
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

		return queriedBuildings;
	}

	/**
	 * Query's and returns the list of queried Tenants and related tables (Spouses, associated Invoices,
	 * and Inspections) from the server
	 * @return Queried Tenant list
	 * */
	public List<Tenant> queryTenants() {
		List<Tenant> queriedTenants = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_TENANTS);

			// Set fields
			while (rs.next()) {
				Tenant tenant = new Tenant();

				tenant.setId(rs.getInt(COLUMN_TNANT_ID));
				tenant.setFk(rs.getInt(COLUMN_TNANT_APT));
				tenant.setDateCreated(rs.getTimestamp(COLUMN_TNANT_DATE_CREATED).toLocalDateTime());
				tenant.setDateModified(rs.getTimestamp(COLUMN_TNANT_DATE_MODIFIED).toLocalDateTime());
				tenant.setFirstName(rs.getString(COLUMN_TNANT_FNAME));
				tenant.setLastName(rs.getString(COLUMN_TNANT_LNAME));
				tenant.setPhone(rs.getString(COLUMN_TNANT_PHONE));
				tenant.setEmail(rs.getString(COLUMN_TNANT_EMAIL));
				tenant.setSsn(rs.getString(COLUMN_TNANT_IDN));
				tenant.setNumChildren(rs.getInt(COLUMN_TNANT_NUM_CHILDREN));
				tenant.setMovInDate(rs.getDate(COLUMN_TNANT_MOVE_IN_DATE).toLocalDate());
				tenant.setDateOfBirth(rs.getDate(COLUMN_TNANT_DOB).toLocalDate());
				tenant.setAnnualIncome(rs.getInt(COLUMN_TNANT_ANNUAL_INCOME));
				tenant.setEvicted(rs.getBoolean(COLUMN_TNANT_SLATED_FOR_EVICTION));
				tenant.setEvictReason(rs.getString(COLUMN_TNANT_EVICT_REASON));
				tenant.setMovOutDate(rs.getDate(COLUMN_TNANT_MOV_OUT_DATE).toLocalDate());

				queriedTenants.add(tenant);
			}
			// Set foreign tables
			// Spouse
			rs = statement.executeQuery(QUERY_TNANT_SPOUSES);

			while (rs.next()) {
				RoomMate tnantRoomMate = new RoomMate();

				tnantRoomMate.setId(rs.getInt(COLUMN_SPOUSE_ID));
				tnantRoomMate.setFk(rs.getInt(COLUMN_SPOUSE_TNANT_ID));
				tnantRoomMate.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED).toLocalDateTime());
				tnantRoomMate.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED).toLocalDateTime());
				tnantRoomMate.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				tnantRoomMate.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				tnantRoomMate.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				tnantRoomMate.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				tnantRoomMate.setSsn(rs.getString(COLUMN_SPOUSE_IDN));
				tnantRoomMate.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB).toLocalDate());
				tnantRoomMate.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				queriedTenants.forEach(tnant -> {
					if (tnantRoomMate.getFk() == tnant.getId()) {
						tnant.addRoomMate(tnantRoomMate);
					}
				});
			}

			// Invoices
			rs = statement.executeQuery(QUERY_TNANT_INVOICES);
			while (rs.next()) {
				Account tnantAccount = new Account();

			}



			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying tenants");
			return null;
		}

		return queriedTenants;
	}

	/**
	 * Query's and returns the list of queried Candidates and related Spouses from the server
	 * @return Queried Candidate list
	 * */
	public List<Candidate> queryCandidates() {
		List<Candidate> queriedCandidates = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_CANDIDATES);

			// Set Fields
			while (rs.next()) {
				Candidate candidate = new Candidate();

				candidate.setId(rs.getInt(COLUMN_CAND_ID));
				candidate.setFk(rs.getInt(COLUMN_CAND_APT));
				candidate.setDateCreated(rs.getTimestamp(COLUMN_CAND_DATE_CREATED).toLocalDateTime());
				candidate.setDateModified(rs.getTimestamp(COLUMN_CAND_DATE_MODIFIED).toLocalDateTime());
				candidate.setFirstName(rs.getString(COLUMN_CAND_FNAME));
				candidate.setLastName(COLUMN_CAND_LNAME);
				candidate.setPhone(rs.getString(COLUMN_CAND_PHONE));
				candidate.setEmail(rs.getString(COLUMN_CAND_EMAIL));
				candidate.setDateOfBirth(rs.getDate(COLUMN_CAND_DOB).toLocalDate());
				candidate.setSsn(rs.getString(COLUMN_CAND_IDN));
				candidate.setNumChildren(rs.getInt(COLUMN_CAND_NUM_CHILDREN));
				candidate.setAnnualIncome(rs.getInt(COLUMN_CAND_ANNUAL_INCOME));

				queriedCandidates.add(candidate);
			}
			// Set foreign tables
			rs = statement.executeQuery(QUERY_CAND_SPOUSES);

			// Should only run once
			while (rs.next()) {
				RoomMate candRoomMate = new RoomMate();

				candRoomMate.setId(rs.getInt(COLUMN_SPOUSE_ID));
				candRoomMate.setFk2(rs.getInt(COLUMN_SPOUSE_CAND_ID));
				candRoomMate.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED).toLocalDateTime());
				candRoomMate.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED).toLocalDateTime());
				candRoomMate.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				candRoomMate.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				candRoomMate.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				candRoomMate.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				candRoomMate.setSsn(rs.getString(COLUMN_SPOUSE_IDN));
				candRoomMate.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB).toLocalDate());
				candRoomMate.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				queriedCandidates.forEach(cand -> {
					if (candRoomMate.getFk2() == cand.getId()) {
						cand.addRoomMate(candRoomMate);
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

	/**
	 * Query's and returns the list of queried Contractors and associated Invoices from the server
	 * @return Queried Contractor list
	 * */
	public List<Contractor> queryContractors() {
		List<Contractor> queriedContractors = new ArrayList<>();

		try {
			rs = statement.executeQuery(QUERY_CONTRACTORS);

			while (rs.next()) {
				Contractor contractor = new Contractor();

				contractor.setId(rs.getInt(COLUMN_CONT_ID));
				contractor.setFk(rs.getInt(COLUMN_CONT_APT));
				contractor.setDateCreated(rs.getTimestamp(COLUMN_CONT_DATE_CREATED).toLocalDateTime());
				contractor.setDateModified(rs.getTimestamp(COLUMN_CONT_DATE_MODIFIED).toLocalDateTime());
				contractor.setName(rs.getString(COLUMN_CONT_NAME));
				//contractor.setBill(rs.getDouble(COLUMN_CONT_BILL));
				contractor.setPhone(rs.getString(COLUMN_CONT_PHONE));
				contractor.setEmail(rs.getString(COLUMN_CONT_EMAIL));

				queriedContractors.add(contractor);
			}
			// Set foreign tables
			// Invoices
			rs = statement.executeQuery(QUERY_CONT_INVOICES);
			while (rs.next()) {
				Account contInvoice = new Account();
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
	public void insert(Table table) {
		switch (table.getTableType()){
			case BUILDINGS:
				insert((Building) table);
				break;
			case TENANTS:
				insert((Tenant) table);
				break;
			case CANDIDATES:
				insert((Candidate) table);
				break;
			case CONTRACTORS:
				insert((Contractor) table);
				break;
			case BILLS:
				insert((Bill) table);
				break;
			case INSPECTIONS:
			case ISSUES:
				insert((NoteLog) table, table.getTableType());
				break;
			default:
				System.out.println("Table does not have the functionality to be inserted into database");
		}
	}

	/**
	 * Inserts a new Apartment and related tables (Insurances, Bills, associated Invoices, and Issues) into the server
	 * @param building Apartment to insert
	 * */
	public void insert(Building building) {
		try {
			// Save Apartment
			if (Main.DEBUG)
				System.out.println("Saving Apartment");
			
			insertApartment.setInt(1, building.getId());
			insertApartment.setTimestamp(2, Timestamp.valueOf(building.getDateCreated()));
			insertApartment.setTimestamp(3, Timestamp.valueOf(building.getDateModified()));
			insertApartment.setString(4, building.getAddress());
			insertApartment.setString(5, building.getCity());
			insertApartment.setString(6, building.getState());
			insertApartment.setString(7, building.getZipCode());
			insertApartment.setInt(8, building.getCapacity());
			insertApartment.setInt(9, building.getNumTenants());

			insertApartment.execute();
			insertApartment.clearParameters();

			// Inspection
			for (LivingSpace livingSpace : building.getLivingSpaces()) {
				for (NoteLog inspection : livingSpace.getInspections()) {
					insert(inspection, building.getTableType());
				}
			}

			// Save sub-tables
			//Bill
			for (Bill bill : building.getBills()) {
				insert(bill);
			}

			// Issue
			for (NoteLog issue : building.getIssues()) {
				insert(issue, DBTables.ISSUES);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	/**
	 * Inserts a new Tenant and related tables (associated Invoices, Spouses, and Inspections) into the server
	 * @param tenant Tenant to insert
	 * */
	public void insert(Tenant tenant) {
		try {
			// Save Tenant
			if (Main.DEBUG)
				System.out.println("Saving Tenant");
			
			insertTenant.setInt(1, tenant.getId());
			insertTenant.setInt(2, tenant.getFk());
			insertTenant.setTimestamp(3, Timestamp.valueOf(tenant.getDateCreated()));
			insertTenant.setTimestamp(4, Timestamp.valueOf(tenant.getDateModified()));
			insertTenant.setString(5, tenant.getFirstName());
			insertTenant.setString(6, tenant.getLastName());
			insertTenant.setString(7, tenant.getPhone());
			insertTenant.setString(8, tenant.getEmail());
			insertTenant.setString(9, tenant.getSsn());
			insertTenant.setInt(11, tenant.getNumChildren());
			insertTenant.setDate(12, Date.valueOf(tenant.getMovInDate()));
			insertTenant.setDate(13, Date.valueOf(tenant.getDateOfBirth()));
			insertTenant.setInt(14, tenant.getAnnualIncome());
			insertTenant.setBoolean(15, tenant.isEvicted());
			insertTenant.setString(16, tenant.getEvictReason());
			insertTenant.setDate(17, Date.valueOf(tenant.getMovOutDate()));

			insertTenant.execute();
			insertTenant.clearParameters();

			// Spouse
			if (!tenant.getRoomMates().isEmpty()) {
				tenant.getRoomMates().forEach(roomMate -> insert(roomMate, tenant.getTableType()));
			}

			// Tnant_Account
			insert(tenant.getAccount(), tenant.getTableType());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts a new Candidate and related Spouses (if any) into the server
	 * @param candidate Candidate to insert
	 * */
	public void insert(Candidate candidate) {
		try {
			// Save Candidate
			if (Main.DEBUG)
				System.out.println("Saving Candidate");
			
			insertCandidate.setInt(1, candidate.getId());
			insertCandidate.setInt(2, candidate.getFk());
			insertCandidate.setTimestamp(3, Timestamp.valueOf(candidate.getDateCreated()));
			insertCandidate.setTimestamp(4, Timestamp.valueOf(candidate.getDateModified()));
			insertCandidate.setString(5, candidate.getFirstName());
			insertCandidate.setString(6, candidate.getLastName());
			insertCandidate.setString(7, candidate.getPhone());
			insertCandidate.setString(8, candidate.getEmail());
			insertCandidate.setDate(9, Date.valueOf(candidate.getDateOfBirth()));
			insertCandidate.setInt(10, candidate.getAnnualIncome());
			insertCandidate.setString(11, candidate.getSsn());
			insertCandidate.setInt(12, candidate.getNumChildren());
			insertCandidate.setBoolean(13, candidate.isAccepted());

			insertCandidate.execute();
			insertCandidate.clearParameters();

			// Roommate
			if (!candidate.getRoomMates().isEmpty()) {
				candidate.getRoomMates().forEach(roomMate -> insert(roomMate, candidate.getTableType()));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts a new Contractor and associated Invoices into the server
	 * @param contractor Contractor to insert
	 * */
	public void insert(Contractor contractor) {
		try {
			// Save Contractor
			if (Main.DEBUG)
				System.out.println("Saving Contractor");
			
			insertContractor.setInt(1, contractor.getId());
			insertContractor.setInt(2, contractor.getFk());
			insertContractor.setTimestamp(3, Timestamp.valueOf(contractor.getDateCreated()));
			insertContractor.setTimestamp(4, Timestamp.valueOf(contractor.getDateModified()));
			insertContractor.setString(5, contractor.getName());
			//insertContractor.setDouble(6, contractor.getBill());
			insertContractor.setString(7, contractor.getPhone());
			insertContractor.setString(8, contractor.getEmail());

			// Invoice
			insert(contractor.getAccount(), contractor.getTableType());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts a new Issue/Inspection into the server
	 * @param issInsp Issue/Inspection to insert
	 * @param table Specifies table to insert into
	 * */
	public void insert(NoteLog issInsp, DBTables table) {
		try {
			switch (table) {
				case INSPECTIONS:
					if (Main.DEBUG)
						System.out.println("Saving Inspection");
					
					insertInspection.setInt(1, issInsp.getId());
					insertInspection.setInt(2, issInsp.getFk());
					insertInspection.setTimestamp(3, Timestamp.valueOf(issInsp.getDateCreated()));
					insertInspection.setTimestamp(4, Timestamp.valueOf(issInsp.getDateModified()));
					insertInspection.setString(5, issInsp.getLog());
					insertInspection.setDate(6, Date.valueOf(issInsp.getLogDate()));

					insertInspection.execute();
					insertInspection.clearParameters();
					break;
				case ISSUES:
					if (Main.DEBUG)
						System.out.println("Saving Issue");
					
					insertIssue.setInt(1, issInsp.getId());
					insertIssue.setInt(2, issInsp.getFk());
					insertIssue.setTimestamp(3, Timestamp.valueOf(issInsp.getDateCreated()));
					insertIssue.setTimestamp(4, Timestamp.valueOf(issInsp.getDateModified()));
					insertIssue.setString(5, issInsp.getLog());
					insertIssue.setDate(6, Date.valueOf(issInsp.getLogDate()));

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

	/**
	 * Inserts a new Bill into the server
	 * @param bill Bill to insert
	 * */
	public void insert(Bill bill) {
		try {
			if (Main.DEBUG)
				System.out.println("Saving Bill");
			
			insertBill.setInt(1, bill.getId());
			insertBill.setInt(2, bill.getFk());
			insertBill.setTimestamp(3, Timestamp.valueOf(bill.getDateCreated()));
			insertBill.setTimestamp(4, Timestamp.valueOf(bill.getDateModified()));
			insertBill.setString(5, bill.getCompanyName());
			insertBill.setString(6, bill.getAddress());
			insertBill.setString(7, bill.getPhone());
			//insertBill.setDouble(8, bill.getBill());

			insertBill.execute();
			insertBill.clearParameters();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new Tenant/Candidate Spouse into the server
	 * @param roomMate Spouse to insert
	 * @param table Specifies table to insert into   
	 * */
	public void insert(RoomMate roomMate, DBTables table) {
		try {
			switch(table) {
				case TENANTS:
					if (Main.DEBUG)
						System.out.println("Saving Spouse(tenant)");
					
					insertTnantSpouse.setInt(1, roomMate.getId());
					insertTnantSpouse.setInt(2, roomMate.getFk());
					insertTnantSpouse.setTimestamp(3, Timestamp.valueOf(roomMate.getDateCreated()));
					insertTnantSpouse.setTimestamp(4, Timestamp.valueOf(roomMate.getDateModified()));
					insertTnantSpouse.setString(5, roomMate.getFirstName());
					insertTnantSpouse.setString(6, roomMate.getLastName());
					insertTnantSpouse.setString(7, roomMate.getPhone());
					insertTnantSpouse.setString(8, roomMate.getEmail());
					insertTnantSpouse.setString(9, roomMate.getSsn());
					insertTnantSpouse.setDate(10, Date.valueOf(roomMate.getDateOfBirth()));
					insertTnantSpouse.setInt(11, roomMate.getAnnualIncome());

					insertTnantSpouse.execute();
					insertTnantSpouse.clearParameters();
					break;
				case CANDIDATES:
					if (Main.DEBUG)
						System.out.println("Saving Spouse(candidate)");
					
					insertCandSpouse.setInt(1, roomMate.getId());
					insertCandSpouse.setInt(2, roomMate.getFk());
					insertCandSpouse.setTimestamp(3, Timestamp.valueOf(roomMate.getDateCreated()));
					insertCandSpouse.setTimestamp(4, Timestamp.valueOf(roomMate.getDateModified()));
					insertCandSpouse.setString(5, roomMate.getFirstName());
					insertCandSpouse.setString(6, roomMate.getLastName());
					insertCandSpouse.setString(7, roomMate.getPhone());
					insertCandSpouse.setString(8, roomMate.getEmail());
					insertCandSpouse.setString(9, roomMate.getSsn());
					insertCandSpouse.setDate(10, Date.valueOf(roomMate.getDateOfBirth()));
					insertCandSpouse.setInt(11, roomMate.getAnnualIncome());

					insertCandSpouse.execute();
					insertCandSpouse.clearParameters();
					break;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	//TODO: Delete anything related to Invoice class for new Account class
	/**
	 * Inserts a new Invoice into the server
	 * @param account Invoice to insert
	 * @param table Selects table to insert into   
	 * */
	public void insert(Account account, DBTables table) {
		try {
			switch (table) {
				case TENANTS:
					if (Main.DEBUG)
						System.out.println("Saving Invoice(tenant)");
					
					insertTnantAccount.setInt(1, account.getId());
					insertTnantAccount.setInt(2, account.getFk());
					insertTnantAccount.setTimestamp(3, Timestamp.valueOf(account.getDateCreated()));
					insertTnantAccount.setTimestamp(4, Timestamp.valueOf(account.getDateModified()));


					insertTnantAccount.execute();
					insertTnantAccount.clearParameters();
					break;
				case CONTRACTORS:
					if (Main.DEBUG)
						System.out.println("Saving Invoice (Contractor)");
					
					insertContAccount.setInt(1, account.getId());
					insertContAccount.setInt(2, account.getFk());
					insertContAccount.setTimestamp(3, Timestamp.valueOf(account.getDateCreated()));
					insertContAccount.setTimestamp(4, Timestamp.valueOf(account.getDateModified()));


					insertContAccount.execute();
					insertContAccount.clearParameters();
					break;
				case BILLS:
					if (Main.DEBUG)
						System.out.println("Saving Invoice (Bill)");
					
					insertBillAccount.setInt(1, account.getId());
					insertBillAccount.setInt(2, account.getFk());
					insertBillAccount.setTimestamp(3, Timestamp.valueOf(account.getDateCreated()));
					insertBillAccount.setTimestamp(4, Timestamp.valueOf(account.getDateModified()));


					insertBillAccount.execute();
					insertBillAccount.clearParameters();
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
	public void update(Table table) {
		switch (table.getTableType()){
			case BUILDINGS:
				update((Building) table);
				break;
			case TENANTS:
				update((Tenant) table);
				break;
			case CANDIDATES:
				update((Candidate) table);
				break;
			case CONTRACTORS:
				update((Contractor) table);
				break;
			case BILLS:
				update((Bill) table);
				break;
			case INSPECTIONS:
			case ISSUES:
				update((NoteLog) table, table.getTableType());
				break;
			default:
				System.out.println("Table does not have the functionality to be inserted into database");
		}
	}

	/**
	 * Updates existing Apartment in the server
	 * @param building Apartment to be updated
	 * */
	public void update(Building building) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
			connection.setAutoCommit(false);
			delete(building);
			insert(building);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error updating apartment");
		}
	}

	/**
	 * Updates existing Tenant in the server
	 * @param tenant Tenant to be updated
	 * */
	public void update(Tenant tenant) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
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

	/**
	 * Updates existing Candidate in the server
	 * @param candidate Candidate to be updated
	 * */
	public void update(Candidate candidate) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
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

	/**
	 * Updates existing Contractor in the server
	 * @param contractor Contractor to be updated
	 * */
	public void update(Contractor contractor) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
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

	/**
	 * Updates existing Bill in the server
	 * @param bill Bill to be updated
	 * */
	public void update(Bill bill) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
			connection.setAutoCommit(false);
			delete(bill);
			insert(bill);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Updates existing Issue/Inspection in the server
	 * @param issInsp Issue/Inspection to be updated
	 * @param table Selects table to update
	 * */
	public void update(NoteLog issInsp, DBTables table) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
			connection.setAutoCommit(false);
			delete(issInsp, table);
			insert(issInsp, table);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Updates existing Spouse in the server
	 * @param roomMate Spouse to be updated
	 * @param table Selects table to update
	 * */
	public void update(RoomMate roomMate, DBTables table) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
			connection.setAutoCommit(false);
			delete(roomMate,table);
			insert(roomMate,table);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Updates existing Invoice in the server
	 * @param invoice Invoice to be updated
	 * @param table Selects table to update
	 * */
	public void update(Account invoice, DBTables table) {
		try {
			if (Main.DEBUG)
				System.out.println("Updating Apartment");
			
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
	public void delete(Table table) {
		switch (table.getTableType()) {
			case BUILDINGS:
				delete((Building) table);
				break;
			case TENANTS:
				delete((Tenant) table);
				break;
			case CANDIDATES:
				delete((Candidate) table);
				break;
			case CONTRACTORS:
				delete((Contractor) table);
				break;
			case ACCOUNT:
				//Insert account deletion
				break;
			case ROOMMATE:
				delete((RoomMate) table, table.getTableType());
				break;
			case BILLS:
				delete((Bill) table);
				break;
			case INSPECTIONS:
			case ISSUES:
				delete((NoteLog) table, table.getTableType());
				break;
			default:
				break;
		}
	}

	/**
	 * Deletes Apartment in the server
	 * @param building Apartment to be deleted
	 * */
	public void delete(Building building) {
		try {
			if (Main.DEBUG)
				System.out.println("Deleting Apartment");
			
			deleteApartment.setInt(1, building.getId());
			deleteApartment.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting apartment");
		}
	}

	/**
	 * Deletes Tenant in the server
	 * @param tenant Tenant to be deleted
	 * */
	public void delete(Tenant tenant) {
		try {
			if (Main.DEBUG)
				System.out.println("Deleting Apartment");
			
			deleteTenant.setInt(1, tenant.getId());
			deleteTenant.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting tenant");
		}
	}

	/**
	 * Deletes Candidate in the server
	 * @param candidate Candidate to be deleted
	 * */
	public void delete(Candidate candidate) {
		try {
			if (Main.DEBUG)
				System.out.println("Deleting Apartment");
			
			deleteCandidate.setInt(1, candidate.getId());
			deleteCandidate.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting candidate");
		}
	}

	/**
	 * Deletes Contractor in the server
	 * @param contractor Contractor to be deleted
	 * */
	public void delete(Contractor contractor) {
		try {
			if (Main.DEBUG)
				System.out.println("Deleting Apartment");
			
			deleteContractor.setInt(1, contractor.getId());
			deleteContractor.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilst deleting contractor");
		}
	}

	/**
	 * Deletes Issue/Inspection in the server
	 * @param issInsp Issue/Inspection to be deleted
	 * @param table Selects table to delete from
	 * */
	public void delete(NoteLog issInsp, DBTables table) {
		try {
			switch (table) {
				case INSPECTIONS:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteInspection.setInt(1, issInsp.getId());
					deleteInspection.execute();
					break;
				case ISSUES:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
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

	/**
	 * Deletes Bill in the server
	 * @param bill Bill to be deleted
	 * */
	public void delete(Bill bill) {
		try {
			if (Main.DEBUG)
				System.out.println("Deleting Apartment");
			
			deleteBill.setInt(1, bill.getId());
			deleteBill.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes Spouse in the server
	 * @param roomMate Spouse to be deleted
	 * @param table Selects table to delete from
	 * */
	public void delete(RoomMate roomMate, DBTables table) {
		try {
			switch(table){
				case TENANTS:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteTnantSpouse.setInt(1, roomMate.getId());
					deleteInspection.execute();
					break;
				case CANDIDATES:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteCandSpouse.setInt(1, roomMate.getId());
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

	/**
	 * Deletes Invoice in the server
	 * @param account Invoice to be deleted
	 * @param table Selects table to delete from
	 * */
	public void delete(Account account, DBTables table) {
		try {
			switch (table) {
				case TENANTS:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteTnantInvoice.setInt(1, account.getId());
					deleteTnantInvoice.execute();
					break;
				case CONTRACTORS:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteContInvoice.setInt(1, account.getId());
					deleteContInvoice.execute();
					break;
//				case INSURANCES:
//					if (Main.DEBUG)
//						System.out.println("Deleting Apartment");
//
//					deleteInsInvoice.setInt(1, account.getId());
//					deleteInsInvoice.execute();
//					break;
				case BILLS:
					if (Main.DEBUG)
						System.out.println("Deleting Apartment");
					
					deleteBillInvoice.setInt(1, account.getId());
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
			insertTnantAccount = connection.prepareStatement(INSERT_INTO_TNANT_INVOICES);
			insertInspection = connection.prepareStatement(INSERT_INTO_INSPECTIONS);
			insertCandidate = connection.prepareStatement(INSERT_INTO_CANDIDATES);
			insertCandSpouse = connection.prepareStatement(INSERT_INTO_CAND_SPOUSES);
			insertContractor = connection.prepareStatement(INSERT_INTO_CONTRACTORS);
			insertContAccount = connection.prepareStatement(INSERT_INTO_CONT_INVOICES);
			insertInsurance = connection.prepareStatement(INSERT_INTO_INSURANCES);
			insertInsInvoice = connection.prepareStatement(INSERT_INTO_INS_INVOICES);
			insertBill = connection.prepareStatement(INSERT_INTO_BILLS);
			insertBillAccount = connection.prepareStatement(INSERT_INTO_BILL_INVOICES);
			insertIssue = connection.prepareStatement(INSERT_INTO_ISSUES);

			//Updates
			updateApartment = connection.prepareStatement(UPDATE_APARTMENTS);
			updateTenant = connection.prepareStatement(UPDATE_TENANTS);
			updateTnantSpouse = connection.prepareStatement(UPDATE_TNANT_SPOUSES);
 			updateTnantInvoice = connection.prepareStatement(UPDATE_TNANT_INVOICES);
			updateInspection = connection.prepareStatement(UPDATE_INSPECTIONS);
			updateCandidate = connection.prepareStatement(UPDATE_CANDIDATES);
 			updateCandSpouse = connection.prepareStatement(UPDATE_CAND_SPOUSES);
			updateContractor = connection.prepareStatement(UPDATE_CONTRACTORS);
			updateContInvoice = connection.prepareStatement(UPDATE_CONT_INVOICES);
			updateInsurance = connection.prepareStatement(UPDATE_INSURANCES);
			updateInsInvoice = connection.prepareStatement(UPDATE_INS_INVOICES);
			updateBill = connection.prepareStatement(UPDATE_BILLS);
 			updateBillInvoice = connection.prepareStatement(UPDATE_BILL_INVOICES);
			updateIssue = connection.prepareStatement(UPDATE_ISSUES);

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
				insertTnantAccount.close();
				insertInspection.close();
				insertCandidate.close();
				insertCandSpouse.close();
				insertContractor.close();
				insertContAccount.close();
				insertInsurance.close();
				insertInsInvoice.close();
				insertBill.close();
				insertBillAccount.close();
				insertIssue.close();

				//Update Prepared Statements
				updateApartment.close();
				updateTenant.close();
				updateTnantSpouse.close();
				updateTnantInvoice.close();
				updateInspection.close();
				updateCandidate.close();
				updateCandSpouse.close();
				updateContractor.close();
				updateContInvoice.close();
				updateInsurance.close();
				updateInsInvoice.close();
				updateBill.close();
				updateBillInvoice.close();
				updateIssue.close();

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
