package luckytnt.tnteffects;


import java.util.List;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KnockbackTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		List<LivingEntity> ents = ent.level().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 75, ent.y() - 75, ent.z() - 75, ent.x() + 75, ent.y() + 75, ent.z() + 75));
		for (LivingEntity lent : ents) {
			if (lent.getPersistentData().getInt("knockbacktime") > 0) {
				lent.getPersistentData().putInt("knockbacktime", lent.getPersistentData().getInt("knockbacktime") - 1);
			}
			double x = ent.x() - lent.getX();
			double y = ent.y() - lent.getY();
			double z = ent.z() - lent.getZ();
			double distance = Math.sqrt(x * x + y * y + z * z) + 0.1D;
			Vec3 vec = new Vec3(x, y, z).normalize().scale(1D / (distance * 0.2D) + 0.5D);
			if (distance > 2.1D && distance <= 75D && lent.getPersistentData().getInt("knockbacktime") <= 0) {
				if (lent instanceof Player player) {
					if (!player.isCreative()) {
						lent.setDeltaMovement(vec);
					}
				} else {
					lent.setDeltaMovement(vec);
				}
			} else if (distance <= 2.1D) {
				if (lent instanceof Player player) {
					if (!player.isCreative()) {
						lent.getPersistentData().putInt("knockbacktime", 40);
						lent.setDeltaMovement(vec.reverse().normalize().scale(5D).add(0, 0.5D, 0));
					}
				} else {
					lent.getPersistentData().putInt("knockbacktime", 40);
					lent.setDeltaMovement(vec.reverse().normalize().scale(5D).add(0, 0.5D, 0));
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		if(ent.level() instanceof ServerLevel sl) {
			List<LivingEntity> ents = ent.level().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 75, ent.y() - 75, ent.z() - 75, ent.x() + 75, ent.y() + 75, ent.z() + 75));
			for(LivingEntity lent : ents) {
				double x = ent.x() - lent.getX();
				double y = ent.y() - lent.getY();
				double z = ent.z() - lent.getZ();
				Vec3 vec = new Vec3(x, y, z).reverse().normalize();
				if(lent instanceof Player player) {
					if(!player.isCreative()) {
						lent.setDeltaMovement(vec.scale(8D).add(0, 1D, 0));
						for(ServerPlayer splayer : sl.players()) {
							if(splayer.distanceTo(lent) < 100f) {
								splayer.connection.send(new ClientboundSetEntityMotionPacket(lent));
							}
						}
					}
				} else {
					lent.setDeltaMovement(vec.scale(8D).add(0, 1D, 0));
					for(ServerPlayer splayer : sl.players()) {
						if(splayer.distanceTo(lent) < 100f) {
							splayer.connection.send(new ClientboundSetEntityMotionPacket(lent));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		double phi = Math.PI * (3D - Math.sqrt(5D));
		for(int i = 0; i < 600; i++) {
			double y = 1D - ((double)i / (600D - 1D)) * 2D;
			double radius = Math.sqrt(1D - y * y);
			
			double theta = phi * i;
			
			double x = Math.cos(theta) * radius;
			double z = Math.sin(theta) * radius;
			
			ent.level().addParticle(new DustParticleOptions(new Vector3f(0.2f, 0.8f, 0.2f), 0.75f), ent.x() + x * 2, ent.y() + 0.5D  + y * 2, ent.z() + 2 * z, 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.KNOCKBACK_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 300;
	}
}
