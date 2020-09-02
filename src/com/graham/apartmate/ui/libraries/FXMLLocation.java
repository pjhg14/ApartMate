package com.graham.apartmate.ui.libraries;

import com.graham.apartmate.main.Main;

/**
 * Enumeration class that holds location constants for WindowLibrary
 * 
 * @since Can we call this an alpha? (0.1)
 * @version {@value Main#VERSION}
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
//TODO: Javadoc's for every method
public enum FXMLLocation {

	LOGIN("/com/graham/apartmate/ui/windows/loginwindow/login.fxml"),
	MAIN("/com/graham/apartmate/ui/windows/mainwindow/mainscene.fxml"),
	LISTS("/com/graham/apartmate/ui/windows/mainwindow/subwindow/listcontrollers/contentboxes.fxml"),
	OVERVIEW("/com/graham/apartmate/ui/windows/mainwindow/subwindow/overview/overview.fxml"),

	APARTMENTS("/com/graham/apartmate/ui/fxml/mainScreen/apartment.fxml"),
	TENANTS("/com/graham/apartmate/ui/fxml/mainScreen/tenant.fxml"),
	CANDIDATES("/com/graham/apartmate/ui/fxml/mainScreen/candidate.fxml"),
	CONTRACTORS("/com/graham/apartmate/ui/fxml/mainScreen/contractor.fxml"),

	APTINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/apartment/aptinfo.fxml"),
	TNANTINFO("/com/graham/apartmate/ui/fxml/info/tnantinfo.fxml"),
	CANDINFO("/com/graham/apartmate/ui/fxml/info/candinfo.fxml"),
	CONTINFO("/com/graham/apartmate/ui/fxml/info/continfo.fxml"),

	APTADD("/com/graham/apartmate/ui/windows/addwindows/apartment/aptadd.fxml"),
	TNANTADD("/com/graham/apartmate/ui/windows/addwindows/tenant/tnantadd.fxml"),
	CANDADD("/com/graham/apartmate/ui/windows/addwindows/candidate/candadd.fxml"),
	CONTADD("/com/graham/apartmate/ui/windows/addwindows/contractor/contadd.fxml"),

	APTEDIT("/com/graham/apartmate/ui/fxml/edit/aptedit.fxml"),
	TNANTEDIT("/com/graham/apartmate/ui/fxml/edit/tnantedit.fxml"),
	CANDEDIT("/com/graham/apartmate/ui/fxml/edit/candedit.fxml"),
	CONTEDIT("/com/graham/apartmate/ui/fxml/edit/contedit.fxml"),

	INVOICES("/com/graham/apartmate/ui/windows/popupwindows/invoice/invtable.fxml"),
	SPOUSEADD("/com/graham/apartmate/ui/windows/addwindows/roommate/spouseadd.fxml"),
	SPOUSEEDIT("/com/graham/apartmate/ui/fxml/edit/spouseedit.fxml"),
	BILLADD("/com/graham/apartmate/ui/windows/addwindows/bill/billadd.fxml"),
	BILLEDIT("/com/graham/apartmate/ui/fxml/edit/billedit.fxml"),
	ISSINSP("/com/graham/apartmate/ui/windows/popupwindows/notes/notes.fxml");

	private final String location;

	FXMLLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
