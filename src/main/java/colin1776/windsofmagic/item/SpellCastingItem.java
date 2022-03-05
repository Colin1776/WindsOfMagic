package colin1776.windsofmagic.item;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import colin1776.windsofmagic.util.KeyboardHelper;
import colin1776.windsofmagic.util.MagicItemData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellCastingItem extends Item
{
    private final Tier tier;
    private final Lore lore;

    public SpellCastingItem(Properties pProperties, Tier tier, Lore lore)
    {
        super(pProperties);
        this.tier = tier;
        this.lore = lore;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
    {
        Spell[] spells = MagicItemData.getSpells(pStack);
        int[] cooldowns = MagicItemData.getCooldowns(pStack);

        if (spells != null && cooldowns != null)
        {
            String s;

            for (int i = 0; i < spells.length; i++)
            {
                s = spells[i] + ": " + cooldowns[i];

                ChatFormatting formatting = ChatFormatting.BLUE;

                if (i == MagicItemData.getCurrentSpellIndex(pStack))
                    formatting = ChatFormatting.GOLD;

                pTooltipComponents.add(new TextComponent(s).withStyle(formatting));
            }
        }


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        if (!pLevel.isClientSide())
        {
            ItemStack castingItem = pPlayer.getItemInHand(pUsedHand);

            if (KeyboardHelper.isHoldingShift())
            {
                MagicItemData.addSpell(castingItem, Spells.IGNITE.get(), 0);
                MagicItemData.addSpell(castingItem, Spells.SECOND.get(), 1);
                MagicItemData.addSpell(castingItem, Spells.FIREBALL.get(), 3);

                MagicItemData.addCooldown(castingItem, 0, 20);
                MagicItemData.addCooldown(castingItem, 1, 20);
                MagicItemData.addCooldown(castingItem, 3, 20);

                System.out.println("Reset spells/cooldowns");
            }
            else
            {
                Spell spell = MagicItemData.getCurrentSpell(castingItem);

                spell.cast(pPlayer, castingItem, 0);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public Tier getTier()
    {
        return tier;
    }

    public Lore getLore()
    {
        return lore;
    }

}
