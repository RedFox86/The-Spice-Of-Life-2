package net.redfox.spiceoflife.event;

import net.redfox.spiceoflife.client.ClientFoodHistoryData;
import net.redfox.spiceoflife.util.HungerHelper;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

public class AppleSkinEvents {
  public static void onAppleSkinFoodEvent(FoodValuesEvent event) {
    event.modifiedFoodValues = new FoodValues(
        (int) (HungerHelper.getMultiplierAndSum(ClientFoodHistoryData.get(), HungerHelper.getItemNameFromStack(event.itemStack)).getB() * event.modifiedFoodValues.hunger),
        event.modifiedFoodValues.saturationModifier
    );
  }
}