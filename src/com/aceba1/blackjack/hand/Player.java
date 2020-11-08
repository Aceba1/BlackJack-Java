package com.aceba1.blackjack.hand;

import com.aceba1.blackjack.Wallet;
import com.aceba1.util.Input;

import java.util.ArrayList;
import java.util.List;

public class Player implements Holder {
  // Only accessible by direct type use
  public String name;
  public final Wallet wallet; // Cannot have any other wallet

  List<Hand> hands = new ArrayList<>();

  public Player(String name, Wallet wallet) {
    this.name = name;
    this.wallet = wallet;
  }

  public String getName() {
    return name;
  }

  @Override
  public void viewHand(Hand hand) {
    System.out.println("You - " + hand.toStringAuth());
  }

  @Override
  public int turn(Hand hand, int iterNum, int visibleDealerValue) {
    boolean canDouble = iterNum == 1;
    boolean canSplit = hand.canSplit();

    System.out.println("Dealer - " + visibleDealerValue);
    System.out.println("You - " + hand.toStringAuth());
    System.out.print("\nOptions:\n" +
      "- 1 : HIT\n" +
      "- 2 : STAND\n" +
      (canDouble ? "- 3 : DOUBLE\n" : "") +
      (canSplit ? "- 4 : SPLIT\n" : ""));

    while (true) {
      int choice = Input.getNum("Choice: ", 1, 4);

      if ((choice == 3 && !canDouble) ||
          (choice == 4 && !canSplit))
        System.out.println("Invalid option!");
      else {
        System.out.println();
        return choice;
      }
    }
  }

  @Override
  public void bet() {
    System.out.println(wallet);
    if (wallet.getFunds() < 10) {
      System.out.println("Funds are too low! Resetting to 50");
      wallet.setFunds(50);
    }
    int bet = Input.getNum(name + " - Bet: ", 10, wallet.getFunds());
    wallet.modifyFunds(bet);

    hands.clear();
    hands.add(new Hand(bet));
  }

  @Override
  public void modifyFunds(int amount) {
    wallet.modifyFunds(amount);
  }

  @Override
  public int getHandCount() {
    return hands.size();
  }

  @Override
  public Hand getHand(int index) {
    return hands.get(index);
  }

  @Override
  public void addHand(Hand newHand) {
    hands.add(newHand);
  }

  @Override
  public boolean insurance(Hand hand) {
    System.out.println("\nDealer has ace up!");
    int half = hand.bet / 2;
    if (!wallet.canDeductAmount(half)) {
      System.out.println("Don't have enough funds for Insurance...");
      return false;
    }
    String in = Input.getLine("Wager half of bet? (" + half + ") [0:No|1:yes]: ");
    if (in.length() != 0) {
      switch (in.charAt(0)) {
        case 'Y':
        case 'y':
        case '1':
          return true;
      }
    }
    return false;
  }
}
