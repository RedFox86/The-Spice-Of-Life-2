package net.redfox.spiceoflife;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.redfox.spiceoflife.config.SpiceOfLifeCommonConfigs;
import net.redfox.spiceoflife.event.AppleSkinEvents;
import net.redfox.spiceoflife.event.ClientEvents;
import net.redfox.spiceoflife.event.ModEvents;
import net.redfox.spiceoflife.networking.ModMessages;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SpiceOfLife.MOD_ID)
public class SpiceOfLife {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "spiceoflife";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public SpiceOfLife(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        context.registerConfig(ModConfig.Type.COMMON, SpiceOfLifeCommonConfigs.SPEC, "spiceoflife-common.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        if (ModList.get().isLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.addListener(AppleSkinEvents::onAppleSkinFoodEvent);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(
            ModMessages::register
        );
    }
}
