package luckytnt.tnteffects.projectile;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.DistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class DisintegratingProjectileEffect extends ChemicalProjectileEffect {
	
	public DisintegratingProjectileEffect() {
		super(12, 10);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		super.explosionTick(entity);
		if (!entity.getLevel().isClientSide()) {
			ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 13, 200, DistanceExplosionRule.greaterEqual(11,
					FilterBlockExplosionRule.applyOnlyWhen(Blocks.STONE,
							new FilterRandomExplosionRule(0.01f,
									new SimpleExplosionRule(BlockRegistry.TOXIC_STONE.get().defaultBlockState())
							)
					)
			));
		}
		if (entity.getTNTFuse() % 20 == 0) {
			List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(toBlockPos(entity.getPos()).offset(-6, -6, -6), toBlockPos(entity.getPos()).offset(6, 6, 6)));
			DamageSources sources = new DamageSources(entity.getLevel().registryAccess());
			for (LivingEntity lent : list) {
				lent.hurt(sources.magic(), 5f);
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() + 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), ent.x() - 0.2f, ent.y() + 1f, ent.z(), 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 1f), ent.x(), ent.y() + 1f, ent.z() + 0.2f, 0, 0, 0);
		ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 0, 0), 1f), ent.x(), ent.y() + 1f, ent.z() - 0.2f, 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DISINTEGRATING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 120;
	}
}
