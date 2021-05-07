package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contract extends Table {

    enum TermType {
        MONTHS,
        YEARS,
        NONE
    }

    //-------------------------------------------------------------
    //Fields///////////////////////////////////////////////////////
    //-------------------------------------------------------------
    /***/
    private final SimpleIntegerProperty term;

    /***/
    private TermType termType;

    /***/
    private final SimpleDoubleProperty payment;

    /***/
    private final ObservableList<String> rules;

    /***/
    private double idSet;
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Constructors/////////////////////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Default constructor
     * */
    public Contract() {
        this(0,0,0,0,0);
    }

    /**
     * Full Constructor
     *
     * @param id id of contract object
     * @param fk Contractor this Contract belongs to (exclusive with fk2)
     * @param fk2 Bill this Contract belongs to (exclusive with fk)
     * @param term amount of months this Contract is in effect (i.e service time, estimated construction time)
     * @param payment amount of payment needed per month
     * */
    public Contract(int id, int fk, int fk2, int term, double payment) {
        super(id, fk, fk2);
        this.term = new SimpleIntegerProperty(term);
        this.payment = new SimpleDoubleProperty(payment);
        rules = FXCollections.observableArrayList();
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //Overrided & Utility Methods//////////////////////////////////
    //-------------------------------------------------------------
    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "generic" name
     */
    @Override
    public String getGenericName() {
        return null;        //TODO
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return null;        //TODO
    }

    /***/
    @Override
    public String getInfoLocation() {
        return null;            //TODO: Contract info
    }

    /***/
    @Override
    public String getAddLocation() {
        return null;
    }

    /***/
    @Override
    public String getEditLocation() {
        return null;
    }

    //-------------------------------------------------------------
    //-------------------------------------------------------------

    //-------------------------------------------------------------
    //General Getters & Setters////////////////////////////////////
    //-------------------------------------------------------------
    /***/
    public int getTerm() {
        return term.get();
    }

    /***/
    public SimpleIntegerProperty termProperty() {
        return term;
    }

    /***/
    public void setTerm(int term) {
        this.term.set(term);
    }

    /***/
    public double getPayment() {
        return payment.get();
    }

    /***/
    public SimpleDoubleProperty paymentProperty() {
        return payment;
    }

    /***/
    public void setPayment(double payment) {
        this.payment.set(payment);
    }

    /***/
    public ObservableList<String> getRules() {
        return rules;
    }

    /***/
    public double getIdSet() {
        return idSet;
    }

    /***/
    public void setIdSet(double idSet) {
        this.idSet = idSet;
    }
    //-------------------------------------------------------------
    //-------------------------------------------------------------
}
