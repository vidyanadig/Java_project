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
	
	public Integer findTotalNumberOfMembers () {
		System.out.println("number of members in library returning ");

		return LibraryMember.numberOfMembers;
	}
	
	public void findTotalNumberOfItemsInLibrary () {	
		//TODO 
		System.out.println("Fiction: "+ Fiction.getNumOfItems() + "NonFiction: "+ NonFiction.getNumOfItems() +
				"Video: "+Video.getNumOfItems());
	}

	public Integer findNumberOfItemsCheckedOut () {
		System.out.println("number of items checkedout returning ");

		return Library.getInstance().totalNumOfItemsCheckedOut();
	}
	
	public String findTitleOfBookCheckedoutMost (int month) {
		System.out.println("Most checkedout book returning");
		return Library.getInstance().mostCheckedOutBookInAMonth(month);
	}
	
	public Double findTotalFineCollected (Date fromDate, Date toDate ) {
		return Library.getInstance().totalFinesCollectedByLibrary (fromDate, toDate);
	}
	
}
