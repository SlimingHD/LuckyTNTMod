package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterLiquidExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.FireExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FieryHellEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 50, Float.POSITIVE_INFINITY, new FilterOffYExplosionRule(-20, 0,
			new StackedExplosionRule(
				new FilterLiquidExplosionRule(new BlockExplosionRule(Blocks.LAVA.defaultBlockState())),
				LogicExplosionRule.not(FilterBlockExplosionRule.builder().filterForTags(List.of(net.minecraftforge.common.Tags.Blocks.STONE)).build(new AlwaysExplosionRule()),
					new FilterBlastResistanceExplosionRule(100f, new BlockExplosionRule(Blocks.LAVA.defaultBlockState()))
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 90, 100f, new FilterAirExplosionRule(
			new StackedExplosionRule(
				new FilterRandomExplosionRule(0.9f, new BlockExplosionRule(Blocks.NETHERRACK.defaultBlockState())),
				new FilterRandomExplosionRule(0.3f, new BlockExplosionRule(Blocks.LAVA.defaultBlockState()))
			)
		));
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 90, 0f, new FireExplosionRule(0.1f));
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 20);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		
		for (int count = 0; count < 15; count++) {
			Entity ghast = new Ghast(EntityType.GHAST, entity.getLevel());
			ghast.setPos(entity.x() + random.nextDouble() * 40d - 20d, entity.y() + 25d + 25d * random.nextDouble(), entity.z() + random.nextDouble() * 40d - 20d);
			entity.getLevel().addFreshEntity(ghast);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0.3d, 0d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0.1d, 0.2d, 0d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), -0.1d, 0.2d, 0d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0.2d, 0.1d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0.2d, -0.1d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0.2d, 0.2d, 0d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), -0.2d, 0.2d, 0d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0.2d, 0.2d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0d, 0.2d, -0.2d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0.3d, 0.2d, 0.3d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), -0.3d, 0.2d, -0.3d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), -0.3d, 0.2d, 0.3d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y(), entity.z(), 0.3d, 0.2d, -0.3d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.FIERY_HELL.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 240;
	}
}
