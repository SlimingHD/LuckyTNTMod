package luckytnt.network;

import java.util.function.Supplier;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

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
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.setToxicCloudData(size, entityId));
		});
		ctx.get().setPacketHandled(true);
	}
}
