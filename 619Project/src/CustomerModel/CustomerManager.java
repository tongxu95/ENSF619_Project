package CustomerModel;

import java.util.Map;
import java.util.Random;

import Model.MovieTicket;
import CustomException.*;

/*
 *  CustomerManager.java
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

public class CustomerManager {
    private Map<String, RegisteredUser> registeredUsers;
    private Map<Integer, Voucher> vouchers;
    
    public CustomerManager(Map<String, RegisteredUser> registeredUsers, Map<Integer, Voucher> vouchers) {
    	this.registeredUsers = registeredUsers;
    	this.vouchers = vouchers;
    }
    
    // after login, call checkFeeRenewal; if false, call processTransaction() for annual fee payment
    public RegisteredUser login (String username, String password) throws InvalidUsernameException, 
    InvalidPasswordException {
    	RegisteredUser theUser = registeredUsers.get(username);
    	if (theUser == null) throw new InvalidUsernameException();
    	else {
    		if (theUser.verifyPassword(password)) return theUser;
    		else throw new InvalidPasswordException();
    	}
    }
    
    public User createTempUser(String name, String addr, String bank, long card_no, int expr_date, int cvv, String email) {
    	return new User(name, addr, bank, card_no, expr_date, cvv, email);
    }
    
    // for registration, call processTransaction() first before calling registerUser() 
    public RegisteredUser registerUser(String name, String addr, String bank, long card_no, int expr_date, int cvv, String email, String username, String pwd)
    throws InvalidUsernameException {
    	RegisteredUser newUser = new RegisteredUser(name, addr, bank, card_no, expr_date, cvv, email, username, pwd);
    	newUser.paidAnnualFee();
    	System.out.println(registeredUsers.keySet());
    	if (registeredUsers.containsKey(username)) throw new InvalidUsernameException();
    	registeredUsers.put(username, newUser);
    	return newUser;
    }
    
    public boolean checkFeeRenewal (RegisteredUser regUser) {
    	return regUser.checkFeeRenewal();
    }
    
    public String sendTicket(User user, MovieTicket ticket) {
    	return user.sendTicket(ticket);
    }
    
    public String creatVoucher(double credit) {
    	int voucherID = 10000 + new Random().nextInt(90000);
    	while (vouchers.containsKey(voucherID)) voucherID = 10000 + new Random().nextInt(90000);
    	
    	Voucher myVoucher = new Voucher(voucherID, credit);
    	vouchers.put(voucherID, myVoucher);
    	return myVoucher.toString();
    }
    
    public double useVoucher(int voucherID, double price) throws InvalidVoucherException,
    ExpiredVoucherException {
    	Voucher myVoucher = vouchers.get(voucherID);
    	if (myVoucher == null) throw new InvalidVoucherException();
    	// if remainingBalance if greater than zero, remove voucher
    	else {
    		if (! myVoucher.isValid()) throw new ExpiredVoucherException();
        	double remainingBalance = myVoucher.useVoucher(price);
        	if (remainingBalance > 0) vouchers.remove(voucherID);
        	return remainingBalance;
    	}
    }
    
    public double getVoucherCredit(int voucherID) throws InvalidVoucherException {
    	Voucher myVoucher = vouchers.get(voucherID);
    	if (myVoucher == null) throw new InvalidVoucherException();
    	return myVoucher.getCredit();
    }
    
    
} 

