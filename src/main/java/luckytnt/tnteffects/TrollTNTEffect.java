package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;

public class TrollTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 4);
		explosion.doEntityExplosion(1f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1f, false, false);
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 1;
	}
}
