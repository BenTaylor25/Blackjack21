<h1>Blackjack21</h1>

This is a simple project that I developed to help me get the hang of Java.

Uses <a href="https://github.com/trystan">Trystan Spangler</a>'s <a href="https://github.com/trystan/AsciiPanel">AsciiPanel</a>.

https://user-images.githubusercontent.com/97246704/170115881-5db87666-ceae-48e7-8aa6-808e3e21cc0b.mp4

<h3>Demo on YouTube:</h3>
- <a href="https://www.youtube.com/watch?v=eVu3l7_d7Kk">Blackjack Project Demo</a>

<h2>Blackjack</h2>

<h3>General Rules</h3>

There are many different versions of this popular card game, but they generally follow these ideas:
- There is a deck of cards containing values 1-11 (usually the same amount of each).
- Each player starts with one face-down card and one face-up card, drawn from the same deck.
    - Players can view their own face-down card, but not their opponent's.
    - Players can see all face-up cards.
- Players take turns to either Draw or Hold.
    - Draw: draw another card from the shared deck to add to your hand (face-up).
    - Hold: do not draw; pass your turn.
- The game ends when all players Hold consecutively.
- Scores are calculated by summing the values of each card in the hand.
    - Scores exceding 21 are 'bust'; there value drops to 0.
    - Therefore the optimal score is 21.
- The player with the highest score wins the round.

(I don't know how accurate this is to the casino version as I have not played casino blackjack).

<h3>Rules for this Version</h3>

- There are two players.
- There is one card for each value.
    - Therefore, if 11 has been drawn, it cannot be drawn again.
    - This decision has been made because I feel it makes the game more predictable and thus strategic.
- There is no gambling aspect in this version.


<h2>AI Algorithm</h2>

We know that playerSum = [something] + playerVisibleSum. <br>
We also know aiSum.

We also know that playerFirst is in {remainingCards + playerFirst}.

We can assign a value to each hand (playerSum and aiSum) depending on it's likelihood of winning using the following:
```
value = deckSum
if deckSum > 21 then
    value = 0                                                                                      
endif                                                                                              
```

Assume playerFirst = median( {remainingCards + playerFirst} ) <br>
Thus playerSum = playerFirst + playerVisibleSum.

Now that we have a prediction for the playerSum, we can make our choice.
```
if cpuSum < playerSum then
    draw()
else
    stick()
endif
```

I make no claim that this is the best algorithm for cpuMove(), in fact I can think of several improvements, i.e.

- assume PlayerFirst = min( Known_[Remaining + PlayerFirst] )
    - if playerSum > 21, always stick

- assume PlayerFirst = max( Known_[Remaining + PlayerFirst] )
    - if playerSum < cpuSum, always stick

-  if cpuSum + max( Known_[Remaining + PlayerFirst] ) <= 21, you can draw safely
    -  most notable when playerValue is higher for most of ( Known_[Remaining + PlayerFirst] )

However I feel that this solution is sufficient to not be too easy.

