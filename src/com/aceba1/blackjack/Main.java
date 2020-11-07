package com.aceba1.blackjack;

import com.aceba1.blackjack.card.Deck;
import com.aceba1.blackjack.game.DealerGame;
import com.aceba1.blackjack.game.Game;
import com.aceba1.blackjack.hand.Hand;

public class Main {

    public static void main(String[] args) {
        Game game = new DealerGame();
        while(true) {
            System.out.println("\nBlackJack - New game (" + game.getClass().getSimpleName() + ")");
            game.start();
        }
    }
}
