package luckytnt.network;

import luckytnt.LevelVariables;
import luckytnt.client.ClientAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

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
	
	public void handle(CustomPayloadEvent.Context ctx) {
		ctx.enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.syncLevelVariables(variables));
		});
		ctx.setPacketHandled(true);
	}
}
