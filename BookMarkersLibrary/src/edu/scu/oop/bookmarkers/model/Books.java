package edu.scu.oop.bookmarkers.model;

public abstract class Books extends Item {
	public Books (String title, String author, double itemPrice) {
		super (title, author, itemPrice);
	}
	
	public int getMaxTimeToHoldInSeconds () {
		return 2*30; //Books can be checkedout for 2 weeks, here 1 week = 30 sec
	}



	
}
