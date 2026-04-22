package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
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
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

public class HoneyTNTEffect extends PrimedTNTEffect {

	private final int radius;
	
	public HoneyTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		ImprovedNoise noise = new ImprovedNoise(random);
				
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockPos centerPos = BlockPos.containing(entity.getPos());
		int interiorDistance = radius - 3;
		int beeHiveDistance = radius - 4;
		ExplosionHelper.customSpheroidExplosion(entity.getLevel(), entity.getPos(), radius, new Vector3f(1f, 1.5f, 1f), (lev, center, pos, state) -> {
			if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 200f) {
				double distance = Math.sqrt(Mth.square(pos.getX() - centerPos.getX()) + Mth.square((pos.getY() - centerPos.getY())) / 1.5f + Mth.square(pos.getZ() - centerPos.getZ()));
				distance += random.nextDouble() * 2d;
				if (distance <= interiorDistance) {
					if (distance >= beeHiveDistance && random.nextFloat() < 0.05f) {
						level.setBlockAndUpdate(pos, Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, RedstoneTNTEffect.getRandomDirectionHorizontal(random)).setValue(BeehiveBlock.HONEY_LEVEL, random.nextInt(6)));
					} else {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						if (random.nextFloat() < 0.025f) {
							Bee bee = new Bee(EntityType.BEE, level);
							bee.setPos(pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d);
							level.addFreshEntity(bee);
						}
					}
				} else if (distance < radius - 0.2d) {
					if (noise.noise(pos.getX() - centerPos.getX(), pos.getY() - centerPos.getY(), pos.getX() - centerPos.getX()) > 0.2d) {
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
