package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class AirStrikeEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() <= 320 && entity.getTNTFuse() % 5 == 0) {
			for(int count = 0; count <= 5; count++) {
				LExplosiveProjectile bomb = EntityRegistry.BOMB.get().create(entity.level());
				bomb.setPos(entity.getPos().add(Math.random() * 100 - 50, LuckyTNTConfigValues.DROP_HEIGHT.get() + Math.random() * 50, Math.random() * 100 - 50));
				entity.level().addFreshEntity(bomb);
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
