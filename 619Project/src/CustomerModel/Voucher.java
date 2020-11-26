package CustomerModel;

import java.time.LocalDate;
import java.util.Random;

/*
 *  Voucher.java
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

public class Voucher {
	private int voucherID;
    private double credit;
    private LocalDate expr_date;
    
    public Voucher(int voucherID, double credit) {
    	this.voucherID = voucherID;
    	this.credit = credit;
    	expr_date = LocalDate.now().plusDays(365);
    }
    
    public boolean isValid() {
    	return expr_date.compareTo(LocalDate.now()) > 0;
    }
    
    public double getCredit() {
    	return credit;
    }
    
    /**
     * Pay with voucher. If credit is greater than or equal to price, return 0; else the remaining price to be paid is returned.
     * @param price 
     * @return remaining price to be paid. 
     */
    public double useVoucher(double price) {
    	if (credit >= price) {
        	credit -= price;
        	return 0;
    	} else {
    		credit = 0;
    		return price - credit;
    	}
    }
    
    @Override
    public String toString() {
    	String info = "******VOUCHER******\n";
    	info += "Voucher ID: " + voucherID + "\n";
    	info += "Credit: " + credit + "\n";
    	info += "Expiration Date: " + expr_date + "\n";
    	return info;
    }
    
    
}
