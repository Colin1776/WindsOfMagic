package colin1776.windsofmagic.util;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.item.SpellCastingItem;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class StaffItemHelper
{
    // TODO finish javadocs
    // TODO add cooldown and cost reduction bound to staff item
    // TODO add method to calc final cost and cooldown reduction between these reductions and the ones bound to the entity

    /** Keys for the NBT data */
    private static final String SPELL_SLOT = "spellslot";
    private static final String CURRENT = "current";
    private static final String COOLDOWNS = "cooldowns";
    private static final String COST_REDUC = "costreduction";
    private static final String COOLDOWN_REDUC = "cooldownreduction";

    /**
     *
     * @param stack an ItemStack of type {@link colin1776.windsofmagic.item.SpellCastingItem}
     * @return an array of {@link colin1776.windsofmagic.spell.Spell}(s) bound to the stack
     */
    public static Spell[] getSpells(ItemStack stack)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            int numberOfSpells = item.maxNumberOfSpells();
            Spell[] spells = new Spell[numberOfSpells];

            CompoundTag tag = stack.getOrCreateTag();
            for (int i = 0; i < numberOfSpells; i++)
            {
                String key = SPELL_SLOT + i;

                if (tag.contains(key))
                {
                    String spellKey = tag.getString(key);
                    spells[i] = Spells.getSpellFromKey(spellKey);
                }
                else
                    spells[i] = Spells.empty();
            }

            return spells;
        }

        return new Spell[0];
    }

    /**
     * This method attempts to "bind" a spell to a casting item. The spell will only
     * be bound if its tier is not higher than the tier of the casting item, the lore
     * of the spell matches the lore of the casting item, and the spell is not already
     * bounded to the casting item.
     * @param stack an ItemStack of type {@link colin1776.windsofmagic.item.SpellCastingItem}
     * @param spell a {@link colin1776.windsofmagic.spell.Spell} to be bounded to the stack
     * @param index the slot the spell is to be bounded to
     * @return true if the spell was successfully bounded
     */
    public static boolean setSpell(ItemStack stack, Spell spell, int index)
    {
        Spell[] spells = getSpells(stack);

        if (stack.getItem() instanceof SpellCastingItem item)
        {
            Lore itemLore = item.getLore();
            Tier itemTier = item.getTier();

            Lore spellLore = spell.getLore();
            Tier spellTier = spell.getTier();

            if (itemLore != Lore.NONE && itemLore != spellLore)
                return false;

            if (spellTier.getNumber() > itemTier.getNumber())
                return false;
        }

        if (index >= 0 && index < spells.length)
        {
            if (!containsSpell(stack, spell))
            {
                String key = SPELL_SLOT + index;
                String spellKey = Spells.getKeyFromSpell(spell);

                CompoundTag tag = stack.getOrCreateTag();
                tag.putString(key, spellKey);

                return true;
            }
        }

        return false;
    }

    public static boolean containsSpell(ItemStack stack, Spell spell)
    {
        Spell[] spells = getSpells(stack);

        for (Spell x : spells)
        {
            if (spell == x)
                return true;
        }

        return false;
    }

    public static Spell getCurrentSpell(ItemStack stack)
    {
        Spell[] spells = getSpells(stack);
        int index = getCurrentIndex(stack);

        if (spells.length > index)
            return spells[index];

        return Spells.empty();
    }

    public static void selectSpell(ItemStack stack, int index)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            int numberOfSpells = item.maxNumberOfSpells();

            if (index >= 0 && index < numberOfSpells)
            {
                CompoundTag tag = stack.getOrCreateTag();

                tag.putInt(CURRENT, index);
            }
        }
    }

    public static void selectNextSpell(ItemStack stack)
    {
        int index = getNextIndex(stack);

        selectSpell(stack, index);
    }

    public static void selectPreviousSpell(ItemStack stack)
    {
        int index = getPreviousIndex(stack);

        selectSpell(stack, index);
    }

    public static int[] getCooldowns(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains(COOLDOWNS))
        {
            return tag.getIntArray(COOLDOWNS);
        }

        return new int[0];
    }

    public static void setCooldowns(ItemStack stack, int[] cooldowns)
    {
        int size = getSpells(stack).length;
        int[] newCooldowns = new int[size];

        int n = Math.min(cooldowns.length, newCooldowns.length);

        System.arraycopy(cooldowns, 0, newCooldowns, 0, n);

        CompoundTag tag = stack.getOrCreateTag();
        tag.putIntArray(COOLDOWNS, newCooldowns);
    }

    public static void addCooldown(ItemStack stack, int cooldown)
    {
        int[] cooldowns = getCooldowns(stack);
        int index = getCurrentIndex(stack);

        if (index < cooldowns.length)
        {
            cooldowns[index] = cooldown;
            setCooldowns(stack, cooldowns);
        }
    }

    public static void decrementCooldowns(ItemStack stack)
    {
        int[] cooldowns = getCooldowns(stack);

        for (int i = 0; i < cooldowns.length; i++)
        {
            int x = cooldowns[i];
            x--;
            cooldowns[i] = Math.min(0, x);
        }

        setCooldowns(stack, cooldowns);
    }

    public static int getCurrentCooldown(ItemStack stack)
    {
        int[] cooldowns = getCooldowns(stack);

        if (getCurrentIndex(stack) < cooldowns.length)
            return cooldowns[getCurrentIndex(stack)];

        return 0;
    }

    public static int getCurrentIndex(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains(CURRENT))
            return tag.getInt(CURRENT);

        return 0;
    }

    public static int getNextIndex(ItemStack stack)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            int index = getCurrentIndex(stack);
            int numberOfSpells = item.maxNumberOfSpells();

            if (index == numberOfSpells - 1)
                index = 0;
            else
                index++;

            return index;
        }

        return 0;
    }

    public static int getPreviousIndex(ItemStack stack)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            int index = getCurrentIndex(stack);
            int numberOfSpells = item.maxNumberOfSpells();

            if (index == 0)
                index = numberOfSpells - 1;
            else
                index--;

            return index;
        }

        return 0;
    }

    // TODO complete this
    public static int getFinalCost()
    {
        return 0;
    }

    // TODO complete this
    public static int getFinalCooldown()
    {
        return 0;
    }
}
