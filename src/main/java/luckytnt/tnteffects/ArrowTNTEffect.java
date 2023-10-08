package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.block.Block;

public class ArrowTNTEffect extends PrimedTNTEffect{

	private final int arrowCount;
	
	public ArrowTNTEffect(int arrowCount) {
		this.arrowCount = arrowCount;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count < arrowCount; count++) {
			Arrow arrow = new Arrow(EntityType.ARROW, entity.getLevel());
			arrow.setPos(entity.x(), entity.y() + (entity instanceof PrimedLTNT ? 0.5f : 0f), entity.z());
			arrow.setDeltaMovement(Math.random() * 3 - Math.random() * 3, Math.random() * 2 - Math.random(), Math.random() * 3 - Math.random() * 3);
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
