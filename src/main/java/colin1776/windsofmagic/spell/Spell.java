package colin1776.windsofmagic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Spell extends ForgeRegistryEntry<Spell>
{
    public Spell()
    {

    }

    public abstract boolean cast(LivingEntity caster, ItemStack castingItem);
}
