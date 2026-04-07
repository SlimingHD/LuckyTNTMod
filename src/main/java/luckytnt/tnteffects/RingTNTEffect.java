package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class RingTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		for (double angle = 0d; angle < 360d; angle += 30d) {
			PrimedLTNT tnt = EntityRegistry.TNT.get().create(level);
			tnt.setTNTFuse(80);
			tnt.setOwner(entity.owner());
			double x = entity.x() + 10d * Math.cos(angle * Mth.DEG_TO_RAD);
			double z = entity.z() + 10d * Math.sin(angle * Mth.DEG_TO_RAD);
			double y = LevelEvents.getTopBlock(level, x, z, false);
			tnt.setPos(x, y + 1d, z);
			level.addFreshEntity(tnt);
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.RING_TNT.get();
	}
}
