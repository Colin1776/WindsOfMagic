package colin1776.windsofmagic.init;

import colin1776.windsofmagic.WindsOfMagic;
import colin1776.windsofmagic.spell.*;
import net.minecraft.resources.ResourceLocation;
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

    /** TEMPORARY DEBUG SPELLS */
    public static final RegistryObject<Spell> ALPHA = SPELLS.register("alpha", () -> new DebugSpell("Alpha", Lore.FIRE, Tier.BEGINNER, Type.UTILITY,
            30, 100, 0, 30, true));
    public static final RegistryObject<Spell> DELTA = SPELLS.register("delta", () -> new DebugSpell("Delta", Lore.HEAVENS, Tier.BEGINNER, Type.PROJECTILE,
            50, 80, 0, 40, false));
    public static final RegistryObject<Spell> THETA = SPELLS.register("theta", () -> new DebugSpell("Theta", Lore.ICE, Tier.ADVANCED, Type.UTILITY,
            5, 10, 0, 15, true));
    public static final RegistryObject<Spell> GAMMA = SPELLS.register("gamma", () -> new DebugSpell("Gamma", Lore.FIRE, Tier.BEGINNER, Type.PROJECTILE,
            8, 20, 0, 10, true));
    /** TEMPORARY DEBUG SPELLS */

    public static final RegistryObject<Spell> IGNITE = SPELLS.register("ignite", IgniteSpell::new);
    public static final RegistryObject<Spell> FIREBALL = SPELLS.register("fireball", () -> new FireballSpell("Fireball", Lore.FIRE, Tier.BEGINNER, Type.PROJECTILE, 5, 10, 0, 10, false));
    public static final RegistryObject<Spell> SECOND = SPELLS.register("second", () -> new FireballSpell("Second", Lore.FIRE, Tier.BEGINNER, Type.PROJECTILE, 5, 10, 0, 10, false));

    public static void register(IEventBus bus)
    {
        SPELLS.register(bus);
    }

    public static IForgeRegistry<Spell> getSpellRegistry()
    {
        return REGISTRY.get();
    }

    public static Spell getSpellFromKey(String key)
    {
        ResourceLocation res = new ResourceLocation(key);

        return getSpellRegistry().getValue(res);
    }

    public static String getKeyFromSpell(Spell spell)
    {
        ResourceLocation res = getSpellRegistry().getKey(spell);

        return res != null ? res.toString() : empty().toString();
    }

    public static Spell empty()
    {
        return new EmptySpell();
    }
}
