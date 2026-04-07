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

public class MeteorShowerEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() <= 640 && entity.getTNTFuse() % 10 == 0) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			
			for (int count = 0; count <= 5; count++) {
				LExplosiveProjectile meteor = EntityRegistry.MINI_METEOR.get().create(level);
				meteor.setOwner(entity.owner());
				meteor.setPos(entity.getPos().add(random.nextDouble() * 400d - 200d, LuckyTNTConfigValues.DROP_HEIGHT.get() + random.nextDouble() * 50d, random.nextDouble() * 400d - 200d));
				level.addFreshEntity(meteor);
			}
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.METEOR_SHOWER.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 720;
	}
}
