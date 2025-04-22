package net.redfox.hungerrecrafted.event;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.redfox.hungerrecrafted.HungerRecrafted;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.util.HungerHelper;

@Mod.EventBusSubscriber(modid = HungerRecrafted.MOD_ID)
public class ClientEvents {
  @SubscribeEvent
  public static void onToolTip(ItemTooltipEvent event) {
    if (!event.getItemStack().isEdible()) return;
    if (event.getEntity() == null) return;

    HungerHelper.addMultiplierAndSum(
        ClientFoodHistoryData.get(),
        HungerHelper.getItemNameFromStack(event.getItemStack()),
        event.getToolTip()
    );
  }
}