package colin1776.windsofmagic.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Spell extends ForgeRegistryEntry<Spell>
{
    private final Lore lore;
    private final SpellType type;
    private final int baseCooldown;
    private final int baseWindup;
    private final int baseRange;
    private final boolean isContinuous;


    /* -------------------------------- CONSTRUCTORS --------------------------------*/
    public Spell(Lore lore, SpellType type, int baseCooldown, int baseWindup, int baseRange, boolean isContinuous)
    {
        this.lore = lore;
        this.type = type;
        this.baseCooldown = baseCooldown;
        this.baseWindup = baseWindup;
        this.baseRange = baseRange;
        this.isContinuous = isContinuous;
    }

    /* -------------------------------- CASTING METHODS --------------------------------*/
    public abstract boolean cast(LivingEntity caster, ItemStack castingItem);


    /* -------------------------------- GETTER METHODS --------------------------------*/
    public Lore getLore()
    {
        return lore;
    }

    public SpellType getType()
    {
        return type;
    }

    public int getBaseCooldown()
    {
        return baseCooldown;
    }

    public int getBaseWindup()
    {
        return baseWindup;
    }

    public int getBaseRange()
    {
        return baseRange;
    }

    public boolean isContinuous()
    {
        return isContinuous;
    }
}
