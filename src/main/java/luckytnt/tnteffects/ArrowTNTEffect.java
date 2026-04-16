package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.block.Block;

public class ArrowTNTEffect extends PrimedTNTEffect {

	private final int arrowCount;
	
	public ArrowTNTEffect(int arrowCount) {
		this.arrowCount = arrowCount;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < arrowCount; count++) {
			Arrow arrow = new Arrow(EntityType.ARROW, entity.getLevel());
			arrow.setPos(entity.x(), entity.y() + 0.5d, entity.z());
			arrow.setDeltaMovement(random.nextDouble() * 6d - 3d, random.nextDouble() * 2d - random.nextDouble(), random.nextDouble() * 6d - 3d);
			arrow.setOwner(entity.owner());
			arrow.setBaseDamage(10);
			arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			entity.getLevel().addFreshEntity(arrow);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ARROW_TNT.get();
	}
}
