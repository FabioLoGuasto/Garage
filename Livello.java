package esercizioGarage_conDatabase;

/**
 * class for attributs of level of garage
 * @author fabio
 *
 */
public class Livello {
	/**
	 * unique number level of garage
	 */
	private int levelGarage;
	/**
	 * number of parking car for each level
	 */
	private int numberParking;
	
	public Livello() {}

	
	public int getLevelGarage() {
		return levelGarage;
	}
	public void setLevelGarage(int levelGarage) {
		this.levelGarage = levelGarage;
	}
	public int getNumerParking() {
		return numberParking;
	}
	public void setNumerParking(int numberParking) {
		this.numberParking = numberParking;
	}
	

}
