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
    return switch (value) {
      case 1 -> "A ";
      case 11 -> "J ";
      case 12 -> "Q ";
      case 13 -> "K ";
      case 14 -> "JK";
      default -> String.format("%1$2s", value);
    };
  }

  /**
   *
   * @param value The card's value, or representation, such as
   *              1:A, [2-10]:Face, 11:J, 12:Q, 13:K, 14:JK
   * @param suit The symbol and color the card has, such as 0:Sp, 1:St, 2:Cl, 3:He, 4:?(Joker)
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
