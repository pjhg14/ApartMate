package com.graham.apartmate.ui.misc;

import com.graham.apartmate.database.tables.mainTables.Table;

import java.util.function.Consumer;

public class FXMLController {

    protected Table currentTable;

    protected Consumer<Table> dataBus;

    public void setCurrentTable(Table content) {
        currentTable = content;
    }

    public void setDataBus(Consumer<Table> dataBus) {
        this.dataBus = dataBus;
    }
}
