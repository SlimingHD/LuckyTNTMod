package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.event.LevelEvents;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WitherStormEffect extends PrimedTNTEffect {
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		Level level = ent.getLevel();
		
		ImprovedExplosion explosion = new ImprovedExplosion(level, (Entity)ent, ent.getPos(), 50);
		explosion.doEntityExplosion(3f, true);
		explosion.doImprovedBlockExplosion(1f, 1f, false, false, null);
		explosion.spawnExplosionParticles();
		
		ImprovedExplosion explosion2 = new ImprovedExplosion(level, (Entity)ent, ent.getPos(), 50);
		explosion2.doImprovedBlockExplosion(1f, 1f, false, false, new FilterAirExplosionRule(
			new RandomBlockExplosionRule(
				RandomList.<BlockState>floatBuilder().addEntry(Blocks.SOUL_SAND.defaultBlockState(), 0.7f).addEntry(Blocks.SOUL_SOIL.defaultBlockState(), 0.3f).build()
			)
		));
		
		WitherBoss wither = new WitherBoss(EntityType.WITHER, level);
		wither.setPos(ent.getPos());
		level.addFreshEntity(wither);
		
		RandomSource random = level.getRandom();
		for (int i = 0; i < 100 + random.nextInt(61); i++) {
			int offX = random.nextInt(141) - 70;
			int offZ = random.nextInt(141) - 70;
			WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
			if (level instanceof ServerLevel server) {
				skeleton.finalizeSpawn(server, level.getCurrentDifficultyAt(toBlockPos(ent.getPos())), MobSpawnType.MOB_SUMMONED, null, null);
			}
			skeleton.setPos(ent.x() + offX, LevelEvents.getTopBlock(level, ent.x() + offX, ent.z() + offZ, false) + 1, ent.z() + offZ);
			level.addFreshEntity(skeleton);
		}

		level.playSound(null, toBlockPos(ent.getPos()), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 3f, 1f);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getLevel() instanceof ServerLevel server) {
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 2.25d, entity.z(), 20, 0.1d, 0.5d, 0.1d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 3d, entity.z(), 20, 0.05d, 0.05d, 0.5d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 2.5d, entity.z(), 20, 0.05d, 0.05d, 0.3d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 2d, entity.z(), 20, 0.05d, 0.05d, 0.2d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 3.5d, entity.z(), 20, 0.2d, 0.2d, 0.2d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 3.25d, entity.z() + 1d, 20, 0.15d, 0.15d, 0.15d, 0d);
			server.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 1f), entity.x(), entity.y() + 3.25d, entity.z() - 1d, 20, 0.15d, 0.15d, 0.15d, 0d);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.WITHER_STORM.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 160;
	}

}
