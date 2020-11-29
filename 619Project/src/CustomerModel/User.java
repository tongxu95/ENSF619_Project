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

	private String name;
	private String addr;
	private String financial_institution;
	private String email;
	private int credit_card_no;
	private int card_cvv;
	private int card_exp;
	
	public User() {}
	
	public User(String name, String addr, String bank, int card_no, int expr_date, int cvv, String email) {
		this.name = name;
		this.addr = addr;
		this.financial_institution = bank;
		this.credit_card_no = card_no;
		this.card_exp = expr_date;
		this.card_cvv = cvv;
		this.email = email;
		
	}
	
	public String sendTicket(MovieTicket ticket) {
		String confirmation = "Thank you for your purchase.\nPlease find below your ticket:\n";
		confirmation += ticket.toString();
		return confirmation;
	}

	public String getName() {
		return name;
	}

	public String getAddr() {
		return addr;
	}

	public String getFinancial_institution() {
		return financial_institution;
	}

	public String getEmail() {
		return email;
	}

	public int getCredit_card_no() {
		return credit_card_no;
	}

	public int getCard_exp() {
		return card_exp;
	}

	public int getCard_cvv() {
		return card_cvv;
	}
    
}