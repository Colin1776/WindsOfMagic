package colin1776.windsofmagic.util;

import colin1776.windsofmagic.spell.Lore;
import colin1776.windsofmagic.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class MagicEntityData
{
    private static final String WINDS = "winds";
    private static final String CAPACITY = "capacity";
    private static final String RECHARGE = "recharge";
    private static final String COST = "cost";

    // TODO add cooldown reduction system

    public static int getWinds(LivingEntity entity)
    {
        CompoundTag tag = entity.getPersistentData();

        if (tag.contains(WINDS))
            return tag.getInt(WINDS);

        return 0;
    }

    public static void setWinds(LivingEntity entity, int winds)
    {
        if (winds > getCapacity(entity))
            winds = getCapacity(entity);
        else if (winds < 0)
            winds = 0;

        CompoundTag tag = entity.getPersistentData();
        tag.putInt(WINDS, winds);
    }

    public static void addWinds(LivingEntity entity, int winds)
    {
        int currentWinds = getWinds(entity);
        setWinds(entity, currentWinds + winds);
    }

    public static void subtractWinds(LivingEntity entity, int winds)
    {
        int currentWinds = getWinds(entity);
        setWinds(entity, currentWinds - winds);
    }

    public static int getCapacity(LivingEntity entity)
    {
        CompoundTag tag = entity.getPersistentData();

        if (tag.contains(CAPACITY))
            return tag.getInt(CAPACITY);

        return Constants.DEFAULT_WINDS_CAPACITY;
    }

    public static void setCapacity(LivingEntity entity, int capacity)
    {
        if (capacity > Constants.MAX_WINDS_CAPACITY)
            capacity = Constants.MAX_WINDS_CAPACITY;
        else if (capacity < 0)
            capacity = 0;

        if (getWinds(entity) > capacity)
            setWinds(entity, capacity);

        CompoundTag tag = entity.getPersistentData();
        tag.putInt(CAPACITY, capacity);
    }

    public static void addCapacity(LivingEntity entity, int capacity)
    {
        int currentCapacity = getCapacity(entity);
        setCapacity(entity, currentCapacity + capacity);
    }

    public static void subtractCapacity(LivingEntity entity, int capacity)
    {
        int currentCapacity = getCapacity(entity);
        setCapacity(entity, currentCapacity - capacity);
    }

    public static int getRecharge(LivingEntity entity)
    {
        CompoundTag tag = entity.getPersistentData();

        if (tag.contains(RECHARGE))
            return tag.getInt(RECHARGE);

        return Constants.DEFAULT_WINDS_PER_TICK;
    }

    public static void setRecharge(LivingEntity entity, int recharge)
    {
        if (recharge > Constants.MAX_WINDS_PER_TICK)
            recharge = Constants.MAX_WINDS_PER_TICK;
        else if (recharge < Constants.MIN_WINDS_PER_TICK)
            recharge = Constants.MIN_WINDS_PER_TICK;

        CompoundTag tag = entity.getPersistentData();
        tag.putInt(RECHARGE, recharge);
    }

    public static void addRecharge(LivingEntity entity, int recharge)
    {
        int currentRecharge = getRecharge(entity);
        setRecharge(entity, currentRecharge + recharge);
    }

    public static void subtractRecharge(LivingEntity entity, int recharge)
    {
        int currentRecharge = getRecharge(entity);
        setRecharge(entity, currentRecharge - recharge);
    }

    public static boolean isRechargeBlocked(LivingEntity entity)
    {
        return false;
    }

    // if positive, cost is reduced, if negative, cost is increased
    public static int getCostReduction(LivingEntity entity)
    {
        CompoundTag tag = entity.getPersistentData();

        if (tag.contains(COST))
            return tag.getInt(COST);

        return 0;
    }

    public static void setCostReduction(LivingEntity entity, int cost)
    {
        if (cost > Constants.MAX_COST_REDUCTION)
            cost = Constants.MAX_COST_REDUCTION;
        else if (cost < Constants.MAX_COST_INCREASE)
            cost = Constants.MAX_COST_INCREASE;

        CompoundTag tag = entity.getPersistentData();
        tag.putInt(COST, cost);
    }

    public static void reduceCost(LivingEntity entity, int amount)
    {
        int cost = getCostReduction(entity);
        setCostReduction(entity, cost + amount);
    }

    public static void increaseCost(LivingEntity entity, int amount)
    {
        int cost = getCostReduction(entity);
        setCostReduction(entity, cost - amount);
    }

    public static int getLoreCostReduction(LivingEntity entity, Lore lore)
    {
        CompoundTag tag = entity.getPersistentData();
        String key = COST + lore.toString();

        if (tag.contains(key))
            return tag.getInt(key);

        return 0;
    }

    public static void setLoreCostReduction(LivingEntity entity, Lore lore, int cost)
    {
        if (cost > Constants.MAX_COST_REDUCTION)
            cost = Constants.MAX_COST_REDUCTION;
        else if (cost < Constants.MAX_COST_INCREASE)
            cost = Constants.MAX_COST_INCREASE;

        CompoundTag tag = entity.getPersistentData();
        String key = COST + lore.toString();
        tag.putInt(key, cost);
    }

    public static void reduceLoreCost(LivingEntity entity, Lore lore, int amount)
    {
        int cost = getLoreCostReduction(entity, lore);
        setLoreCostReduction(entity, lore, cost + amount);
    }

    public static void increaseLoreCost(LivingEntity entity, Lore lore, int amount)
    {
        int cost = getLoreCostReduction(entity, lore);
        setLoreCostReduction(entity, lore, cost - amount);
    }

    public static int getFinalCost(LivingEntity entity, Spell spell)
    {
        Lore lore = spell.getLore();

        int baseCost = spell.getBaseCost();

        int costReduction = getCostReduction(entity);
        int loreCostReduction = getLoreCostReduction(entity, lore);

        int reducedCost = baseCost - (costReduction + loreCostReduction);

        return Math.max(reducedCost, 1);
    }
}
