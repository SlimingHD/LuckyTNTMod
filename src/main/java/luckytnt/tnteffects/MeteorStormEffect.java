package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MeteorStormEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() % 40 == 0) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			
			for (int count = 0; count < 6; count++) {
				LExplosiveProjectile meteor = EntityRegistry.LITTLE_METEOR.get().create(level);
				meteor.setOwner(entity.owner());
				meteor.setPos(entity.x() + 400d * random.nextDouble() - 200d, entity.y() + LuckyTNTConfigValues.DROP_HEIGHT.get() / 2 * random.nextDouble() + LuckyTNTConfigValues.DROP_HEIGHT.get() / 2, entity.z() + 400d * random.nextDouble() - 200d);
				level.addFreshEntity(meteor);
			}
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.METEOR_STORM.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 720;
	}
}
