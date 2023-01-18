package esercizioGarage_conDatabase;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriaPostoDAO {
	
	/**
	 * given type category of car, get his hours parking price
	 * @param idParkingCategory : id parking of category between ("auto normale","auto lusso","auto van")
	 * @return getHourlyPrice() : return hourly price of selected category
 	 */
	public static Double getPrezzoOrario(int idParkingCategory){
//		List<CategoriaPosto> listaCategoriaPosto = new ArrayList<>();
		CategoriaPosto c = new CategoriaPosto();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT prezzo_orario FROM categoria_posto WHERE id_categoria_posto ='")
		.append(idParkingCategory)
		.append("' ");
		
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString()); // QUA C'E' DENTRO UNA RIGA
			
			// NON DEVO PASSARE TUTTE LE RIGHE, LO FA LA QUERY
			// listaCategoriaPosto NON LO USO, POPOLO LA LISTA CON UNA RIGA
			while(rs.next()) { // CURSORE, PASSA TUTTE LE RIGHE
//				c.setIdCategoriaPosto(					rs.getInt("id_categoria_posto") );
//				c.setNomeCategoria( 					rs.getString("nome_categoria"));
//				c.setPrezzoMensile(	  					rs.getDouble("prezzo_mensile"));  
				c.setHourlyPrice(                      rs.getDouble("prezzo_orario"));
//				listaCategoriaPosto.add(c);
			}
//		System.out.println("IL PREZZO ORARIO PER QUESTA CATEGORIA E' : " + c.getPrezzoOrario() + " €");
			
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}	
		return c.getHourlyPrice();
	}
	
	
	
	/**
	 * given type category of car, get his penalty price if the car is more than 8 hours in the parking 
	 * @param idParkingCategory : id parking of category between ("auto normale","auto lusso","auto van")
	 * @return getHourlyPenalty() : return penalty hourly price of selected category
 	 */
	public static int getPrezzoPenale(int idParkingCategory){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT penale FROM categoria_posto WHERE id_categoria_posto ='")
		.append(idParkingCategory)
		.append("' ");
		CategoriaPosto c = new CategoriaPosto();
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) {
				c.setHourlyPenalty(rs.getInt("penale"));
			}
//		System.out.println("IL PREZZO DELLA PENALE PER QUESTA CATEGORIA E' : " + c.getPenale() + " €");
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}		
		return c.getHourlyPenalty();
	}
	
	
	
	
	
	
	/**
	 * given the number of plate, get id category parking
	 * @param numberPlate : number plate of the car
	 * @return getIdCategoryParking() : id of category parking
	 */
	public static int getCategoria(String numberPlate) {
		CategoriaPosto c = new CategoriaPosto();	
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.id_categoria_posto ")
		.append("FROM auto a, posto p, categoria_posto c ")
		.append("WHERE  a.posto_id =  p.id_posto AND p.categoria_posto_id = c.id_categoria_posto AND a.targa ='")
		.append(numberPlate)
		.append("' ");
		
		try (Statement stmt = ConnectionManager.getInstance().getConnection().createStatement()){
			ResultSet rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()) { 
				c.setIdCategoryParking(rs.getInt("id_categoria_posto"));
			}
		
		}catch(Exception e) {
			System.out.println("got an Exception!");
			System.out.println(e.getMessage());
		}
		
		return c.getIdCategoryParking();
	}

}
