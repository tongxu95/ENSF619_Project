package DBController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDBController extends DBController {
	/**
	 * Return registered users in the database.
	 * 
	 * @return ResultSet with data of all the registered user in the database.
	 */
	public ResultSet getRegUsers() {
		String sql = "SELECT * FROM REGISTERED;";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return vouchers in the database.
	 * 
	 * @return ResultSet with data of all the vouchers in the database.
	 */
	public ResultSet getVouchers() {
		String sql = "SELECT * FROM VOUCHER;";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return banks in the database.
	 * 
	 * @return ResultSet with data of all the banks in the database.
	 */
	public ResultSet getBanks() {
		String sql = "SELECT * FROM BANK;";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return credit cards in the database.
	 * 
	 * @return ResultSet with data of all the credit cards in the database.
	 */
	public ResultSet getCreditCards(String bank) {
		String sql = "SELECT * FROM CREDITCARD WHERE BankName=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, bank);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}