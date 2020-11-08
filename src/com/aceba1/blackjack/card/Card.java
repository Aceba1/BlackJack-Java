package com.aceba1.blackjack.card;

public class Card {
  public final byte value;
  public final byte suit;
  public boolean show;

  public static char getSuit(byte suit) {
    return switch (suit) {
      case 0 -> '♤';
      case 1 -> '♢';
      case 2 -> '♧';
      case 3 -> '♡';
      default -> '?';
    };
  }

  public static String getFace(byte value) {
    if (value == 1) return "A ";
    if (value == 11) return "J ";
    if (value == 12) return "Q ";
    if (value == 13) return "K ";
    // No joker

    return String.format("%1$2s", value);
  }

  /**
   *
   * @param value The card's value, or representation, such as
   *              1:A [1/11], Face [2-10] 11:J [10], 12:Q [10] 13:K [10]
   * @param suit The symbol and color the card has, such as 0:Sp, 1:St, 2:Cl, 3:He. Not important to function
   */
  public Card(byte value, byte suit) {
    this.value = value;
    this.suit = suit;
    show = true;
  }

  public void setShow(boolean isUp) {
    show = isUp;
  }

  // Primarily separated for use in hands to see what they have hidden
  public String toStringAuth() {
    return '[' + getFace(value) + ' ' + getSuit(suit) + ']';
  }

  @Override
  public String toString() {
    return show ? toStringAuth() : "[ <> ]";
  }
}
