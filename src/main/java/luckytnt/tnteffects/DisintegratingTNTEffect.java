package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class DisintegratingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() <= 30) {
			((Entity)ent).setDeltaMovement(((Entity)ent).getDeltaMovement().x, 0.8f, ((Entity)ent).getDeltaMovement().z);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		for(int count = 0; count < 50; count++) {
			LExplosiveProjectile projectile = EntityRegistry.DISINTEGRATING_PROJECTILE.get().create(ent.getLevel());
			projectile.setPos(ent.getPos());
			projectile.setOwner(ent.owner());
			projectile.setDeltaMovement(Math.random() * 4f - Math.random() * 4f, Math.random() * 4f - Math.random() * 4f, Math.random() * 4f - Math.random() * 4f);
			ent.getLevel().addFreshEntity(projectile);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		Vec3 vec31 = new Vec3(0.8D, 0.8D, 0);
		Vec3 vec32 = new Vec3(-0.8D, 0.8D, 0);
		
		for(double offX = 0D; offX <= 1D; offX += 0.1D) {
			for(double offY = 0D; offY <= 1D; offY += 0.1D) {
				ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 0.5f), ent.x() - 0.5D + offX, ent.y() + 1.25D + offY, ent.z(), 0, 0, 0);
			}
		}
		for(double i = 0; i < 1; i += 0.1D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 0.5f), ent.x() + 0.4D + i * vec32.x, ent.y() + 1.35D + i * vec32.y, ent.z(), 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 0.5f), ent.x() - 0.4D + i * vec31.x, ent.y() + 1.35D + i * vec31.y, ent.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DISINTEGRATING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 200;
	}
}
