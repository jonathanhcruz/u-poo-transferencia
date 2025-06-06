 package Model.Meal;

public abstract class Meal  {
    private String name;
    private double calories;
    private String type;
    private int userId;
    private int id;

    public Meal (String name, double calories, String type, int userId, int id) {
        this.name =  name;
        this.calories = calories;
        this.type = type;
        this.userId = userId;
        this.id = id;
    }

    // getters
    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String showInfo();
}
