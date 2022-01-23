package Blackjack21;

import java.lang.Math;
import java.util.ArrayList;

public class GenerateDeck {
    protected ArrayList<Integer> fullDeck;

    public GenerateDeck() {
        fullDeck = new ArrayList<Integer>();
        ResetDeck();
    }

    public void ResetDeck() {
        fullDeck.clear();
        for (int i = 1; i <= 11; i++) {
            fullDeck.add(i);
        }
    }

    public int PopRand() {
        if (fullDeck.size() > 0) {
            int ind = (int)(Math.random() * (fullDeck.size()-1));

            int popVal = fullDeck.get(ind);
            fullDeck.remove(ind);

            return popVal;
        }
        return -1;
    }

    public ArrayList<Integer> GetRemaining() {
        return new ArrayList<Integer>(fullDeck);
    }
}
