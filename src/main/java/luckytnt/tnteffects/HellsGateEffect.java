package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterLiquidExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.FireExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class HellsGateEffect extends PrimedTNTEffect {

	private static final List<TagKey<Block>> WOOD_TAGS = List.of(BlockTags.LOGS, BlockTags.PLANKS, BlockTags.ALL_SIGNS, BlockTags.WOODEN_TRAPDOORS, BlockTags.WOODEN_DOORS, BlockTags.WOODEN_SLABS, BlockTags.WOODEN_STAIRS, BlockTags.WOODEN_BUTTONS, BlockTags.WOODEN_PRESSURE_PLATES, BlockTags.WOODEN_FENCES, BlockTags.FENCE_GATES, BlockTags.BAMBOO_BLOCKS, BlockTags.CAMPFIRES, BlockTags.BEEHIVES, BlockTags.BANNERS);
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int islandHeight = 50;
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos().add(0d, islandHeight, 0d), 30, 200f, new FilterOffYExplosionRule(-20, 20,
			new OffsetExplosionRule(-islandHeight, 
				new FilterBlastResistanceExplosionRule(200f, 
					new StackedExplosionRule(
						FilterBlockExplosionRule.applyOnlyWhen(Blocks.AIR, new CopyBlockExplosionRule()),
						FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LEAVES, new BlockExplosionRule(Blocks.NETHER_BRICKS.defaultBlockState())),
						FilterBlockExplosionRule.builder().filterForTags(WOOD_TAGS).build(new BlockExplosionRule(Blocks.OBSIDIAN.defaultBlockState())),
						new FilterLiquidExplosionRule(true, new BlockExplosionRule(Blocks.LAVA.defaultBlockState())),
						new BlockExplosionRule(Blocks.NETHERRACK.defaultBlockState())
					)
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos().add(0d, islandHeight, 0d), 30, 0f, new FireExplosionRule(0.1f));
		
		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 30, 200f, new FilterOffYExplosionRule(-20, 20, new CraterExplosionRule()));
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0d, -0.1d);
		
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0.1d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0.1d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0.1d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0.1d, -0.1d);
		
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0.2d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0.2d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, 0.2d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, 0.2d, -0.1d);
	
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, -0.1d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, -0.1d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, -0.1d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, -0.1d, -0.1d);
		
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, -0.2d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, -0.2d, -0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), -0.1d, -0.2d, 0.1d);
		level.addParticle(ParticleTypes.FLAME, ent.x(), ent.y() + 0.5d, ent.z(), 0.1d, -0.2d, -0.1d);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.HELLS_GATE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
