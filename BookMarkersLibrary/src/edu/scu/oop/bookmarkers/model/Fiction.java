package edu.scu.oop.bookmarkers.model;

public final class Fiction extends Books {
	private static double finePerSecond = 0.15;
	public static int numOfItems;
	
	public Fiction(String title, String author, double itemPrice) {
		super (title, author, itemPrice);
		itemID = "F".concat(itemID);
		numOfItems++;
	}
	
	public double getFinePerSecond () {
		return finePerSecond;
	}
	
	public String getItemType() {
		return "Fiction";
	}

	public static int getNumOfItems () {
		return numOfItems;
	}
	
	
}
