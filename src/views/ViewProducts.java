package views;

import DB.DataBase;
import Model.Meal.Meal;
import interfaces.TypeProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class ViewProducts {
    DataBase db;
    public void renderView(JPanel viewProducts, DataBase db) {
        this.db = db;
        final ArrayList<Meal> products = db.getProductsByUserId();
        if (products.isEmpty()) {
            JLabel noProductsLabel = new JLabel("No products available for this user.");
            noProductsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            viewProducts.add(noProductsLabel);
            return;
        }
        // create title of view
        JLabel title = new JLabel("Products");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        viewProducts.add(title);

        // create table to show products
        String[] columnNames = {"ID", "Name", " Calories", "UserId", "Type","action"};
        JTable table = new JTable(new DefaultTableModel(columnNames,  0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        viewProducts.add(scrollPane, BorderLayout.CENTER);


        // add data to table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Meal product : products) {
            // create button delete
            JButton btnDelete = new JButton("Delete Product");
            btnDelete.addActionListener(e -> {
                handleDeleteProduct(product.getId());
                model.removeRow(table.getSelectedRow());
            });

            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getCalories(),
                    product.getUserId(),
                    product.getType(),
                    btnDelete
            });
        }
        // create form for insert products
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 3, 0, 0));

        formPanel.setBorder(BorderFactory.createEmptyBorder(10,0, 10, 0));
        formPanel.setPreferredSize(new Dimension(830, 60));
        JTextField txtName = new JTextField("Product Name");
        JTextField txtCalories = new JTextField("calories ");
        JComboBox<String> selectType = new JComboBox<>(new String[]{"Select Type Product", TypeProduct.Fat, TypeProduct.Carbohydrates, TypeProduct.FruitVegetable});
        formPanel.add(txtName);
        formPanel.add(txtCalories);
        formPanel.add(selectType);
        viewProducts.add(formPanel, BorderLayout.CENTER);

        // create button to add products
        JButton btnAdd = new JButton("Add Product");
        btnAdd.setPreferredSize(new Dimension(200, 40));
        btnAdd.addActionListener(e -> {
            String nameProduct = txtName.getText();
            String typeProduct = Objects.requireNonNull(selectType.getSelectedItem()).toString();
            double calories = Double.parseDouble(txtCalories.getText() == null || txtCalories.getText().isEmpty() ? "0" : txtCalories.getText());

            handleAddProduct(
                    nameProduct,
                    typeProduct,
                    calories
            );
        });
        viewProducts.add(btnAdd, BorderLayout.CENTER);
    }

    private void handleAddProduct(String name, String type, double calories) {
        System.out.println(name);
        System.out.println(type);
        System.out.println(calories);

        if(name.isEmpty() || name.equals("Product Name")  || type.equals("Select Type Product") || calories <= 0) {
            JOptionPane.showMessageDialog(null, "Please fill all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!type.equals(TypeProduct.Fat) && !type.equals(TypeProduct.Carbohydrates) && !type.equals(TypeProduct.FruitVegetable)) {
            JOptionPane.showMessageDialog(null, "Invalid product type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = db.setProduct(
                name,
                calories,
                db.getCurrentUser().getId(),
                type
        );

        db.getProductById(productId);
    }

    private void handleDeleteProduct(int productId) {
        Meal product = db.getProductById(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            db.deleteProductById(productId);
            JOptionPane.showMessageDialog(null, "Product deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

