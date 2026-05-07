package luckytnt.tnteffects.projectile;

import java.util.List;

import javax.annotation.Nullable;

import luckytnt.registry.ItemRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class HomingDynamiteEffect extends PrimedTNTEffect {

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 20);
		explosion.doEntityExplosion((ent, dist) -> {
			AdvancementHelper.grantAdvancementToOwnerOrNearby(entity, AdvancementKeys.BULLSEYE);
		});
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.25f, false, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (entity.getTNTFuse() < 390) {
			Entity target = entity.getLevel().getEntity(entity.getPersistentData().getInt("targetID"));
			if (target == null || (target instanceof LivingEntity ent && ent.isDeadOrDying())) {
				target = setTarget(entity);
				if (target != null) {
					entity.getPersistentData().putInt("targetID", target.getId());
				}
			} else {
				Entity ent = (Entity)entity;
				ent.setDeltaMovement(target.getEyePosition(1f).subtract(entity.getPos()).normalize());
			}
		}
	}
	
	@Nullable
	public Entity setTarget(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		Entity target = null;
		double distance = Double.POSITIVE_INFINITY;
		boolean seen = false;
		for (Player player : level.players()) {
			double playerDistanceSqr = entity.getPos().distanceToSqr(player.getPosition(1f));
			if (playerDistanceSqr > 10000d || player == entity.owner() || player.isDeadOrDying() || !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player) || playerDistanceSqr >= distance) {
				continue;
			}
			boolean canSee = Explosion.getSeenPercent(entity.getPos(), player) >= 0.5f;
			if (!seen || canSee) {
				distance = playerDistanceSqr;
				target = player;
				if (canSee) {
					seen = true;
				}
			}
		}
		if (target == null) {
			seen = false;
			List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, ((Entity)entity).getBoundingBox().inflate(100d), e -> !(e instanceof Player));
			for (LivingEntity ent : livingEntities) {
				double entityDistanceSqr = entity.getPos().distanceToSqr(ent.getPosition(1f));
				if (entityDistanceSqr > 10000d || ent == entity.owner() || ent.isDeadOrDying() || entityDistanceSqr >= distance) {
					continue;
				}
				boolean canSee = Explosion.getSeenPercent(entity.getPos(), ent) >= 0.5f;
				if (!seen || canSee) {
					distance = entityDistanceSqr;
					target = ent;
					if (canSee) {
						seen = true;
					}
				}
			}
		}
		return target;
	}
	
	@Override
	public boolean airFuse() {
		return true;
	}
	
	@Override
	public Item getItem() {
		return ItemRegistry.HOMING_DYNAMITE.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 400;
	}
}
