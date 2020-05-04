package com.apartmate.database.validation;

public class User {

	enum UserAuth {
		/**
		 * User Authentication flag {@code DBADMIN}
		 * Gives full access to database
		 * */
		@SuppressWarnings("unused")
		DBADMIN,

		/**
		 * User Authentication flag {@code DBMANAGEMENT}
		 * Gives access to CRUD for non-admin users and all apartments
		 * */
		@SuppressWarnings("unused")
		DBMANAGEMENT,

		/**
		 * User Authentication flag {@code TENANTUSER}
		 * Gives access to a single Tenant
		 * switches overview (start of MainWindow) to tenant overview
		 * Tenant overview expected inclusion: v1.7..?*/
		@SuppressWarnings("unused")
		TENANTUSER;
	}

	/**
	 * Id of User
	 * */
	private int id;

	/**
	 * User's username
	 * */
	private String username;

	/**
	 * User's password
	 * */
	private String pass;

	/**
	 * Stores the authorization level of the user
	 * */
	private UserAuth auth;
	
	public User() {
		
	}

	/**
	 * @return id of User
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id new id of User
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return User's username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username new username for the User
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return User's password
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass new password for User
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return Authorization level of User
	 */
	public UserAuth getAuth() {
		return auth;
	}

	/**
	 * @param auth new authorization level of User
	 */
	public void setAuth(UserAuth auth) {
		this.auth = auth;
	}
}
