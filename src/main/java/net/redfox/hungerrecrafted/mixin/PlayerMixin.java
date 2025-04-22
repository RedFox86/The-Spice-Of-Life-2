package net.redfox.hungerrecrafted.mixin;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.util.HungerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
  @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
  private void modifyFoodData(Level level, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
    if (level.isClientSide()) return;
    if (stack.getItem().isEdible()) {
      ServerPlayer serverPlayer = (ServerPlayer)(Object)this;
      FoodProperties properties = stack.getFoodProperties(serverPlayer);
      serverPlayer.getFoodData().eat(-properties.getNutrition(), -properties.getSaturationModifier());
      
      float multiplier = HungerHelper.getMultiplierAndSum(ClientFoodHistoryData.get(), HungerHelper.getItemNameFromStack(stack))[0];
      serverPlayer.getFoodData().eat((int)(properties.getNutrition()*multiplier), properties.getSaturationModifier());
      serverPlayer.connection.send(new ClientboundSetHealthPacket(
          serverPlayer.getHealth(),
          serverPlayer.getFoodData().getFoodLevel(),
          serverPlayer.getFoodData().getSaturationLevel()
          ));
    }
  }
}