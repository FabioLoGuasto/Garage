package esercizioGarage_conDatabase;
/**
 * class for attributes of type of parking
 * @author fabio
 */
public class CategoriaPosto {

	/**
	 * unique number of the parking type
	 */
	private int idCategoryParking;
	/**
	 * name category of the type parking between "auto normale","auto lusso","auto van"
	 */
	private String nameCategory;
	/**
	 * monthly parking price for specific category of car
	 */
	private double monthlyPrice;
	/**
	 * hourly parking price for specific category of car
	 */
	private double hourlyPrice;
	/**
	 * penalty hourly parking price if the car will stay on the garage plus that 8 hours
	 * for specific category of car
	 */
	private int hourlyPenalty;
	
	public CategoriaPosto() {}

	public String getNameCategory() {
		return nameCategory;
	}
	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}
	public double getMonthlyPrice() {
		return monthlyPrice;
	}
	public void setMonthlyPrice(double monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}
	public double getHourlyPrice() {
		return hourlyPrice;
	}
	public void setHourlyPrice(double hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

	public int getIdCategoryParking() {
		return idCategoryParking;
	}

	public void setIdCategoryParking(int idCategoryParking) {
		this.idCategoryParking = idCategoryParking;
	}
	public int getHourlyPenalty() {
		return hourlyPenalty;
	}
	public void setHourlyPenalty(int hourlyPenalty) {
		this.hourlyPenalty = hourlyPenalty;
	}
	
	
}
