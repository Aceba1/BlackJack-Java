package com.aceba1.blackjack.hand;


public class AIStrategyMap {
  // A hard hand is without any ace cards
  // A soft hand is with any ace cards
  // A pair is a hand with any 2 identical cards

  public static int getHardChoice(int playerValue, int dealerVisible) {
    return HARD_MAP[
      Math.min(
        Math.max(0, playerValue - HARD_MAP_PLAYER_START),
        HARD_MAP.length - 1)
      ][dealerVisible - 2];
  }
  public static int getSoftChoice(int playerValue, int dealerVisible) {
    return SOFT_MAP[
      Math.min(
        Math.max(0, playerValue - SOFT_MAP_PLAYER_START),
        SOFT_MAP.length - 1)
      ][dealerVisible - 2];
  }
  public static int getPairChoice(int uniValue, int dealerVisible) {
    return PAIR_MAP[uniValue][dealerVisible - 2];
  }

  private static final int
    HARD_MAP_PLAYER_START = 7,
    SOFT_MAP_PLAYER_START = 13;

  private static final byte[][]
    HARD_MAP = new byte[][] {
/*07*/genStrip(1,10),
/*08*/genStrip(1,3, 3,2, 1,5),
/*09*/genStrip(3,5, 1,5),
/*10*/genStrip(3,8, 1,2),
/*11*/genStrip(3,10),
/*12*/genStrip(1,2, 2,3, 1,5),
/*13*/genStrip(3,5, 1,5),
/*14*/genStrip(3,5, 1,5),
/*15*/genStrip(3,5, 1,5),
/*16*/genStrip(3,5, 1,5),
/*17*/genStrip(3,10)
    },
    SOFT_MAP = new byte[][] {
/*13*/genStrip(1,2, 3,3, 1,5),
/*14*/genStrip(1,2, 3,3, 1,5),
/*15*/genStrip(1,2, 3,3, 1,5),
/*16*/genStrip(1,2, 3,3, 1,5),
/*17*/genStrip(3,5, 1,5),
/*18*/genStrip(2,1, 3,4, 2,2, 1,2, 2,1),
/*19*/genStrip(2,4, 3,1, 2,5),
/*20*/genStrip(2,10),
    },
    PAIR_MAP = new byte[][] {
/*02*/genStrip(1,1, 4,5, 1,4),
/*03*/genStrip(1,2, 4,4, 1,4),
/*04*/genStrip(1,3, 3,2, 1,5),
/*05*/genStrip(3,8, 1,2),
/*06*/genStrip(4,5, 1,5),
/*07*/genStrip(4,6, 1,2, 2,1, 1,1),
/*08*/genStrip(4,10),
/*09*/genStrip(4,5, 2,1, 4,2, 2,2),
/*10*/genStrip(2,10),
/*11*/genStrip(4,10),
    };

  /**
   * Generates a 10-long byte array
   *
   * @param args Pairs of (value, count)
   * @return The generated byte strip
   */
  private static byte[] genStrip(int... args) {
    if (args.length % 2 != 0)
      throw new IllegalArgumentException("Expected a pair of integers");

    // We always expect the byte array to be 10 long
    byte[] result = new byte[10];

    int index = 0;
    for (int iter = 0; iter < args.length; iter += 2) {
      byte value = (byte) args[iter];

      int count = args[iter + 1] + index;
      for (; index < count; index++) {
        result[index] = value;
      }
    }

    return result;
  }
}
