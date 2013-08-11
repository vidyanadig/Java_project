/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.sql.SQLException;
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
	
	private Library () throws SQLException {
		loadMembersFromDB ();
	}
	
	public static Library getInstance () throws SQLException {
		if (myLibrary == null) {
			myLibrary = new Library();
		}
		
		return myLibrary;
	}
	
	private void loadMembersFromDB () throws SQLException {
		libraryMembers = new HashMap <String, LibraryMember> ();
		List<LibraryMember> ls = DatabaseConnection.loadMembersIntoHashMap("Springfield");
		for (LibraryMember l: ls)
			libraryMembers.put(l.getMembershipCardId(), l);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
