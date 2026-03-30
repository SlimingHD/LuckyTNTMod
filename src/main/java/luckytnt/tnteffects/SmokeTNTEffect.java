package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class SmokeTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			float r = entity.getPersistentData().getFloat("r");
			float g = entity.getPersistentData().getFloat("g");
			float b = entity.getPersistentData().getFloat("b");

			server.sendParticles(new DustParticleOptions(new Vector3f(r, g, b), 1f), entity.x(), entity.y() + 1d, entity.z(), 1, 0d, 0d, 0d, 0d);
			if (entity.getTNTFuse() < 460) {
				server.sendParticles(new DustParticleOptions(new Vector3f(r, g, b), 10f), entity.x(), entity.y(), entity.z(), 30, 2.5d, 2.5d, 2.5d, 0d);
			}
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 520;
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.SMOKE_TNT.get();
	}
}
