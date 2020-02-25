package com.apartmate.database.tables.subTables;

import com.apartmate.database.tables.mainTables.Table;

/**
 * Inspection object
 * <p>
 * Records the description of an inspection as well as the dates it was done and
 * added to database
 * 
 * @since Can we call this an alpha? (0.1)
 * @version MileStone 4 (0.4)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Inspection extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public Inspection() {
		super();
		description = "";
	}

	public Inspection(int id, int fk, int fk2, String description) {
		super(id, fk, fk2);
		this.description = description;
	}

	// *************************************************************
	// General getters and setters
	public void VVVVVV() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	// *************************************************************
}
