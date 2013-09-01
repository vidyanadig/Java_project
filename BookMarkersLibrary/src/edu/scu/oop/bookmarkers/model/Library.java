/**
 * 
 */
package edu.scu.oop.bookmarkers.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import edu.scu.oop.bookmarkers.db.Serialization;
import edu.scu.oop.bookmarkers.gui.LibraryGUI;


/**
 * @author vidyanadig
 *
 */
public class Library {
	private static Library myLibrary;
	private Map<String, LibraryMember> libraryMembers;
	private List<FineObject> fineList;
	private Map<String, List<Transaction>> transactionMap;
	private Map<String, Item> itemsMap;
	
	
	public static String countyName = "Springfield";

	private Library ()  {
		//loadMembersFromDB ();
	}

	public static Library getInstance ()  {
		if (myLibrary == null) {
			myLibrary = new Library();
		}

		return myLibrary;
	}

	// Load from backend related functions
	public void loadValuesFromDB () throws FileNotFoundException, ClassNotFoundException, IOException {
		libraryMembers = Serialization.loadMembersIntoHashMap();
		LibraryMember.numberOfMembers = libraryMembers.size();
		System.out.println("Loaded Members from file");

		fineList = Serialization.loadFinesFromFinesFile();
		System.out.println("Loaded fines from DB");
		
		transactionMap = Serialization.loadTransactionIntoHashMap();
		System.out.println("Loaded transactions from file");
		
		itemsMap = Serialization.loadItemsIntoHashMap() ;
		Item.setNumOfItemsInEachCategory(itemsMap);
		System.out.println("Loaded  "+ itemsMap.size() + "items from file");
	}

	// Write to backend related functions
	public void writeValuesToDB () throws FileNotFoundException, ClassNotFoundException, IOException {
		Serialization.writeMembersFromHashMapIntoFile(libraryMembers);
		System.out.println("Wrote Members into file");
		Serialization.writeFinesToFinesFile(fineList);
		System.out.println("Wrote Members into file");
		Serialization.writeTransactionFromHashMapIntoFile(transactionMap);
		System.out.println("Wrote transactions into file");
		Serialization.writeItemsIntoFile(itemsMap);
		System.out.println("Wrote " + itemsMap.size() + "items into file");
	}

	public LibraryMember getLibraryMemberIfPresent (String memId) {
		return (libraryMembers.get(memId));
	}
	
	public Item getItemIfPresent (String ItemID) {
		return (itemsMap.get(ItemID));
	}
	
	/* 
	 * Called when a new library member registers to this Springfield Library.
	 * This function will be called from the GUI. This will in turn call the Databaseconnection method
	 * to add the member into the backend database. If that succeeds , we add the member to our Hash map
	 * which is stored in memory. So whenever a user registers, we add in the database + in the hash map.
	 */
	public LibraryMember newLibraryMemberRegistration (String name, String email, String address, String phoneNum, String county) 
	{
		LibraryMember l;
		if (county == countyName) {
			l = new CountyMember(name, email, address, phoneNum, county);
		} else {
			l = new NonCountyMember(name, email, address, phoneNum, county);
		}
		// If this is the first LibraryMember of this Library
		if (libraryMembers == null) {
			libraryMembers = new HashMap<String, LibraryMember> ();
		}
		/*
		 * Add the library member to the hash map with the memID as the key if there 
		 * was no previous member with the same key
		 */
		
		if (!libraryMembers.containsKey(l.getMembershipCardId())) {
			libraryMembers.put(l.getMembershipCardId(), l);
		} else {
			// TODO, raise exception
			System.out.println("Previous same memID found, not adding !!");
		}
		System.out.println("Yay, added " + name);
		return l;
	}

	// For Lib admin to add new items
	public void newLibraryItemRegistration (String title, String author, double price, String type) 
	{
		Item i;
		if (type == "F") {
			i = new Fiction(title, author, price);
		} else if (type == "NF" ) {
			i = new NonFiction(title, author, price);
		} else { //if (type == "V") {
			i = new Video (title, author, price);
		}
		// If this is the first Item in this Library
		if (itemsMap == null) {
			itemsMap = new HashMap<String, Item> ();
		}
		/*
		 * Add the item to the hash map with the itemID as the key if there 
		 * was no previous item with the same key
		 */
		
		if (!itemsMap.containsKey(i.getItemID())) {
			itemsMap.put(i.getItemID(), i);
		} else {
			// TODO, raise exception
			System.out.println("Previous same itemID found, not adding !!");
		}
		System.out.println("Yay, added " + title);
	}
	
	/*
	 * This method is called for every item which the user checks out.
	 * If the user checks out 5 items, then this method is called 5 times.
	 */
	public int newCheckoutTransactionByMember (String memID, String itemID) {
		// First, check if this memID is valid and no fines pending
		LibraryMember l = getLibraryMemberIfPresent(memID) ;
		if (l == null || l.hasMemberPaidFines() == Boolean.FALSE) {
			System.out.println("MemID not found, invalid transaction or no fines paid!!");
			return -1;
		}

		Item i = itemsMap.get(itemID);
		if (i == null) {
			// Item does not exist in database. Return error.
			System.out.println("ItemID not found !!");
			return -2;
		}
		
		/*
		 *  Consider the case when there were no previous transactions. 
		 *  This case is true when the first transaction of the library takes place.
		 */
		if (transactionMap == null) { 
			transactionMap = new HashMap <String, List <Transaction>>();
		} 
		
		/*
		 * There was no previous transaction from this member. 
		 * So create a new transaction list against his/her memID.
		 */
		if (!transactionMap.containsKey(memID)) {
			transactionMap.put(memID, new ArrayList<Transaction>());
		}
		
		if (transactionMap != null) {
			int numFict = 0 , numNonFict = 0 , numVideo = 0;
			// Find from transaction Map if user has borrowed 
			Iterator<Transaction> oldCheckoutTransactionList = transactionMap.get(memID).iterator();
			while (oldCheckoutTransactionList.hasNext()) {
				Transaction t = oldCheckoutTransactionList.next();
				// For all unreturned Items. Check what type they are. 
				if (t.getReturnDate() == null) {
					// This means this is a borrowed item still. Not yet returned. 
					Item checkItem = (itemsMap.get(t.getItemID()));

					// We now compare if it is a fiction/nonfiction/video
					if(checkItem.getItemType() == "Fiction") {
						numFict++; // Max is 3
						if (i.getItemType() == "Fiction") {
							if (numFict == 3) {
								// Cannot borrow this book.
								return -5;
							}
						}
					} else if (checkItem.getItemType() == "NonFiction") {
						numNonFict++; //Max is 2.
						if (i.getItemType() == "NonFiction") {
							if (numNonFict == 2) {
								// Cannot borrow this book.
								return -5;
							}
						}
					} else if (checkItem.getItemType() == "Video") {
						numVideo++; // Max is 2
						if (i.getItemType() == "Video") {
							if (numVideo == 2) {
								// Cannot borrow this book.
								return -5;
							}
						}
					}
				}
			}

		}
		
		switch (i.getItemState()) {
		case AVAILABLE:
			i.setItemState(ItemStates.CHECKEDOUT);
			break;
		case RESERVED:
			if (i.getItemReservedBy().toLowerCase().equals(memID.toLowerCase())) {
				//This person has reserved the item. Let him check it out.
				i.setItemState(ItemStates.CHECKEDOUT);
				i.setItemReservedBy(null);
			} else {
				//Return error. TODO
				System.out.println("This person has not reserved this item, someone else has");
				return -3;
			}
			break;
		default:
			// TODO
			System.out.println("Should not be here at all, Check what is happening");
			return -4;
		}
		
		
		
		/*
		 * Finally, add the transaction object to the corresponding 
		 * transaction list for this memID.
		 */
		transactionMap.get(memID).add(new Transaction(memID, itemID));
		return 1;
	}
	
	
	/*
	 * Called when a user scans an Item to return
	 */
	public int newReturnTransactionByMember (String memID, String itemID) throws UnsupportedEncodingException {
		// First, check if this memID is valid
		LibraryMember l = getLibraryMemberIfPresent(memID);
		if (l == null || !itemsMap.containsKey(itemID)) {
			System.out.println("MemID/ItemID not found, invalid transaction !!");
			return -1; // MemID/ItemID not valid
		}

		if (transactionMap != null) {
			if (transactionMap.containsKey(memID)) {
				Iterator<Transaction> oldCheckoutTransactionList = transactionMap.get(memID).iterator();
				while(oldCheckoutTransactionList.hasNext()) {
					Transaction t = oldCheckoutTransactionList.next();
					if ((t.getReturnDate() == null) && (t.getItemID().equals(itemID))) {
						System.out.println("We found one itemid, so transaction map is ok ");
						/* 
						 * We have found the checkout transaction. 
						 * Set its return date to current date.
						 */
						t.setReturnDate();
						
						/* 
						 * Check if we need to pay any fine for this item
						 * TODO: add items hashmap
						 */
						Item i = itemsMap.get(itemID);
						if (i.getMaxTimeToHoldInSeconds() < (t.getItemHoldTimeInSeconds())) {
							/*
							 * We have exceeded our borrow time limit. 
							 * Ask user to pay fine = late fee per second * number of seconds late.
							 */
							double fine = itemsMap.get(itemID).getFinePerSecond() * 
									(t.getItemHoldTimeInSeconds() - i.getMaxTimeToHoldInSeconds());
							if (fine > itemsMap.get(itemID).getItemPrice()) {
								fine = itemsMap.get(itemID).getItemPrice();
							}
							l.setFineAmount(fine);  
						}
						
						//Set the item state to Available
						switch (i.getItemState()) {
							case CHECKEDOUT:
								i.setItemState(ItemStates.AVAILABLE);
								return 0;
							case CHECKEDOUTANDRESERVED:
								i.setItemState(ItemStates.RESERVED);
								/*
								 * Send an email to the person who had reserved this book.
								 * This takes a lot of time, so create another thread.
								 */
								
								Thread emailThread = new Thread(new sendEmail(libraryMembers.get(i.getItemReservedBy()).getEmailId(), i.getItemTitle()));
								emailThread.start();
								return 0;
							default:
								// We should not come here at all
								i.setItemState(ItemStates.AVAILABLE);
								System.out.println("Something wrong here in return !!");
								return 0;			
						}
					} else {
						System.out.println("Itemid date " + t.getItemID() + t.getReturnDate() + itemID);

					}
				} 
				return -3; // Had you borrowed this item at all?

			} else {
				return -2; // No item borrowed at all by this member
			}
		} else {
			return -2;
		}
	}
	
	/*
	 * Called when a user pays his/her fines. Add an entry to the FinesDB and update the List which is held
	 * in memory.
	 */
	public boolean finesPaidByLibraryMember (String memId, double finePaid) {
		LibraryMember l = getLibraryMemberIfPresent(memId);
		if (l != null) {
			FineObject f = new FineObject(memId, new Date(), finePaid);
			if (fineList == null) {
				fineList = new ArrayList<FineObject>();
			}
			fineList.add(f);
			l.memberPaysFines();
			return true;
		} else {
			System.out.println("No user found in database ! ");
			return false;
		}

	}
	
	/*
	 * Called when a user pays his/her fines. Add an entry to the FinesDB and update the List which is held
	 * in memory.
	 */
	public double lookupFinesForLibraryMember (String memId) {
		LibraryMember l = getLibraryMemberIfPresent(memId);
		if (l != null) {
			return l.getFineAmount();
		} else {
			System.out.println("No user found in database ! ");
			return -1;
		}

	}
	
	// Total fines collected by Library between "from" and "to" dates
	public Double totalFinesCollectedByLibrary (Date fromDate, Date toDate) {
		Iterator<FineObject> fineIter = fineList.iterator();
		double totalfinesCollected = 0;
		while (fineIter.hasNext()) {
			FineObject f = fineIter.next();
			Date dateOfFinePayment = f.getDateOfPayment();
			// If the date of payment of fine is between from and to date, add that fine.
			if (!dateOfFinePayment.before(fromDate) && !dateOfFinePayment.after(toDate)) {
				totalfinesCollected += f.getFine();
			}
		}
		return totalfinesCollected;
	}

	public int totalNumOfItemsCheckedOut () {
		int checkedOutCount = 0;
		for (Item item : itemsMap.values()) {
		    if (item.getItemState() == ItemStates.CHECKEDOUT) {
		    	System.out.println("Checkout items inc");
		    	checkedOutCount++;
		    }
		}
		return checkedOutCount;
	}
	
	public String mostCheckedOutBookInAMonth (int month) {
		//Iterator of list of transactions. This will just give a iter or all the transaction lists.
		Iterator<List<Transaction>> oldCheckoutTransactionIter = transactionMap.values().iterator();
		Iterator<Transaction> transIter;
		Map<String, Integer> mostCheckedMap = new HashMap <String, Integer> ();
		
		while (oldCheckoutTransactionIter.hasNext()) {
			// For every list for each member.. 
			List<Transaction> l = oldCheckoutTransactionIter.next();
			//..create an iterator
			transIter = l.iterator();
			while (transIter.hasNext()) {
				// then get a single transaction from this iterator (in every list)
				Transaction t = transIter.next();
				// If this transaction happened in the month we are interested in and it is a book
				if (t.getBorrowMonth() == month && ((itemsMap.get(t.getItemID())) instanceof Books)) {
					/*
					 * If our mostCheckedMap already contained the key .i.e, if
					 * there are multiple check outs for this item.
					 */
					if (mostCheckedMap.containsKey(t.getItemID())) {
						mostCheckedMap.put(t.getItemID(),(mostCheckedMap.get(t.getItemID()))+1);
					} else {
						// This is the first time we encountered this itemId
						mostCheckedMap.put(t.getItemID(),1);
					}
				}
			}
		}
		
		if (mostCheckedMap.size() != 0) {
			// Now that we have a map of (itemId,numberOfCheckouts), find the largest number.
			Map.Entry<String, Integer> maxEntry = null;
			for(Map.Entry <String, Integer> entry: mostCheckedMap.entrySet()) {
				if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
					maxEntry = entry;
				}
			}
			return itemsMap.get(maxEntry.getKey()).getItemTitle();
		} else {
			return "No Item checked out in this month";
		}
	}
	
	// Called when a person queries for an Item
	public List<Item> queryIfItemAvailable (String searchStr) {
		if (itemsMap != null) {
			Iterator<Item> listOfItemsPresent = itemsMap.values().iterator();
			List <Item> listOfItemsFromSearchResult = new ArrayList <Item>();
			while (listOfItemsPresent.hasNext()) {
				Item itemObj = listOfItemsPresent.next();
				// If searchStr is a part of the itemID or the item title, add to the list 
				if (itemObj.getItemID().toLowerCase().contains(searchStr.toLowerCase()) || 
						itemObj.getItemTitle().toLowerCase().contains(searchStr.toLowerCase())) {				
					//List all items avail/not avail .. no if (itemObj.getItemState() == ItemStates.AVAILABLE)
					listOfItemsFromSearchResult.add(itemObj);
				}
			}
			return listOfItemsFromSearchResult;
		} 
		System.out.println("returning NULL, should not happen");
		return null;
		
	}
	
	public int reserveItem (String itemId, String memID) {
		if (itemsMap.containsKey(itemId) && getLibraryMemberIfPresent(memID) != null) {
			/*
			 * If we have the item in our database 
			 * (we should be having it already, else we won't come here)
			 */
			Item i = itemsMap.get(itemId);
			/*
			 *  We can reserve Item only if it was checkedout.
			 *  If it was reserved already, we cannot reserve again.
			 *  If it is available, user has to check it out (cannot reserve).
			 */
			
			if (i.getItemState() == ItemStates.CHECKEDOUT) { 
				i.setItemState(ItemStates.CHECKEDOUTANDRESERVED);
				i.setItemReservedBy (memID);
				return 1;
			} else {
				return -2 ; // Item state is not checkedout. So you can checkout, not reserve.
			}
		} else {
			return -1; // memID/ItemID wrong
		}
	}
	
	public List<Transaction> queryForTransactionHistory (String memID) {
		if (transactionMap.containsKey(memID)) {
			return transactionMap.get(memID);
		} else 
			return null;
	}
	
	/*
	public static java.sql.Date getTimeInSQLFormat () {
		java.util.Date today = new java.util.Date();
		return (new java.sql.Date(today.getTime()));
	}*/


	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException  {
		// TODO Auto-generated method stub
		 This is for advertisement
		 * Thread libService = new Thread() {
		    public void run() {
		    }
		};
		libService.start();

		// Load our stored values into internal datastructures.
		//Library.getInstance().loadValuesFromDB();

		//Start our GUI window
		//new LibraryGUI();
		


		/*Library.getInstance().newLibraryMemberRegistration("Richard Parker", "richard@gmail.com", "Ottawa", "2323232323", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Jack Sparrow", "jack@gmail.com", "MountainView", "2121212121", "Springfield");
		Library.getInstance().newLibraryMemberRegistration("Katie Holmes", "katie@gmail.com", "Orange County", "1212121212", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Wu Chen", "chen@gmail.com", "RedwoodCity", "3434343434", "Springfield");
				
		Library.getInstance().newLibraryMemberRegistration("Deepika Gopal", "deepika@gmail.com", "San Jose", "4545454545", "SpringField");
		Library.getInstance().newLibraryMemberRegistration("Kim Jong", "kim@gmail.com", "San Jose", "5454545454", "Palo Alto");
		Library.getInstance().newLibraryMemberRegistration("Akshay Kumar", "akshay@gmail.com", "Orange County", "6767676767", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Priyanka Chopra", "chopra@gmail.com", "New Delhi", "7676767676", "Springfield");
		Library.getInstance().newLibraryMemberRegistration("Hermoine Granger", "hermoine@gmail.com", "Orange County", "8787878787", "Santa Clara");

	   
		Library.getInstance().finesPaidByLibraryMember("Wu434", 10.5);
		Library.getInstance().finesPaidByLibraryMember("Pr676", 4);
		Library.getInstance().finesPaidByLibraryMember("Ka212", 10);
		Library.getInstance().finesPaidByLibraryMember("Wu434", 12.5);

			Library.getInstance().newLibraryItemRegistration("Kite Runner", "Khaled Hosseni", 20, "V"); 
			Library.getInstance().newLibraryItemRegistration("State machines", "Ulman", 35.5, "NF"); 
			Library.getInstance().newLibraryItemRegistration("Diary of a wimpy kid", "Jeff Kinney", 8.37, "F"); 
			Library.getInstance().newLibraryItemRegistration("Boy on the wooden box", "Leon Leson", 12.23, "F"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the philosophers stone", "JK Rowling", 25, "F"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the prisoner of Askaban", "JK Rowling", 25, "F"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the half-blood prince", "JK Rowling", 25, "F"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the order of Phoenix", "JK Rowling", 25, "F"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the chamber of secrets", "JK Rowling", 2.5, "V"); 
			Library.getInstance().newLibraryItemRegistration("Harry Potter and the goblet of fire", "JK Rowling", 3.5, "V"); 

			Library.getInstance().newLibraryItemRegistration("NatGeo: Penguins", "Nat Geo", 30.5, "NF");
			Library.getInstance().newLibraryItemRegistration("NatGeo: Sea Turtles", "Nat Geo", 30.5, "NF");
			Library.getInstance().newLibraryItemRegistration("NatGeo: Elephants", "Nat Geo", 40.5, "NF");

		// Save our internal datastructures into storage .
 		Library.getInstance().writeValuesToDB();
 		


	}*/

}
