package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

public class KolaBoreholeTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
		BlockState defaultBlock = Blocks.STONE.defaultBlockState();
		if (level instanceof ServerLevel server && server.getChunkSource().getGenerator() instanceof NoiseBasedChunkGenerator generator) {
			defaultBlock = generator.generatorSettings().value().defaultBlock();
		}
		
		int x = Mth.floor(entity.x());
		int z = Mth.floor(entity.z());
		int endHeight = Mth.floor(entity.y()) + 32;
		int sectionHeight = (endHeight - level.getMinBuildHeight()) / 10;
		int blocksSinceLastIncrease = 0;
		int radius = 0;
		for (int y = level.getMinBuildHeight(); y < endHeight; y++) {
			for (int offX = -radius - 1; offX <= radius + 1; offX++) {
				for (int offZ = -radius - 1; offZ <= radius + 1; offZ++) {
					BlockPos pos = new BlockPos(x + offX, y, z + offZ);
					BlockState state = level.getBlockState(pos);
					int distanceSqr = offX * offX + offZ * offZ;
					if (distanceSqr <= radius * radius) {
						if (y <= level.getMinBuildHeight() + 5 || Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f && !state.isAir()) {
							level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
							state.getBlock().wasExploded(level, pos, dummy);
						}
					} else if (distanceSqr <= (radius + 1) * (radius + 1)) {
						if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f && !state.isCollisionShapeFullBlock(level, pos) && level.canSeeSkyFromBelowWater(pos)) {
							level.setBlockAndUpdate(pos, defaultBlock);
							state.getBlock().wasExploded(level, pos, dummy);
						}
					}
				}
			}
			blocksSinceLastIncrease++;
			if (blocksSinceLastIncrease >= sectionHeight) {
				blocksSinceLastIncrease = 0;
				radius++;
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.KOLA_BOREHOLE_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
