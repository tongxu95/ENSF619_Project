package FinancialInstitute;

import java.util.ArrayList;

/*
 *  Bank.java
 *  package: FinancialInstitute
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

public class Bank {

	private String name;
	
	private ArrayList<CreditCard> creditCards;
	
	public Bank(String name, ArrayList<CreditCard> creditCards) {
		this.name = name;
		this.creditCards = creditCards;
	}
	
	public boolean processTransaction(int card_no, int exp_date, int cvv, double payment) {
		for (CreditCard card : creditCards) {
			if (card.getCardNo() == card_no &&
					card.getExpDate() == exp_date &&
					card.getCvv() == cvv) {
				card.processTransaction(payment);
				return true;
			}
		}
		return false;
	}
    
	
}
