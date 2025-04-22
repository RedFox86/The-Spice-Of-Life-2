package net.redfox.hungerrecrafted.history;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFoodHistoryProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
  public static Capability<PlayerFoodHistory> PLAYER_FOOD_HISTORY = CapabilityManager.get(new CapabilityToken<PlayerFoodHistory>() {});

  private PlayerFoodHistory foodHistory = null;
  private final LazyOptional<PlayerFoodHistory> optional = LazyOptional.of(this::createPlayerFoodHistory);

  private PlayerFoodHistory createPlayerFoodHistory() {
    if (this.foodHistory == null) {
      this.foodHistory = new PlayerFoodHistory();
    }
    return this.foodHistory;
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == PLAYER_FOOD_HISTORY) {
      return optional.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public CompoundTag serializeNBT() {
    CompoundTag nbt = new CompoundTag();
    createPlayerFoodHistory().saveNBTData(nbt);
    return nbt;
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    createPlayerFoodHistory().loadNBTData(nbt);
  }
}