package esercizioGarage_conDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DESIGN PATTERN
 * @author fabio
 *
 */
public class ConnectionManager {

	private static ConnectionManager instance;
	private Connection conn;
	
	//JDBC driver name and database URL
	static final String DB_URL = "******";
		
	// DATABASE CREDENTIAL
	static final String USER = "*****";
	static final String PASS = "*****";
	
	/**
	 * Quando mi crea l'istanza la prima volta mi crea la connessione 
	 * poi utilizzero getConnection x la connessione.
	 * 
	 * Ho un unica classe x collegarmi al db.
	 * 
	 * Posso avere mille autodao che recupera la connection e la usa, non la crea ogni volta
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
