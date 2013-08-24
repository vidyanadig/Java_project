package edu.scu.oop.bookmarkers.model;

public final class Video extends Items {
	static double finePerSecond = 0.50; // 50 cents per day
	static int numOfItems;
	
	public Video(String itemId, String title, String author, double itemPrice) {
		super (itemId, "V".concat(title), author, itemPrice);
		numOfItems++;
	}
	
	public double getFinePerSecond () {
		return finePerSecond;
	}

	public int getMaxTimeToHoldInSeconds () {
		return 1*30; //Books can be checkedout for 2 weeks, here 1 week = 30 sec
	}

	
}
