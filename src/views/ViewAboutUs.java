package views;

import UiComponents.IconCompensar;

import javax.swing.*;
import java.awt.*;

public class ViewAboutUs {
    public void renderView (JPanel view) {
        view.setLayout(new BorderLayout());

        // Create Image
        JLabel iconoCompensar = new IconCompensar("me.jpg", 200, 200);
        iconoCompensar.setBorder(BorderFactory.createEmptyBorder(30, 0, 50, 0));

        // Create text
        final String textAboutCompensar = "Soy Jonathan Cruz, un desarrollador frontend con 7 años de experiencia en la industria. A lo largo de mi carrera, he trabajado con tecnologías como JavaScript, React, Vue, Next.js, Dart, Flutter y SQL, lo que me ha permitido desarrollar aplicaciones robustas y eficientes. Actualmente, soy Software Engineer en Mercado Libre, donde continúo aprendiendo y creciendo en el mundo del desarrollo.\n" +
                "\n" +
                "Fuera del trabajo, me apasiona viajar y explorar nuevos lugares, lo que me inspira a mantenerme curioso y abierto a nuevas experiencias. También disfruto del deporte, ya que me ayuda a mantener el equilibrio entre el trabajo y mi bienestar personal. Mi enfoque siempre ha sido el de aprender y mejorar constantemente, lo que me motiva a afrontar nuevos desafíos y continuar creciendo profesionalmente.";

        JTextArea aboutArea = new JTextArea(textAboutCompensar);
        aboutArea.setLineWrap(true);
        aboutArea.setWrapStyleWord(true);
        aboutArea.setEditable(false);
        aboutArea.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutArea.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));

        // Create panel for content
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.add(iconoCompensar);
        panelContenido.add(aboutArea);

        // Add components to the view
        view.add(panelContenido, BorderLayout.CENTER);
    }
}
