package net.redfox.spiceoflife.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TooltipHandler {
  private static final String[] NUMBER_WORDS = new String[]{"once", "twice", "three times", "four times", "five times", "six times", "seven times", "eight times", "nine times", "ten times"};

  public static Component color(MutableComponent component, ChatFormatting color) {
    return component.withStyle(s -> s.withColor(color));
  }
  public static Component colorWithItalics(MutableComponent component, ChatFormatting color) {
    return component.withStyle(s -> s.withColor(color).withItalic(true));
  }
  public static ChatFormatting getColorFromPercentage(float percentage) {
    return switch ((int) (percentage/25)) {
      case 4 -> ChatFormatting.DARK_GREEN;
      case 3 -> ChatFormatting.GREEN;
      case 2 -> ChatFormatting.YELLOW;
      case 1 -> ChatFormatting.RED;
      case 0 -> ChatFormatting.DARK_RED;
      default -> ChatFormatting.WHITE;
    };
  }
  public static String getWordingFromNumber(int num) {
    if (num-1 < 10) return NUMBER_WORDS[num-1];
    return num + " times";
  }
}