package colin1776.windsofmagic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DebugSpell extends Spell
{
    // TODO just for debugging delete when publishing

    public DebugSpell(String name, Lore lore, Tier tier, Type type, int baseCost, int baseCooldown, int windup, int range, boolean isContinuous)
    {
        super(name, lore, tier, type, baseCost, baseCooldown, windup, range, isContinuous);
    }

    @Override
    public boolean cast(LivingEntity caster, ItemStack castingItem, int ticksInCast)
    {
        System.out.println(this + " is being casted");

        return true;
    }
}
