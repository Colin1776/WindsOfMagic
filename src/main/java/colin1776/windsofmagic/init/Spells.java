package colin1776.windsofmagic.init;

import colin1776.windsofmagic.WindsOfMagic;
import colin1776.windsofmagic.spell.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class Spells
{
    private static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, WindsOfMagic.MOD_ID);

    private static final Supplier<IForgeRegistry<Spell>> REGISTRY = SPELLS.makeRegistry("spell_registry", RegistryBuilder::new);

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
        ResourceLocation resourceLocation = new ResourceLocation(key);

        return getSpellRegistry().getValue(resourceLocation);
    }

    public static String getKeyFromSpell(Spell spell)
    {
        return Objects.requireNonNull(getSpellRegistry().getKey(spell)).toString();
    }
}
