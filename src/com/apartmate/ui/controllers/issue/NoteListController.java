package com.apartmate.ui.controllers.issue;

import com.apartmate.database.dbMirror.Database;

import javafx.fxml.FXML;

public class NoteListController {

	@FXML
	public void Initialize() {
		if (Database.getInstance().isIssInsSwitch()) {
			// Initialize for Issues
		} else {
			// Initialize for inspections
		}
	}
}
