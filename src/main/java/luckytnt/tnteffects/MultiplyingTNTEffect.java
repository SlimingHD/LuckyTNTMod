package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MultiplyingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent && ent.onGround() && ent.getPersistentData().getInt("level") > 0) {
			playExplosionSound(entity);
			serverExplosion(entity);
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();

		int tntLevel = entity.getPersistentData().getInt("level");
		if (tntLevel == 4) {
			ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), 10);
			explosion.doEntityExplosion(1f, true);
			explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
			explosion.spawnExplosionParticles();

			entity.destroy();
			return;
		}

		for (int count = 0; count < (tntLevel == 0 ? 4 : tntLevel * 2); count++) {
			PrimedLTNT tnt = EntityRegistry.MULTIPLYING_TNT.get().create(level);
			tnt.setPos(entity.getPos());
			tnt.setOwner(entity.owner());
			tnt.setDeltaMovement(random.nextDouble() * 2d - 1d, 1d + random.nextDouble(), random.nextDouble() * 2d - 1d);
			tnt.getPersistentData().putInt("level", tntLevel + 1);
			level.addFreshEntity(tnt);
		}
		entity.destroy();
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.MULTIPLYING_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return ((Entity)entity).getPersistentData().getInt("level") == 4 ? 100000 : 120;
	}
}
