package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class NightTNTEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			server.setDayTime(18000);
		}
	}

	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, -0.1d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.NIGHT_TNT.get();
	}
}
