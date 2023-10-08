package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundIntNBTPacket {
	
	public final String name;
	public final int tag;
	public final int entityId;
	
	public ClientboundIntNBTPacket(String name, int tag, int entityId) {
		this.name = name;
		this.tag = tag;
		this.entityId = entityId;
	}
	
	public ClientboundIntNBTPacket(FriendlyByteBuf buffer) {
		name = buffer.readUtf();
		tag = buffer.readInt();
		entityId = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(name);
		buffer.writeInt(tag);
		buffer.writeInt(entityId);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.setEntityIntTag(name, tag, entityId));
		});
		ctx.setPacketHandled(true);
	}
}
