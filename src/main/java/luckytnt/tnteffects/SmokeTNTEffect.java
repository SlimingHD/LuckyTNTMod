package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class SmokeTNTEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		spawnParticles(entity);
		if(entity.getTNTFuse() < 460 && entity.getLevel() instanceof ServerLevel sLevel) {
			sLevel.sendParticles(new DustParticleOptions(new Vector3f(((Entity)entity).getPersistentData().getFloat("r"), ((Entity)entity).getPersistentData().getFloat("g"), ((Entity)entity).getPersistentData().getFloat("b")), 10f), entity.x(), entity.y(), entity.z(), 30, 2.5f, 2.5f, 2.5f, 0);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if(entity.getLevel() instanceof ServerLevel sLevel) {
			sLevel.sendParticles(new DustParticleOptions(new Vector3f(((Entity)entity).getPersistentData().getFloat("r"), ((Entity)entity).getPersistentData().getFloat("g"), ((Entity)entity).getPersistentData().getFloat("b")), 1f), entity.x(), entity.y() + 1f, entity.z(), 1, 0, 0, 0, 0);
		}
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
