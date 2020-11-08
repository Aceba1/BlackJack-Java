package com.aceba1.blackjack;

import com.aceba1.blackjack.card.Deck;
import com.aceba1.blackjack.card.RiggedDeck;
import com.aceba1.blackjack.card.ShuffledDeck;
import com.aceba1.blackjack.game.Game;
import com.aceba1.blackjack.hand.*;
import com.aceba1.util.Input;

import java.util.ArrayList;
import java.util.List;

public class Menu {
  static Deck deckInUse = new ShuffledDeck();
  static final List<Holder> playerList = new ArrayList<>();

  static final Game game = new Game();

  public static void run() {
    playerList.add(new Player("Player", new Wallet()));
    playerList.add(new Dealer(false));
    game.configure(deckInUse, playerList);

    //noinspection StatementWithEmptyBody
    while (menu()) { /*loop*/ }
  }

  static boolean menu() {
    System.out.println("\nMenu!\n" +
      "- 1 : Play\n" +
      "- 2 : Configure\n" +
      "- 0 : Quit\n");
    switch (Input.getNum("Choice: ", 0, 2)) {
      case 1 -> game.start();
      case 2 -> configure();
      case 0 -> {
        return false; // Break the loop, terminate the CLI
      }
    }

    return true;
  }

  static void configure() {
    while (true) {
      System.out.println("\nConfigure Game!\n" +
        "- 1 : Players\n" +
        "- 2 : Deck type\n" +
        "- 0 : Back\n");
      switch (Input.getNum("Choice: ", 0, 2)) {
        case 1 -> setPlayers();
        case 2 -> setDeckType();
        case 0 -> {
          return;
        }
      }
    }
  }

  static void setDeckType() {
    System.out.println("\nSet Deck! Current: " + deckInUse.getName() + "\n" +
      "- 1 : Shuffled Draw\n" +
      "- 2 : Select Draw (DEBUG)\n" +
      "- 0 : Back\n");
    switch (Input.getNum("Choice: ", 0, 2)) {
      case 1 -> deckInUse = new ShuffledDeck();
      case 2 -> deckInUse = new RiggedDeck();
      case 0 -> {
        return;
      }
    }
    game.setDeck(deckInUse);
  }

  static void setPlayers() {
    while (true) {
      System.out.println("\nSet Players!");

      int count = 0;
      while (count < playerList.size() - 1)
        System.out.println((count + 1) + " > " +
          playerList.get(count++).getName());

      System.out.println("- " + (++count) + " : New Player\n" +
        "- 0 : Back\n");

      // Cannot use a switch case for this!
      int choice = Input.getNum("Choice: ", 0, count);

      if (choice == 0) return;
      if (choice == count)
        addPlayer();
      else
        modifyPlayer(choice - 1);

      // No need to modify the game directly, as we have the player list in reference;
    }
  }

  static void modifyPlayer(int index) {
    Holder holder = playerList.get(index);

    boolean playerFlag = holder instanceof Player;
    Player player = playerFlag ? (Player) holder : null;
    while (true) {
      System.out.println("\nModifying " + (index + 1) + " - " + holder.getName() +
        " (" + holder.getClass().getSimpleName() + "):\n" +
        "- 1 : Move Up\n" +
        "- 2 : Move Down\n" +
        "- 3 : Destroy\n" +
        (playerFlag ?
          "- 4 : Set Wallet Funds (" + player.wallet.getFunds() + ")\n" +
          "- 5 : Set Display Name\n" : "") +
        "- 0 : Back\n");
      switch (Input.getNum("Choice: ", 0, playerFlag ? 5 : 3)) {
        case 1 -> {
          if (index > 0)
            playerList.add(index - 1, playerList.remove(index--));
          else
            System.out.println("Cannot move any higher!");
        }
        case 2 -> {
          if (index < playerList.size() - 2)
            playerList.add(index + 1, playerList.remove(index++));
          else
            System.out.println("Cannot move below Dealer!");
        }
        case 3 -> {
          if (playerList.size() > 2) {
            playerList.remove(index);
            System.out.println("Instance Destroyed...");
            return; // Escape!
          } else {
            System.out.println("Must have at least 1!");
          }
        }
        case 4 -> { // Player cannot be null, as we limit the input above
          System.out.println(player.wallet);
          player.wallet.setFunds(
            Input.getNum("Set new funds [10-1000000000]: ", 10, 1_000_000_000)
          );
        }
        case 5 -> player.name = Input.getLine("New Name: ");
        case 0 -> {
          return; // Also escape!
        }
      }
    }
  }

  static void addPlayer() {
    System.out.println("\nAdd Player!\n" +
      "- 1 : Player (Command Line)\n" +
      "- 2 : Multi-Hand Player\n" +
      "- 3 : Strategic AI\n" +
      "- 4 : Dealer AI\n" +
      "- 0 : Back\n");
    switch (Input.getNum("Choice: ", 0, 4)) {
      case 1 -> {
        Player player = new Player(
          Input.getLine("Player Name: "),
          new Wallet()
        );
        playerList.add(playerList.size() - 1, player);
        System.out.println(player.getName() + " added!");
      }
      case 2 -> {
        PlayerMultiHand player = new PlayerMultiHand(
          Input.getLine("Player Name: "),
          new Wallet()
        );
        playerList.add(playerList.size() - 1, player);
        System.out.println(player.getName() + " added!");
      }
      case 3 -> {
        AI ai = new AI();
        playerList.add(playerList.size() - 1, ai);
        System.out.println(ai.getName() + " added!");
      }
      case 4 -> {
        Dealer dealer = new Dealer(true);
        playerList.add(playerList.size() - 1, dealer);
        System.out.println(dealer.getName() + " added!");
      }
    }
  }
}
