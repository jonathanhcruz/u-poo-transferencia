package utils;

import interfaces.NamePage;

import javax.swing.*;
import java.awt.*;

public class RedirectTo {
    private final CardLayout cardLayout;
    private final JPanel views;

    public RedirectTo(CardLayout cardLayout, JPanel views) {
        this.cardLayout = cardLayout;
        this.views = views;
    }

    public void redirectTo(String viewName) {
        switch (viewName) {
            case NamePage.viewLogin:
                break;
            case NamePage.viewAboutUs:
                break;
            case NamePage.viewProducts:
                break;
            default:
                throw new IllegalArgumentException("Unknown view: " + viewName);
        }
        cardLayout.show(views, viewName);
    }
}
