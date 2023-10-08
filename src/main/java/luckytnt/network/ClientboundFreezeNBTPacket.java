package luckytnt.network;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

public class ClientboundFreezeNBTPacket {
	
	public final String nbt;
	public final int value;
	
	public ClientboundFreezeNBTPacket(String nbt,int value) {
		this.nbt = nbt;
		this.value = value;
	}
	
	public ClientboundFreezeNBTPacket(FriendlyByteBuf buffer) {
		nbt = buffer.readUtf();
		value = buffer.readInt();
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeUtf(nbt);
		buffer.writeInt(value);
	}
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.updateEntityIntNBT(nbt, value));
		});
		ctx.setPacketHandled(true);
	}
}
