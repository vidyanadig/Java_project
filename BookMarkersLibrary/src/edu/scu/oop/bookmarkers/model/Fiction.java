package edu.scu.oop.bookmarkers.model;

public final class Fiction extends Books {
	static double finePerSecond = 0.15;
	static int numOfItems;
	
	public Fiction(String itemId, String title, String author, double itemPrice) {
		super (itemId, "F".concat(title), author, itemPrice);
		numOfItems++;
	}
	
	public double getFinePerSecond () {
		return finePerSecond;
	}

	
}
