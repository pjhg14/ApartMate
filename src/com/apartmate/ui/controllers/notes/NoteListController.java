package com.apartmate.ui.controllers.notes;

import com.apartmate.database.dbMirror.DBTables;
import com.apartmate.database.dbMirror.Database;

import com.apartmate.database.tables.subTables.NoteLog;
import com.apartmate.main.Main;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * Controls the scene responsible for interacting with Apartment Issues and Tenant Inspections
 * */
//TODO: Javadoc's for every method
public class NoteListController {

	@FXML
	public ListView<NoteLog> logListView;

	@FXML
	public TextArea logTextArea;

	@FXML
	public Text text;

	@FXML
	public DatePicker logDatePicker;

	@FXML
	public Button deleteButton;

	@FXML
	public Button addButton;

	@FXML
	public Button saveButton;

	private ObservableList<NoteLog> logList;

	private String oldLog;

	@FXML
	public void initialize() {
		switch (Database.getCurrTable()) {
			case INSPECTIONS:
				//Initialize for Inspections
				logList = FXCollections.observableArrayList(Database.getInstance().getCurrTnant().getInspections());
				logListView.setItems(logList);
				text.setText("New Inspection:");

				logTextArea.setOnKeyTyped(event -> {
					String currentText = logTextArea.getText();
					if (currentText.equals("") || currentText.equals(oldLog.trim()) || currentText.equals("Enter new inspection log"))
						saveButton.setDisable(true);

					if (!logTextArea.getText().equals(""))
						saveButton.setDisable(false);
				});
				break;
			case ISSUES:
				//Initialize for Issues
				logList = FXCollections.observableArrayList(Database.getInstance().getCurrApt().getIssues());
				logListView.setItems(logList);
				text.setText("New Issue:");

				logTextArea.setOnKeyTyped(event -> {
					String currentText = logTextArea.getText();
					if (currentText.equals("") || currentText.equals(oldLog.trim()) || currentText.equals("Enter new issue log"))
						saveButton.setDisable(true);

					if (!logTextArea.getText().equals(""))
						saveButton.setDisable(false);
				});
				break;
		}

		addListeners();

		//Initialize List and TextArea
		if (!logList.isEmpty()) {
			logListView.getSelectionModel().clearAndSelect(0);
			logTextArea.setText(logListView.getSelectionModel().getSelectedItem().getLog());
		}
	}

	@FXML
	public void addLog() {
		try{
			NoteLog newLog = new NoteLog();
			int lastIndex;
			switch (Database.getCurrTable()) {
				case INSPECTIONS:
					lastIndex = Database.getInstance().getLastID(DBTables.INSPECTIONS);
					newLog.setFk(Database.getInstance().getCurrTnant().getId());
					break;
				case ISSUES:
					lastIndex = Database.getInstance().getLastID(DBTables.ISSUES);
					newLog.setFk(Database.getInstance().getCurrApt().getId());
					break;
				default:
					return;
			}

			if (lastIndex <= 0)
				lastIndex = 0;

			newLog.setId(lastIndex + 1);

			newLog.setLogDate(Date.from(Instant.from(logDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()))));
			logList.add(newLog);
		} catch (NullPointerException e) {
			System.out.println(Database.getCurrTable());
			if (Main.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	@FXML
	public void saveLog() {
		logList.get(logList.indexOf(logListView.getSelectionModel().getSelectedItem())).setLog(logTextArea.getText());
		saveButton.setDisable(true);
	}

	@FXML
	public void deleteLog() {
		logList.remove(logListView.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void showLog() {
		logTextArea.clear();

		NoteLog selectedLog = logListView.getSelectionModel().getSelectedItem();
		if (selectedLog != null) {
			logTextArea.setText(selectedLog.getLog());
			oldLog = selectedLog.getLog();
			saveButton.setDisable(true);
		}
	}

	private void addListeners() {
		logList.addListener((ListChangeListener<NoteLog>) c -> {
			while (c.next()) {
				if (c.wasAdded()) {
					for (NoteLog noteLog : c.getAddedSubList()) {
						Database.getInstance().sqlBridge.insert(noteLog, Database.getCurrTable());
					}
				} else if (c.wasRemoved()) {
					for (NoteLog noteLog : c.getRemoved()) {
						Database.getInstance().sqlBridge.delete(noteLog,Database.getCurrTable());
					}
				} else if (c.wasUpdated()) {
					for (NoteLog noteLog : c.getList()) {
						Database.getInstance().sqlBridge.update(noteLog, Database.getCurrTable());
					}
				}
			}
		});
	}

}
