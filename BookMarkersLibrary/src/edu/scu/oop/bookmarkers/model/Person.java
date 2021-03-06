package edu.scu.oop.bookmarkers.model;

import java.io.Serializable;

/**
 * @author vidyanadig
 *
 */

public class Person implements Serializable{

	// Data members
	private String personName;
	private String emailId;
	private String homeAddress; // Just a city
	private String phoneNum;
	private String countyName;
	
	public Person () {
		
	}
	
	public Person (String name, String email, String address, String phoneNum, String county) {
		this.personName = name;
		this.emailId = email;
		this.homeAddress = address;
		this.phoneNum = phoneNum;
		this.countyName = county;
	}
	
	public String getPersonName () {
		return personName;
	}
	
	public String getEmailId () {
		return emailId;
	}
	
	public String getHomeAddress () {
		return homeAddress;
	}
	
	public String getPhoneNum () {
		return phoneNum;
	}
	
	public String getCountyName () {
		return countyName;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
