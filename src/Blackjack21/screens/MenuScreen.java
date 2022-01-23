package Blackjack21.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuScreen implements Screen {
    
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("21 Blackjack", 2);

        /*
        terminal.writeCenter("21 Blackjack is a two-player card game where each player starts with two cards", 7);
        terminal.writeCenter("from a deck of cards valued 1-11.", 8);
        terminal.writeCenter("Each player's first card is face down and only that player knows its value.", 9);
        terminal.writeCenter("Every other card is face up and can be seen by both players.", 10);
        terminal.writeCenter("The goal is to finish the round with the higher score,", 11);
        terminal.writeCenter("but if you go over 21, you lose.", 12);
        terminal.writeCenter("Players can choose to Draw or Stick.", 13);
        terminal.writeCenter("To Draw is to take another card from the shared deck.", 14);
        terminal.writeCenter("To Stick is to keep the cards you have.", 15);
        terminal.writeCenter("The game ends when both players Stick consecutively.", 15);
        //*/

        ///*
        try {
            File myFile = new File("test.txt");
            Scanner myReader = new Scanner(myFile);
            int y = 7;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                terminal.writeCenter(data, y);
                y++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //*/

        terminal.writeCenter("J = Stick   K = Draw   Enter = Continue", 18);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new GameScreen() : this;
    }
}
