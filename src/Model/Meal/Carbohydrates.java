package Model.Meal;

public class Carbohydrates extends Meal {
    public Carbohydrates(String name, double calories, int userId) {
        super(name, calories, "carbohydrates", userId);
    }

    @Override
    public String showInfo() {
        return this.getName() + " es un elemento de typo " + this.getType() + "y tiene " + this.getCalories() + " calorias";
    }
}
