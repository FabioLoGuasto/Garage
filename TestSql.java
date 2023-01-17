package esercizioGarage_conDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Class with main method
 * @author fabio
 *
 */
public class TestSql {

	public static void main(String[] args) {
		
		AutoDAO autodao = new AutoDAO();
		Scanner s = new Scanner(System.in);
		String insert = " ";
		String orarioUscita = " ";
		String orarioEntrata = " ";
		int scelta = 0;
		int info = 0;
		int minutiTotali = 0;
		int livello = 0;
		int categoriaParcheggio = 0; // 1-AUTO NORMALE, 2-AUTO LUSSO, 3-AUTO VAN
		int occupato = 0; // INIZIALIZZATO A 0 FA CAPIRE CHE IL POSTO E' LIBERO
		int insertNumber = 0;
		int prezzoPenale = 0;
		Double prezzoOrario = 0.0;
		Double tariffaParcheggio = 0.0;
		List<Posto>listaPosti = new ArrayList<>();
		
		
		System.out.println("Scegli tra le opzioni :");
		System.out.println("1 - SELEZIONA TUTTA LA LISTA");
		System.out.println("2 - SELEZIONA 1 MACCHINA");
		System.out.println("3 - SELEZIONA 1 MACCHINA E FAI LA MODIFICA DI UN CAMPO");
		System.out.println("4 - UPDATE");
		System.out.println("5 - DELETE MANUALE");
		System.out.println("6 - USCITA DELLA AUTO E PAGAMENTO PARCHEGGIO");
		System.out.println("7 - INSERIMENTO DI UN AUTO");
		System.out.println("8 - LISTA DEI POSTI LIBERI DATO UN LIVELLO E UNA CATEGORIA SELEZIONATA");
		System.out.println("9 - TEST");
		scelta = s.nextInt();
		
		switch(scelta) {
		case 1:
			System.out.println("VEDI TUTTA LA LISTA");
			for (Auto a : autodao.selectAll()) {
				System.out.println(a);
			}
			System.out.println();
			
			break;
		case 2:
			System.out.println("VEDI UNA SOLA AUTO");
			Auto auto = autodao.selectOne(1);
			System.out.println(auto);
			System.out.println();
			
			break;
		case 3:
			System.out.println("UPDATE UN VALORE SINGOLO");
			Auto autoDaModificare = autodao.selectOne(1); // SELEZIONO UN AUTO
			autoDaModificare.setEmissionType("NON SI SA");
			AutoDAO.update(autoDaModificare);
			
			break;
		case 4:
			System.out.println("UPDATE DI UN CAMPO DI UN AUTO");
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
			
			System.out.println();
			
			break;
		case 5:
			System.out.println("DELETE MANUALE DI UN AUTO");
			System.out.println("Cancello riga con id_auto = 9");
			autodao.delete(9);
			for(Auto aa : autodao.selectAll()) {
				System.out.println(aa);
			}
			System.out.println();
			
			break;
		case 6:
			System.out.println("6 - USCITA DELLA AUTO ");
			
			System.out.println("INSERISCI LA TARGA DEL VEICOLO");
			insert = s.next(); s.nextLine();
			System.out.println("INSERISCI L'ORARIO DI USCITA DIVISA DA :");
			orarioUscita = s.next(); s.nextLine();
			
			System.out.println("UPDATE ISDELETED ED INSERIMENTO ORARIO USCITA");
			AutoDAO.updateUscitaAuto(orarioUscita, insert); // INSERIMENTO ORARIO USCITA E MODIFICA IS_DELETED
			int posto = AutoDAO.returnPosto(insert); // DATA LA TARGA COME PARAMETRO RITORNO IL NUMERO DI POSTO CHE OCCUPAVA
			System.out.println();
			
			System.out.println("LIBERO IL POSTO AUTO");
			PostoDAO.updatePostoLibero(posto); // RENDO LIBERO IL POSTO
			System.out.println();
			
			System.out.println("PAGAMENTO PARCHEGGIO");
			orarioEntrata = AutoDAO.returnOrarioEntrata(insert); // RITORNA L'ORARIO DI ENTRATA
			orarioUscita = AutoDAO.returnOrarioUscita(insert);// RITORNA L'ORARIO DI USCITA
			System.out.println();
			
			categoriaParcheggio = CategoriaPostoDAO.getCategoria(insert); // MI TIRA FUORI LA CATEGORIA DELL'AUTO
			prezzoOrario = CategoriaPostoDAO.getPrezzoOrario(categoriaParcheggio); // MI TIRA FUORI IL PREZZO ORARIO DELLA CATEGORIA POSTO
			prezzoPenale = CategoriaPostoDAO.getPrezzoPenale(categoriaParcheggio); // MI TIRA FUORI IL PREZZO DELLA PENALE DELLA CAT. POSTO
			System.out.println();
			
			// PAGAMENTO PARCHEGGIO
			minutiTotali = AutoDAO.timingParcheggio(orarioEntrata, orarioUscita);// CALCOLO DEI MINUTI TOTALI DEL PARCHEGGIO DATO ORARIO E/U
			tariffaParcheggio  = AutoDAO.tariffaParcheggio(minutiTotali,prezzoOrario); // CALCOLO TARIFFA DEL PARCHEGGIO
			
			if(minutiTotali > 480) {
				AutoDAO.tariffaParcheggioConPenale(minutiTotali, tariffaParcheggio, prezzoPenale);// TARIFFA PARCHEGGIO IN CASO DI PENALE
			}
			
			break;
		case 7:
			System.out.println("7 - INSERIMENTO DI UN AUTO");
			Auto a1 = new Auto();
			
			do {
				System.out.println("L'AUTO E' GPL ?");
				System.out.println("1 - SI");
				System.out.println("2 - NO");
				info = s.nextInt();
				
				if((info < 1) | (info > 2)) {
					System.out.println("Inserimento errato, RIPROVA");
					System.out.println();
				}
			}while((info < 1) | (info > 2));
			System.out.println();
			
			if(info == 1) {
				System.out.println("SEI OBBLIGATO AD ANDARE AL LIVELLO 1");
				do{
					System.out.println("INDICARE LA CATEGORIA DELLA VETTURA :");
					System.out.println("1 - AUTO NORMALE");
					System.out.println("2 - AUTO LUSSO");
					System.out.println("3 - AUTO VAN");
					info = s.nextInt();
					
					if((info < 1) | (info > 3)) {
						System.out.println("Inserimento errato, RIPROVA");
						System.out.println();
					}
				}while((info < 1) | (info > 3));
				System.out.println();
				
				listaPosti = PostoDAO.getParcheggiLiberi(1,info,0);// livello,categoriaPosto,occupato
				
				if(listaPosti.isEmpty()) {
					System.out.println("I POSTI DEL LIVELLO 1 SONO PIENI E NON PUOI PARCHEGGIARE");
				}else {
					System.out.println("INDICA LA TARGA :");
					insert = s.next();s.nextLine();
					a1.setNumberPlate(insert);
					a1.setCategoryCar("auto normale");
					a1.setEmissionType("gpl");
					System.out.println("INDICA L'ORARIO DI ENTRATA DIVISO DA :");
					insert = s.next();s.nextLine();
					a1.setEntryTime(insert);
					a1.setIsDeleted(1); // INSERISCO 1 X INDICARE IL POSTO OCCUPATO
					System.out.println("INDICA IL POSTO TRA QUELLI LIBERI :"); // SI DOVREBBERO VEDERE I POSTI IN BASE ALLA TIPOLOGIA DI AUTO
					insertNumber = s.nextInt();
					a1.setParkingId(insertNumber);
					
					PostoDAO.updatePostoOccupato(insertNumber); // MODIFICA L'ATTRIBUTO OCCUPATO IN TABELLA POSTO
				}	
				
			}else {
				do {
					System.out.println("A QUALE LIVELLO VUOI PARCHEGGIARE ? ");
					System.out.println("2 - SECONDO LIVELLO ");
					System.out.println("3 - TERZO LIVELLO ");
					System.out.println("4 - QUARTO LIVELLO ");
					livello = s.nextInt();
					
					if((livello < 2) | (livello > 4)) {
						System.out.println("Inserimento errato, RIPROVA");
						System.out.println();
					}
				}while((livello < 2) | (livello > 4));
				System.out.println();
				
				do{
					System.out.println("INDICARE LA CATEGORIA DELLA VETTURA :");
					System.out.println("1 - AUTO NORMALE");
					System.out.println("2 - AUTO LUSSO");
					System.out.println("3 - AUTO VAN");
					info = s.nextInt();
					
					if((info < 1) | (info > 3)) {
						System.out.println("Inserimento errato, RIPROVA");
						System.out.println();
					}
				}while((info < 1) | (info > 3));
				System.out.println();
				
				occupato = 0; // METTO 0 DI DEFAULT PERCHE' DEVO CAPIRE IL POSTO LIBERO
				listaPosti = PostoDAO.getParcheggiLiberi(livello,info,occupato);// livello,categoriaPosto,occupato
				
				if(listaPosti.isEmpty()) {
					System.out.println("I POSTI DEL LIVELLO " + livello + " SONO PIENI E NON PUOI PARCHEGGIARE");
				}else {
					System.out.println("INDICA LA TARGA :");
					insert = s.next();s.nextLine();
					a1.setNumberPlate(insert);
					System.out.println("INDICA LA CATEGORIA DELL'AUTO :");
					insert = s.next();s.nextLine();
					a1.setCategoryCar(insert);
					System.out.println("INDICA IL TIPO DI EMISSIONE DELL'AUTO :");
					insert = s.next();s.nextLine();
					a1.setEmissionType(insert);
					System.out.println("INDICA L'ORARIO DI ENTRATA DIVISO DA :");
					insert = s.next();s.nextLine();
					a1.setEntryTime(insert);
					a1.setIsDeleted(1); // INSERISCO 1 X INDICARE IL POSTO OCCUPATO
					System.out.println("INDICA IL POSTO TRA QUELLI LIBERI :"); // SI DOVREBBERO VEDERE I POSTI IN BASE ALLA TIPOLOGIA DI AUTO
					insertNumber = s.nextInt();
					a1.setParkingId(insertNumber);
					
					PostoDAO.updatePostoOccupato(insertNumber); // MODIFICA L'ATTRIBUTO OCCUPATO IN TABELLA POSTO
				}
			}
			autodao.insert(a1);

			break;
		case 8:
			do {
				System.out.println("8 - LISTA DEI POSTI LIBERI DATO UN LIVELLO E UNA CATEGORIA SELEZIONATA");
				System.out.println("INSERISCI IL LIVELLO");
				System.out.println("1 - PRIMO LIVELLO");
				System.out.println("2 - SECONDO LIVELLO ");
				System.out.println("3 - TERZO LIVELLO ");
				System.out.println("4 - QUARTO LIVELLO ");
				livello = s.nextInt();
				
				if((livello < 1) | (livello > 4)) {
					System.out.println("Inserimento errato, RIPROVA");
					System.out.println();
				}
			}while((livello < 1) | (livello > 4));
			System.out.println();
			
			do{
				System.out.println("INSERISCI LA CATEGORIA DEL POSTO");
				System.out.println("1 - AUTO NORMALE");
				System.out.println("2 - AUTO LUSSO");
				System.out.println("3 - AUTO VAN");
				categoriaParcheggio = s.nextInt();
				
				if((categoriaParcheggio < 1) | (categoriaParcheggio > 3)) {
					System.out.println("Inserimento errato, RIPROVA");
					System.out.println();
				}
			}while((categoriaParcheggio < 1) | (categoriaParcheggio > 3));
			
			occupato = 0; //INDICA IL POSTO LIBERO
			listaPosti = PostoDAO.getParcheggiLiberi(livello,categoriaParcheggio,occupato);// livello,categoriaPosto,occupato
			
			break;
		case 9:
			String i = AutoDAO.returnOrarioUscita("AA111");
			System.out.println(i);
			break;
		}
		
	}
	
	
	

}
