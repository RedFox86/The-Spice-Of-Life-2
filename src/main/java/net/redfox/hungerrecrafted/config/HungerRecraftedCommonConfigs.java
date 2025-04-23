package net.redfox.hungerrecrafted.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class HungerRecraftedCommonConfigs {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;

  public static final ForgeConfigSpec.ConfigValue<List<Float>> NUTRITION_DECAY;
  public static final ForgeConfigSpec.ConfigValue<Integer> MAX_HISTORY;

  static {
    BUILDER.push("Common configs for RedFox86's Hunger Recrafted");

    NUTRITION_DECAY = BUILDER.comment("The sequence for the decay on the nutrition values of food. Note that each value is a flat value",
        "and so they are not applied on top of each other.", "1.0 means 100% of the nutritional value, while 0.0 means 0%."
    ).define("Nutritional Decay", Arrays.asList(1.0f, 1.0f, 1.0f, 0.75f, 0.5f, 0.25f, 0.0f));

    MAX_HISTORY = BUILDER.comment("The maximum food history that is stored. In other words, the mod will remember the last N foods you",
        "ate, where N is the max history"
    ).defineInRange("Max History", 30, 3, 100);

    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}