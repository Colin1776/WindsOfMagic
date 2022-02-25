package colin1776.windsofmagic;

import colin1776.windsofmagic.init.ModItems;
import colin1776.windsofmagic.init.Spells;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WindsOfMagic.MOD_ID)
public class WindsOfMagic
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "windsofmagic";

    public WindsOfMagic()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Spells.register(bus);
        ModItems.register(bus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
}
