package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.util.Noise3D;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class HoneyTNTEffect extends PrimedTNTEffect {

	private final int radius;
	
	public HoneyTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Noise3D noise = new Noise3D(radius * 4, radius * 4, radius * 4, 5);
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockPos centerPos = BlockPos.containing(entity.getPos());
		int interiorDistanceSqr = Mth.square(radius - 2);
		int beeHiveDistanceSqr = Mth.square(radius - 3);
		int scaledRadiusY = Mth.ceil(radius * 1.5f);
		ExplosionHelper.customSpheroidExplosion(entity.getLevel(), entity.getPos(), radius, new Vector3f(1f, 1.5f, 1f), (lev, center, pos, state) -> {
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
				int distanceSqr = Mth.square(pos.getX() - centerPos.getX()) + Mth.square(pos.getY() - centerPos.getY()) + Mth.square(pos.getZ() - centerPos.getZ());
				distanceSqr += random.nextInt(2);
				if (distanceSqr <= interiorDistanceSqr) {
					if (distanceSqr >= beeHiveDistanceSqr && random.nextFloat() < 0.05f) {
						level.setBlockAndUpdate(pos, Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, RedstoneTNTEffect.getRandomDirectionHorizontal(random)).setValue(BeehiveBlock.HONEY_LEVEL, random.nextInt(6)));
					} else {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						if (random.nextFloat() < 0.025f) {
							Bee bee = new Bee(EntityType.BEE, level);
							bee.setPos(pos.getX(), pos.getY(), pos.getZ());
							level.addFreshEntity(bee);
						}
					}
				} else {
					if (noise.getValue(pos.getX() - centerPos.getX() + radius, pos.getY() - centerPos.getY() + scaledRadiusY, pos.getX() - centerPos.getX() + radius) > 0.7d) {
						level.setBlockAndUpdate(pos, Blocks.HONEY_BLOCK.defaultBlockState());
					} else {
						level.setBlockAndUpdate(pos, Blocks.HONEYCOMB_BLOCK.defaultBlockState());
					}
				}
				state.getBlock().wasExploded(level, pos, dummy);
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.DRIPPING_HONEY, entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HONEY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
