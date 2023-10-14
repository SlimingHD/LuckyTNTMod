package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundStringNBTPacket {
	
	public final String name;
	public final String tag;
	public final int entityId;
	
	public ClientboundStringNBTPacket(String name, String tag, int entityId) {
		this.name = name;
		this.tag = tag;
		this.entityId = entityId;
	}
	
	public ClientboundStringNBTPacket(FriendlyByteBuf buffer) {
		name = buffer.readUtf();
		tag = buffer.readUtf();
		entityId = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(name);
		buffer.writeUtf(tag);
		buffer.writeInt(entityId);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.setEntityStringTag(name, tag, entityId));
		});
		ctx.setPacketHandled(true);
	}
}
