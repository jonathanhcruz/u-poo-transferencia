import DB.DataBase;
import UiComponents.IconCompensar;
import UiComponents.MenuButton;
import interfaces.NamePage;
import utils.RedirectTo;
import views.ViewAboutUs;
import views.ViewLogin;
import views.ViewProducts;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class UiInterfaces extends JDialog {
  // Variables
  private JPanel contentPane;
  private JPanel menuBar;
  private JPanel views;
  private CardLayout cardLayout;
  private RedirectTo redirectTo;

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
  private void menuApp () {
    // Crear el panel lateral
    menuBar = new JPanel();
    menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
    menuBar.setPreferredSize(new Dimension(300, 800));
    menuBar.setBackground(Color.LIGHT_GRAY);

    // mount icon in JTable
    JLabel iconoCompensar = new IconCompensar("compensar.png", 200, 200);

    // add buttons
    JButton btnLogin = new MenuButton("Products");
    JButton btnAboutUs = new MenuButton("AboutUs");
    JButton btnExit = new MenuButton("Salir");


    // Actions for buttons
    btnLogin.addActionListener(_ -> cardLayout.show(views, NamePage.viewLogin));
    btnAboutUs.addActionListener(_ -> cardLayout.show(views, NamePage.viewAboutUs));
    btnExit.addActionListener(_ -> onExit());


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
    // Create the JPanel instances for view content
    JPanel viewLogin = new JPanel();
    JPanel viewProducts = new JPanel();
    JPanel viewAboutUs = new JPanel();

    views.add(viewLogin, NamePage.viewLogin);
    views.add(viewProducts, NamePage.viewProducts);
    views.add(viewAboutUs, NamePage.viewAboutUs);

    redirectTo = new RedirectTo(cardLayout, views);

    // Setup ViewLogin with a callback
    new ViewLogin().renderView(viewLogin, db, (viewName, user) -> {
      db.setCurrentUser(user);
      if (NamePage.viewProducts.equals(viewName)) {
        new ViewProducts().renderView(viewProducts, db);
      }

      redirectTo.redirectTo(viewName);
    });

    new ViewAboutUs().renderView(viewAboutUs);
  }

  private void createUIComponents() {
    DataBase db = new DataBase();
    cardLayout = new CardLayout();

    if (views != null) {
        views.setLayout(cardLayout);
    } else {
        views = new JPanel(cardLayout);
    }

    views.setPreferredSize(new Dimension(900, 800));

    menuApp();
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
