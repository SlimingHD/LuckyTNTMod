package luckytnt.tnteffects;

import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class GhostTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 60) {
			Player player = ent.getLevel().getNearestPlayer((Entity)ent, 100);
			if(player != null && player != ent.owner()) {
				((Entity)ent).setPos(player.getX(), player.getY(), player.getZ());
			} else {
				LivingEntity entity = ent.getLevel().getNearestEntity(ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 100, ent.y() - 100, ent.z() - 100, ent.x() + 100, ent.y() + 100, ent.z() + 100)), TargetingConditions.forNonCombat().range(5).ignoreLineOfSight().ignoreInvisibilityTesting(), null, ent.x(), ent.y(), ent.z());
				if(entity != null && entity != ent.owner()) {
					((Entity)ent).setPos(entity.getX(), entity.getY(), entity.getZ());
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), 30);
		explosion.doEntityExplosion(1.5f, true);
		explosion.doBlockExplosion(1f, 1.1f, 1f, 1.2f, false, false);
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
