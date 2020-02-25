package com.apartmate.database.utilities;

import java.util.ArrayList;
import java.util.List;

import com.apartmate.database.tables.mainTables.Apartment;
import com.apartmate.database.tables.mainTables.Candidate;
import com.apartmate.database.tables.mainTables.Contractor;
import com.apartmate.database.tables.mainTables.Tenant;
import com.apartmate.database.tables.subTables.*;
import com.apartmate.database.dbMirror.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Class; establishes connection and facilitates the saving and loading of
 * table
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com) NOTE: Update constants unused in
 *         favor of delete/insert to update
 */
public class SQLBridge {
	// Connection variables
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet rs = null;

	private String url;
	private String username;
	private String pass;

	public static final int DBADMIN = 1;
	public static final int DBMANAGEMENT = 2;
	public static final int TNANTUSER = 3;
	
	private int credentials;
	
	private boolean preparedStatementsSet = false;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Table constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// TODO: tables
	// Tables
	private final String TABLE_APARTMENTS = "apartments";
	private final String TABLE_TENANTS = "tenants";
	private final String TABLE_CANDIDATES = "candidates";
	private final String TABLE_CONTRACTORS = "contractors";

	// Sub-tables
	private final String SUB_TABLE_INSURANCES = "insurances";
	private final String SUB_TABLE_ISSUES = "issues";
	private final String SUB_TABLE_INSPECTIONS = "inspections";
	private final String SUB_TABLE_TNANT_SPOUSES = "tnant_spouses";
	private final String SUB_TABLE_CAND_SPOUSES = "cand_spouses";
	private final String SUB_TABLE_TNANT_INVOICES = "tnant_invoices";
	private final String SUB_TABLE_CONT_INVOICES = "cont_invoices";

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Column constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Apartment table columns
	private final String COLUMN_APT_ID = "apartment_id";
	private final String COLUMN_APT_DATE_CREATED = "date_created";
	private final String COLUMN_APT_DATE_MODIFIED = "date_modified";
	private final String COLUMN_APT_ADDRESS = "address";
	private final String COLUMN_APT_CITY = "city";
	private final String COLUMN_APT_STATE = "state";
	private final String COLUMN_APT_ZIP = "zip";
	private final String COLUMN_APT_CAPACITY = "capacity";
	private final String COLUMN_APT_NUM_TENANTS = "num_tenants";
	private final String COLUMN_APT_INS_MONTHLY_PAYMENT = "ins_bill";

	// Tenant table columns
	private final String COLUMN_TNANT_ID = "tenant_id";
	private final String COLUMN_TNANT_APT = "apartment_id";
	private final String COLUMN_TNANT_DATE_CREATED = "date_created";
	private final String COLUMN_TNANT_DATE_MODIFIED = "date_modified";
	private final String COLUMN_TNANT_FNAME = "first_name";
	private final String COLUMN_TNANT_LNAME = "last_name";
	private final String COLUMN_TNANT_PHONE = "phone_number";
	private final String COLUMN_TNANT_EMAIL = "email";
	private final String COLUMN_TNANT_IDN = "ssn";
	private final String COLUMN_TNANT_RENT = "rent";
	private final String COLUMN_TNANT_NUM_CHILDREN = "num_children";
	private final String COLUMN_TNANT_MOVE_IN_DATE = "move_in_date";
	private final String COLUMN_TNANT_DOB = "date_of_birth";
	private final String COLUMN_TNANT_ANNUAL_INCOME = "annual_income";
	private final String COLUMN_TNANT_SLATED_FOR_EVICTION = "slated_for_eviction";
	private final String COLUMN_TNANT_EVICT_REASON = "evict_reason";
	private final String COLUMN_TNANT_MOV_OUT_DATE = "move_out_date";

	// Candidate table columns
	private final String COLUMN_CAND_ID = "candidate_id";
	private final String COLUMN_CAND_APT = "apartment_id";
	private final String COLUMN_CAND_DATE_CREATED = "date_created";
	private final String COLUMN_CAND_DATE_MODIFIED = "date_modified";
	private final String COLUMN_CAND_FNAME = "first_name";
	private final String COLUMN_CAND_LNAME = "last_name";
	private final String COLUMN_CAND_PHONE = "phone_number";
	private final String COLUMN_CAND_EMAIL = "email";
	private final String COLUMN_CAND_DOB = "date_of_birth";
	private final String COLUMN_CAND_ANNUAL_INCOME = "annual_income";
	private final String COLUMN_CAND_IDN = "ssn";
	private final String COLUMN_CAND_NUM_CHILDREN = "num_children";
	private final String COLUMN_CAND_ACCEPTED = "accepted";

	// Contractor table columns
	private final String COLUMN_CONT_ID = "contractor_id";
	private final String COLUMN_CONT_APT = "apartment_id";
	private final String COLUMN_CONT_DATE_CREATED = "date_created";
	private final String COLUMN_CONT_DATE_MODIFIED = "date_modified";
	private final String COLUMN_CONT_NAME = "company_name";
	private final String COLUMN_CONT_BILL = "bill";
	private final String COLUMN_CONT_PHONE = "phone_number";
	private final String COLUMN_CONT_EMAIL = "email";

	// Insurance table columns
	private final String COLUMN_INSURANCE_ID = "insurance_id";
	private final String COLUMN_INSURANCE_APT_ID = "apartment_id";
	private final String COLUMN_INSURANCE_DATE_CREATED = "date_created";
	private final String COLUMN_INSURANCE_DATE_MODIFIED = "date_modified";
	private final String COLUMN_INSURANCE_PAYMENT = "ins_payment";
	private final String COLUMN_INSURANCE_BALANCE = "ins_balance";
	private final String COLUMN_INSURANCE_TOTAL_PAID = "ins_ttl_paid";
	private final String COLUMN_INSURANCE_TOTAL_DUE = "ins_ttl_due";
	private final String COLUMN_INSURANCE_PMT_DATE = "ins_pmt_date";
	private final String COLUMN_INSURANCE_DUE_DATE = "ins_due_date";

	// Issue table columns
	private final String COLUMN_ISSUE_ID = "issue_id";
	private final String COLUMN_ISSUE_APT_ID = "apartment_id";
	private final String COLUMN_ISSUE_TNANT_ID = "tenant_id";
	private final String COLUMN_ISSUE_DATE_CREATED = "date_created";
	private final String COLUMN_ISSUE_DATE_MODIFIED = "date_modified";
	private final String COLUMN_ISSUE_DESCRIPTION = "issue_desc";

	// Inspection table columns
	private final String COLUMN_INSPECTION_ID = "inspection_id";
	private final String COLUMN_INSPECTION_APT_ID = "apartment_id";
	private final String COLUMN_INSPECTION_TNANT_ID = "tenant_id";
	private final String COLUMN_INSPECTION_DATE_CREATED = "date_created";
	private final String COLUMN_INSPECTION_DATE_MODIFIED = "date_modified";
	private final String COLUMN_INSPECTION_DESCRIPTION = "inspection_desc";

	// Spouse table columns
	private final String COLUMN_SPOUSE_ID = "spouse_id";
	private final String COLUMN_SPOUSE_TNANT_ID = "tenant_id";
	private final String COLUMN_SPOUSE_CAND_ID = "candidate_id";
	private final String COLUMN_SPOUSE_DATE_CREATED = "date_created";
	private final String COLUMN_SPOUSE_DATE_MODIFIED = "date_modified";
	private final String COLUMN_SPOUSE_FNAME = "first_name";
	private final String COLUMN_SPOUSE_LNAME = "last_name";
	private final String COLUMN_SPOUSE_PHONE = "phone_number";
	private final String COLUMN_SPOUSE_EMAIL = "email";
	private final String COLUMN_SPOUSE_IDN = "ssn";
	private final String COLUMN_SPOUSE_DOB = "date_of_birth";
	private final String COLUMN_SPOUSE_ANNUAL_INCOME = "annual_income";

	// Tenant invoice table columns
	private final String COLUMN_TNANT_INVOICE_ID = "invoice_id";
	private final String COLUMN_TNANT_INVOICE_TNANT_ID = "tenant_id";
	private final String COLUMN_TNANT_INVOICE_DATE_MODIFIED = "date_modified";
	private final String COLUMN_TNANT_INVOICE_PMT_AMOUNT = "payment_amount";
	private final String COLUMN_TNANT_INVOICE_BALANCE = "balance";
	private final String COLUMN_TNANT_INVOICE_TOTAL_PAID = "tnant_ttl_paid";
	private final String COLUMN_TNANT_INVOICE_TOTAL_DUE = "tnant_ttl_due";
	private final String COLUMN_TNANT_INVOICE_PAYMENT_DATE = "payment_date";
	private final String COLUMN_TNANT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// Contractor invoice table columns
	private final String COLUMN_CONT_INVOICE_ID = "invoice_id";
	private final String COLUMN_CONT_INVOICE_TNANT_ID = "contractor_id";
	private final String COLUMN_CONT_INVOICE_DATE_MODIFIED = "date_modified";
	private final String COLUMN_CONT_INVOICE_PMT_AMOUNT = "payment_amount";
	private final String COLUMN_CONT_INVOICE_BALANCE = "balance";
	private final String COLUMN_CONT_INVOICE_TOTAL_PAID = "cont_ttl_paid";
	private final String COLUMN_CONT_INVOICE_TOTAL_DUE = "cont_ttl_due";
	private final String COLUMN_CONT_INVOICE_PAYMENT_DATE = "payment_date";
	private final String COLUMN_CONT_INVOICE_PMT_DUE_DATE = "payment_due_date";

	// TODO: Queries
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Query constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Query Strings
	private final String QUERY_APARTMENTS = "SELECT * FROM " + TABLE_APARTMENTS;
	private final String QUERY_TENANTS = "SELECT * FROM " + TABLE_TENANTS;
	private final String QUERY_CANDIDATES = "SELECT * FROM " + TABLE_CANDIDATES;
	private final String QUERY_CONTRACTORS = "SELECT * FROM " + TABLE_CONTRACTORS;

	private final String QUERY_INSURANCES = "SELECT * FROM " + SUB_TABLE_INSURANCES;
	private final String QUERY_ISSUES = "SELECT * FROM " + SUB_TABLE_ISSUES;
	private final String QUERY_INSPECTIONS = "SELECT * FROM " + SUB_TABLE_INSPECTIONS;
	private final String QUERY_TNANT_SPOUSES = "SELECT * FROM " + SUB_TABLE_TNANT_SPOUSES;
	private final String QUERY_CAND_SPOUSES = "SELECT * FROM " + SUB_TABLE_CAND_SPOUSES;
	private final String QUERY_TNANT_INVOICES = "SELECT * FROM " + SUB_TABLE_TNANT_INVOICES;
	private final String QUERY_CONT_INVOICES = "SELECT * FROM " + SUB_TABLE_CONT_INVOICES;

	// TODO: Inserts
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Insert constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Insert statements
	private final String INSERT_INTO_APARTMENTS = "INSERT INTO " + TABLE_APARTMENTS + " VALUES (?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_TENANTS = "INSERT INTO " + TABLE_TENANTS
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_CANDIDATES = "INSERT INTO " + TABLE_CANDIDATES
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_CONTRACTORS = "INSERT INTO " + TABLE_CONTRACTORS + " VALUES (?,?,?,?,?,?,?,?)";

	private final String INSERT_INTO_INSURANCES = "INSERT INTO " + SUB_TABLE_INSURANCES
			+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_ISSUES = "INSERT INTO " + SUB_TABLE_ISSUES + " VALUES (?,?,?,?,?,?)";
	private final String INSERT_INTO_INSPECTIONS = "INSERT INTO " + SUB_TABLE_INSPECTIONS + " VALUES (?,?,?,?,?,?)";
	private final String INSERT_INTO_TNANT_SPOUSES = "INSERT INTO " + SUB_TABLE_TNANT_SPOUSES + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_CAND_SPOUSES = "INSERT INTO " + SUB_TABLE_CAND_SPOUSES + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_TNANT_INVOICES = "INSERT INTO " + SUB_TABLE_TNANT_INVOICES
			+ " VALUES (?,?,?,?,?,?,?,?,?)";
	private final String INSERT_INTO_CONT_INVOICES = "INSERT INTO " + SUB_TABLE_CONT_INVOICES
			+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

	// TODO: Updates
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Prepared Statement table constants
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	/*
	 * //Apartment update private final String UPDATE_APARTMENTS_APARTMENT_ID=
	 * "UPDATE " + TABLE_APARTMENTS + " SET " + COLUMN_APT_ID + " = ? WHERE " +
	 * COLUMN_APT_ID + " = ?"; private final String
	 * UPDATE_APARTMENTS_APARTMENT_ADDRESS="UPDATE " + TABLE_APARTMENTS + " SET " +
	 * COLUMN_APT_ADDRESS +" = ? WHERE " + COLUMN_APT_ID + " = ?"; private final
	 * String UPDATE_APARTMENTS_APARTMENT_SIZE= "UPDATE " + TABLE_APARTMENTS +
	 * " SET " + COLUMN_APT_CAPACITY + " = ? WHERE " + COLUMN_APT_ID + " = ?";
	 * private final String UPDATE_APARTMENTS_APARTMENT_CITY= "UPDATE " +
	 * TABLE_APARTMENTS + " SET " + COLUMN_APT_CITY + " = ? WHERE " + COLUMN_APT_ID
	 * + " = ?"; private final String UPDATE_APARTMENTS_APARTMENT_STATE= "UPDATE " +
	 * TABLE_APARTMENTS + " SET " + COLUMN_APT_STATE + " = ? WHERE " + COLUMN_APT_ID
	 * + " = ?"; private final String UPDATE_APARTMENTS_APARTMENT_ZIP= "UPDATE " +
	 * TABLE_APARTMENTS + " SET "+ COLUMN_APT_ZIP +" = ? WHERE " + COLUMN_APT_ID +
	 * " = ?"; private final String UPDATE_APARTMENTS_APARTMENT_DATE_MODIFIED=
	 * "UPDATE " + TABLE_APARTMENTS + " SET " + COLUMN_APT_DATE_MODIFIED +
	 * " = ? WHERE " + COLUMN_APT_ID + " = ?";
	 * 
	 * 
	 * //Tenant update private final String UPDATE_TENANTS_TENANT_ID = "UPDATE " +
	 * TABLE_TENANTS + " SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_TENANTS_TENANT_FNAME = "UPDATE " + TABLE_TENANTS +
	 * " SET ? = ? WHERE id = ?"; private final String UPDATE_TENANTS_TENANT_LNAME =
	 * "UPDATE " + TABLE_TENANTS + " SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_TENANTS_TENANT_APT_ID = "UPDATE " + TABLE_TENANTS +
	 * " SET ? = ? WHERE id = ?"; private final String UPDATE_TENANTS_TENANT_PHONE =
	 * "UPDATE " + TABLE_TENANTS + " SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_TENANTS_TENANT_EMAIL = "UPDATE " + TABLE_TENANTS +
	 * " SET ? = ? WHERE id = ?"; private final String UPDATE_TENANTS_TENANT_DOB =
	 * "UPDATE " + TABLE_TENANTS + " SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_TENANTS_TENANT_IDN = "UPDATE " + TABLE_TENANTS +
	 * " SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_TENANTS_TENANT_DATE_CREATED = "UPDATE " + TABLE_TENANTS +
	 * " SET ? = ? WHERE id = ?";
	 * 
	 * 
	 * //Candidate update private final String UPDATE_CANDIDATES_CANDIDATE_ID =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_APT_ID =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_DATE_CREATED =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_DATE_MODIFIED =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_FNAME =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_LNAME =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_PHONE =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_EMAIL =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_DOB =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_ANNUAL_INCOME =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_IDN =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_NUM_CHILDREN =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CANDIDATES_CANDIDATE_ACCEPTED =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?";
	 * 
	 * 
	 * //Contractor update private final String UPDATE_CONTRACTORS_CONTRACTOR_ID =
	 * "UPDATE contractors SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CONTRACTORS_CONTRACTOR_NAME =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CONTRACTORS_CONTRACTOR_APT =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_CONTRACTORS_CONTRACTOR_DATE_MODIFIED =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?";
	 * 
	 * 
	 * //Insurance; private String UPDATE_INSURANCES_INSURANCE_ID =
	 * "UPDATE insurances SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_INSURANCES_INSURANCE_APT_ID =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_INSURANCES_INSURANCE_DATE_MODIFIED =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_INSURANCES_INSURANCE_PAYMENT =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_INSURANCES_INSURANCE_BALANCE =
	 * "UPDATE cont_invoices SET ? = ? WHERE id = ?";
	 * 
	 * 
	 * //Issue; private final String UPDATE_ISSUES_ISSUE_ID =
	 * "UPDATE issues SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_ISSUES_ISSUE_APT_ID = "UPDATE issues SET ? = ? WHERE id = ?"; private
	 * final String UPDATE_ISSUES_ISSUE_TNANT_ID =
	 * "UPDATE issues SET ? = ? WHERE id = ?"; private final String
	 * UPDATE_ISSUES_ISSUE_DATE_MODIFIED = "UPDATE issues SET ? = ? WHERE id = ?";
	 * private final String UPDATE_ISSUES_ISSUE_DESCRIPTION =
	 * "UPDATE issues SET ? = ? WHERE id = ?";
	 * 
	 * 
	 * //Inspection; private final String UPDATE_INSPECTIONS_INSPECTION_ID =
	 * "UPDATE inspections SET inspection_id = ? WHERE inspection_id = ?"; private
	 * final String UPDATE_INSPECTIONS_INSPECTION_APT_ID =
	 * "UPDATE inspections SET apartment_id = ? WHERE inspection_id = ?"; private
	 * final String UPDATE_INSPECTIONS_INSPECTION_TNANT_ID =
	 * "UPDATE inspections SET tenant_id = ? WHERE inspection_id = ?"; private final
	 * String UPDATE_INSPECTIONS_INSPECTION_DATE_MODIFIED =
	 * "UPDATE inspections SET date_modified = ? WHERE inspection_id = ?"; private
	 * final String UPDATE_INSPECTIONS_INSPECTION_DESCRIPTION =
	 * "UPDATE inspections SET description = ? WHERE inspection_id = ?";
	 * 
	 * 
	 * //Spouse; private final String UPDATE_SPOUSES_SPOUSE_ID =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_TENANT_ID =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_CANDIDATE_ID =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_DATE_CREATED =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_DATE_MODIFIED =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_FNAME =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_LNAME =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_PHONE
	 * ="UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_EMAIL =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_IDN =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_DOB =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?"; private final String
	 * UPDATE_SPOUSES_SPOUSE_ANNUAL_INCOME =
	 * "UPDATE spouses SET spouse_id = ? WHERE spouse_id = ?";
	 * 
	 * 
	 * //TnantInvoice; private final String UPDATE_TNANT_INVOICES_INVOICE_ID =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_TNANT_ID =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_DATE_MODIFIED =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_PAYMENT_AMOUNT =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_BALANCE =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_PAYMENT_DATE =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_TNANT_INVOICES_TNANT_INVOICE_DUE_DATE =
	 * "UPDATE tnant_invoices SET ? = ? WHERE invoice_id = ?";
	 * 
	 * 
	 * //ContInvoice; private final String UPDATE_CONT_INVOICES_INVOICE_ID =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_TNANT_ID =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_DATE_MODIFIED =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_PAYMENT_AMOUNT =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_BALANCE =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_PAYMENT_DATE =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?"; private final String
	 * UPDATE_CONT_INVOICES_CONT_INVOICE_DUE_DATE =
	 * "UPDATE cont_invoices SET ? = ? WHERE invoice_id = ?";
	 */

	// TODO Deletes
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Delete Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Delete methods
	private final String DELETE_FROM_APARTMENTS = "DELETE FROM " + TABLE_APARTMENTS + " WHERE " + COLUMN_APT_ID
			+ " = ?";
	private final String DELETE_FROM_TENANTS = "DELETE FROM " + TABLE_TENANTS + " WHERE " + COLUMN_TNANT_ID + " = ?";
	private final String DELETE_FROM_CANDIDATES = "DELETE FROM " + TABLE_CANDIDATES + " WHERE " + COLUMN_CAND_ID
			+ " = ?";
	private final String DELETE_FROM_CONTRACTORS = "DELETE FROM " + TABLE_CONTRACTORS + " WHERE " + COLUMN_CONT_ID
			+ " = ?";

	private final String DELETE_FROM_INSURANCES = "DELETE FROM " + SUB_TABLE_INSURANCES + " WHERE "
			+ COLUMN_INSURANCE_ID + " = ?";
	private final String DELETE_FROM_ISSUES = "DELETE FROM " + SUB_TABLE_ISSUES + " WHERE " + COLUMN_ISSUE_ID + " = ?";
	private final String DELETE_FROM_INSPECTIONS = "DELETE FROM " + SUB_TABLE_INSPECTIONS + " WHERE "
			+ COLUMN_INSPECTION_ID + " = ?";
	private final String DELETE_FROM_TNANT_SPOUSES = "DELETE FROM " + SUB_TABLE_TNANT_SPOUSES + " WHERE " + COLUMN_SPOUSE_ID
			+ " = ?";
	private final String DELETE_FROM_CAND_SPOUSES = "DELETE FROM " + SUB_TABLE_CAND_SPOUSES + " WHERE " + COLUMN_SPOUSE_ID
			+ " = ?";
	private final String DELETE_FROM_TNANT_INVOICES = "DELETE FROM " + SUB_TABLE_TNANT_INVOICES + " WHERE "
			+ COLUMN_TNANT_INVOICE_ID + " = ?";
	private final String DELETE_FROM_CONT_INVOICES = "DELETE FROM " + SUB_TABLE_CONT_INVOICES + " WHERE "
			+ COLUMN_CONT_INVOICE_ID + " = ?";

	// ---------------------------------------------------------------------------------

	// TODO: Prepared Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Prepared Statements
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// Query Prepared Statements (Might not be needed; no (known) risk of SQL
	// injection (requires no user input))
	/*
	 * private PreparedStatement queryApartments; private PreparedStatement
	 * queryTenants; private PreparedStatement queryContractors; private
	 * PreparedStatement queryInsurance; private PreparedStatement queryIssue;
	 * private PreparedStatement queryInspection; private PreparedStatement
	 * queryTnantInvoice; private PreparedStatement queryContInvoice;
	 */

	// Insert Prepared Statements
	private PreparedStatement insertApartment;
	private PreparedStatement insertTenant;
	private PreparedStatement insertCandidate;
	private PreparedStatement insertContractor;
	private PreparedStatement insertInsurance;
	private PreparedStatement insertIssue;
	private PreparedStatement insertInspection;
	private PreparedStatement insertTnantSpouse;
	private PreparedStatement insertCandSpouse;
	private PreparedStatement insertTnantInvoice;
	private PreparedStatement insertContInvoice;

	/*
	 * //Update Prepared Statements private PreparedStatement updateApartment;
	 * private PreparedStatement updateTenant; private PreparedStatement
	 * updateCandidate; private PreparedStatement updateContractor; private
	 * PreparedStatement updateInsurance; private PreparedStatement updateIssue;
	 * private PreparedStatement updateInspection; private PreparedStatement
	 * updateSpouse; private PreparedStatement updateTnantInvoice; private
	 * PreparedStatement updateContInvoice;
	 */

	// Delete Prepared statements
	private PreparedStatement deleteApartment;
	private PreparedStatement deleteTenant;
	private PreparedStatement deleteCandidate;
	private PreparedStatement deleteContractor;
	private PreparedStatement deleteInsurance;
	private PreparedStatement deleteIssue;
	private PreparedStatement deleteInspection;
	private PreparedStatement deleteTnantSpouse;
	private PreparedStatement deleteCandSpouse;
	private PreparedStatement deleteTnantInvoice;
	private PreparedStatement deleteContInvoice;

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// SQLBridge Constructors
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Constructor
	public SQLBridge() {
		url = "jdbc:mysql://localhost/am";
		username = "testing";
		pass = "Java#1sQl#2TestingUser#99";
	}

	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------
	// SQL Connection methods
	// ---------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------

	// Testing connect function
	public boolean connect() {
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

	// Final connect method; requires username entry
	public boolean connect(String username, String pass) {
		this.username = username;
		this.pass = pass;

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

	public boolean save() {
		Database.getInstance();

		// add/delete new/removed apartments

		// add/delete new/removed tenants

		// add/delete new/removed candidates

		// add/delete new/removed contractors
		return true;
	}

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
	// TODO: Query method testing
	public List<Apartment> queryApartments() {
		List<Apartment> temp = new ArrayList<>();

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
				app.setInsBill(rs.getDouble(COLUMN_APT_INS_MONTHLY_PAYMENT));

				temp.add(app);
			}
			// Set foreign tables

			// Insurances
			rs = statement.executeQuery(QUERY_INSURANCES);

			while (rs.next()) {
				Insurance insurance = new Insurance();

				insurance.setId(rs.getInt(COLUMN_INSURANCE_ID));
				insurance.setFk(rs.getInt(COLUMN_INSURANCE_APT_ID));
				insurance.setDateCreated(rs.getTimestamp(COLUMN_INSURANCE_DATE_CREATED));
				insurance.setDateModified(rs.getTimestamp(COLUMN_INSURANCE_DATE_MODIFIED));
				insurance.setPayment(rs.getDouble(COLUMN_INSURANCE_PAYMENT));
				insurance.setBalance(rs.getDouble(COLUMN_INSURANCE_BALANCE));
				insurance.setTotalPaid(rs.getDouble(COLUMN_INSURANCE_TOTAL_PAID));
				insurance.setTotalDue(rs.getDouble(COLUMN_INSURANCE_TOTAL_DUE));
				insurance.setPaymentDate(rs.getTimestamp(COLUMN_INSURANCE_PMT_DATE));
				insurance.setDueDate(rs.getTimestamp(COLUMN_INSURANCE_DUE_DATE));

				temp.forEach(apt -> {
					if (insurance.getFk() == apt.getId()) {
						apt.getInsurances().add(insurance);
					}
				});
			}

			// Issues
			rs = statement.executeQuery(QUERY_ISSUES);

			while (rs.next()) {
				Issue issue = new Issue();

				issue.setId(rs.getInt(COLUMN_ISSUE_ID));
				issue.setFk(rs.getInt(COLUMN_ISSUE_APT_ID));
				issue.setFk2(rs.getInt(COLUMN_ISSUE_TNANT_ID));
				issue.setDateCreated(rs.getTimestamp(COLUMN_ISSUE_DATE_CREATED));
				issue.setDateModified(rs.getTimestamp(COLUMN_ISSUE_DATE_MODIFIED));
				issue.setDescription(rs.getString(COLUMN_ISSUE_DESCRIPTION));

				temp.forEach(apt -> {
					if (issue.getFk() == apt.getId()) {
						apt.getIssues().add(issue);
					}
				});

			}

			// Inspection
			rs = statement.executeQuery(QUERY_INSPECTIONS);

			while (rs.next()) {
				Inspection inspection = new Inspection();

				inspection.setId(rs.getInt(COLUMN_INSPECTION_ID));
				inspection.setFk(rs.getInt(COLUMN_INSPECTION_APT_ID));
				inspection.setFk2(rs.getInt(COLUMN_INSPECTION_TNANT_ID));
				inspection.setDateCreated(rs.getTimestamp(COLUMN_INSPECTION_DATE_CREATED));
				inspection.setDateModified(rs.getTimestamp(COLUMN_INSPECTION_DATE_MODIFIED));
				inspection.setDescription(rs.getString(COLUMN_INSPECTION_DESCRIPTION));

				temp.forEach(apt -> {
					if (inspection.getFk() == apt.getId()) {
						apt.getInspections().add(inspection);
					}
				});
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying apartments");
			return null;
		}

		return temp;
	}

	public List<Tenant> queryTenants() {
		List<Tenant> temp = new ArrayList<>();
		;

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
				tenant.setLastName(COLUMN_TNANT_LNAME);
				tenant.setPhone(rs.getString(COLUMN_TNANT_PHONE));
				tenant.setEmail(rs.getString(COLUMN_TNANT_EMAIL));
				tenant.setSSN(rs.getString(COLUMN_TNANT_IDN));
				tenant.setRent(rs.getDouble(COLUMN_TNANT_RENT));
				tenant.setNumChildren(rs.getInt(COLUMN_TNANT_NUM_CHILDREN));
				tenant.setMovinDate(rs.getDate(COLUMN_TNANT_MOVE_IN_DATE));
				tenant.setDateOfBirth(rs.getDate(COLUMN_TNANT_DOB));
				tenant.setAnnualIncome(rs.getInt(COLUMN_TNANT_ANNUAL_INCOME));
				tenant.setEvicted(rs.getBoolean(COLUMN_TNANT_SLATED_FOR_EVICTION));
				tenant.setEvictReason(rs.getString(COLUMN_TNANT_EVICT_REASON));
				tenant.setMovoutDate(rs.getDate(COLUMN_TNANT_MOV_OUT_DATE));

				temp.add(tenant);
			}
			// Set foreign tables
			// Spouse
			rs = statement.executeQuery(QUERY_TNANT_SPOUSES);

			while (rs.next()) {
				Spouse spouse = new Spouse();

				spouse.setId(rs.getInt(COLUMN_SPOUSE_ID));
				spouse.setFk(rs.getInt(COLUMN_SPOUSE_TNANT_ID));
				spouse.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED));
				spouse.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED));
				spouse.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				spouse.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				spouse.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				spouse.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				spouse.setSSN(rs.getString(COLUMN_SPOUSE_IDN));
				spouse.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB));
				spouse.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				temp.forEach(tnant -> {
					if (spouse.getFk() == tnant.getId()) {
						tnant.setSpouse(spouse);
					}
				});
			}

			// Invoices
			rs = statement.executeQuery(QUERY_TNANT_INVOICES);
			while (rs.next()) {
				TnantInvoice invoice = new TnantInvoice();

				invoice.setId(rs.getInt(COLUMN_TNANT_INVOICE_ID));
				invoice.setFk(rs.getInt(COLUMN_TNANT_INVOICE_TNANT_ID));
				invoice.setDateModified(rs.getTimestamp(COLUMN_TNANT_INVOICE_DATE_MODIFIED));
				invoice.setPayment(rs.getDouble(COLUMN_TNANT_INVOICE_PMT_AMOUNT));
				invoice.setBalance(rs.getDouble(COLUMN_TNANT_INVOICE_BALANCE));
				invoice.setTotalPaid(rs.getDouble(COLUMN_TNANT_INVOICE_TOTAL_PAID));
				invoice.setTotalDue(rs.getDouble(COLUMN_TNANT_INVOICE_TOTAL_DUE));
				invoice.setPaymentDate(rs.getTimestamp(COLUMN_TNANT_INVOICE_PAYMENT_DATE));
				invoice.setDueDate(rs.getTimestamp(COLUMN_TNANT_INVOICE_PMT_DUE_DATE));

				temp.forEach(tnant -> {
					if (invoice.getFk() == tnant.getId()) {
						tnant.getInvoices().add(invoice);
					}
				});
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying tenants");
			return null;
		}

		return temp;
	}

	public List<Candidate> queryCandidates() {
		List<Candidate> temp = new ArrayList<>();

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

				temp.add(candidate);
			}
			// Set foreign tables
			rs = statement.executeQuery(QUERY_CAND_SPOUSES);

			// Should only run once
			while (rs.next()) {
				Spouse spouse = new Spouse();

				spouse.setId(rs.getInt(COLUMN_SPOUSE_ID));
				spouse.setFk2(rs.getInt(COLUMN_SPOUSE_CAND_ID));
				spouse.setDateCreated(rs.getTimestamp(COLUMN_SPOUSE_DATE_CREATED));
				spouse.setDateModified(rs.getTimestamp(COLUMN_SPOUSE_DATE_MODIFIED));
				spouse.setFirstName(rs.getString(COLUMN_SPOUSE_FNAME));
				spouse.setLastName(rs.getString(COLUMN_SPOUSE_LNAME));
				spouse.setPhone(rs.getString(COLUMN_SPOUSE_PHONE));
				spouse.setEmail(rs.getString(COLUMN_SPOUSE_EMAIL));
				spouse.setSSN(rs.getString(COLUMN_SPOUSE_IDN));
				spouse.setDateOfBirth(rs.getDate(COLUMN_SPOUSE_DOB));
				spouse.setAnnualIncome(rs.getInt(COLUMN_SPOUSE_ANNUAL_INCOME));

				temp.forEach(cand -> {
					if (spouse.getFk2() == cand.getId()) {
						cand.setSpouse(spouse);
					}
				});
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying candidates");
			return null;
		}

		return temp;
	}

	public List<Contractor> queryContractors() {
		List<Contractor> temp = new ArrayList<>();

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

				temp.add(contractor);
			}
			// Set foreign tables
			// Invoices
			rs = statement.executeQuery(QUERY_CONT_INVOICES);
			while (rs.next()) {
				ContInvoice invoice = new ContInvoice();

				invoice.setId(rs.getInt(COLUMN_CONT_INVOICE_ID));
				invoice.setFk(rs.getInt(COLUMN_CONT_INVOICE_TNANT_ID));
				invoice.setDateModified(rs.getDate(COLUMN_CONT_INVOICE_DATE_MODIFIED));
				invoice.setPayment(rs.getDouble(COLUMN_CONT_INVOICE_PMT_AMOUNT));
				invoice.setBalance(rs.getDouble(COLUMN_CONT_INVOICE_BALANCE));
				invoice.setTotalPaid(rs.getDouble(COLUMN_CONT_INVOICE_TOTAL_PAID));
				invoice.setTotalDue(rs.getDouble(COLUMN_CONT_INVOICE_TOTAL_DUE));
				invoice.setPaymentDate(rs.getTimestamp(COLUMN_CONT_INVOICE_PAYMENT_DATE));
				invoice.setDueDate(rs.getTimestamp(COLUMN_CONT_INVOICE_PMT_DUE_DATE));

				temp.forEach(cont -> {
					if (invoice.getFk() == cont.getId()) {
						cont.getInvoices().add(invoice);
					}
				});
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error querying contractors");
			return null;
		}

		return temp;
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Insert Methods
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
			insertApartment.setDouble(10, apartment.getInsBill());

			insertApartment.execute();
			insertApartment.clearParameters();

			// Save sub-tables
			// Insurance
			for (Insurance insurance : apartment.getInsurances()) {
				insert(insurance);
			}
			// Issue
			for (Issue issue : apartment.getIssues()) {
				insert(issue);
			}

			// Inspection
			for (Inspection inspection : apartment.getInspections()) {
				insert(inspection);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

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
			insertTenant.setDate(12, new Date(tenant.getMovinDate().getTime()));
			insertTenant.setDate(13, new Date(tenant.getDateOfBirth().getTime()));
			insertTenant.setInt(14, tenant.getAnnualIncome());
			insertTenant.setBoolean(15, tenant.isEvicted());
			insertTenant.setString(16, tenant.getEvictReason());
			insertTenant.setDate(17, new Date(tenant.getMovoutDate().getTime()));

			insertTenant.execute();
			insertTenant.clearParameters();

			// Spouse
			insert(tenant.getSpouse(), Database.TENANTS);

			// Tnant_Invoice
			for (TnantInvoice invoice : tenant.getInvoices()) {
				insert(invoice);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

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
			insert(candidate.getSpouse(), Database.CANDIDATES);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

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
			for (ContInvoice invoice : contractor.getInvoices()) {
				insert(invoice);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insert(Insurance insurance) {
		try {
			System.out.println("Saving Insurance");
			insertInsurance.setInt(1, insurance.getId());
			insertInsurance.setInt(2, insurance.getFk());
			insertInsurance.setTimestamp(3, new Timestamp(insurance.getDateCreated().getTime()));
			insertInsurance.setTimestamp(4, new Timestamp(insurance.getDateModified().getTime()));
			insertInsurance.setDouble(5, insurance.getPayment());
			insertInsurance.setDouble(6, insurance.getBalance());
			insertInsurance.setDouble(7, insurance.getTotalPaid());
			insertInsurance.setDouble(8, insurance.getTotalDue());
			insertInsurance.setTimestamp(9, new Timestamp(insurance.getPaymentDate().getTime()));
			insertInsurance.setTimestamp(10, new Timestamp(insurance.getDueDate().getTime()));

			insertInsurance.execute();
			insertInsurance.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void insert(Issue issue) {
		try {
			System.out.println("Saving Issue");
			insertIssue.setInt(1, issue.getId());
			insertIssue.setInt(2, issue.getFk());
			insertIssue.setInt(3, issue.getFk2());
			insertIssue.setTimestamp(4, new Timestamp(issue.getDateCreated().getTime()));
			insertIssue.setTimestamp(5, new Timestamp(issue.getDateModified().getTime()));
			insertIssue.setString(6, issue.getDescription());

			insertIssue.execute();
			insertIssue.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void insert(Inspection inspection) {
		try {
			System.out.println("Saving Inspection");
			insertInspection.setInt(1, inspection.getId());
			insertInspection.setInt(2, inspection.getFk());
			insertInspection.setInt(3, inspection.getFk2());
			insertInspection.setTimestamp(4, new Timestamp(inspection.getDateCreated().getTime()));
			insertInspection.setTimestamp(5, new Timestamp(inspection.getDateModified().getTime()));
			insertInspection.setString(6, inspection.getDescription());

			insertInspection.execute();
			insertInspection.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void insert(Spouse spouse, int table) {
		try {
			switch(table) {
				case Database.TENANTS:
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
				case Database.CANDIDATES:
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
	public void insert(TnantInvoice invoice) {
		try {
			System.out.println("Saving Invoice(tenant)");
			insertTnantInvoice.setInt(1, invoice.getId());
			insertTnantInvoice.setInt(2, invoice.getFk());
			insertTnantInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
			insertTnantInvoice.setDouble(4, invoice.getPayment());
			insertTnantInvoice.setDouble(5, invoice.getBalance());
			insertTnantInvoice.setDouble(6, invoice.getTotalPaid());
			insertTnantInvoice.setDouble(7, invoice.getTotalDue());
			insertTnantInvoice.setTimestamp(8, new Timestamp(invoice.getPaymentDate().getTime()));
			insertTnantInvoice.setTimestamp(9, new Timestamp(invoice.getDueDate().getTime()));

			insertTnantInvoice.execute();
			insertTnantInvoice.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void insert(ContInvoice invoice) {
		try {
			System.out.println("Saving Invoice (Contractor)");
			insertContInvoice.setInt(1, invoice.getId());
			insertContInvoice.setInt(2, invoice.getFk());
			insertContInvoice.setTimestamp(3, new Timestamp(invoice.getDateCreated().getTime()));
			insertContInvoice.setTimestamp(4, new Timestamp(invoice.getDateModified().getTime()));
			insertContInvoice.setDouble(5, invoice.getPayment());
			insertContInvoice.setDouble(6, invoice.getBalance());
			insertContInvoice.setDouble(7, invoice.getTotalPaid());
			insertContInvoice.setDouble(8, invoice.getTotalDue());
			insertContInvoice.setTimestamp(9, new Timestamp(invoice.getPaymentDate().getTime()));
			insertContInvoice.setTimestamp(10, new Timestamp(invoice.getDueDate().getTime()));

			insertContInvoice.execute();
			insertContInvoice.clearParameters();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Update methods

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
	public void update(Issue issue) {
		try {
			connection.setAutoCommit(false);
			delete(issue);
			insert(issue);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void update(Inspection inspection) {
		try {
			connection.setAutoCommit(false);
			delete(inspection);
			insert(inspection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void update(Spouse spouse, int table) {
		try {
			switch(table) {
				case Database.TENANTS:
					connection.setAutoCommit(false);
					delete(spouse,table);
					insert(spouse,table);
					connection.commit();
					connection.setAutoCommit(true);
					break;
				case Database.CANDIDATES:
					connection.setAutoCommit(false);
					delete(spouse,table);
					insert(spouse,table);
					connection.commit();
					connection.setAutoCommit(true);
					break;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void update(TnantInvoice invoice) {
		try {
			connection.setAutoCommit(false);
			delete(invoice);
			insert(invoice);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void update(ContInvoice invoice) {
		try {
			connection.setAutoCommit(false);
			delete(invoice);
			insert(invoice);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// ---------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------
	// Delete methods
	public void delete(Apartment apartment) {
		try {
			deleteApartment.setInt(1, apartment.getId());
			deleteApartment.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilist deleting apartment");
		}
	}

	public void delete(Tenant tenant) {
		try {
			deleteTenant.setInt(1, tenant.getId());
			deleteTenant.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilist deleting tenant");
		}
	}

	public void delete(Candidate candidate) {
		try {
			deleteTenant.setInt(1, candidate.getId());
			deleteTenant.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilist deleting candidate");
		}
	}

	public void delete(Contractor contractor) {
		try {
			deleteTenant.setInt(1, contractor.getId());
			deleteTenant.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Exception occurred whilist deleting contractor");
		}
	}
	
	public void delete(Insurance insurance) {
		try {
			deleteInsurance.setInt(1, insurance.getId());
			deleteInsurance.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Issue issue) {
		try {
			deleteIssue.setInt(1, issue.getId());
			deleteIssue.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Inspection inspection) {
		try {
			deleteInspection.setInt(1, inspection.getId());
			deleteInspection.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(Spouse spouse, int table) {
		try {
			switch(table){
				case Database.TENANTS:
					deleteTnantSpouse.setInt(1, spouse.getId());
					deleteInspection.execute();
					break;
				case Database.CANDIDATES:
					deleteCandSpouse.setInt(1, spouse.getId());
					deleteInspection.execute();
					break;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(TnantInvoice invoice) {
		try {
			deleteTnantInvoice.setInt(1, invoice.getId());
			deleteTnantInvoice.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void delete(ContInvoice invoice) {
		try {
			deleteContInvoice.setInt(1, invoice.getId());
			deleteContInvoice.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// ---------------------------------------------------------------------------------
	// Other
	private void prepareStatements() {
		try {
			// Inserts
			// Insert Prepared Statements
			insertApartment = connection.prepareStatement(INSERT_INTO_APARTMENTS);
			insertTenant = connection.prepareStatement(INSERT_INTO_TENANTS);
			insertContractor = connection.prepareStatement(INSERT_INTO_CONTRACTORS);
			insertCandidate = connection.prepareStatement(INSERT_INTO_CANDIDATES);
			insertInsurance = connection.prepareStatement(INSERT_INTO_INSURANCES);
			insertIssue = connection.prepareStatement(INSERT_INTO_ISSUES);
			insertInspection = connection.prepareStatement(INSERT_INTO_INSPECTIONS);
			insertTnantSpouse = connection.prepareStatement(INSERT_INTO_TNANT_SPOUSES);
			insertCandSpouse = connection.prepareStatement(INSERT_INTO_CAND_SPOUSES);
			insertTnantInvoice = connection.prepareStatement(INSERT_INTO_TNANT_INVOICES);
			insertContInvoice = connection.prepareStatement(INSERT_INTO_CONT_INVOICES);

			/*
			 * //Updates updateApartment =
			 * connection.prepareStatement(UPDATE_APARTMENTS_APARTMENT_ID); updateTenant =
			 * connection.prepareStatement(UPDATE_TENANTS_TENANT_ID); updateCandidate =
			 * connection.prepareStatement(UPDATE_CANDIDATES_CANDIDATE_ID); updateContractor
			 * = connection.prepareStatement(UPDATE_CONTRACTORS_CONTRACTOR_ID);
			 * updateInsurance =
			 * connection.prepareStatement(UPDATE_INSURANCES_INSURANCE_ID); updateIssue =
			 * connection.prepareStatement(UPDATE_ISSUES_ISSUE_ID); updateInspection =
			 * connection.prepareStatement(UPDATE_INSPECTIONS_INSPECTION_ID); updateSpouse =
			 * connection.prepareStatement(UPDATE_SPOUSES_SPOUSE_ID); updateTnantInvoice =
			 * connection.prepareStatement(UPDATE_TNANT_INVOICES_INVOICE_ID);
			 * updateContInvoice =
			 * connection.prepareStatement(UPDATE_CONT_INVOICES_INVOICE_ID);
			 */

			// Deletes
			deleteApartment = connection.prepareStatement(DELETE_FROM_APARTMENTS);
			deleteTenant = connection.prepareStatement(DELETE_FROM_TENANTS);
			deleteCandidate = connection.prepareStatement(DELETE_FROM_CANDIDATES);
			deleteContractor = connection.prepareStatement(DELETE_FROM_CONTRACTORS);
			deleteInsurance = connection.prepareStatement(DELETE_FROM_INSURANCES);
			deleteIssue = connection.prepareStatement(DELETE_FROM_ISSUES);
			deleteInspection = connection.prepareStatement(DELETE_FROM_INSPECTIONS);
			deleteTnantSpouse = connection.prepareStatement(DELETE_FROM_TNANT_SPOUSES);
			deleteCandSpouse = connection.prepareStatement(DELETE_FROM_CAND_SPOUSES);
			deleteTnantInvoice = connection.prepareStatement(DELETE_FROM_TNANT_INVOICES);
			deleteContInvoice = connection.prepareStatement(DELETE_FROM_CONT_INVOICES);

			preparedStatementsSet = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Something went wrong with preparing statements");
			preparedStatementsSet = false;

		}
	}

	private void closeStatements() {
		try {
			if (preparedStatementsSet) {
				insertApartment.close();
				insertTenant.close();
				insertCandidate.close();
				insertContractor.close();
				insertInsurance.close();
				insertIssue.close();
				insertInspection.close();
				insertTnantSpouse.close();
				insertCandSpouse.close();
				insertTnantInvoice.close();
				insertContInvoice.close();

				/*
				 * //Update Prepared Statements updateApartment.close(); updateTenant.close();
				 * updateCandidate.close(); updateContractor.close(); updateInsurance.close();
				 * updateIssue.close(); updateInspection.close(); updateSpouse.close();
				 * updateTnantInvoice.close(); updateContInvoice.close();
				 */

				// Delete Prepared statements
				deleteApartment.close();
				deleteTenant.close();
				deleteCandidate.close();
				deleteContractor.close();
				deleteInsurance.close();
				deleteIssue.close();
				deleteInspection.close();
				deleteTnantSpouse.close();
				deleteCandSpouse.close();
				deleteTnantInvoice.close();
				deleteContInvoice.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getCredentials() {
		return credentials;
	}

	public void setCredentials(int credentials) {
		this.credentials = credentials;
	}
}
