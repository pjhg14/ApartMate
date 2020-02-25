package com.apartmate.database.tables.mainTables;

import java.io.Serializable;
import java.util.Date;

/**
 * Table abstract class
 * <p>
 * Provides the basis for all table mirrors
 * 
 * @see tables
 * @see sub_tables
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public abstract class Table implements Serializable, Comparable<Table> {

	private int id;
	private int fk;
	private int fk2;
	private Date dateCreated;
	private Date dateModified;

	private boolean edited;
	private boolean isPopulated;

	private static final long serialVersionUID = 2L;
	// private boolean isNew;

	public Table() {
		id = 0;
		fk = 0;
		fk2 = 0;

		dateCreated = new Date();
		dateModified = new Date();
		dateCreated.setTime(System.currentTimeMillis());
		dateModified.setTime(System.currentTimeMillis());
		isPopulated = false;
		// debug();
	}

	public Table(int id) {
		this.id = id;
		this.fk = 0;
		this.fk2 = 0;

		dateCreated = new Date();
		dateModified = new Date();
		dateCreated.setTime(System.currentTimeMillis());
		dateModified.setTime(System.currentTimeMillis());
		isPopulated = true;
		// debug();
	}

	public Table(int id, int fk) {
		this.id = id;
		this.fk = fk;
		fk2 = 0;

		dateCreated = new Date();
		dateModified = new Date();
		dateCreated.setTime(System.currentTimeMillis());
		dateModified.setTime(System.currentTimeMillis());
		isPopulated = true;
		// debug();
	}

	public Table(int id, int fk, int fk2) {
		this.id = id;
		this.fk = fk;
		this.fk2 = fk2;

		dateCreated = new Date();
		dateModified = new Date();
		dateCreated.setTime(System.currentTimeMillis());
		dateModified.setTime(System.currentTimeMillis());
		isPopulated = true;
		// debug();
	}

	@SuppressWarnings("unused")
	private void debug() {
		if (isPopulated) {
			System.out.println("New Table class created w/ id: " + id);
		} else {
			System.out.println("Dummy Table class created, be sure to delete later");
		}
	}

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

	@Override
	public String toString() {
		return "id= " + id + ", created on: " + dateCreated;
	}

	// *************************************************************
	// General getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFk() {
		return fk;
	}

	public void setFk(int fk) {
		this.fk = fk;
	}

	public int getFk2() {
		return fk2;
	}

	public void setFk2(int fk2) {
		this.fk2 = fk2;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public boolean isPopulated() {
		return isPopulated;
	}

	public void setPopulated(boolean isPopulated) {
		this.isPopulated = isPopulated;
	}
	// *************************************************************
}
