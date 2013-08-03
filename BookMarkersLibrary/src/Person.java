/**
 * @author vidyanadig
 *
 */

public class Person {

	// Data members
	private String personName;
	private String emailId;
	private String homeAddress;
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
