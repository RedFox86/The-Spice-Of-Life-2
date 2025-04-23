package net.redfox.hungerrecrafted.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.history.PlayerFoodHistory;

import java.util.ArrayList;
import java.util.List;

public class HungerHelper {
  private static final float[] MULTIPLIERS = new float[]{1.0f, 1.0f, 1.0f, 0.75f, 0.5f, 0.25f, 0.0f};

  private static int sum;

  public static int getSum() {
    return sum;
  }

  public static float getMultiplier() {
    return multiplier;
  }

  private static float multiplier;

  public static void appendNutritionStats(ItemStack stack, List<Component> tooltip) {
    updateMultiplierAndSum(ClientFoodHistoryData.get(), getItemNameFromStack(stack));

    tooltip.add(Component.translatable(
        "tooltip.hungerrecrafted.nutritional_value",
        TooltipHandler.colorComponent(Component.literal(multiplier*100+"%"), TooltipHandler.getColorFromPercentage(multiplier*100)))
    );
    if (sum != 0) {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.times_eaten",
          TooltipHandler.getWordingFromNumber(sum),
          PlayerFoodHistory.MAX_HISTORY_SIZE
      ).withStyle(s -> s.withItalic(true)));
    } else {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.not_recently_eaten"
      ).withStyle(s -> s.withColor(ChatFormatting.DARK_AQUA).withItalic(true)));
    }
  }

  public static void updateMultiplierAndSum(ArrayList<String> foodHistory, String item) {
    sum = 0;
    for (String food : foodHistory) {
      if (food.equals(item)) {
        sum++;
      }
    }

    if (sum > MULTIPLIERS.length-1) multiplier = MULTIPLIERS[MULTIPLIERS.length-1];
    else if (!(sum == 0)) multiplier = MULTIPLIERS[sum-1];
    else multiplier = 1;
  }

  public static String getItemNameFromStack(ItemStack stack) {
    return ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
  }
}