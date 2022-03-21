package colin1776.windsofmagic.util;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.item.SpellCastingItem;
import colin1776.windsofmagic.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.system.CallbackI;

public class StaffItemHelper
{
    private static final String SPELL_SLOT = "spellslot";
    private static final String CURRENT = "current";
    private static final String COOLDOWNS = "cooldowns";

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

    public static boolean setSpell(ItemStack stack, Spell spell, int index)
    {
        Spell[] spells = getSpells(stack);

        if (index >= 0 && index < spells.length)
        {
            if (spells[index] != spell)
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
}
