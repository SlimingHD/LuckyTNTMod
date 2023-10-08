package luckytnt.tnteffects;

import java.util.List;

import luckytnt.network.ClientboundIntNBTPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class HungryTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		Entity target = null;
		double distance = 2000;
		List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(ent.x() - 50, ent.y() - 50, ent.z() - 50, ent.x() + 50, ent.y() + 50, ent.z() + 50));

		for(LivingEntity living : list) {
			double x = living.getX() - ent.x();
			double y = living.getEyeY() - ent.y();
			double z = living.getZ() - ent.z();
			double magnitude = Math.sqrt(x * x + y * y + z * z);

			if(magnitude < distance) {
				distance = magnitude;
				target = living;
			}
		}
		if(target != null) {
			double x = ent.x() - target.getX();
			double y = ent.y() - target.getY();
			double z = ent.z() - target.getZ();
			double magnitude = Math.sqrt(x * x + y * y + z * z);

			if(magnitude > 2) {
				Vec3 vec3d = new Vec3(x, y + 0.1D, z).normalize();
				if(!(target instanceof Player)) {
					target.setDeltaMovement(vec3d);
				} else if(target instanceof Player) {
					target.setDeltaMovement(vec3d.scale(0.3D));
				}
			} else if(magnitude <= 2) {
				if(!(target instanceof Player)) {
					ent.getPersistentData().putInt("amount", ent.getPersistentData().getInt("amount") + 1);
					if(!ent.getLevel().isClientSide()) {
        				PacketHandler.CHANNEL.send(new ClientboundIntNBTPacket("amount", ent.getPersistentData().getInt("amount"), ((Entity)ent).getId()), PacketDistributor.TRACKING_ENTITY.with((Entity)ent));
        			}
        			target.discard();
				} else if(target instanceof Player) {
					DamageSources sources = new DamageSources(ent.getLevel().registryAccess());
					
					target.hurt(sources.fellOutOfWorld(), 4f);
					Vec3 vec3d = new Vec3(target.getX() - ent.x(), target.getY() - ent.y(), target.getZ() - ent.z()).normalize().scale(10);
					target.setDeltaMovement(vec3d);
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		int amount = ent.getPersistentData().getInt("amount");
		if(amount < 0) {
			amount = 0;
		}
		if(amount > 20) {
			amount = 20;
		}

		float size = 80f + ((80f / 20f) * amount);
		float yStrength = 1.3f - ((0.3f / 20f) * amount);
		float resistanceImpact = 1f - ((0.833f / 20f) * amount);
		float knockback = 5f + ((10f / 20f) * amount);
		
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), Mth.floor((double)size));
		explosion.doEntityExplosion(knockback, true);
		explosion.doBlockExplosion(1f, yStrength, resistanceImpact, size >= 110f ? 0.05f : 1f, false, size >= 110f ? true : false);
	}
	
	@Override
	public float getSize(IExplosiveEntity ent) {
		int amount = ent.getPersistentData().getInt("amount");
		if(amount < 0) {
			amount = 0;
		}
		if(amount > 20) {
			amount = 20;
		}

		return 1f + (3f / 20f) * amount;
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HUNGRY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 600;
	}
}
