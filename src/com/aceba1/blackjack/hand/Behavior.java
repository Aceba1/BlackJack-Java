package com.aceba1.blackjack.hand;

public interface Behavior {
  /**
   * This is the choice stage of the game for this behavior. It may loop or break as necessary
   * @param iterNum How many turns this behavior has taken, starts at 1
   * @return 1:HIT, 2:STAND, 3:DOUBLE, 4:SPLIT
   */
  int turn(int iterNum);

  int bet();

  String getName();
}
