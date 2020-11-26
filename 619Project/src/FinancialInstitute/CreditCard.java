package FinancialInstitute;
/*
 *  CreditCard.java
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

public class CreditCard {
	/** 12-digit card number */
    private int card_no;
    
    /** 4 digits expiration date, first two digits for month and last two digits for year*/
    private int exp_date;
    
    /** 3 digits card verification value */
    private int cvv;
    
    /** starting balance is 0 */
    private double balance;
    
    /** default 3000 */
    private double limit;
    
    public CreditCard(int card_no, int exp_date, int cvv) {
    	this.card_no = card_no;
    	this.exp_date = exp_date;
    	this.cvv = cvv;
    	balance = 0;
    	limit = 3000;
    }

	public int getCardNo() {
		return card_no;
	}

	public int getExpDate() {
		return exp_date;
	}

	public int getCvv() {
		return cvv;
	}

	public void processTransaction(double payment) {
		balance += payment;
	}
	
	
}
