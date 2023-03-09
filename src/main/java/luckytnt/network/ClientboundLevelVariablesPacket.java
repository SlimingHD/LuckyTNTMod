package luckytnt.network;

import java.util.function.Supplier;

import luckytnt.LevelVariables;
import luckytnt.client.ClientAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundLevelVariablesPacket {

	public final LevelVariables variables;
	
	public ClientboundLevelVariablesPacket(LevelVariables variables) {
		this.variables = variables;
	}
	
	public ClientboundLevelVariablesPacket(FriendlyByteBuf buffer) {
		variables = new LevelVariables();
		variables.read(buffer.readNbt());
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeNbt(variables.save(new CompoundTag()));
	}
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.syncLevelVariables(variables));
		});
		ctx.get().setPacketHandled(true);
	}
}
