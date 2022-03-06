package colin1776.windsofmagic.item;

import colin1776.windsofmagic.util.KeyboardHelper;
import colin1776.windsofmagic.util.MagicEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@SuppressWarnings("NullableProblems")
public class DebugItem extends Item
{

    public DebugItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        if (!pLevel.isClientSide())
        {
            if (KeyboardHelper.isHoldingShift())
            {
                MagicEntityData.addRecharge(pPlayer, 1);
            }
            else if (KeyboardHelper.isHoldingCtrl())
            {
                MagicEntityData.subtractRecharge(pPlayer, 1);
            }
            else
            {
                MagicEntityData.subtractCapacity(pPlayer, 100);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
