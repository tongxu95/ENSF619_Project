package CustomerModel;

import java.time.LocalDate;
import java.util.ArrayList;
import Model.Movies;

/*
 *  RegisteredUser.java
 *  package: CustomerModel
 *  ENSF 619 - Term Project - Movie Theater Ticket Reservation App
 * 
 *  Completed by: Group #14
 *  Haixia Wu
 *  Jenny Tong Xu
 *  John Van Heurn
 *  Javier Vite
 * 
 *  Date: November 30 2020
 */

public class RegisteredUser extends User {
    private String username, password;
    
    private LocalDate feeRenewDate;
    
    public RegisteredUser(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    public RegisteredUser(String name, String addr, String bank, int card_no, int expr_date, int cvv, String email, String username, String pwd) {
    	super(name, addr, bank, card_no, expr_date, cvv, email);
    	this.username = username;
    	this.password = pwd;
    }
    
    /**
     * Return true is account is still active or false if renewal is required.
     * @return true is account is active and false otherwise
     */
    public boolean checkFeeRenewal() {
    	LocalDate today = LocalDate.now(); 
    	if (feeRenewDate == null || feeRenewDate.compareTo(today) < 0)
    		return false;
    	else 
    		return true;
    }

	boolean verifyPassword(String pwd) {
		return password.equals(pwd);
	}
    
	public void paidAnnualFee() {
		feeRenewDate = LocalDate.now().plusDays(365);
	}
	

}

