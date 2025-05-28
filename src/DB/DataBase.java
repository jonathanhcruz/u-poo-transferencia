package DB;

import Model.Meal.Carbohydrates;
import Model.Meal.Fat;
import Model.Meal.FruitVegetable;
import Model.Meal.Meal;
import Model.User;
import interfaces.TypeProduct;

import java.util.ArrayList;

public class DataBase {
    private ArrayList<Meal> products = new ArrayList<>();
    private ArrayList<User> AllowUsers = new ArrayList<>();
    private User currentUser;
    private static int nextProductId = 1;

    // Constructor
    public DataBase() {
        // User
        AllowUsers.add(new User("SuperAdmin","12345", 0));
        AllowUsers.add(new User("Admin","12345", 1));

        // Products
        this.setProduct("Apple", 52, 1, TypeProduct.FruitVegetable);
        this.setProduct("Avocado", 224, 1, TypeProduct.FruitVegetable);
        this.setProduct("Grape", 67, 1, TypeProduct.FruitVegetable);
        this.setProduct("Oil", 884, 0, TypeProduct.Fat);
        this.setProduct("Cheese", 402, 0, TypeProduct.Fat);
        this.setProduct("Chocolate", 546, 1, TypeProduct.Fat);
        this.setProduct("Fish", 546, 1, TypeProduct.Fat);
        this.setProduct("Potato", 546, 0, TypeProduct.Carbohydrates);
        this.setProduct("Pasta", 546, 1, TypeProduct.Carbohydrates);
        this.setProduct("Bread", 546, 0, TypeProduct.Carbohydrates);
    }

    // Users
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
