package main.java.com.weighttracker.model.entity;

import java.util.Objects;

/**
 * Represents a food item in the weight tracking application.
 * Contains nutritional information including calories, protein, carbohydrates,
 * and fat content.
 * <p>
 * This class encapsulates the details of a food item, providing mechanisms
 * to validate and manage its nutritional information. It supports basic
 * nutritional attributes and allows for the creation of both basic and
 * comprehensive food records.
 * </p>
 * 
 * <p><strong>Example Usage:</strong></p>
 * <pre>
 *     Food apple = new Food("Apple", 150.0, 95);
 *     Food chickenBreast = new Food("Chicken Breast", 200.0, 330, 31.0, 0.0, 7.0);
 * </pre>
 * 
 * @author 
 * @version 1.0
 * @since 2024-02-11
 */
public class Food {
    /** Maximum allowed amount in grams */
    private static final double MAX_AMOUNT = 5000.0;
    
    /** Maximum allowed calories per food item */
    private static final int MAX_CALORIES = 2000;
    
    /** Maximum allowed macronutrient amount in grams */
    private static final double MAX_MACRO = 500.0;
    
    /** The name of the food item */
    private String food;
    
    /** Amount in grams */
    private double amount;
    
    /** Calories per serving */
    private int kcal;
    
    /** Protein content in grams */
    private double proteinAmount;
    
    /** Carbohydrate content in grams */
    private double carbsAmount;
    
    /** Fat content in grams */
    private double fatAmount;

    /**
     * Constructs a basic Food item with only essential nutritional information.
     * The macronutrient contents (protein, carbohydrates, and fat) are initialized to zero.
     *
     * @param food the name of the food
     * @param amount the amount in grams
     * @param kcal the calorie content
     * @throws IllegalArgumentException if {@code amount} is not within the valid range
     *                                  or if {@code kcal} is not within the valid range
     * @throws NullPointerException if {@code food} is {@code null}
     */
    public Food(String food, double amount, int kcal) {
        validateFood(food);
        validateAmount(amount);
        validateKcal(kcal);
        
        this.food = food.trim();
        this.amount = amount;
        this.kcal = kcal;
        this.proteinAmount = 0.0;
        this.carbsAmount = 0.0;
        this.fatAmount = 0.0;
    }

    /**
     * Constructs a Food item with complete nutritional information.
     *
     * @param food the name of the food
     * @param amount the amount in grams
     * @param kcal the calorie content
     * @param proteinAmount the protein content in grams
     * @param carbsAmount the carbohydrate content in grams
     * @param fatAmount the fat content in grams
     * @throws IllegalArgumentException if {@code amount}, {@code kcal}, {@code proteinAmount},
     *                                  {@code carbsAmount}, or {@code fatAmount} are not within valid ranges
     * @throws NullPointerException if {@code food} is {@code null}
     */
    public Food(String food, double amount, int kcal, 
                double proteinAmount, double carbsAmount, double fatAmount) {
        this(food, amount, kcal);
        validateMacro(proteinAmount, "Protein");
        validateMacro(carbsAmount, "Carbohydrate");
        validateMacro(fatAmount, "Fat");
        
        this.proteinAmount = proteinAmount;
        this.carbsAmount = carbsAmount;
        this.fatAmount = fatAmount;
    }

    /**
     * Retrieves the name of the food item.
     *
     * @return the food name
     */
    public String getFood() {
        return food;
    }

    /**
     * Sets the name of the food item.
     *
     * @param food the food name to set
     * @throws IllegalArgumentException if {@code food} is empty or contains only whitespace
     * @throws NullPointerException if {@code food} is {@code null}
     */
    public void setFood(String food) {
        validateFood(food);
        this.food = food.trim();
    }

    /**
     * Retrieves the amount of the food item in grams.
     *
     * @return the amount in grams
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the food item in grams.
     *
     * @param amount the amount in grams to set
     * @throws IllegalArgumentException if {@code amount} is not within the valid range
     */
    public void setAmount(double amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    /**
     * Retrieves the calorie content of the food item.
     *
     * @return the calorie content
     */
    public int getKcal() {
        return kcal;
    }

    /**
     * Sets the calorie content of the food item.
     *
     * @param kcal the calorie content to set
     * @throws IllegalArgumentException if {@code kcal} is not within the valid range
     */
    public void setKcal(int kcal) {
        validateKcal(kcal);
        this.kcal = kcal;
    }

    /**
     * Retrieves the protein content of the food item in grams.
     *
     * @return the protein content in grams
     */
    public double getProteinAmount() {
        return proteinAmount;
    }

    /**
     * Sets the protein content of the food item in grams.
     *
     * @param proteinAmount the protein content in grams to set
     * @throws IllegalArgumentException if {@code proteinAmount} is not within the valid range
     */
    public void setProteinAmount(double proteinAmount) {
        validateMacro(proteinAmount, "Protein");
        this.proteinAmount = proteinAmount;
    }

    /**
     * Retrieves the carbohydrate content of the food item in grams.
     *
     * @return the carbohydrate content in grams
     */
    public double getCarbsAmount() {
        return carbsAmount;
    }

    /**
     * Sets the carbohydrate content of the food item in grams.
     *
     * @param carbsAmount the carbohydrate content in grams to set
     * @throws IllegalArgumentException if {@code carbsAmount} is not within the valid range
     */
    public void setCarbsAmount(double carbsAmount) {
        validateMacro(carbsAmount, "Carbohydrate");
        this.carbsAmount = carbsAmount;
    }

    /**
     * Retrieves the fat content of the food item in grams.
     *
     * @return the fat content in grams
     */
    public double getFatAmount() {
        return fatAmount;
    }

    /**
     * Sets the fat content of the food item in grams.
     *
     * @param fatAmount the fat content in grams to set
     * @throws IllegalArgumentException if {@code fatAmount} is not within the valid range
     */
    public void setFatAmount(double fatAmount) {
        validateMacro(fatAmount, "Fat");
        this.fatAmount = fatAmount;
    }

    /**
     * Validates the food name.
     *
     * @param food the food name to validate
     * @throws IllegalArgumentException if {@code food} is empty or contains only whitespace
     * @throws NullPointerException if {@code food} is {@code null}
     */
    private void validateFood(String food) {
        Objects.requireNonNull(food, "Food name cannot be null");
        if (food.trim().isEmpty()) {
            throw new IllegalArgumentException("Food name cannot be empty or blank");
        }
    }

    /**
     * Validates the amount in grams.
     *
     * @param amount the amount to validate
     * @throws IllegalArgumentException if {@code amount} is not within the range (0, {@value #MAX_AMOUNT}]
     */
    private void validateAmount(double amount) {
        if (amount <= 0 || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(
                "Amount must be greater than 0 and less than or equal to " + MAX_AMOUNT + " grams"
            );
        }
    }

    /**
     * Validates the calorie content.
     *
     * @param kcal the calories to validate
     * @throws IllegalArgumentException if {@code kcal} is not within the range [0, {@value #MAX_CALORIES}]
     */
    private void validateKcal(int kcal) {
        if (kcal < 0 || kcal > MAX_CALORIES) {
            throw new IllegalArgumentException(
                "Calories must be between 0 and " + MAX_CALORIES + " kcal"
            );
        }
    }

    /**
     * Validates a macronutrient amount.
     *
     * @param amount the macronutrient amount to validate
     * @param macroName the name of the macronutrient for error messaging (e.g., "Protein")
     * @throws IllegalArgumentException if {@code amount} is not within the range [0, {@value #MAX_MACRO}]
     */
    private void validateMacro(double amount, String macroName) {
        if (amount < 0 || amount > MAX_MACRO) {
            throw new IllegalArgumentException(
                macroName + " must be between 0 and " + MAX_MACRO + " grams"
            );
        }
    }

    /**
     * Returns a string representation of the Food object.
     *
     * @return a formatted string containing the food's name, amount, calories, and macronutrient contents
     */
    @Override
    public String toString() {
        return String.format(
            "Food[name=%s, amount=%.1fg, kcal=%d, protein=%.1fg, carbs=%.1fg, fat=%.1fg]",
            food, amount, kcal, proteinAmount, carbsAmount, fatAmount
        );
    }

    /**
     * Determines whether this Food object is equal to another object.
     * Two Food objects are considered equal if they have the same name, amount, and calorie content.
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal based on the defined criteria; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food other = (Food) o;
        return Objects.equals(food, other.food) &&
               Double.compare(other.amount, amount) == 0 &&
               kcal == other.kcal;
    }

    /**
     * Returns a hash code value for the Food object.
     * The hash code is based on the food's name, amount, and calorie content.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(food, amount, kcal);
    }
}
