package edu.scu.oop.bookmarkers.model;

public final class NonFiction extends Books {
		private static double finePerSecond = 0.30;
		public static int numOfItems;
		
		public NonFiction(String title, String author, double itemPrice) {
			super ("NF".concat(title), author, itemPrice);
			numOfItems++;
		} 
		
		public double getFinePerSecond () {
			return finePerSecond;
		}

		
		public String getItemType() {
			return "NonFiction";
		}
		
		public static int getNumOfItems () {
			return numOfItems;
		}
		
}
