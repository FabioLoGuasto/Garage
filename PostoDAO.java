package esercizioGarage_conDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostoDAO {
	
	/**
	 * DATO IL NUMERO DI POSTO DALL'AUTO, INSERISCO 1 NELLA COLONNA OCCUPATO DI QUEL POSTO NELLA TABELLA POSTO
	 * UPDATE DEL POSTO OCCUPATO (1 OCCUPATO)
	 * SE E' UN INSERT IL POSTO SI OCCUPA QUINDI 1
	 * @param a
	 */
	public static void updatePostoOccupato (int numeroPosto) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE garage.posto SET ")
		.append("occupato = '")
		.append(1)
		.append("' WHERE id_posto = '")
		.append(numeroPosto)
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
	 * DATO IL NUMERO DI POSTO DALL'AUTO, INSERISCO 0 NELLA COLONNA OCCUPATO DI QUEL POSTO NELLA TABELLA POSTO
	 * UPDATE DEL POSTO LIBERO (0 LIBERO)
	 * @param a
	 */
	public static void updatePostoLibero (int numeroPosto) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE garage.posto SET ")
		.append("occupato = '")
		.append(0)
		.append("' WHERE id_posto = '")
		.append(numeroPosto)
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
	public static List<Posto> getParcheggiLiberi(int livello, int idCategoriaPosto, int occupato) {
		List<Posto> listaPosti = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.id_posto, p.num_livello, p.occupato ")
		.append("FROM posto p, categoria_posto c, livello l WHERE l.numero_livello = p.num_livello AND c.id_categoria_posto = p.categoria_posto_id AND  num_livello= '")
		.append(livello)
		.append("' AND c.id_categoria_posto = '")
		.append(idCategoriaPosto)
		.append("' AND p.occupato = '")
		.append(occupato)
		.append("' ");
		
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { // CURSORE, PASSA TUTTE LE RIGHE
				Posto p = new Posto();
				p.setIdParking(	                rs.getInt("id_posto"));
				p.setParkingBusy(               rs.getInt("occupato"));
				p.setNumLevel(                  rs.getInt("num_livello"));
				
				listaPosti.add(p);
				System.out.println("POSTO LIBERO N° : " + rs.getInt("id_posto") + " AL LIVELLO : " + rs.getInt("num_livello"));
			}
		
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
		return listaPosti;
	}

}
