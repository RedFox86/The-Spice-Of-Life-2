package net.redfox.spiceoflife.event;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.redfox.spiceoflife.SpiceOfLife;
import net.redfox.spiceoflife.util.HungerHelper;

@Mod.EventBusSubscriber(modid = SpiceOfLife.MOD_ID)
public class ClientEvents {
  @SubscribeEvent
  public static void onToolTip(ItemTooltipEvent event) {
    if (!event.getItemStack().isEdible()) return;
    if (event.getEntity() == null) return;

    HungerHelper.appendNutritionStats(event.getItemStack(), event.getToolTip());
  }
}