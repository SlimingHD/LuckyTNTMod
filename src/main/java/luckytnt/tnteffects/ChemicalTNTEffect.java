package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class ChemicalTNTEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for (int count = 0; count < 30; count++) {
			LExplosiveProjectile projectile = EntityRegistry.CHEMICAL_PROJECTILE.get().create(entity.getLevel());
			projectile.setPos(entity.getPos());
			projectile.setOwner(entity.owner());
			projectile.setDeltaMovement(Math.random() * 1.5f - Math.random() * 1.5f, 0.2f, Math.random() * 1.5f - Math.random() * 1.5f);
			entity.getLevel().addFreshEntity(projectile);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.6f), 1), entity.x() + 0.2f, entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.8f, 0.4f), 1), entity.x() - 0.2f, entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 1f, 0.8f), 1), entity.x(),+ entity.y() + 1f, entity.z() + 0.2f, 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.2f), 1), entity.x(),+ entity.y() + 1f, entity.z() - 0.2f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CHEMICAL_TNT.get();
	}
}
