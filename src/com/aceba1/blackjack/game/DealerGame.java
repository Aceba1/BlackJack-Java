package com.aceba1.blackjack.game;

import com.aceba1.blackjack.hand.Dealer;
import com.aceba1.blackjack.hand.Hand;
import com.aceba1.blackjack.hand.Player;

public class DealerGame extends Game {
  public DealerGame() {
    hands.add(new Player("Player"));
    hands.add(new Dealer()); // Dealer plays after the player
  }

  private int valueOrBust(Hand hand) {
    int value = hand.getValue();
    if (value > 21) return 0;
    return value;
  }

  @Override
  protected void finish() {
    Hand player = hands.get(0);
    Hand dealer = hands.get(1);

    //TODO: Implement wallet system
    int pValue = valueOrBust(player);
    int dValue = valueOrBust(dealer);
    if (pValue > dValue) {
      System.out.println("WIN : " + bets[0] * 2);
    } else if (pValue < dValue) {
      System.out.println("LOSE : 0");
    } else {
      System.out.println("PUSH : " + bets[0]);
    }

    super.finish();
  }
}
