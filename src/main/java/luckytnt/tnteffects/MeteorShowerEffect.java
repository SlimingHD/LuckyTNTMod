package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class MeteorShowerEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() <= 640 && entity.getTNTFuse() % 10 == 0) {
			for(int count = 0; count <= 5; count++) {
				LExplosiveProjectile meteor = EntityRegistry.MINI_METEOR.get().create(entity.getLevel());
				meteor.setOwner(entity.owner());
				meteor.setPos(entity.getPos().add(Math.random() * 400 - 200, LuckyTNTConfigValues.DROP_HEIGHT.get() + Math.random() * 50, Math.random() * 400 - 200));
				entity.getLevel().addFreshEntity(meteor);
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
