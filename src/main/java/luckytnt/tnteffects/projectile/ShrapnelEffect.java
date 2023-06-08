package luckytnt.tnteffects.projectile;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ShrapnelEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 3);
		explosion.doEntityExplosion(0.5f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1f, false, false);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.CLOUD, true, entity.x(), entity.y() + 0.5f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 0.25f;
	}
	
	@Override
	public Block getBlock() {
		return Blocks.BLACKSTONE;
	}
}
