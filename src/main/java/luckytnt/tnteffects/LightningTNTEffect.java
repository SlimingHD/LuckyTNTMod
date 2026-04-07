package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class LightningTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() < 120) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			
			double x = entity.x() + random.nextDouble() * 40d - 20d;
			double z = entity.z() + random.nextDouble() * 40d - 20d;
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
			lighting.setPos(x, LevelEvents.getTopBlock(level, x, z, false) + 1d, z);
			level.addFreshEntity(lighting);
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.LIGHTNING_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
