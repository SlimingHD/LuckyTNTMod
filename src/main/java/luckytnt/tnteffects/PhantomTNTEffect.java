package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class PhantomTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		explosion.spawnExplosionParticles();
	}

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() == 5 && entity instanceof Entity ent) {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();

			double x = entity.x() + random.nextDouble() * 90d - 45d;
			double z = entity.z() + random.nextDouble() * 90d - 45d;
			int y = LevelEvents.getTopBlock(level, x, z, false) + 1;
			ent.setPos(x, y, z);
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}

	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
}
