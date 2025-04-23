package net.redfox.hungerrecrafted.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.redfox.hungerrecrafted.client.ClientFoodHistoryData;
import net.redfox.hungerrecrafted.util.HungerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
  @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
  private void modifyFoodData(Level level, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
    if (!level.isClientSide) {
      ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
      FoodProperties properties = stack.getFoodProperties(serverPlayer);

      final float multiplier = HungerHelper.getMultiplierAndSum(ClientFoodHistoryData.get(), HungerHelper.getItemNameFromStack(stack)).getB();
      serverPlayer.getFoodData().eat((int) (properties.getNutrition() * multiplier), properties.getSaturationModifier());

      serverPlayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
      level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
      CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
      if (stack.isEdible()) {
        level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);

        Item item = stack.getItem();
        if (item.isEdible()) {
          for (Pair<MobEffectInstance, Float> pair : stack.getFoodProperties(serverPlayer).getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
              serverPlayer.addEffect(new MobEffectInstance(pair.getFirst()));
            }
          }
        }

        if (!serverPlayer.getAbilities().instabuild) {
          stack.shrink(1);
        }

        serverPlayer.gameEvent(GameEvent.EAT);
      }

      serverPlayer.connection.send(new ClientboundSetHealthPacket(
          serverPlayer.getHealth(),
          serverPlayer.getFoodData().getFoodLevel(),
          serverPlayer.getFoodData().getSaturationLevel()
      ));

      cir.setReturnValue(stack);
    } else {
      Player player = (Player)(Object)this;

      player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
      if (stack.isEdible()){
        level.playSound(null, player.getX(), player.getY(), player.getZ(), player.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
        Item item = stack.getItem();
        if (item.isEdible()) {
          for (Pair<MobEffectInstance, Float> pair : stack.getFoodProperties(player).getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
              player.addEffect(new MobEffectInstance(pair.getFirst()));
            }
          }
        }

        if (!player.getAbilities().instabuild) {
          stack.shrink(1);
        }

        player.gameEvent(GameEvent.EAT);

        cir.setReturnValue(stack);
      }
    }
  }
}