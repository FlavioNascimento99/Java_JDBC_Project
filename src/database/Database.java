package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
	private static Connection connection = null;
	public static Connection getConnection() {
		if (connection == null) {
			try {
				Properties props = loadProperties();
				String URL = props.getProperty("dburl");
				connection = DriverManager.getConnection(URL, props);
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
		
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try { 
				connection.close();
			} catch(SQLException e ) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try { 
				resultSet.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream file = new FileInputStream("database.properties")) { 
			Properties props = new Properties();
			props.load(file);
			return props;
		} catch (IOException e) {
			throw new DatabaseException(e.getMessage());	
		}
	}
}
