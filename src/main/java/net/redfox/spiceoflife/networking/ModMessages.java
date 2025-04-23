package net.redfox.spiceoflife.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.redfox.spiceoflife.SpiceOfLife;
import net.redfox.spiceoflife.networking.packet.EatFoodC2SPacket;
import net.redfox.spiceoflife.networking.packet.FoodHistorySyncS2CPacket;

public class ModMessages {
  private static SimpleChannel INSTANCE;

  private static int packetId = 0;
  private static int id() {
    return packetId++;
  }

  public static void register() {
    SimpleChannel net = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(SpiceOfLife.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
    INSTANCE = net;

    net.messageBuilder(EatFoodC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
        .decoder(EatFoodC2SPacket::new)
        .encoder(EatFoodC2SPacket::toBytes)
        .consumerMainThread(EatFoodC2SPacket::handle)
        .add();
    net.messageBuilder(FoodHistorySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        .decoder(FoodHistorySyncS2CPacket::new)
        .encoder(FoodHistorySyncS2CPacket::toBytes)
        .consumerMainThread(FoodHistorySyncS2CPacket::handle)
        .add();
  }

  public static <MSG> void sendToServer(MSG message) {
    INSTANCE.sendToServer(message);
  }

  public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
    INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
  }
}