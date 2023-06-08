package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class TimerTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		float r = entity.getTNTFuse() < 400 ? 1f : 2f - 0.0025f * entity.getTNTFuse();
		float g = entity.getTNTFuse() >= 400 ? 1f : 0.0025f * entity.getTNTFuse();
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(r, g, 0), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.TIMER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 800;
	}
}
