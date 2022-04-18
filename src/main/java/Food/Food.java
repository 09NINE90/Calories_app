package Food;

public class Food {
    private int id;
    private String nameFood;
    private float caloriesFood;
    private float proteinFood;
    private float fatFood;
    private float carbohydratesFood;


    public Food(String nameFood, float caloriesFood, float proteinFood, float fatFood, float carbohydratesFood) {
        this.nameFood = nameFood;
        this.caloriesFood = caloriesFood;
        this.proteinFood = proteinFood;
        this.fatFood = fatFood;
        this.carbohydratesFood = carbohydratesFood;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public void setCaloriesFood(float caloriesFood) {
        this.caloriesFood = caloriesFood;
    }

    public void setProteinFood(float proteinFood) {
        this.proteinFood = proteinFood;
    }

    public void setFatFood(float fatFood) {
        this.fatFood = fatFood;
    }

    public void setCarbohydratesFood(float carbohydratesFood) {
        this.carbohydratesFood = carbohydratesFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public float getCaloriesFood() {
        return caloriesFood;
    }

    public float getProteinFood() {
        return proteinFood;
    }

    public float getFatFood() {
        return fatFood;
    }

    public float getCarbohydratesFood() {
        return carbohydratesFood;
    }
}
