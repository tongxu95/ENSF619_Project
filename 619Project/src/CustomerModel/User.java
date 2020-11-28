package CustomerModel;

import Model.MovieTicket;

/*
 *  User.java
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

public class User {

	String name, addr, financial_institution, email;
	int credit_card_no, card_cvv, card_exp;
	
	public User() {}
	
	public User(String name, String addr, String bank, int card_no, int expr_date, int cvv, String email) {
		this.name = name;
		this.addr = addr;
		financial_institution = bank;
		credit_card_no = card_no;
		card_exp = expr_date;
		card_cvv = cvv;
		this.email = email;
		
	}
	
	public String sendTicket(MovieTicket ticket) {
		String confirmation = "Thank you for your purchase.\nPlease find below your ticket:\n";
		confirmation += ticket.toString();
		return confirmation;
	}
    
}