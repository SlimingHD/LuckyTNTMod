package luckytnt.tnteffects;

import java.util.concurrent.atomic.AtomicBoolean;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public class FarmingTNTEffect extends PrimedTNTEffect {
	
	private final int radius;
	
	public FarmingTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		
		AtomicBoolean frozen = new AtomicBoolean(false);
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(entity.getLevel());
		ExplosionHelper.customSurfaceExplosion(entity.getLevel(), entity.getPos(), radius, (level, center, pos, state) -> {
			BlockPos posAbove = pos.above();
			BlockState stateAbove = level.getBlockState(posAbove);
			if (state.getExplosionResistance(level, pos, dummy) >= 100f || stateAbove.getExplosionResistance(level, posAbove, dummy) >= 100f || !Block.isFaceFull(state.getCollisionShape(level, pos), Direction.UP) || stateAbove.isCollisionShapeFullBlock(level, posAbove) || random.nextFloat() < 0.3f) {
				return;
			}
			if (random.nextFloat() < 0.8f) {
				BlockState crop = getRandomCrop(random);
				level.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE, 7));
				level.setBlockAndUpdate(posAbove, crop);
			} else {
				boolean placeWater = true;
				for (Direction dir : Direction.Plane.HORIZONTAL) {
					BlockPos posRel = pos.relative(dir);
					if (level.getBlockState(posRel).canBeReplaced(Fluids.WATER)) {
						placeWater = false;
						break;
					}
				}
				if (placeWater) {
					boolean placeLantern = level.getBiome(pos).get().coldEnoughToSnow(pos);
					BlockPos posBelow = pos.below();
					BlockState stateBelow = level.getBlockState(posBelow);
					if ((placeLantern || !stateBelow.is(Blocks.WATER)) && !stateBelow.isCollisionShapeFullBlock(level, posBelow) && Math.max(stateBelow.getBlock().getExplosionResistance(), stateBelow.getFluidState().getExplosionResistance()) <= 200f) {
						level.setBlockAndUpdate(posBelow, Blocks.DIRT.defaultBlockState());
						stateBelow.getBlock().wasExploded(level, posBelow, dummy);
						frozen.set(frozen.get() || placeLantern);
					}
					
					level.setBlockAndUpdate(pos, placeLantern ? Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true) : Blocks.WATER.defaultBlockState());
					BlockState s = level.getBlockState(posAbove);
					if (s.isAir() && level.getRandom().nextFloat() < 0.25f) {
						level.setBlockAndUpdate(posAbove, Blocks.LILY_PAD.defaultBlockState());
					}
				}
			}
		});
		
		if (frozen.get()) {
			AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.FROZEN_HARVEST);
		}
	}
	
	private static BlockState getRandomCrop(RandomSource random) {
		BlockState crop = Blocks.POTATOES.defaultBlockState();
		int rand = random.nextInt(6);
		switch (rand) {
			case 0: crop = Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)); break;
			case 1: crop = Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)); break;
			case 2: crop = Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)); break;
			case 3: crop = Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, random.nextInt(4)); break;
			case 4: crop = Blocks.PUMPKIN_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)); break;
			case 5: crop = Blocks.MELON_STEM.defaultBlockState().setValue(BlockStateProperties.AGE_7, random.nextInt(8)); break;
		}
		return crop;
	}
	 
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0.5f, 0.1f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FARMING_TNT.get();
	}
}
