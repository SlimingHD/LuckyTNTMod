package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GlobalDisasterEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 50, new IForEachBlockExplosionEffect() {
		
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 200 && !state.isAir()) {
					state.getBlock().onBlockExploded(state, level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(double angle = 0; angle < 360; angle += 6D) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 2 * Math.cos(angle * Math.PI / 180), ent.y() + 0.5f, ent.z() + 2 * Math.sin(angle * Math.PI / 180), 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x() + 2 * Math.cos(angle * Math.PI / 180), ent.y() + 0.5f + 2 * Math.sin(angle * Math.PI / 180), ent.z(), 0, 0, 0);
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 0.75f), ent.x(), ent.y() + 0.5f + 2 * Math.cos(angle * Math.PI / 180), ent.z() + 2 * Math.sin(angle * Math.PI / 180), 0, 0, 0);
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.GLOBAL_DISASTER.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
