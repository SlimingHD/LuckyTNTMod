package luckytnt.tnteffects;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WitheringTNTEffect extends PrimedTNTEffect{

	private final int strength;
	
	public WitheringTNTEffect(int strength) {
		this.strength = strength;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)entity, entity.getPos(), strength);
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.5f, false, false, null);
		explosion.spawnExplosionParticles();
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, new FilterAirExplosionRule(
			new RandomBlockExplosionRule(
				RandomList.ofEqualProbability(Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SOIL.defaultBlockState())
			)
		));
		
		RandomSource random = level.getRandom();
		for (int i = 0; i < strength * 2; i++) {
			int offX = random.nextInt(strength * 2 + 1) - strength;
			int offZ = random.nextInt(strength * 2 + 1) - strength;
			WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
			if (level instanceof ServerLevel server) {
				skeleton.finalizeSpawn(server, level.getCurrentDifficultyAt(toBlockPos(entity.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			skeleton.setPos(Mth.floor(entity.x()) + 0.5d, LevelEvents.getTopBlock(level, entity.x() + offX, entity.z() + offZ, false) + 1, Mth.floor(entity.z()) + 0.5d);
			level.addFreshEntity(skeleton);
		}
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WITHERING_TNT.get();
	}
}
