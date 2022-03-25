package colin1776.windsofmagic.init;

import colin1776.windsofmagic.WindsOfMagic;
import colin1776.windsofmagic.item.DebugItem;
import colin1776.windsofmagic.item.OldStaffItem;
import colin1776.windsofmagic.item.StaffItem;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Tier;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModItems
{
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WindsOfMagic.MOD_ID);

    public static final RegistryObject<Item> STAFF = ITEMS.register("staff", () -> new StaffItem(new Item.Properties(), Tier.ADVANCED, Lore.FIRE));

    public static final RegistryObject<Item> TEST_STAFF = ITEMS.register("test_staff", () -> new OldStaffItem(new Item.Properties(), Tier.BEGINNER, Lore.FIRE));
    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("spell_debug_stick", () -> new DebugItem(new Item.Properties()));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
