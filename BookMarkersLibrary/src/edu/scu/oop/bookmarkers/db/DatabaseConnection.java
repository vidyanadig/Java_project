package edu.scu.oop.bookmarkers.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.scu.oop.bookmarkers.model.CountyMember;
import edu.scu.oop.bookmarkers.model.FineObject;
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
	private static Statement stmt;

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
			stmt = dbc.con.createStatement();
		}	
		return dbc;
	}
	
	//Insert new library member into MemberDB.
	public Boolean insertMemberIntoDatabase (LibraryMember lm) throws SQLException {
        //Statement stmt = dbc.con.createStatement();
		String strInsert = "insert into memberDB values ('" + lm.getPersonName() + "','" + lm.getEmailId() + "','" 
        + lm.getHomeAddress() + "','" + lm.getPhoneNum() + "','" + lm.getCountyName() + "','" + lm.getMembershipCardId() +
        "', CURRENT_DATE," + lm.hasMemberPaidFines() + ", '" + lm.getFineAmount() + "')";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strInsert);  
        int rset = stmt.executeUpdate(strInsert);
        System.out.println("affected rows: " + rset);
		return (rset > 0) ? Boolean.TRUE: Boolean.FALSE;

	}
	
	// Update MemberDB if all fines are paid
	public void updateMemberIntoDatabase (LibraryMember lm) throws SQLException {
        //Statement stmt = dbc.con.createStatement();
		String strUpdate = "update memberDB set areFinesPaid = "+ lm.hasMemberPaidFines() + ", fineAmount = 0"  
						+ " where membershipCardId = '" + lm.getMembershipCardId() + "'";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strUpdate); 
        int rset = stmt.executeUpdate(strUpdate);
        System.out.println("affected rows: " + rset);
	}
	
	// Finds out whether the member ID is in the MemberDB to check if it is valid or not
	public Boolean searchMemberDatabase (String memID) throws SQLException {
        //Statement stmt = dbc.con.createStatement();
		String strSearch = "select count(*) as total from memberDB where membershipCardID = '" + memID + "'";
		//TODO Remove the printf
		ResultSet rset = stmt.executeQuery(strSearch);  
		rset.next();
		return (rset.getInt("total") > 0) ? Boolean.TRUE: Boolean.FALSE;
	}
	
	//Load all members into LibraryMembers hash map
	public  List<LibraryMember> loadMembersIntoHashMap (String county) throws SQLException {
        String strSelect = "select * from memberDB";
        List<LibraryMember> ls = new ArrayList<LibraryMember>();
        ResultSet rset = stmt.executeQuery(strSelect);

        LibraryMember l;
        while (rset.next()) {
        	if (rset.getString("countyName") == county) {
        		l = new CountyMember(rset.getString("personName"), rset.getString("emailID"), 
        							   			   rset.getString("homeAddress"), rset.getString("phoneNum"),
        							   			   county);
        		
        	} else {
        		l = new NonCountyMember(rset.getString("personName"), rset.getString("emailID"), 
			   			   rset.getString("homeAddress"), rset.getString("phoneNum"),
			   			   rset.getString("countyName"));
        	}
        	
        	ls.add(l);
        }
        return ls;
	}
	
	//Load all members into Fines List
	public  void loadFinesFromFinesDB (List<FineObject> l) throws SQLException {
        String strSelect = "select * from FinesDB";
        ResultSet rset = stmt.executeQuery(strSelect);
	    while (rset.next()) {
	       	FineObject f = new FineObject(rset.getString("memberID"), rset.getDate("dateOfPayment"), rset.getDouble("fine"));
	       	l.add(f);
	    }
	}

	//Insert new fine paid into MemberDB.
	public Boolean insertFinePaidIntoDatabase (FineObject f) throws SQLException {
        //Statement stmt = dbc.con.createStatement();
		String strInsert = "insert into FinesDB (`memberID`, `dateOfPayment`,`fine`) values ('" 
        + f.getMemberID() + "','" + f.getDateOfPayment() + "'," 
        + f.getFine() + ")";
		//TODO Remove the printf
		System.out.println("The SQL query is: " + strInsert);  
        int rset = stmt.executeUpdate(strInsert);
        System.out.println("affected rows: " + rset);
        return (rset > 0) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		//LibraryMember l = new NonCountyMember("Bruce Lee", "Bruce Lee@gmail.com", "Taiwan", "3456789123", "TaiwanTown");
		//System.out.print(l.getPersonName().substring(0,2));

		//DatabaseConnection.getInstance();
	
		//DatabaseConnection.getInstance().updateMemberIntoDatabase(l);
		//System.out.println("Is there a Er345 ?" + DatabaseConnection.getInstance().searchMemberDatabase("Hi1523"));
		//Library.getInstance().loadMembersFromDB();

	}*/

}
