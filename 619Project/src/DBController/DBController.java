package DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBController {
	
	// JDBC driver name and database URL
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost";

	// Database credentials
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	// TODO: fill in password
	
	// Attributes
	protected Connection conn;//Object of type connection from the JDBC class that deals with connecting to 
	//the database 
	protected PreparedStatement stmt; //object of type statement from JDBC class that enables the creation "Query 
	//statements"
	protected ResultSet rs;//object of type ResultSet from the JDBC class that stores the result of the query

	public DBController() {
		try {
            //Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			String query = "USE movie;";
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Problem");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
