package edu.scu.oop.bookmarkers.model;



/**
 * @author vidyanadig
 *
 */

public class CountyMember extends LibraryMember{

	public CountyMember (String name, String email, String address, String phoneNum, String county) {
		super (name,  email,  address,  phoneNum,  county);
		fineAmount = getMembershipFee();
		areFinesPaid = (fineAmount > 0 ? Boolean.TRUE : Boolean.FALSE);	
	}
	
	public double getMembershipFee () {
		return 0.0;
	}

}
