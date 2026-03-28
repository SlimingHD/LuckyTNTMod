package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;

public class ChemicalProjectileEffect extends PrimedTNTEffect {
	
	private final int radius;
	private final int roughEdgeStartRadius;
	
	public ChemicalProjectileEffect(int radius, int roughEdgeStartRadius) {
		this.radius = radius;
		this.roughEdgeStartRadius = roughEdgeStartRadius;
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), radius, 100, new FilterAirExplosionRule(
					new StackedExplosionRule(
							FilterDistanceExplosionRule.lessEqual(3, new CraterExplosionRule()),
							FilterRandomDistanceExplosionRule.quadraticDecrease(roughEdgeStartRadius, radius, new CraterExplosionRule())
					)
			));
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		entity.getLevel().playSound(null, toBlockPos(entity.getPos()), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 1f, 1f);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.6f), 1), entity.x() + 0.2f, entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.8f, 0.4f), 1), entity.x() - 0.2f, entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 1f, 0.8f), 1), entity.x(), entity.y() + 1f, entity.z() + 0.2f, 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.2f), 1), entity.x(), entity.y() + 1f, entity.z() - 0.2f, 0, 0, 0);
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}

	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CHEMICAL_TNT.get();
	}
}
