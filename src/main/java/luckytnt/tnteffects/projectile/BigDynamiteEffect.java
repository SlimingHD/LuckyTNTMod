package luckytnt.tnteffects.projectile;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

public class BigDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 10);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.25f, false, false);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.BIG_DYNAMITE.get();
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 3f;
	}
}
