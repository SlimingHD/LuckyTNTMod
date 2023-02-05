package luckytnt.network;

import java.util.function.Supplier;

import luckytnt.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

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
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.updateEntityIntNBT(nbt, value));
		});
		ctx.get().setPacketHandled(true);
	}
}
