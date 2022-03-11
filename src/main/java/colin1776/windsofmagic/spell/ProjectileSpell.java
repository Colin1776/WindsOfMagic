package colin1776.windsofmagic.spell;

import colin1776.windsofmagic.projectile.AbstractMagicProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class ProjectileSpell<T extends AbstractMagicProjectile> extends Spell
{
    private final T projectile;

    public ProjectileSpell(String name, Lore lore, Tier tier, Type type, T projectile, int baseCost, int baseCooldown, int windup, int baseRange)
    {
        super(name, lore, tier, type, baseCooldown, baseCost, windup, baseRange, false);
        this.projectile = projectile;
    }

    @Override
    public boolean cast(LivingEntity caster, ItemStack castingItem, int ticksInCast)
    {
        return false;
    }

    public T getProjectile()
    {
        return projectile;
    }
}

