package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ColossalTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 190);
		explosion.doEntityExplosion(20f, true);
		explosion.doBlockExplosion(1f, 1f, 0.167f, 0.05f, false, true);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.SMOKE, ent.x(), ent.y() + 19.5f, ent.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return Blocks.TNT;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 400;
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		return 20f;
	}
}
