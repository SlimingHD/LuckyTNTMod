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
	public void serverExplosion(IExplosiveEntity entity) {
		LevelVariables variables = LevelVariables.get(entity.getLevel());
		int time = 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() + 1000 * LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get() * (int)Math.random();
		
		if (disaster.equals("doomsday")) {
			variables.doomsdayTime = time;
		} else if (disaster.equals("toxic_clouds")) {
			variables.toxicCloudsTime = time;
		} else if (disaster.equals("clear")) {
			variables.doomsdayTime = 0;
			variables.heatDeathTime = 0;
			variables.iceAgeTime = 0;
			variables.tntRainTime = 0;
			variables.toxicCloudsTime = 0;
			if (entity.getLevel() instanceof ServerLevel sl) {
				sl.setWeatherParameters(1000000, 0, false, false);
			}
		} else if (disaster.equals("ice_age")) {
			variables.iceAgeTime = time;
		} else if (disaster.equals("heat_death")) {
			variables.heatDeathTime = time;
		} else if (disaster.equals("tnt_rain")) {
			variables.tntRainTime = time;
		}
		
		if (entity.getLevel() instanceof ServerLevel sl) {
			variables.sync(sl);
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
