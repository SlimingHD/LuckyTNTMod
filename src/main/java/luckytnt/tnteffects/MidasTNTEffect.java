package luckytnt.tnteffects;

import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EffectRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class MidasTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if (!ent.getLevel().isClientSide() && ent.getTNTFuse() < 80 && ent.getTNTFuse() % 2 == 0) {
			Level level = ent.getLevel();
			int size = Math.max(1, ent.getPersistentData().getInt("size"));
			
			ExplosionHelper.createSphericalCrater(level, ent.getPos(), size, 99f, new FilterAirExplosionRule(
				LogicExplosionRule.and(
					LogicExplosionRule.not(
						FilterBlockExplosionRule.applyOnlyWhen(Blocks.GOLD_BLOCK, new AlwaysExplosionRule()), 
						new AlwaysExplosionRule()
					), 
					FilterDistanceExplosionRule.inBetween(Math.max(0, size - 2), size, new AlwaysExplosionRule()),
					new BlockExplosionRule(Blocks.GOLD_BLOCK.defaultBlockState())
				)
			));
			
			ent.getPersistentData().putInt("size", ++size);
			
			List<LivingEntity> entities = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(ent.getPos().add(-size, -size, -size), ent.getPos().add(size, size, size)));
			for (LivingEntity living : entities) {
				living.addEffect(new MobEffectInstance(EffectRegistry.MIDAS_TOUCH_EFFECT.get(), 2000, 0));
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0.4f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0d, 0d, 0d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.MIDAS_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
