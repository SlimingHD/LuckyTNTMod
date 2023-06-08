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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ReactionDynamiteEffect extends PrimedTNTEffect{

	@Override
	public void baseTick(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		if(entity instanceof LExplosiveProjectile ent) {
			if(ent.inGround()) {
				ent.getPersistentData().putBoolean("hitBefore", true);
			}
			if(ent.getTNTFuse() == 0) {
				ent.destroy();
			}
			if(ent.inGround() || ent.getPersistentData().getBoolean("hitBefore")) {
				explosionTick(ent);
				ent.setTNTFuse(ent.getTNTFuse() - 1);
			}
			if(level.isClientSide) {
				spawnParticles(entity);
			}
		}
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity){
		Level level = entity.getLevel();
		if(!level.isClientSide) {
			if(entity.getPersistentData().getInt("nextExplosion") == 0) {
				Vec3 randomPos = new Vec3(Math.random() * 20 - 10, Math.random() * 10 - 5, Math.random() * 20 - 10);
				float explosionSize = 5 + level.random.nextFloat() * 5;
				ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos().add(randomPos), Math.round(explosionSize));
				explosion.doEntityExplosion(1f + 0.05f * explosionSize, true);
				explosion.doBlockExplosion(1f, 1f, 0.75f, 1.25f, false, false);
				level.playSound((Entity)entity, toBlockPos(entity.getPos().add(randomPos)), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f);
				entity.getPersistentData().putInt("nextExplosion", 2 + level.random.nextInt(3));
			}
			entity.getPersistentData().putInt("nextExplosion", entity.getPersistentData().getInt("nextExplosion") - 1);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.37f, 1f, 1f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.59f, 1f, 0f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.11f, 0.26f, 0.11f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
		}
		if(Math.random() < 0.15f) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.16f, 0.42f, 0.15f), 1), entity.x(), entity.y(), entity.z(), 0, 0, 0);
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
