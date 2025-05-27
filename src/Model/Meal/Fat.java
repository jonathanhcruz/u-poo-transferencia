package Model.Meal;

public class Fat extends Meal {
    public Fat(String name, double calories, int userId) {
        super(name, calories, "Fat", userId);
    }

    @Override
    public String showInfo() {
        return this.getName() + " es un elemento de typo " + this.getType() + "y tiene " + this.getCalories() + " calorias";
    }
}
