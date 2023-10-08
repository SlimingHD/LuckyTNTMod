package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundToxicCloudPacket {
	
	public final double size;
	public final int entityId;
	
	public ClientboundToxicCloudPacket(double size, int entityId) {
		this.size = size;
		this.entityId = entityId;
	}
	
	public ClientboundToxicCloudPacket(FriendlyByteBuf buffer) {
		size = buffer.readDouble();
		entityId = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeDouble(size);
		buffer.writeInt(entityId);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.setToxicCloudData(size, entityId));
		});
		ctx.setPacketHandled(true);
	}
}
