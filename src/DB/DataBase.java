package DB;

import Model.Meal.Carbohydrates;
import Model.Meal.Fat;
import Model.Meal.FruitVegetable;
import Model.Meal.Meal;
import Model.User;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<Meal> products = new ArrayList<>();
    private ArrayList<User> AllowUsers = new ArrayList<>();
    private User currentUser;
    private static int nextProductId = 1; // Static counter for unique product IDs

    // Constructor
    public DataBase() {
        // User
        AllowUsers.add(new User("SuperAdmin","12345", 0));
        AllowUsers.add(new User("Admin","12345", 1));

        // Products
        this.setProduct("Apple", 52, 1, "FruitVegetable");
        this.setProduct("Avocado", 224, 1, "FruitVegetable");
        this.setProduct("Grape", 67, 1, "FruitVegetable");
        this.setProduct("Oil", 884, 0, "Fat");
        this.setProduct("Cheese", 402, 0, "Fat");
        this.setProduct("Chocolate", 546, 1, "Fat");
        this.setProduct("Fish", 546, 1, "Fat");
        this.setProduct("Potato", 546, 0, "Carbohydrates");
        this.setProduct("Pasta", 546, 1, "Carbohydrates");
        this.setProduct("Bread", 546, 0, "Carbohydrates");
    }

    // Users
    // Setters
    public void setUser(User userAllow) {
        AllowUsers.add(userAllow);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Getters
    public User getUserByName(String name) {
        User user = null;
        for (User employee : AllowUsers) {
            if (employee.getUserName().toLowerCase().equals(name)) {
                user = employee;
            }
        }
        return user;
    }

    public ArrayList<User> getAllowUsers() {
      return AllowUsers;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserById(int id) {
        User employeesById = null;
        for (User employee : AllowUsers) {
            if (employee.getId() == id ) {
                employeesById = employee;
            }
        }
        return employeesById;
    }

    // Delete
    public void deleteUserById(int id) {
        User employee = getUserById(id);
        AllowUsers.remove(employee);
    }

    // Update
    public void updateUser(User user) {
        User employeeById = getUserById(user.getId());
        employeeById.setUserName(user.getUserName());
    }

    //---------------------------------//

    // Products
    // Getters
    public Meal getProductById(int id) {
        Meal productById = null;
        for (Meal product : products) {
            if (product.getId() == id) {
                productById = product;
            }
        }
        return productById;
    }

    public ArrayList<Meal> getProductsByUserId(int userId) {
        ArrayList<Meal> userProducts = new ArrayList<>();
        for (Meal product : products) {
            if (product.getUserId() == userId) {
                userProducts.add(product);
            }
        }
        return userProducts;
    }

    public ArrayList<Meal> getProductsByType(String type) {
        ArrayList<Meal> productsByType = new ArrayList<>();
        for (Meal product : products) {
            if (product.getType().equalsIgnoreCase(type)) {
                productsByType.add(product);
            }
        }
        return productsByType;
    }

    // Setters
    public int setProduct(String name, double calories, int userId, String type) {
        Meal product = null;
        int productId = nextProductId++; // Assign current unique ID and then increment for the next one
        switch (type) {
            case "FruitVegetable":
                product = new FruitVegetable(name, calories, userId, productId);
                break;
            case "Fat":
                product = new Fat(name, calories, userId, productId);
                break;
            case "Carbohydrates":
                product = new Carbohydrates(name, calories, userId, productId);
                break;
        }
        if (product == null) {
            return -1;
        }

        products.add(product);
        return productId;
    }

    // Delete
    public void deleteProductById(int id) {
        Meal product = getProductById(id);
        if (product != null) {
            products.remove(product);
        }
    }

}
