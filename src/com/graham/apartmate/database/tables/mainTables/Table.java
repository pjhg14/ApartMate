package com.graham.apartmate.database.tables.mainTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.main.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public abstract class Table implements Serializable, Comparable<Table> {

	//----------------------------------------------------------------
	//Fields//////////////////////////////////////////////////////////
	//----------------------------------------------------------------
	/**
	 * Serialization long
	 * */
	private static final long serialVersionUID = 2L;

	/**
	 * Image of this Table instance
	 * */
	protected Image image;

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
	 * Second foreign key of Table
	 * <p>
	 * Defaults to 0 to represent no foreign key
	 * */
	private int fk3;

	/**
	 * Second foreign key of Table
	 * <p>
	 * Defaults to 0 to represent no foreign key
	 * */
	private int fk4;

	/**
	 * Date Table was created
	 * */
	private final SimpleObjectProperty<LocalDateTime> dateCreated;

	/**
	 * Date Table was modified
	 * */
	private final SimpleObjectProperty<LocalDateTime> dateModified;

	//Status fields
	/**
	 * Whether a Table was edited
	 * <p>
	 * True if a field was changed
	 * */
	private boolean edited;

	/**
	 * Whether a Table is considered a dummy Table
	 * <p>
	 * Dummy Tables are used as placeholders for various lists in the program
	 * </p>
	 * True if so, False if not
	 * */
	private boolean dummy;

	public static final String DUMMY_TABLE = "DUMMY";
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
	}

	/**
	 * Creates the baseline for a Table /w no foreign keys
	 * @param id Primary Key of the Table
	 * */
	public Table(int id) {
		this(id,0,0,0,0);
	}

	/**
	 * Creates the baseline for a Table with a foreign key
	 * @param id Primary Key of the Table
	 * @param fk Foreign Key of the Table
	 * */
	public Table(int id, int fk) {
		this(id, fk,0,0,0);
	}

	/**
	 * Creates the baseline for a Table with two foreign keys
	 * @param id Primary Key of the Table
	 * @param fk First foreign key of the Table
	 * @param fk2 Second foreign key of the Table
	 * */
	public Table(int id, int fk, int fk2) {
		this(id, fk, fk2,0,0);
	}

	/**
	 * Creates the baseline for a Table with three foreign keys
	 * @param id Primary Key of the Table
	 * @param fk First foreign key of the Table
	 * @param fk2 Second foreign key of the Table
	 * @param fk3 Third foreign key of the Table
	 * */
	public Table(int id, int fk, int fk2, int fk3) {
		this(id, fk, fk2, fk3,0);
	}

	/**
	 * Creates the baseline for a Table with four foreign keys
	 * @param id Primary Key of the Table
	 * @param fk First foreign key of the Table
	 * @param fk2 Second foreign key of the Table
	 * @param fk3 Third foreign key of the Table
	 * @param fk4 Fourth foreign key of the Table
	 * */
	public Table(int id, int fk, int fk2, int fk3, int fk4) {
		this.id = id;
		this.fk = fk;
		this.fk2 = fk2;
		this.fk3 = fk3;
		this.fk4 = fk4;

		dateCreated = new SimpleObjectProperty<>(LocalDateTime.now());
		dateModified = new SimpleObjectProperty<>(LocalDateTime.now());
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
			System.out.println("New Table class created");
		} else {
			System.out.println("Dummy Table class created");
		}
	}

	/**
	 * Gets the main identifying name of an instance of a Table
	 * @return Table's "generic" name
	 * */
	public abstract String getGenericName();

	/**
	 * Gets the type of Table in question
	 * @return table type
	 * */
	public abstract DBTables getTableType();
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
		int buffer = Integer.compare(this.id, other.id);

		if (buffer == 0) {
			buffer = Integer.compare(this.fk, other.fk);

			if (buffer == 0) {
				return Integer.compare(this.fk2, other.fk);
			}
		}

		return buffer;

		/*
		* if (this.getId() == other.getId()) {
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
		}*/
	}

	/**
	 * Overrided Object equals method:
	 * Provides Table context of equivalence and used methods
	 * @return true if equals, false if not
	 * */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Table other = (Table) o;

		if (id != other.id) return false;
		if (fk != other.fk) return false;
		if (fk2 == other.fk2) return false;
		return getTableType().equals(other.getTableType());
	}

	/**
	 * Overrided Object hashCode method:
	 * Allows Table to be properly searched in a hashTable
	 * */
	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + fk;
		result = 31 * result + fk2;
		return 31 * result + getTableType().hashCode();
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
	 * <p>
	 * Gets the image related to this particular Table
	 * @return image
	 * */
	public Image getImage() {
		return image;
	}

	/**
	 * Setter:
	 * <p>
	 * Sets the image related to this particular Table
	 * @param image new Image
	 * */
	public void setImage(Image image) {
		this.image = image;
	}

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
	 * Gives the foreign key
	 * @return fk
	 * */
	public int getFk3() {
		return fk3;
	}

	/**
	 * Setter:
	 * Sets the foreign key
	 * @param fk3 ...
	 * */
	public void setFk3(int fk3) {
		this.fk3 = fk3;
	}

	/**
	 * Getter:
	 * Gives the foreign key
	 * @return fk
	 * */
	public int getFk4() {
		return fk4;
	}

	/**
	 * Setter:
	 * Sets the foreign key
	 * @param fk4 ...
	 * */
	public void setFk4(int fk4) {
		this.fk4 = fk4;
	}

	/**
	 * Getter:
	 * Gives the date and time the Table was created
	 * @return dateCreated
	 * */
	public LocalDateTime getDateCreated() {
		return dateCreated.get();
	}

	/**
	 * Setter:
	 * Sets the date and time the Table was created
	 * <p><i>Don't know if this is needed... </i></p>
	 * @param dateCreated ...
	 * */
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated.set(dateCreated);
	}

	public SimpleObjectProperty<LocalDateTime> dateCreatedProperty() {
		return dateCreated;
	}

	/**
	 * Getter:
	 * Gives the date and time the Table was last modified
	 * @return dateModified
	 * */
	public LocalDateTime getDateModified() {
		return dateModified.get();
	}

	/**
	 * Setter:
	 * Sets the date and time the Table was last modified
	 * @param dateModified new date the Table was modified
	 * */
	public void setDateModified(LocalDateTime dateModified) {
		this.dateModified.set(dateModified);
	}

	public SimpleObjectProperty<LocalDateTime> dateModifiedProperty() {
		return dateModified;
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

	/**
	 * Setter:
	 * Sets whether or not this Table is a dummy Table
	 * @param dummy state of Table
	 * */
	protected void setDummy(boolean dummy) {
		this.dummy = dummy;
	}
	//----------------------------------------------------------------
}
