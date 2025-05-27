import DB.DataBase;
import UiComponents.IconCompensar;
import UiComponents.MenuButton;
import views.ViewAboutUs;
import views.ViewLogin;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class UiInterfaces extends JDialog {
  // Variables
  private DataBase db;
  private JPanel contentPane;
  private JPanel menuBar;
  private JPanel views;
  private CardLayout cardLayout;

  // General methods
  private void onExit() {
    dispose();
  }

  // Interface methods
  public UiInterfaces() {
    setContentPane(contentPane);
    setModal(true);

    // config window properties
    setLayout(new BorderLayout());

    // mount of ui components
    createUIComponents();

    // add components to the window
    add(menuBar, BorderLayout.WEST);
    add(views, BorderLayout.CENTER);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onExit();
      }
    });
  }

  // Menu
  private void menuApp (DataBase db) {
    // Crear el panel lateral
    menuBar = new JPanel();
    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
    menuBar.setPreferredSize(new Dimension(300, 800));
    menuBar.setBackground(Color.LIGHT_GRAY);

    // mount icon in JTable
    JLabel iconoCompensar = new IconCompensar("compensar.png", 200, 200);

    // add buttons
    JButton btnLogin = new MenuButton("Login");
    JButton btnAboutUs = new MenuButton("AboutUs");
    JButton btnExit = new MenuButton("Salir");


    // Acciones de botones
    btnLogin.addActionListener(e -> {
      cardLayout.show(views, "Login");
    });
    btnAboutUs.addActionListener(e -> {
      cardLayout.show(views, "AboutUs");
    });
    btnExit.addActionListener(e -> onExit());


    // Add components to the menu
    menuBar.add(Box.createVerticalStrut(50));
    menuBar.add(iconoCompensar);
    menuBar.add(Box.createVerticalStrut(10));
    menuBar.add(btnLogin);
    menuBar.add(Box.createVerticalStrut(10));
    menuBar.add(btnAboutUs);
    menuBar.add(Box.createVerticalStrut(10));
    menuBar.add(btnExit);
  }

  // Create views
  private void createViews(DataBase db) {
    // create views
    JPanel viewLogin = new JPanel();
    new ViewLogin().renderView(viewLogin, db);

    JPanel viewAboutUs = new JPanel();
    new ViewAboutUs().renderView(viewAboutUs);

    // Add views
    views.add(viewLogin, "Login");
    views.add(viewAboutUs, "AboutUs");
  }

  private void createUIComponents() {
    DataBase db = new DataBase();
    cardLayout = new CardLayout();
    views = new JPanel(cardLayout);


    views.setPreferredSize(new Dimension(900, 800));
    views.setBackground(Color.WHITE);

    menuApp(db);
    createViews(db);
  }

  public static void main(String[] args) {
    UiInterfaces inventory = new UiInterfaces();
    inventory.setSize(1200, 800);
    inventory.pack();
    inventory.setVisible(true);
    System.exit(0);
  }
}
