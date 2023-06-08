package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ChemicalTNTEffect extends PrimedTNTEffect{

	@SuppressWarnings("resource")
	@Override
	public void baseTick(IExplosiveEntity entity) {
		if(entity instanceof LExplosiveProjectile) {
			if(!entity.getLevel().isClientSide) {
				explosionTick(entity);
			}
			else {
				spawnParticles(entity);
			}
			entity.setTNTFuse(entity.getTNTFuse() - 1);
			if(entity.getTNTFuse() <= 0) {
				entity.destroy();
			}
		}
		else {
			super.baseTick(entity);
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity instanceof LExplosiveProjectile) {
		ImprovedExplosion dummyExplosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 4);
			ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 4, new IForEachBlockExplosionEffect() {		
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(distance + Math.random() < 4f && state.getExplosionResistance(level, pos, dummyExplosion) < 100) {
						state.onBlockExploded(level, pos, dummyExplosion);
					}
				}
			});
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		for(int count = 0; count < 30; count++) {
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
