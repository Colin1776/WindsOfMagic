package colin1776.windsofmagic.item;

import colin1776.windsofmagic.init.Spells;
import colin1776.windsofmagic.spell.Spell;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellCastingItem extends Item
{
    public SpellCastingItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        Spell spell = getCurrentSpell();
        spell.cast(pPlayer, pPlayer.getItemInHand(pUsedHand));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public Spell getCurrentSpell()
    {
        return null;
    }
}
