package com.graham.apartmate.ui.windows.popupwindows.notes;

import com.graham.apartmate.database.dbMirror.DBTables;
import com.graham.apartmate.database.dbMirror.Database;

import com.graham.apartmate.database.tables.mainTables.Table;
import com.graham.apartmate.database.tables.subTables.NoteLog;
import com.graham.apartmate.main.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

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

	private Table currentTable;

	private ObservableList<NoteLog> logList;

	private String oldLog;

	@FXML
	public void initialize() {
//		switch (currentTable.getTableType()) {
//			case APARTMENTS:
//				//Initialize for Issues
//				logList = FXCollections.observableArrayList(((Apartment) currentTable).getIssues());
//				logListView.setItems(logList);
//				text.setText("New Issue:");
//
//				break;
//			case TENANTS:
//				//Initialize for Inspections
//				logList = FXCollections.observableArrayList(((Tenant) currentTable).getInspections());
//				logListView.setItems(logList);
//				text.setText("New Inspection:");
//
//				break;
//			default:
//				if (Main.DEBUG)
//					System.out.println("Invalid Table operand");
//				Main.getLibrary().closePopup();
//		}
//
//		logTextArea.setOnKeyTyped(event -> {
//			String currentText = logTextArea.getText();
//			if (currentText.equals("") || currentText.equals(oldLog.trim()) || currentText.equals("Enter new issue log"))
//				saveButton.setDisable(true);
//
//			if (!logTextArea.getText().equals(""))
//				saveButton.setDisable(false);
//		});
//
//		addListeners();
//
//		//Initialize List and TextArea
//		if (!logList.isEmpty()) {
//			logListView.getSelectionModel().clearAndSelect(0);
//			logTextArea.setText(logListView.getSelectionModel().getSelectedItem().getLog());
//		}
	}

	@FXML
	public void addLog() {
		try{
			boolean isIssue;
			//Set new ID
			int lastIndex;
			switch (Database.getCurrTable()) {
				case INSPECTIONS:
					lastIndex = Database.getInstance().getLastID(DBTables.INSPECTIONS);
					isIssue = false;
					break;
				case ISSUES:
					lastIndex = Database.getInstance().getLastID(DBTables.ISSUES);
					isIssue = true;
					break;
				default:
					return;
			}

			NoteLog newLog = new NoteLog(isIssue);

			if (lastIndex <= 0)
				lastIndex = 0;

			//Set FK
			newLog.setFk(currentTable.getId());

			newLog.setId(lastIndex + 1);

			newLog.setLogDate(logDatePicker.getValue());
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
			oldLog = selectedLog.getLog().trim();
			saveButton.setDisable(true);
		}
	}

	//TODO: to be moved to related superclasses when Lists are changed to Observable Lists
	private void addListeners() {
//		logList.addListener((ListChangeListener<NoteLog>) c -> {
//			while (c.next()) {
//				if (c.wasAdded()) {
//					for (NoteLog noteLog : c.getAddedSubList()) {
//						Database.getInstance().sqlBridge.insert(noteLog, Database.getCurrTable());
//					}
//				} else if (c.wasRemoved()) {
//					for (NoteLog noteLog : c.getRemoved()) {
//						Database.getInstance().sqlBridge.delete(noteLog,Database.getCurrTable());
//					}
//				} else if (c.wasUpdated()) {
//					for (NoteLog noteLog : c.getList()) {
//						Database.getInstance().sqlBridge.update(noteLog, Database.getCurrTable());
//					}
//				}
//			}
//		});
	}

	public void setCurrTable(Table content) {
		currentTable = content;
	}
}
