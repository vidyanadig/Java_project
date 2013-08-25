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

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import javax.mail.internet.*;
import  javax.mail.Message;

import com.sun.mail.smtp.SMTPTransport;

import edu.scu.oop.bookmarkers.db.Serialization;


/**
 * @author vidyanadig
 *
 */
public class Library {
	private static Library myLibrary;
	private Map<String, LibraryMember> libraryMembers;
	private List<FineObject> fineList;
	private Map<String, List<Transaction>> transactionMap;
	private Map<String, Items> itemsMap;
	
	
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
		System.out.print("Loaded Members from file");
		fineList = Serialization.loadFinesFromFinesFile();
		System.out.print("Loaded fines from DB");
		transactionMap = Serialization.loadTransactionIntoHashMap();
		System.out.print("Loaded transactions from file");

	}

	// Write to backend related functions
	public void writeValuesToDB () throws FileNotFoundException, ClassNotFoundException, IOException {
		Serialization.writeMembersFromHashMapIntoFile(libraryMembers);
		System.out.print("Wrote Members into file");
		Serialization.writeFinesToFinesFile(fineList);
		System.out.print("Wrote Members into file");
		Serialization.writeTransactionFromHashMapIntoFile(transactionMap);
		System.out.print("Wrote transactions into file");
	}

	public LibraryMember getLibraryMemberIfPresent (String memId) {
		return (libraryMembers.get(memId));
	}
	
	/* 
	 * Called when a new library member registers to this Springfield Library.
	 * This function will be called from the GUI. This will in turn call the Databaseconnection method
	 * to add the member into the backend database. If that succeeds , we add the member to our Hash map
	 * which is stored in memory. So whenever a user registers, we add in the database + in the hash map.
	 */
	public void newLibraryMemberRegistration (String name, String email, String address, String phoneNum, String county) 
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
	}

	/*
	 * This method is called for every item which the user checks out.
	 * If the user checks out 5 items, then this method is called 5 times.
	 */
	public void newCheckoutTransactionByMember (String memID, String itemID) {
		// First, check if this memID is valid and no fines pending
		LibraryMember l = getLibraryMemberIfPresent(memID) ;
		if (l == null || l.hasMemberPaidFines() == Boolean.FALSE) {
			System.out.println("MemID not found, invalid transaction or no fines paid!!");
			return;
		}

		Items i = itemsMap.get(itemID);
		if (i == null) {
			// Item does not exist in database. Return error.
			System.out.println("ItemID not found !!");
			return;
		}
		
		switch (i.getItemState()) {
		case AVAILABLE:
			i.setItemState(ItemStates.CHECKEDOUT);
			break;
		case RESERVED:
			if (i.getItemReservedBy() == memID) {
				//This person has reserved the item. Let him check it out.
				i.setItemState(ItemStates.CHECKEDOUT);
				i.setItemReservedBy(null);
			} else {
				//Return error. TODO
				System.out.println("This person has not reserved this item, someone else has");
				return;
			}
			break;
		default:
			// TODO
			System.out.println("Should not be here at all, Check what is happening");
			return;
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
		
		/*
		 * Finally, add the transaction object to the corresponding 
		 * transaction list for this memID.
		 */
		transactionMap.get(memID).add(new Transaction(memID, itemID));
	}
	
	private void sendEmail (String emailID, String itemTitle) throws UnsupportedEncodingException {
		//1. Set some properties
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		Properties props = new Properties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);

        props.put("mail.smtps.quitwait", "false");
        
        //2. Set the msg body
        String msgBody = "The book you reserved " + itemTitle + " is available! Please stop by.";
        
        //3. Create a new message
        try {
            Message msg = new MimeMessage(session);
            //msg.setFrom(new InternetAddress("mailnadig@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
            msg.setSubject("Your Book is available at Bookmarkers!");
            msg.setText(msgBody);
            msg.setSentDate(new Date());
            
            System.out.println("Sending email !! This might take a while");

            SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
            //4. Send out email
            t.connect("smtp.gmail.com", "coen275java@gmail.com", "exceptions");
            t.sendMessage(msg, msg.getAllRecipients());      
            t.close();
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        }
	}
	
	/*
	 * Called when a user scans an Item to return
	 */
	public void newReturnTransactionByMember (String memID, String itemID) throws UnsupportedEncodingException {
		// First, check if this memID is valid
		LibraryMember l = getLibraryMemberIfPresent(memID);
		if (l == null) {
			System.out.println("MemID not found, invalid transaction !!");
			return;
		}

		if (transactionMap != null) {
			if (transactionMap.containsKey(memID)) {
				Iterator<Transaction> oldCheckoutTransactionList = transactionMap.get(memID).iterator();
				while(oldCheckoutTransactionList.hasNext()) {
					Transaction t = oldCheckoutTransactionList.next();
					if (t.getReturnDate() == null && t.getItemID() == itemID) {
						/* 
						 * We have found the checkout transaction. 
						 * Set its return date to current date.
						 */
						t.setReturnDate();
						
						/* 
						 * Check if we need to pay any fine for this item
						 * TODO: add items hashmap
						 */
						Items i = itemsMap.get(itemID);
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
								break;
							case CHECKEDOUTANDRESERVED:
								i.setItemState(ItemStates.RESERVED);
								// Send an email to the person who had reserved this book.
								sendEmail(libraryMembers.get(i.getItemReservedBy()).getEmailId(), i.getItemTitle());
								break;
							default:
								// We should not come here at all
								i.setItemState(ItemStates.AVAILABLE);
								System.out.println("Something wrong here in return !!");
								break;			
						}
						
						break;
					}
				}

			}
		}
	}
	
	/*
	 * Called when a user pays his/her fines. Add an entry to the FinesDB and update the List which is held
	 * in memory.
	 */
	public void finesPaidByLibraryMember (String memId, double finePaid) {
		LibraryMember l = getLibraryMemberIfPresent(memId);
		if (l != null) {
			FineObject f = new FineObject(memId, new Date(), finePaid);
			if (fineList == null) {
				fineList = new ArrayList<FineObject>();
			}
			fineList.add(f);
			l.memberPaysFines();
		} else {
			System.out.println("No user found in database ! ");
		}

	}
	
	// Total fines collected by Library between "from" and "to" dates
	public double totalFinesCollectedByLibrary (Date fromDate, Date toDate) {
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
		for (Items item : itemsMap.values()) {
		    if (item.getItemState() == ItemStates.CHECKEDOUT) {
		    	checkedOutCount++;
		    }
		}
		return checkedOutCount;
	}
	
	public Items mostCheckedOutBookInAMonth (int month) {
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
		
		// Now that we have a map of (itemId,numberOfCheckouts), find the largest number.
		Map.Entry<String, Integer> maxEntry = null;
		for(Map.Entry <String, Integer> entry: mostCheckedMap.entrySet()) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			}
		}
		return itemsMap.get(maxEntry.getKey());
	}
	
	// Called when a person queries for an Item
	public List<Items> queryIfItemAvailable (String searchStr) {
		Iterator<Items> listOfItemsPresent = itemsMap.values().iterator();
		List <Items> listOfItemsFromSearchResult = new ArrayList <Items>();
		while (listOfItemsPresent.hasNext()) {
			Items itemObj = listOfItemsPresent.next();
			// If searchStr is a part of the itemID or the item title, add to the list 
			if (itemObj.getItemID().contains(searchStr) || itemObj.getItemTitle().contains(searchStr)) {				
				//List all items avail/not avail .. no if (itemObj.getItemState() == ItemStates.AVAILABLE)
				listOfItemsFromSearchResult.add(itemObj);
			}
		}
		return listOfItemsFromSearchResult;
	}
	
	public void reserveItem (String itemId, String memID) {
		if (itemsMap.containsKey(itemId) && getLibraryMemberIfPresent(memID) != null) {
			/*
			 * If we have the item in our database 
			 * (we should be having it already, else we won't come here)
			 */
			Items i = itemsMap.get(itemId);
			/*
			 *  We can reserve Item only if it was checkedout.
			 *  If it was reserved already, we cannot reserve again.
			 *  If it is available, user has to check it out (cannot reserve).
			 */
			
			if (i.getItemState() == ItemStates.CHECKEDOUT) { 
				i.setItemState(ItemStates.CHECKEDOUTANDRESERVED);
				i.setItemReservedBy (memID);
			}
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
	 */
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException  {
		// TODO Auto-generated method stub
		Library.getInstance().loadValuesFromDB();
		/*Library.getInstance().newLibraryMemberRegistration("Richard Parker", "richard@gmail.com", "Ottawa", "2323232323", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Jack Sparrow", "jack@gmail.com", "MountainView", "2121212121", "Springfield");
		Library.getInstance().newLibraryMemberRegistration("Katie Holmes", "katie@gmail.com", "Orange County", "1212121212", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Wu Chen", "chen@gmail.com", "RedwoodCity", "3434343434", "Springfield");
				
		Library.getInstance().newLibraryMemberRegistration("Deepika Gopal", "deepika@gmail.com", "San Jose", "4545454545", "SpringField");
		Library.getInstance().newLibraryMemberRegistration("Kim Jong", "kim@gmail.com", "San Jose", "5454545454", "Palo Alto");
		Library.getInstance().newLibraryMemberRegistration("Akshay Kumar", "akshay@gmail.com", "Orange County", "6767676767", "Santa Clara");
		Library.getInstance().newLibraryMemberRegistration("Priyanka Chopra", "chopra@gmail.com", "New Delhi", "7676767676", "Springfield");
	   */
		//Library.getInstance().finesPaidByLibraryMember("Wu434", 10.5);
		
 		Library.getInstance().writeValuesToDB();


	}

}
