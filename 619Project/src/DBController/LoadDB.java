package DBController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.sql.Date;

import FinancialInstitute.*;
import CustomerModel.*;

public class LoadDB {
	
	private static UserBankDBController userDB = new UserBankDBController();
	
	public static Map<String, Bank> LoadBanks() {
		ResultSet rs = userDB.getBanks();
		ArrayList<String> bankNames = new ArrayList<>();
		Map<String, Bank> banks = new HashMap<>();
		ArrayList<CreditCard> cards;
		
		try {
			do {
				bankNames.add(rs.getString("Name"));
			} while (rs.next());
			
			for (int i = 0; i < bankNames.size(); i++) {
				
				String bankName = bankNames.get(i);
				rs = userDB.getCreditCards(bankName);
				
				cards  = new ArrayList<>();
				do {
					long card_no = Long.parseLong(rs.getString("Card_no"));
					int card_cvv = rs.getInt("Card_cvv");
					int card_exp = rs.getInt("Card_exp");
					cards.add(new CreditCard(card_no, card_exp, card_cvv));
				} while (rs.next());
				
				banks.put(bankName, new Bank(bankName, cards));
				
			}
			
			return banks;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	
	public static Map<Integer, Voucher> loadVouchers() {
		ResultSet rs = userDB.getVouchers();
		Map<Integer, Voucher> vouchers = new HashMap<>();
		
		try {
			do {
				int voucherID = rs.getInt("VoucherID");
				int credit = rs.getInt("Credit");
				Date expr_date = rs.getDate("Expr_Date");
				vouchers.put(voucherID, new Voucher(voucherID, credit, expr_date));
			} while (rs.next());
			
			return vouchers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Map<String, RegisteredUser> loadRegUsers() {
		ResultSet rs = userDB.getRegUsers();
		Map<String, RegisteredUser> regUsers = new HashMap<>();
		
		try {
			do {
				String username = rs.getString("Username");
				String password = rs.getString("Password");
				Date renewDate = rs.getDate("Fee_renew_date");
				String name = rs.getString("Name");
				String addr = rs.getString("Address");
				String bank = rs.getString("Bank");
				String email = rs.getString("Email");
				long card_no = Long.parseLong(rs.getString("Card_no"));
				int card_cvv = rs.getInt("Card_cvv");
				int card_exp = rs.getInt("Card_exp");
				regUsers.put(username, new RegisteredUser(name, addr, bank, card_no, card_exp, card_cvv, email, username, password, renewDate));

			} while (rs.next());
			
			return regUsers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main (String[] args) {
		BankManager bm = new BankManager(LoadDB.LoadBanks());
		
		Map<Integer, Voucher> vouchers = LoadDB.loadVouchers();
		Map<String, RegisteredUser> regUsers = LoadDB.loadRegUsers();
		CustomerManager cm = new CustomerManager(regUsers, vouchers);
		
	}
}