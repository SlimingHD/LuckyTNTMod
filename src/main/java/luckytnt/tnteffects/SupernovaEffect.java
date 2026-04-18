package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;

public class SupernovaEffect extends SphereTNTEffect {

	public SupernovaEffect() {
		super(() -> BlockRegistry.SUPERNOVA, 200, 5000f);
	}

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() == 300) {
			Entity lighting = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.getLevel());
			lighting.setPos(entity.x(), entity.y(), entity.z());
			entity.getLevel().addFreshEntity(lighting);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double angle = 0d; angle < 360d; angle += 6d) {
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 0.5d, entity.z() + 2d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 0.5d + 2d * Math.sin(angle * Mth.DEG_TO_RAD), entity.z(), 0d, 0d, 0d);
			entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d + 2d * Math.cos(angle * Mth.DEG_TO_RAD), entity.z() + 2d * Math.sin(angle * Mth.DEG_TO_RAD), 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
}
