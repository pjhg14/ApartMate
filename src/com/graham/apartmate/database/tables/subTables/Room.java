package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.main.Main;
import javafx.scene.image.Image;

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

	/**
	 * Dummy Room Constructor
	 * */
	public Room(String dummy) {
		this();
		if (dummy.equals(DUMMY_TABLE)) {
			super.setDummy(true);
		}
	}

	public Room(int id, int fk) {
		super(id, fk);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 *
	 * @return Table's "unique" name
	 */
	@Override
	public String getGenericName() {
		return "Insert room name here, i.e: 2A, 1C, etc.";
	}

	/**
	 * Gets the type of Table in question
	 *
	 * @return table type
	 */
	@Override
	public DBTables getTableType() {
		return DBTables.NONE;
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

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

}
