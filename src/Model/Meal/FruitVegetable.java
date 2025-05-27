package Model.Meal;

public class FruitVegetable extends Meal {
    public FruitVegetable(String name, double calories, int userId) {
        super(name, calories, "FruitVegetable", userId);
    }

    @Override
    public String showInfo() {
        return this.getName() + " es un elemento de typo " + this.getType() + "y tiene " + this.getCalories() + " calorias";
    }
}
