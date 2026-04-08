package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class InvertedTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity instanceof Entity ent) {
			Level level = ent.level();
			RandomSource random = level.getRandom();
			ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
			BlockPos centerPos = BlockPos.containing(entity.getPos());
			for (float angleY = 0f; angleY < 360f; angleY += 11.25f) {
				for (float angleX = 0f; angleX < 360f; angleX += 11.25f) {
					Vec3 viewVec = ent.getViewVector(1f);
					double x = 30d * viewVec.x + random.nextDouble() * 8d - 4d;
					double y = 30d * viewVec.y + random.nextDouble() * 8d - 4d;
					double z = 30d * viewVec.z + random.nextDouble() * 8d - 4d;
					double magnitude = Math.sqrt(x * x + y * y + z * z);
					for (double j = 0d; j < magnitude; j += magnitude / 34d) {
						BlockPos pos = centerPos.offset(Mth.floor(x * j), Mth.floor(y * j), Mth.floor(z * j));
						BlockState state = level.getBlockState(pos);
						BlockState stateDown = level.getBlockState(pos.below(60));
						if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) < 100f && !state.isCollisionShapeFullBlock(level, pos) && Math.max(stateDown.getBlock().getExplosionResistance(), stateDown.getFluidState().getExplosionResistance()) < 100f) {
							level.setBlockAndUpdate(pos, stateDown);
							state.getBlock().wasExploded(level, pos, dummy);
						}
					}
					ent.setXRot(angleX);
				}
				ent.setYRot(angleY);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.INVERTED_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 120;
	}
}
