package com.aceba1.blackjack;

import com.aceba1.util.Input;

public class Main {

    public static void main(String[] args) {
        //System.out.println("♠♤♥♡♣♧♦♢");
	// write your code here
        Deck deck = new Deck();

        Hand hand = new Hand();

        while (!deck.isEmpty()) {
            hand.drawCard(deck, false);
            System.out.println(hand);

            // Testing only!
            int rm;
            do {
                rm = Input.getNum("Remove? [-1, 0-" + hand.getSize() + "]: ", -1, hand.getSize());
                if (rm != -1) deck.returnCard(hand.pullCard(rm));
            } while (rm != -1);
        }

//        while (!deck.isEmpty())
//            System.out.println(deck.pullCard());
    }
}
