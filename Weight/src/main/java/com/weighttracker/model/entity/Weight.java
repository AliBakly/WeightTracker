package main.java.com.weighttracker.model.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a daily weight record in the weight tracking application.
 * Each record contains the date, weight measurement, calorie intake,
 * and gym attendance information.
 * 
 * @author YourName
 * @version 1.0
 * @since 2024-02-11
 */
public class Weight {
    /** Maximum allowed weight in kilograms */
    private static final double MAX_WEIGHT = 1000.0;
    
    /** Maximum allowed calories per day */
    private static final int MAX_CALORIES = 10000;
    
    /** The date of the weight measurement */
    private LocalDate date;
    
    /** The weight measurement in kilograms */
    private double weight;
    
    /** Total calorie intake for the day */
    private int kcal;
    
    /** Indicates whether the person went to the gym (yes/no) */
    private String gym;

    /**
     * Constructs a new Weight record with the specified parameters.
     *
     * @param date the date of the weight measurement
     * @param weight the weight in kilograms
     * @param kcal the calorie intake for the day
     * @param gym whether the person went to the gym ("yes" or "no")
     * @throws IllegalArgumentException if any parameter is invalid
     * @throws NullPointerException if date or gym is null
     */
    public Weight(LocalDate date, double weight, int kcal, String gym) {
        validateDate(date);
        validateWeight(weight);
        validateKcal(kcal);
        validateGym(gym);
        
        this.date = date;
        this.weight = weight;
        this.kcal = kcal;
        this.gym = gym.toLowerCase();
    }

    /**
     * Gets the date of the weight measurement.
     *
     * @return the measurement date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the weight measurement.
     *
     * @param date the measurement date
     * @throws IllegalArgumentException if date is invalid
     * @throws NullPointerException if date is null
     */
    public void setDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    /**
     * Gets the weight measurement in kilograms.
     *
     * @return the weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight measurement.
     *
     * @param weight the weight in kilograms
     * @throws IllegalArgumentException if weight is invalid
     */
    public void setWeight(double weight) {
        validateWeight(weight);
        this.weight = weight;
    }

    /**
     * Gets the calorie intake for the day.
     *
     * @return the calorie intake
     */
    public int getKcal() {
        return kcal;
    }

    /**
     * Sets the calorie intake for the day.
     *
     * @param kcal the calorie intake
     * @throws IllegalArgumentException if kcal is invalid
     */
    public void setKcal(int kcal) {
        validateKcal(kcal);
        this.kcal = kcal;
    }

    /**
     * Gets whether the person went to the gym.
     *
     * @return "yes" or "no"
     */
    public String getGym() {
        return gym;
    }

    /**
     * Sets whether the person went to the gym.
     *
     * @param gym "yes" or "no"
     * @throws IllegalArgumentException if gym is invalid
     * @throws NullPointerException if gym is null
     */
    public void setGym(String gym) {
        validateGym(gym);
        this.gym = gym.toLowerCase();
    }

    /**
     * Validates the date parameter.
     *
     * @param date the date to validate
     * @throws NullPointerException if date is null
     */
    private void validateDate(LocalDate date) {
        Objects.requireNonNull(date, "Date cannot be null");
    }

    /**
     * Validates the weight parameter.
     *
     * @param weight the weight to validate
     * @throws IllegalArgumentException if weight is negative or exceeds maximum
     */
    private void validateWeight(double weight) {
        if (weight < 0 || weight >= MAX_WEIGHT) {
            throw new IllegalArgumentException(
                "Weight must be between 0 and " + MAX_WEIGHT + " kg"
            );
        }
    }

    /**
     * Validates the calorie intake parameter.
     *
     * @param kcal the calories to validate
     * @throws IllegalArgumentException if kcal is negative or exceeds maximum
     */
    private void validateKcal(int kcal) {
        if (kcal < 0 || kcal >= MAX_CALORIES) {
            throw new IllegalArgumentException(
                "Calories must be between 0 and " + MAX_CALORIES
            );
        }
    }

    /**
     * Validates the gym attendance parameter.
     *
     * @param gym the gym attendance to validate
     * @throws IllegalArgumentException if gym is not "yes" or "no"
     * @throws NullPointerException if gym is null
     */
    private void validateGym(String gym) {
        Objects.requireNonNull(gym, "Gym cannot be null");
        if (!gym.equalsIgnoreCase("yes") && !gym.equalsIgnoreCase("no")) {
            throw new IllegalArgumentException("Gym must be 'yes' or 'no'");
        }
    }

    @Override
    public String toString() {
        return String.format("Weight[date=%s, weight=%.1f kg, kcal=%d, gym=%s]",
            date, weight, kcal, gym);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weight)) return false;
        Weight weight1 = (Weight) o;
        return date.equals(weight1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
