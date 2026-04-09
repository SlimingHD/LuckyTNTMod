package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterFullBlockExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.AlwaysExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterDistanceExplosionRule;
import luckytntlib.util.explosions.rules.FireExplosionRule;
import luckytntlib.util.explosions.rules.LogicExplosionRule;
import luckytntlib.util.explosions.rules.SimpleExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FirestormTNTEffect extends PrimedTNTEffect {

	private static final List<TagKey<Block>> REMOVE_TAGS = List.of(BlockTags.LOGS, BlockTags.PLANKS, BlockTags.BAMBOO_BLOCKS, BlockTags.BEEHIVES, BlockTags.LEAVES);

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.createCylindricalCrater(entity.getLevel(), entity.getPos(), 50, 50, 200f, FilterDistanceExplosionRule.lessEqual(50,
			new StackedExplosionRule(
				LogicExplosionRule.not(new FilterFullBlockExplosionRule(new AlwaysExplosionRule()),
					FilterBlockExplosionRule.builder().filterForTags(REMOVE_TAGS).build(new CraterExplosionRule())
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 50, 200f, new FilterAirExplosionRule(new SimpleExplosionRule(Blocks.NETHERRACK.defaultBlockState())));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 50, 0f, new FireExplosionRule(0.75f));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 1d, entity.z(), 0.25d, 0.25d, 0);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 1d, entity.z(), -0.25d, 0.25d, 0);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 1d, entity.z() + 0.5d, 0, 0.25d, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 1d, entity.z() - 0.5d, 0, 0.25d, -0.25d);
		
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 1d, entity.z() + 0.5d, 0.25d, 0.25d, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 1d, entity.z() + 0.5d, -0.25d, 0.25d, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 1d, entity.z() - 0.5d, 0.25d, 0.25d, -0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 1d, entity.z() - 0.5d, -0.25d, 0.25d, -0.25d);
		
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 0.5d, entity.z(), 0.25d, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 0.5d, entity.z(), -0.25d, 0, 0);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z() + 0.5d, 0, 0, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5d, entity.z() - 0.5d, 0, 0, -0.25d);
		
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 0.5d, entity.z() + 0.5d, 0.25d, 0, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 0.5d, entity.z() + 0.5d, -0.25d, 0, 0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() + 0.5d, entity.y() + 0.5d, entity.z() - 0.5d, 0.25d, 0, -0.25d);
		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x() - 0.5d, entity.y() + 0.5d, entity.z() - 0.5d, -0.25d, 0, -0.25d);

		entity.getLevel().addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 1d, entity.z(), 0, 0.25d, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.FIRESTORM_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
}
