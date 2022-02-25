package colin1776.windsofmagic.init;

import colin1776.windsofmagic.WindsOfMagic;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.SpellType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Spells
{
    private static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, WindsOfMagic.MOD_ID);

    private static final Supplier<IForgeRegistry<Spell>> REGISTRY = SPELLS.makeRegistry("spell_registry", RegistryBuilder::new);

   // public static final RegistryObject<Spell> FIREBALL = SPELLS.register("fireball", () -> new FireballSpell(Lore.FIRE, SpellType.TYPE1, 20, 0, false));

    public static void register(IEventBus bus)
    {
        SPELLS.register(bus);
    }

    public IForgeRegistry<Spell> getSpellRegistry()
    {
        return REGISTRY.get();
    }
}
