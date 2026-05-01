package luckytnt.tnteffects;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;

public class HypernovaEffect extends SphereTNTEffect {

	public static final ParticleOptions PARTICLE = new DustColorTransitionOptions(new Vector3f(0.4f, 0.8f, 1f), new Vector3f(0f, 0f, 0.6f), 1f);
	
	public HypernovaEffect() {
		super(() -> BlockRegistry.HYPERNOVA, 500, 100000f);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 540) {
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
			lighting.setPos(entity.x(), entity.y(), entity.z());
			entity.getLevel().addFreshEntity(lighting);
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 5);
			explosion.doEntityExplosion(2f, true);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
			explosion.spawnExplosionParticles();
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		double degrees = (entity.getTNTFuse() / 540d) * 1080d * Mth.DEG_TO_RAD;
		Quaternionf quat1 = new Quaternionf().setAngleAxis(6f * Mth.DEG_TO_RAD, Math.cos(degrees), 0f, Math.sin(degrees));
		Quaternionf quat2 = new Quaternionf().setAngleAxis(6f * Mth.DEG_TO_RAD, 0f, 1f, 0f);
		Quaternionf quat3 = new Quaternionf().setAngleAxis(6f * Mth.DEG_TO_RAD, Math.sin(degrees), 0f, -Math.cos(degrees));
		Vector3f vec1 = new Vector3f(0f, 2f, 0f);
		Vector3f vec2 = new Vector3f(2f, 0f, 0f);
		Vector3f vec3 = new Vector3f(0f, 2f, 0f);
		for (int i = 0; i < 60; i++) {
			entity.getLevel().addParticle(PARTICLE, entity.x() + vec1.x, entity.y() + 0.5d + vec1.y, entity.z() + vec1.z, 0d, 0d, 0d);
			entity.getLevel().addParticle(PARTICLE, entity.x() + vec2.x, entity.y() + 0.5d + vec2.y, entity.z() + vec2.z, 0d, 0d, 0d);
			entity.getLevel().addParticle(PARTICLE, entity.x() + vec3.x, entity.y() + 0.5d + vec3.y, entity.z() + vec3.z, 0d, 0d, 0d);
			vec1.rotate(quat1);
			vec2.rotate(quat2);
			vec3.rotate(quat3);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 540;
	}
}
