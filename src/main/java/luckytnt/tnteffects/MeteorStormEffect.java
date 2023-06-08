package luckytnt.tnteffects;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.level.block.Block;

public class MeteorStormEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() % 40 == 0) {
			for(int count = 0; count < 6; count++) {
				LExplosiveProjectile meteor = EntityRegistry.LITTLE_METEOR.get().create(ent.getLevel());
				meteor.setOwner(ent.owner());
				meteor.setPos(ent.x() + 400 * Math.random() - 200, ent.y() + LuckyTNTConfigValues.DROP_HEIGHT.get() / 2 * Math.random() + LuckyTNTConfigValues.DROP_HEIGHT.get() / 2, ent.z() + 400 * Math.random() - 200);
				ent.getLevel().addFreshEntity(meteor);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.METEOR_STORM.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 720;
	}
}
