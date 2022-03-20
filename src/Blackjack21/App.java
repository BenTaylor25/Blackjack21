package Blackjack21;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Blackjack21.screens.Screen;
import Blackjack21.screens.MenuScreen;

public class App extends JFrame implements KeyListener {
    private AsciiPanel terminal;
    private Screen screen;

    public App() {
        super();
        terminal = new AsciiPanel(80, 24);
        add(terminal);
        pack();
        screen = new MenuScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
