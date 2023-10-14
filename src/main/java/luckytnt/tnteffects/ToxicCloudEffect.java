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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.PacketDistributor;

public class ToxicCloudEffect extends PrimedTNTEffect {

	@Override
	public void explosionTick(IExplosiveEntity ent) {
		if(ent.getTNTFuse() == 1200 && !ent.getLevel().isClientSide()) {
			ent.getPersistentData().putDouble("size", 1D + Math.random() * 3D);
			PacketHandler.CHANNEL.send(new ClientboundToxicCloudPacket(ent.getPersistentData().getDouble("size"), ((Entity)ent).getId()), PacketDistributor.TRACKING_ENTITY.with((Entity)ent));
		}
		((Entity)ent).setDeltaMovement(0, 0, 0);
		((Entity)ent).setPos(((Entity)ent).xOld, ((Entity)ent).yOld, ((Entity)ent).zOld);
		List<LivingEntity> list = ent.getLevel().getEntitiesOfClass(LivingEntity.class, ((Entity)ent).getBoundingBox());
		for(LivingEntity lent : list) {
			lent.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 4));
			lent.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
			lent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		ImprovedExplosion explosion = new ImprovedExplosion(ent.getLevel(), (Entity)ent, ent.getPos(), (int)Math.round(ent.getPersistentData().getDouble("size") * 5D));
		explosion.doEntityExplosion(2f, true);
		explosion.doBlockExplosion(1f, 1f, 1f, 1.1f, true, false);
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity ent) {
		for(int count = 0; count < ent.getPersistentData().getDouble("size") * 5; count++) {
			ent.getLevel().addParticle(new DustParticleOptions(new Vector3f(0.7f, 1f, 0.5f), 10f), true, ent.x() + ent.getPersistentData().getDouble("size") * 1.5f * Math.random() - ent.getPersistentData().getDouble("size") * 1.5f * Math.random(), ent.y() + ent.getPersistentData().getDouble("size") * 1.5f * Math.random() - ent.getPersistentData().getDouble("size") * 1.5f * Math.random(), ent.z() + ent.getPersistentData().getDouble("size") * 1.5f * Math.random() - ent.getPersistentData().getDouble("size") * 1.5f * Math.random(), 0, 0, 0);
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
