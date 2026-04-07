package luckytnt.tnteffects;

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector3f;

import com.mojang.datafixers.util.Pair;

import luckytnt.entity.PrimedReplayTNT;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ReplayTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (!level.isClientSide() && entity instanceof PrimedReplayTNT tnt) {
			if (tnt.getTNTFuse() >= 200) {
				List<Pair<BlockPos, BlockState>> list = new LinkedList<>();
				ExplosionHelper.customSphericalExplosion(level, tnt.getPos(), 10, (lev, center, pos, state) -> {
					if (state.is(BlockRegistry.REPLAY_TNT.get())) {
						list.add(Pair.of(pos, Blocks.AIR.defaultBlockState()));
						return;
					}
					list.add(Pair.of(pos, state));
				});
				tnt.replayQueue.add(list);
			} else {
				for (Pair<BlockPos, BlockState> pair : tnt.replayQueue.poll()) {
					level.setBlockAndUpdate(pair.getFirst(), pair.getSecond());
				}
			}
			
			tnt.setDeltaMovement(0d, 0d, 0d);
			tnt.setPos(tnt.getPosition(0f));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (entity.getTNTFuse() >= 200) {
			level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 0.5f), entity.x(), entity.y() + 1.5d, entity.z(), 0d, 0d, 0d);
			for (double angle = 0d; angle < 360d; angle += 36d) {
				level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 0.5f), entity.x() + 0.125d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 1.5d + 0.125d * Math.sin(angle * Mth.DEG_TO_RAD), entity.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 0.5f), entity.x() + 0.0675d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 1.5d + 0.0675d * Math.sin(angle * Mth.DEG_TO_RAD), entity.z(), 0d, 0d, 0d);
			}
			for (double angle = 0; angle < 360; angle += 12D) {
				level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 0.5f), entity.x() + 0.175d * Math.cos(angle * Mth.DEG_TO_RAD), entity.y() + 1.5d + 0.175d * Math.sin(angle * Mth.DEG_TO_RAD), entity.z(), 0d, 0d, 0d);
			}
		} else {
			Vec3 vec1 = new Vec3((entity.x() + 0.175d) - (entity.x() - 0.175d), (entity.y() + 1.5d) - (entity.y() + 1.5d + 0.175d), 0d);
			Vec3 vec2 = new Vec3((entity.x() + 0.175d) - (entity.x() - 0.175d), (entity.y() + 1.5d) - (entity.y() + 1.5d - 0.175d), 0d);
			
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x(), entity.y() + 1.5d, entity.z(), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.0875d, entity.y() + 1.5d, entity.z(), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.0875d, entity.y() + 1.5d + 0.08d, entity.z(), 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.0875d, entity.y() + 1.5d - 0.08d, entity.z(), 0d, 0d, 0d);
			for (double d = 0d; d <= 0.35d; d += 0.05d) {
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.175d, entity.y() + 1.5d - 0.175d + d, entity.z(), 0d, 0d, 0d);
			}
			for (double d = 0d; d <= 1d; d += 0.1d) {
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.175d + d * vec1.x, entity.y() + 1.5d + 0.175d + d * vec1.y, entity.z(), 0d, 0d, 0d);
				level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 0.5f), entity.x() - 0.175d + d * vec2.x, entity.y() + 1.5d - 0.175d + d * vec2.y, entity.z(), 0d, 0d, 0d);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.REPLAY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 400;
	}
}
