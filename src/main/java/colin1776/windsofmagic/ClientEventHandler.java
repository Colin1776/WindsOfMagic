package colin1776.windsofmagic;

import colin1776.windsofmagic.item.SpellCastingItem;
import colin1776.windsofmagic.util.KeyboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class ClientEventHandler
{
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onScroll(InputEvent.MouseScrollEvent event)
    {
        if (KeyboardHelper.isHoldingShift())
        {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            // negative = scroll down, positive = scroll up
            double delta = event.getScrollDelta();

            if (player != null)
            {
                ItemStack mainHand = player.getMainHandItem();
                ItemStack offHand = player.getOffhandItem();

                if (mainHand.getItem() instanceof SpellCastingItem)
                {
                    event.setCanceled(true);
                    cycleSpells(player, mainHand, delta);
                }
                else if (offHand.getItem() instanceof SpellCastingItem)
                {
                    event.setCanceled(true);
                    cycleSpells(player, offHand, delta);
                }
            }
        }
    }

    private static void cycleSpells(Player player, ItemStack stack, double delta)
    {
        // TODO send cycle spells packet
    }

}
