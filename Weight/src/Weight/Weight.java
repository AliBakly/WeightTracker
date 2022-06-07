package Weight;
import java.time.LocalDate;

public class Weight {
	private LocalDate date;
	private double weight;
	private int kcal;
	private String gym;
	
	
	
	public Weight(LocalDate date, double weight, int kcal, String gym) {
		if(weight>= 1000 || kcal>=10000 || (!gym.equalsIgnoreCase("no") && !gym.equalsIgnoreCase("yes"))) {
			throw new IllegalArgumentException();
		}else {
			this.date = date; // throws DateTimeException if invalid input
			this.weight = weight;
			this.kcal = kcal;
			this.gym = gym;
		}
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		if(weight>= 1000) {
			throw new IllegalArgumentException();
		}else {
			this.weight = weight;
		}
	}
	
	public int getKcal() {
		return kcal;
	}
	
	public void setKcal(int kcal) {
		if(kcal>=10000) {
			throw new IllegalArgumentException();
		}else {
			this.kcal = kcal;
		}
	}
	
	public String getGym() {
		return gym;
	}
	
	public void setGym(String gym) {
		if(!gym.equalsIgnoreCase("no") && !gym.equalsIgnoreCase("yes")) {
			throw new IllegalArgumentException();
		}else {
			this.gym = gym;
		}
	}
	
	public String toString() {
		return date.toString() + ", " + String.valueOf(weight) + ", " + String.valueOf(kcal) + ", " + String.valueOf(gym);
	}
}
