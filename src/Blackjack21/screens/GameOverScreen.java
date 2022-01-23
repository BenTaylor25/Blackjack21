package Blackjack21.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class GameOverScreen implements Screen {
    char winner;
    ArrayList<Integer> cpuDeck;
    ArrayList<Integer> playerDeck;

    public GameOverScreen(char winnerFromGame, ArrayList<Integer> cpuDeckFromGame, ArrayList<Integer> playerDeckFromGame) {
        winner = winnerFromGame;
        cpuDeck = cpuDeckFromGame;
        playerDeck = playerDeckFromGame;
    }
    
    public void displayOutput(AsciiPanel terminal) { 
        for (int i = 0; i < cpuDeck.size(); i++) {
            terminal.write(getWrStr(cpuDeck, i), i*4, 0);
        }
        terminal.write(sumString(cpuDeck), 30, 0);

        for (int i = 0; i < playerDeck.size(); i++) {
            terminal.write(getWrStr(playerDeck, i), i*4, 2);
        }
        terminal.write(sumString(playerDeck), 30, 2);

        switch (winner) {
            case 'p':
                terminal.writeCenter("You Win", 10);
                break;
            case 'c':
                terminal.writeCenter("You Lose", 10);
                break;
            case 'd':
                terminal.writeCenter("Draw", 10);
                break;
        }

        terminal.writeCenter("Enter = Main Menu   Space = Play Again", 18);

        String wins = "Unknown";
        
        try {
            File myFile = new File("winCount.txt");
            Scanner myReader = new Scanner(myFile);
            wins = myReader.nextLine();

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred");
            e.printStackTrace();
        }

        if (winner == 'p') {
            int winsInt = Integer.valueOf(wins) + 1;
            wins = Integer.toString(winsInt);

            try {
                FileWriter myWriter = new FileWriter("winCount.txt");
                myWriter.write(wins);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occured.");
                e.printStackTrace();
            }
        }
        terminal.write("Total Wins: "+wins, 0, 23);
    }

    private String getWrStr(ArrayList<Integer> deck, int ind) {
        String wrStr = Integer.toString(deck.get(ind));

        if (wrStr.length() == 1) {
            wrStr = " " + wrStr;
        }

        return wrStr;
    }

    private String sumString(List<Integer> al) {
        int total = 0;
        for (int x : al){
            total += x;
        }
        return Integer.toString(total);
    }

    public Screen respondToUserInput(KeyEvent key) { 
        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            return new MenuScreen();
        }
        if (key.getKeyCode() == KeyEvent.VK_SPACE) {
            return new GameScreen();
        }
        return this;
    }
}
