package Blackjack21.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import Blackjack21.GenerateDeck;

public class GameScreen implements Screen {
    private static GenerateDeck gameDeck;
    private static ArrayList<Integer> playerDeck;
    private static ArrayList<Integer> cpuDeck;
    private static boolean isPlayerTurn;
    private static int stickCount;

    public GameScreen() {
        ResetDecks();
    }

    private static void ResetDecks() {
        gameDeck = new GenerateDeck();
        playerDeck = new ArrayList<Integer>();
        cpuDeck = new ArrayList<Integer>();
        isPlayerTurn = true;
        stickCount = 0;

        for (int i = 0; i <= 1; i++) {
            playerDeck.add(gameDeck.PopRand());
            cpuDeck.add(gameDeck.PopRand());
        }
    }

    public void displayOutput(AsciiPanel terminal) { 
        terminal.write(" ?", 0, 0);
        for (int i = 1; i < cpuDeck.size(); i++) {
            terminal.write(getWrStr(cpuDeck, i), i*4, 0);
        }

        for (int i = 0; i < playerDeck.size(); i++) {
            terminal.write(getWrStr(playerDeck, i), i*4, 2);
        }
        
        terminal.writeCenter("J = Stick   K = Draw", 18);

        if (isPlayerTurn) {
            terminal.writeCenter("Your Turn", 22);
        } else {
            terminal.writeCenter("Computer's Turn", 22);
            terminal.writeCenter("press Enter to Continue", 23);
        }
        
        /* DEBUG
        terminal.write(getWrStr(cpuDeck, 0), 35, 0);     // Player and Cpu Deck Totals
        terminal.write(String.valueOf(stickCount), 35, 3);   
        
        ArrayList<Integer> rem = gameDeck.GetRemaining();   // Remaining Deck
        if (rem.size() > 0) {
            for (int i = 0; i < rem.size(); i++) {
                terminal.write(getWrStr(rem, i), i*4, 10);
            }
        }   
        //*/
    }

    private String getWrStr(ArrayList<Integer> deck, int ind) {
        String wrStr = Integer.toString(deck.get(ind));

        if (wrStr.length() == 1) {
            wrStr = " " + wrStr;
        }

        return wrStr;
    }

    public Screen respondToUserInput(KeyEvent key) {
        if (stickCount == 2) {
            char winner = determineWinner();
            return new GameOverScreen(winner, cpuDeck, playerDeck);
        }

        if (isPlayerTurn) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_J:   // Stick
                    doCommand('S');
                    break;
                case KeyEvent.VK_K:   // Draw
                    doCommand('D');
                    break;
            }
        } else {
            if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                cpuMove();
            }
        }
        return this;
    }

    private void doCommand(char commandID) {
        ArrayList<Integer> rem = gameDeck.GetRemaining();
        if (commandID == 'D' && rem.size() > 0) {
            playerDeck.add(gameDeck.PopRand());

            stickCount = 0;
            isPlayerTurn = false;
        } else if (commandID == 'S') {
            stickCount ++;
            isPlayerTurn = false;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //      cpuMove() Algorithm                                                                                    //
    //      -------------------                                                                                    //
    //                                                                                                             //
    //      We know that PlayerSum = PlayerFirst(?) + Known_PlayerSum                                              //
    //      We also know that CpuSum = Known_CpuSum                                                                //
    //                                                                                                             //
    //      We also know that PlayerFirst is in Known_[Remaining + PlayerFirst]                                    //
    //                                                                                                             //
    //      We can assign a value to each sum depending on it's likelihood of winning using the following:         //
    //          value = deckSum                                                                                    //
    //          if deckSum > 21 then                                                                               //
    //              value = 0                                                                                      //
    //          endif                                                                                              //
    //                                                                                                             //
    //  *   Assume PlayerSum = median( Known_[Remaining + PlayerFirst] )                                           //
    //                                                                                                             //
    //      if cpuSum < playerValue then                                                                           //
    //          draw()                                                                                             //
    //      else                                                                                                   //
    //          stick()                                                                                            //
    //      endif                                                                                                  //
    //                                                                                                             //
    //      I make no claim that this is the best algorithm for cpuMove(),                                         //
    //      in fact I can think of several improvements, i.e.                                                      //
    //                                                                                                             //
    //          - assume PlayerFirst = min( Known_[Remaining + PlayerFirst] )                                      //
    //              - if playerSum > 21, always stick                                                              //
    //                                                                                                             //
    //          - assume PlayerFirst = max( Known_[Remaining + PlayerFirst] )                                      //
    //              - if playerSum < cpuSum, always stick                                                          //
    //                                                                                                             //
    //          -  if cpuSum + max( Known_[Remaining + PlayerFirst] ) <= 21, you can draw safely                   //
    //              - most notable when playerValue is higher for most of ( Known_[Remaining + PlayerFirst] )      //
    //                                                                                                             //
    //      However this is sufficient to not be too easy or too hard.                                             //
    //                                                                                                             //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void cpuMove() {
        ArrayList<Integer> remaining = gameDeck.GetRemaining();
        remaining.add(playerDeck.get(0));

        List<Integer> playerGuessDeck = new ArrayList<Integer>(playerDeck);   // copy
        playerGuessDeck.remove(0);
        playerGuessDeck.add(median(remaining));

        int cpuSum = sum(cpuDeck);
        int playerValue = winValue(playerGuessDeck);

        if (cpuSum < playerValue && remaining.size() > 1) {
            cpuDeck.add(gameDeck.PopRand());
            stickCount = 0;
        } else {
            stickCount ++;
        }

        isPlayerTurn = true;
    }

    private int winValue(List<Integer> playerGuessDeck) {
        int value = sum(playerGuessDeck);

        if (value > 21) {
            value = 0;
        }
        return value;
    }

    private int sum(List<Integer> al) {
        int total = 0;
        for (int x : al){
            total += x;
        }
        return total;
    }

    private int median(ArrayList<Integer> deck) {
        Collections.sort(deck);
        int ind = (int)(deck.size()/2) + (deck.size()%2) - 1;

        return deck.get(ind);
    }

    private char determineWinner() {
        int playerValue = winValue(playerDeck);
        int cpuValue = winValue(cpuDeck);

        if (playerValue > cpuValue) {
            return 'p';
        }
        if (playerValue < cpuValue) {
            return 'c';
        }
        return 'd';
    }
}
