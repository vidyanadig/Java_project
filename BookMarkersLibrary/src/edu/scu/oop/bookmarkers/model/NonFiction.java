package edu.scu.oop.bookmarkers.model;

public final class NonFiction extends Books {
		static double finePerSecond = 0.30;
		static int numOfItems;
		
		public NonFiction(String itemId, String title, String author, double itemPrice) {
			super (itemId, "NF".concat(title), author, itemPrice);
			numOfItems++;
		} 
		
		public double getFinePerSecond () {
			return finePerSecond;
		}

		
		
		
}
