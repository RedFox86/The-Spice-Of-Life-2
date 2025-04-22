package net.redfox.hungerrecrafted.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.redfox.hungerrecrafted.history.PlayerFoodHistory;

import java.util.ArrayList;
import java.util.List;

public class HungerHelper {
  private static final float[] MULTIPLIERS = new float[]{1.0f, 1.0f, 1.0f, 0.75f, 0.5f, 0.25f, 0.0f};

  public static void addMultiplierAndSum(ArrayList<String> foodHistory, String item, List<Component> tooltip) {
    float[] multiplierAndSum = getMultiplierAndSum(foodHistory, item);

    final float finalMultiplier = multiplierAndSum[0]*100;
    final int sum = (int)multiplierAndSum[1];

    tooltip.add(Component.translatable(
        "tooltip.hungerrecrafted.nutritional_value",
        Component.literal(finalMultiplier+"%").withStyle(s -> {
          if (finalMultiplier == 100) return s.withColor(ChatFormatting.GREEN);
          else if (finalMultiplier >= 75) return s.withColor(ChatFormatting.DARK_GREEN);
          else if (finalMultiplier >= 50) s.withColor(ChatFormatting.YELLOW);
          else if (finalMultiplier >= 25) s.withColor(ChatFormatting.RED);
          return s.withColor(ChatFormatting.DARK_RED);
        })
    ));
    if (sum != 0) {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.times_eaten",
          getWordingFromNumber(sum),
          PlayerFoodHistory.MAX_HISTORY_SIZE
      ).withStyle(s -> s.withItalic(true)));
    } else {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.not_recently_eaten"
      ).withStyle(s -> s.withColor(ChatFormatting.AQUA).withItalic(true)));
    }
  }

  public static float[] getMultiplierAndSum(ArrayList<String> foodHistory, String item) {
    int sum = 0;
    for (String food : foodHistory) {
      if (food.equals(item)) {
        sum++;
      }
    }

    if (sum > MULTIPLIERS.length-1) return new float[]{MULTIPLIERS[MULTIPLIERS.length-1], sum};
    if (!(sum == 0)) return new float[]{MULTIPLIERS[sum-1], sum};
    return new float[]{1, sum};
  }

  private static String getWordingFromNumber(int num) {
    return switch (num) {
      case 1 -> "once";
      case 2 -> "twice";
      default -> num + " times";
    };
  }

  public static String getItemNameFromStack(ItemStack stack) {
    return ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
  }
}