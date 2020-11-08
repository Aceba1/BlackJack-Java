package com.aceba1.blackjack;

public class Wallet {
  private int funds;

  public int getFunds() {
    return funds;
  }

  public boolean canDeductAmount(int amount) {
    return funds - amount > 0;
  }

  public void modifyFunds(int amount) {
    funds += amount;
    if (funds < 0) {
      System.out.println("Out of funds; Bankrupt!");
    }
  }

  public void setFunds(int value) {
    if (value < 0)
      System.out.println("Cannot set funds below ");
    else
      funds = value;
  }

  public Wallet() {
    this.funds = 1000;
  }

  public Wallet(int funds) {
    this.funds = funds;
  }

  @Override
  public String toString() {
    return "Wallet: " + funds;
  }
}
