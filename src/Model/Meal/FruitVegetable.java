package Model.Meal;

public class FruitVegetable extends Meal {
    public FruitVegetable(String name, double calories, int userId, int id) {
        super(name, calories, "FruitVegetable", userId, id);
    }

    @Override
    public String showInfo() {
        return this.getName() + " es un elemento de typo " + this.getType() + "y tiene " + this.getCalories() + " calorias";
    }
}
