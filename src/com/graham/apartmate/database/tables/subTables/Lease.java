package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

public class Lease extends Table {

    //-------------------------------------------------------------
    //Fields///////////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * The term of the tenant's occupancy, in the form of months from the move in date
     * */
    private final SimpleIntegerProperty term;

    /**
     * The rent amount the tenant will pay
     * */
    private final SimpleDoubleProperty rent;

    /**
     * The utility bills the tenant will pay
     * */
    private final SimpleDoubleProperty utilities;

    /**
     * The amount of the security deposit the tenant paid
     * */
    private final SimpleDoubleProperty security;

    /**
     * "List"(Map) of restrictions/ allotments the Tenant must abide by/ entitled to, such as:
     * <p>
     * Whether subletting or having a sublease or co-tenant is allowed
     * <p>
     * Whether parking spaces are available
     * <p>
     * Whether the tenant can have pets
     * <p>
     * etc.
     * */
    private final ObservableMap<String,String> residencyRules;

    /**
     * The reasons the landlord may enter the unit
     * */
    private ObservableList<String> entrancePolicies;

    /**
     * Whether or not the id for this lease has been set
     * */
    private boolean idSet;

    /**
     * List of all previous leases related to a particular Tenant
     * */
    private ObservableList<Lease> invoicedLeases;
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Constructors/////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Default constructor
     */
    public Lease() {
        this(0,0,0,0,0,0);
        idSet = false;
    }

    /**
     * Constructor w/o id
     *
     * @param fk Foreign Key of the Table
     * @param term term of residency
     * @param rent amount of monthly rent
     * @param utilities amount of utilities Tenant pays
     * @param security amount if Tenant's security deposit
     */
    public Lease(int fk, int term, double rent, double utilities, double security) {
        this(0, fk, term, rent, utilities, security);
        idSet = false;
    }

    /**
     * Full Constructor
     *
     * @param id Primary Key of the Table
     * @param fk Foreign Key of the Table
     * @param term term of residency
     * @param rent amount of monthly rent
     * @param utilities amount of utilities Tenant pays
     * @param security amount if Tenant's security deposit
     */
    public Lease(int id, int fk, int term, double rent, double utilities, double security) {
        super(id, fk);
        idSet = true;

        this.term = new SimpleIntegerProperty(term);
        this.rent = new SimpleDoubleProperty(rent);
        this.utilities = new SimpleDoubleProperty(utilities);
        this.security = new SimpleDoubleProperty(security);

        residencyRules = FXCollections.observableHashMap();
        entrancePolicies = FXCollections.observableArrayList();
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Overrided & Utility Methods//////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "unique" name
     */
    @Override
    public String getGenericName() {
        return null;
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return DBTables.LEASE;
    }

    /**
     * Returns the image related to a particular instance of a Table
     *
     * @return Table image
     */
    @Override
    public Image getImage() {
        return null;
    }

    /**
     * Adds/Changes a(n) new/existing rule
     * @return <code>true</code> if rule was added successfully, <code>false</code> if not
     * */
    public boolean addRule(String rule,String verdict) {
        return residencyRules.put(rule, verdict) != null;
    }

    /**
     * Removes an existing rule
     * <code>true</code> if file was removed successfully, <code>false</code> if not
     * */
    public boolean removeRule(String rule) {
        return residencyRules.remove(rule) != null;
    }

    /**
     * Adds an entrance policy
     * @return <code>true</code> if policy was added successfully, <code>false</code> if not
     * */
    public boolean addPolicy(String policy) {
        return entrancePolicies.add(policy);
    }

    /**
     * Removes an entrance policy
     * @return <code>true</code> if policy was removed successfully, <code>false</code> if not
     * */
    public boolean removePolicy(String policy) {
        return entrancePolicies.remove(policy);
    }

    /***/
    public void renew(Lease newLease) {
        invoicedLeases.add(this);

        this.setId(newLease.getId());
        this.setFk(newLease.getFk());

        idSet = true;

        this.term.set(newLease.getTerm());
        this.rent.set(newLease.getRent());
        this.utilities.set(newLease.getUtilities());
        this.security.set(newLease.getSecurity());

        residencyRules.clear();
        residencyRules.putAll(newLease.getResidencyRules());

        entrancePolicies.clear();
        entrancePolicies.addAll(newLease.entrancePolicies);
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //General Getters & Setters////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Getter
     * <p>
     * Gets the term (amount of months) of the Lease
     * @return Lease term
     * */
    public int getTerm() {
        return term.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the term (amount of months) of the Lease
     * @param term new term
     * */
    public void setTerm(int term) {
        this.term.set(term);
    }

    /**
     * Getter:
     * <p>
     * Gets the term field property
     * @return term property
     * */
    public SimpleIntegerProperty termProperty() {
        return term;
    }

    /**
     * Getter
     * <p>
     * Gets the rent owed each month
     * @return rent
     * */
    public double getRent() {
        return rent.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the amount of rent due monthly
     * @param rent new monthly rent
     * */
    public void setRent(double rent) {
        this.rent.set(rent);
    }

    /**
     * Getter:
     * <p>
     * Gets the rent field property
     * @return rent property
     * */
    public SimpleDoubleProperty rentProperty() {
        return rent;
    }

    /**
     * Getter
     * <p>
     * Gets amount of utility bills Tenant will pay
     * @return utility amount
     * */
    public double getUtilities() {
        return utilities.get();
    }

    /**
     * Setter:
     * <p>
     * Sets amount of utility bills Tenant will pay
     * @param utilities new amount of utility pay
     * */
    public void setUtilities(double utilities) {
        this.utilities.set(utilities);
    }

    /**
     * Getter:
     * <p>
     * Gets the utilities field property
     * @return utilities property
     * */
    public SimpleDoubleProperty utilitiesProperty() {
        return utilities;
    }

    /**
     * Getter
     * <p>
     * Gets the amount the Tenant's security check
     * @return security amount
     * */
    public double getSecurity() {
        return security.get();
    }

    /**
     * Setter:
     * <p>
     * Sets the amount the Tenant's security check
     * @param security new security amount
     * */
    public void setSecurity(double security) {
        this.security.set(security);
    }

    /**
     * Getter:
     * <p>
     * Gets the security deposit field property
     * @return security deposit property
     * */
    public SimpleDoubleProperty securityProperty() {
        return security;
    }

    /**
     * Getter:
     * <p>
     * Gets the residency rules
     * @return residency rules
     * */
    public ObservableMap<String, String> getResidencyRules() {
        return FXCollections.unmodifiableObservableMap(residencyRules);
    }

    /**
     * Getter
     * <p>
     * Gets the list of entrances policies
     * @return list of entrance policies
     * */
    public ObservableList<String> getEntrancePolicies() {
        return FXCollections.unmodifiableObservableList(entrancePolicies);
    }

    /**
     * Setter
     * <p>
     * Sets the list of entrances policies
     * @param entrancePolicies new list of entrances policies
     * */
    public void setEntrancePolicies(ObservableList<String> entrancePolicies) {
        this.entrancePolicies = entrancePolicies;
    }

    /**
     * Getter
     * <p>
     * Gets whether or not the id has been set
     * @return <code>true</code> if set, <code>false</code> if not
     * */
    public boolean isIdSet() {
        return idSet;
    }

    /**
     * Setter:
     * <p>
     * Sets whether or not the id has been set
     * @param idSet new set state
     * */
    public void setIdSet(boolean idSet) {
        this.idSet = idSet;
    }

    /**
     * Getter:
     * <p>
     * Gets the list of invoiced leases
     * @return list of leases
     * */
    public ObservableList<Lease> getInvoicedLeases() {
        return FXCollections.unmodifiableObservableList(invoicedLeases);
    }

    /**
     * Setter:
     * <p>
     * Sets the list of invoiced leases
     * @param invoicedLeases new list of invoiced leases
     * */
    public void setInvoicedLeases(ObservableList<Lease> invoicedLeases) {
        this.invoicedLeases = invoicedLeases;
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------
}
