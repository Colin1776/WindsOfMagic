package colin1776.windsofmagic.init;

import colin1776.windsofmagic.WindsOfMagic;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WindsOfMagic.MOD_ID);

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
