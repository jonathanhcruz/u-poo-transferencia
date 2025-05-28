package views;

import DB.DataBase;
import Model.Meal.Meal;
import Model.User;
import interfaces.TypeProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

// Custom TableCellRenderer for JButtons
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

// Custom TableCellEditor for JButtons
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private final JTable table;
    private final ViewProducts viewProductsInstance; // Reference to ViewProducts

    public ButtonEditor(JCheckBox checkBox, JTable table, ViewProducts viewProductsInstance) {
        super(checkBox);
        this.table = table;
        this.viewProductsInstance = viewProductsInstance;
        this.button = new JButton();
        this.button.setOpaque(true);
        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int viewRow = ButtonEditor.this.table.getEditingRow();

                if (viewRow != -1) {
                    int modelRow = ButtonEditor.this.table.convertRowIndexToModel(viewRow);
                    if (modelRow >= 0 && modelRow < ButtonEditor.this.table.getModel().getRowCount()) {
                        Object idObj = ButtonEditor.this.table.getModel().getValueAt(modelRow, 0);
                        if (idObj instanceof Integer) {
                            int productId = (Integer) idObj;
                            ButtonEditor.this.viewProductsInstance.handleDeleteProductFromTable(productId, modelRow);

                            fireEditingCanceled();
                            return;
                        }
                    }
                }
                fireEditingCanceled();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.label = (value == null) ? "" : value.toString();
        this.button.setText(this.label);
        return this.button;
    }

    @Override
    public Object getCellEditorValue() {
        return this.label;
    }
}


public class ViewProducts {
    DataBase db;
    DefaultTableModel model;
    JTable table;

    public void renderView(JPanel viewProducts, DataBase db) {
        this.db = db;
        viewProducts.removeAll();
        viewProducts.setLayout(new BorderLayout());

        final User currentUser = db.getCurrentUser();
        if (currentUser == null) {
            JLabel noUserLabel = new JLabel("No user logged in. Please log in to see products.");
            noUserLabel.setHorizontalAlignment(JLabel.CENTER);
            viewProducts.add(noUserLabel, BorderLayout.CENTER);
            viewProducts.revalidate();
            viewProducts.repaint();
            return;
        }

        final ArrayList<Meal> products = db.getProductsByUserId(currentUser.getId());

        // create title of view
        JLabel title = new JLabel("Products for " + currentUser.getUserName());
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setHorizontalAlignment(JLabel.CENTER);

        // create table to show products
        String[] columnNames = {"ID", "Name", "Calories", "Type", "Action"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));


        // Populate the table with products
        for (Meal product : products) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getCalories(),
                    product.getType(),
                    "Delete" // Text for the button
            });
        }

        // Set the custom renderer and editor for the "Action" column
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table, this));


        // create form for insert products
        JPanel formPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField txtName = new JTextField("Product Name");
        JTextField txtCalories = new JTextField("Calories");
        JComboBox<String> selectType = new JComboBox<>(new String[]{"Select Type Product", TypeProduct.Fat, TypeProduct.Carbohydrates, TypeProduct.FruitVegetable});

        // Clear placeholder text on focus
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtName.getText().equals("Product Name")) {
                    txtName.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtName.getText().isEmpty()) {
                    txtName.setText("Product Name");
                }
            }
        });
        txtCalories.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtCalories.getText().equals("Calories")) {
                    txtCalories.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtCalories.getText().isEmpty()) {
                    txtCalories.setText("Calories");
                }
            }
        });


        formPanel.add(txtName);
        formPanel.add(txtCalories);
        formPanel.add(selectType);

        // create button to add products
        JButton btnAdd = new JButton("Add Product");
        btnAdd.setPreferredSize(new Dimension(200, 40));
        btnAdd.addActionListener(e -> {
            String nameProduct = txtName.getText();
            String typeProduct = Objects.requireNonNull(selectType.getSelectedItem()).toString();
            double caloriesValue = 0;
            try {
                caloriesValue = Double.parseDouble(txtCalories.getText().trim().isEmpty() ? "0" : txtCalories.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(viewProducts, "Invalid calories format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            handleAddProduct(
                    nameProduct,
                    typeProduct,
                    caloriesValue,
                    viewProducts // Pass parent for JOptionPane
            );

             // Clear form fields after adding
            txtName.setText("Product Name");
            txtCalories.setText("Calories");
            selectType.setSelectedIndex(0);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER))
        buttonContainer.add(btnAdd);
        bottomPanel.add(buttonContainer, BorderLayout.SOUTH);

        if (products.isEmpty() && currentUser != null) {
            JLabel noProductsLabel = new JLabel("No products available for " + currentUser.getUserName() + ". Add some below!");
            noProductsLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            noProductsLabel.setHorizontalAlignment(JLabel.CENTER);
            viewProducts.add(noProductsLabel, BorderLayout.CENTER);
        } else {
            viewProducts.add(scrollPane, BorderLayout.CENTER);
        }

        viewProducts.add(title, BorderLayout.NORTH);
        viewProducts.add(bottomPanel, BorderLayout.SOUTH);

        viewProducts.revalidate();
        viewProducts.repaint();
    }

    private void handleAddProduct(String name, String type, double calories, JPanel parentPanel) {
        if(name.isEmpty() || name.equals("Product Name")  || type.equals("Select Type Product") || calories <= 0) {
            JOptionPane.showMessageDialog(parentPanel, "Please fill all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!type.equals(TypeProduct.Fat) && !type.equals(TypeProduct.Carbohydrates) && !type.equals(TypeProduct.FruitVegetable)) {
            JOptionPane.showMessageDialog(parentPanel, "Invalid product type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = db.setProduct(
                name,
                calories,
                db.getCurrentUser().getId(),
                type
        );

        Meal newProduct = db.getProductById(productId);

        // Check if the product was added successfully
        if (newProduct != null && model != null) {
            model.addRow(new Object[]{
                    newProduct.getId(),
                    newProduct.getName(),
                    newProduct.getCalories(),
                    newProduct.getType(),
                    "Delete"
            });

            if (model.getRowCount() == 1) {
                parentPanel.remove(1);
                parentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        }
        JOptionPane.showMessageDialog(parentPanel, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleDeleteProductFromTable(int productId, int modelRow) {
        Meal product = db.getProductById(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "Product not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product: " + product.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            db.deleteProductById(productId);
            if (modelRow >= 0 && modelRow < model.getRowCount()) {
                model.removeRow(modelRow);
            }
            JOptionPane.showMessageDialog(null, "Product deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
