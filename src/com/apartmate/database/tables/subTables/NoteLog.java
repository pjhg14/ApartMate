package com.apartmate.database.tables.subTables;

import com.apartmate.database.tables.mainTables.Table;

import java.util.Date;

public class NoteLog extends Table {

    /***/
    private static final long serialVersionUID = 1L;

    /***/
    private String log;

    /***/
    private Date logDate;

    /***/
    public NoteLog() {
        this(0,0,"");
    }

    /***/
    public NoteLog(int id, int fk, String log) {
        super(id, fk);
        this.log = log;
    }

    /***/
    @Override
    public String toString() {
        return "Log Date: " + logDate;
    }

    // *************************************************************
    // General getters and setters
    /***/
    public String getLog() {
        return log;
    }

    /***/
    public void setLog(String log) {
        this.log = log;
    }

    /***/
    public Date getLogDate() {
        return logDate;
    }

    /***/
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
    // *************************************************************
}
