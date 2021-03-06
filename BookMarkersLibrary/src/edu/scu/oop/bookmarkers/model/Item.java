/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author vidyanadig
 *
 */
public abstract class Item implements Serializable{
	protected String itemID;
	private String title;
	private String author;
	private double itemPrice;
	private ItemStates itemState;
	private String itemReservedBy; // memID of the person who reserved it
	private static Integer forItemIDOnly = 100;
	
	public abstract int getMaxTimeToHoldInSeconds ();
	public abstract double getFinePerSecond ();
	public abstract String getItemType();

	public Item () {	
	}
	
	public Item (String title, String author, double itemPrice) {
		forItemIDOnly++;
		this.itemID = forItemIDOnly.toString();
		this.title = title;
		this.author = author;
		this.itemPrice = itemPrice;
		this.itemState = ItemStates.AVAILABLE;
		this.itemReservedBy = null;
	}
	
	public ItemStates getItemState () {
		return itemState;
	}
	
	public String getItemStateString () {
		switch (this.itemState) {
		case CHECKEDOUT:
			return "CHECKEDOUT";
		case AVAILABLE:
			return "AVAILABLE for checkout";
		case CHECKEDOUTANDRESERVED:
			return "CHECKEDOUTANDRESERVED";
		case RESERVED:
			return "RESERVED";
		}
		return null;
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
	public String getAuthor () {
		return author;
	}
	
	public void setItemReservedBy (String memId) {
		this.itemReservedBy = memId;
	}
	
	public String getItemReservedBy () {
		return this.itemReservedBy;
	}
	
	public static void setNumOfItemsInEachCategory (Map<String, Item> itemsMap) {
		Iterator<Item> iterItemsMap = itemsMap.values().iterator();
		while (iterItemsMap.hasNext()) {
			Item i = iterItemsMap.next();
			if (i instanceof Fiction) {
				Fiction.numOfItems++;
			} else if (i instanceof NonFiction) {
				NonFiction.numOfItems++;
			} else if (i instanceof Video) {
				Video.numOfItems++;
			}
		}
	}
}
