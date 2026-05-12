package luckytnt.tnteffects.projectile;

import java.util.HashMap;
import java.util.Map;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TunnelingDynamiteEffect extends PrimedTNTEffect {

	@SuppressWarnings("deprecation")
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();

		Level level = entity.getLevel();
		BlockPos center = BlockPos.containing(entity.getPos());
		
		float[][] vectorLengths = new float[9][9];
		BlockPos[][] blockPosCache = new BlockPos[9][9];
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				vectorLengths[i][j] = 240f;
			}
		}
		
		Map<BlockPos, Float> cachedResistances = new HashMap<BlockPos, Float>();
		Vec3 forward = entity.getPos().subtract(ent.getPosition(0f)).normalize();
		Vec3 worldUp = Math.abs(forward.y()) < 0.99d ? new Vec3(0d, 1d, 0d) : new Vec3(1d, 0d, 0d);
		Vec3 right = worldUp.cross(forward).normalize();
		Vec3 up = forward.cross(right).normalize();
		for (float step = 0; step < 240f; step += 0.225f) {
			int circleCount = 0;
			int finishedCount = 0;
			for (int i = 0; i <= 8; i++) {
				for (int j = 0; j <= 8; j++) {
					int distanceSqr = (i - 4) * (i - 4) + (j - 4) * (j - 4);
					if (distanceSqr > 16) {
						continue;
					}				
					circleCount++;
			
					float vectorLength = vectorLengths[i][j];
					if (vectorLength <= 0) {
						finishedCount++;
						continue;
					}
					
					BlockPos pos = center.offset(BlockPos.containing(forward.scale(step).add(right.scale(i - 4)).add(up.scale(j - 4))));
					if (pos.equals(blockPosCache[i][j])) {
						continue;
					}
					blockPosCache[i][j] = pos;
					
					BlockState state = level.getBlockState(pos);
					float resistance = cachedResistances.computeIfAbsent(pos, p -> Math.max(state.getBlock().getExplosionResistance(), state.getFluidState().getExplosionResistance()));
					vectorLengths[i][j] -= resistance;
					if (resistance > vectorLength) {
						continue;
					}
					
					level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					state.getBlock().wasExploded(level, pos, particleExplosion);
				}
			}
			if (finishedCount == circleCount) {
				break;
			}
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.TUNNELING_DYNAMITE.get();
	}
}
