package colin1776.windsofmagic.item;

import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import colin1776.windsofmagic.util.StaffItemHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffItem extends Item implements SpellCastingItem
{
    private final Tier tier;
    private final Lore lore;

    public StaffItem(Properties pProperties, Tier tier, Lore lore)
    {
        super(pProperties);
        this.tier = tier;
        this.lore = lore;
    }

    @Override
    public Tier getTier()
    {
        return tier;
    }

    @Override
    public Lore getLore()
    {
        return lore;
    }

    @Override
    public int maxNumberOfSpells()
    {
        return getTier().getNumber();
    }

    @Override
    public Spell[] getSpells(ItemStack stack)
    {
        return StaffItemHelper.getSpells(stack);
    }

    @Override
    public Spell getCurrentSpell(ItemStack stack)
    {
        return StaffItemHelper.getCurrentSpell(stack);
    }

    @Override
    public void selectSpell(ItemStack stack, int index)
    {
        StaffItemHelper.selectSpell(stack, index);
    }

    @Override
    public void selectNextSpell(ItemStack stack)
    {
        StaffItemHelper.selectNextSpell(stack);
    }

    @Override
    public void selectPreviousSpell(ItemStack stack)
    {
        StaffItemHelper.selectPreviousSpell(stack);
    }

    @Override
    public boolean setSpell(ItemStack stack, Spell spell, int index)
    {
        return StaffItemHelper.setSpell(stack, spell, index);
    }

    @Override
    public int[] getCooldowns(ItemStack stack)
    {
        return StaffItemHelper.getCooldowns(stack);
    }

    @Override
    public int getCurrentCooldown(ItemStack stack)
    {
        return StaffItemHelper.getCurrentCooldown(stack);
    }

    @Override
    public void setCurrentCooldown(ItemStack stack, int cooldown)
    {

    }

    @Override
    public void decrementCooldowns(ItemStack stack)
    {

    }

    @Override
    public boolean canCast(LivingEntity caster, ItemStack stack, Spell spell)
    {
        return false;
    }

    @Override
    public void postCast(LivingEntity caster, ItemStack stack, Spell spell, int castingTick, boolean applyCooldown)
    {

    }
}
