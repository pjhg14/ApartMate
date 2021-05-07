package com.graham.apartmate.ui.windows.utility;

import com.graham.apartmate.database.tables.mainTables.Table;

import java.util.function.BiConsumer;

public abstract class SubWindowController {

    /**
     * The current Table of the SubWindow
     * */
    protected Table currentTable;

    /**
     * Changes the SubWindow based on the passed table as well as whether or not a list of the table is desired
     * */
    protected BiConsumer<Table, Boolean> subWindowSubmit;

    /**
     * Initializes the SubWindow
     * */
    public abstract void init();

    /**
     * Current Table accessor; MUST be assigned prior to loading an instance of a SubWindow
     * */
    public void setCurrentTable(Table content) {
        currentTable = content;
    }

    /**
     * Submission consumer accessor; MUST be assigned before loading an instance of a SubWindow
     * */
    public void setSubWindowSubmit(BiConsumer<Table, Boolean> subWindowSubmit) {
        this.subWindowSubmit = subWindowSubmit;
    }
}
