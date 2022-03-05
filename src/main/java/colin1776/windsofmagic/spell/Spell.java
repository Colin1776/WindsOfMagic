package colin1776.windsofmagic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Spell extends ForgeRegistryEntry<Spell>
{
    private final String name;
    private final Lore lore;
    private final Tier tier;
    private final SpellType type;
    private final int baseCost;
    private final int baseCooldown;
    private final int windup;
    private final int range;
    private final boolean isContinuous;


    /* -------------------------------- CONSTRUCTORS --------------------------------*/
    public Spell(String name, Lore lore, Tier tier, SpellType type, int baseCost, int baseCooldown, int windup, int range, boolean isContinuous)
    {
        this.name = name;
        this.lore = lore;
        this.tier = tier;
        this.type = type;
        this.baseCost = baseCost;
        this.baseCooldown = baseCooldown;
        this.windup = windup;
        this.range = range;
        this.isContinuous = isContinuous;
    }

    /* -------------------------------- CASTING METHODS --------------------------------*/
    public abstract boolean cast(LivingEntity caster, ItemStack castingItem, int ticksInCast);


    /* -------------------------------- GETTER METHODS --------------------------------*/

    @Override
    public String toString()
    {
        return name;
    }

    public Lore getLore()
    {
        return lore;
    }

    public Tier getTier()
    {
        return tier;
    }

    public SpellType getType()
    {
        return type;
    }

    public int getBaseCost()
    {
        return baseCost;
    }

    public int getBaseCooldown()
    {
        return baseCooldown;
    }

    public int getWindup()
    {
        return windup;
    }

    public int getRange()
    {
        return range;
    }

    public boolean isContinuous()
    {
        return isContinuous;
    }
}
