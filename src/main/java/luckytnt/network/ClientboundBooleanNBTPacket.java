package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundBooleanNBTPacket {
	
	public final String name;
	public final boolean tag;
	public final int entityId;
	
	public ClientboundBooleanNBTPacket(String name, boolean tag, int entityId) {
		this.name = name;
		this.tag = tag;
		this.entityId = entityId;
	}
	
	public ClientboundBooleanNBTPacket(FriendlyByteBuf buffer) {
		name = buffer.readUtf();
		tag = buffer.readBoolean();
		entityId = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(name);
		buffer.writeBoolean(tag);
		buffer.writeInt(entityId);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.setEntityBooleanTag(name, tag, entityId));
		});
		ctx.setPacketHandled(true);
	}
}
