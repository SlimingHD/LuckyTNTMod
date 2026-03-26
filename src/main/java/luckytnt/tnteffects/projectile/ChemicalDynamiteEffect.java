package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.item.Item;

public class ChemicalDynamiteEffect extends ChemicalProjectileEffect {
	
	public ChemicalDynamiteEffect() {
		super(4, 3);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.6f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.6f, 0.8f, 0.4f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.8f, 1f, 0.8f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 1f, 0.2f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
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
