package esercizioGarage_conDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutoDAO {
		
	
	/**
	 * Get list of cars in the garage 
	 * @return listCar : list of the cars  
	 */
	public List<Auto>selectAll(){
		List<Auto> listCar = new ArrayList<>();
		String query = "SELECT id_auto,targa,categoria_auto,tipologia_emissione,orario_entrata,orario_uscita,is_deleted,posto_id FROM auto";
		System.out.println(query);
		
		/*
		 * Questo si chiama try with resources.
		 * Quando mi devo collegare ad un file o ad un db, istanzio l'oggetto da leggere e poi dentro al try leggo il file
		 * e con il finally devo fare il .close perchè sia i file che le connessione che utilizzo vanno chiuse per renderle libere.
		 * 
		 * Dalla versione 7 di java creando l'istanza dopo il try ma prima delle {} , posso istanziare la risorsa (messa tra parentesi).
		 * Facedo in questo modo java in automatico farò il .close nel finally senza che venga scritto.
		 */
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) { // CURSORE, PASSA TUTTE LE RIGHE
				Auto a = new Auto();
				a.setIdAuto( 					rs.getInt("id_auto"));
				a.setNumberPlate(				rs.getString("targa"));
				a.setCategoryCar(				rs.getString("categoria_auto"));
				a.setEmissionType(				rs.getString("tipologia_emissione"));
				a.setEntryTime(					rs.getString("orario_entrata"));
				a.setExitTime(					rs.getString("orario_uscita"));
				a.setIsDeleted(					rs.getInt("is_deleted"));
				a.setParkingId( 				rs.getInt("posto_id"));
				
				listCar.add(a);
			}
		
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
		return listCar;
	}
	
	/**
	 * given id of the car return all fields of that car.
	 * @param id : id of selected car 
	 * @return car : this is the selected car
	 */
	public Auto selectOne(int id){
		String query = "SELECT id_auto,targa,categoria_auto,tipologia_emissione,orario_entrata,orario_uscita,is_deleted,posto_id FROM auto WHERE id_auto = " + id;
		System.out.println(query);
		Auto car = null;
		
		
		// STMT E' LA RISORSA
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(query);
			
		if(rs.next()) { // CURSORE, PASSA TUTTE LE RIGHE
			car = new Auto(); // SE ENTRA LO ISTANZIA
			car.setIdAuto( 					rs.getInt("id_auto"));
			car.setNumberPlate(				rs.getString("targa"));
			car.setCategoryCar(				rs.getString("categoria_auto"));
			car.setEmissionType(			rs.getString("tipologia_emissione"));
			car.setEntryTime(				rs.getString("orario_entrata"));
			car.setExitTime(				rs.getString("orario_uscita"));
			car.setIsDeleted(				rs.getInt("is_deleted"));
			car.setParkingId( 				rs.getInt("posto_id"));
			
		}
		
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
		return car;
	}
	
	/**
	 * insert of the new car
	 * @param car : istance of car
	 */
	public void insert (Auto car) { 
				
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO auto(targa,categoria_auto,tipologia_emissione,orario_entrata,is_deleted,posto_id)VALUES ('")
		.append(car.getNumberPlate())
		.append("','")
		.append(car.getCategoryCar())
		.append("','")
		.append(car.getEmissionType())
		.append("','")
		.append(car.getEntryTime())
		.append("','")
		.append(car.getIsDeleted())
		.append("','")
		.append(car.getParkingId())
		.append("')");
		
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(sb.toString());
			System.out.println("Inserted rows : " + rowAffected);	
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * update fields or field of auto 
	 * @param car : instance of auto
	 */
	public static void update (Auto car) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE auto SET ")		
		.append("targa = '")    
		.append(car.getNumberPlate())
		.append("',categoria_auto = '") 
		.append(car.getCategoryCar()) 
		.append("',tipologia_emissione = '")
		.append(car.getEmissionType())
		.append("',orario_entrata = '")
		.append(car.getEntryTime())
		.append("',orario_uscita = '")
		.append(car.getExitTime())
		.append("',is_deleted = '")
		.append(car.getIsDeleted())
		.append("',posto_id = '")
		.append(car.getParkingId())
		.append("' WHERE id_auto = '")
		.append(car.getIdAuto())
		.append("'");
//		System.out.println(sb);
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(sb.toString());
			System.out.println("Modified rows : " + rowAffected);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	/**
	 * delete row of car in the table auto on database
	 * @param idAuto : this is the unique number of car
	 */
	public void delete (int idAuto) {
		
		String query = "DELETE FROM auto WHERE id_auto = " + idAuto;
		System.out.println(query);
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(query);
			System.out.println("Delete rows : " + rowAffected);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
	}
	

	
	/**
	 * this method set the field is_deleted = 0 and the field orario_uscita
	 * on the table of database, because the car exit of garage.
	 * update the history of garage
	 * @param exitTime : hours exit of the car
	 * @param numberPlate : number of the selected car plate 
	 */
	public static void updateUscitaAuto (String exitTime, String numberPlate) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE auto SET ")
		.append(" orario_uscita = '")
		.append(exitTime)
		.append("', is_deleted = ")
		.append(0)
		.append(" WHERE targa = '")
		.append(numberPlate)
		.append("'");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(sb.toString());
			System.out.println("Modified rows : " + rowAffected);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
	/**
	 * given the number plate of car, get his number of parking 
	 * @param numberPlate : number plate of the car that arrived in the garage
	 * @return parking : number of the car parking occuped
	 */
	public static int returnPosto(String numberPlate){
		int parking = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT posto_id FROM auto WHERE targa ='")
		.append(numberPlate)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { 			
				parking = rs.getInt("posto_id");
			}
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		return parking;
	}
	
	
	
	/**
	 * given the number plate of car, get his entry hour in the garage
	 * @param numberPlate : number plate of the car that arrived in the garage
	 * @return entryTimeCar : time of arrived of car
	 */
	public static String returnOrarioEntrata(String numberPlate){
		String entryTimeCar = " ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT targa,orario_entrata FROM auto WHERE targa ='")
		.append(numberPlate)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { 
				entryTimeCar = rs.getString("orario_entrata");
			}
//			System.out.println("L'ORARIO DI ENTRATA DELL'AUTO E' " + orarioEntrata);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		return entryTimeCar;
	}
	
	
	
	
	/**
	 * given the number plate of car, get his exit hour in the garage
	 * @param numberPlate : number plate of the car that has left the garage
	 * @return exitTimeCar : time of exit of car
	 */
	public static String returnOrarioUscita(String numberPlate){
		String exitTimeCar = " ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT targa,orario_uscita FROM auto WHERE targa ='")
		.append(numberPlate)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { 
				exitTimeCar = rs.getString("orario_uscita");
			}
//			System.out.println("L'ORARIO DI USCITA DELL'AUTO E' " + orarioUscita);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		return exitTimeCar;
	}
	
	
	
	/**
	 * This method calculate the minutes that the car stay into the parking
	 * The minutes max that the car can staying in the parking is 480, after that the owner of the car will pay the penalty 
	 * @param entry : hours of car entry to the garage
	 * @param exit : hours of car exit to the garage
	 * @return totMinutes : total minutes of the car parking
	 */
	public static int timingParcheggio(String entry, String exit) {
		LocalTime timeEntrata = LocalTime.parse(entry);
		LocalTime timeUscita = LocalTime.parse(exit);
		Duration timeDifferent = Duration.between(timeUscita, timeEntrata);
		timeDifferent = timeDifferent.abs();
		int totMinutes = (int)timeDifferent.toMinutes();
//		System.out.println("I MINUTI TOTALI SONO : " + timeDifferenza.toMinutes()); //FA VEDERE I MINUTI
		return totMinutes;
	}
	
	
	
	/**
	 * given the total minutes of the car parking and the price of the parking category, this method calculate the price parking  
	 * @param totalMinutes : result of timingParcheggio method
	 * @param priceParkingCategory : price of the car category for example: ("auto normale","auto lusso", "auto van")
	 * @return toPay : price of parking to pay
	 */
	public static double tariffaParcheggio(int totalMinutes, double priceParkingCategory) {
		double priceParkingForOneHour = totalMinutes / 60 ;
		double toPay = priceParkingForOneHour * priceParkingCategory;
		System.out.println("THE PRICE OF PARKING TO PAY IS : " + toPay + " €");
		return toPay;
	}
	
	
	/**
	 * calculation penalty price in case of exceeding minutes 
	 * The max minutes allowed are 480
	 * @param totalMinutes : result of timingParcheggio method
	 * @param ordinaryPay : result of tariffaParcheggio method
	 * @param penaltyPriceCategory : price of selected category
	 * @return
	 */
	public static void tariffaParcheggioConPenale(int totalMinutes, double ordinaryPay, int penaltyPriceCategory) {
		double toPay = 0.0;
		
		if(totalMinutes > 480) {
			int minutesOver = totalMinutes - 480;
			double penaltyToPay = ((minutesOver / 30) * penaltyPriceCategory);
			toPay = penaltyToPay + ordinaryPay;
			System.out.println("THE PARKING PRICE WITH PENALTY IS : " + toPay + " €");
		}	
	}
	
	
	
//------------------------------------------------ TEST ----------------------------------------------------------------------	
//	/**
//	 * UPDATE CON GUIDA
//	 * @param a
//	 * @throws SQLException 
//	 */
//	public static void update2 () {
//		//JDBC driver name and database URL
//		String DB_URL = "jdbc:mysql://127.0.0.1/garage?serverTimezone=Europe/Rome&useSSL=false";
//			
//		// DATABASE CREDENTIAL
//		String USER = "root";
//		String PASS = "truzzo130866";
//		
//		Connection con = null;
//		try {
//			con = DriverManager.getConnection(DB_URL,USER,PASS);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		};
//		
//		
//		// DA RIVEDERE
//		try {
//			PreparedStatement preparedStatement = (PreparedStatement) ConnectionManager.getInstance().getConnection().createStatement();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		//---------------------------------------------------------------------------
//		
//		
//		PreparedStatement pstmt ;
//		int numUpd;
//		
//		try {
//			pstmt = con.prepareStatement("UPDATE auto SET targa=? WHERE posto_id=?"); // Create a PreparedStatement object
//			pstmt.setString(1,"PPPPP");        // Assign first value to first parameter    2 
//			pstmt.setString(2,"18");      // Assign first value to second parameter 
//			numUpd = pstmt.executeUpdate();   // Perform first update      
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}  
//	}
	
	
	
	
}
