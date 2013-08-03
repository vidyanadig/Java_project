/**
 * 
 */

/**
 * @author vidyanadig
 *
 */
public class NonCountyMember extends LibraryMember {
	public NonCountyMember (String name, String email, String address, String phoneNum, String county) {
		super (name,  email,  address,  phoneNum,  county);
		fineAmount = getMembershipFee();
		areFinesPaid = (fineAmount > 0 ? Boolean.TRUE : Boolean.FALSE);
	}
	
	public double getMembershipFee () {
		return 10;
	}

}
