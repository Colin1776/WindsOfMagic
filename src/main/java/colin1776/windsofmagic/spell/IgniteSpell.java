package colin1776.windsofmagic.spell;

import colin1776.windsofmagic.util.HitResultHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class IgniteSpell extends Spell
{
    public IgniteSpell()
    {
        super("Ignite", Lore.FIRE, Tier.BEGINNER, SpellType.UTILITY, 80, 50, 0, 8, false);
    }

    // TODO set blocks on fire, light campfires and candles, etc if the cast will be successful, look at FlintAndSteelItem, maybe add some fire particles
    @Override
    public boolean cast(LivingEntity caster, ItemStack castingItem, int ticksInCast)
    {
        HitResult result = HitResultHelper.result(caster, getRange());

        if (result instanceof BlockHitResult blockResult)
        {
            BlockPos pos = blockResult.getBlockPos();
            Level level = caster.level;

            if (level.getBlockState(pos).getBlock() != Blocks.AIR)
            {
                System.out.println("Its a block!");
                return true;
            }
        }

        System.out.println("Its a miss!");
        return false;
    }
}
