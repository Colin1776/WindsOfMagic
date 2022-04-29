package colin1776.windsofmagic.item;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import colin1776.windsofmagic.spell.Tier;
import colin1776.windsofmagic.util.KeyboardHelper;
import colin1776.windsofmagic.util.MagicEntityData;
import colin1776.windsofmagic.util.MagicItemData;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class OldStaffItem extends Item
{
    private final Tier tier;
    private final Lore lore;

    // TODO this is being scrapped for StaffItem

    public OldStaffItem(Properties pProperties, Tier tier, Lore lore)
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
        ItemStack castingItem = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide())
        {
            if (KeyboardHelper.isHoldingShift())
            {
                MagicItemData.addSpell(castingItem, Spells.IGNITE.get(), 0);
                MagicItemData.addSpell(castingItem, Spells.SECOND.get(), 1);
                MagicItemData.addSpell(castingItem, Spells.FIREBALL.get(), 3);

                MagicItemData.addCooldown(castingItem, 0, 0);

                System.out.println("Reset spells/cooldowns");
            }
            else
            {
                Spell spell = MagicItemData.getCurrentSpell(castingItem);

                if (spell != null)
                {
                    if (canCast(pPlayer, castingItem, spell))
                    {
                        boolean isContinuous = spell.isContinuous();
                        boolean hasWindup = spell.getWindup() > 0;

                        if (!hasWindup && !isContinuous)
                        {
                            if (spell.cast(pPlayer, castingItem, 0))
                            {
                                handleCooldowns(pPlayer, castingItem, spell);
                                handleCost(pPlayer, spell);
                                return InteractionResultHolder.success(castingItem);
                            }
                        }
                        else
                        {
                            pPlayer.startUsingItem(pUsedHand);
                            return InteractionResultHolder.consume(castingItem);
                        }
                    }
                }
            }
        }

        return InteractionResultHolder.fail(castingItem);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration)
    {
        Spell spell = MagicItemData.getCurrentSpell(pStack);

        if (spell != null)
        {
            int windup = spell.getWindup();
            boolean isContinuous = spell.isContinuous();

            int tick = 72000 - pRemainingUseDuration;

            if (isContinuous)
            {
                if (tick >= windup)
                {
                    int castingTick = tick - windup;

                    if (castingTick == 0 || canCast(pLivingEntity, pStack, spell))
                    {
                        if (spell.cast(pLivingEntity, pStack, castingTick))
                            handleCost(pLivingEntity, spell);
                    }
                }
            }
            else
            {
                if (tick == windup)
                {
                    if (spell.cast(pLivingEntity, pStack, 0))
                        handleCost(pLivingEntity, spell);
                }
            }

           /* if (hasWindup)
            {
                int windup = spell.getWindup();

                if (tick >= windup)
                {
                    if (canCast(pLivingEntity, pStack, spell))
                    {
                        if (spell.cast(pLivingEntity, pStack, tick))
                        {
                            handleCost(pLivingEntity, spell);
                        }
                    }

                    if (!isContinuous)
                    {
                        pLivingEntity.stopUsingItem();
                    }
                }

            }
            else
            {
                if (canCast(pLivingEntity, pStack, spell))
                    if (spell.cast(pLivingEntity, pStack, tick));
                        handleCost(pLivingEntity, spell);
            }*/
        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged)
    {
        System.out.println("releaseUsing");

        Spell spell = MagicItemData.getCurrentSpell(pStack);

        if (spell != null)
        {
            handleCooldowns(pLivingEntity, pStack, spell);
        }

        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public int getUseDuration(ItemStack pStack)
    {
        return 72000;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
    {
        MagicItemData.decrementCooldowns(pStack);

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean canCast(LivingEntity caster, ItemStack stack, Spell spell)
    {
        if (MagicItemData.getCurrentCooldown(stack) > 0) return false;

        if (MagicEntityData.getFinalCost(caster, spell) > MagicEntityData.getWinds(caster)) return false;

        return true;
    }

    private void doPostCast(LivingEntity caster, ItemStack stack, Spell spell, int castingTick)
    {
        if (spell.cast(caster, stack, castingTick))
        {
            int cost = MagicEntityData.getFinalCost(caster, spell);
            MagicEntityData.subtractWinds(caster, cost);

            int cooldown = MagicEntityData.getFinalCooldown(caster, spell);
            MagicItemData.addCooldown(stack, MagicItemData.getCurrentSpellIndex(stack), cooldown);
        }
    }

    private void handleCooldowns(LivingEntity caster, ItemStack stack, Spell spell)
    {
        int cooldown = MagicEntityData.getFinalCooldown(caster, spell);
        MagicItemData.addCooldown(stack, MagicItemData.getCurrentSpellIndex(stack), cooldown);
    }

    private void handleCost(LivingEntity caster, Spell spell)
    {
        int cost = MagicEntityData.getFinalCost(caster, spell);
        MagicEntityData.subtractWinds(caster, cost);
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
