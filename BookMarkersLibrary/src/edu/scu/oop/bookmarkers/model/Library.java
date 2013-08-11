/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.scu.oop.bookmarkers.db.DatabaseConnection;

/**
 * @author vidyanadig
 *
 */
public class Library {
	private static Library myLibrary;
	private Map<String, LibraryMember> libraryMembers;
	private List<FineObject> fineList;
	public static String countyName = "Springfield";
	
	private Library () throws SQLException {
		//loadMembersFromDB ();
	}
	
	public static Library getInstance () throws SQLException {
		if (myLibrary == null) {
			myLibrary = new Library();
		}
		
		return myLibrary;
	}
	
	// Libmember related functions
	public void loadMembersFromDB () throws SQLException {
		libraryMembers = new HashMap <String, LibraryMember> ();
		List<LibraryMember> ls = DatabaseConnection.getInstance().loadMembersIntoHashMap("Springfield");
		for (LibraryMember l: ls) {
			libraryMembers.put(l.getMembershipCardId(), l);
		}
        System.out.print("Loaded Members from DB");

	}
	
	/* 
	 * Called when a new library member registers to this Springfield Library.
	 * This function will be called from the GUI. This will in turn call the Databaseconnection method
	 * to add the member into the backend database. If that succeeds , we add the member to our Hash map
	 * which is stored in memory. So whenever a user registers, we add in the database + in the hash map.
	 */
	public void newLibraryMemberRegistration (String name, String email, String address, String phoneNum, String county) 
			throws SQLException {
		LibraryMember l;
		if (county == countyName) {
			l = new CountyMember(name, email, address, phoneNum, county);
		} else {
			l = new NonCountyMember(name, email, address, phoneNum, county);
		}
		if ((DatabaseConnection.getInstance().insertMemberIntoDatabase (l)) == Boolean.TRUE ) {
			libraryMembers.put(l.getMembershipCardId(), l);
		} else {
			System.out.println("Cannot add user ! DB error ");
		}
		
	}
	
	// Fines paid related functions
	public void loadFinesFromDB () throws SQLException {
        fineList = new ArrayList<FineObject>();
        DatabaseConnection.getInstance().loadFinesFromFinesDB(fineList);
        System.out.print("Loaded fines from DB");
	}

	public LibraryMember isMemIDValid (String memId) {
		return (libraryMembers.get(memId));
	}
	
	/*
	 * Called when a user pays his/her fines. Add an entry to the FinesDB and update the List which is held
	 * in memory.
	 */
	public void finesPaidByLibraryMember (String memId, double finePaid) 
			throws SQLException {
		LibraryMember l = isMemIDValid(memId);
		if (l != null) {
			FineObject f = new FineObject(memId, getTimeInSQLFormat(), finePaid);
			if ((DatabaseConnection.getInstance().insertFinePaidIntoDatabase(f)) == Boolean.TRUE ) {
				fineList.add(f);
			} else {
				System.out.println("Cannot add user ! DB error ");
			}
			l.memberPaysFines();
		} else {
			System.out.println("No use found in database ! ");
		}
		
	}
	
	public static java.sql.Date getTimeInSQLFormat () {
		java.util.Date today = new java.util.Date();
		return (new java.sql.Date(today.getTime()));
	}


	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Library.getInstance().loadMembersFromDB();
		Library.getInstance().loadFinesFromDB();
		Library.getInstance().finesPaidByLibraryMember("Ja671", 10);
	}

}
