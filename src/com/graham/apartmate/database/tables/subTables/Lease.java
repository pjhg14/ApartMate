package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.scene.image.Image;

import java.util.List;

public class Lease extends Table {

    //names will be returned by the accompanying tenant class
    //address will be given by the apartment class
    /**
     * The term of the tenant's occupancy, in the form of months from the move in date
     * */
    private int term;

    //move in and out dates will be given from the tenant class

    /**
     * The rent amount the tenant will pay
     * */
    private double rent;

    /**
     * The utility bills the tenant will pay
     * */
    private double utilities;

    /**
     * The amount of the security deposit for each new tenant
     * */
    private double security;

    /**
     * Whether the tenant can have pets
     * */
    private boolean petsAllowed;

    /**
     * Any "house rules" the landlord requires (such as quiet hours or rules for shared living spaces)
     * */
    private List<String> houseRules;

    /**
     * Whether parking spaces are available
     * */
    private boolean parkingSpaces;

    /**
     * Whether subletting or having a sublease or co-tenant is allowed
     * */
    private boolean sublettingAllowed;

    /**
     * How many people may live in the rental unit
     * */
    private int allowedOccupancy;

    /**
     * The reasons the landlord may enter the unit
     * */
    private List<String> entrancePolicies;

    /**
     * The party responsible for paying the legal fees when a dispute arises
     * */
    private String responsibility;

    /**
     * Creates the baseline for a Table with a foreign key
     *
     * @param id Primary Key of the Table
     * @param fk Foreign Key of the Table
     */
    public Lease(int id, int fk) {
        super(id, fk);
    }

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
        return null;
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

    //Getters & Setters
    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getUtilities() {
        return utilities;
    }

    public void setUtilities(double utilities) {
        this.utilities = utilities;
    }

    public double getSecurity() {
        return security;
    }

    public void setSecurity(double security) {
        this.security = security;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public List<String> getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(List<String> houseRules) {
        this.houseRules = houseRules;
    }

    public boolean isParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(boolean parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public boolean isSublettingAllowed() {
        return sublettingAllowed;
    }

    public void setSublettingAllowed(boolean sublettingAllowed) {
        this.sublettingAllowed = sublettingAllowed;
    }

    public int getAllowedOccupancy() {
        return allowedOccupancy;
    }

    public void setAllowedOccupancy(int allowedOccupancy) {
        this.allowedOccupancy = allowedOccupancy;
    }

    public List<String> getEntrancePolicies() {
        return entrancePolicies;
    }

    public void setEntrancePolicies(List<String> entrancePolicies) {
        this.entrancePolicies = entrancePolicies;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
