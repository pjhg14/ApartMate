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
	DEBUG("/com/graham/apartmate/ui/windows/dbgwindow/dbgwindow.fxml"),
	MAIN("/com/graham/apartmate/ui/windows/mainwindow/mainscene.fxml"),
	LISTS("/com/graham/apartmate/ui/windows/mainwindow/subwindow/listcontrollers/contentboxes.fxml"),
	OVERVIEW("/com/graham/apartmate/ui/windows/mainwindow/subwindow/overview/overview.fxml"),

	BLDGINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/building/bldginfo.fxml"),
	TNANTINFO("/com/graham/apartmate/ui/fxml/info/tnantinfo.fxml"),
	CANDINFO("/com/graham/apartmate/ui/fxml/info/candinfo.fxml"),
	CONTINFO("/com/graham/apartmate/ui/fxml/info/continfo.fxml"),
	ACTINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/account/actinfo.fxml"),
	BILLINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/bill/billinfo.fxml"),
	NOTEINFO("com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/notelog/noteinfo.fxml"),

	BLDGADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/building/bldgadd.fxml"),
	TNANTADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/tenant/tnantadd.fxml"),
	CANDADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/candidate/candadd.fxml"),
	CONTADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/contractor/contadd.fxml"),

	BLDGEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/building/bldgedit.fxml"),
	TNANTEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/tenant/tnantedit.fxml"),
	CANDEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/candidate/candedit.fxml"),
	CONTEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/contractor/contedit.fxml"),

	SPOUSEADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/roommate/spouseadd.fxml"),
	SPOUSEEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/roommate/spouseedit.fxml"),
	BILLADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/bill/billadd.fxml"),
	BILLEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/bill/billedit.fxml"),
	NOTEADD(""),
	NOTEEDIT("");

	private final String location;

	FXMLLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
