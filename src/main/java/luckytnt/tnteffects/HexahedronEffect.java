package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class HexahedronEffect extends CubicTNTEffect {

	public HexahedronEffect() {
		super(25, 200f);
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		for (double d = 0f; d < 3.25f; d += 0.25f) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d, entity.y() - 1d + d, entity.z() + 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d, entity.y() - 1d + d, entity.z() - 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d, entity.y() - 1d + d, entity.z() - 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d, entity.y() - 1d + d, entity.z() + 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d + d, entity.y() - 1d, entity.z() + 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d, entity.y() - 1d, entity.z() - 1.5d + d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d + d, entity.y() + 2d, entity.z() + 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() - 1.5d, entity.y() + 2d, entity.z() - 1.5d + d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d - d, entity.y() - 1d, entity.z() - 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d, entity.y() - 1d, entity.z() + 1.5d - d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d - d, entity.y() + 2d, entity.z() - 1.5d, 0d, 0d, 0d);
			level.addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), entity.x() + 1.5d, entity.y() + 2d, entity.z() + 1.5d - d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HEXAHEDRON.get();
	}
}
