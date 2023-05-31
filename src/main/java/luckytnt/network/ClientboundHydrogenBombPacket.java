package luckytnt.network;

import java.util.function.Supplier;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

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
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.displayHydrogenBombParticles(entityId));
		});
		ctx.get().setPacketHandled(true);
	}
}
