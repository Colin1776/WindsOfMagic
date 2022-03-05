package colin1776.windsofmagic.util;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.item.SpellCastingItem;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class MagicItemData
{
    private static final String SPELL_SLOT = "spellslot";
    private static final String CURRENT_SPELL = "currentspell";
    private static final String COOLDOWNS = "cooldowns";

    // SPELLS
    public static Spell[] getSpells(ItemStack stack)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            Tier tier = item.getTier();
            int numberOfSpells = tier.getNumberOfSpells();

            CompoundTag tag = stack.getOrCreateTag();
            Spell[] spells = new Spell[numberOfSpells];

            for (int i = 0; i < numberOfSpells; i++)
            {
                String key = SPELL_SLOT + i;

                if (tag.contains(key))
                {
                    String spellKey = tag.getString(key);
                    spells[i] = Spells.getSpellFromKey(spellKey);
                }
            }

            return spells;
        }

        return null;
    }

    public static void addSpell(ItemStack stack, Spell spell, int index)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            Tier tier = item.getTier();
            int numberOfSpells = tier.getNumberOfSpells();

            if (index >= 0 && index < numberOfSpells)
            {
                CompoundTag tag = stack.getOrCreateTag();

                String key = SPELL_SLOT + index;
                String spellKey = Spells.getKeyFromSpell(spell);

                tag.putString(key, spellKey);
            }
        }
    }

    public static void removeSpell(ItemStack stack, Spell spell, int index)
    {

    }

    public static Spell getCurrentSpell(ItemStack stack)
    {
        Spell[] spells = getSpells(stack);

        if (spells != null)
        {
            int index = getCurrentSpellIndex(stack);

            if (spells[index] != null)
            {
                return spells[index];
            }
        }

        return null;
    }

    public static void selectSpell(ItemStack stack, int index)
    {
        Spell[] spells = getSpells(stack);

        if (spells != null)
        {
            if (index >= 0 && index < spells.length)
            {
                if (spells[index] != null)
                {
                    CompoundTag tag = stack.getOrCreateTag();

                    tag.putInt(CURRENT_SPELL, index);
                }
            }
        }
    }

    public static void selectNextSpell(ItemStack stack)
    {
        int nextIndex = getNextSpellIndex(stack);

        selectSpell(stack, nextIndex);
    }

    public static void selectPreviousSpell(ItemStack stack)
    {
        int previousIndex = getPreviousSpellIndex(stack);

        selectSpell(stack, previousIndex);
    }

    public static int getCurrentSpellIndex(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains(CURRENT_SPELL))
        {
            return tag.getInt(CURRENT_SPELL);
        }

        return 0;
    }

    public static int getNextSpellIndex(ItemStack stack)
    {
        int current = getCurrentSpellIndex(stack);

        Spell[] spells = getSpells(stack);

        if (spells != null)
        {
            Spell spell = null;

            while (spell == null)
            {
                if (current == spells.length - 1)
                    current = 0;
                else
                    current++;

                spell = spells[current];
            }
            return current;
        }

        return current;
    }

    public static int getPreviousSpellIndex(ItemStack stack)
    {
        int current = getCurrentSpellIndex(stack);

        Spell[] spells = getSpells(stack);

        if (spells != null)
        {
            Spell spell = null;

            while (spell == null)
            {
                if (current == 0)
                    current = spells.length - 1;
                else
                    current--;

                spell = spells[current];
            }
            return current;
        }

        return current;
    }

    // COOLDOWNS
    public static int[] getCooldowns(ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();

        if (tag.contains(COOLDOWNS))
        {
            return tag.getIntArray(COOLDOWNS);
        }

        return null;
    }

    public static void addCooldown(ItemStack stack, int index, int cooldownAmount)
    {
        if (stack.getItem() instanceof SpellCastingItem item)
        {
            Tier tier = item.getTier();
            int numberOfCooldowns = tier.getNumberOfSpells();

            if (index < 0 || index > numberOfCooldowns - 1) return;

            CompoundTag tag = stack.getOrCreateTag();
            int[] currentCooldowns = getCooldowns(stack);

            if (currentCooldowns != null)
            {
                if (currentCooldowns.length == numberOfCooldowns)
                {
                    currentCooldowns[index] = cooldownAmount;
                    tag.putIntArray(COOLDOWNS, currentCooldowns);
                }
                else if(currentCooldowns.length > numberOfCooldowns)
                {
                    int[] newCooldowns = new int[numberOfCooldowns];

                    for (int i = 0; i < numberOfCooldowns; i++)
                    {
                        newCooldowns[i] = currentCooldowns[i];
                    }

                    tag.putIntArray(COOLDOWNS, newCooldowns);
                }
                else
                {
                    int[] newCooldowns = new int[numberOfCooldowns];

                    for (int i = 0; i < currentCooldowns.length; i++)
                    {
                        newCooldowns[i] = currentCooldowns[i];
                    }

                    tag.putIntArray(COOLDOWNS, newCooldowns);
                }
            }
            else
            {
                tag.putIntArray(COOLDOWNS, new int[numberOfCooldowns]);
            }
        }
    }

    public static void decrementCooldowns(ItemStack stack)
    {
        int[] cooldowns = getCooldowns(stack);

        if (cooldowns != null)
        {
            for (int i = 0; i < cooldowns.length; i++)
            {
                int cooldown = cooldowns[i];

                if (cooldown > 0)
                    cooldown--;

                cooldowns[i] = cooldown;
            }

            CompoundTag tag = stack.getOrCreateTag();
            tag.putIntArray(COOLDOWNS, cooldowns);
        }
    }

    public static int getCurrentCooldown(ItemStack stack)
    {
        int[] cooldowns = getCooldowns(stack);

        if (cooldowns != null)
        {
            return cooldowns[getCurrentSpellIndex(stack)];
        }

        return 0;
    }
}
