package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class EnderTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public EnderTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity) entity, entity.getPos(), strength * 1.5f);
		explosion.doEntityExplosion(strength, false);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0f, 0.9f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ENDER_TNT.get();
	}
}
