package luckytnt.tnteffects;

import java.util.List;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
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
			
			List<Entity> entities = level.getEntities((Entity)entity, new AABB(entity.getPos().add(-25d, -25d, -25d), entity.getPos().add(25d, 25d, 25d)), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
			for (Entity e : entities) {
				if (e instanceof IExplosiveEntity explosiveEnt && explosiveEnt.getEffect() == this) {
					continue;
				}
				Vec3 movement = e.getDeltaMovement();
				double x = entity.x() - e.getX();
				double y = entity.y() - e.getY();
				double z = entity.z() - e.getZ();
				double distanceSqr = x * x + y * y + z * z;
				if (movement.y < 5d) {
					if (distanceSqr > 4d) {
						e.addDeltaMovement(new Vec3(x, y, z).normalize().scale(0.1d).add(0d, 0.1d, 0d));
					} else {
						e.setDeltaMovement(movement.x, 6d, movement.z);
					}
					if (e instanceof Player) {
						e.hurtMarked = true;
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
