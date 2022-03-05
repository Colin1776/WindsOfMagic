package colin1776.windsofmagic;

import colin1776.windsofmagic.util.MagicEntityData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler
{
    @SubscribeEvent
    public void rechargeWinds(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;

        if (player instanceof ServerPlayer && event.phase == TickEvent.Phase.END)
        {
            if (!MagicEntityData.isRechargeBlocked(player))
            {
                int recharge = MagicEntityData.getRecharge(player);
                MagicEntityData.addWinds(player, recharge);

                int winds = MagicEntityData.getWinds(player);
                int capacity = MagicEntityData.getCapacity(player);

                player.displayClientMessage(new TextComponent("Winds: " + winds + " Capacity: " + capacity + " Recharge: " + recharge), true);
            }
        }
    }
}

