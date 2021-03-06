package edu.scu.oop.bookmarkers.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vidyanadig
 *
 */
public abstract class LibraryMember extends Person implements Serializable{

	private String membershipCardId; // String of 5 chars
	private Date membershipStartDate;
	protected Boolean areFinesPaid;
	protected double fineAmount;
	public abstract double getMembershipFee ();
	public static int numberOfMembers;

	public LibraryMember (String name, String email, String address, String phoneNum, String county) {
		super (name,  email,  address,  phoneNum,  county);
		// John Doe (123-456-7890) will be Jo890
		membershipCardId = (name.substring(0,2)).concat(phoneNum.substring(7));
		membershipStartDate = new Date(); 
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
	
	public void setFineAmount (double fines) {
		 this.fineAmount += fines;
		 this.areFinesPaid = Boolean.FALSE;
	}
	
	public void memberPaysFines () {
		areFinesPaid = Boolean.TRUE;
		fineAmount = 0;
	}
	
	

}
