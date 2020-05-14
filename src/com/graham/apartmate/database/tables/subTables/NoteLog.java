package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;

import java.util.Date;

/**
 * Logging object for Notes
 * <p>
 * Records Issue/Inspection information for a particular Apartment/Tenant
 * <p>
 * Replaces individual Classes Issue and Inspection
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Capstone (0.1)
 */
public class NoteLog extends Table {

    /**
     * Serialization long
     * */
    private static final long serialVersionUID = 1L;

    /**
     * Log Information
     * */
    private String log;

    /**
     * Date log was recorded
     * */
    private Date logDate;

    /**
     * Default constructor
     * */
    public NoteLog() {
        this(0,0,"");
    }

    /**
     * Full constructor
     * */
    public NoteLog(int id, int fk, String log) {
        super(id, fk);
        this.log = log;
    }

    /**
     * Overrided toString() method
     * Returns log date
     * @return log date
     * */
    @Override
    public String toString() {
        return "Log Date: " + logDate;
    }

    // *************************************************************
    // General getters and setters
    /**
     * Getter:
     * <p>
     * Gets log information
     * @return log
     * */
    public String getLog() {
        return log;
    }

    /**
     * Setter:
     * <p>
     * Sets log information
     * @param log New log
     * */
    public void setLog(String log) {
        this.log = log;
    }

    /**
     * Getter:
     * <p>
     * Gets date log was recorded
     * @return log record date
     * */
    public Date getLogDate() {
        return logDate;
    }

    /**
     * Setter:
     * <p>
     * Sets date log was recorded
     * @param logDate New log date
     * */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
    // *************************************************************
}
