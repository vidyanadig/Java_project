package edu.scu.oop.bookmarkers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.scu.oop.bookmarkers.model.CountyMember;
import edu.scu.oop.bookmarkers.model.LibraryMember;
import edu.scu.oop.bookmarkers.model.NonCountyMember;

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
	public void insertMemberIntoDatabase (LibraryMember lm) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strInsert = "insert into memberDB values ('" + lm.getPersonName() + "','" + lm.getEmailId() + "','" 
        + lm.getHomeAddress() + "','" + lm.getPhoneNum() + "','" + lm.getCountyName() + "','" + lm.getMembershipCardId() +
        "', CURRENT_DATE," + lm.hasMemberPaidFines() + ", '" + lm.getFineAmount() + "')";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strInsert);  
        int rset = stmt.executeUpdate(strInsert);
        System.out.println("affected rows: " + rset);
	}
	
	// Update MemberDB if all fines are paid
	public void updateMemberIntoDatabase (LibraryMember lm) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strUpdate = "update memberDB set areFinesPaid = "+ lm.hasMemberPaidFines() + ", fineAmount = " + lm.getFineAmount() 
						+ " where membershipCardId = '" + lm.getMembershipCardId() + "'";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strUpdate); 
        int rset = stmt.executeUpdate(strUpdate);
        System.out.println("affected rows: " + rset);
	}
	
	// Finds out whether the member ID is in the MemberDB to check if it is valid or not
	public Boolean searchMemberDatabase (String memID) throws SQLException {
        Statement stmt = dbc.con.createStatement();
		String strSearch = "select count(*) as total from memberDB where membershipCardID = '" + memID + "'";
		//TODO Remove the printf
		ResultSet rset = stmt.executeQuery(strSearch);  
		rset.next();
		return (rset.getInt("total") > 0) ? Boolean.TRUE: Boolean.FALSE;
	}
	
	//Load all members into LibraryMembers hash map
	public static List<LibraryMember> loadMembersIntoHashMap (String county) throws SQLException {
        Statement stmt = dbc.con.createStatement();
        //String strSelect = "select name, email, addr, phone, county, memID, memStartDate, paidFine, fineAmt from memberDB";
        String strSelect = "select * from memberDB";
        List<LibraryMember> ls = new ArrayList<LibraryMember>();
        ResultSet rset = stmt.executeQuery(strSelect);
        LibraryMember l;
        while (rset.next()) {
        	if (rset.getString("countyName") == county) {
        		l = new CountyMember(rset.getString("personName"), rset.getString("emailID"), 
        							   			   rset.getString("homeAddress"), rset.getString("phoneNum"),
        							   			   "Springfield");
        		
        	} else {
        		l = new NonCountyMember(rset.getString("personName"), rset.getString("emailID"), 
			   			   rset.getString("homeAddress"), rset.getString("phoneNum"),
			   			   rset.getString("countyName"));
        	}
        	
        	ls.add(l);
        }
        return ls;
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		LibraryMember l = new CountyMember("Erin Liv", "erin.liv@gmail.com", "Sunnyvale", "4084567890", "Santa Clara");
		System.out.print(l.getPersonName().substring(0,2));

		//DatabaseConnection.getInstance().insertMemberIntoDatabase(l);
	
		DatabaseConnection.getInstance().updateMemberIntoDatabase(l);
		//System.out.println("Is there a Er345 ?" + DatabaseConnection.getInstance().searchMemberDatabase("Hi1523"));

	}

}
