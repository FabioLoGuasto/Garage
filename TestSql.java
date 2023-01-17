package esercizioGarage_conDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Class with main method
 * @author Fabio
 */
public class TestSql {

	public static void main(String[] args) {
		
		AutoDAO autodao = new AutoDAO();
		Scanner scanner = new Scanner(System.in);
		String insert = " ";
		String exitTime = " ";
		String entryTime = " ";
		int scelta = 0;
		int info = 0;
		int totalMinutes = 0;
		int level = 0;
		int categoryParking = 0; 
		int insertNumber = 0;
		int penaltyPrice = 0;
		Double hourlyPrice = 0.0;
		Double priceParking = 0.0;
		List<Posto>parkingList = new ArrayList<>();
		
		
		System.out.println("CHOOSE FROM THE OPTIONS :");
		System.out.println("1 - GET ALL CARS");
		System.out.println("2 - GET ONE CAR");
		System.out.println("3 - SELECT ONE CAR AND UPDATE ONE FIELDS");
		System.out.println("4 - UPDATE");
		System.out.println("5 - DELETE ONE ROWS IN THE DATABASE TABLE");
		System.out.println("6 - EXIT OF CAR AND PAY PARKING");
		System.out.println("7 - INSERT CAR");
		System.out.println("8 - LIST OF FREE PARKING GIVEN LEVEL AND CATEGORY TYPE OF CAR");
		scelta = scanner.nextInt();
		
		switch(scelta) {
		case 1:
			System.out.println("GET ALL CARS");
			for (Auto a : autodao.selectAll()) {
				System.out.println(a);
			}
			System.out.println();
			
			break;
		case 2:
			System.out.println("GET ONE CAR");
			Auto auto = autodao.selectOne(1);
			System.out.println(auto);
			System.out.println();
			
			break;
		case 3:
			System.out.println("SELECT ONE CAR AND UPDATE ONE FIELDS");
			Auto autoDaModificare = autodao.selectOne(1); // SELEZIONO UN AUTO
			autoDaModificare.setEmissionType("********");
			AutoDAO.update(autoDaModificare);
			
			break;
		case 4:
			System.out.println("UPDATE");
			Auto a = new Auto();
			a.setIdAuto(80);
			a.setNumberPlate("AA800");
			a.setCategoryCar("auto lusso");
			a.setEmissionType("gpl");
			a.setEntryTime("09:00");
			a.setExitTime("20:00");
			a.setIsDeleted(0);
			a.setParkingId(3);
			AutoDAO.update(a);
			
			break;
		case 5:
			System.out.println("DELETE ONE ROW IN THE DATABASE TABLE");
			System.out.println("INSERT ID CAR TO DELETE");
			scelta = scanner.nextInt();
			System.out.println("DELETE ROW WITH ID CAR = " + scelta);
			autodao.delete(scelta);
			
			break;
		case 6:
			System.out.println("6 - EXIT CAR ");
			
			System.out.println("INSERT NUMBER PLATE OF CAR");
			insert = scanner.next(); scanner.nextLine();
			System.out.println("INSERT THE EXIT TIME DIVIDED BY:");
			exitTime = scanner.next(); scanner.nextLine();
			
			System.out.println("UPDATE FIELE ISDELETED = 0 AND INSERT EXIT TIME");
			AutoDAO.updateUscitaAuto(exitTime, insert); 
			int posto = AutoDAO.returnPosto(insert); 
			System.out.println();
			
			System.out.println("FREE PARKING PLACE");
			PostoDAO.updatePostoLibero(posto); 
			System.out.println();
			
			System.out.println("PAY PARKING");
			entryTime = AutoDAO.returnOrarioEntrata(insert); 
			exitTime = AutoDAO.returnOrarioUscita(insert);
			System.out.println();
			
			categoryParking = CategoriaPostoDAO.getCategoria(insert); 
			hourlyPrice = CategoriaPostoDAO.getPrezzoOrario(categoryParking); 
			penaltyPrice = CategoriaPostoDAO.getPrezzoPenale(categoryParking); 
			System.out.println();
			
			// PAGAMENTO PARCHEGGIO
			totalMinutes = AutoDAO.timingParcheggio(entryTime, exitTime);
			priceParking  = AutoDAO.tariffaParcheggio(totalMinutes,hourlyPrice); 
			
			if(totalMinutes > 480) {
				AutoDAO.tariffaParcheggioConPenale(totalMinutes, priceParking, penaltyPrice);
			}
			
			break;
		case 7:
			System.out.println("7 - INSERT CAR");
			Auto a1 = new Auto();
			
			do {
				System.out.println("IS THE TYPE OF EMISSION OF THE CAR GPL?");
				System.out.println("1 - YES");
				System.out.println("2 - NO");
				info = scanner.nextInt();
				
				if((info < 1) | (info > 2)) {
					System.out.println("DATA ENTRY ERROR, TRY AGAIN");
					System.out.println();
				}
			}while((info < 1) | (info > 2));
			System.out.println();
			
			if(info == 1) {
				System.out.println("YOU MUST TO GO TO THE LEVEL 1 BECAUSE YOUR TYPE OF EMISSION IS GPL");
				do{
					System.out.println("INSERT THE CATEGORY OF THE CAR :");
					System.out.println("1 - AUTO NORMALE");
					System.out.println("2 - AUTO LUSSO");
					System.out.println("3 - AUTO VAN");
					info = scanner.nextInt();
					
					if((info < 1) | (info > 3)) {
						System.out.println("DATA ENTRY ERROR, TRY AGAIN");
						System.out.println();
					}
				}while((info < 1) | (info > 3));
				System.out.println();
				
				parkingList = PostoDAO.getParcheggiLiberi(1,info);
				
				if(parkingList.isEmpty()) {
					System.out.println("LEVEL 1 PLACES ARE FULL AND YOU CAN'T PARK");
				}else {
					System.out.println("INSERT NUMBER PLATE OF CAR :");
					insert = scanner.next();scanner.nextLine();
					a1.setNumberPlate(insert);
					a1.setCategoryCar("auto normale");
					a1.setEmissionType("gpl");
					System.out.println("INSERT THE ENTRY TIME DIVIDED BY:");
					insert = scanner.next();
					scanner.nextLine();
					a1.setEntryTime(insert);
					a1.setIsDeleted(1); // DEFAULT 1 FOR SET PARKING PLACE BUSY
					System.out.println("INSERT THE PARKING NUMBER CHOSEN BETWEEN THOSE FREE:"); // SI DOVREBBERO VEDERE I POSTI IN BASE ALLA TIPOLOGIA DI AUTO
					insertNumber = scanner.nextInt();
					a1.setParkingId(insertNumber);
					
					PostoDAO.updatePostoOccupato(insertNumber); // UPDATE FIELD OCCUPATO
				}	
				
			}else {
				do {
					System.out.println("AT WHICH LEVEL DO YOU WANT TO PARK? ");
					System.out.println("2 - SECOND LEVEL");
					System.out.println("3 - THIRD LEVEL");
					System.out.println("4 - FOURTH LEVEL");
					level = scanner.nextInt();
					
					if((level < 2) | (level > 4)) {
						System.out.println("DATA ENTRY ERROR, TRY AGAIN");
						System.out.println();
					}
				}while((level < 2) | (level > 4));
				System.out.println();
				
				do{
					System.out.println("INSERT THE CATEGORY OF THE CAR :");
					System.out.println("1 - AUTO NORMALE");
					System.out.println("2 - AUTO LUSSO");
					System.out.println("3 - AUTO VAN");
					info = scanner.nextInt();
					
					if((info < 1) | (info > 3)) {
						System.out.println("DATA ENTRY ERROR, TRY AGAIN");
						System.out.println();
					}
				}while((info < 1) | (info > 3));
				System.out.println();
				
				parkingList = PostoDAO.getParcheggiLiberi(level,info);
				
				if(parkingList.isEmpty()) {
					System.out.println("THE LEVEL " + level + " CAR PARKS ARE FULL AND YOU CAN'T PARK");
				}else {
					System.out.println("INSERT NUMBER PLATE OF CAR :");
					insert = scanner.next();scanner.nextLine();
					a1.setNumberPlate(insert);
					System.out.println("INSERT TYPE CATEGORY OF CAR :");
					insert = scanner.next();scanner.nextLine();
					a1.setCategoryCar(insert);
					System.out.println("INSERT EMISSION TYPE OF CAR :");
					insert = scanner.next();scanner.nextLine();
					a1.setEmissionType(insert);
					System.out.println("INSERT THE ENTRY TIME DIVIDED BY:");
					insert = scanner.next();scanner.nextLine();
					a1.setEntryTime(insert);
					a1.setIsDeleted(1); // INSERISCO 1 X INDICARE IL POSTO OCCUPATO
					System.out.println("INSERT THE PARKING NUMBER CHOSEN BETWEEN THOSE FREE :"); // SI DOVREBBERO VEDERE I POSTI IN BASE ALLA TIPOLOGIA DI AUTO
					insertNumber = scanner.nextInt();
					a1.setParkingId(insertNumber);
					
					PostoDAO.updatePostoOccupato(insertNumber); // UPDATE FIELD OCCUPATO IN THA DATABASE TABLE
				}
			}
			autodao.insert(a1);

			break;
		case 8:
			do {
				System.out.println("8 - LIST OF FREE PARKING GIVEN LEVEL AND CATEGORY TYPE OF CAR");
				System.out.println("INSERT LEVEL");
				System.out.println("1 - FIRST LEVEL");
				System.out.println("2 - SECOND LEVEL");
				System.out.println("3 - THIRD LEVEL");
				System.out.println("4 - FOURTH LEVEL");
				level = scanner.nextInt();
				
				if((level < 1) | (level > 4)) {
					System.out.println("DATA ENTRY ERROR, TRY AGAIN");
					System.out.println();
				}
			}while((level < 1) | (level > 4));
			System.out.println();
			
			do{
				System.out.println("INSERT THE CATEGORY OF THE CAR");
				System.out.println("1 - AUTO NORMALE");
				System.out.println("2 - AUTO LUSSO");
				System.out.println("3 - AUTO VAN");
				categoryParking = scanner.nextInt();
				
				if((categoryParking < 1) | (categoryParking > 3)) {
					System.out.println("DATA ENTRY ERROR, TRY AGAIN");
					System.out.println();
				}
			}while((categoryParking < 1) | (categoryParking > 3));
			
			parkingList = PostoDAO.getParcheggiLiberi(level,categoryParking);
			
			break;
		default:
			System.out.println("DATA ENTRY ERROR, TRY AGAIN");
			break;
		}
		
	}
	
	
	

}
