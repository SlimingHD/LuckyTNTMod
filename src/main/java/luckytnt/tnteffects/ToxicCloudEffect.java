package luckytnt.tnteffects;

import java.util.List;

import org.joml.Math;
import org.joml.Vector3f;

import luckytnt.network.ClientboundToxicCloudPacket;
import luckytnt.network.PacketHandler;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.PacketDistributor;

public class ToxicCloudEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		Entity ent = (Entity)entity;
		if (entity.getTNTFuse() == 1200 && !entity.getLevel().isClientSide()) {
			entity.getPersistentData().putDouble("size", 1d + entity.getLevel().getRandom().nextDouble() * 3d);
			PacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> ent), new ClientboundToxicCloudPacket(entity.getPersistentData().getDouble("size"), ((Entity)entity).getId()));
		}
		ent.setDeltaMovement(0, 0, 0);
		ent.setPos(ent.xOld, ent.yOld, ent.zOld);
		List<LivingEntity> list = entity.getLevel().getEntitiesOfClass(LivingEntity.class, ent.getBoundingBox().inflate(entity.getPersistentData().getDouble("size")));
		for (LivingEntity living : list) {
			living.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 4));
			living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
			living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ImprovedExplosion explosion = new ImprovedExplosion(entity.getLevel(), (Entity)entity, entity.getPos(), (int)Math.round(entity.getPersistentData().getDouble("size") * 5D));
		explosion.doEntityExplosion(2f, true);
		explosion.doImprovedBlockExplosion(1f, 1.1f, true, false, null);
		explosion.spawnExplosionParticles();
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		RandomSource random = entity.getLevel().getRandom();
		for (int count = 0; count < entity.getPersistentData().getDouble("size") * 5; count++) {
			entity.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.7f, 1f, 0.5f), 10f), true, entity.x() + entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble() - entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble(), entity.y() + entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble() - entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble(), entity.z() + entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble() - entity.getPersistentData().getDouble("size") * 1.5f * random.nextDouble(), 0, 0, 0);
		}
	}
	
	@Override
	public Block getBlock() {
		return Blocks.AIR;
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity ent) {
		return 1200;
	}
}
