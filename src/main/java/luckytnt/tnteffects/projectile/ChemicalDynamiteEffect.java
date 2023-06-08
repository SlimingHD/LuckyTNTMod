package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ChemicalDynamiteEffect extends PrimedTNTEffect{
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getLevel() instanceof ServerLevel) {
			ExplosionHelper.doSphericalExplosion(entity.getLevel(), entity.getPos(), 4, new IForEachBlockExplosionEffect() {
				
				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel())) < 100 && distance + Math.random() <= 4) {
						state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
					}
				}
			});
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.6f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.8f, 0.4f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 1f, 0.8f), 1), entity.x(),+ entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.2f), 1), entity.x(),+ entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean playsSound() {
		return false;
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}

	@Override
	public boolean explodesOnImpact() {
		return false;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.CHEMICAL_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 40;
	}
}
