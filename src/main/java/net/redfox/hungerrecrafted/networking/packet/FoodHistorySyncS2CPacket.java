package net.redfox.hungerrecrafted.networking.packet;

import com.google.common.graph.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;

import java.util.ArrayList;
import java.util.function.Supplier;

public class FoodHistorySyncS2CPacket {
  private final ArrayList<String> foodHistory;

  public FoodHistorySyncS2CPacket(ArrayList<String> foodHistory) {
    this.foodHistory = foodHistory;
  }

  public FoodHistorySyncS2CPacket(FriendlyByteBuf buf) {
    int size = buf.readVarInt();
    ArrayList<String> list = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      list.add(buf.readUtf());
    }
    foodHistory = list;
  }

  public void toBytes(FriendlyByteBuf buf) {
    buf.writeVarInt(foodHistory.size());
    for (String str : foodHistory) {
      buf.writeUtf(str);
    }
  }

  public boolean handle(Supplier<NetworkEvent.Context> supplier) {
    NetworkEvent.Context context = supplier.get();
    context.enqueueWork(() -> {
      ClientFoodHistoryData.set(foodHistory);
    });
    return true;
  }
}