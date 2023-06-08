package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.SoundRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class SayGoodbyeEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() == 30) {
			entity.getLevel().playSound(null, entity.x(), entity.y(), entity.z(), SoundRegistry.SAY_GOODBYE.get(), SoundSource.HOSTILE, 20, 1);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {		
		Player ent = entity.getLevel().getNearestPlayer((Entity)entity, 60);
		if(ent != null) {
			DamageType type = new DamageType("say_goodbye", DamageScaling.NEVER, 0f, DamageEffects.HURT, DeathMessageType.DEFAULT);
			DamageSource source = new DamageSource(Holder.direct(type), (Entity)entity, entity.owner());
			
			ImprovedExplosion explosion = new ImprovedExplosion(ent.level(), (Entity) entity, source, ent.getX(), ent.getY(), ent.getZ(), 20);
			explosion.doEntityExplosion(2f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
			if(entity.getLevel() instanceof ServerLevel sLevel) {
				sLevel.sendParticles(ParticleTypes.EXPLOSION, ent.getX(), ent.getY(), ent.getZ(), 60, 2, 2, 2, 0);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SAY_GOODBYE.get();
	}
}
