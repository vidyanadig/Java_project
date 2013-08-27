package edu.scu.oop.bookmarkers.model;



/**
 * @author vidyanadig
 *
 */
public class NonCountyMember extends LibraryMember {
	public NonCountyMember (String name, String email, String address, String phoneNum, String county) {
		super (name,  email,  address,  phoneNum,  county);
		fineAmount = getMembershipFee();
		areFinesPaid = (fineAmount > 0 ? Boolean.FALSE : Boolean.TRUE);
	}
	
	public double getMembershipFee () {
		return 10;
	}

}
