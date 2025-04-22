package net.redfox.hungerrecrafted;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.redfox.hungerrecrafted.networking.ModMessages;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HungerRecrafted.MOD_ID)
public class HungerRecrafted  {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "hungerrecrafted";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public HungerRecrafted(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }
}
