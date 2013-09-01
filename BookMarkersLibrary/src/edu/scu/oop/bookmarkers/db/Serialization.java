package edu.scu.oop.bookmarkers.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.scu.oop.bookmarkers.model.Books;
import edu.scu.oop.bookmarkers.model.FineObject;
import edu.scu.oop.bookmarkers.model.Item;
import edu.scu.oop.bookmarkers.model.LibraryMember;
import edu.scu.oop.bookmarkers.model.Transaction;

/**
 * @author vidyanadig
 * 
 */
public class Serialization implements Serializable  {
	private static String storeFinesObj = "fines.ser";
	private static String storeTransationObj = "transactions.ser" ;
	private static String storeLibMemObj = "libmembers.ser";
	private static String storeItemsObj = "items.ser";

	
	//Load all members into LibraryMembers hash map
	public static Map <String, LibraryMember> loadMembersIntoHashMap () 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		
		ObjectInputStream in;
		
		// Try to open the inputstream and see if the file is present.
		in = new ObjectInputStream(new FileInputStream(storeLibMemObj));

		// File is present, lets read objects from it
		Map <String, LibraryMember> m = (Map <String, LibraryMember>)in.readObject();

		in.close();
		return m;
	}

	//Write all members from LibraryMembers hash map to file
	public static void writeMembersFromHashMapIntoFile (Map <String, LibraryMember> m) 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectOutputStream out;

		// Try to open the output stream and see if the file is present.
		out = new ObjectOutputStream(new FileOutputStream(storeLibMemObj));

		// File is present, lets write objects to it	
		out.writeObject(m);
		out.flush();
		out.close();
	}

	public static Map <String, Item> loadItemsIntoHashMap () throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in;
		
		// Try to open the inputstream and see if the file is present.
		in = new ObjectInputStream(new FileInputStream(storeItemsObj));

		// File is present, lets read objects from it
		Map <String, Item> m = (Map <String, Item>)in.readObject();

		in.close();
		System.out.println("returning from loading items");
		return m;
		
	}
	
	public static void writeItemsIntoFile (Map <String, Item> m) 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream out;
		
		// Try to open the outputstream and see if the file is present.
		out = new ObjectOutputStream(new FileOutputStream(storeItemsObj));

		// File is present, lets read objects from it
		out.writeObject(m);
		out.flush();
		out.close();
		
	}
	
	
	//Load all fines from Fines List
	public static List<FineObject> loadFinesFromFinesFile () 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream in;

		// Try to open the input stream and see if the file is present.
		in = new ObjectInputStream(new FileInputStream(storeFinesObj));

		// File is present, lets read the list from it
		List<FineObject> l = (List<FineObject>)in.readObject();
		
		in.close();
		return l;
	}
			
		
	//Write all fines into Fines file
	public static void writeFinesToFinesFile (List<FineObject> l) 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectOutputStream out;

		// Try to open the output stream and see if the file is present.
		out = new ObjectOutputStream(new FileOutputStream(storeFinesObj));

		// File is present, lets write objects to it
		out.writeObject(l);

		out.flush();
		out.close();
	}
	
	//Load all transactions into transaction list
		public static Map<String, List<Transaction>> loadTransactionIntoHashMap () 
				throws IOException, FileNotFoundException, ClassNotFoundException {
			
			ObjectInputStream in;
			
			// Try to open the inputstream and see if the file is present.
			in = new ObjectInputStream(new FileInputStream(storeTransationObj));

			// File is present, lets read objects from it
			Map<String, List<Transaction>> transactionMap = (Map<String, List<Transaction>>)in.readObject();

			in.close();
			return transactionMap;
		}

		//Write all transactions from hash map to file
		public static void writeTransactionFromHashMapIntoFile (Map<String, List<Transaction>> transactionMap) 
				throws IOException, FileNotFoundException, ClassNotFoundException {
			ObjectOutputStream out;

			// Try to open the output stream and see if the file is present.
			out = new ObjectOutputStream(new FileOutputStream(storeTransationObj));

			// File is present, lets write objects to it	
			out.writeObject(transactionMap);
			out.flush();
			out.close();
		}

}
