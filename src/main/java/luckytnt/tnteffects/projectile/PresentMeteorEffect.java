package luckytnt.tnteffects.projectile;

import java.util.Random;

import luckytnt.block.PresentBlock;
import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomDistanceExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PresentMeteorEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		((ServerLevel)entity.getLevel()).sendParticles(ParticleTypes.WAX_OFF, entity.x(), entity.y() + 2, entity.z(), 500, 3f, 3f, 3f, 0f);
		if (LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS.get()) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 40);
			explosion.doEntityExplosion(3, true);
			ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 40, 100, new FilterAirExplosionRule(
					new StackedExplosionRule(
							FilterDistanceExplosionRule.lessEqual(35,new CraterExplosionRule()),
							FilterRandomDistanceExplosionRule.quadraticDecrease(35, 40, 
									new RandomBlockExplosionRule(RandomList.<BlockState>floatBuilder()
											.addEntry(Blocks.BLUE_ICE.defaultBlockState(), 0.125f)
											.addEntry(Blocks.PACKED_ICE.defaultBlockState(), 0.125f)
											.addEntry(Blocks.AIR.defaultBlockState(), 0.75f).build()
									)
							)
					)
			));
		}
		ImprovedExplosion dummyExplosion = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.customSurfaceExplosion(entity.getLevel(), entity.getPos(), 70, (level, center, pos, state) -> {
			BlockPos placePos = pos.above();
			int distanceSqr = (int)pos.distSqr(toBlockPos(center));
			if (level.getRandom().nextFloat() < 0.025f) {
				Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom());
				if (level.getBlockState(placePos).getExplosionResistance(level, placePos, dummyExplosion) < 100) {
					state.getBlock().wasExploded(level, placePos, dummyExplosion);
					level.setBlockAndUpdate(placePos, BlockRegistry.PRESENT.get().defaultBlockState().setValue(PresentBlock.FACING, facing).setValue(PresentBlock.TYPE, level.getRandom().nextInt(4)));
				}
			} else if (level.getBlockState(placePos).getExplosionResistance(level, placePos, dummyExplosion) < 100 && (distanceSqr < 3600 || level.getRandom().nextFloat() < 0.7f) && ((SnowLayerBlock)Blocks.SNOW).canSurvive(state, level, placePos)) {
				state.getBlock().wasExploded(level, placePos, dummyExplosion);
				level.setBlockAndUpdate(placePos, Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, level.getRandom().nextInt(1, 3)));
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.SNOWFLAKE, entity.x(), entity.y() + 4, entity.z(), 0, 0, 0);
	}

	@Override
	public float getSize(IExplosiveEntity entity) {
		return 4;
	}

	@Override
	public BlockState getBlockState(IExplosiveEntity entity) {
		if (!entity.getPersistentData().getBoolean("has_present")) {
			entity.getPersistentData().putBoolean("has_present", true);
			entity.getPersistentData().putInt("type", new Random().nextInt(4));
		}
		return BlockRegistry.PRESENT.get().defaultBlockState().setValue(PresentBlock.TYPE, entity.getPersistentData().getInt("type"));
	}
}
