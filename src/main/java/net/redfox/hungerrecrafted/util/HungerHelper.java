package net.redfox.hungerrecrafted.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class HungerHelper {
  private static final float[] MULTIPLIERS = new float[]{1.0f, 1.0f, 1.0f, 0.75f, 0.5f, 0.25f, 0.0f};

  public static float getMultiplier(ArrayList<String> foodHistory, String item) {
    int sum = 0;
    for (String food : foodHistory) {
      if (food.equals(item)) {
        sum++;
      }
    }
    if (sum > MULTIPLIERS.length-1) return MULTIPLIERS[MULTIPLIERS.length-1]*100;
    if (sum == 0) return 1.0f*100;
    return MULTIPLIERS[sum-1]*100;
  }

  public static String getItemNameFromStack(ItemStack stack) {
    return ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
  }
}