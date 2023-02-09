package luckytnt.network;

import java.util.Optional;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

	public static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("luckytntmod", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	private PacketHandler() {	
	}
	
	public static void register() {
		int index = 0;
		CHANNEL.registerMessage(index++, ClientboundStringNBTPacket.class, ClientboundStringNBTPacket::encode, ClientboundStringNBTPacket::new, ClientboundStringNBTPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(index++, ClientboundIntNBTPacket.class, ClientboundIntNBTPacket::encode, ClientboundIntNBTPacket::new, ClientboundIntNBTPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(index++, ClientboundLevelVariablesPacket.class, ClientboundLevelVariablesPacket::encode, ClientboundLevelVariablesPacket::new, ClientboundLevelVariablesPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(index++, ClientboundToxicCloudPacket.class, ClientboundToxicCloudPacket::encode, ClientboundToxicCloudPacket::new, ClientboundToxicCloudPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(index++, ClientboundFreezeNBTPacket.class, ClientboundFreezeNBTPacket::encode, ClientboundFreezeNBTPacket::new, ClientboundFreezeNBTPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(index++, ClientboundBooleanNBTPacket.class, ClientboundBooleanNBTPacket::encode, ClientboundBooleanNBTPacket::new, ClientboundBooleanNBTPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
}
