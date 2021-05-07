package com.graham.apartmate.ui.res.classes;

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
	TNANTINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/tenant/tnantinfo.fxml"),
	CANDINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/candidate/candinfo.fxml"),
	CONTRINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/contractor/continfo.fxml"),
	ACTINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/account/actinfo.fxml"),
	CONTACTINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/contact/contactinfo.fxml"),
	CONTRACTINFO(""),
	BILLINFO("/com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/bill/billinfo.fxml"),
	NOTEINFO("com/graham/apartmate/ui/windows/mainwindow/subwindow/infocontrollers/notelog/noteinfo.fxml"),

	BLDGADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/building/bldgadd.fxml"),
	TNANTADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/tenant/tnantadd.fxml"),
	CANDADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/candidate/candadd.fxml"),
	CONTACTADD(""),
	CONTRADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/contractor/contadd.fxml"),
	BILLADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/bill/billadd.fxml"),

	OCCUPADD("/com/graham/apartmate/ui/windows/popupwindows/addwindows/roommate/spouseadd.fxml"),
	NOTEADD(""),

	BLDGEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/building/bldgedit.fxml"),
	TNANTEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/tenant/tnantedit.fxml"),
	CANDEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/candidate/candedit.fxml"),
	CONTREDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/contractor/contedit.fxml"),
	CONTACTEDIT(""),
	BILLEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/bill/billedit.fxml"),
	OCCUPEDIT("/com/graham/apartmate/ui/windows/popupwindows/editwindows/roommate/spouseedit.fxml"),
	NOTEEDIT("");


	private final String location;

	FXMLLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
