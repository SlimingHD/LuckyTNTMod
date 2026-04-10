package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GravityTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() < 200) {
			Level level = entity.getLevel();
			
			List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-25d, -25d, -25d), entity.getPos().add(25d, 25d, 25d)), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
			for (LivingEntity living : entities) {
				Vec3 movement = living.getDeltaMovement();
				double x = living.getX() - entity.x();
				double y = living.getY() - entity.y();
				double z = living.getZ() - entity.z();
				double distanceSqr = x * x + y * y + z * z;
				if (movement.y < 5d) {
					if (distanceSqr > 4d) {
						living.setDeltaMovement(new Vec3(x, y, z).normalize().add(0d, 0.1d, 0d));
					} else {
						living.setDeltaMovement(movement.x, 6d, movement.z);
					}
					if (living instanceof Player) {
						living.hurtMarked = true;
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		Level level = entity.getLevel();
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 2d, entity.y() + 0.5d, entity.z(), -0.2d, 0d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 2d, entity.y() + 0.5d, entity.z(), 0.2d, 0d, 0d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z() + 2d, 0d, 0d, -0.2d);
		level.addParticle(ParticleTypes.SMOKE, entity.x(), entity.y() + 0.5d, entity.z() - 2d, 0d, 0d, 0.2d);
		
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 1.5d, entity.y() + 0.5d, entity.z() + 1.5d, -0.1d, 0d, -0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 1.5d, entity.y() + 0.5d, entity.z() - 1.5d, 0.1d, 0d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() + 1.5d, entity.y() + 0.5d, entity.z() - 1.5d, -0.1d, 0d, 0.1d);
		level.addParticle(ParticleTypes.SMOKE, entity.x() - 1.5d, entity.y() + 0.5d, entity.z() + 1.5d, 0.1d, 0d, -0.1d);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.GRAVITY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
}
