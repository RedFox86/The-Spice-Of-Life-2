package net.redfox.spiceoflife.client;

import java.util.ArrayList;

public class ClientFoodHistoryData {
  private static ArrayList<String> playerFoodHistory = new ArrayList<>();

  public static void set(ArrayList<String> history) {
    ClientFoodHistoryData.playerFoodHistory = history;
  }

  public static ArrayList<String> get() {
    return playerFoodHistory;
  }
}