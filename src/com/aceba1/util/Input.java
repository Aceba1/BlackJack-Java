package com.aceba1.util;

import java.util.Scanner;

public class Input {
  final static Scanner in = new Scanner(System.in);

  public static String getLine(String prompt) {
    System.out.print(prompt);
    return in.nextLine().trim().toLowerCase();
  }

  public static int getNum(String prompt, int minValue, int maxValue) {
    while (true) {
      System.out.print(prompt);
      try {
        int val = in.nextInt();
        in.nextLine();

        if (val > maxValue || val < minValue)
          System.out.println("Out of range (" + minValue + "-" + maxValue + ")");
        else
          return val;
      } catch (Exception e) {
        System.out.println("Entry not a number");
        in.nextLine(); // Clear buffer
      }
    }
  }
}
