package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundHydrogenBombPacket {
	
	public final int entityId;
	
	public ClientboundHydrogenBombPacket(int entityId) {
		this.entityId = entityId;
	}
	
	public ClientboundHydrogenBombPacket(FriendlyByteBuf buffer) {
		entityId = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(entityId);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.displayHydrogenBombParticles(entityId));
		});
		ctx.setPacketHandled(true);
	}
}
