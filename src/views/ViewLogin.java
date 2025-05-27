package views;

import DB.DataBase;
import Model.User;
import UiComponents.TextFile;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class ViewLogin {
    public void renderView(JPanel view, DataBase db) {
        // Add title of view
        JLabel title = new JLabel("Login to Compensar");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        view.add(title);

        // Create a box layout for the view
        Box box = Box.createVerticalBox();
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.setPreferredSize(new Dimension(800, 20));

        // Form configuration
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        formPanel.setPreferredSize(new Dimension(400, 150));

        // input fields
        JTextField txtName = new TextFile("User name");
        JTextField txtPrices = new TextFile("Password");

        // Button
        JButton btnAdd = new JButton("Login");
        btnAdd.setPreferredSize(new Dimension(200, 40));

        // Add data to DB
        btnAdd.addActionListener(e -> {
            handlerLogin(txtName, txtPrices, view, db);
        });

        formPanel.add(txtName);
        formPanel.add(txtPrices);

        // Add components to the view
        view.add(box, BorderLayout.CENTER);
        view.add(formPanel, BorderLayout.SOUTH);
        view.add(btnAdd, BorderLayout.CENTER);

        // render view
        JScrollPane scrollPane = new JScrollPane();
        view.add(scrollPane, BorderLayout.CENTER);
    }

    // Event Inventory
    private void handlerLogin (JTextField txtName, JTextField txtPassword, JPanel view, DataBase db) {
        final String userName = txtName.getText().toLowerCase();
        final String password = txtPassword.getText();

        if(userName.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(view, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final User user = db.getUserByName(userName);

        if (user == null) {
            JOptionPane.showMessageDialog(view, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!userName.equals(user.getUserName().toLowerCase()) || !password.equals(user.getPassWord())) {
            JOptionPane.showMessageDialog(view, "User or password incorrect", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        JOptionPane.showMessageDialog(view, "Welcome " + user.getUserName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        db.setCurrentUser(user);
    }
}
