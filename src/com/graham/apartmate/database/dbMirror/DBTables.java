package com.graham.apartmate.database.dbMirror;

public enum DBTables {
    /**
     * Apartment Table pointer
     * */
    APARTMENTS,

    /**
     * Tenant Table pointer
     * Also used for Spouses & Invoices
     * */
    TENANTS,

    /**
     * Candidate Table pointer
     * Also used for Spouses
     * */
    CANDIDATES,

    /**
     * Contractor Table pointer
     * Also used for Invoices
     * */
    CONTRACTORS,

    /**
     * Insurance Table pointer
     * Here for Invoices
     * */
    INSURANCES,

    /**
     * Bill Table pointer
     * Also used for Invoices
     * */
    BILLS,

    /**
     * Inspection Table pointer
     * */
    INSPECTIONS,

    /**
     * Issue Table pointer
     * */
    ISSUES,

    /**
     * Invalid Table pointer
     * */
    NONE
}
