package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SolarEruptionProjectileEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 8);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.2f, true, false, null);
		explosion.spawnExplosionParticles();
		
		ExplosionHelper.customSphericalExplosion(entity.getLevel(), entity.getPos(), 5, (level, center, pos, state) -> {
			if (entity.y() - pos.getY() >= 0 && entity.y() - pos.getY() <= 3) {
				if (state.getExplosionResistance(level, pos, explosion) < 100f && !state.isCollisionShapeFullBlock(level, pos)) {
					state.getBlock().wasExploded(level, pos, explosion);
					level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.LARGE_SMOKE, true, ent.x(), ent.y() + 0.5f, ent.z(), 0, 0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return Blocks.MAGMA_BLOCK;
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 1f;
	}
}
