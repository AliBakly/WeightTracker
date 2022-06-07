package Weight;

public class Food {
	private String food;
	private double amount;
	private int kcal;
	private double proteinAmount;
	private double carbsAmount;
	private double fatAmount;
	
	public Food(String food, double amount, int kcal) {
		this.food = food;
		this.amount = amount;
		this.kcal = kcal;
		this.proteinAmount = 0;
		this.carbsAmount = 0;
		this.fatAmount = 0;
	}
	
	public Food(String food, double amount, int kcal, double proteinAmount, double carbsAmount, double fatAmount) {
		this(food, amount, kcal);
		this.proteinAmount = proteinAmount;
		this.carbsAmount = carbsAmount;
		this.fatAmount = fatAmount;
	}
	
	public void setFood(String food) {
		this.food = food;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void setKcal(int kcal) {
		this.kcal = kcal;
	}
	
	public void setProteinAmount(double proteinAmount) {
		this.proteinAmount = proteinAmount;
	}
	
	public void setCarbsAmount(double carbsAmount) {
		this.carbsAmount = carbsAmount;
	}
	
	public void setFatAmount(double fatAmount) {
		this.fatAmount = fatAmount;
	}
	
	public String getFood() {
		return food;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public int getKcal() {
		return kcal;
	}
	
	public double getProteinAmount() {
		return proteinAmount;
	}
	
	public double getCarbsAmount() {
		return carbsAmount;
	}
	
	public double getFatAmount() {
		return fatAmount;
	}
	
	public String toString() {
		String s = "";
		s = s + "Food Name: " + food+ "\n";
		s = s + "Amount: " + amount + "g \n";
		s = s + "Kcal: " + kcal + "\n";
		s = s + "Protein Amount: " + proteinAmount +"\n";
		s = s + "Carbs Amount: " + carbsAmount + "\n";
		s = s + "Fat Amount:  " + fatAmount + "\n";
		return s;
	}
	
}

