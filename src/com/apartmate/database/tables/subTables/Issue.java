package com.apartmate.database.tables.subTables;

import com.apartmate.database.tables.mainTables.Table;

/**
 * Issue object
 * <p>
 * Records an Issue pertaining to a specific Apartment reported by or pertaining
 * to a specific Tenant
 * 
 * @since Can we call this an alpha? (0.1)
 * @version MileStone 4 (0.4)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public class Issue extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public Issue() {
		super();
		description = "";
	}

	public Issue(int id, int fk, int fk2, String description) {
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
