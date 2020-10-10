package com.graham.apartmate.database.tables.mainTables;

import java.time.LocalDate;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.subTables.*;
import com.graham.apartmate.main.Main;
import com.graham.apartmate.ui.libraries.FXMLLocation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Tenant object
 * <p>
 * Records the information of a tenant of a specific Apartment
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
public class Tenant extends Candidate {

	/**
	 * Serializable Long
	 * */
	private static final long serialVersionUID = 1L;

	//------------------------------------------------------------
	// Mandatory fields///////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Tenant's move-in date
	 * */
	private final SimpleObjectProperty<LocalDate> movInDate; // tenant's move in date
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// Optional/uninitialized fields//////////////////////////////
	//------------------------------------------------------------
	/**
	 * True if the Tenant is slated for eviction/evicted
	 * */
	private final SimpleBooleanProperty slatedForEviction;

	/**
	 * Eviction reason
	 * */
	private final SimpleStringProperty evictReason;

	/***/
	private final SimpleBooleanProperty securitySent;

	/**
	 * Date a Tenant has moved out of the Apartment
	 * */
	private final SimpleObjectProperty<LocalDate> movOutDate;

	/**
	 * Address Tenant has moved to
	 * */
	private final SimpleStringProperty addressMoved;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// Tenant sub-tables//////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Tenant's Account
	 * */
	private final Account account;

	/**
	 * Tenant's lease agreement
	 * */
	private final Lease lease;
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Constructors////////////////////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Default constructor:
	 * */
	public Tenant() {
		this(0,0,"","","","",LocalDate.MIN,-1,"",-1,
				LocalDate.MIN, null, null, new Account(), new Lease());
	}

	/**
	 * Candidate conversion constructor
	 * */
	public Tenant(Candidate candidate, int aptId, LocalDate movInDate, Account initialAccount, Lease initialLease) {
		this(candidate.getId(),
				aptId,
				candidate.getFirstName(),
				candidate.getLastName(),
				candidate.getPhone(),
				candidate.getEmail(),
				candidate.getDateOfBirth(),
				candidate.getAnnualIncome(),
				candidate.getSsn(),
				candidate.getNumChildren(),
				movInDate,
				candidate.getEContact1(),
				candidate.getEContact2(),
				initialAccount,
				initialLease
		);
	}

	// Constructor
	/**
	 * Creates a Tenant object
	 * @param id Primary Key of Tenant
	 * @param fk Foreign Key to Apartment (0 if moved out)
	 * @param firstName Tenant's first name
	 * @param lastName Tenant's last name
	 * @param phone Tenant's phone #
	 * @param email Tenant's email
	 * @param dateOfBirth Tenant's date of birth
	 * @param annualIncome Tenant's annual income
	 * @param SSN Tenant's ID #
	 * @param numChildren Tenant's # of children
	 * @param movInDate Tenant's move in date
	 * */
	public Tenant(int id, int fk, String firstName, String lastName, String phone, String email, LocalDate dateOfBirth,
				  int annualIncome, String SSN, int numChildren, LocalDate movInDate,
				  Contact contact1, Contact contact2, Account initialAccount, Lease initialLease) {
		super(id, fk, firstName,lastName,phone,email,SSN,dateOfBirth,annualIncome,numChildren, contact1,contact2);
		super.setAccepted(true);

		this.movInDate = new SimpleObjectProperty<>(movInDate);
		movOutDate = new SimpleObjectProperty<>(LocalDate.MIN);
		slatedForEviction = new SimpleBooleanProperty(false);
		evictReason = new SimpleStringProperty();
		addressMoved = new SimpleStringProperty();
		securitySent = new SimpleBooleanProperty(false);

		this.lease = initialLease;
		this.account = initialAccount;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	//Overrided & Utility Methods/////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Overrided toString() method:
	 * <p>
	 * Returns Tenant's Id and name
	 * @return id, last name, first name
	 * */
	@Override
	public String toString() {
		return String.format("Tenant %d: %s, %s", super.getId(), super.getLastName(), super.getFirstName());
	}

	/***/
	@Override
	public DBTables getTableType() {
		return DBTables.TENANTS;
	}

	/***/
	@Override
	public String getInfoLocation() {
		return FXMLLocation.TNANTINFO.getLocation();
	}

	/***/
	@Override
	public String getAddLocation() {
		return FXMLLocation.TNANTADD.getLocation();
	}

	/***/
	@Override
	public String getEditLocation() {
		return FXMLLocation.TNANTEDIT.getLocation();
	}

	/***/
	public double getMonthlyRent() {
		return lease.getRent();
	}

	/***/
	public double getBalance() {
		return account.getBalance();
	}
	//------------------------------------------------------------
	//------------------------------------------------------------

	//------------------------------------------------------------
	// General getters and setters////////////////////////////////
	//------------------------------------------------------------
	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's move in date
	 * @return move in date
	 * */
	public LocalDate getMovInDate() {
		return movInDate.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's move in date
	 * @param movInDate New move in date
	 * */
	public void setMovInDate(LocalDate movInDate) {
		this.movInDate.set(movInDate);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets move in date field property
	 * @return move in date property
	 * */
	public SimpleObjectProperty<LocalDate> movInDateProperty() {
		return movInDate;
	}

	/**
	 * Getter:
	 * Gets whether or not the security deposit was sent
	 * */
	public boolean isSecuritySent() {
		return securitySent.get();
	}

	/**
	 * Getter:
	 * Gets the security deposit sent field property
	 * @return security sent property
	 * */
	public SimpleBooleanProperty securitySentProperty() {
		return securitySent;
	}

	/**
	 * Setter:
	 * Sets whether or not the Tenant's security deposit was sent
	 * @param securitySent new state of security deposit sent
	 * */
	public void setSecuritySent(boolean securitySent) {
		this.securitySent.set(securitySent);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's move out date
	 * @return move out date
	 * */
	public LocalDate getMovOutDate() {
		return movOutDate.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets Tenant's move out date
	 * @param movOutDate New move out date
	 * */
	public void setMovOutDate(LocalDate movOutDate) {
		this.movOutDate.set(movOutDate);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets move out date field property
	 * @return move out date property
	 * */
	public SimpleObjectProperty<LocalDate> movOutDateProperty() {
		return movOutDate;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets whether the Tenant is evicted
	 * @return annual income
	 * */
	public boolean isEvicted() {
		return slatedForEviction.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets whether the Tenant was evicted or not
	 * @param isEvicted New value
	 * */
	public void setEvicted(boolean isEvicted) {
		this.slatedForEviction.set(isEvicted);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets slated for eviction field property
	 * @return slated for eviction property
	 * */
	public SimpleBooleanProperty evictionProperty() {
		return slatedForEviction;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the reason for the Tenant's eviction
	 * @return eviction reason
	 * */
	public String getEvictReason() {
		return evictReason.get();
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the reason for the Tenant's eviction
	 * @param evictReason New text
	 * */
	public void setEvictReason(String evictReason) {
		this.evictReason.set(evictReason);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets eviction reason field property
	 * @return eviction reason property
	 * */
	public SimpleStringProperty evictReasonProperty() {
		return evictReason;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the address the Tenant moved to for security deposit mailing
	 * @return address moved to
	 * */
	public String getAddressMoved() {
		return addressMoved.get();
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the address moved field property
	 * @return address moved property
	 * */
	public SimpleStringProperty addressMovedProperty() {
		return addressMoved;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the address the Tenant moved to for security deposit mailing
	 * @param addressMoved the address the Tenant has moved to
	 * */
	public void setAddressMoved(String addressMoved) {
		this.addressMoved.set(addressMoved);
	}

	/**
	 * Getter:
	 * <p>
	 * Gets Tenant's Invoice list
	 * @return invoice list
	 * */
	public Account getAccount() {
		return account;
	}

	/**
	 * Getter:
	 * <p>
	 * Gets the Tenant's current lease
	 * @return lease
	 * */
	public Lease getLease() {
		return lease;
	}
	//------------------------------------------------------------
	//------------------------------------------------------------
}
