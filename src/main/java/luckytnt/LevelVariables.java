package luckytnt;

import luckytnt.network.ClientboundLevelVariablesPacket;
import luckytnt.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;

public class LevelVariables extends SavedData{

	public int doomsdayTime = 0;
	public int toxicCloudsTime = 0;
	public int iceAgeTime = 0;
	public int heatDeathTime = 0;
	public int tntRainTime = 0;
	
	public static LevelVariables clientSide = new LevelVariables();
	
	public CompoundTag save(CompoundTag tag) {
		tag.putInt("doomsdayTime", doomsdayTime);
		tag.putInt("toxicCloudsTime", toxicCloudsTime);
		tag.putInt("iceAgeTime", iceAgeTime);
		tag.putInt("heatDeathTime", heatDeathTime);
		tag.putInt("tntRainTime", tntRainTime);
		return tag;
	}
	
	public static LevelVariables load(CompoundTag tag) {
		LevelVariables variables = new LevelVariables();
		variables.read(tag);
		return variables;
	}
	
	public void read(CompoundTag tag) {
		doomsdayTime = tag.getInt("doomsdayTime");
		toxicCloudsTime = tag.getInt("toxicCloudsTime");
		iceAgeTime = tag.getInt("iceAgeTime");
		heatDeathTime = tag.getInt("heatDeathTime");
		tntRainTime = tag.getInt("tntRainTime");
	}
	
	public static LevelVariables get(LevelAccessor level) {
		if(level instanceof ServerLevelAccessor sLevel)
			return sLevel.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(new SavedData.Factory<LevelVariables>(LevelVariables::new, f -> LevelVariables.load(f), DataFixTypes.LEVEL), "ltm_level_variables");
		else
			return clientSide;
	}
	
	public void sync(ServerLevel level) {
		setDirty();
		PacketHandler.CHANNEL.send(new ClientboundLevelVariablesPacket(this), PacketDistributor.DIMENSION.with(level.dimension()));
	}
}
