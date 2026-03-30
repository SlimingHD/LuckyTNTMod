package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SinkholeTNTEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void explosionTick(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		if (!level.isClientSide()) {
			if (ent.getTNTFuse() == 150) {
				((Entity)ent).setDeltaMovement(Vec3.ZERO);
				((Entity)ent).setNoGravity(true);
				ent.getPersistentData().putInt("depth", 20);
			}
			if (ent.getTNTFuse() <= 150 && ent.getTNTFuse() % 4 == 0) {
				ImprovedExplosion dummy = ImprovedExplosion.dummyExplosion(level);
				RandomSource random = level.getRandom();
				BlockPos center = BlockPos.containing(ent.getPos());
				for (int offX = -33; offX <= 33; offX++) {
					for (int offY = -33; offY <= (ent.getTNTFuse() == 150 ? 33 : 1); offY++) {
						for (int offZ = -33; offZ <= 33; offZ++) {
							double rand = random.nextDouble() * 4d - 2d;
							double distance = offX * offX + offY * offY + offZ * offZ + rand * rand * (rand >= 0d ? 1d : -1d);
							if (distance <= 900d) {
								BlockPos pos = center.offset(offX, offY + ent.getPersistentData().getInt("depth"), offZ);
								BlockState state = level.getBlockState(pos);
								if (Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()) <= 200f) {
									level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
									state.getBlock().wasExploded(level, pos, dummy);
								}
							}
						}
					}
				}
				ent.getPersistentData().putInt("depth", ent.getPersistentData().getInt("depth") - 2);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SINKHOLE_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 250;
	}
}
