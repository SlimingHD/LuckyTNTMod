package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.keys.AdvancementKeys;
import luckytnt.util.AdvancementHelper;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class LeapingTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = entity.getLevel();
		RandomSource random = level.getRandom();
		int bounces = ent.getPersistentData().getInt("bounces");
		if (ent.onGround() && !level.isClientSide()) {
			ent.getPersistentData().putInt("bounces", bounces + 1);
			ent.setDeltaMovement(random.nextDouble() * 2d - 1d, random.nextDouble() * 1.5d, random.nextDouble() * 2d - 1d);
			level.playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1f, 1f);
			if (bounces >= 1) {
				playExplosionSound(entity);
				serverExplosion(entity);
				if (bounces >= 24) {
					entity.destroy();
				}
			}
		}
	}

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int size = 10;
		float knockback = 1.5f;
		float vecLength = 1.25f;
		if (entity.getPersistentData().getInt("bounces") >= 24) {
			size = 20;
			knockback = 2f;
			vecLength = 1.5f;
		}
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity) entity, entity.getPos(), size);
		explosion.doEntityExplosion(knockback, true);
		explosion.doImprovedBlockExplosion(1f, vecLength, false, false, null);
		explosion.spawnExplosionParticles();
		
		for (Player player : explosion.getHitPlayers().keySet()) {
			if (player.isDeadOrDying()) {
				AdvancementHelper.grantAdvancementOnePlayer(player, AdvancementKeys.HOP_TIL_YOU_DROP);
			}
		}
	}

	@Override
	public Block getBlock() {
		return BlockRegistry.LEAPING_TNT.get();
	}

	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
