package com.graham.apartmate.ui.windows.utility;

import com.graham.apartmate.database.tables.mainTables.Table;

import java.util.function.BiConsumer;

public abstract class SubWindowController {

    protected Table currentTable;

    protected BiConsumer<Table, Boolean> subWindowSubmit;

    public abstract void init();

    public void setCurrentTable(Table content) {
        currentTable = content;
    }

    public void setSubWindowSubmit(BiConsumer<Table, Boolean> subWindowSubmit) {
        this.subWindowSubmit = subWindowSubmit;
    }
}
