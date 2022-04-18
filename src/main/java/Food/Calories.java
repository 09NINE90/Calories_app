package Food;


public class Calories {
    private double caloriesQuantity;
    private double caloriesDay;
    private double caloriesDayNormal;
    private double caloriesDayNormalDb;
    private int dateCalories;

    public double getCaloriesDayNormalDb() {
        return caloriesDayNormalDb;
    }

    public void setCaloriesDayNormalDb(double caloriesDayNormalDb) {
        this.caloriesDayNormalDb = caloriesDayNormalDb;
    }


    public Calories(double caloriesQuantity, double caloriesDayNormal, int dateCalories) {
        this.caloriesQuantity = caloriesQuantity;
        this.caloriesDayNormal = caloriesDayNormal;
        this.dateCalories = dateCalories;
    }

    public double getCaloriesDayNormal() {
        return caloriesDayNormal;
    }

    public void setCaloriesDayNormal(double caloriesDayNormal) {
        this.caloriesDayNormal = caloriesDayNormal;
    }

    public void setCaloriesDay(double caloriesDay) {
        this.caloriesDay = caloriesDay;
    }

    public void setCaloriesQuantity(double caloriesQuantity) {
        this.caloriesQuantity = caloriesQuantity;
    }

    public double getCaloriesDay() {
        return caloriesDay;
    }

    public double getCaloriesQuantity() {
        return caloriesQuantity;
    }

    public int getDateCalories() {
        return dateCalories;
    }

    public void setDateCalories(int dateCalories) {
        this.dateCalories = dateCalories;
    }
}
