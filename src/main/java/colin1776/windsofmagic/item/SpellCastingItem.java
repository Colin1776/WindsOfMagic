package colin1776.windsofmagic.item;

import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface SpellCastingItem
{
    // TODO finish javadocs and add any more necessary methods

    /**
     * The {@link colin1776.windsofmagic.spell.Tier} determines whether a
     * {@link colin1776.windsofmagic.spell.Spell} can be bound to the item
     * as well as the max number of spells that can be bound.
     * @return the {@link colin1776.windsofmagic.spell.Tier} of the item.
     */
    Tier getTier();

    Lore getLore();

    /**
     * This is usually determined by the {@link colin1776.windsofmagic.spell.Tier}
     * of the item.
     * @return the number of spells that can be bound to the item.
     */
    int maxNumberOfSpells();

    Spell[] getSpells(ItemStack stack);

    Spell getCurrentSpell(ItemStack stack);

    void selectSpell(ItemStack stack, int index);

    void selectNextSpell(ItemStack stack);

    void selectPreviousSpell(ItemStack stack);

    /**
     * Binds a {@link colin1776.windsofmagic.spell.Spell} to the item.
     * @param stack the item the spell is binding to.
     * @param spell the spell that is being bound.
     * @param index the slot being bound to.
     * @return true if the spell was bound to the given slot.
     */
    boolean setSpell(ItemStack stack, Spell spell, int index);

    int[] getCooldowns(ItemStack stack);

    int getCurrentCooldown(ItemStack stack);

    void setCurrentCooldown(ItemStack stack, int cooldown);

    void decrementCooldowns(ItemStack stack);

    boolean canCast(LivingEntity caster, ItemStack stack, Spell spell);

    void postCast(LivingEntity caster, ItemStack stack, Spell spell, int castingTick, boolean applyCooldown);
}
