package luckytnt.tnteffects;

import java.util.HashMap;
import java.util.Map.Entry;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GeodeTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		HashMap<BlockPos, BlockState> blocks = new HashMap<>();
		ExplosionHelper.customSphericalExplosion(level, entity.getPos(), 10, (l, center, pos, state) -> {
			blocks.put(pos, state);
			level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
		});
		if (level instanceof ServerLevel serverLevel) {
			Holder<ConfiguredFeature<?, ?>> feature = entity.getLevel().registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(CaveFeatures.AMETHYST_GEODE);
			feature.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), level.getRandom(), toBlockPos(entity.getPos()));
		}
		for (Entry<BlockPos, BlockState> block : blocks.entrySet()) {
			if (level.getBlockState(block.getKey()).is(Blocks.STONE)) {
				level.setBlockAndUpdate(block.getKey(), block.getValue());
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.1f, 1f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.3f, 0.3f, 0.3f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GEODE_TNT.get();
	}
}
