package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;

/**
 * @since Capstone (0.8)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr
 */
//TODO: Javadoc's for every method
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
