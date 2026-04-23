package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.block.UraniumOreBlock;
import luckytnt.registry.ItemRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class DeathRayRayEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.customSphericalExplosion(entity.getLevel(), entity.getPos(), 5, (level, center, pos, state) -> {
			if (state.getBlock() instanceof UraniumOreBlock) {
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				ItemEntity antimatter = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegistry.ANTIMATTER.get()));
				level.addFreshEntity(antimatter);
			} else {
				state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.getLevel()));
			}
		});
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 4);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 4);
		explosion.doEntityExplosion((ent, distance) -> {
			if (!ent.equals(entity.owner())) {
				DamageSources sources = ent.damageSources();
				if (ent instanceof ItemEntity itemEntity) {
					if (!itemEntity.getItem().getItem().equals(ItemRegistry.ANTIMATTER.get())) {
						ent.hurt(sources.explosion(explosion), 1);
					}
				} else {
					ent.hurt(sources.explosion(explosion), 200);
				}
			}
		});
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.5f, 0.25f, 0f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 10000;
	}
}
