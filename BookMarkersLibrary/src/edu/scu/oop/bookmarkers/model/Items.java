/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.io.Serializable;

/**
 * @author vidyanadig
 *
 */
public abstract class Items implements Serializable{
	String itemID;
	String title;
	String author;
	double itemPrice;
	ItemStates itemState;
	String itemReservedBy; // memID of the person who reserved it
	
	public abstract int getMaxTimeToHoldInSeconds ();
	public abstract double getFinePerSecond ();

	public Items () {	
	}
	
	public Items (String itemId, String title, String author, double itemPrice) {
		this.itemID = itemId;
		this.title = title;
		this.author = author;
		this.itemPrice = itemPrice;
	}
	
	public ItemStates getItemState () {
		return itemState;
	}
	
	public void setItemState (ItemStates state) {
		this.itemState = state;
	}
	
	public String getItemID() {
		return itemID;
	}
	
	public String getItemTitle () {
		return title;
	}
	
	public double getItemPrice () {
		return itemPrice;
	}
	
	public void setItemReservedBy (String memId) {
		this.itemReservedBy = memId;
	}
	
	public String getItemReservedBy () {
		return this.itemReservedBy;
	}
}
