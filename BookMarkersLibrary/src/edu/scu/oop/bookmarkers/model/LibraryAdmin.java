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
		//TODO 
		System.out.println("Fiction: "+ Fiction.numOfItems + "NonFiction: "+ NonFiction.numOfItems +
				"Video: "+Video.numOfItems);
	}
	
	public int findNumberOfItemsCheckedOut () {
		return Library.getInstance().totalNumOfItemsCheckedOut();
	}
	
	public Item findTitleOfBookCheckedoutMost (int month) {
		return Library.getInstance().mostCheckedOutBookInAMonth(month);
	}
	
	public void findTotalFineCollected (Date fromDate, Date toDate ) {
		Library.getInstance().totalFinesCollectedByLibrary (fromDate, toDate);
	}
	
}
