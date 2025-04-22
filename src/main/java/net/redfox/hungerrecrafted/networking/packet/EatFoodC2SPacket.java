package net.redfox.hungerrecrafted.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.history.PlayerFoodHistoryProvider;
import net.redfox.hungerrecrafted.networking.ModMessages;

import java.util.ArrayList;
import java.util.function.Supplier;

public class EatFoodC2SPacket {
  private final String foodName;

  public EatFoodC2SPacket(String foodName) {
    this.foodName = foodName;
  }

  public EatFoodC2SPacket(FriendlyByteBuf buf) {
    foodName = buf.readUtf();
  }

  public void toBytes(FriendlyByteBuf buf) {
    buf.writeUtf(foodName);
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      ServerPlayer player = context.getSender();
      player.getCapability(PlayerFoodHistoryProvider.PLAYER_FOOD_HISTORY).ifPresent(history -> {
        history.addFood(foodName);
        ModMessages.sendToPlayer(new FoodHistorySyncS2CPacket(history.getFoodHistory()), player);
      });
    });
    return true;
  }
}