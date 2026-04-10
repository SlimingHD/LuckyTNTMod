package luckytnt.tnteffects;

import java.util.List;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytnt.rules.FilterLiquidExplosionRule;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.BlockExplosionRule;
import luckytntlib.util.explosions.rules.CanSurviveExplosionRule;
import luckytntlib.util.explosions.rules.CopyBlockExplosionRule;
import luckytntlib.util.explosions.rules.CraterExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlastResistanceExplosionRule;
import luckytntlib.util.explosions.rules.FilterBlockExplosionRule;
import luckytntlib.util.explosions.rules.FilterOffYExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.FilterSurfaceExplosionRule;
import luckytntlib.util.explosions.rules.OffsetExplosionRule;
import luckytntlib.util.explosions.rules.StackedExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EndGateEffect extends PrimedTNTEffect{

	private static final List<TagKey<Block>> WOOD_TAGS = List.of(BlockTags.LOGS, BlockTags.PLANKS, BlockTags.ALL_SIGNS, BlockTags.WOODEN_TRAPDOORS, BlockTags.WOODEN_DOORS, BlockTags.WOODEN_SLABS, BlockTags.WOODEN_STAIRS, BlockTags.WOODEN_BUTTONS, BlockTags.WOODEN_PRESSURE_PLATES, BlockTags.WOODEN_FENCES, BlockTags.FENCE_GATES, BlockTags.BAMBOO_BLOCKS, BlockTags.CAMPFIRES, BlockTags.BEEHIVES, BlockTags.BANNERS);

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		ExplosionHelper.createSphericalCrater(level, entity.getPos().add(0d, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0d), 30, 200f, new FilterOffYExplosionRule(-20, 20,
			new OffsetExplosionRule(-LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 
				new FilterBlastResistanceExplosionRule(200f, 
					new StackedExplosionRule(
						FilterBlockExplosionRule.applyOnlyWhen(Blocks.AIR, new CopyBlockExplosionRule()),
						FilterBlockExplosionRule.applyOnlyWhen(BlockTags.LEAVES, new BlockExplosionRule(Blocks.PURPUR_BLOCK.defaultBlockState())),
						FilterBlockExplosionRule.builder().filterForTags(WOOD_TAGS).build(new BlockExplosionRule(Blocks.OBSIDIAN.defaultBlockState())),
						new FilterLiquidExplosionRule(new BlockExplosionRule(Blocks.AIR.defaultBlockState())),
						new BlockExplosionRule(Blocks.END_STONE.defaultBlockState())
					)
				)
			)
		));
		
		ExplosionHelper.createSphericalCrater(level, entity.getPos().add(0d, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), 0d), 30, 200f,
			new FilterSurfaceExplosionRule(false, new FilterRandomExplosionRule(0.05f, new CanSurviveExplosionRule(Blocks.CHORUS_FLOWER.defaultBlockState()))
		));

		ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), 30, 200f, new FilterOffYExplosionRule(-20, 20, new CraterExplosionRule()));
		
		RandomSource random = level.getRandom();
		for (int i = 0; i < 80; i++) {
			double offX = random.nextDouble() * 30d - 15d;
			double offZ = random.nextDouble() * 30d - 15d;
			double offY = LevelEvents.getTopBlock(level, entity.x() + offX, entity.z() + offZ, false);
			EnderMan enderMan = new EnderMan(EntityType.ENDERMAN, level);	
			enderMan.setPos(entity.x() + offX, offY, entity.z() + offZ);
			level.addFreshEntity(enderMan);
		}
		
		level.playSound(null, toBlockPos(entity.getPos()), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 0.5f, 1);
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.setDayTime(18000);
		}
		
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), 12);
		particleExplosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		ent.getLevel().addParticle(ParticleTypes.END_ROD, ent.x(), ent.y() + 1f, ent.z(), 0, 0, 0);
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.END_GATE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 140;
	}
}
