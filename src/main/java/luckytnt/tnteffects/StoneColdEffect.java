package luckytnt.tnteffects;

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector3f;

import luckytnt.entity.PrimedOreTNT;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.explosions.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;

public class StoneColdEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (level instanceof ServerLevel server) {
			server.setDayTime(server.getDayTime() + 200);
			tryPlaceStone(entity, 15, 100);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		ExplosionHelper.createSphericalCrater(level, entity.getPos(), 130, 200, new FilterAirExplosionRule(
			new StackedExplosionRule(
				new FilterFullBlockExplosionRule(new BlockExplosionRule(Blocks.BLUE_ICE.defaultBlockState())),
				FilterBlockExplosionRule.builder().filterForBlocks(Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS, Blocks.KELP, Blocks.KELP_PLANT).build(new BlockExplosionRule(Blocks.ICE.defaultBlockState())),
				LogicExplosionRule.and(
					LogicExplosionRule.not(
						new FilterFullBlockExplosionRule(new AlwaysExplosionRule()), 
						new AlwaysExplosionRule()
					),
					new FilterCollidableExplosionRule(new AlwaysExplosionRule()),
					new AlwaysExplosionRule()
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(level, entity.getPos(), 130, 200, new FilterSurfaceExplosionRule(false,
			new CanSurviveExplosionRule(Blocks.SNOW.defaultBlockState())
		));
		
		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().subtract(90d, 90d, 90d), entity.getPos().add(90d, 90d, 90d)));
		for (LivingEntity living : entities) {
			living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 2));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.9f, 1f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.STONE_COLD.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
	
	@SuppressWarnings("deprecation")
	private static void tryPlaceStone(IExplosiveEntity entity, int maxStone, int maxAttempts) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		List<BlockPos> positions = null;
		if (entity instanceof PrimedOreTNT tnt) {
			positions = tnt.availablePos;
		} else {
			return;
		}
		
		if (positions.size() == 0) {
			calculateAvailablePositions(entity, positions);
		}
		
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		int placedOres = 0;
		int attempts = 0;
		while (placedOres < maxStone && attempts < maxAttempts) {
			BlockPos pos = positions.remove(random.nextInt(positions.size()));
			BlockState state = level.getBlockState(pos);
			if (!state.isAir() && !state.is(Tags.Blocks.ORES) && Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && state.isCollisionShapeFullBlock(level, pos)) {
				level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
				state.getBlock().wasExploded(level, pos, dummy);
				level.playSound(null, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1f, 1f);
				placedOres++;
			}
			attempts++;
		}
	}
	
	private static void calculateAvailablePositions(IExplosiveEntity entity, List<BlockPos> list) {
		LinkedList<BlockPos> availablePositions = new LinkedList<>();
		ExplosionHelper.customSphericalExplosion(entity.getLevel(), entity.getPos(), 15, (lev, center, pos, state) -> {
			if (!state.isAir() && !state.is(Tags.Blocks.STONE) && state.isCollisionShapeFullBlock(lev, pos)) {
				availablePositions.add(pos);
			}
		});
		list.addAll(availablePositions);
	}
}
