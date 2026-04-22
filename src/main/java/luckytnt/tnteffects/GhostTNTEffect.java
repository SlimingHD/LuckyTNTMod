package luckytnt.tnteffects;

import java.util.List;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GhostTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		Level level = entity.getLevel();
		if (entity.getTNTFuse() == 60) {
			Player player = level.getNearestPlayer(ent, 100);
			if (player != null && player != entity.owner()) {
				ent.setPos(player.getX(), player.getY(), player.getZ());
			} else {
				List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, ent.getBoundingBox().inflate(100d), e -> e != entity.owner());
				if (targets.isEmpty()) {
					return;
				}
				targets.sort((ent1, ent2) -> ent1.distanceToSqr(ent) < ent2.distanceToSqr(ent) ? -1 : 1);
				LivingEntity target = targets.get(0);
				ent.setPos(target.getX(), target.getY(), target.getZ());
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), 30);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doImprovedBlockExplosion(1f, 1.2f, false, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 150;
	}
}
