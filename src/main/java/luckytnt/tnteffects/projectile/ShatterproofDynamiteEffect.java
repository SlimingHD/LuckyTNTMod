package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterCollidableExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class ShatterproofDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.legacySphericalExplosion(entity.getLevel(), entity.getPos(), 9, 1200, new FilterAirExplosionRule(
				new FilterCollidableExplosionRule(new BlockExplosionRule(Blocks.OBSIDIAN.defaultBlockState()))
		));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.1f, 0.1f, 0.1f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.3f, 0.8f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.SHATTERPROOF_DYNAMITE.get();
	}
}
