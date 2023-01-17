package esercizioGarage_conDatabase;
/**
 * class for attributes of parking car
 * @author fabio
 */
public class Posto {
	/**
	 * unique number of parking 
	 */
	private int idParking;
	/**
	 * this field will have value 1 if the car parking will be occupated
	 * this field will have value 0 if the car parking will be free
	 */
	private int parkingBusy;
	/**
	 * number level of the garage.
	 * There are 4 level
	 */
	private int numLevel;
	/**
	 * unique number of category type of the parking
	 * 1 - for auto normale
	 * 2 - for auto lusso
	 * 3 - for auto van
	 */
	private int categoryParkingID;
	
	public Posto() {}

	public int getIdParking() {
		return idParking;
	}

	public void setIdParking(int idParking) {
		this.idParking = idParking;
	}

	public int getParkingBusy() {
		return parkingBusy;
	}

	public void setParkingBusy(int parkingBusy) {
		this.parkingBusy = parkingBusy;
	}

	public int getNumLevel() {
		return numLevel;
	}

	public void setNumLevel(int numLevel) {
		this.numLevel = numLevel;
	}

	public int getCategoryParkingID() {
		return categoryParkingID;
	}

	public void setCategoryParkingID(int categoryParkingID) {
		this.categoryParkingID = categoryParkingID;
	}

	@Override
	public String toString() {
		return "id posto : " + idParking + ", occupato : " + parkingBusy + ", number level : " + numLevel
				+ ", categoria_posto_id : " + categoryParkingID + "]";
	}
	
	
	
	
	

}
