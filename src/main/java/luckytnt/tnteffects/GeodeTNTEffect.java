package luckytnt.tnteffects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mojang.math.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GeodeTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		HashMap<BlockPos, BlockState> blocks = new HashMap<>();
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), 10, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				blocks.put(pos, state);
				state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.level()));
				level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
			}
		});
		if(entity.level() instanceof ServerLevel sLevel) {
			CaveFeatures.AMETHYST_GEODE.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, new BlockPos(entity.getPos()));
		}
		for(int i = blocks.size() - 1; i > 0; i--) {
			List<BlockPos> poses = new ArrayList<>(blocks.keySet());
			BlockPos pos = poses.get(i);
			if(entity.level().getBlockState(pos).is(Blocks.STONE)) {
				entity.level().setBlockAndUpdate(pos, blocks.get(pos));
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.1f, 1f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.level().addParticle(new DustParticleOptions(new Vector3f(0.3f, 0.3f, 0.3f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GEODE_TNT.get();
	}
}