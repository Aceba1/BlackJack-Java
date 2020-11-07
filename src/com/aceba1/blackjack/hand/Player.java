package com.aceba1.blackjack.hand;

import com.aceba1.util.Input;

public class Player extends Hand {
  String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public int turn(int iterNum) {
    boolean canDouble = iterNum == 1;
    boolean canSplit = getSize() == 2 &&
      Math.min(10, cards.get(0).value) == Math.min(10, cards.get(1).value);

    System.out.println(toStringAuth());
    System.out.print("\nOptions:\n" +
      "- 1 : HIT\n" +
      "- 2 : STAND\n" +
      (canDouble ? "- 3 : DOUBLE\n" : "") +
      (canSplit ? "- 4 : SPLIT\n" : ""));

    while (true) {
      int choice = Input.getNum("Choice: ", 1, 4);

      if ((choice == 3 && !canDouble) || (choice == 4 && !canSplit))
        System.out.println("Invalid option!");
      else {
        System.out.println();
        return choice;
      }
    }
  }

  @Override
  public int bet() {
    return Input.getNum(name + " - Bet: ", 10, 1000000000);
  }
}
