/**
 * This is the class for fine object. The objects are stored in the finesDB in the database.
 */
package edu.scu.oop.bookmarkers.model;

import java.sql.Date;

/**
 * @author vidyanadig
 *
 */
public class FineObject {
	private String memberID; // Member who is paying the fine.
	private Date dateOfPayment; // The date when member pays fines.
	private double fine; // The fine amount itself
	
	public FineObject (String memberID, Date dateOfPayment, double fine) {
		this.memberID = memberID;
		this.dateOfPayment = dateOfPayment;
		this.fine = fine;
	}
	
	public String getMemberID () {
		return this.memberID;
	}
	
	public Date getDateOfPayment () {
		return this.dateOfPayment;
	}
	
	public double getFine () {
		return this.fine;
	}
}

