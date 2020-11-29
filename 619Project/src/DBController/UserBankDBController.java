package DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBankDBController {
	
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost";

	// Database credentials
	private final String USERNAME = "root";
	private final String PASSWORD = "JennyXu1020";
	// TODO: fill in password
	
	// Attributes
	protected Connection conn;//Object of type connection from the JDBC class that deals with connecting to 
	//the database 
	protected PreparedStatement stmt; //object of type statement from JDBC class that enables the creation "Query 
	//statements"
	protected ResultSet rs;//object of type ResultSet from the JDBC class that stores the result of the query

	public UserBankDBController() {
		try {
            //Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			String query = "USE movieuser;";
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Problem");
			e.printStackTrace();
		}
	}
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
