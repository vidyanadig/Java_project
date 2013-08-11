package edu.scu.oop.bookmarkers.model;

import java.sql.SQLException;
import java.util.Date;

import edu.scu.oop.bookmarkers.db.DatabaseConnection;

/**
 * @author vidyanadig
 *
 */
public abstract class LibraryMember extends Person {

	private String membershipCardId; // String of 5 chars
	private java.sql.Date membershipStartDate;
	protected Boolean areFinesPaid;
	protected double fineAmount;
	public abstract double getMembershipFee ();
	public static int numberOfMembers;

	public LibraryMember (String name, String email, String address, String phoneNum, String county) {
		super (name,  email,  address,  phoneNum,  county);
		// John Doe (123-456-7890) will be Jo890
		membershipCardId = (name.substring(0,2)).concat(phoneNum.substring(7));
		membershipStartDate = Library.getTimeInSQLFormat(); // get the date in a format which is same as SQL date format
		areFinesPaid = Boolean.FALSE;
		
		numberOfMembers++;
	}
	
	public String getMembershipCardId () {
		return membershipCardId;
	}
	
	public Boolean hasMemberPaidFines () {
		return areFinesPaid;
	}
	
	public Date getMembershipStartDate () {
		return membershipStartDate;
	}
	
	public double getFineAmount () {
		return fineAmount;
	}
	
	public void checkoutBook () {
		
	}
	
	public void returnBook () {
		
	}
	
	public void retrieveTransaction () {
		
	}
	
	public void memberPaysFines () throws SQLException {
		areFinesPaid = Boolean.TRUE;
		fineAmount = 0;
		DatabaseConnection.getInstance().updateMemberIntoDatabase(this);
	}

}
