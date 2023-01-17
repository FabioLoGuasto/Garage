package esercizioGarage_conDatabase;
/**
 * Class with attributes of car
 * @author fabio
 */
public class Auto {
	/**
	 * unique number of car
	 */
	int idAuto;
	/**
	 * unique number plate of car
	 */
	String numberPlate;
	/**
	 * category of car between "auto normale","auto lusso","auto van"
	 */
	String categoryCar;
	/**
	 * type of emission of car between "gpl","benzina","diesel","elettrica".
	 * If gpl, the car will have to go to the first level
	 */
	String emissionType;
	/**
	 * hour of entry of the car in the parking
	 */
	String entryTime;
	/**
	 * hour of exit of the car in the parking
	 */
	String exitTime;
	/**
	 * unique number of the parking where the car will park
	 */
	int parkingId;
	/**
	 * this fiel save the history of the garage.
	 * it will have the value 1 if the car is parked
	 * it will have the value 0 if the car is not parked
	 */
	int isDeleted; // 0 SE LA MACCHINA NON C'E' + NEL GARAGE QUINDI POSTO LIBERO
	
	public Auto() {}
	public Auto(int idAuto, String numberPlate, String categoryCar, String emissionType, String entryTime,
			String exitTime, int parkingId, int isDeleted) {
		super();
		this.idAuto = idAuto;
		this.numberPlate = numberPlate;
		this.categoryCar = categoryCar;
		this.emissionType = emissionType;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.parkingId = parkingId;
		this.isDeleted = isDeleted;
	}


	public int getIdAuto() {
		return idAuto;
	}
	public void setIdAuto(int idAuto) {
		this.idAuto = idAuto;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getCategoryCar() {
		return categoryCar;
	}
	public void setCategoryCar(String categoryCar) {
		this.categoryCar = categoryCar;
	}
	public String getEmissionType() {
		return emissionType;
	}
	public void setEmissionType(String emissionType) {
		this.emissionType = emissionType;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}
	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}
	public int getParkingId() {
		return parkingId;
	}
	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Override
	public String toString() {
		return "Auto [idAuto = " + idAuto + "], [number of plate = " + numberPlate + "], [category car = " + categoryCar
				+ "], [type of emission = " + emissionType + "], [time of entry car = " + entryTime
				+ "], [time of exit car = " + exitTime + "], [parking id = " + parkingId + "], [isDeleted = " + isDeleted + "]";
	}
	
	
	
	
	
	

}
