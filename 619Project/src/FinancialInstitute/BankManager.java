package FinancialInstitute;
import java.util.Map;
/*
 *  BankManager.java
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

public class BankManager {
    private Map<String, Bank> banks;
    
    public BankManager(Map<String, Bank> banks) {
    	this.banks = banks;
    }
    
    public boolean processTransaction(String bank, int card_no, int exp_date, int cvv) {
    	Bank theBank = findBank(bank);
//    	if (theBank == null) throw new InvalidBankException();
    	return false;
    }
    
    private Bank findBank(String bank) {
    	return banks.get(bank);
    }
}