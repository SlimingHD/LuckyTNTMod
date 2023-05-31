package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WitherStormEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), (Entity)ent, ent.getPos(), 50);
		explosion.doEntityExplosion(3f, true);
		explosion.doBlockExplosion(1f, 1.3f, 1f, 1f, false, false);
		
		ImprovedExplosion explosion2 = new ImprovedExplosion(ent.level(), (Entity)ent, ent.getPos(), 50);
		explosion2.doBlockExplosion(new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, explosion) < 100 && !state.isAir()) {
					if(Math.random() < 0.7D) {
						state.onBlockExploded(level, pos, explosion);
						level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 3);
					} else {
						state.onBlockExploded(level, pos, explosion);
						level.setBlock(pos, Blocks.SOUL_SOIL.defaultBlockState(), 3);
					}
				}
			}
		});
		
		WitherBoss wither = new WitherBoss(EntityType.WITHER, ent.level());
		wither.setPos(ent.getPos());
		ent.level().addFreshEntity(wither);
		
		for(int i = 0; i < 160; i++) {
			int offX = (int)Math.round(Math.random() * 140D - 70D);
			int offZ = (int)Math.round(Math.random() * 140D - 70D);
			WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, ent.level());
			if(ent.level() instanceof ServerLevel sl) {
				skeleton.finalizeSpawn(sl, ent.level().getCurrentDifficultyAt(toBlockPos(ent.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			for(int y = ent.level().getMaxBuildHeight(); y >= ent.level().getMinBuildHeight(); y--) {
				BlockPos pos = new BlockPos(Mth.floor(ent.x() + offX), y, Mth.floor(ent.z() + offZ));
				BlockState state = ent.level().getBlockState(pos);
				if(!Block.isFaceFull(state.getCollisionShape(ent.level(), pos), Direction.UP) && Block.isFaceFull(ent.level().getBlockState(pos.below()).getCollisionShape(ent.level(), pos.below()), Direction.UP)) {
					skeleton.setPos(pos.getX() + 0.5D, y, pos.getZ() + 0.5D);
					break;
				}
			}
			ent.level().addFreshEntity(skeleton);
		}
		
		ent.level().playSound(null, ent.x(), ent.y(), ent.z(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 3, 1);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.level() instanceof ServerLevel sl) {
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 2.25f, ent.z(), 20, 0.1f, 0.5f, 0.1f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 3f, ent.z(), 20, 0.05f, 0.05f, 0.5f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 2.5f, ent.z(), 20, 0.05f, 0.05f, 0.3f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 2f, ent.z(), 20, 0.05f, 0.05f, 0.2f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 3.5f, ent.z(), 20, 0.2f, 0.2f, 0.2f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 3.25f, ent.z() + 1, 20, 0.15f, 0.15f, 0.15f, 0);
			sl.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), ent.x(), ent.y() + 3.25f, ent.z() - 1, 20, 0.15f, 0.15f, 0.15f, 0);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WITHER_STORM.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 160;
	}

}
