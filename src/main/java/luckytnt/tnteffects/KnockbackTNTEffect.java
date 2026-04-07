package luckytnt.tnteffects;


import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KnockbackTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity.getTNTFuse() >= 1) {
			List<LivingEntity> entities = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-75d, -75d, -75d), entity.getPos().add(75d, 75d, 75d)), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
			for (LivingEntity living : entities) {
				if (living.getPersistentData().getInt("knockbacktime") > 0) {
					living.getPersistentData().putInt("knockbacktime", living.getPersistentData().getInt("knockbacktime") - 1);
				}
				double x = entity.x() - living.getX();
				double y = entity.y() - living.getY();
				double z = entity.z() - living.getZ();
				double distance = Math.sqrt(x * x + y * y + z * z) + 0.1d;
				Vec3 vec = new Vec3(x, y, z).normalize().scale(1d / (distance * 0.2d) + 0.5d).add(0d, 0.1d, 0d);
				if (distance > 2.1d && distance <= 75d && living.getPersistentData().getInt("knockbacktime") <= 0) {
					living.setDeltaMovement(vec);
					if (living instanceof Player player) {
						player.hurtMarked = true;
					}
				} else if (distance <= 2.1d) {
					living.getPersistentData().putInt("knockbacktime", 40);
					living.setDeltaMovement(vec.reverse().normalize().scale(5d).add(0d, 0.5d, 0d));
					if (living instanceof Player player) {
						player.hurtMarked = true;
					}
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		double maxDistanceSqr = 75d * 75d;
		List<LivingEntity> entities = entity.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-75, -75, -75), entity.getPos().add(75, 75, 75)), EntitySelector.NO_CREATIVE_OR_SPECTATOR);
		for (LivingEntity living : entities) {
			double x = living.getX() - entity.x();
			double y = living.getY() - entity.y();
			double z = living.getZ() - entity.z();
			double distanceSqr = x * x + y * y + z * z;
			if (distanceSqr <= maxDistanceSqr) {
				Vec3 vec = new Vec3(x, y, z).normalize().scale(15d).add(0d, 0.5d, 0d);
				living.setDeltaMovement(vec);
				if (living instanceof Player player) {
					player.hurtMarked = true;
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		double phi = Math.PI * (3d - Math.sqrt(5d));
		for (int i = 0; i < 600; i++) {
			double y = 1d - (i / 599d) * 2d;
			double radius = Math.sqrt(1d - y * y);
			
			double theta = phi * i;
			
			double x = Math.cos(theta) * radius;
			double z = Math.sin(theta) * radius;
			
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.8f, 0.2f), 0.75f), entity.x() + x * 2d, entity.y() + 0.5d + y * 2d, entity.z() + z * 2d, 0d, 0d, 0d);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.KNOCKBACK_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 300;
	}
}
