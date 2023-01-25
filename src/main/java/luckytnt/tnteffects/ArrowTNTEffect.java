package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.block.Block;

public class ArrowTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count < 300; count++) {
			Arrow arrow = new Arrow(EntityType.ARROW, entity.level());
			arrow.setPos(entity.x(), entity.y() + 0.5f, entity.z());
			arrow.setDeltaMovement(Math.random() * 3 - Math.random() * 3, Math.random() * 2 - Math.random(), Math.random() * 3 - Math.random() * 3);
			arrow.setBaseDamage(10);
			entity.level().addFreshEntity(arrow);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ARROW_TNT.get();
	}
}
