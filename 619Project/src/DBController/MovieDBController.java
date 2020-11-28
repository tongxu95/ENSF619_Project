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
	 * Return registered users in the database.
	 * 
	 * @return ResultSet with data of all the registered user in the database.
	 */
	public ResultSet getVouchers() {
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
}