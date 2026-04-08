package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class DisintegratingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() <= 30) {
			Entity ent = (Entity)entity;
			ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8d, ent.getDeltaMovement().z);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < 50; count++) {
			LExplosiveProjectile projectile = EntityRegistry.DISINTEGRATING_PROJECTILE.get().create(entity.getLevel());
			projectile.setPos(entity.getPos());
			projectile.setOwner(entity.owner());
			projectile.setDeltaMovement(random.nextDouble() * 8d - 4d, random.nextDouble() * 8d - 4d, random.nextDouble() * 8d - 4d);
			entity.getLevel().addFreshEntity(projectile);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		for (double offX = 0d; offX <= 1d; offX += 0.1d) {
			for (double offY = 0d; offY <= 1d; offY += 0.1d) {
				entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 0.5f), entity.x() - 0.5d + offX, entity.y() + 1.25d + offY, entity.z(), 0, 0, 0);
			}
		}
		Vec3 offsetVec = new Vec3(0.8d, 0.8d, 0d);
		for (double i = 0; i < 1; i += 0.1d) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 0.5f), entity.x() + 0.4d - i * offsetVec.x, entity.y() + 1.35d + i * offsetVec.y, entity.z(), 0, 0, 0);
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 0.5f), entity.x() - 0.4d + i * offsetVec.x, entity.y() + 1.35d + i * offsetVec.y, entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DISINTEGRATING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
