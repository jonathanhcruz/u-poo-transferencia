package DB;

import Model.Meal.Carbohydrates;
import Model.Meal.Fat;
import Model.Meal.FruitVegetable;
import Model.Meal.Meal;
import Model.User;

import java.util.ArrayList;

public class DataBase {
    ArrayList<Meal> products = new ArrayList<>();
    ArrayList<User> AllowUsers = new ArrayList<>();

    // Constructor
    public DataBase() {
        // User
        AllowUsers.add(new User("SuperAdmin","12345", 0));
        AllowUsers.add(new User("Admin","12345", 1));

        // Products
        products.add(new FruitVegetable("Apple", 52, 1));
        products.add(new FruitVegetable("Avocado", 224, 1));
        products.add(new FruitVegetable("grape", 67, 1));

        products.add(new Fat("Oil", 884, 0));
        products.add(new Fat("Cheese", 402, 0));
        products.add(new Fat("Chocolate", 546,1));
        products.add(new Fat("Fish", 546, 1));

        products.add(new Carbohydrates("Potato", 546, 0));
        products.add(new Carbohydrates("Pasta", 546, 1));
        products.add(new Carbohydrates("Bread", 546, 0));
    }

    // Users
    // Setters
    public void setUser(User userAllow) {
        AllowUsers.add(userAllow);
    }

    // Getters
    public ArrayList<User> getAllowUsers() {
      return AllowUsers;
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


    // Products
    // Getters
    public User getUserByName(String name) {
        User employeeByName = null;
        for (User employee : AllowUsers) {
            if (employee.getUserName().toLowerCase().equals(name)) {
                employeeByName = employee;
            }
        }
        return employeeByName;
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

}
