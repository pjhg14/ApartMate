package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;

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

    //--------------------------------------------------------------------
    //Fields//////////////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Serialization long
     * */
    private static final long serialVersionUID = 1L;

    /***/
    private final DBTables tableType;

    /**
     * Log Information
     * */
    private final SimpleStringProperty log;

    /**
     * Date log was recorded
     * */
    private final SimpleObjectProperty<LocalDate> logDate;

    /**
     * Whether or not the log was resolved in any way
     * */
    private final SimpleBooleanProperty resolved;
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Constructors////////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Default constructor
     * */
    public NoteLog(boolean isIssue) {
        this(0,0,"",LocalDate.now() ,isIssue);
    }

    /**
     * Dummy NoteLog Constructor
     * */
    public NoteLog(String dummy) {
        this(true);
        if (dummy.equals(DUMMY_TABLE)) {
            super.setDummy(true);
        }
    }

    /**
     * Full constructor
     * */
    public NoteLog(int id, int fk, String log, LocalDate logDate, boolean isIssue) {
        super(id, fk);
        this.log = new SimpleStringProperty(log);
        this.logDate = new SimpleObjectProperty<>(logDate);
        resolved = new SimpleBooleanProperty(false);

        if (isIssue) {
            tableType = DBTables.ISSUES;
        } else {
            tableType = DBTables.INSPECTIONS;
        }
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Overrided & Utility Methods/////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Overrided toString() method
     * Returns log date
     * @return log date
     * */
    @Override
    public String toString() {
        return "Log Date: " + logDate;
    }

    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "unique" name
     */
    @Override
    public String getGenericName() {
        return "";
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return tableType;
    }

    /**
     * Returns the image related to a particular instance of a Table
     *
     * @return Table image
     */
    @Override
    public Image getImage() {
        return new Image("");
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //General Getters and setters/////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Getter:
     * <p>
     * Gets log information
     * @return log
     * */
    public String getLog() {
        return log.get();
    }

    /**
     * Setter:
     * <p>
     * Sets log information
     * @param log New log
     * */
    public void setLog(String log) {
        this.log.set(log);
    }

    /**
     * Getter:
     * <p>
     * Gets log field property
     * @return log property
     * */
    public SimpleStringProperty logProperty() {
        return log;
    }

    /**
     * Getter:
     * <p>
     * Gets date log was recorded
     * @return log record date
     * */
    public LocalDate getLogDate() {
        return logDate.get();
    }

    /**
     * Setter:
     * <p>
     * Sets date log was recorded
     * @param logDate New log date
     * */
    public void setLogDate(LocalDate logDate) {
        this.logDate.set(logDate);
    }

    /**
     * Getter:
     * <p>
     * Gets log date field property
     * @return log date property
     * */
    public SimpleObjectProperty<LocalDate> logDateProperty() {
        return logDate;
    }

    /**
     * Getter:
     * Gets whether any issues related to this particular log is resolved
     * @return true if resolved, false if not
     * */
    public boolean isResolved() {
        return resolved.get();
    }

    /**
     * Getter:
     * Gets the reserved field property
     * @return reserved property
     * */
    public SimpleBooleanProperty resolvedProperty() {
        return resolved;
    }

    /**
     * Setter:
     * Sets whether an issue related to a particular log is resolved
     * @param resolved true if done, false if not
     * */
    public void setResolved(boolean resolved) {
        this.resolved.set(resolved);
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
}
