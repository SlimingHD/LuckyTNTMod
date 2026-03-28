package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;

public class AirStrikeEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() <= 320 && entity.getTNTFuse() % 5 == 0) {
			RandomSource random = entity.getLevel().getRandom();
			for (int count = 0; count <= 5; count++) {
				LExplosiveProjectile bomb = EntityRegistry.BOMB.get().create(entity.getLevel());
				bomb.setPos(entity.getPos().add(random.nextDouble() * 100d - 50d, LuckyTNTConfigValues.DROP_HEIGHT.get() + random.nextDouble() * 50d, random.nextDouble() * 100d - 50d));
				bomb.setOwner(entity.owner());
				entity.getLevel().addFreshEntity(bomb);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.AIR_STRIKE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 360;
	}
}
