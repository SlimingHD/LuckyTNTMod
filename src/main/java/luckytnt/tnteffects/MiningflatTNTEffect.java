package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class MiningflatTNTEffect extends PrimedTNTEffect {

	private final int radius;
	private final int radiusY;

	public MiningflatTNTEffect(int radius, int radiusY) {
		this.radius = radius;
		this.radiusY = radiusY;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), radius);
		particleExplosion.spawnExplosionParticles();
		BlockPos centerPos = BlockPos.containing(entity.getPos());
		ExplosionHelper.customCylindricalExplosion(entity.getLevel(), entity.getPos(), radius, radiusY, (level, center, pos, state) -> {
			int offY = pos.getY() - centerPos.getY();
			if (offY >= 0 && Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 100f) {
				if (state.is(Tags.Blocks.ORES)) {
					Block.dropResources(state, level, pos);
				}

				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				state.getBlock().wasExploded(level, pos, particleExplosion);

				if (offY == 0 && random.nextFloat() < 0.05f && Block.canSupportCenter(level, pos.below(), Direction.UP)) {
					level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
				}
			}
		});
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.MININGFLAT_TNT.get();
	}
}
