package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EnormousTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 380);
		explosion.doEntityExplosion(25f, true);
		explosion.doImprovedBlockExplosion(0.167f, 0.1f, true, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 39.5d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return Blocks.TNT;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 520;
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 40f;
	}
}
