# ApartMate
Database GUI system responsible for the management of investment properties and the persons inhabiting or repairing them

## Known issues:
- error in insurance addition (foreign key failure for initial Invoice)
- invoice screen needs edit button(or edit from table)
- numTenants for Apartment not updated when adding or deleting tenants
- create add,delete, cancel buttons leading to respective windows/actions for noteLog info window
- noteLogs not being updated in database
- change factories for various ListViews and TableViews to display data in a managable fashion
- bills ListView does not update upon returning
- Bill addition does not create & save initial Invoice
- Tenant or Spouse always saves w/ primary key of 1
- Inspection always saves w/ primary key of 1
- Index out-of-bounds exception in candidate screen controller
- Need button to accept candidates into full-fledged Tenants
- Add thumbnails for every GUI field
- Candidate Spouse not saved for some reason
- Tenant/Candidate add window needs to close when add button is clicked
- Candidate edit button leads to null pointer exception
- Finish quick select TreeView
- Contractor view needs new icons
- foreign key failure for initial contractor Invoices
- Contractor info view leads to null pointer exception
- Contractor view leads to null pointer exception w/ more than 2 Contractors
- Contractors not inserted into SQL database