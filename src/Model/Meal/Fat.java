package Model.Meal;

public class Fat extends Meal {
    public Fat(String name, double calories, int userId, int id) {
        super(name, calories, "Fat", userId, id);
    }

    @Override
    public String showInfo() {
        return this.getName() + " es un elemento de typo " + this.getType() + "y tiene " + this.getCalories() + " calorias";
    }
}
