package luckytnt.tnteffects.projectile;

import org.joml.Vector3f;

import luckytnt.registry.ItemRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ReactionDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if (entity instanceof LExplosiveProjectile ent) {
			if (ent.getTNTFuse() == 0) {
				ent.destroy();
			}
			if (ent.inGround() || ent.getPersistentData().getBoolean("hitBefore")) {
				ent.getPersistentData().putBoolean("hitBefore", true);
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if (level.isClientSide()) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity){
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		if (!level.isClientSide()) {
			if (entity.getPersistentData().getInt("nextExplosion") == 0) {
				Vec3 randomPos = new Vec3(random.nextDouble() * 20d - 10d, random.nextDouble() * 10d - 5d, random.nextDouble() * 20d - 10d);
				float explosionSize = 5f + random.nextFloat() * 5f;
				ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos().add(randomPos), Math.round(explosionSize));
				explosion.doEntityExplosion(1f + 0.05f * explosionSize, true);
				explosion.doImprovedBlockExplosion(0.75f, 1.25f, false, false, null);
				explosion.spawnExplosionParticles();
				level.playSound((Entity)entity, toBlockPos(entity.getPos().add(randomPos)), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f);
				entity.getPersistentData().putInt("nextExplosion", 2 + random.nextInt(3));
			}
			entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		if(random.nextFloat() < 0.15f) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.37f, 1f, 1f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(random.nextFloat() < 0.15f) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.59f, 1f, 0f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(random.nextFloat() < 0.15f) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.11f, 0.26f, 0.11f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(random.nextFloat() < 0.15f) {
			level.addParticle(new DustParticleOptions(new Vector3f(0.16f, 0.42f, 0.15f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.REACTION_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 60;
	}
}
