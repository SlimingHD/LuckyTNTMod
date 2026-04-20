package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HyperionEffect extends GroveTNTEffect {

	public HyperionEffect() {
		super(50, 5, 0.3f, true);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		
		for (int count = 0; count < 10; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0f), 1f), entity.x() + random.nextDouble() * 0.5d - 0.25d, entity.y() + 1d + random.nextDouble() * 2d, entity.z() + random.nextDouble() * 0.5d - 0.25d, 0d, 0d, 0d);
		}
		for (int count = 0; count < 40; count++) {
			level.addParticle(new DustParticleOptions(new Vector3f(0f, 0.5f, 0f), 1f), entity.x() + random.nextDouble() * 2d - 1d, entity.y() + 3d + random.nextDouble() * 2d - 1d, entity.z() + random.nextDouble() * 2d - 1d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HYPERION.get();
	}
}
