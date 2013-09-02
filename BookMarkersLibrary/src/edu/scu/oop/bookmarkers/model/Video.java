package edu.scu.oop.bookmarkers.model;

public final class Video extends Item {
	private static double finePerSecond = 0.50; // 50 cents per day
	public static int numOfItems;
	
	public Video(String title, String author, double itemPrice) {
		super (title, author, itemPrice);
		itemID = "V".concat(itemID);
		numOfItems++;
	}
	
	public double getFinePerSecond () {
		return finePerSecond;
	}

	public int getMaxTimeToHoldInSeconds () {
		return 1*30; //Books can be checkedout for 2 weeks, here 1 week = 30 sec
	}

	public String getItemType() {
		return "Video";
	}

	public static int getNumOfItems () {
		return numOfItems;
	}
	
}
