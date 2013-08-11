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
	
	//Insert new library member into MemberDB.
	public void insertMemberIntoDatabase (String memID, boolean areFinesPaid, double fines) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strInsert = "insert into memberDB values ('" + memID + "', CURRENT_DATE," + areFinesPaid + ", '" + fines + "')";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strInsert);  
        int rset = stmt.executeUpdate(strInsert);
        System.out.println("affected rows: " + rset);

	}
	
	// Update MemberDB if all fines are paid
	public void updateMemberIntoDatabase (String memID, boolean areFinesPaid, double fines) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strUpdate = "update memberDB set areFinesPaid = "+areFinesPaid + ", fineAmount = " + fines + " where membershipCardId = '" + memID + "'";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strUpdate); 
        int rset = stmt.executeUpdate(strUpdate);
        System.out.println("affected rows: " + rset);
	}
	
	// Finds out whether the member ID is in the MemberDB to check if it is valid or not
	public Boolean searchMemberDatabase (String memID) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strUpdate = "select count(*) as total from memberDB where membershipCardID = '" + memID + "'";
		//TODO Remove the printf
		ResultSet rset = stmt.executeQuery(strUpdate);  
		rset.next();
		return (rset.getInt("total") > 0) ? Boolean.TRUE: Boolean.FALSE;
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//DatabaseConnection.getInstance().insertMemberIntoDatabse("Er345", Boolean.FALSE, 10);
		//DatabaseConnection.getInstance().updateMemberIntoDatabse("Er345", Boolean.TRUE, 0);
		System.out.println("Is there a Er345 ?" + DatabaseConnection.getInstance().searchMemberDatabase("Hi1523"));

	}

}
