package edu.scu.oop.bookmarkers.db;

import java.sql.*; 
import java.text.SimpleDateFormat;
import java.util.Date;


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
			// exception to be handled in the calling class. 
		} finally {
		/*	if (con != null) {
				con.close();
			}*/
		}
		
	}
	
	public static DatabaseConnection getInstance () throws SQLException {
		if (dbc == null) {
			dbc = new DatabaseConnection();
		}
		
		return dbc;
	}
	
	public void insertMemberIntoDatabse (String memID, boolean areFinesPaid, double fines) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strInsert = "insert into memberDB values ('" + memID + "', CURRENT_DATE," + areFinesPaid + ", '" + fines + "')";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strInsert);  
        int rset = stmt.executeUpdate(strInsert);
        System.out.println("affected rows: " + rset);

	}
	
	public void updateMemberIntoDatabse (String memID, boolean areFinesPaid, double fines) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strUpdate = "update memberDB set areFinesPaid = "+areFinesPaid + ", fineAmount = " + fines + " where membershipCardId = '" + memID + "'";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strUpdate); 
        int rset = stmt.executeUpdate(strUpdate);
        System.out.println("affected rows: " + rset);
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//DatabaseConnection.getInstance().insertMemberIntoDatabse("Go345", Boolean.FALSE, 10);
		//DatabaseConnection.getInstance().updateMemberIntoDatabse("Go345", Boolean.TRUE, 0);

	}

}
