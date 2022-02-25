package colin1776.windsofmagic.spell;

import colin1776.windsofmagic.projectile.AbstractMagicProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ProjectileSpell<T extends AbstractMagicProjectile> extends Spell
{
    private final T projectile;

    public ProjectileSpell(Lore lore, SpellType type, T projectile, int baseCooldown, int baseWindup, int baseRange, boolean isContinuous)
    {
        super(lore, type, baseCooldown, baseWindup, baseRange, isContinuous);
        this.projectile = projectile;
    }

    @Override
    public boolean cast(LivingEntity caster, ItemStack castingItem)
    {
        return false;
    }
}

