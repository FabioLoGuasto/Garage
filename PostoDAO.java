package esercizioGarage_conDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostoDAO {
	
	/**
	 * given id of parking, insert 1 on field occupato(table posto) on that id parking 
	 * because the car enter into the garage
	 * @param numberParking : car's number of parking
	 */
	public static void updatePostoOccupato (int numberParking) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE garage.posto SET ")
		.append("occupato = '")
		.append(1)
		.append("' WHERE id_posto = '")
		.append(numberParking)
		.append("' ");

		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(sb.toString());
			System.out.println("Righe modificate : " + rowAffected);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * given id of parking, insert 0 on field occupato(table posto) on that id parking 
	 * With this method it's free parking place, because the car exit to garage
	 * @param numberParking : car's number of parking
	 */
	public static void updatePostoLibero (int numberParking) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE garage.posto SET ")
		.append("occupato = '")
		.append(0)
		.append("' WHERE id_posto = '")
		.append(numberParking)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			int rowAffected = stmt.executeUpdate(sb.toString());
			System.out.println("Righe modificate : " + rowAffected);
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * LISTA DEI POSTI LIBERI DATO UN LIVELLO E UNA CATEGORIA DI AUTO SELEZIONATA
	 * 1 - AUTO NORMALE
	 * 2 - AUTO LUSSO
	 * 3 - AUTO VAN
	 */
	
	/**
	 * given the level and category of car, this method return a list of parking id FREE !!!
	 * @param level : selected number of level
	 * @param idCategoryParking : selected category of car between ("auto normale","auto lusso","auto van")
	 * @return listIdParking : list of parking id free, for selected level and category car 
	 */
	public static List<Posto> getParcheggiLiberi(int level, int idCategoryParking) {
		List<Posto> listIdParking = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.id_posto, p.num_livello, p.occupato ")
		.append("FROM posto p, categoria_posto c, livello l WHERE l.numero_livello = p.num_livello AND c.id_categoria_posto = p.categoria_posto_id AND  num_livello= '")
		.append(level)
		.append("' AND c.id_categoria_posto = '")
		.append(idCategoryParking)
		.append("' AND p.occupato = '")
		.append(0)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { // CURSORE, PASSA TUTTE LE RIGHE
				Posto p = new Posto();
				p.setIdParking(	                rs.getInt("id_posto"));
				p.setParkingBusy(               rs.getInt("occupato"));
				p.setNumLevel(                  rs.getInt("num_livello"));
				
				listIdParking.add(p);
				System.out.println("PARKING FREE N° : " + rs.getInt("id_posto") + " ON THE LEVEL : " + rs.getInt("num_livello"));
			}
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		return listIdParking;
	}

}
