package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class NuclearTNTEffect extends PrimedTNTEffect {

	private final int strength;

	public NuclearTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(strength / 10f, true);
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LEAVES, new AlwaysExplosionRule()));
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, new FilterSurfaceExplosionRule(false, new FilterRandomExplosionRule(1f / 3f, new CanSurviveExplosionRule(BlockRegistry.NUCLEAR_WASTE.get().defaultBlockState()))));
		explosion.spawnExplosionParticles();
		
		List<LivingEntity> entities = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-strength, -strength, -strength), entity.getPos().add(strength, strength, strength)));
		for (LivingEntity living : entities) {
			living.addEffect(new MobEffectInstance(EffectRegistry.CONTAMINATED_EFFECT.get(), 48 * strength));
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.9f, 1f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.NUCLEAR_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 200;
	}
}
