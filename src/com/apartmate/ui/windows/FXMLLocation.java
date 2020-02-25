package com.apartmate.ui.windows;

/**
 * Enumeration class that holds location constants for WindowLibrary
 * 
 * @since Can we call this an alpha? (0.1)
 * @version Capstone (0.8)
 * @author Paul Graham Jr (pjhg14@gmail.com)
 */
public enum FXMLLocation {

	LOGIN("/com/apartmate/ui/fxml/mainScreen/login.fxml"),

	APARTMENT("/com/apartmate/ui/fxml/mainScreen/apartment.fxml"),
	TENANT("/com/apartmate/ui/fxml/mainScreen/tenant.fxml"),
	CANDIDATE("/com/apartmate/ui/fxml/mainScreen/candidate.fxml"),
	CONTRACTOR("/com/apartmate/ui/fxml/mainScreen/apartment.fxml"),

	APTINFO("/com/apartmate/ui/fxml/info/aptinfo.fxml"), 
	TNANTINFO("/com/apartmate/ui/fxml/info/tnantinfo.fxml"),
	CANDINFO("/com/apartmate/ui/fxml/info/candinfo.fxml"), 
	CONTINFO("/com/apartmate/ui/fxml/info/continfo.fxml"),

	APTADD("/com/apartmate/ui/fxml/addition/aptadd.fxml"), 
	TNANTADD("/com/apartmate/ui/fxml/addition/tnantadd.fxml"),
	CANDADD("/com/apartmate/ui/fxml/addition/candadd.fxml"), 
	CONTADD("/com/apartmate/ui/fxml/addition/contadd.fxml"),

	APTEDIT("/com/apartmate/ui/fxml/edit/aptedit.fxml"), 
	TNANTEDIT("/com/apartmate/ui/fxml/edit/tnantedit.fxml"),
	CANDEDIT("/com/apartmate/ui/fxml/edit/candedit.fxml"), 
	CONTEDIT("/com/apartmate/ui/fxml/edit/contedit.fxml"),

	TNANTINVOICES("/com/apartmate/ui/fxml/invoice/tnantinvtable.fxml"),
	CONTINVOICES("/com/apartmate/ui/fxml/invoice/continvtable.fxml"),
	INSURANCES("/com/apartmate/ui/fxml/invoice/instable.fxml"),

	SPOUSEADD("/com/apartmate/ui/fxml/addition/spouseadd.fxml"),
	SPOUSEEDIT("/com/apartmate/ui/fxml/edit/spouseedit.fxml"), 
	ISSINSP("/com/apartmate/ui/fxml/edit/notes.fxml");

	private String location;

	private FXMLLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
