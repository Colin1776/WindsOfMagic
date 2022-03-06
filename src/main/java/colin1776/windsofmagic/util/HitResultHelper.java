package colin1776.windsofmagic.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;

public class HitResultHelper
{
    public static HitResult result(LivingEntity entity, int range)
    {
        HitResult result;

        result = entity.pick(range, 0.0F, false);

        return result;
    }
}
