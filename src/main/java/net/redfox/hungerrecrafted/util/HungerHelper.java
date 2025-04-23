package net.redfox.hungerrecrafted.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.config.HungerRecraftedCommonConfigs;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class HungerHelper {
  public static void appendNutritionStats(ItemStack stack, List<Component> tooltip) {
    Pair<Integer, Float> multiplierAndSum = getMultiplierAndSum(ClientFoodHistoryData.get(), HungerHelper.getItemNameFromStack(stack));
    final int sum = multiplierAndSum.getA();
    final float multiplier = multiplierAndSum.getB();

    tooltip.add(Component.translatable(
        "tooltip.hungerrecrafted.nutritional_value",
        TooltipHandler.colorComponent(Component.literal(multiplier*100+"%"), TooltipHandler.getColorFromPercentage(multiplier*100)))
    );
    if (sum != 0) {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.times_eaten",
          TooltipHandler.getWordingFromNumber(sum),
          HungerRecraftedCommonConfigs.MAX_HISTORY.get()
      ).withStyle(s -> s.withItalic(true)));
    } else {
      tooltip.add(Component.translatable(
          "tooltip.hungerrecrafted.not_recently_eaten"
      ).withStyle(s -> s.withColor(ChatFormatting.DARK_AQUA).withItalic(true)));
    }
  }

  public static Pair<Integer, Float> getMultiplierAndSum(ArrayList<String> foodHistory, String item) {
    int sum = 0;
    for (String food : foodHistory) {
      if (food.equals(item)) {
        sum++;
      }
    }

    if (sum > HungerRecraftedCommonConfigs.NUTRITION_DECAY.get().size()-1) return new Pair<>(sum, HungerRecraftedCommonConfigs.NUTRITION_DECAY.get().get(HungerRecraftedCommonConfigs.NUTRITION_DECAY.get().size()-1));
    else if (!(sum == 0)) return new Pair<>(sum, HungerRecraftedCommonConfigs.NUTRITION_DECAY.get().get(sum-1));
    else return new Pair<>(0, 1.0f);
  }

  public static String getItemNameFromStack(ItemStack stack) {
    return ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
  }
}