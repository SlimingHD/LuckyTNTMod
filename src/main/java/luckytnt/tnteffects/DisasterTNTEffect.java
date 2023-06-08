package luckytnt.tnteffects;

import luckytnt.LevelVariables;
import luckytnt.config.LuckyTNTConfigValues;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DisasterTNTEffect extends PrimedTNTEffect {
	
	private final String disaster;
	private final boolean rain;
	
	public DisasterTNTEffect(String disaster, boolean rain) {
		this.disaster = disaster;
		this.rain = rain;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		LevelVariables var = LevelVariables.get(ent.getLevel());
		int time = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() * (int)Math.random();
		
		if(disaster.equals("doomsday")) {
			var.doomsdayTime = time;
		} else if(disaster.equals("toxic_clouds")) {
			var.toxicCloudsTime = time;
		} else if(disaster.equals("clear")) {
			var.doomsdayTime = 0;
			var.heatDeathTime = 0;
			var.iceAgeTime = 0;
			var.tntRainTime = 0;
			var.toxicCloudsTime = 0;
			if(ent.getLevel() instanceof ServerLevel sl) {
				sl.setWeatherParameters(1000000, 0, false, false);
			}
		} else if(disaster.equals("ice_age")) {
			var.iceAgeTime = time;
		} else if(disaster.equals("heat_death")) {
			var.heatDeathTime = time;
		} else if(disaster.equals("tnt_rain")) {
			var.tntRainTime = time;
		}
		
		if(ent.getLevel() instanceof ServerLevel sl) {
			var.sync(sl);
			if(rain) {
				sl.setWeatherParameters(0, time, true, true);
			}
		}
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 0;
	}
}
