package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytnt.registry.EntityRegistry;
import luckytntlib.entity.PrimedLTNT;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class JumpingTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity ent) {
		PrimedLTNT tnt = EntityRegistry.LEAPING_TNT.get().create(ent.level());
		tnt.setPos(ent.getPos());
		tnt.setOwner(ent.owner());
		tnt.setTNTFuse(1000000);
		ent.level().addFreshEntity(tnt);
		EntityRegistry.TNT_X20_EFFECT.build().serverExplosion(ent);
	}
	
	@Override
	public void explosionTick(IExplosiveEntity entity) {
		if(((Entity)entity).isOnGround()) {
			((Entity)entity).getPersistentData().putInt("bounces", ((Entity)entity).getPersistentData().getInt("bounces") + 1);
			((Entity)entity).setDeltaMovement(Math.random() * 2D - Math.random() * 2D, Math.random() * 3f, Math.random() * 2D - Math.random() * 2D);
			entity.level().playSound(null, entity.x(), entity.y(), entity.z(), SoundEvents.SLIME_JUMP, SoundSource.MASTER, 1, 1);
			
			if(((Entity)entity).getPersistentData().getInt("bounces") >= 10) {
				if(entity.level() instanceof ServerLevel) {
					serverExplosion(entity);
				}
				entity.destroy();
			}
			if(((Entity)entity).getPersistentData().getInt("bounces") >= 1 && entity.level() instanceof ServerLevel) {
				serverExplosion(entity);
			}
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.JUMPING_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 100000;
	}
}
