package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytnt.util.CustomTNTConfig;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.RandomList;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.explosions.rules.FilterAirExplosionRule;
import luckytntlib.util.explosions.rules.FilterRandomExplosionRule;
import luckytntlib.util.explosions.rules.RandomBlockExplosionRule;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CustomTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide()) {
			Entity ent = (Entity)entity;
			CompoundTag data = ent.getPersistentData();
			CustomTNTConfig config = LuckyTNTConfigValues.getCustomTNTExplosion(data.getInt("level"));
			if (config == CustomTNTConfig.FIREWORK) {
				ent.setDeltaMovement(ent.getDeltaMovement().x, 0.8f, ent.getDeltaMovement().z);
				if (entity.getTNTFuse() > 40) {
					entity.setTNTFuse(40);
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int customLevel = entity.getPersistentData().getInt("level");
		CustomTNTConfig config = LuckyTNTConfigValues.getCustomTNTExplosion(customLevel);
		int explosionIntensity = LuckyTNTConfigValues.getCustomTNTExplosionIntensity(customLevel);
		
		if (config == CustomTNTConfig.NORMAL_EXPLOSION) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), explosionIntensity * 5);
			explosion.doEntityExplosion(3f, true);
			explosion.doImprovedBlockExplosion(1f, 1.2f, explosionIntensity > 10 ? true : false, false, null);
			explosion.spawnExplosionParticles();
		} else if (config == CustomTNTConfig.SPHERICAL_EXPLOSION) {
			ExplosionHelper.createSphericalCrater(entity.getLevel(), entity.getPos(), explosionIntensity, 200);
			ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), explosionIntensity);
			particleExplosion.spawnExplosionParticles();
		} else if (config == CustomTNTConfig.CUBICAL_EXPLOSION) {
			ExplosionHelper.createCubicalCrater(entity.getLevel(), entity.getPos(), explosionIntensity, 200);
			ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), explosionIntensity);
			particleExplosion.spawnExplosionParticles();
		} else if (config == CustomTNTConfig.EASTER_EGG) {
			ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), explosionIntensity * 3);
			explosion.doImprovedBlockExplosion(1f, explosionIntensity > 10 ? 1.75f : 1.5f, false, false, null);
			explosion.doImprovedBlockExplosion(1f, 1f, false, false, new FilterAirExplosionRule(
				new FilterRandomExplosionRule(0.66f,
					new RandomBlockExplosionRule(RandomList.ofEqualProbability(Blocks.MELON.defaultBlockState(), Blocks.PUMPKIN.defaultBlockState()))
				)
			));
		}
		spawnChildren(entity, config, explosionIntensity);
		ImprovedExplosion particleExplosion = new ImprovedExplosion(entity.getLevel(), entity.getPos(), explosionIntensity);
		particleExplosion.spawnExplosionParticles();
	}
	
	private void spawnChildren(IExplosiveEntity entity, CustomTNTConfig config, int explosionIntensity) {
		if (config == CustomTNTConfig.FIREWORK) {
			spawnFireworkChildren(entity, explosionIntensity);
		} else {
			Level level = entity.getLevel();
			RandomSource random = level.getRandom();
			int customLevel = entity.getPersistentData().getInt("level");
			if (customLevel + 1 >= 3 || LuckyTNTConfigValues.getCustomTNTExplosion(customLevel + 1) == CustomTNTConfig.NO_EXPLOSION) {
				return;
			}
			int maxCount = config == CustomTNTConfig.EASTER_EGG ? explosionIntensity : 3;
			for (int count = 0; count < maxCount; count++) {
				PrimedLTNT custom = EntityRegistry.CUSTOM_TNT.get().create(level);
				custom.setPos(entity.getPos());
				custom.setOwner(entity.owner());
				custom.setDeltaMovement(random.nextDouble() * 2d - 1d, random.nextDouble() * 2d, random.nextDouble() * 2d - 1d);
				custom.getPersistentData().putInt("level", customLevel + 1);
				level.addFreshEntity(custom);
			}
		}
	}
	
	private void spawnFireworkChildren(IExplosiveEntity entity, int explosionIntensity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		int customLevel = entity.getPersistentData().getInt("level");
		boolean finalLevel = customLevel >= 3;
		for(int count = 0; count < 10 * explosionIntensity; count++) {
			PrimedLTNT custom = finalLevel ? EntityRegistry.TNT.get().create(level) : EntityRegistry.CUSTOM_TNT.get().create(level);
			custom.setPos(entity.getPos());
			custom.setOwner(entity.owner());
			custom.setDeltaMovement(random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d, random.nextDouble() * 3d - 1.5d);
			if (!finalLevel) {
				custom.getPersistentData().putInt("level", customLevel + 1);
			}
			level.addFreshEntity(custom);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		level.addParticle(new DustParticleOptions(new Vector3f(1f, 0f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(0f, 1f, 0f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0, 0, 0);
		level.addParticle(new DustParticleOptions(new Vector3f(0f, 0f, 1f), 1f), entity.x(), entity.y() + 1d, entity.z(), 0, 0, 0);
		CustomTNTConfig config = LuckyTNTConfigValues.getCustomTNTExplosion(entity.getPersistentData().getInt("level"));
		if(config == CustomTNTConfig.FIREWORK) {
			level.addParticle(ParticleTypes.FLAME, entity.x(), entity.y() + 0.5f, entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.CUSTOM_TNT.get();
	}
}
