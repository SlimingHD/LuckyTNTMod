package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class AcidicTNTEffect extends PrimedTNTEffect {

	@Override
	public void baseTick(IExplosiveEntity entity) {
		if (entity instanceof LExplosiveProjectile) {
			if (!entity.getLevel().isClientSide()) {
				explosionTick(entity);
			} else {
				spawnParticles(entity);
			}
			entity.setTNTFuse(entity.getTNTFuse() - 1);
			if (entity.getTNTFuse() <= 0) {
				entity.destroy();
			}
		} else {
			super.baseTick(entity);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if (entity.getTNTFuse() <= 10) {
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 70; count++) {
			LExplosiveProjectile projectile = EntityRegistry.ACIDIC_PROJECTILE.get().create(entity.getLevel());
			projectile.setPos(entity.getPos());
			projectile.setOwner(entity.owner());
			projectile.setDeltaMovement(random.nextDouble() * 5d - 2.5d, random.nextDouble() * 2d - 1d, random.nextDouble() * 5d - 2.5d);
			entity.getLevel().addFreshEntity(projectile);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Vec3 vec31 = new Vec3(0.5d, Math.sqrt(1d - (0.5d * 0.5d)), 0);
		Vec3 vec32 = new Vec3(-0.5d, Math.sqrt(1d - (0.5d * 0.5d)), 0);
		for (int count = 0; count <= 10; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.5d + 0.1d * count, entity.y() + 1.25d, entity.z(), 0, 0, 0);
		}
		for (double i = 0; i < 1; i += 0.1d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() + 0.5d + i * vec32.x, entity.y() + 1.25d + i * vec32.y, entity.z(), 0, 0, 0);
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.5d + i * vec31.x, entity.y() + 1.25d + i * vec31.y, entity.z(), 0, 0, 0);
		}
		for (double i = 0; i < 0.7; i += 0.1d) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() - 0.1d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() + 0.1d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
		}
		for (double i = 0; i < 0.4; i += 0.1d) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() - 0.2d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() + 0.2d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
		}
		for (double i = 0; i < 0.2; i += 0.1d) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() - 0.3d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() + 0.3d, entity.y() + 1.35d + i * vec31.y, entity.z(), 0, 0, 0);
		}
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() - 0.4d, entity.y() + 1.35d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x() + 0.4d, entity.y() + 1.35d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x(), entity.y() + 1.35d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x(), entity.y() + 1.45d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x(), entity.y() + 1.55d, entity.z(), 0, 0, 0);
		for (double i = 0; i < 0.3; i += 0.1d) {
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x(), entity.y() + 1.65d + i * vec31.y, entity.z(), 0, 0, 0);
		}
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x(), entity.y() + 1.95d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0f), 0.5f), entity.x(), entity.y() + 2.05d, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.ACIDIC_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return ent instanceof PrimedLTNT ? 160 : 120;
	}
}
