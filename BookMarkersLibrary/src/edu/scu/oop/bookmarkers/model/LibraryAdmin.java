package edu.scu.oop.bookmarkers.model;

import java.util.*;


/**
 * @author vidyanadig
 *
 */
public final class LibraryAdmin extends Person {
	private String username;
	private String password;
	private static LibraryAdmin libAdmin; 
	
	private LibraryAdmin () {
		// Private constructor so that there is only 1 admin.
		super ("Steve Jobs", "jobs@apple.com","123, SCU drive, Santa Clara", "111-111-1111","Santa Clara");
		username = "bookmarkeradmin";
		password = "adminpass";
	}
	
	public static LibraryAdmin getAdminInstance () {
		if (libAdmin == null) {
			libAdmin = new LibraryAdmin();
		}
		return libAdmin;
	}
	
	public int findTotalNumberOfMembers () {
		return LibraryMember.numberOfMembers;
	}
	
	public void findTotalNumberOfItemsInLibrary () {
		//TODO return Items.numberOfItems;
	}
	
	public void findNumberOfItemsCheckedOut () {
		//TODO return Library.numberOfItemsCheckedOut;
	}
	
	public void findTitleOfBookCheckedoutMost () {
		//TODO return Library.mostCheckedoutBook
	}
	
	public void findTotalFineCollected (Date fromDate, Date toDate ) {
		//TODO Library.totalFineCollected (fromDate, toDate)
	}
	
}
