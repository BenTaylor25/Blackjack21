<h1>Blackjack21</h1>

This is a simple project that I developed to help me get the hang of Java.

Uses <a href="https://github.com/trystan">Trystan Spangler</a>'s <a href="https://github.com/trystan/AsciiPanel">AsciiPanel</a>.

<h2>Blackjack</h2>


<h2>AI algorithm</h2>                                                
We know that playerSum = [something] + playerVisibleSum.
We also know aiSum.

We also know that playerFirst is in {remainingCards + playerFirst}.

We can assign a value to each hand (playerSum and aiSum) depending on it's likelihood of winning using the following:
```
value = deckSum
if deckSum > 21 then
    value = 0                                                                                      
endif                                                                                              
```

Assume playerFirst = median( {remainingCards + playerFirst} )
Thus playerSum = playerFirst + playerVisibleSum.

Now that we have a prediction for the playerSum, we can make our choice.
```
if cpuSum < playerSum then
    draw()
else
    stick()
endif
```

I make no claim that this is the best algorithm for cpuMove(),
in fact I can think of several improvements, i.e.

- assume PlayerFirst = min( Known_[Remaining + PlayerFirst] )
  - if playerSum > 21, always stick

- assume PlayerFirst = max( Known_[Remaining + PlayerFirst] )
  - if playerSum < cpuSum, always stick

-  if cpuSum + max( Known_[Remaining + PlayerFirst] ) <= 21, you can draw safely
  - most notable when playerValue is higher for most of ( Known_[Remaining + PlayerFirst] )

However I feel that this solution is sufficient to not be too easy.

