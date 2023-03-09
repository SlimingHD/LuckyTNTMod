package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.SoundRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class SayGoodbyeEffect extends PrimedTNTEffect{

	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(entity.getTNTFuse() == 30) {
			entity.level().playSound(null, entity.x(), entity.y(), entity.z(), SoundRegistry.SAY_GOODBYE.get(), SoundSource.HOSTILE, 20, 1);
		}
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {		
		Player ent = entity.level().getNearestPlayer((Entity)entity, 60);
		if(ent != null) {
			ImprovedExplosion explosion = new ImprovedExplosion(ent.level, (Entity) entity, new DamageSource("say_goodbye"), ent.getX(), ent.getY(), ent.getZ(), 20f);
			explosion.doEntityExplosion(2f, true);
			explosion.doBlockExplosion(1f, 1f, 1f, 1.5f, false, false);
			if(entity.level() instanceof ServerLevel sLevel) {
				sLevel.sendParticles(ParticleTypes.EXPLOSION, ent.getX(), ent.getY(), ent.getZ(), 60, 2, 2, 2, 0);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.SAY_GOODBYE.get();
	}
}
