package com.apartmate.database.tables.subTables;

import com.apartmate.database.tables.mainTables.Table;

/**
 * @since Capstone (0.8)
 * @version Capstone (0.8)
 * @author Paul Graham Jr
 */
public class Room extends Table {

	/**
	 * Serializable element
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds the id pointer of a respective tenant (vacant if 0)
	 */
	private int occupancy;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(int id, int fk) {
		super(id, fk);
		// TODO Auto-generated constructor stub
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

}
