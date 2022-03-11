package colin1776.windsofmagic.network;

import colin1776.windsofmagic.WindsOfMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler
{
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(WindsOfMagic.MOD_ID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    private static int nextPacketID = 0;

    public static void init()
    {

    }

    public static int getNextPacketID()
    {
        return nextPacketID++;
    }

    public static void sendPacketToServer(Object packet)
    {
        INSTANCE.sendToServer(packet);
    }
}
