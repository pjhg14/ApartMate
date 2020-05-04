package com.apartmate.database.tables.mainTables;

import com.apartmate.main.Main;

import java.io.Serializable;
import java.util.Date;

/**
 * Table abstract class
 * <p>
 * Provides the basis for all table mirrors
 *
 * @author Paul Graham Jr (pjhg14@gmail.com)
 * @version {@value Main#VERSION}
 * @since Can we call this an alpha? (0.1)
 */
//TODO: Replace all mentions of depreciated Date classes with java.time.LocalDate
//TODO: Make all constructors succinct
public abstract class Table implements Serializable, Comparable<Table> {

	//----------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////////
	//----------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 2L;

	/**
	 * Primary Key of Table
	 * */
	private int id;

	/**
	 * First foreign key of Table
	 * <p>
	 * Defaults to 0 to represent no foreign key
	 * */
	private int fk;

	/**
	 * Second foreign key of Table
	 * <p>
	 * Defaults to 0 to represent no foreign key
	 * */
	private int fk2;

	/**
	 * Date Table was created
	 * */
	private Date dateCreated;

	/**
	 * Date Table was modified
	 * */
	private Date dateModified;

	//Status fields
	/**
	 * Whether a Table was edited
	 * <p>
	 * True if a field was changed*/
	private boolean edited;

	/**
	 * Whether a Table is considered a dummy Table
	 * <p>
	 * Dummy Tables are used as placeholders for various lists in the program
	 * </p>
	 * True if so, False if not
	 * */
	private boolean dummy;
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	//Constructors////////////////////////////////////////////////////
	//----------------------------------------------------------------

	/**
	 * Default constructor
	 * Creates the baseline for a dummy Table
	 * */
	public Table() {
		this(0,0,0);
		dummy = true;
	}

	/**
	 * Creates the baseline for a Table /w no foreign keys
	 * @param id Primary Key of the Table
	 * */
	public Table(int id) {
		this(id,0,0);
	}

	/**
	 * Creates the baseline for a Table with a foreign key
	 * @param id Primary Key of the Table
	 * @param fk Foreign Key of the Table
	 * */
	public Table(int id, int fk) {
		this(id, fk,0);
	}

	/**
	 * Creates the baseline for a Table with two foreign keys
	 * @param id Primary Key of the Table
	 * @param fk First foreign key of the Table
	 * @param fk2 Second foreign key of the Table
	 * */
	public Table(int id, int fk, int fk2) {
		this.id = id;
		this.fk = fk;
		this.fk2 = fk2;

		dateCreated = new Date();
		dateModified = new Date();
		dateCreated.setTime(System.currentTimeMillis());
		dateModified.setTime(System.currentTimeMillis());
		dummy = false;
		edited = false;

		if (Main.DEBUG)
			debug();
	}
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	//Methods/////////////////////////////////////////////////////////
	//----------------------------------------------------------------
	/**
	 * Debug method...
	 * */
	private void debug() {
		if (!dummy) {
			System.out.println("New Table class created w/ id: " + id);
		} else {
			System.out.println("Dummy Table class created, be sure to delete later");
		}
	}
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	//Method Overrides////////////////////////////////////////////////
	//----------------------------------------------------------------
	/**
	 * Overrided Comparable compareTo method:
	 * Used to provide Table context to Comparable methods
	 * */
	@Override
	public int compareTo(Table other) {
		if (this.getId() == other.getId()) {
			if (this.getFk() == other.getFk()) {
				if (this.getFk2() == other.getFk2()) {
					return 0;
				} else if (this.getFk2() > other.getFk2()) {
					return 1;
				} else {
					return -1;
				}
			} else if (this.getFk() > other.getFk()) {
				return 1;
			} else {
				return -1;
			}
		} else if (this.getId() > other.getId()) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Overrided Object equals method:
	 * Provides Table context of equivalence and used methods
	 * @return true if equals, false if not
	 * */
	@Override
	public boolean equals(Object o) {

		// If the Table is compared with itself then return true
		if (this == o) return true;

		//If o is null or the class of o does not match Table then returns false
		if (!(o instanceof Table)) return false;

		//If ids match, return true; false if otherwise
		Table table = (Table) o;
		return id == table.id;
	}

	/**
	 * Overrided Object hashCode method:
	 * Allows Table to be properly searched in a hashTable
	 * */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + id;

		return result;
	}

	/**
	 * Overrided Object toString method:
	 * Returns relevant Table data as a single String
	 * */
	@Override
	public String toString() {
		return "id= " + id + ", created on: " + dateCreated;
	}
	//----------------------------------------------------------------

	//----------------------------------------------------------------
	// General getters and setters////////////////////////////////////
	//----------------------------------------------------------------
	/**
	 * Getter:
	 * Gives the primary key
	 * @return id
	 * */
	public int getId() {
		return id;
	}

	/**
	 * Setter:
	 * Sets the primary key
	 * @param id ...
	 * */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter:
	 * Gives the foreign key
	 * @return fk
	 * */
	public int getFk() {
		return fk;
	}

	/**
	 * Setter:
	 * Sets the foreign key
	 * @param fk ...
	 * */
	public void setFk(int fk) {
		this.fk = fk;
	}

	/**
	 * Getter:
	 * Gives the second foreign key
	 * @return fk2
	 * */
	public int getFk2() {
		return fk2;
	}

	/**
	 * Setter:
	 * Sets the second foreign key
	 * @param fk2 ...
	 * */
	public void setFk2(int fk2) {
		this.fk2 = fk2;
	}

	/**
	 * Getter:
	 * Gives the date and time the Table was created
	 * @return dateCreated
	 * */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Setter:
	 * Sets the date and time the Table was created
	 * <p><i>Don't know if this is needed... </i></p>
	 * @param dateCreated ...
	 * */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Getter:
	 * Gives the date and time the Table was last modified
	 * @return dateModified
	 * */
	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * Setter:
	 * Sets the date and time the Table was last modified
	 * @param dateModified new date the Table was modified
	 * */
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	/**
	 * Getter:
	 * Gives whether or not this Table was edited
	 * @return edited
	 * */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Setter:
	 * Sets whether or not this Table was edited
	 * @param edited new edited state
	 * */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Getter:
	 * Gives whether or not this Table is a dummy Table
	 * @return dummy
	 * */
	public boolean isDummy() {
		return dummy;
	}
	//----------------------------------------------------------------
}
