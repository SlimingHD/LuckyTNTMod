package luckytnt.tnteffects.projectile;

import java.util.Random;

import luckytnt.block.PresentBlock;
import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PresentMeteorEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		((ServerLevel)entity.level()).sendParticles(ParticleTypes.WAX_OFF, entity.x(), entity.y() + 2, entity.z(), 500, 3f, 3f, 3f, 0f);
		Random random = new Random();
		if(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS.get()) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.level(), (Entity)entity, entity.getPos(), 40);
			explosion.doEntityExplosion(3, true);
			ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 40, new IForEachBlockExplosionEffect() {
				
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(distance <= (35) && state.getExplosionResistance(level, pos, explosion) <= 100) {
						state.onBlockExploded(level, pos, explosion);
					}
					else if(!state.isAir() && Math.random() < 0.6f && state.getExplosionResistance(level, pos, explosion) <= 100) {
						state.onBlockExploded(level, pos, explosion);
						if(Math.random() < 0.25f) {
							level.setBlockAndUpdate(pos, Math.random() < 0.5f ? Blocks.BLUE_ICE.defaultBlockState() : Blocks.PACKED_ICE.defaultBlockState());
						}
					}
				}
			});
		}
		ExplosionHelper.doTopBlockExplosionForAll(entity.level(), entity.getPos(), 70, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(Math.random() < 0.025f) {
					Direction dir = Direction.NORTH;
					switch(random.nextInt(4)) {
						case 1: dir = Direction.EAST; break;
						case 2: dir = Direction.SOUTH; break;
						case 3: dir = Direction.WEST; break;
					}
					level.setBlockAndUpdate(pos, BlockRegistry.PRESENT.get().defaultBlockState().setValue(PresentBlock.FACING, dir).setValue(PresentBlock.TYPE, random.nextInt(4)));
				}
				else if(((SnowLayerBlock)Blocks.SNOW).canSurvive(state, level, pos) && (distance < 60 || Math.random() < 0.7f)) {
					level.setBlockAndUpdate(pos, Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, random.nextInt(1, 3)));
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(ParticleTypes.SNOWFLAKE, entity.x(), entity.y() + 4, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 4;
	}

	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		if(!entity.getPersistentData().getBoolean("has_present")) {
			entity.getPersistentData().putBoolean("has_present", true);
			entity.getPersistentData().putInt("type", new Random().nextInt(4));
		}
		return BlockRegistry.PRESENT.get().defaultBlockState().setValue(PresentBlock.TYPE, entity.getPersistentData().getInt("type"));
	}
}
