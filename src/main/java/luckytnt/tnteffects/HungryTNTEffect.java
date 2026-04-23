package luckytnt.tnteffects;

import java.util.List;

import luckytnt.network.ClientboundIntNBTPacket;
import luckytnt.network.PacketHandler;
import luckytnt.registry.BlockRegistry;
import luckytnt.registry.LuckyTNTDamageSources;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class HungryTNTEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if (!entity.getLevel().isClientSide() && entity instanceof Entity ent) {
			Level level = ent.level();
			
			Entity target = null;
			List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(entity.getPos().add(-50d, -50d, -50d), entity.getPos().add(50d, 50d, 50d)));
			list.sort((l1, l2) -> l1.distanceToSqr(ent) < l2.distanceTo(ent) ? -1 : 1);
			for (LivingEntity living : list) {
				if (living instanceof Player && living.getPersistentData().getInt("hungryTimer") > 0) {
					living.getPersistentData().putInt("hungryTimer", living.getPersistentData().getInt("hungryTimer") - 1);
					continue;
				}
				if (target == null) {
					target = living;
				}
			}
			
			if (target != null) {
				double x = entity.x() - target.getX();
				double y = entity.y() - target.getY();
				double z = entity.z() - target.getZ();
				double distanceSqr = x * x + y * y + z * z;

				if (distanceSqr >= 4d) {
					target.setDeltaMovement(new Vec3(x, y + 0.1d, z).normalize());
					if (target instanceof Player) {
						target.hurtMarked = true;
					}
				} else {
					level.playSound(null, target.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.MASTER, 2f, 1f);
					ent.getPersistentData().putInt("amount", ent.getPersistentData().getInt("amount") + 1);
					PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundIntNBTPacket("amount", ent.getPersistentData().getInt("amount"), ent.getId()));
					if (target instanceof Player) {
						target.getPersistentData().putInt("hungryTimer", 80);
						target.hurt(LuckyTNTDamageSources.devoured(level, entity.owner()), 15f);
						target.setDeltaMovement(new Vec3(x, y + 1d, z).reverse().normalize().scale(10d));
					} else {
						target.hurt(LuckyTNTDamageSources.devoured(level, entity.owner()), 10000f);
					}
				}
			}
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		int amount = Mth.clamp(entity.getPersistentData().getInt("amount"), 0, 20);
		int size = 80 + 4 * amount;
		float resistanceImpact = 1f - (0.833f / 20f) * amount;
		float knockback = 5f + 0.5f * amount;
		
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), size);
		explosion.doEntityExplosion(knockback, true);
		explosion.doImprovedBlockExplosion(resistanceImpact, size >= 110 ? 0.05f : 1f, false, size >= 110 ? true : false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public float getSize(IExplosiveEntity entity) {
		return 1f + (3f / 20f) * Mth.clamp(entity.getPersistentData().getInt("amount"), 0, 20);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.HUNGRY_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 600;
	}
}
