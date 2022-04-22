package colin1776.windsofmagic.item;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import colin1776.windsofmagic.util.KeyboardHelper;
import colin1776.windsofmagic.util.MagicEntityData;
import colin1776.windsofmagic.util.StaffItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StaffItem extends Item implements SpellCastingItem
{
    private static final int USE_DURATION = 72000;

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
        StaffItemHelper.addCooldown(stack, cooldown);
    }

    @Override
    public void decrementCooldowns(ItemStack stack)
    {
        StaffItemHelper.decrementCooldowns(stack);
    }

    @Override
    public boolean canCast(LivingEntity caster, ItemStack stack, Spell spell)
    {
        if (StaffItemHelper.getCurrentCooldown(stack) > 0) return false;

        if (StaffItemHelper.getFinalCost() > MagicEntityData.getWinds(caster)) return false;

        return true;
    }

    @Override
    public void postCast(LivingEntity caster, ItemStack stack, Spell spell, int castingTick, boolean applyCooldown)
    {

    }

    // TODO handle item interaction methods for spell casts of either continuous or non continuous variety, or of windup or non windup variety

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        if (!pLevel.isClientSide())
        {
            ItemStack castingItem = pPlayer.getItemInHand(pUsedHand);

            if (canCast(pPlayer, castingItem, getCurrentSpell(castingItem)))
            {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.success(castingItem);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration)
    {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged)
    {
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public int getUseDuration(ItemStack pStack)
    {
        return USE_DURATION;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        if (KeyboardHelper.isHoldingCtrl())
        {
            setSpell(pStack, Spells.ALPHA.get(), 0);
            setSpell(pStack, Spells.DELTA.get(), 1);
            setSpell(pStack, Spells.SECOND.get(), 2);
            setSpell(pStack, Spells.IGNITE.get(), 3);
            setSpell(pStack, Spells.FIREBALL.get(), 4);

            setCurrentCooldown(pStack, 0);
        }

        decrementCooldowns(pStack);

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
    {
        Spell[] spells = getSpells(pStack);

        pTooltipComponents.add(new TextComponent(""));
        pTooltipComponents.add(new TextComponent("Current spell: " + getCurrentSpell(pStack).toString()));

        for (Spell spell : spells)
        {
            String name = spell.toString();

            if (getCurrentSpell(pStack) == spell)
                name = "-> " + name;

            pTooltipComponents.add(new TextComponent(name).withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
