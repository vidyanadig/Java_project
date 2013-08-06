package edu.scu.oop.bookmarkers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author vidyanadig
 * 
 */
public class DatabaseConnection {
	private Connection con = null;

	private static final String URL = "jdbc:mysql://localhost:3306/COEN275Java";
	private static final String USER = "root";
	private static final String PASSWORD = "vnadig";

	private static DatabaseConnection dbc;

	private DatabaseConnection () throws SQLException {
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
	public static DatabaseConnection getInstance () throws SQLException {
		if (dbc == null) {
			dbc = new DatabaseConnection();
		}
		
		return dbc;
	}

}
