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

    /***/
    ROOMMATE,

    /***/
    PERSONALCONTACT,

    /***/
    ACCOUNT,

    /***/
    LEASE,

    /***/
    ROOM,

    /***/
    TRANSACTION,

    /**
     * Invalid Table pointer
     * */
    INVALID
}
