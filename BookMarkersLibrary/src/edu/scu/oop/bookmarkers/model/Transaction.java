/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
/**
 * @author vidyanadig
 *
 */
public class Transaction implements Serializable{
	private int transID;
	private String memID;
	private Date borrowDate;
	private Date returnDate;
	private String itemID;
	private static int transTillNow;

	public Transaction (String memID, String itemID ) {
		transTillNow++;
		this.transID = transTillNow;
		this.memID = memID;
		this.borrowDate = new Date();
		this.itemID = itemID;
		this.returnDate = null;
	}
	
	public void setReturnDate () {
		this.returnDate = new Date();
	}
	
	public String getItemID () {
		return this.itemID;
	}
	
	public Date getReturnDate () {
		return returnDate;
	}
	
	public Date getBorrowDate () {
		return borrowDate;
	}
	
	public int getTransID () {
		return transID;
	}
	
	public int getBorrowMonth () {
		Calendar cal = Calendar.getInstance();
		cal.setTime(borrowDate);
		return cal.get(Calendar.MONTH);
	}
	
	public long getItemHoldTimeInSeconds () {
		return (returnDate.getTime() - borrowDate.getTime())/1000;
	}
}

