package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class DisintegratingProjectileEffect extends PrimedTNTEffect {

	@Override
	public void baseTick(IExplosiveEntity entity) {
		if(!entity.getLevel().isClientSide()) {
			explosionTick(entity);
		} else {
			spawnParticles(entity);
		}
		entity.setTNTFuse(entity.getTNTFuse() - 1);
		if(entity.getTNTFuse() <= 0) {
			entity.destroy();
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 0) {
			ent.getLevel().playSound(null, toBlockPos(ent.getPos()), SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 1f, 1f);
		}
		if(!ent.getLevel().isClientSide()) {
			ExplosionHelper.doCubicalExplosion(ent.getLevel(), ent.getPos(), 12, new IForEachBlockExplosionEffect() {
					
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(distance < (10D + (Math.random() * 2))) {
						if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel())) < 200) {
							state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(ent.getLevel()));
						}
					} else if(distance > 11 && distance <= 13) {
						if(state.getBlock() == Blocks.STONE && Math.random() < 0.01D) {
							level.setBlock(pos, BlockRegistry.TOXIC_STONE.get().defaultBlockState(), 3);
						}
					}
				}
			});
		}
		if(ent.getTNTFuse() % 20 == 0) {
			BlockPos min = toBlockPos(ent.getPos()).offset(-6, -6, -6);
			BlockPos max = toBlockPos(ent.getPos()).offset(6, 6, 6);
			List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()));
			DamageSources sources = new DamageSources(ent.getLevel().registryAccess());
			for(LivingEntity lent : list) {
				lent.hurt(sources.magic(), 5f);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() + 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() - 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 1f), ent.x(), ent.y() + 1f, ent.z() + 0.2f, 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 1f), ent.x(), ent.y() + 1f, ent.z() - 0.2f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DISINTEGRATING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 120;
	}
}
