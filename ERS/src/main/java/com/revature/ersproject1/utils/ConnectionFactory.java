package com.revature.ersproject1.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author Brandon Semba
 * 
 * This class utilizes a lazily loaded singleton design pattern
 * to only create a single database connection instance
 *
 */
public class ConnectionFactory {
	
	private static ConnectionFactory cf = null;
	private static Boolean build = true;
	
	/**
	 * Constructs a connection fact
	 */
	private ConnectionFactory() {
		build = false;
	}
	
	/**
	 * Used to create a singleton instance if one has not yet
	 * been created or returns the singleton instance if it has been
	 * instantiated
	 * 
	 * @return a connectionFactory instance
	 */
	public static synchronized ConnectionFactory getInstance() {
		return (build) ? cf = new ConnectionFactory() : cf;
	}
	
	/**
	 * Gets the connection to the database from the driver manager
	 * 
	 * @return a connection to the database
	 */
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		
		try {
			prop.load(new FileReader("C:/Users/defto/my_git_repos/1803-MAR26-Java"
					+ "/Brandon_Semba_Code/ERS_Project1/ersproject1/Resources"
					+ "/connection.properties"));
			
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(
					prop.getProperty("url"), 
					prop.getProperty("usr"),
					prop.getProperty("pwd"));
			
		} catch (ClassNotFoundException cfne) {
			cfne.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return conn;
	}
}
