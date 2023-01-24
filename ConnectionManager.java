package esercizioGarage_conDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author fabio
 *
 */
public class ConnectionManager {

	private static ConnectionManager instance;
	private Connection conn;
	
	//JDBC driver name and database URL
	static final String DB_URL = "jdbc:mysql://127.0.0.1/garage?serverTimezone=Europe/Rome&useSSL=false";
		
	// DATABASE CREDENTIAL
	static final String USER = "root";
	static final String PASS = "truzzo130866";
	
	/**
	 * When i create the instance the first time i create the connection 
	 * then i am will use getConnection for the connection..
	 * I have a single class and single object to connect to the db.
	 * 
	 * I can have a thousand autodao that fetch the connection and use it, not create it every time
	 */
	private ConnectionManager() {
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionManager getInstance() {	
		if(instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return conn;
	}
}
