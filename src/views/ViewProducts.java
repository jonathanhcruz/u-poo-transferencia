package views;

import DB.DataBase;

import javax.swing.*;
import java.awt.*;

public class ViewProducts {
    public void renderView(JPanel viewProducts, DataBase db) {
        // Implementation for rendering the products view will here
        viewProducts.setLayout(new BoxLayout(viewProducts, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Products");
        title.setFont(new Font("Arial", Font.BOLD, 20));

    }
}
