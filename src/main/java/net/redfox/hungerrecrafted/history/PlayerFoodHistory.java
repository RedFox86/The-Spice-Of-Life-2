package net.redfox.hungerrecrafted.history;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;

@AutoRegisterCapability
public class PlayerFoodHistory {
  private ArrayList<String> foodHistory = new ArrayList<>();
  public static final int MAX_HISTORY_SIZE = 30;

  public ArrayList<String> getFoodHistory() {
    return foodHistory;
  }

  public void addFood(String item) {
    if (!(foodHistory.size() < MAX_HISTORY_SIZE)) foodHistory.remove(foodHistory.size()-1);
    foodHistory.add(0, item);
  }

  public void copyFrom(PlayerFoodHistory source) {
    this.foodHistory = source.foodHistory;
  }

  public void saveNBTData(CompoundTag nbt) {
    ListTag list = new ListTag();
    for (String item : foodHistory) {
      list.add(StringTag.valueOf(item));
    }
    nbt.put("foodHistory", list);
  }

  public void loadNBTData(CompoundTag nbt) {
    ArrayList<String> temp = new ArrayList<>();
    ListTag list = nbt.getList("foodHistory", StringTag.TAG_STRING);
    for (int i = 0; i < list.size(); i++) {
      temp.add(list.getString(i));
    }
    foodHistory = temp;
  }
}