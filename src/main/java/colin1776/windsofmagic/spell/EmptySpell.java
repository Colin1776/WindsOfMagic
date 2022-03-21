package colin1776.windsofmagic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EmptySpell extends Spell
{

    public EmptySpell()
    {
        super("[Empty Slot]", Lore.NONE, null, null, 0, 0, 0, 0, false);
    }

    @Override
    public boolean cast(LivingEntity caster, ItemStack castingItem, int ticksInCast)
    {
        return false;
    }
}
