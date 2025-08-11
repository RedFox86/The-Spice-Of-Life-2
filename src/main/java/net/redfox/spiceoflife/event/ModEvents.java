package net.redfox.spiceoflife.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.redfox.spiceoflife.SpiceOfLife;
import net.redfox.spiceoflife.client.ClientFoodHistoryData;
import net.redfox.spiceoflife.history.PlayerFoodHistoryProvider;
import net.redfox.spiceoflife.networking.ModMessages;
import net.redfox.spiceoflife.networking.packet.EatFoodC2SPacket;
import net.redfox.spiceoflife.networking.packet.FoodHistorySyncS2CPacket;
import net.redfox.spiceoflife.util.HungerHelper;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

@Mod.EventBusSubscriber(modid = SpiceOfLife.MOD_ID)
public class ModEvents {
  final static boolean isAppleSkinLoaded = ModList.get().isLoaded("appleskin");
  @SubscribeEvent
  public static void onEatFood(LivingEntityUseItemEvent.Finish event) {
    if (!(event.getEntity() instanceof Player p)) return;
    if (!p.level().isClientSide()) return;
    if (!event.getItem().isEdible()) return;

    ModMessages.sendToServer(new EatFoodC2SPacket(HungerHelper.getItemNameFromStack(event.getItem())));
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
    if (event.getObject() instanceof Player) {
      if (!event.getObject().getCapability(PlayerFoodHistoryProvider.PLAYER_FOOD_HISTORY).isPresent()) {
        event.addCapability(ResourceLocation.fromNamespaceAndPath(SpiceOfLife.MOD_ID, "properties"), new PlayerFoodHistoryProvider());
      }
    }
  }
  @SubscribeEvent
  public static void onPlayerClone(PlayerEvent.Clone event) {
    if (event.isWasDeath()) {
      event.getOriginal().reviveCaps();
      event.getOriginal().getCapability(PlayerFoodHistoryProvider.PLAYER_FOOD_HISTORY).ifPresent(oldStore ->
          event.getEntity().getCapability(PlayerFoodHistoryProvider.PLAYER_FOOD_HISTORY).ifPresent(newStore ->
              newStore.copyFrom(oldStore)));
    }
    event.getOriginal().invalidateCaps();
  }
  @SubscribeEvent
  public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
    if (!event.getLevel().isClientSide()) {
      if (event.getEntity() instanceof ServerPlayer player) {
        player.getCapability(PlayerFoodHistoryProvider.PLAYER_FOOD_HISTORY).ifPresent(history ->
            ModMessages.sendToPlayer(new FoodHistorySyncS2CPacket(history.getFoodHistory()), player));
      }
    }
  }
}