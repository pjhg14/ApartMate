package com.graham.apartmate.database.tables.subTables;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.tables.mainTables.Table;

public class Room extends Table {
    //--------------------------------------------------------------------
    //Enumerations
    //--------------------------------------------------------------------
    enum RoomType{
        BEDROOM,
        BATHROOM,
        KITCHEN,
        LIVING_ROOM,
        FAMILY_ROOM,
        OFFICE;


        /**
         * Returns the name of this enum constant
         *
         * @return the name of this enum constant
         */
        @Override
        public String toString() {

            return super.name().charAt(0) + super.name().substring(1).toLowerCase();
        }
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Fields//////////////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    private RoomType roomType;
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Constructors////////////////////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Room Constructor
     *
     * @param id Primary Key of the Table
     * @param fk Foreign Key of the Table
     */
    public Room(int id, int fk, RoomType roomType) {
        super(id, fk);
        this.roomType = roomType;
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //Overrided & Utility Methods/////////////////////////////////////////
    //--------------------------------------------------------------------
    /**
     * Gets the main identifying name of an instance of a Table
     *
     * @return Table's "generic" name
     */
    @Override
    public String getGenericName() {
        return roomType.toString();
    }

    /**
     * Gets the type of Table in question
     *
     * @return table type
     */
    @Override
    public DBTables getTableType() {
        return null;
    }

    /***/
    @Override
    public String getInfoLocation() {
        return null;
    }

    /***/
    @Override
    public String getAddLocation() {
        return null;
    }

    /***/
    @Override
    public String getEditLocation() {
        return null;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------

    //--------------------------------------------------------------------
    //General Getters and setters/////////////////////////////////////////
    //--------------------------------------------------------------------
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
}
